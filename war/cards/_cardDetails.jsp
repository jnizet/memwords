<div id="cardDetails">
  <script type="text/javascript">
    $(document).ready(function() {
      $("#unmaskPasswordLink").show();
        $("#unmaskPasswordLink").click(function() {
          $("#passwordDiv").attr("class", "unmasked");
          $("#maskPasswordLink").show();
          $("#unmaskPasswordLink").hide();
          return false;
        }); 
        $("#maskPasswordLink").click(function() {
          $("#passwordDiv").attr("class", "masked");
          $("#unmaskPasswordLink").show();
          $("#maskPasswordLink").hide();
          return false;
        }); 
    });
  </script> <c:set var="card" value="${actionBean.card}" />
  <h2><tags:cardIcon card="${card}" /> <c:out value="${card.name}" /></h2>
  <table>
    <tr>
      <th>Login / User ID :</th>
      <td><c:out value="${card.login}" /></td>
    </tr>
    <tr>
      <th>Password :</th>
      <td>
      <div style="float: left;" id="passwordDiv" class="masked"><c:out value="${card.password}" /></div>
      <a href="#" style="display: none;" id="unmaskPasswordLink">Unmask</a>
      <a href="#" style="display: none;" id="maskPasswordLink">Mask</a></td>
    </tr>
    <tr>
      <th>URL :</th>
      <td><c:if test="${!empty card.url}">
          <a class="external" target="_blank" href="<c:out value="${card.url}"/>"><c:out value="${card.url}" /></a>
        </c:if>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <stripes:form beanclass="com.googlecode.memwords.web.cards.DisplayCardActionBean"
          id="cardDetailsForm">
          <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean" />
          <stripes:submit name="cancel" value="Close" onclick="return closeCardDetails();" />
        </stripes:form>
      </td>
    </tr>
  </table>
  <div id="cardDetailsLinks">
    <div style="float: left;">
      <stripes:link beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean" 
                    onclick="return createCard();"><img src="<c:url value="/img/add.png"/>" class="cardIcon" alt="Create a new card" />Create a new card</stripes:link>
    </div>
    <div style="float: right">
      <stripes:link beanclass="com.googlecode.memwords.web.cards.ModifyCardActionBean"
                    onclick="return modifyCard('${card.id}');" title="Modify this card"><img src="<c:url value="/img/modify.png"/>" class="cardIcon" alt="Modify this card" />
        <stripes:param name="cardId" value="${card.id}" />
      </stripes:link>
      <stripes:link beanclass="com.googlecode.memwords.web.cards.DeleteCardActionBean"
                    onclick="return deleteCard('${card.id}');" title="Delete this card"><img src="<c:url value="/img/delete.png"/>" class="cardIcon" alt="Delete this card" />
        <stripes:param name="cardId" value="${card.id}" />
      </stripes:link>
    </div>
  </div>
</div>