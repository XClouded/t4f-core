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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jsec" uri="http://www.jsecurity.org/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>User Area Main Page</title>
</head>
<body>
<h3>User Area Main Page</h3>
<h3>Welcome <jsec:principal /></h3>
<p>Since our web site DOES have security, including access by role, only people logged in with a role of user can visit this web page because it's in our user area.</p>
<p><a href="<c:url value='/index.jsp' />">Home</a> 
<jsec:hasRole name="admin">
 | <a href="<c:url value='/admin/index.jsp' />"> Admin Area </a> 
 </jsec:hasRole>
 <jsec:hasPermission name="secure"> 
 | <a href="<c:url value='/staff/index.jsp' />" > Staff Area</a> 
 </jsec:hasPermission>
 | <a href="<c:url value='/LogoutUser' />">Log Out</a></p>
</body>
</html>
