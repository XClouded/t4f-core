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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import name.brucephillips.nosecurity.model.*;

public class UserDAO
{
  
    
    public static List<User> getUser(String userName)
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
    	
    	  

  	      
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List <User>usersList = new ArrayList<User>();
        
        String query = "SELECT * FROM users " +
                       "WHERE username = ?";
        try
        {
        	
        	
            ps = connection.prepareStatement(query);
            ps.setString(1, userName);
            rs = ps.executeQuery();
            while (rs.next())
            {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setUserID(Long.valueOf(rs.getString("userid") ) );
                user.setPassword(rs.getString("password") );
                
                usersList.add(user);
            }
            return usersList;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }        
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static List <User> getAllUsers()
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
    	
    	//Connection connection;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List <User>usersList = new ArrayList<User>();
        
        String query = "SELECT * FROM users ";
        try
        {
        	//connection = ConnectionFactory.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next())
            {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setUserID(Long.valueOf(rs.getString("userid") ) );
                user.setPassword(rs.getString("password") );
                usersList.add(user);
            }
            return usersList;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }        
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            //pool.freeConnection(connection);
        }
    }
}
