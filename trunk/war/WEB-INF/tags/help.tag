<%@ tag isELIgnored="false"  %>
<%@ attribute name="key" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="id" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<img <c:if test="${!empty id}">id="${id}" </c:if>src="<c:url value="/img/help.png"/>" class="icon help" title="<fmt:message key="${key}"/>"/>