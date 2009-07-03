// SMSLib for Java v3
// A Java API library for sending and receiving SMS via a GSM modem
// or other supported gateways.
// Web Site: http://www.smslib.org
//
// Copyright (C) 2002-2008, Thanasis Delenikas, Athens/GREECE.
// SMSLib is distributed under the terms of the Apache License version 2.0
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.smslib.scheduler;

import org.smslib.Service;

public abstract class ASchedulerTask implements Runnable
{
	String name;

	Service service;

	volatile boolean enabled;

	public ASchedulerTask(String myName, Service myService)
	{
		setName(myName);
		setService(myService);
		disable();
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String myName)
	{
		this.name = myName;
	}

	Service getService()
	{
		return service;
	}

	void setService(Service service)
	{
		this.service = service;
	}

	public boolean isEnabled()
	{
		return this.enabled;
	}

	public void enable()
	{
		this.enabled = true;
	}

	public void disable()
	{
		this.enabled = false;
	}

	public void run()
	{
		if (isEnabled())
		{
			getService().getLogger().logDebug("Running task: " + getName(), null, null);
			process();
		}
		else getService().getLogger().logDebug("Task is disabled: " + getName(), null, null);
	}

	public abstract void process();
}
