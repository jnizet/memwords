<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ tag isELIgnored="false"  %>
<%@ attribute name="cards" required="true" type="java.util.List" %>
<%@ attribute name="readOnly" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<c:if test="${readOnly == null}">
  <c:set var="readOnly" value="${true}"/>
</c:if>

<div id="cards">
    <c:forEach var="card" items="${cards}" varStatus="varStatus">
        <c:if test="${varStatus.index mod 2 == 0}">
            <div class="spacer">&nbsp;</div>
        </c:if>
        <div class="card">
	        <c:if test="${!readOnly}"><a href="<stripes:url beanclass="com.googlecode.memwords.web.cards.DisplayCardActionBean">
	                                             <stripes:param name="cardId" value="${card.id}"/>
	                                           </stripes:url>"></c:if>
	          <c:out value="${card.name}"/>
	        <c:if test="${!readOnly}"></a></c:if>
	        <div class="cardLinks">
	            <c:if test="${!readOnly}"><a href="<stripes:url beanclass="com.googlecode.memwords.web.cards.DeleteCardActionBean">
	                                                   <stripes:param name="cardId" value="${card.id}"/>
	                                               </stripes:url>"></c:if>
	              <img src="<c:url value="/img/delete.png"/>" width="16" height="16" alt="Delete card <c:out value='${card.name}'/>" title="Delete card" />
	            <c:if test="${!readOnly}"></a></c:if>
	        </div>
	    </div>
    </c:forEach>
</div>

<c:if test="${readOnly}">
<script type="text/javascript">
  $("document").ready(function() {
    $("#cards").block({ message: null });
  });
</script>
</c:if>