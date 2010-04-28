<%@ taglib prefix="stripes-d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<div id="cardDetails">
  <script type="text/javascript">
    $("document").ready(function() {
      m.cards.initModifyCard("${actionBean.javaScriptEscapedPassword}",
                             ${actionBean.passwordGenerationPreferences.length},
                             ${actionBean.passwordGenerationPreferences.lowerCaseLettersIncluded},
                             ${actionBean.passwordGenerationPreferences.upperCaseLettersIncluded},
                             ${actionBean.passwordGenerationPreferences.digitsIncluded},
                             ${actionBean.passwordGenerationPreferences.specialCharactersIncluded});
    });
  </script>
  <h2><fmt:message key="cards._modifyCard.h2"/></h2>
  <tags:formNotice/>
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
