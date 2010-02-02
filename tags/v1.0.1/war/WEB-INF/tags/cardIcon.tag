<%@ tag isELIgnored="false"  %>
<%@ attribute name="card" required="true" rtexprvalue="true" type="java.lang.Object"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="iconUrl" value="/img/card.png"/>
<c:if test="${!empty card.iconUrl}">
  <c:set var="iconUrl" value="${card.iconUrl}"/>
</c:if>
<img src="<c:out value="${iconUrl}"/>" class="cardIcon" alt=""/>