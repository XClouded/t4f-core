<%--
  Licensed to the AOS Community (AOS) under one or more
  contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The AOS licenses this file
  to you under the Apache License, Version 2.0 (the 
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
--%>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Role Security Home Page</title>
</head>
<body>
<h3>Welcome to The Role Security Home Page</h3>
<p>Our web site does have security and it includes security by ROLE, so NOT everyone
who is logged in can visit all our pages.</p>
<p>If you haven't <a href='login.jsp'>logged in</a> you cannot visit the <a href='secure/index.jsp'>pages in the secure area</a>.</p>
<p>If you haven't logged in yet or if you've logged in but don't have the 
admin role, you most cannot see our <a href='GetAllUsers'> secret users list.</a></p>
<p>If you are logged in you can view the above pages.  You should <a href='LogoutUser'>log out</a> when 
you're finished.</p>
</body>
</html>
