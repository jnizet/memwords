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

$(document).ready(function () {
    initMessages();
});

$.blockUI.defaults.overlayCSS.opacity = 0.3;
$.blockUI.defaults.overlayCSS.cursor = 'default';