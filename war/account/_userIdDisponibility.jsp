<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${actionBean.userIdAvailable}">
        <span class="message">This user ID is available</span>
    </c:when>
    <c:otherwise>
        <span class="error">This user ID is not available</span>
    </c:otherwise>
</c:choose>
