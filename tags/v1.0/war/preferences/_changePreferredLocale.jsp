<p><fmt:message key="preferences._changePreferredLocale.fillFormMessage"/></p>
<stripes:form beanclass="com.googlecode.memwords.web.preferences.ChangePreferredLocaleActionBean" 
              method="post"
              id="changePreferredLocaleForm">
  <table>
    <tr>
      <th><fmt:message key="preferences._changePreferredLocale.localeLabel"/></th>
      <td>
        <stripes:select name="locale" id="locale">
          <stripes:option value=""><fmt:message key="preferences._changePreferredLocale.noLocaleOption"/></stripes:option>
          <stripes:options-collection collection="${actionBean.supportedLocales}"
                                      value="locale"
                                      label="displayedName"
                                      sort="label"/>
        </stripes:select>  
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <stripes:submit name="change"><fmt:message key="preferences._changePreferredLocale.submitButton"/></stripes:submit>
        <stripes:submit name="cancel" id="cancelButton"><fmt:message key="preferences._changePreferredLocale.cancelButton"/></stripes:submit>
      </td>
    </tr>
  </table>
</stripes:form>