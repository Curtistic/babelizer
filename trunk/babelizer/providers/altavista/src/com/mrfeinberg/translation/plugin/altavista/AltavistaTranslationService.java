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
package com.mrfeinberg.translation.plugin.altavista;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.mrfeinberg.proxyprefs.ProxyPreferences;
import com.mrfeinberg.translation.AbstractTranslationService;
import com.mrfeinberg.translation.Language;
import com.mrfeinberg.translation.LanguagePair;
import com.mrfeinberg.translation.util.HtmlEntityUtility;

class AltavistaTranslationService extends AbstractTranslationService
{

	private static final String END_TOKEN = "</div>";
	private static final String START_TOKEN = "<div style=\"padding:0.6em;\">";
	private static final Map<Language, String> LANGUAGES = new HashMap<Language, String>();
	static
	{
		LANGUAGES.put(Language.ENGLISH, "en");
		LANGUAGES.put(Language.ITALIAN, "it");
		LANGUAGES.put(Language.FRENCH, "fr");
		LANGUAGES.put(Language.SPANISH, "es");
		LANGUAGES.put(Language.PORTUGUESE, "pt");
		LANGUAGES.put(Language.DUTCH, "nl");
		LANGUAGES.put(Language.GERMAN, "de");
		LANGUAGES.put(Language.GREEK, "el");
		LANGUAGES.put(Language.JAPANESE, "ja");
		LANGUAGES.put(Language.KOREAN, "ko");
		LANGUAGES.put(Language.CHINESE, "zt");
		LANGUAGES.put(Language.RUSSIAN, "ru");
	}

	public AltavistaTranslationService(final ProxyPreferences proxyPrefs)
	{
		super(proxyPrefs);
		for (final Iterator<Language> it = LANGUAGES.keySet().iterator(); it.hasNext();)
		{
			addSupportedLanguage(it.next());
		}
	}

	@Override
	protected HttpMethod getHttpMethod(final String phrase, final LanguagePair lp)
	{
		final PostMethod post = new PostMethod("http://babelfish.yahoo.com/translate_txt");
		post.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		post.addRequestHeader("Accept-Charset", "ISO-8859-1,utf-8");
		final NameValuePair[] params = new NameValuePair[] {
				new NameValuePair("doit", "done"),
				new NameValuePair("ei", "UTF-8"),
				new NameValuePair("intl", "1"),
				new NameValuePair("trtext", phrase),
				new NameValuePair("lp", LANGUAGES.get(lp.a()) + "_"
						+ LANGUAGES.get(lp.b())), };
		post.setRequestBody(params);
		return post;
	}

	@Override
	protected String findTranslatedText(final String html)
	{
		int startIndex = html.indexOf(START_TOKEN);
		if (startIndex == -1)
		{
			return null;
		}
		startIndex += START_TOKEN.length();
		final int endIndex = html.indexOf(END_TOKEN, startIndex);
		if (endIndex == -1)
		{
			return null;
		}
		final String withEntities = html.substring(startIndex, endIndex).trim();
		return HtmlEntityUtility.entityDecode(withEntities);
	}
}