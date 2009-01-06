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

import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Language implements Comparable<Language>
{
	private static final boolean IS_MAC = System.getProperty("os.name").toLowerCase()
			.indexOf("mac os x") != -1;

	private static final Set<String> FONTS = new HashSet<String>(Arrays
			.asList(GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames()));

	private static final String DEFAULT_FONT = "Serif";

	public static final Language ENGLISH = new Language("English", Locale.US,
			DEFAULT_FONT);
	public static final Language FRENCH = new Language("French", Locale.FRANCE,
			DEFAULT_FONT);
	public static final Language ITALIAN = new Language("Italian", Locale.ITALY,
			DEFAULT_FONT);
	public static final Language SPANISH = new Language("Spanish",
			new Locale("es", "ES"), DEFAULT_FONT);
	public static final Language PORTUGUESE = new Language("Portuguese", new Locale("pt",
			"BR"), DEFAULT_FONT);
	public static final Language JAPANESE = new Language("Japanese", Locale.JAPAN,
			getJapaneseFont());
	public static final Language KOREAN = new Language("Korean", Locale.KOREA,
			getKoreanFont());
	public static final Language DUTCH = new Language("Dutch", new Locale("nl", "NL"),
			DEFAULT_FONT);
	public static final Language GERMAN = new Language("German", Locale.GERMANY,
			DEFAULT_FONT);
	public static final Language RUSSIAN = new Language("Russian",
			new Locale("ru", "RU"), DEFAULT_FONT);
	public static final Language GREEK = new Language("Greek", new Locale("el", "GR"),
			DEFAULT_FONT);
	public static final Language CHINESE = new Language("Chinese", Locale.CHINESE,
			getChineseFont());

	private final String name;
	private final Locale locale;
	private final String fontName;

	private Language(final String name, final Locale locale, final String fontName)
	{
		this.name = name;
		this.locale = locale;
		this.fontName = fontName;
	}

	public Locale getLocale()
	{
		return locale;
	}

	public String getFontName()
	{
		return fontName;
	}

	@Override
	public String toString()
	{
		return name;
	}

	public int compareTo(final Language o)
	{
		if (this == ENGLISH)
			return -1;
		else if (o == ENGLISH)
			return 1;
		else
			return name.compareTo(o.name);
	}

	private static String getJapaneseFont()
	{
		if (IS_MAC)
			return DEFAULT_FONT;
		if (FONTS.contains("MS Mincho"))
			return "MS Mincho";
		if (FONTS.contains("MS Gothic"))
			return "MS Gothic";
		System.err.println("You do not have any recognizable Japanese fonts installed.");
		return DEFAULT_FONT;
	}

	private static String getKoreanFont()
	{
		if (IS_MAC)
			return DEFAULT_FONT;
		if (FONTS.contains("Batang"))
			return "Batang";
		if (FONTS.contains("BatangChe"))
			return "BatangChe";
		if (FONTS.contains("GulimChe"))
			return "GulimChe";
		System.err.println("You do not have any recognizable Korean fonts installed.");
		return DEFAULT_FONT;
	}

	private static String getChineseFont()
	{
		if (IS_MAC)
			return DEFAULT_FONT;
		if (FONTS.contains("PMingLiU"))
			return "PMingLiU";
		if (FONTS.contains("MingLiU"))
			return "MingLiU";
		System.err
				.println("You do not have any recognizable traditional Chinese fonts installed.");
		return DEFAULT_FONT;
	}

}