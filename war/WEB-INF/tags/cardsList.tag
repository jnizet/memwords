<%@ tag isELIgnored="false"  %>
<%@ attribute name="cards" required="true" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<div id="cards">
    <c:forEach var="card" items="${cards}" varStatus="varStatus">
        <c:if test="${varStatus.index mod 2 == 0}">
            <div class="spacer">&nbsp;</div>
        </c:if>
        <div class="card">
	        <a href="<stripes:url beanclass="com.googlecode.memwords.web.cards.DisplayCardActionBean">
	                                             <stripes:param name="cardId" value="${card.id}"/>
	                                           </stripes:url>" onclick="return displayCard('${card.id}');" title="Display card details">
	          <tags:cardIcon  card="${card}"/>
              <c:out value="${card.name}"/>
	        </a>
	        <div class="cardLinks">
	            <a href="<stripes:url beanclass="com.googlecode.memwords.web.cards.ModifyCardActionBean">
                                                       <stripes:param name="cardId" value="${card.id}"/>
                                                   </stripes:url>" onclick="return modifyCard('${card.id}');" title="Modify card">
                  <img src="<c:url value="/img/modify.png"/>" width="16" height="16" alt="Modify card <c:out value='${card.name}'/>"/>
                </a>
                <a href="<stripes:url beanclass="com.googlecode.memwords.web.cards.DeleteCardActionBean">
	                                                   <stripes:param name="cardId" value="${card.id}"/>
	                                               </stripes:url>" onclick="return deleteCard('${card.id}');" title="Delete card" >
	              <img src="<c:url value="/img/delete.png"/>" width="16" height="16" alt="Delete card <c:out value='${card.name}'/>"/>
	            </a>
	        </div>
	    </div>
    </c:forEach>
</div>
