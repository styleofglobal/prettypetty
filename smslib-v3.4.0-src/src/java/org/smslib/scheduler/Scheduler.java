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

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.smslib.Service;
import org.smslib.helper.Logger;

public class Scheduler
{
	ScheduledThreadPoolExecutor executor;

	Service srv;

	Logger logger;

	public Scheduler(Service myService, Logger myLogger, int pool)
	{
		this.executor = new ScheduledThreadPoolExecutor(pool);
		this.srv = myService;
		this.logger = myLogger;
	}

	Service getService()
	{
		return this.srv;
	}

	Logger getLogger()
	{
		return this.logger;
	}

	ScheduledThreadPoolExecutor getExecutor()
	{
		return this.executor;
	}

	public boolean executeOnce(ASchedulerTask myTask)
	{
		getExecutor().execute(myTask);
		return true;
	}

	public boolean executeOnceAfter(ASchedulerTask myTask, int myDelay, TimeUnit myTimeUnit)
	{
		getExecutor().schedule(myTask, myDelay, myTimeUnit);
		return true;
	}

	public boolean scheduleAtFixedRate(ASchedulerTask myTask, long myInitialDelay, long myPeriod, TimeUnit myTimeUnit)
	{
		getExecutor().scheduleAtFixedRate(myTask, myInitialDelay, myPeriod, myTimeUnit);
		return true;
	}

	public boolean scheduleWithFixedDelay(ASchedulerTask myTask, long myInitialDelay, long myPeriod, TimeUnit myTimeUnit)
	{
		getExecutor().scheduleWithFixedDelay(myTask, myInitialDelay, myPeriod, myTimeUnit);
		return true;
	}

	public boolean remove(ASchedulerTask myTask)
	{
		myTask.disable();
		return getExecutor().remove(myTask);
	}

	public void shutdown()
	{
		getExecutor().shutdown();
	}
}
