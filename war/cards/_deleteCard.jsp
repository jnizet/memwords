<div id="cardDetails">
  <script type=text/javascript>
    $("document").ready(function() {
      var form = $("#deleteCardForm")
      changeFormEvent(form, "deleteCard", "ajaxDeleteCard");
      ajaxifyForm(form);
    });
  </script>

  <c:set var="card" value="${actionBean.card}"/>
  <p>Do you really want to delete the following card?</p>
  <h2>
    <tags:cardIcon card="${card}"/>
    <c:out value="${card.name}"/>
  </h2>
  <table>
    <tr>
      <th>URL :</th>
      <td>
        <c:if test="${!empty card.url}">
          <a class="out" href="<c:out value="${card.url}"/>"><c:out value="${card.url}"/></a>
        </c:if>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <stripes:form beanclass="com.googlecode.memwords.web.cards.DeleteCardActionBean" id="deleteCardForm">
          <stripes:hidden name="cardId"/>
          <stripes:submit name="deleteCard" value="Yes, Delete"/>
          <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean"/>
          <stripes:submit name="cancel" value="No, Cancel" onclick="return closeCardDetails();"/>
        </stripes:form>
      </td>
    </tr>
  </table>
</div>