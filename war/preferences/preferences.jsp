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
<title><fmt:message key="preferences.preferences.title"/></title>
</head>
<body>
  <h1><fmt:message key="preferences.preferences.h1"/></h1>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePreferredLocaleActionBean">
      <img class="icon" alt="" src="<c:url value="/img/locale.png"/>"/>
      <fmt:message key="preferences.preferences.changePreferredLocale"/></stripes:link>
  </div>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePreferredTimeZoneActionBean">
      <img class="icon" alt="" src="<c:url value="/img/timezone.png"/>"/>
      <fmt:message key="preferences.preferences.changePreferredTimeZone"/></stripes:link>
  </div>
</body>
</html>