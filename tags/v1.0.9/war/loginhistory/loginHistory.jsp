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
<title><fmt:message key="loginhistory.loginHistory.title"/></title>
</head>
<body>
  <h1><fmt:message key="loginhistory.loginHistory.h1"/></h1>
  <table class="data" id="loginHistoryTable">
    <tr>
      <th><fmt:message key="loginhistory.loginHistory.date"/></th>
      <th><fmt:message key="loginhistory.loginHistory.ip"/></th>
      <th style="text-align: center;"><fmt:message key="loginhistory.loginHistory.browser"/></th>
      <th style="text-align: center;"><fmt:message key="loginhistory.loginHistory.os"/></th>
    </tr>
    <c:forEach var="login" items="${actionBean.history}" varStatus="varStatus">
      <tr<c:if test="${varStatus.index mod 2 == 0}"> class="even"</c:if>>
        <td>
          <c:if test="${varStatus.index == 0}"><fmt:message key="loginhistory.loginHistory.currentSession"/><br/></c:if>
          <fmt:formatDate value="${login.date}" type="both" dateStyle="full" timeStyle="short"/>
        </td>
        <td>${login.ip}</td>
        <td style="text-align: center;"><tags:browserImg userAgent="${login.userAgent}" /></td>
        <td style="text-align: center;"><tags:osImg userAgent="${login.userAgent}" /></td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>