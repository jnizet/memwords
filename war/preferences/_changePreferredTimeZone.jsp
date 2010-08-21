<p><fmt:message key="preferences._changePreferredTimeZone.fillFormMessage"/></p>
<stripes:form beanclass="com.googlecode.memwords.web.preferences.ChangePreferredTimeZoneActionBean" 
              method="post"
              id="changePreferredTimeZoneForm">
  <table>
    <tr>
      <th><label for="timeZone"><fmt:message key="preferences._changePreferredTimeZone.timeZoneLabel"/></label></th>
      <td>
        <stripes:select name="timeZoneId" id="timeZone">
          <stripes:options-collection collection="${actionBean.timeZones}"
                                      value="ID"
                                      label="ID"
                                      sort="label"/>
        </stripes:select>  
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <stripes:submit name="change"><fmt:message 
        key="preferences._changePreferredTimeZone.submitButton"/></stripes:submit>
        <stripes:submit name="cancel" id="cancelButton"><fmt:message 
        key="preferences._changePreferredTimeZone.cancelButton"/></stripes:submit>
      </td>
    </tr>
  </table>
</stripes:form>