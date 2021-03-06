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
<title><fmt:message key="account.destroyAccount.title"/></title>
<script type="text/javascript">
  $("document").ready(function() {
    $("#cancelButton").click(function() {
      window.location = m.url("/account/Account.action");
      return false;
    });
  });
</script>
</head>
<body>
  <h1><fmt:message key="account.destroyAccount.h1"/></h1>

  <p><fmt:message key="account.destroyAccount.confirmationMessage"/></p>
  <stripes:form beanclass="com.googlecode.memwords.web.account.DestroyAccountActionBean" 
                id="destroyAccountForm">
    <stripes:submit name="destroy"><fmt:message key="account.destroyAccount.destroyButton"/></stripes:submit>
    <stripes:submit name="cancel" id="cancelButton"><fmt:message key="account.destroyAccount.cancelButton"/></stripes:submit>
  </stripes:form>
</body>
</html>
