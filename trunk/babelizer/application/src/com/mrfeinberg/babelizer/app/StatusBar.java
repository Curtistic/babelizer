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
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel
{

	private final JLabel label = new JLabel("Idle.");

	transient private final Timer statusClearingTimer = new Timer();
	transient private TimerTask statusClearingTask = null;

	public StatusBar()
	{
		super(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
		add(label);
	}

	public void setError(final String text)
	{
		setStatus(text, Color.red);
	}

	public void setStatus(final String text)
	{
		setStatus(text, Color.black);
	}

	private void setStatus(final String text, final Color color)
	{
		if (statusClearingTask != null)
		{
			statusClearingTask.cancel();
		}
		label.setText(text);
		label.setForeground(color);
		statusClearingTask = new TimerTask() {
			@Override
			public void run()
			{
				label.setText("Idle.");
				label.setForeground(Color.black);
			}
		};
		statusClearingTimer.schedule(statusClearingTask, 5000);
	}

	private static final long serialVersionUID = -1404602343527591110L;

}
