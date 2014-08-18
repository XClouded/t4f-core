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
package io.aos.aop.proxy.p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class for common persistence functions
 */
public abstract class DaoUtil {

    private static ThreadLocal<Connection> connections = new ThreadLocal<Connection>() {
        @Override
        protected synchronized Connection initialValue() {
            return createConnection();
        }
        @Override
        public Connection get() {
            if (super.get() == null) {
                set(createConnection());
            }
            return super.get();
        }
    };

    public static synchronized Connection getConnection() {
        return (Connection) connections.get();
    }

    public static synchronized void closeConnection(Connection conn) {
        close(conn);
        connections.set(null);
    }

    /**
     * Get a connection
     * This code to be replaced with however you derive your datasource!!!!
     */
    private static Connection createConnection() {

        try {

            PropertyUtil props = PropertyUtil.get(
                    "au.com.proxy.util.datasource", "mysql");
            // load driver
            Class.forName(props.getAsString("driver")).newInstance();

            String username = props.getAsString("user.name");
            String password = props.getAsString("user.password");
            String url = props.getAsString("db.url");

            Connection conn = null;

            if (username != null && password != null)
                conn = DriverManager.getConnection(url, username, password);
            else
                conn = DriverManager.getConnection(url);

            return conn;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Close a result set
     */
    public static void close(ResultSet results) {
        try {
            if (results != null)
                results.close();
        } catch (SQLException exception) {
        }
    }

    /**
     * Close a connection
     */
    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close a prepared statement
     */
    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close all resources
     */
    public static void close(Connection connection, PreparedStatement stmt,
            ResultSet results) {
        close(results);
        close(stmt);
        close(connection);
    }

    /**
     * Substitute prepared stmt query parameters
     * 
     * @param stmt
     * @param params
     */
    public static void substitute(PreparedStatement stmt, Object[] params)
            throws SQLException {
        for (int i = 0; i < params.length; ++i) {
            stmt.setObject(i + 1, params[i]);
        }
    }

}
