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
<table>
  <tr>
    <td colspan="2">
      <fmt:message key="cards.ajaxGeneratePassword.length"/>
      <select name="passwordLength" id="passwordLength">
        <c:forEach var="length" begin="4" end="20">
          <option value="${length}" <c:if test="${length == actionBean.passwordGenerationPreferences.length}">selected="selected"</c:if>>${length}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td><input type="checkbox" name="includeLowerCaseLetters" id="includeLowerCaseLetters" <c:if test="${actionBean.passwordGenerationPreferences.lowerCaseLettersIncluded}">checked="checked"</c:if>/><label for="includeLowerCaseLetters"><fmt:message key="cards.ajaxGeneratePassword.lowerCaseLetters"/></label></td>
    <td><input type="checkbox" name="includeUpperCaseLetters" id="includeUpperCaseLetters" <c:if test="${actionBean.passwordGenerationPreferences.upperCaseLettersIncluded}">checked="checked"</c:if>/><label for="includeUpperCaseLetters"><fmt:message key="cards.ajaxGeneratePassword.upperCaseLetters"/></label></td>
  </tr>
  <tr>
    <td><input type="checkbox" name="includeDigits" id="includeDigits" <c:if test="${actionBean.passwordGenerationPreferences.digitsIncluded}">checked="checked"</c:if>/><label for="includeDigits"><fmt:message key="cards.ajaxGeneratePassword.digits"/></label></td>
    <td><input type="checkbox" name="includeSpecial" id="includeSpecial" <c:if test="${actionBean.passwordGenerationPreferences.specialCharactersIncluded}">checked="checked"</c:if>/><label for="includeSpecial"><fmt:message key="cards.ajaxGeneratePassword.specialCharacters"/></label></td>
  </tr>
  <tr>
    <td colspan="2">
      <button id="generatePasswordButton"><fmt:message key="cards.ajaxGeneratePassword.generateButton"/></button>
      <button id="cancelPasswordGenerationButton"><fmt:message key="cards.ajaxGeneratePassword.cancelButton"/></button>
    </td>
  </tr>
</table>