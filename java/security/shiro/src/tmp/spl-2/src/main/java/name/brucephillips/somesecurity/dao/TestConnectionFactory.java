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
package name.brucephillips.somesecurity.dao;

/**
 * <p>Title: TestConnectionFactory.java</p>
 *
 * <p>Description: Test ConnectionFactory class</p>
 *
 *
 *
 * <p>Date: September 2005</p>
 *
 * @author Bruce Phillips
 * @version 1.0
 */

import java.sql.* ;



public class TestConnectionFactory {

  public static void main(String... args) {

    Connection aConnection = null ;

    try {

       aConnection = ConnectionFactory.getConnection();

       System.out.println("Connection successfully received.");

       DatabaseMetaData dbmd = aConnection.getMetaData() ;

       System.out.println("Database name: " + dbmd.getDatabaseProductName() );


      System.out.println("Information about the database: ");
      System.out.println("Database name=[" + dbmd.getDatabaseProductName()
                         + "]");
      System.out.println("Database version=["
                         + dbmd.getDatabaseProductVersion() + "]");
      System.out.println("Driver name=[" + dbmd.getDriverName() + "]");
      System.out.println("Driver version=[" + dbmd.getDriverVersion()
                         + "]");

      System.out.println("\nInformation about stored procedure support:");
      System.out.println("Vendors name for \'procedure\'=["
                         + dbmd.getProcedureTerm() + "]");
      System.out.println("All procedures are callable: ["
                         + dbmd.allProceduresAreCallable() + "]");
      System.out.println("Supports stored procedures: ["
                         + dbmd.supportsStoredProcedures() + "]");
      ResultSet rs = dbmd.getProcedures(null, null, null);
      System.out.println("Procedures stored in the database: ");
      int count = 0;
      while (rs.next()) {
        System.out.print(rs.getString("PROCEDURE_NAME") + " ");
        if (count++ > 25) {
          break;
        }
      }
      System.out.println();


      System.out
        .println("Supports forward only ResultSet=["
                 + dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)
                 + "]");
      System.out
        .println("Supports scroll sensitive ResultSet=["
                 + dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)
                 + "]");
      System.out
        .println("Supports scroll insensitive ResultSet=["
                 + dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)
                 + "]");

      System.out
        .println("Supports updatable ResultSet=["
                 + dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
                 + "]");
      System.out.println("Supports ANSI92 entry=["
                         + dbmd.supportsANSI92EntryLevelSQL() + "]");
      System.out.println("Supports ANSI92 intermediate=["
                         + dbmd.supportsANSI92IntermediateSQL() + "]");
      System.out.println("Supports ANSI92 full=["
                         + dbmd.supportsANSI92FullSQL() + "]");
      
      String sql = "DROP TABLE USERS";
      PreparedStatement psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();

      sql = "CREATE TABLE USERS (USERID BIGINT, USERNAME VARCHAR(50), PASSWORD VARCHAR(50))";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();
      
      sql = "INSERT INTO USERS (USERID, USERNAME, PASSWORD) VALUES (12, 'bruce@hotmail.com', 'bruce')";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();
      
      sql = "INSERT INTO USERS (USERID, USERNAME, PASSWORD) VALUES (13, 'sue@hotmail.com', 'sue')";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();
      
      PreparedStatement ps = null;
      System.out.println("\n\nHere are the users from the database:");
      String query = "SELECT * FROM users " ;
                    
          ps = aConnection.prepareStatement(query);
       
          rs = ps.executeQuery();
        while (rs.next())
          {
              System.out.println("User ID: " + rs.getString("userid") );
              System.out.println("Username: " + rs.getString("username") );
              System.out.println("Password: " + rs.getString("password") );

              
          }
        
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      ConnectionFactory.close(aConnection);
    }


  }
}

