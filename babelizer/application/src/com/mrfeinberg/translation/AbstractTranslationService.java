/*
	Copyright 2008 Jonathan Feinberg

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 
 */
package com.mrfeinberg.translation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;

import com.mrfeinberg.proxyprefs.ProxyPreferences;

public abstract class AbstractTranslationService implements TranslationService
{
	private static final ExecutorService executor = Executors
			.newSingleThreadExecutor(new ThreadFactory() {
				public Thread newThread(Runnable command)
				{
					Thread t = new Thread(command);
					t.setPriority(Thread.MIN_PRIORITY + 1);
					t.setDaemon(true);
					return t;
				}
			});

	private final Collection<Language> supportedLanguages = new ArrayList<Language>();
	private final ProxyPreferences proxyPrefs;

	private long timeout = 5000L;

	public AbstractTranslationService(final ProxyPreferences proxyPrefs)
	{
		this.proxyPrefs = proxyPrefs;
	}

	protected void addSupportedLanguage(final Language language)
	{
		supportedLanguages.add(language);
	}

	public void setTimeout(final long timeoutInMillis)
	{
		this.timeout = timeoutInMillis;
	}

	abstract protected HttpMethod getHttpMethod(String phrase, LanguagePair lp);

	abstract protected String findTranslatedText(String html);

	public Language[] getSupportedLanguages()
	{
		final Language[] langs = supportedLanguages.toArray(new Language[0]);
		Arrays.sort(langs);
		return langs;
	}

	public Runnable translate(final String phrase, final LanguagePair lp,
			final TranslationListener listener)
	{
		final Language b = lp.b();

		final HttpClient httpClient = new HttpClient();
		if (proxyPrefs.getUseProxy())
		{
			httpClient.getHostConfiguration().setProxy(proxyPrefs.getProxyHost(),
					proxyPrefs.getProxyPort());
		}

		final HttpMethod httpMethod = getHttpMethod(phrase, lp);
		final Callable<String> callable = new Callable<String>() {
			public String call() throws Exception
			{
				int result = httpClient.executeMethod(httpMethod);
				if (result != 200)
				{
					throw new Exception("Got " + result + " status for "
							+ httpMethod.getURI());
				}
				final BufferedReader in = new BufferedReader(new InputStreamReader(
						httpMethod.getResponseBodyAsStream(), "utf8"));
				try
				{
					final StringBuilder sb = new StringBuilder();
					String line;
					while ((line = in.readLine()) != null)
						sb.append(line);
					return sb.toString();
				}
				finally
				{
					in.close();
					httpMethod.releaseConnection();
				}
			}
		};
		final FutureTask<String> tc = new FutureTask<String>(callable);
		return new Runnable() {
			public void run()
			{
				try
				{
					executor.execute(tc);
					final String result = tc.get(timeout, TimeUnit.MILLISECONDS);
					String found = findTranslatedText(result);
					if (found == null)
					{
						listener.error("Cannot find translated text in result.");
					}
					else
					{
						found = found.replaceAll("\\s+", " ");
						listener.result(found, b);
					}
				}
				catch (final TimeoutException e)
				{
					listener.timedOut();
				}
				catch (final InterruptedException e)
				{
					listener.cancelled();
				}
				catch (final Exception e)
				{
					e.printStackTrace();
					listener.error(e.toString());
				}
			}
		};
	};

	protected ProxyPreferences getProxyPreferences()
	{
		return proxyPrefs;
	}
}