<%@ taglib prefix="stripes-d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<div id="cardDetails">
  <script type=text/javascript>
    <%@ include file="_editCardJs.jsp" %>
    $("document").ready(function() {
      var form = $("#modifyCardForm")
      changeFormEvent(form, "modifyCard", "ajaxModifyCard");
      ajaxifyForm(form);
      $("#cancelButton").click(function() {
        return closeCardDetails();
      });
    });
  </script>
  <stripes:form id="modifyCardForm"
                beanclass="com.googlecode.memwords.web.cards.ModifyCardActionBean">
    <stripes:hidden name="cardId"/>
    <table>
      <%@ include file="_editCardRows.jsp" %>
      <tr>
        <td colspan="2">
          <stripes:submit name="modifyCard" id="submitButton"><fmt:message key="cards._modifyCard.submitButton"/></stripes:submit>
          <stripes:submit name="cancel" id="cancelButton"><fmt:message key="cards._modifyCard.cancelButton"/></stripes:submit>
        </td>
      </tr>
    </table>
  </stripes:form>
</div>
