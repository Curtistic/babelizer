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
 
 */package com.mrfeinberg.proxyprefs;

import java.util.prefs.Preferences;

import com.jgoodies.binding.beans.Model;

public class ProxyPreferences extends Model
{
	public static final String PROPERTYNAME_USE_PROXY = "useProxy";
	public static final String PROPERTYNAME_PROXY_HOST = "proxyHost";
	public static final String PROPERTYNAME_PROXY_PORT = "proxyPort";

	private final Preferences prefs;

	public ProxyPreferences(final Preferences prefs)
	{
		this.prefs = prefs;
	}

	public String getProxyHost()
	{
		return prefs.get(PROPERTYNAME_PROXY_HOST, "localhost");
	}

	public void setProxyHost(final String proxyHost)
	{
		final String oldHost = getProxyHost();
		prefs.put(PROPERTYNAME_PROXY_HOST, proxyHost);
		firePropertyChange(PROPERTYNAME_PROXY_HOST, oldHost, proxyHost);
	}

	public int getProxyPort()
	{
		return prefs.getInt(PROPERTYNAME_PROXY_PORT, 8080);
	}

	public void setProxyPort(final int proxyPort)
	{
		final int oldPort = getProxyPort();
		prefs.putInt(PROPERTYNAME_PROXY_PORT, proxyPort);
		firePropertyChange(PROPERTYNAME_PROXY_PORT, oldPort, proxyPort);
	}

	public boolean getUseProxy()
	{
		return prefs.getBoolean(PROPERTYNAME_USE_PROXY, false);
	}

	public void setUseProxy(final boolean useProxy)
	{
		final boolean oldUseProxy = getUseProxy();
		prefs.putBoolean(PROPERTYNAME_USE_PROXY, useProxy);
		firePropertyChange(PROPERTYNAME_USE_PROXY, oldUseProxy, useProxy);
	}

	@Override
	public String toString()
	{
		return "[ProxyPrefs: " + (getUseProxy() ? "Use " : "Do not use ") + "proxy "
				+ getProxyHost() + ":" + getProxyPort() + "]";
	}

	private static final long serialVersionUID = 1L;
}
