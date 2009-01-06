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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public final class ProxyPrefsEditorBuilder
{
	private final PresentationModel<Object> proxyPrefsPresentationModel;

	private JCheckBox useProxyCheckbox;
	private JTextComponent hostField;
	private JTextComponent portField;

	public ProxyPrefsEditorBuilder(final PresentationModel<Object> bookPresentationModel)
	{
		this.proxyPrefsPresentationModel = bookPresentationModel;
	}

	private void initComponents()
	{
		hostField = BasicComponentFactory.createTextField(proxyPrefsPresentationModel
				.getBufferedModel(ProxyPreferences.PROPERTYNAME_PROXY_HOST));
		portField = BasicComponentFactory.createIntegerField(proxyPrefsPresentationModel
				.getBufferedModel(ProxyPreferences.PROPERTYNAME_PROXY_PORT),
				new DecimalFormat("0"));
		useProxyCheckbox = BasicComponentFactory.createCheckBox(
				proxyPrefsPresentationModel
						.getBufferedModel(ProxyPreferences.PROPERTYNAME_USE_PROXY),
				"Use HTTP Proxy");
		useProxyCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e)
			{
				enableFields();
			}
		});
		enableFields();
	}

	protected void enableFields()
	{
		final boolean enabled = useProxyCheckbox.isSelected();
		hostField.setEnabled(enabled);
		portField.setEnabled(enabled);
	}

	public JComponent build()
	{
		initComponents();

		final FormLayout layout = new FormLayout( //
				"right:pref, 3dlu, 200dlu:grow", //
				"p, 3dlu, p, 3dlu, p");

		final PanelBuilder builder = new PanelBuilder(layout);
		final CellConstraints cc = new CellConstraints();

		builder.add(useProxyCheckbox, cc.xywh(1, 1, 3, 1));
		builder.addLabel("Host", cc.xy(1, 3));
		builder.add(hostField, cc.xy(3, 3));
		builder.addLabel("Port", cc.xy(1, 5));
		builder.add(portField, cc.xy(3, 5));

		return builder.getPanel();
	}

}