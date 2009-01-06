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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;
import com.mrfeinberg.proxyprefs.ProxyPreferences;
import com.mrfeinberg.proxyprefs.ProxyPrefsDialog;
import com.mrfeinberg.translation.Language;
import com.mrfeinberg.translation.LanguagePair;
import com.mrfeinberg.translation.TranslationFactory;
import com.mrfeinberg.translation.TranslationService;
import com.mrfeinberg.translation.plugin.altavista.AltavistaTranslationServiceFactory;

public class BabelizerMain implements ActionListener, ManagerListener
{
	private final Preferences preferences = Preferences
			.userNodeForPackage(BabelizerMain.class);
	private final ProxyPreferences proxyPrefs = new ProxyPreferences(preferences);

	private final StatusBar status = new StatusBar();
	private final MultiLanguageTextPane textPane = new MultiLanguageTextPane();
	private final JComboBox fromMenu = new JComboBox();
	private final JComboBox toMenu = new JComboBox();
	private final JCheckBox cycleCheckbox = new JCheckBox("Cycle between languages");
	private final JButton goButton = new JButton("Translate");
	private final JTextArea phrase = new JTextArea("I speak other languages real good.");

	private transient TranslationFactory tf = null;
	private transient TranslationService ts = null;
	private transient TranslationManager tm = null;

	public BabelizerMain()
	{
		phrase.addKeyListener(new TextAreaActionProvider(new ActionListener() {
			public void actionPerformed(final ActionEvent e)
			{
				goButton.doClick();
			}
		}));
		goButton.addActionListener(this);

		phrase.setLineWrap(true);
		phrase.setRows(4);
		cycleCheckbox.setSelected(true);

		final JFrame f = getFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setJMenuBar(createMenuBar(f));

		final Container cp = f.getContentPane();
		cp.setLayout(new BorderLayout());

		final FormLayout layout = new FormLayout(//
				"right:p, 4dlu, p:g, p", //
				"p, 3dlu, p, 8dlu, p, 2dlu, t:p, 8dlu, f:d:g");
		final PanelBuilder builder = new PanelBuilder(layout);

		final CellConstraints cc = new CellConstraints();
		builder.setDefaultDialogBorder();

		builder.addLabel("From", cc.xy(1, 1));
		builder.add(fromMenu, cc.xywh(3, 1, 2, 1));
		builder.addLabel("To", cc.xy(1, 3));
		builder.add(toMenu, cc.xywh(3, 3, 2, 1));

		{
			final JScrollPane scrollPane = new JScrollPane(phrase);
			scrollPane.getVerticalScrollBar().setFocusable(false);
			builder.add(scrollPane, cc.xywh(1, 5, 4, 1));
		}

		builder.add(cycleCheckbox, cc.xywh(1, 7, 3, 1));
		builder.add(goButton, cc.xy(4, 7));

		{
			final JScrollPane scrollPane = new JScrollPane(textPane);
			scrollPane.getVerticalScrollBar().setFocusable(false);
			builder.add(scrollPane, cc.xywh(1, 9, 4, 1));
		}

		cp.add(builder.getContainer());
		cp.add(status, BorderLayout.SOUTH);

		factoryChosen(new com.mrfeinberg.translation.plugin.altavista.AltavistaTranslationServiceFactory());
		f.setVisible(true);
		phrase.grabFocus();
		phrase.selectAll();
	}

	private JMenuBar createMenuBar(final JFrame f)
	{
		final JMenuBar menuBar = new JMenuBar();
		final JMenu file = new JMenu("File");
		menuBar.add(file);
		final JMenu options = new JMenu("Options");
		file.add(options);

		final JMenu factoryMenu = new JMenu("Translation Service");
		options.add(factoryMenu);
		populateFactoryMenu(factoryMenu);

		options.add(new JMenuItem(new AbstractAction("HTTP Proxy...") {
			private static final long serialVersionUID = 4550099266324561517L;

			public void actionPerformed(final ActionEvent e)
			{
				new ProxyPrefsDialog(f, proxyPrefs).open();
			}
		}));
		file.add(new JSeparator());
		file.add(new JMenuItem(new AbstractAction("Exit") {
			private static final long serialVersionUID = -6319368356494359274L;

			public void actionPerformed(final ActionEvent e)
			{
				System.exit(0);
			}
		}));
		return menuBar;
	}

