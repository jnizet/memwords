<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><fmt:message key="account.changeMasterPassword.title"/></title>
<script type="text/javascript">
  $(document).ready(function () {
    $("#cancelButton").click(function() {
      window.location = m.url("/account/Account.action");
    });
    $("#newPassword").keyup(function() {
      m.displayPasswordStrength($("#newPassword").val(), $("#strength"), "inline-block");
    });
    m.displayPasswordStrength($("#newPassword").val(), $("#strength"), "inline-block");
    $("#currentPassword").focus();
  });
</script>
</head>
<body>
  <h1><fmt:message key="account.changeMasterPassword.h1"/></h1>
  <p><fmt:message key="account.changeMasterPassword.fillFormMessage"/></p>
  <tags:formNotice />
  <stripes:form beanclass="com.googlecode.memwords.web.account.ChangeMasterPasswordActionBean" 
                method="post"
                id="changeMasterPasswordForm">
    <table>
      <tr>
        <th><label class="required" for="<fmt:message key="main.loadingMessage"/>"><fmt:message 
          key="account.changeMasterPassword.currentPasswordLabel"/></label></th>
        <td><stripes:password name="currentPassword" id="currentPassword" repopulate="false"/></td>
      </tr>
      <tr>
        <th><label class="required" for="newPassword"><fmt:message 
          key="account.changeMasterPassword.newPasswordLabel"/></label></th>
        <td>
          <stripes:password name="newPassword" repopulate="false" id="newPassword"/>
          <div id="strength" class="strength" title="<fmt:message key="main.passwordStrength"/>"></div>
        </td>
      </tr>
      <tr>
        <th><label class="required" for="newPasswordConfirmation"><fmt:message 
          key="account.changeMasterPassword.newPasswordConfirmationLabel"/></label></th>
        <td><stripes:password name="newPasswordConfirmation" repopulate="false" id="newPasswordConfirmation"/></td>
      </tr>
      <tr>
        <td colspan="2">
          <stripes:submit name="change"><fmt:message key="account.changeMasterPassword.submitButton"/></stripes:submit>
          <stripes:submit name="cancel" id="cancelButton"><fmt:message 
            key="account.changeMasterPassword.cancelButton"/></stripes:submit>
        </td>
      </tr>
    </table>
  </stripes:form>
</body>
</html>
