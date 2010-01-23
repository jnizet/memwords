<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Create an account</title>
<stripes:url beanclass="com.googlecode.memwords.web.account.CreateAccountActionBean" 
             event="ajaxGetUserIdAvailability"
             var="userIdAvailabilityUrl"/>
<script type="text/javascript">
  function loadUserIdAvailability() {
    var userId = jQuery.trim($("#userId").val());
    if (userId.length == 0) {
      $("#userIdAvailability").html("");
    }
    else {
      setLoading($("#userIdAvailability"));
      $.ajax({
        url: '${userIdAvailabilityUrl}',
        data: {userId : userId},
        success: function(html) {
          $("#userIdAvailability").html(html);
        }
      });
    }
  }
  $(document).ready(function () {
    $("#userId").bind("blur", function () {
        loadUserIdAvailability();
    });
  });
</script>
</head>
<body>
  <h1>Create an account</h1>

  <p>Please fill the following form in order to create an account.</p>
  <stripes:form beanclass="com.googlecode.memwords.web.account.CreateAccountActionBean" 
                id="createAccountForm">
    <table>
      <tr>
        <th>User ID :</th>
        <td><stripes:text name="userId" id="userId" /><span id="userIdAvailability"></span></td>
      </tr>
      <tr>
        <th>Master password :</th>
        <td><stripes:password name="masterPassword" /></td>
      </tr>
      <tr>
        <th>Master password confirmation :</th>
        <td><stripes:password name="masterPassword2" /></td>
      </tr>
      <tr>
        <td colspan="2"><stripes:submit name="createAccount" value="Create Account" /></td>
      </tr>
    </table>
  </stripes:form>
</body>
</html>