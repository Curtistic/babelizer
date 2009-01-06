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
package com.mrfeinberg.proxyprefs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class ProxyPrefsDialog extends JDialog
{
	private final PresentationModel<Object> proxyPresentationModel;

	private boolean canceled;

	public ProxyPrefsDialog(final Frame parent, final ProxyPreferences prefs)
	{
		super(parent, "HTTP Proxy Preferences", true);
		this.proxyPresentationModel = new PresentationModel<Object>(prefs);
		canceled = false;
	}

	public void open()
	{
		build();
		canceled = false;
		setVisible(true);
	}

	public void close()
	{
		dispose();
	}

	public boolean hasBeenCanceled()
	{
		return canceled;
	}

	private void build()
	{
		setContentPane(buildContentPane());
		pack();
		setResizable(false);
		final Dimension paneSize = getSize();
		final Dimension screenSize = getToolkit().getScreenSize();
		setLocation((screenSize.width - paneSize.width) / 2,
				(screenSize.height - paneSize.height) / 2);
	}

	private JComponent buildContentPane()
	{
		final FormLayout layout = new FormLayout("fill:pref", "fill:pref, pref");
		final PanelBuilder builder = new PanelBuilder(layout);
		builder.getPanel().setBorder(new EmptyBorder(12, 10, 10, 10));
		final CellConstraints cc = new CellConstraints();
		builder.add(buildEditorPanel(), cc.xy(1, 1));
		builder.add(buildButtonBar(), cc.xy(1, 2));
		return builder.getPanel();
	}

	private JComponent buildEditorPanel()
	{
		return new ProxyPrefsEditorBuilder(proxyPresentationModel).build();
	}

	private JComponent buildButtonBar()
	{
		final JPanel bar = ButtonBarFactory.buildOKCancelBar(new JButton(new OKAction()),
				new JButton(new CancelAction()));
		bar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
		return bar;
	}

	private class OKAction extends AbstractAction
	{
		private static final long serialVersionUID = 6837629245625045250L;

		private OKAction()
		{
			super("OK");
		}

		public void actionPerformed(final ActionEvent e)
		{
			proxyPresentationModel.triggerCommit();
			canceled = false;
			close();
		}
	}

	private class CancelAction extends AbstractAction
	{
		private static final long serialVersionUID = 1568378674977637171L;

		private CancelAction()
		{
			super("Cancel");
		}

		public void actionPerformed(final ActionEvent e)
		{
			proxyPresentationModel.triggerFlush();
			canceled = true;
			close();
		}
	}

	public static void main(final String[] args)
	{
		final ProxyPreferences pp = new ProxyPreferences(Preferences
				.userNodeForPackage(ProxyPrefsDialog.class));
		final ProxyPrefsDialog d = new ProxyPrefsDialog(null, pp);
		d.open();
	}
}
