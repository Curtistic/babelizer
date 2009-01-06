package com.mrfeinberg.translation.util;

import java.util.HashMap;
import java.util.Map;

/*
 * I got this from:
 * http://www.purpletech.com/code/src/com/purpletech/util/Utils.java
 */

/*
 * ====================================================================
 * Copyright (c) 1995-1999 Purple Technology, Inc. All rights reserved.
 * 
 * PLAIN LANGUAGE LICENSE: Do whatever you like with this code, free of charge,
 * just give credit where credit is due. If you improve it, please send your
 * improvements to alex@purpletech.com. Check http://www.purpletech.com/code/
 * for the latest version and news.
 * 
 * LEGAL LANGUAGE LICENSE: Redistribution and use in source and binary forms,
 * with or without modification, are permitted provided that the following
 * conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * 3. The names of the authors and the names "Purple Technology," "Purple
 * Server" and "Purple Chat" must not be used to endorse or promote products
 * derived from this software without prior written permission. For written
 * permission, please contact server@purpletech.com.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHORS AND PURPLE TECHNOLOGY ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHORS OR PURPLE TECHNOLOGY BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * ====================================================================
 *  
 */
public class HtmlEntityUtility
{
	static Object[][] entities = {
	// {"#39", new Integer(39)}, // ' - apostrophe
			{ "quot", new Integer(34) }, // " - double-quote
			{ "amp", new Integer(38) }, // & - ampersand
			{ "lt", new Integer(60) }, // < - less-than
			{ "gt", new Integer(62) }, // > - greater-than
			{ "nbsp", new Integer(160) }, // non-breaking space
			{ "copy", new Integer(169) }, // � - copyright
			{ "reg", new Integer(174) }, // � - registered trademark
			{ "Agrave", new Integer(192) }, // � - uppercase A, grave accent
			{ "Aacute", new Integer(193) }, // � - uppercase A, acute accent
			{ "Acirc", new Integer(194) }, // � - uppercase A, circumflex accent
			{ "Atilde", new Integer(195) }, // � - uppercase A, tilde
			{ "Auml", new Integer(196) }, // � - uppercase A, umlaut
			{ "Aring", new Integer(197) }, // � - uppercase A, ring
			{ "AElig", new Integer(198) }, // � - uppercase AE
			{ "Ccedil", new Integer(199) }, // � - uppercase C, cedilla
			{ "Egrave", new Integer(200) }, // � - uppercase E, grave accent
			{ "Eacute", new Integer(201) }, // � - uppercase E, acute accent
			{ "Ecirc", new Integer(202) }, // � - uppercase E, circumflex accent
			{ "Euml", new Integer(203) }, // � - uppercase E, umlaut
			{ "Igrave", new Integer(204) }, // � - uppercase I, grave accent
			{ "Iacute", new Integer(205) }, // � - uppercase I, acute accent
			{ "Icirc", new Integer(206) }, // � - uppercase I, circumflex accent
			{ "Iuml", new Integer(207) }, // � - uppercase I, umlaut
			{ "ETH", new Integer(208) }, // � - uppercase Eth, Icelandic
			{ "Ntilde", new Integer(209) }, // � - uppercase N, tilde
			{ "Ograve", new Integer(210) }, // � - uppercase O, grave accent
			{ "Oacute", new Integer(211) }, // � - uppercase O, acute accent
			{ "Ocirc", new Integer(212) }, // � - uppercase O, circumflex accent
			{ "Otilde", new Integer(213) }, // � - uppercase O, tilde
			{ "Ouml", new Integer(214) }, // � - uppercase O, umlaut
			{ "Oslash", new Integer(216) }, // � - uppercase O, slash
			{ "Ugrave", new Integer(217) }, // � - uppercase U, grave accent
			{ "Uacute", new Integer(218) }, // � - uppercase U, acute accent
			{ "Ucirc", new Integer(219) }, // � - uppercase U, circumflex accent
			{ "Uuml", new Integer(220) }, // � - uppercase U, umlaut
			{ "Yacute", new Integer(221) }, // � - uppercase Y, acute accent
			{ "THORN", new Integer(222) }, // � - uppercase THORN, Icelandic
			{ "szlig", new Integer(223) }, // � - lowercase sharps, German
			{ "agrave", new Integer(224) }, // � - lowercase a, grave accent
			{ "aacute", new Integer(225) }, // � - lowercase a, acute accent
			{ "acirc", new Integer(226) }, // � - lowercase a, circumflex accent
			{ "atilde", new Integer(227) }, // � - lowercase a, tilde
			{ "auml", new Integer(228) }, // � - lowercase a, umlaut
			{ "aring", new Integer(229) }, // � - lowercase a, ring
			{ "aelig", new Integer(230) }, // � - lowercase ae
			{ "ccedil", new Integer(231) }, // � - lowercase c, cedilla
			{ "egrave", new Integer(232) }, // � - lowercase e, grave accent
			{ "eacute", new Integer(233) }, // � - lowercase e, acute accent
			{ "ecirc", new Integer(234) }, // � - lowercase e, circumflex accent
			{ "euml", new Integer(235) }, // � - lowercase e, umlaut
			{ "igrave", new Integer(236) }, // � - lowercase i, grave accent
			{ "iacute", new Integer(237) }, // � - lowercase i, acute accent
			{ "icirc", new Integer(238) }, // � - lowercase i, circumflex accent
			{ "iuml", new Integer(239) }, // � - lowercase i, umlaut
			{ "igrave", new Integer(236) }, // � - lowercase i, grave accent
			{ "iacute", new Integer(237) }, // � - lowercase i, acute accent
			{ "icirc", new Integer(238) }, // � - lowercase i, circumflex accent
			{ "iuml", new Integer(239) }, // � - lowercase i, umlaut
			{ "eth", new Integer(240) }, // � - lowercase eth, Icelandic
			{ "ntilde", new Integer(241) }, // � - lowercase n, tilde
			{ "ograve", new Integer(242) }, // � - lowercase o, grave accent
			{ "oacute", new Integer(243) }, // � - lowercase o, acute accent
			{ "ocirc", new Integer(244) }, // � - lowercase o, circumflex accent
			{ "otilde", new Integer(245) }, // � - lowercase o, tilde
			{ "ouml", new Integer(246) }, // � - lowercase o, umlaut
			{ "oslash", new Integer(248) }, // � - lowercase o, slash
			{ "ugrave", new Integer(249) }, // � - lowercase u, grave accent
			{ "uacute", new Integer(250) }, // � - lowercase u, acute accent
			{ "ucirc", new Integer(251) }, // � - lowercase u, circumflex accent
			{ "uuml", new Integer(252) }, // � - lowercase u, umlaut
			{ "yacute", new Integer(253) }, // � - lowercase y, acute accent
			{ "thorn", new Integer(254) }, // � - lowercase thorn, Icelandic
			{ "yuml", new Integer(255) }, // � - lowercase y, umlaut
			{ "euro", new Integer(8364) }, // Euro symbol
	};

