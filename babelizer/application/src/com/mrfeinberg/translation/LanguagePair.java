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

public class LanguagePair
{
	private Language a, b;

	public LanguagePair(final Language a, final Language b)
	{
		this.a = a;
		this.b = b;
	}

	public void swap()
	{
		final Language tmp = a;
		a = b;
		b = tmp;
	}

	public Language a()
	{
		return a;
	}

	public Language b()
	{
		return b;
	}
}