<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<script type="text/javascript">
  $("document").ready(function() {
      bindGeneratePasswordFormEvents();
  });
</script>
<stripes:form partial="true" beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean">
<table>
  <tr>
    <td colspan="2">
      <fmt:message key="cards.ajaxGeneratePassword.length"/>
      <stripes:select name="passwordGenerationPreferences.length" id="passwordLength">
        <c:forEach var="length" begin="4" end="20">
          <stripes:option value="${length}">${length}</stripes:option>
        </c:forEach>
      </stripes:select>
    </td>
  </tr>
  <tr>
    <td>
      <stripes:checkbox name="passwordGenerationPreferences.lowerCaseLettersIncluded" id="lowerCaseLettersIncluded"/>
      <label for="lowerCaseLettersIncluded"><fmt:message key="cards.ajaxGeneratePassword.lowerCaseLetters"/></label>
    </td>
    <td>
      <stripes:checkbox name="passwordGenerationPreferences.upperCaseLettersIncluded" id="upperCaseLettersIncluded"/>
      <label for="upperCaseLettersIncluded"><fmt:message key="cards.ajaxGeneratePassword.upperCaseLetters"/></label>
    </td>
  </tr>
  <tr>
    <td>
      <stripes:checkbox name="passwordGenerationPreferences.digitsIncluded" id="digitsIncluded"/>
      <label for="digitsIncluded"><fmt:message key="cards.ajaxGeneratePassword.digits"/></label></td>
    <td>
      <stripes:checkbox name="passwordGenerationPreferences.specialCharactersIncluded" id="specialCharactersIncluded"/>
      <label for="specialCharactersIncluded"><fmt:message key="cards.ajaxGeneratePassword.specialCharacters"/></label>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <button id="generatePasswordButton"><fmt:message key="cards.ajaxGeneratePassword.generateButton"/></button>
      <button id="cancelPasswordGenerationButton"><fmt:message key="cards.ajaxGeneratePassword.cancelButton"/></button>
    </td>
  </tr>
</table>
</stripes:form>