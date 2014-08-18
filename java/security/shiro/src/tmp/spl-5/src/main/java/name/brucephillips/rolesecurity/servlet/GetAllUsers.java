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
package name.brucephillips.rolesecurity.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import name.brucephillips.rolesecurity.dao.UserDAO;
import name.brucephillips.rolesecurity.model.User;

import org.jsecurity.SecurityUtils;
import org.jsecurity.subject.Subject;

/**
 * If user associated with this request is 
 * authenticated then gets all users from data store
 * and forwards user to the /admin/users.jsp
 * 
 * If user is not authenticated then forwards
 * user to the /login.jsp
 * 
 * If user is authenticated but doesn't have the 
 * admin role forwards user to /unauthorized.jsp
 * 
 * Uses JSecurity to determine if user is logged in
 * or not
 * 
 *
 */
 public class GetAllUsers extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public GetAllUsers() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	   //Get the user associated with this request
	   //see: http://jsecurity.org/api/index.html?org/jsecurity/subject/Subject.html
		Subject currentUser = SecurityUtils.getSubject();
		
		String url = "/login.jsp";
		
		if (currentUser.isAuthenticated() && ! currentUser.hasRole("admin") ) {
			
			//user is authenticated but doesn't have a role that
			//allows her to access this feature
			url = "/unauthorized.jsp";
			
			
		}
		
		//check if the user is logged in
		//and has a role of admin
		//see http://jsecurity.org/api/org/jsecurity/subject/Subject.html
		if (currentUser.isAuthenticated() && currentUser.hasRole("admin") ) {
			//user is logged in 
			//get users from data store
			//and forward to /admin/users.jsp
	        url = "/admin/users.jsp";
	        
	        List<User> userList = UserDAO.getAllUsers();
	        
	        request.setAttribute("userList", userList);
        
		}
        
        // forward the request and response to the view
        RequestDispatcher dispatcher =
             getServletContext().getRequestDispatcher(url);
        
        dispatcher.forward(request, response);   
		
	}   	  	    
}
