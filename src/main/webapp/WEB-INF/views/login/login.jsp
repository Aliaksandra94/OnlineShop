<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 24.06.2021
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form:form method="post" action="/login">
    <div align="center" style="color: #bc1b1b; font-size: larger;">${msg}</div>
    <label>Login</label>
    <input name="login" required/>

    <label>Pass</label>
    <input name="password" required/>

    <input type="submit" value="submit">
</form:form>
</body>
</html>