<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<body>
  <tags:messages/>
  <div id="passwordGenerationPreferencesDiv">
    <script type="text/javascript">
      $(document).ready(function () {
        bindChangePasswordGenerationPreferencesEvents();
      });
    </script>
    <%@ include file="_changePasswordGenerationPreferences.jsp" %>
  </div>
</body>