	private void populateFactoryMenu(final JMenu factoryMenu)
	{
		final ButtonGroup bg = new ButtonGroup();
		final TranslationFactory[] factories = new TranslationFactory[] { new AltavistaTranslationServiceFactory() };
		for (int i = 0; i < factories.length; i++)
		{
			final TranslationFactory fac = factories[i];
			final Action a = new AbstractAction(fac.getName()) {
				private static final long serialVersionUID = 1768287818954971991L;

				public void actionPerformed(ActionEvent e)
				{
					factoryChosen(fac);
				}
			};
			final JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(a);
			bg.add(menuItem);
			factoryMenu.add(menuItem);
			if (i == 0)
			{
				menuItem.setSelected(true);
			}
		}
	}

	private void setGuiEnabled(final boolean enabled)
	{
		fromMenu.setEnabled(enabled);
		toMenu.setEnabled(enabled);
		cycleCheckbox.setEnabled(enabled);
	}

	static class WindowCloser extends WindowAdapter
	{
		@Override
		public void windowClosing(final WindowEvent arg0)
		{
			System.exit(0);
		}
	}

	private JFrame getFrame()
	{
		final JFrame f = new JFrame("Babelizer");
		f.setSize(300, 600);
		f.addWindowListener(new WindowCloser());
		return f;
	}

	private void factoryChosen(final TranslationFactory translationFactory)
	{
		this.tf = translationFactory;
		final TranslationService newService = tf.getTranslationService(proxyPrefs);
		if (newService == ts)
		{
			return;
		}
		ts = newService;
		final Language oldFrom = getFromLanguage();
		final Language oldTo = getToLanguage();
		fromMenu.removeAllItems();
		toMenu.removeAllItems();

		final Language[] langs = ts.getSupportedLanguages();
		Arrays.sort(langs);
		for (int i = 0; i < langs.length; i++)
		{
			final Language lang = langs[i];
			fromMenu.addItem(lang);
			if (lang.equals(oldFrom))
			{
				fromMenu.setSelectedIndex(i);
			}
			toMenu.addItem(lang);
			if (lang.equals(oldTo))
			{
				toMenu.setSelectedIndex(i);
			}
		}
		final int toIndex = toMenu.getSelectedIndex();
		if (fromMenu.getSelectedIndex() == toIndex)
		{
			toMenu.setSelectedIndex((toIndex + 1) % langs.length);
		}
	}

	public static void main(final String[] args)
	{
		try
		{
			if (System.getProperty("os.name").toLowerCase().startsWith("win"))
			{
				UIManager.put("ClassLoader", LookUtils.class.getClassLoader());
				PlasticLookAndFeel.setPlasticTheme(new ExperienceBlue());
				UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
			}
		}
		catch (final Exception e)
		{
			System.err.println(e);
		}
		new BabelizerMain();
	}

	public void actionPerformed(final ActionEvent e)
	{
		if (goButton.getText().equalsIgnoreCase("cancel"))
		{
			requestCancel();
		}
		else
		{
			handleTranslate();
		}
	}

	private void handleTranslate()
	{
		setGuiEnabled(false);
		goButton.setText("Cancel");
		textPane.setText("");
		final TranslationService ts = tf.getTranslationService(proxyPrefs);

		final LanguagePair languagePair = new LanguagePair(getFromLanguage(),
				getToLanguage());
		tm = new TranslationManager(this, status, ts, phrase.getText().trim(),
				languagePair, shouldCycle());
		tm.start();
	}

	private boolean shouldCycle()
	{
		return cycleCheckbox.isSelected();
	}

	private Language getToLanguage()
	{
		return (Language) toMenu.getSelectedItem();
	}

	private Language getFromLanguage()
	{
		return (Language) fromMenu.getSelectedItem();
	}

	private void requestCancel()
	{
		if (tm != null)
		{
			tm.cancel();
		}
		tm = null;
	}

	public void translationComplete()
	{
		setGuiEnabled(true);
		goButton.setText("Translate");
	}

	public void addResult(final String phrase, final Language language)
	{
		textPane.appendLine(phrase, language);
		textPane.appendLine();
	}

}
