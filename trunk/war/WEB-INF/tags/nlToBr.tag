<%@ tag isELIgnored="false"  %>
<%@ attribute name="value" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%! private static final String DELIMS = "\r\n"; %>
<% jspContext.setAttribute("delims", DELIMS); %>
<c:forTokens items="${value}" delims="${delims}" var="line" varStatus="varStatus"><c:out value="${line}"/><c:if test="${!varStatus.last}"><br/></c:if></c:forTokens>