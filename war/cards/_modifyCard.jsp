<%@ taglib prefix="stripes-d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<div id="cardDetails">
	<script type=text/javascript>
		<%@ include file="_editCardJs.jsp" %>
	    $("document").ready(function() {
	      var form = $("#modifyCardForm")
	      changeFormEvent(form, "modifyCard", "ajaxModifyCard");
	      ajaxifyForm(form);
	    });
	</script>
	<stripes:form id="modifyCardForm"
	              beanclass="com.googlecode.memwords.web.cards.ModifyCardActionBean">
	<stripes:hidden name="cardId"/>
	<table>
	  <%@ include file="_editCardRows.jsp" %>
	  <tr>
	    <td colspan="2">
	      <stripes:submit name="modifyCard" value="Modify card" id="submitButton"/>
	      <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean"/>
	      <stripes:submit name="cancel" value="Cancel" onclick="return closeCardDetails();"/>
	    </td>
	  </tr>
	</table>
	</stripes:form>
  </div>
</body>
