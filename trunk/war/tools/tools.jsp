<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><fmt:message key="tools.tools.title"/></title>
<script type="text/javascript" src="<c:url value="/js/tools.min.js"/>"></script>
<script type="text/javascript">
  $("document").ready(function() {
    m.tools.initTools(${actionBean.passwordsUnmasked});
  });
</script>
</head>
<body>
  <h1><fmt:message key="tools.tools.h1"/></h1>
  <div id="noScript">
    <fmt:message key="tools.tools.noScript"/>
  </div>
  <div id="tools" style="display: none;">
    <h2><fmt:message key="tools.tools.evalPasswordStrengthH2" /></h2>
    <p>
      <fmt:message key="tools.tools.evalPasswordStrengthExplanation"/>
    </p>
    <form id="evalPasswordStrengthForm" action="#">
      <table>
        <tr>
          <th><label for="passwordToEval"><fmt:message key="tools.tools.passwordToEvalLabel"/></label></th>
          <td>
            <input type="password" id="passwordToEval" autocomplete="off"/>
            <div id="passwordToEvalStrength" class="strength"></div>
          </td>
        </tr>
      </table>
    </form>
    
    <h2><fmt:message key="tools.tools.generatePasswordH2" /></h2>
    <p><fmt:message key="tools.tools.generatePasswordExplanation"/></p>
    <stripes:form beanclass="com.googlecode.memwords.web.tools.ToolsActionBean" id="generatePasswordForm">
      <table>
        <tr>
          <th><label for="length"><fmt:message key="tools.tools.lengthLabel"/></label></th>
          <td colspan="2">
            <stripes:select name="passwordGenerationPreferences.length" id="length">
              <c:forEach var="length" begin="4" end="20">
                <stripes:option value="${length}">${length}</stripes:option>
              </c:forEach>
            </stripes:select>
          </td>
        </tr>
        <tr>
          <th><fmt:message key="tools.tools.includeLabel"/></th>
          <td>
            <stripes:checkbox name="passwordGenerationPreferences.lowerCaseLettersIncluded" id="lowerCaseLettersIncluded"/>
            <label for="lowerCaseLettersIncluded"><fmt:message key="tools.tools.lowerCaseLetters"/></label>
          </td>
          <td>
            <stripes:checkbox name="passwordGenerationPreferences.upperCaseLettersIncluded" id="upperCaseLettersIncluded"/>
            <label for="upperCaseLettersIncluded"><fmt:message key="tools.tools.upperCaseLetters"/></label>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <stripes:checkbox name="passwordGenerationPreferences.digitsIncluded" id="digitsIncluded"/>
            <label for="digitsIncluded"><fmt:message key="tools.tools.digits"/></label>
          </td>
          <td>
            <stripes:checkbox name="passwordGenerationPreferences.specialCharactersIncluded" id="specialCharactersIncluded"/>
            <label for="specialCharactersIncluded"><fmt:message key="tools.tools.specialCharacters"/></label>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <button id="generatePasswordButton"><fmt:message key="tools.tools.generatePasswordButton"/></button>
          </td>
        </tr>
      </table>
    </stripes:form>
    <div id="generatedPasswordDiv" style="display: none;">
      <c:set var="passwordClass" value="masked"/>
      <c:if test="${actionBean.passwordsUnmasked}"><c:set var="passwordClass" value="unmasked"/></c:if>
      <table>
        <tr>
          <th><fmt:message key="tools.tools.generatedPasswordLabel"/></th>
          <td>
            <div style="float: left;" id="generatedPassword" class="${passwordClass}"></div>
            <tags:help key="tools.tools.maskedPasswordHelp" 
                       id="maskedPasswordHelp"
                       hidden="${actionBean.passwordsUnmasked}"/>
            <a href="#" style="display: none;" id="unmaskPasswordLink"><fmt:message key="tools.tools.unmaskLink"/></a>
            <a href="#" style="display: none;" id="maskPasswordLink"><fmt:message key="tools.tools.maskLink"/></a>
          </td>
        </tr>
        <tr>
          <th><fmt:message key="tools.tools.generatedPasswordStrengthLabel"/></th>
          <td><div id="generatedPasswordStrength" class="strength"></div></td>
        </tr>
      </table>
    </div>
  </div>
</body>
</html>