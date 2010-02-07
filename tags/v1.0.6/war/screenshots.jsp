<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
  <title><fmt:message key="screenshots.title"/></title>
</head>
<body>
  <h1><fmt:message key="screenshots.h1"/></h1>
  <div class="screenshot">
    <img src="<c:url value="/img/screenshot_cards_${pageContext.request.locale}.png"/>" alt="<fmt:message key="screenshots.cardsAlt"/>"/><br/>
    <fmt:message key="screenshots.cardsLegend"/>
  </div>
  <div class="screenshot">
    <img src="<c:url value="/img/screenshot_card_details_${pageContext.request.locale}.png"/>" alt="<fmt:message key="screenshots.cardDetailsAlt"/>"/><br/>
    <fmt:message key="screenshots.cardDetailsLegend"/>
  </div>
  <div class="screenshot">
    <img src="<c:url value="/img/screenshot_create_card_${pageContext.request.locale}.png"/>" alt="<fmt:message key="screenshots.createCardAlt"/>"/><br/>
    <fmt:message key="screenshots.createCardLegend"/>
  </div>
</body>
</html>