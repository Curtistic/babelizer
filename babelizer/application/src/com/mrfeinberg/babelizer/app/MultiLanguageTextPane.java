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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.mrfeinberg.translation.Language;

class MultiLanguageTextPane extends JTextPane
{

	public MultiLanguageTextPane()
	{
		setEditable(false);
	}

	@Override
	protected void paintComponent(final Graphics g)
	{
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		super.paintComponent(g);
	}

	public void appendLine()
	{
		append("\n", Language.ENGLISH);
	}

	public void appendLine(final String text, final Language lang)
	{
		append(text + "\n", lang);
	}

	private void append(final String text, final Language lang)
	{
		setEditable(true);
		select(Integer.MAX_VALUE, Integer.MAX_VALUE);
		setSelectionLanguage(lang);
		replaceSelection(text);
		setEditable(false);
	}

	private void setSelectionLanguage(final Language lang)
	{
		setLocale(lang.getLocale());
		final MutableAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setFontFamily(set, lang.getFontName());
		StyleConstants.setFontSize(set, 18);
		setCharacterAttributes(set, false);
	}

	private static final long serialVersionUID = 5426182593804897667L;

}