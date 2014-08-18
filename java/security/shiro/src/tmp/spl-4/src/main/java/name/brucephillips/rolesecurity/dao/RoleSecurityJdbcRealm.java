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
package name.brucephillips.rolesecurity.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.jsecurity.realm.jdbc.JdbcRealm;

/**
 * Sub-class of JdbcRealm that defines
 * the Data Source the JdbcRealm should
 * use
 * The configuration specified in web.xml
 * will cause an object of this class to 
 * be injected into the SecurityManager
 * @author brucephillips
 *
 */
public class RoleSecurityJdbcRealm extends JdbcRealm {

	public RoleSecurityJdbcRealm() {
		
		super();

		//get the DataSource that JSecurity's JdbcRealm
		//should use to find the user's password
		//using the provided username
		//see context.xml for this DataSource's properties
        InitialContext ic;
        DataSource dataSource;
		try {
			
			ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/security");
			this.setDataSource(dataSource);
			
		} catch (NamingException e) {
			
			e.printStackTrace();
			
		}
	
	}

}
