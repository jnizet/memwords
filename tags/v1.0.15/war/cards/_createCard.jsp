<%@ taglib prefix="stripes-d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<div id="cardDetails">
  <script type="text/javascript">
    $("document").ready(function() {
        m.cards.initCreateCard("${actionBean.javaScriptEscapedPassword}",
                               ${actionBean.passwordGenerationPreferences.length},
                               ${actionBean.passwordGenerationPreferences.lowerCaseLettersIncluded},
                               ${actionBean.passwordGenerationPreferences.upperCaseLettersIncluded},
                               ${actionBean.passwordGenerationPreferences.digitsIncluded},
                               ${actionBean.passwordGenerationPreferences.specialCharactersIncluded});
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
