<div id="cardDetails">
  <script type="text/javascript">
    $("document").ready(function() {
      var form = $("#deleteCardForm")
      changeFormEvent(form, "deleteCard", "ajaxDeleteCard");
      ajaxifyForm(form);
      $("#cancelButton").click(function() {
        return closeCardDetails();
      });
    });
  </script>

  <h2><fmt:message key="cards._deleteCard.h2"/></h2>
  <c:set var="card" value="${actionBean.card}"/>
  <p>
    <fmt:message key="cards._deleteCard.deleteConfirmationMessagePattern">
      <fmt:param value="${card.name}"/>
    </fmt:message>
  </p>
  <stripes:form beanclass="com.googlecode.memwords.web.cards.DeleteCardActionBean" id="deleteCardForm">
    <stripes:hidden name="cardId"/>
    <stripes:submit name="deleteCard"><fmt:message key="cards._deleteCard.deleteButton"/></stripes:submit>
    <stripes:submit name="cancel" id="cancelButton"><fmt:message key="cards._deleteCard.cancelButton"/></stripes:submit>
  </stripes:form>
</div>