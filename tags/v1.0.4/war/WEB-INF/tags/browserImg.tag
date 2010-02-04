<%@ tag isELIgnored="false" 
        import="com.googlecode.memwords.web.util.UserAgent" %>
<%@ attribute name="userAgent" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% jspContext.setAttribute("browserType", UserAgent.detectBrowserType(userAgent)); %>
<img src="<c:url value="${browserType.imageUrl}"/>" width="32" height="32" alt="${browserType}" title="<c:out value="${userAgent}"/>"/>
<br/>
<c:choose><c:when test="${browserType.unknown}"><fmt:message key="tags.browserImg.unknown"/></c:when><c:otherwise><c:out value="${browserType.name}"/></c:otherwise></c:choose>
