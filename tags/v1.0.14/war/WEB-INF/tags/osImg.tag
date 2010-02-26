<%@ tag isELIgnored="false" 
        import="com.googlecode.memwords.web.util.UserAgent" %>
<%@ attribute name="userAgent" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% jspContext.setAttribute("operatingSystem", UserAgent.detectOperatingSystem(userAgent)); %>
<img src="<c:url value="${operatingSystem.imageUrl}"/>" width="32" height="32" alt="${operatingSystem}" title="<c:out value="${userAgent}"/>"/>
<br/>
<c:choose><c:when test="${operatingSystem.unknown}"><fmt:message key="tags.osImg.unknown"/></c:when><c:otherwise><c:out value="${operatingSystem.name}"/></c:otherwise></c:choose>