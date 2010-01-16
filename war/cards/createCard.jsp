<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Create a card</title>
<script type=text/javascript>
  function loadUrlIcon() {
    var url = $("#urlTextField").val();
    if (url.length > 0) {
        var loadUrlButton = $("#loadIconUrlButton");
        var iconUrlTextField = $("#iconUrlTextField");
        var iconUrlLoading = $("#iconUrlLoading");
        
        loadUrlButton.attr("disabled", true);
        loadUrlButton.attr("disabled", true);
        iconUrlLoading.show();
        
    	$.ajax({ url: '<stripes:url beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean" event="ajaxGetIcon"/>',
                 data: {url : url},
		         success: function(html) {
               	   loadUrlButton.attr("disabled", false);
                   loadUrlButton.attr("disabled", false);
                   iconUrlLoading.hide();
                   $("#iconUrlTextField").val($.trim(html));
		           displayIconUrl();
    		      }
    	       });
    }
  }

  function displayIconUrl() {
	  var url = $.trim($("#iconUrlTextField").val());
	  if (url.length > 0) {
		  $("#iconUrlSpan").html('<img src="' + url + '" width="16" height="16" alt="' + url + '"/>');
	  }
	  else {
        $("#iconUrlSpan").html("");
	  }
  }
</script>
</head>
<body>
    <h1>Create a card</h1>
    <tags:cardsList cards="${actionBean.cards}" readOnly="${true}"/>
    <div id="cardDetails">
        <stripes:form id="createCardForm"
                      beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean">
		    <table>
		      <tr>
		        <th>Name :</th>
		        <td><stripes:text name="name"/></td>
		      </tr>
		      <tr>
                <th>URL :</th>
                <td>
                  <stripes:text name="url" id="urlTextField"/>
                  <stripes:button id="loadIconUrlButton" name="getIcon" value="Get icon" onclick="loadUrlIcon();"/>
                  <span id="iconUrlLoading" style="display: none;"><img src="<c:url value="/img/busy.gif"/>" alt="Loading..." width="16" height="16"/></span>
                </td>
              </tr>
              <tr>
                <th>Icon URL:</th>
                <td>
                  <stripes:text name="iconUrl" id="iconUrlTextField" onblur="displayIconUrl();"/>
                  <span id="iconUrlSpan"></span>
                </td>
              </tr>
              <tr>
		        <td colspan="2">
		          <stripes:submit name="createCard" value="Create card"/>
		          <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean"/>
                  <stripes:submit name="cancel" value="Cancel" onclick="window.location = '${cardsUrl}'; return false;"/>
                </td>
		      </tr>
		    </table>
		</stripes:form>
    </div>
</body>
</html>