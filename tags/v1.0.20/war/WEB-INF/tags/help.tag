<%@ tag isELIgnored="false"  %>
<%@ attribute name="key" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="hidden" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<img <tags:hideIf test="${hidden}"/><c:if test="${!empty id}">id="${id}" </c:if>src="<c:url value="/img/help.png"/>" class="icon help" alt="<fmt:message key="${key}"/>" title="<fmt:message key="${key}"/>"/>