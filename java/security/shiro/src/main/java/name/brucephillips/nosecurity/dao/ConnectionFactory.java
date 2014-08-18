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
package name.brucephillips.nosecurity.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * Description: A class that other classes may use to get a connection to the
 * data storage. This static class manages the connection and associated
 * resources. Every object in our project will use this one class to obtain a
 * connection to the data storage.
 * </p>
 * 
 */
public class ConnectionFactory {

    // Call the private constructor to initialize the DriverManager
    @SuppressWarnings("unused")
    private static ConnectionFactory ref = new ConnectionFactory();

    /**
     * Private default constructor No outside objects can create an object of
     * this class This constructor initializes the DriverManager by loading the
     * driver for the database
     */
    private ConnectionFactory() {
        String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
        System.out.println("in connection factory constructor");
        try {
            Class.forName(driverName);
        }
        catch (ClassNotFoundException e) {
            System.out.println("ERROR: exception loading driver class");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get and return a Connection object that can be used to connect to the
     * data storage
     * 
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {

        System.out.println("in get connection");

        String dbName = "securityDB";
        // Connect to the data storage
        // In this case it is Microsoft Access database
        String sourceURL = "jdbc:derby:c:/derby/" + dbName + ";create=true";
        String userName = "users";
        String password = "password";

        return DriverManager.getConnection(sourceURL, userName, password);

    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        }
        catch (SQLException e) {
            System.out.println("ERROR: Unable to close Result Set");
            System.out.println(e.getMessage());
        }
    }

    public static void close(Statement stmt) {
        try {
            stmt.close();
        }
        catch (SQLException e) {
            System.out.println("ERROR: Unable to close Statement");
            System.out.println(e.getMessage());
        }
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        }
        catch (SQLException e) {
            System.out.println("ERROR: Unable to close Statement");
            System.out.println(e.getMessage());
        }
    }

}
