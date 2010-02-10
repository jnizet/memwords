<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><fmt:message key="account.login.title"/></title>
<script type="text/javascript" src="<c:url value="/js/account.js"/>"></script>
<script type="text/javascript">
  $("document").ready(function() {
    $("#userId").focus();
  });
</script>
</head>
<body>
  <h1><fmt:message key="account.login.h1"/></h1>
  
  <p><fmt:message key="account.login.fillFormMessage"/></p>
  <tags:formNotice/>
  <stripes:form beanclass="com.googlecode.memwords.web.account.LoginActionBean" id="loginForm">
    <stripes:hidden name="requestedUrl" />
    <table>
      <tr>
        <th><label class="required" for="userId"><fmt:message key="account.login.userIdLabel"/></label></th>
        <td><stripes:text name="userId" id="userId"/></td>
      </tr>
      <tr>
        <th><label class="required" for="masterPassword"><fmt:message key="account.login.masterPasswordLabel"/></label></th>
        <td><stripes:password name="masterPassword" id="masterPassword" repopulate="false" /></td>
      </tr>
      <tr>
        <td colspan="2"><stripes:submit name="login"><fmt:message key="account.login.loginButton"/></stripes:submit></td>
      </tr>
    </table>
  </stripes:form>
</body>
</html>