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

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextAreaActionProvider extends KeyAdapter
{
	private final ActionListener action;

	public TextAreaActionProvider(final ActionListener action)
	{
		super();
		this.action = action;
	}

	@Override
	public void keyPressed(final KeyEvent e)
	{
		final Component component = (Component) e.getSource();
		if (e.getKeyCode() == KeyEvent.VK_TAB)
		{
			if (!e.isShiftDown())
			{
				component.transferFocus();
			}
			else
			{
				component.transferFocusBackward();
			}
			e.consume();
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			action.actionPerformed(null);
			e.consume();
		}
	}

}
