<%@ taglib prefix="stripes-d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<div id="cardDetails">
  <script type=text/javascript>
    <%@ include file="_editCardJs.jsp" %>
    $("document").ready(function() {
      var form = $("#createCardForm")
      changeFormEvent(form, "createCard", "ajaxCreateCard");
      ajaxifyForm(form);
    });
  </script>
  <stripes:form id="createCardForm"
                beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean">
    <table>
      <%@ include file="_editCardRows.jsp" %>
      <tr>
        <td colspan="2">
          <stripes:submit name="createCard" value="Create card" id="submitButton"/>
          <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean"/>
          <stripes:submit name="cancel" value="Cancel" onclick="return closeCardDetails();"/>
        </td>
      </tr>
    </table>
  </stripes:form>
</div>
