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
package io.aos.guice.annotation;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class RealBillingService implements BillingService {
	private CreditCardProcessor processor;
	private CreditCardProcessor checkoutProcessor;
	private TransactionLog transactionLog;
	private TransactionLog fileTransactionLog;

	public String toString(){
		return this.getClass().getName();
	}

	public RealBillingService() {
		System.out.println("RealBillingService Constructor");
	}

	@Inject
	RealBillingService(CreditCardProcessor processor, @Named("Checkout") CreditCardProcessor checkoutProcessor, TransactionLog transactionLog, @File TransactionLog fileTransactionLog) {
		System.out.println("RealBillingService Constructor");
		this.processor = processor;
		this.checkoutProcessor = checkoutProcessor;
		this.transactionLog = transactionLog;
		this.fileTransactionLog = fileTransactionLog;
		System.out.println("CreditCardProcessor : "+processor);
		System.out.println("CheckoutCreditCardProcessor : "+checkoutProcessor);
		System.out.println("TransactionLog : "+transactionLog);
		System.out.println("FileTransactionLog : "+fileTransactionLog);
	}

}
