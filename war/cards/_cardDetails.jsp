<div id="cardDetails">
  <c:set var="card" value="${actionBean.card}" />
  <script type="text/javascript">
    $(document).ready(function() {
      m.cards.initCardDetails(${actionBean.passwordsUnmasked}, "${card.id}");
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
      <c:set var="passwordClass" value="masked"/>
      <c:if test="${actionBean.passwordsUnmasked}"><c:set var="passwordClass" value="unmasked"/></c:if>
      <div style="float: left;" id="passwordDiv" class="${passwordClass}"><c:out value="${card.password}" /></div>
      <tags:help key="cards._cardDetails.maskedPasswordHelp" 
                 id="passwordHelp"
                 hidden="${actionBean.passwordsUnmasked}"/>
      <a href="#" style="display: none;" id="unmaskPasswordLink"><fmt:message 
        key="cards._cardDetails.unmaskLink"/></a>
      <a href="#" style="display: none;" id="maskPasswordLink"><fmt:message 
        key="cards._cardDetails.maskLink"/></a>
      </td>
    </tr>
    <tr>
      <th><fmt:message key="cards._cardDetails.urlLabel"/></th>
      <td>
        <c:choose>
          <c:when test="${!empty card.absolutizedUrl}">
            <a class="external" target="_blank" href="<c:out value="${card.absolutizedUrl}"/>"><c:out 
              value="${card.url}"/></a>
          </c:when>
          <c:otherwise>
            <c:out value="${card.url}"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <th class="top"><fmt:message key="cards._cardDetails.noteLabel"/></th>
      <td><tags:nlToBr value="${card.note}"/></td>
    </tr>
    <tr>
      <td colspan="2">
        <stripes:form beanclass="com.googlecode.memwords.web.cards.DisplayCardActionBean"
                      id="cardDetailsForm">
          <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean" />
          <stripes:submit name="cancel" id="cancelButton"><fmt:message 
            key="cards._cardDetails.closeButton"/></stripes:submit>
        </stripes:form>
      </td>
    </tr>
  </table>
  <div id="cardDetailsLinks">
      <stripes:link beanclass="com.googlecode.memwords.web.cards.ModifyCardActionBean"
                    id="modifyCardLink"
                    class="icon modifyCard"><fmt:message key="cards._cardDetails.modifyCardLink"/>
        <stripes:param name="cardId" value="${card.id}" />
      </stripes:link>
      <br/>
      <stripes:link beanclass="com.googlecode.memwords.web.cards.DeleteCardActionBean"
                    id="deleteCardLink"
                    class="icon deleteCard"><fmt:message key="cards._cardDetails.deleteCardLink"/>
        <stripes:param name="cardId" value="${card.id}" />
      </stripes:link>
      <br/>
      <stripes:link beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean" 
                    id="createCardLink"
                    class="icon createCard"><fmt:message key="cards._cardDetails.createCardLink"/></stripes:link>
  </div>
</div>