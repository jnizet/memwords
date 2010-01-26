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
<title><fmt:message key="preferences.changePreferredLocale.title"/></title>
<script type="text/javascript">
  $(document).ready(function () {
    $("#cancelButton").click(function() {
      window.location = url("/preferences/Preferences.action");
    });
    $("#locale").focus();
  });
</script>
</head>
<body>
  <h1><fmt:message key="preferences.changePreferredLocale.h1"/></h1>
  <p><fmt:message key="preferences.changePreferredLocale.fillFormMessage"/></p>
  <stripes:form beanclass="com.googlecode.memwords.web.preferences.ChangePreferredLocaleActionBean" 
                method="post"
                id="changePreferredLocaleForm">
    <table>
      <tr>
        <th><fmt:message key="preferences.changePreferredLocale.localeLabel"/></th>
        <td>
          <stripes:select name="locale">
            <stripes:option value=""><fmt:message key="preferences.changePreferredLocale.noLocaleOption"/></stripes:option>
            <stripes:options-collection collection="${actionBean.supportedLocales}"
                                        value="locale"
                                        label="displayedName"
                                        sort="label"/>
          </stripes:select>  
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <stripes:submit name="change"><fmt:message key="preferences.changePreferredLocale.submitButton"/></stripes:submit>
          <stripes:submit name="cancel" id="cancelButton"><fmt:message key="preferences.changePreferredLocale.cancelButton"/></stripes:submit>
        </td>
      </tr>
    </table>
  </stripes:form>
</body>
</html>