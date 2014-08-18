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
package io.aos.guice.justintime;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class PaypalCreditCardProcessor implements CreditCardProcessor {

	private String apiKey;

	public String toString() {
		return this.getClass().getName();
	}

	public PaypalCreditCardProcessor() {
		System.out.println("PaypalCreditCardProcessor Constructor");
	}

	@Inject
	public PaypalCreditCardProcessor(@Named("PayPal API key") String apiKey) {
		this.apiKey = apiKey;
		System.out.println("PaypalCreditCardProcessor String Argument Constructor");
	}
}
