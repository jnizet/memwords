<%@ taglib prefix="stripes-d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<div id="cardDetails">
  <script type="text/javascript">
    <%@ include file="_editCardJs.jsp" %>
    $("document").ready(function() {
      var form = $("#createCardForm")
      changeFormEvent(form, "createCard", "ajaxCreateCard");
      ajaxifyForm(form);
      $("#cancelButton").click(function() {
        return closeCardDetails();
      });
      $("#nameInput").focus();
    });
  </script>
  <h2><fmt:message key="cards._createCard.h2"/></h2>
  <tags:formNotice/>
  <stripes:form id="createCardForm"
                beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean">
    <table>
      <%@ include file="_editCardRows.jsp" %>
      <tr>
        <td colspan="2">
          <stripes:submit name="createCard" id="submitButton"><fmt:message key="cards._createCard.submitButton"/></stripes:submit>
          <stripes:submit name="cancel" id="cancelButton"><fmt:message key="cards._createCard.cancelButton"/></stripes:submit>
        </td>
      </tr>
    </table>
  </stripes:form>
</div>
