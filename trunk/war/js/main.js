function initMessages() {
	$("#messagesContainer").hide();
	$("#messages").hide();
	$("#messagesControl").hide();
	
	var messagesHtml = jQuery.trim($("#messages").html());
	
	if (messagesHtml.length > 0) {
		$("#messagesContainer").show();
		$("#messagesControl").show();
		showMessagePanel();
	}
}

function showMessagePanel() {
	$("#showMessagesLink").hide();
	$("#hideMessagesLink").show();
	$("#messages").hide();
	$("#messages").slideDown("normal");
}

function hideMessagePanel() {
	$("#showMessagesLink").show();
	$("#hideMessagesLink").hide();
	$("#messages").slideUp("normal");
}

function changeFormEvent(form, oldEvent, newEvent) {
	$("input[name='" + oldEvent + "']", form).attr("name", newEvent);
}

function ajaxifyForm(form) {
	form.ajaxForm(
		{ success: function(responseText) {htmlMultiple(responseText, true)} });
}

function loadMultiple(url) {
	$.ajax({
	  url: url,
	  success : function(responseText){
	      htmlMultiple(responseText, true);
	  }
	});
}

function htmlMultiple(responseText, initMessagesAfterUpdate) {
	var dom = $(responseText);
	dom.each(function(index) { 
	    	if ($(this).attr("id") != null) {
	    	    $("#" + $(this).attr("id")).html($(this).html())
	    	}
	    });
	if (initMessagesAfterUpdate == null || initMessagesAfterUpdate) {
		initMessages();
	}
}

function url(u) {
	if (u.length > 0 && u.charAt(0) == '/') {
		u = u.substring(1, u.length);
	}
	return baseUrl + u;
}

function createCard() {
	loadMultiple(url("/cards/CreateCard.action?ajaxView="));
    return false;
}

function displayCard(cardId) {
	loadMultiple(url("/cards/DisplayCard.action?ajaxView=&cardId="+ cardId));
    return false;
}

function deleteCard(cardId) {
	loadMultiple(url("/cards/DeleteCard.action?ajaxView=&cardId="+ cardId));
    return false;
}

function closeCardDetails() {
	loadMultiple(url("/cards/Cards.action?ajaxCancel="));
    return false;
}

function loadUrlIcon(cardUrl, iconUrlHiddenField, iconSpan, defaultIconSpan, loadingSpan, submitButton) {
    submitButton.attr("disabled", true);
	iconUrlHiddenField.val("");
    iconSpan.hide();
    defaultIconSpan.hide();
    setLoading(loadingSpan, true);
    
	$.ajax({ url: url("/cards/CreateCard.action"),
             data: {url : cardUrl,
		            ajaxGetIcon : ""},
	         success: function(responseText) {
           	   $("#messages").html($(responseText).filter("#messages").html());
               var iconUrl = $.trim($(responseText).filter("#body").html());
               if (iconUrl.length > 0) {
                   iconSpan.children("img").attr('src', iconUrl);
                   iconSpan.show();
           	       iconUrlHiddenField.val(iconUrl);
               }
               else {
            	   defaultIconSpan.show();
               }
               setLoading(loadingSpan, false);
               submitButton.attr("disabled", false);
               initMessages();
		     }
	       });
}

function setLoading(span, loading) {
	if (loading) {
		span.html('<img src="' + url("/img/busy.gif") + '" class="loading" alt="Loading..."/>');
	}
	else {
		span.html('');
	}
}

function isUrlValid(u) {
	return (u.indexOf("http://") == 0) || (u.indexOf("https://") == 0);
}

$(document).ready(function () {
    initMessages();
    $("#body").ajaxError(function(event, xmlHttpRequest, ajaxOptions, thrownError) {
		if (xmlHttpRequest.status == 403) {
			window.location = url("/account/Login.action");
		}
		else {
    	    $(this).html(xmlHttpRequest.responseText);
		}
	});
});

$.blockUI.defaults.overlayCSS.opacity = 0.3;
$.blockUI.defaults.overlayCSS.cursor = 'default';
