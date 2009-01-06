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
package com.mrfeinberg.babelizer.app;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import com.mrfeinberg.translation.Language;
import com.mrfeinberg.translation.LanguagePair;
import com.mrfeinberg.translation.TranslationListener;
import com.mrfeinberg.translation.TranslationService;

public class TranslationManager implements TranslationListener
{
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private final ManagerListener app;
	private final LanguagePair lp;
	private final boolean cycle;
	private final StatusBar status;
	private final TranslationService ts;
	private final Set<String> seen = new HashSet<String>();
	private FutureTask<Object> currentTransaction = null;
	private String currentPhrase = null;

	public TranslationManager(final ManagerListener app, final StatusBar status,
			final TranslationService ts, final String phrase, final LanguagePair lp,
			final boolean cycle)
	{
		this.app = app;
		this.lp = lp;
		this.cycle = cycle;
		this.status = status;
		this.ts = ts;
		currentPhrase = phrase;
	}

	public synchronized void start()
	{
		currentTransaction = new FutureTask<Object>(Executors.callable(ts.translate(
				currentPhrase, lp, TranslationManager.this)));
		status.setStatus(lp.a() + " -> " + lp.b());
		lp.swap();
		executor.execute(currentTransaction);
	}

	public synchronized void cancel()
	{
		currentTransaction.cancel(true);
	}

	public synchronized void result(final String result, final Language language)
	{
		currentPhrase = result;
		app.addResult(result, language);
		if (cycle && !seen.contains(result))
		{
			seen.add(result);
			start();
		}
		else
		{
			status.setStatus("Done.");
			app.translationComplete();
		}
	}

	private synchronized void terminateWithError(final String message)
	{
		status.setError(message);
		app.translationComplete();
	}

	public void error(final String message)
	{
		terminateWithError(message);
	}

	public void cancelled()
	{
		terminateWithError("Cancelled.");
	}

	public void timedOut()
	{
		terminateWithError("Timed out.");
	}
}