<%--
  Created by IntelliJ IDEA.
  User: blue
  Date: 23/10/2017
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.connection.FirstTwitterApp" %>

<html>
<head>
    <title>Hello Page</title>
</head>
<body>
<h2>Hello, ${user}!</h2>
<%
    FirstTwitterApp test = new FirstTwitterApp();
%>
</body>
</html>