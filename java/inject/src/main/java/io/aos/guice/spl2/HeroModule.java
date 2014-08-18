/****************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The AOS licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package io.aos.guice.spl2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class HeroModule implements Module {

	public void configure(Binder binder) {
		binder.bind(Vehicle.class).annotatedWith(Names.named("Fast")).to(WeaselCopter.class);
		binder.bind(FuelSource.class).to(HeavyWaterRefinery.class);
		binder.bind(Appendable.class).toInstance(System.out);
		loadProperties(binder);
		binder.bind(String.class).annotatedWith(Names.named("LicenseKey")).toInstance("QWERTY");
	}

	@Provides
	@Inject
	private Hero provideHero(FrogMan frogMan, WeaselGirl weaselGirl) {
		if (Math.random() > .5) {
			return frogMan;
		}
		return weaselGirl;
	}

	@Provides
	@Inject
	private WeaselGirl provideWeaselGirl(WeaselCopter copter, Appendable recorder, @Named("WeaselSaying") String saying) {
		return new WeaselGirl(copter, recorder, saying);
	}

	private void loadProperties(Binder binder) {
		InputStream stream = HeroModule.class.getResourceAsStream("/app.properties");
		Properties appProperties = new Properties();
		try {
			appProperties.load(stream);
			Names.bindProperties(binder, appProperties);
		} catch (IOException e) {
			binder.addError(e);
		}
	}

}
