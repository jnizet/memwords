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
<title><fmt:message key="account.createAccount.title"/></title>
<script type="text/javascript" src="<c:url value="/js/account.js"/>"></script>
<script type="text/javascript">
  $(document).ready(function () {
    $("#userId").bind("blur", function () {
        loadUserIdAvailability();
    });
    $("#userId").focus();
    $("#masterPassword").keyup(function() {
      displayPasswordStrength($("#masterPassword").val(), $("#strength"), "inline-block");
    });
    displayPasswordStrength($("#masterPassword").val(), $("#strength"), "inline-block");
  });
</script>
</head>
<body>
  <h1><fmt:message key="account.createAccount.h1"/></h1>

  <p><fmt:message key="account.createAccount.fillFormMessage"/></p>
  <tags:formNotice/>
  <stripes:form beanclass="com.googlecode.memwords.web.account.CreateAccountActionBean" 
                id="createAccountForm">
    <table>
      <tr>
        <th><label class="required"><fmt:message key="account.createAccount.userIdLabel"/></label></th>
        <td><stripes:text name="userId" id="userId" /><span id="userIdAvailability"></span></td>
      </tr>
      <tr>
        <th class="topText"><label class="required"><fmt:message key="account.createAccount.masterPasswordLabel"/></label></th>
        <td>
          <stripes:password name="masterPassword" id="masterPassword"/>
          <div class="strength" id="strength" title="<fmt:message key="main.passwordStrength"/>"></div>
        </td>
      </tr>
      <tr>
        <th><label class="required"><fmt:message key="account.createAccount.masterPasswordConfirmationLabel"/></label></th>
        <td><stripes:password name="masterPassword2" /></td>
      </tr>
      <tr>
        <td colspan="2"><stripes:submit name="createAccount"><fmt:message key="account.createAccount.createAccountButton"/></stripes:submit></td>
      </tr>
    </table>
  </stripes:form>
</body>
</html>