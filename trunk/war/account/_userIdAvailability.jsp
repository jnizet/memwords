<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
  <c:when test="${actionBean.userIdAvailable}">
    <span class="message"><fmt:message key="account._userIdAvailability.userIdAvailable"/></span>
  </c:when>
  <c:otherwise>
    <span class="error"><fmt:message key="account._userIdAvailability.userIdNotAvailable"/></span>
  </c:otherwise>
</c:choose>