	static Map<Object, Object> e2i = new HashMap<Object, Object>();
	static Map<Object, Object> i2e = new HashMap<Object, Object>();
	static
	{
		for (int i = 0; i < entities.length; ++i)
		{
			e2i.put(entities[i][0], entities[i][1]);
			i2e.put(entities[i][1], entities[i][0]);
		}
	}

	/**
	 * Turns funky characters into HTML entity equivalents
	 * <p>
	 * e.g. <tt>"bread" & "butter"</tt>=>
	 * <tt>&amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;</tt>.
	 * Update: supports nearly all HTML entities, including funky accents. See
	 * the source code for more detail.
	 * 
	 * @see #entityDecode(String)
	 */
	public static String entityEncode(final String s1)
	{
		final StringBuffer buf = new StringBuffer();
		int i;
		for (i = 0; i < s1.length(); ++i)
		{
			final char ch = s1.charAt(i);
			final String entity = (String) i2e.get(new Integer(ch));
			if (entity == null)
			{
				if (ch > 128)
				{
					buf.append("&#" + (int) ch + ";");
				}
				else
				{
					buf.append(ch);
				}
			}
			else
			{
				buf.append("&" + entity + ";");
			}
		}
		return buf.toString();
	}

	/**
	 * Given a string containing entity escapes, returns a string containing the
	 * actual Unicode characters corresponding to the escapes.
	 * 
	 * Note: nasty bug fixed by Helge Tesgaard (and, in parallel, by Alex, but
	 * Helge deserves major props for emailing me the fix). 15-Feb-2002 Another
	 * bug fixed by Sean Brown <sean@boohai.com>
	 * 
	 * @see #entityEncode(String)
	 */
	public static String entityDecode(final String s1)
	{
		final StringBuffer buf = new StringBuffer();
		int i;
		for (i = 0; i < s1.length(); ++i)
		{
			final char ch = s1.charAt(i);
			if (ch == '&')
			{
				final int semi = s1.indexOf(';', i + 1);
				if (semi == -1)
				{
					buf.append(ch);
					continue;
				}
				final String entity = s1.substring(i + 1, semi);
				Integer iso;
				if (entity.charAt(0) == '#')
				{
					// jdf protect against bogus entity codes...
					try
					{
						iso = new Integer(entity.substring(1));
					}
					catch (final NumberFormatException e)
					{
						buf.append(ch);
						continue;
					}
				}
				else
				{
					iso = (Integer) e2i.get(entity);
				}
				if (iso == null)
				{
					buf.append("&" + entity + ";");
				}
				else
				{
					buf.append((char) iso.intValue());
				}
				i = semi;
			}
			else
			{
				buf.append(ch);
			}
		}
		return buf.toString();
	}
}