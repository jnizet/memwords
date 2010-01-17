<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Login</title>
</head>
<body>
    <h1>Login</h1>
    
    <p>
        Please enter your user ID and master password to log in.
    </p>
    <stripes:form beanclass="com.googlecode.memwords.web.account.LoginActionBean">
        <stripes:hidden name="requestedUrl"/>
        <table>
            <tr>
                <th>User ID :</th>
                <td><stripes:text name="userId"/></td>
            </tr>
            <tr>
                <th>Master password :</th>
                <td><stripes:password name="masterPassword" repopulate="false"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <stripes:submit name="login" value="Login"/>                    
                </td>
            </tr>
        </table>
    </stripes:form>
</body>
</html>