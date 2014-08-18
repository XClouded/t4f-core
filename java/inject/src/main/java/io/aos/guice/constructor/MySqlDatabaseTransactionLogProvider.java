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
package io.aos.guice.constructor;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MySqlDatabaseTransactionLogProvider implements
		Provider<TransactionLog> {
	private Service service;

	@Inject
	public MySqlDatabaseTransactionLogProvider(Service service) {
		this.service = service;
		System.out.println("MySqlDatabaseTransactionLogProvider Service Constructor");
	}

	public TransactionLog get() {
		MySqlDatabaseTransactionLog databaseTransactionLog = new MySqlDatabaseTransactionLog();
		databaseTransactionLog.setUrl("tmep/url");
		databaseTransactionLog.setThreadLimit(1);
		databaseTransactionLog.setService(service);
		return databaseTransactionLog;
	}

}
