<%@ tag isELIgnored="false"  %>
<%@ attribute name="cards" required="true" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<div id="cards">
  <script type="text/javascript">
    $("document").ready(function() {
      var cardsList = [<c:forEach var="card" items="${cards}">"${card.id}", </c:forEach>];
      bindCardsListEvents(cardsList);
    });
  </script>
  <c:forEach var="card" items="${cards}" varStatus="varStatus">
    <c:if test="${varStatus.index mod 2 == 0}">
      <div class="spacer">&nbsp;</div>
    </c:if>
    <div class="card">
      <a href="<stripes:url beanclass="com.googlecode.memwords.web.cards.DisplayCardActionBean">
                 <stripes:param name="cardId" value="${card.id}"/>
               </stripes:url>" id="displayCardLink_${card.id}" title="<fmt:message key="tags.cardsList.displayCardLinkTitle"/>">
        <tags:cardIcon card="${card}"/>
        <c:out value="${card.name}"/>
      </a>
      <div class="cardLinks">
        <fmt:message key="tags.cardsList.modifyCardLinkTitle" var="modifyCardLinkTitle"/>
        <fmt:message key="tags.cardsList.modifyCardAltPattern" var="modifyCardAlt">
          <fmt:param value="${fn:escapeXml(card.name)}"/>
        </fmt:message>
        <a href="<stripes:url beanclass="com.googlecode.memwords.web.cards.ModifyCardActionBean">
                     <stripes:param name="cardId" value="${card.id}"/>
                 </stripes:url>" id="modifyCardLink_${card.id}" title="${modifyCardLinkTitle}">
          <img src="<c:url value="/img/modify.png"/>" width="16" height="16" alt="${modifyCardAlt}"/>
        </a>
        <fmt:message key="tags.cardsList.deleteCardLinkTitle" var="deleteCardLinkTitle"/>
        <fmt:message key="tags.cardsList.deleteCardAltPattern" var="deleteCardAlt">
          <fmt:param value="${fn:escapeXml(card.name)}"/>
        </fmt:message>
        <a href="<stripes:url beanclass="com.googlecode.memwords.web.cards.DeleteCardActionBean">
                     <stripes:param name="cardId" value="${card.id}"/>
                 </stripes:url>" id="deleteCardLink_${card.id}" title="${deleteCardLinkTitle}" >
          <img src="<c:url value="/img/delete.png"/>" width="16" height="16" alt="${deleteCardAlt}"/>
        </a>
      </div>
    </div>
  </c:forEach>
</div>
