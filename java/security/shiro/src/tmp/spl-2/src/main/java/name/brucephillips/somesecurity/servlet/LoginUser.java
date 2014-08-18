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
package name.brucephillips.somesecurity.servlet;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jsecurity.SecurityUtils;
import org.jsecurity.authc.IncorrectCredentialsException;
import org.jsecurity.authc.UnknownAccountException;
import org.jsecurity.authc.UsernamePasswordToken;
import org.jsecurity.subject.Subject;


/**
 * Uses JSecurity to authenticate a user
 * If user can be authenticated successfully
 * forwards user to /secure/index.jsp
 * 
 * If user cannot be authenticated then forwards
 * user to the /login.jsp which will display
 * an error message
 *
 */
 public class LoginUser extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoginUser() {
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

		String url = "/login.jsp";
		
		//see /login.jsp for these form fields
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println(username);
		System.out.println(password);
		
	    //create a UsernamePasswordToken using the
		//username and password provided by the user
		//See:  http://jsecurity.org/api/org/jsecurity/authc/UsernamePasswordToken.html
		UsernamePasswordToken token = 
			new UsernamePasswordToken(username, password);
	
		try {
			
			//get the user (aka subject) associated with 
			//this request.
			
			Subject subject = SecurityUtils.getSubject();
			
			//The use of JSecurityFilter specified in web.xml
			//caused JSecurity to create the DefaultWebSecurityManager object
			//see: http://jsecurity.org/api/org/jsecurity/web/DefaultWebSecurityManager.html
			//This security manager is the default for web-based applications
			//The SecurityUtils was provided that security manager automatically
			//The configuration specified in web.xml caused 
			//a JdbcRealm object to be provided to the SecurityManager
			//so when the login method is called that JdbcRealm
			//object will be used
			//This application uses all the other defaults
			//For example the default authentication query string is
			//"select password from users where username = ?"
			//since the database this application uses (securityDB)
			//has a users table and that table has a column named username
			//and a column named password, the default authentication query
			//string will work
			//see http://jsecurity.org/api/constant-values.html#org.jsecurity.realm.jdbc.JdbcRealm.DEFAULT_AUTHENTICATION_QUERY
			//The call to login will cause the following to occur
			//JSecurity will query the database for a password associated with the 
			//provided username (which is stored in token).  If a password is found 
			//and matches the password
			//provided by the user (also stored in the token), a new Subject will be created that is
			//authenticated.  This subject will be bound to the session for the
			//user who made this request
			//see:  http://jsecurity.org/api/org/jsecurity/authc/Authenticator.html#authenticate(org.jsecurity.authc.AuthenticationToken)
			//for a list of potential Exceptions that might be generated if
			//authentication fails (e.g. incorrect password, no username found)

			subject.login(token);
			
			
			//clear the information stored in the token
			//see: http://jsecurity.org/api/org/jsecurity/authc/UsernamePasswordToken.html#clear()
			token.clear();
			
			url = "/secure/index.jsp";

		} catch (UnknownAccountException ex) {
			//username provided was not found
			ex.printStackTrace();
			request.setAttribute("error", ex.getMessage() );
			
		} catch (IncorrectCredentialsException ex) {
			//password provided did not match password found in database
			//for the username provided
			ex.printStackTrace();
			request.setAttribute("error", ex.getMessage());
		}
		
		catch (Exception ex) {
			
			ex.printStackTrace();
			
			request.setAttribute("error", "Login NOT SUCCESSFUL - cause not known!");
			
		}
		
		
	     // forward the request and response to the view
        RequestDispatcher dispatcher =
             getServletContext().getRequestDispatcher(url);
        
        dispatcher.forward(request, response);   
	
		
	}   	  	    
}
