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

import com.mrfeinberg.proxyprefs.ProxyPreferences;
import com.mrfeinberg.translation.TranslationFactory;
import com.mrfeinberg.translation.TranslationService;

public class AltavistaTranslationServiceFactory implements TranslationFactory
{

	private static AltavistaTranslationService SERVICE = null;

	public TranslationService getTranslationService(final ProxyPreferences proxyPrefs)
	{
		if (SERVICE == null)
		{
			SERVICE = new AltavistaTranslationService(proxyPrefs);
		}
		return SERVICE;
	}

	public String getName()
	{
		return "Yahoo Babelfish";
	}

}