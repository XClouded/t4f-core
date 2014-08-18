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
      sql = "DROP TABLE USER_ROLES";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();
      sql = "DROP TABLE ROLES_PERMISSIONS";
      psc = aConnection.prepareStatement(sql);
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
      
      sql = "CREATE TABLE USER_ROLES (USERNAME VARCHAR(50), ROLE_NAME VARCHAR(50))";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();
      sql = "INSERT INTO USER_ROLES (USERNAME, ROLE_NAME) VALUES ('bruce@hotmail.com', 'admin')";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();      
      sql = "INSERT INTO USER_ROLES (USERNAME, ROLE_NAME) VALUES ('bruce@hotmail.com', 'user')";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();      
      sql = "INSERT INTO USER_ROLES (USERNAME, ROLE_NAME) VALUES ('sue@hotmail.com', 'user')";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();      

      sql = "CREATE TABLE ROLES_PERMISSIONS (ROLE_NAME VARCHAR(50), PERMISSION VARCHAR(50))";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();
      sql = "INSERT INTO ROLES_PERMISSIONS (ROLE_NAME, PERMISSION) VALUES ('admin', 'winnebago:drive:eagle5,winnebago:drive:eagle7')";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();      
      sql = "INSERT INTO ROLES_PERMISSIONS (ROLE_NAME, PERMISSION) VALUES ('user', 'winnebago2')";
      psc = aConnection.prepareStatement(sql);
      psc.execute();
      psc.close();      

      
      PreparedStatement ps = null;
     
      System.out.println("\n\nHere are the users from the database:");
      String query = "SELECT u.userid, u.username, r.role_name FROM users u " +
                     " inner join user_roles r on u.username = r.username";
                    
          ps = aConnection.prepareStatement(query);
       
          rs = ps.executeQuery();
        while (rs.next())
          {
              System.out.println("User ID: " + rs.getString("userid") );
              System.out.println("Username: " + rs.getString("username") );
              System.out.println("Role: " + rs.getString("role_name") );

              
          }
        
       query = "Select * from roles_permissions";
        	
            ps = aConnection.prepareStatement(query);
        
        rs = ps.executeQuery();
        
        System.out.println("\n\nPermissions by role:");
      while (rs.next())
        {
            System.out.println("Role name: " + rs.getString("role_name") );
            System.out.println("Permission: " + rs.getString("permission") );
      

            
        }
        
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      ConnectionFactory.close(aConnection);
    }


  }
}

