<p><fmt:message key="preferences._changePasswordPreferences.fillFormMessage"/></p>
<stripes:form beanclass="com.googlecode.memwords.web.preferences.ChangePasswordPreferencesActionBean" 
              method="post"
              id="changePasswordPreferencesForm">
  <table>
    <tr>
      <th class="top"><fmt:message key="preferences._changePasswordPreferences.displayTypeLabel"/></th>
      <td>
        <stripes:radio value="false" name="unmasked" id="masked"/><label for="masked"><fmt:message 
          key="preferences._changePasswordPreferences.masked"/></label>
        <br/>
        <stripes:radio value="true" name="unmasked" id="unmasked"/><label for="unmasked"><fmt:message 
          key="preferences._changePasswordPreferences.unmasked"/></label>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <stripes:submit name="change"><fmt:message 
          key="preferences._changePasswordPreferences.submitButton"/></stripes:submit>
        <stripes:submit name="cancel" id="cancelButton"><fmt:message 
          key="preferences._changePasswordPreferences.cancelButton"/></stripes:submit>
      </td>
    </tr>
  </table>
</stripes:form>