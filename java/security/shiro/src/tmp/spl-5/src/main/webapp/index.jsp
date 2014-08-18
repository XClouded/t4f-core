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
<%@ taglib prefix="jsec" uri="http://www.jsecurity.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Role and Permissions Security Home Page</title>
</head>
<body>
<h3>Welcome to The Role and Permissions Security Home Page</h3>
<p>Our web site does have security and it includes security by ROLE and by PERMISSION, so NOT everyone
who is logged in can visit all our pages.</p>

<jsec:guest>
<p><a href="login.jsp">Please login.</a></p>
</jsec:guest>

<jsec:authenticated>
<jsec:hasPermission name="secure">
<p>Below link is available only for authenticated users with a permission of secure.</p>
<p><a href="staff/index.jsp">Secure Area</a></p>
</jsec:hasPermission>

<jsec:hasRole name="admin"> 
<p>Below link is available only for authenticated users with a role of admin.</p>
<p><a href="admin/index.jsp" >Admin Area</a></p>
</jsec:hasRole>


<p><a href="LogoutUser">Log out when you're finished.</a></p>
</jsec:authenticated>

</body>
</html>
