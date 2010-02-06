<p><fmt:message key="preferences._changePasswordGenerationPreferences.fillFormMessage"/></p>
<stripes:form beanclass="com.googlecode.memwords.web.preferences.ChangePasswordGenerationPreferencesActionBean" 
              method="post"
              id="changePasswordGenerationPreferencesForm">
  <table>
    <tr>
      <th><fmt:message key="preferences._changePasswordGenerationPreferences.lengthLabel"/></th>
      <td colspan="2">
        <stripes:select name="length" id="length">
          <c:forEach var="length" begin="4" end="20">
            <stripes:option value="${length}">${length}</stripes:option>
          </c:forEach>
        </stripes:select>
      </td>
    </tr>
    <tr>
      <th><fmt:message key="preferences._changePasswordGenerationPreferences.includeLabel"/></th>
      <td>
        <stripes:checkbox name="lowerCaseLettersIncluded" id="lowerCaseLettersIncluded" value="true"/>
        <label for="lowerCaseLettersIncluded"><fmt:message key="preferences._changePasswordGenerationPreferences.lowerCaseLetters"/></label>
      </td>
      <td>
        <stripes:checkbox name="upperCaseLettersIncluded" id="upperCaseLettersIncluded" value="true"/>
        <label for="upperCaseLettersIncluded"><fmt:message key="preferences._changePasswordGenerationPreferences.upperCaseLetters"/></label>
      </td>
    </tr>
    <tr>
      <td></td>
      <td>
        <stripes:checkbox name="digitsIncluded" id="digitsIncluded" value="true"/>
        <label for="digitsIncluded"><fmt:message key="preferences._changePasswordGenerationPreferences.digits"/></label>
      </td>
      <td>
        <stripes:checkbox name="specialCharactersIncluded" id="specialCharactersIncluded" value="true"/>
        <label for="specialCharactersIncluded"><fmt:message key="preferences._changePasswordGenerationPreferences.specialCharacters"/></label>
      </td>
    </tr>
    <tr style="display: none;" id="strengthSection">
      <th><fmt:message key="preferences._changePasswordGenerationPreferences.passwordStrengthLabel"/></th>
      <td colspan="2">
        <div id="strength" class="strength"></div>
      </td>
    </tr>
    <tr>
      <td colspan="3">
        <stripes:submit name="change" id="submitButton"><fmt:message key="preferences._changePasswordGenerationPreferences.submitButton"/></stripes:submit>
        <stripes:submit name="cancel" id="cancelButton"><fmt:message key="preferences._changePasswordGenerationPreferences.cancelButton"/></stripes:submit>
      </td>
    </tr>
  </table>
</stripes:form>