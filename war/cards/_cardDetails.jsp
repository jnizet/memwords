<div id="cardDetails">
  <c:set var="card" value="${actionBean.card}" />
  <script type="text/javascript">
    $(document).ready(function() {
      $("#unmaskPasswordLink").show();
      $("#unmaskPasswordLink").click(function() {
        return unmaskPassword();
      }); 
      $("#maskPasswordLink").click(function() {
        return maskPassword();
      });
      $("#cancelButton").click(function() {
        return closeCardDetails();
      });
      $("#createCardLink").click(function() {
        return createCard();
      });
      $("#modifyCardLink").click(function() {
        return modifyCard("${card.id}");
      });
      $("#deleteCardLink").click(function() {
        return deleteCard("${card.id}");
      });
      // global var used in mask/unmask
      maskedPasswordTitle = $("#passwordDiv").attr("title");
    });
  </script> 
  <h2><tags:cardIcon card="${card}" /> <c:out value="${card.name}" /></h2>
  <table>
    <tr>
      <th><fmt:message key="cards._cardDetails.loginLabel"/></th>
      <td><c:out value="${card.login}" /></td>
    </tr>
    <tr>
      <th><fmt:message key="cards._cardDetails.passwordLabel"/></th>
      <td>
      <div style="float: left;" id="passwordDiv" class="masked" title="<fmt:message key="cards._cardDetails.maskedPasswordTitle"/>"><c:out value="${card.password}" /></div>
      <a href="#" style="display: none;" id="unmaskPasswordLink"><fmt:message key="cards._cardDetails.unmaskLink"/></a>
      <a href="#" style="display: none;" id="maskPasswordLink"><fmt:message key="cards._cardDetails.maskLink"/></a>
      </td>
    </tr>
    <tr>
      <th><fmt:message key="cards._cardDetails.urlLabel"/></th>
      <td>
        <c:if test="${!empty card.url}">
          <a class="external" target="_blank" href="<c:out value="${card.url}"/>"><c:out value="${card.url}" /></a>
        </c:if>
      </td>
    </tr>
    <tr>
      <th style="vertical-align: top;"><fmt:message key="cards._cardDetails.noteLabel"/></th>
      <td><tags:nlToBr value="${card.note}"/></td>
    </tr>
    <tr>
      <td colspan="2">
        <stripes:form beanclass="com.googlecode.memwords.web.cards.DisplayCardActionBean"
          id="cardDetailsForm">
          <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean" />
          <stripes:submit name="cancel" id="cancelButton"><fmt:message key="cards._cardDetails.closeButton"/></stripes:submit>
        </stripes:form>
      </td>
    </tr>
  </table>
  <div id="cardDetailsLinks">
    <div style="float: left;">
      <stripes:link beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean" 
                    id="createCardLink"><img src="<c:url value="/img/add.png"/>" class="cardIcon" alt="<fmt:message key="cards._cardDetails.createCardLink"/>" /><fmt:message key="cards._cardDetails.createCardLink"/></stripes:link>
    </div>
    <div style="float: right;">
      <fmt:message key="cards._cardDetails.modifyCardLinkTitle" var="modifyCardLinkTitle"/>
      <stripes:link beanclass="com.googlecode.memwords.web.cards.ModifyCardActionBean"
                    id="modifyCardLink" 
                    title="${modifyCardLinkTitle}"><img src="<c:url value="/img/modify.png"/>" class="cardIcon" alt="${modifyCardLinkTitle}" />
        <stripes:param name="cardId" value="${card.id}" />
      </stripes:link>
      <fmt:message key="cards._cardDetails.deleteCardLinkTitle" var="deleteCardLinkTitle"/>
      <stripes:link beanclass="com.googlecode.memwords.web.cards.DeleteCardActionBean"
                    id="deleteCardLink" 
                    title="${deleteCardLinkTitle}"><img src="<c:url value="/img/delete.png"/>" class="cardIcon" alt="${deleteCardLinkTitle}" />
        <stripes:param name="cardId" value="${card.id}" />
      </stripes:link>
    </div>
  </div>
</div>