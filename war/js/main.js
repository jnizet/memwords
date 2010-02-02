/**
 * Initializes the message panel and its control links (visible or hidden depending on
 * the presence of messages)
 */
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

/**
 * Shows the message panel with a sliding effect, and adapts the visibility of the message
 * panel control links
 * @return <code>false</code>
 */
function showMessagePanel() {
    $("#showMessagesLink").hide();
    $("#hideMessagesLink").show();
    $("#messages").hide();
    $("#messages").slideDown();
    return false;
}

/**
 * Hides the message panel with a sliding effect, and adapts the visibility of the message
 * panel control links
 * @return <code>false</code>
 */
function hideMessagePanel() {
    $("#showMessagesLink").show();
    $("#hideMessagesLink").hide();
    $("#messages").slideUp();
    return false;
}

/**
 * Hides and clears the message panel with a sliding effect
 * @return <code>false</code>
 */
function clearMessagePanel() {
    $("#messages").slideUp(function() {
        $("#messages").html("");
        initMessages();
    });
    return false;
}

/**
 * changes the event of a form, in order to ajaxify it afterwards. An input with a 
 * name equal to oldEvent is searched in the form, and its name is replaced by
 * newEvent
 * @param form the form (JQuery object)
 * @param oldEvent the name of the input field which must be changed
 * @param newEvent the new name of the input field
 */
function changeFormEvent(form, oldEvent, newEvent) {
    $("input[name='" + oldEvent + "']", form).attr("name", newEvent);
}

/**
 * ajaxifies a form, so that its submission results in an ajax call. When the ajax call
 * succeeds, <code>htmlMultiple</code> is called with the response
 * @param form the form (JQuery object)
 */
function ajaxifyForm(form) {
    form.ajaxForm(
        { success: function(responseText) {htmlMultiple(responseText, true)}
        });
}

/**
 * Executes an ajax call to the given URL, and calls <code>htmlMultiple</code> with the
 * response when the call succeeds
 * @param url the URL to call
 */
function loadMultiple(url) {
    $.ajax({
      url: url,
      success : function(responseText){
          htmlMultiple(responseText, true);
      }
    });
}

/**
 * iterates through each child element of the root element (typically a body element)
 * of <code>responseText</code> and, if the child element has an ID, replaces the content
 * of the element of the current document which has this ID with the content of the child
 * element in the response. This allows updating multiple HTML elements at once.
 * @param responseText the response of an ajax call
 * @param initMessagesAfterUpdate if <code>true</code> or <code>null</code>, then initializes
 * the message panel after the multiple update, as if the page had been completely reloaded
 */
function htmlMultiple(responseText, initMessagesAfterUpdate) {
    var dom = $(responseText);
    dom.each(function(index) { 
        var idAttr = $(this).attr("id");
        if (idAttr != null && idAttr.length > 0) {
            $("#" + idAttr).html($(this).html())
        }
    });
    if (initMessagesAfterUpdate == null || initMessagesAfterUpdate) {
        initMessages();
    }
}

/**
 * Transforms an URL relative to the context path of the webapp (example : /cards/Cards.action)
 * into an URL relative to the root of the web server. This has in fact no effect in GAE, since 
 * the context path is also the root of the web server
 * @param u the URL to transform
 * @return the transformed URL
 */
function url(u) {
    var result = baseUrl;
    if (u.charAt(0) != '/') {
        result = result + "/";
    }
    result = result + u;
    return result;
}

/**
 * Takes a span, and replaces its content with a loading icon (if <code>loading</code> is 
 * <code>true</code> or with an empty string (if <code>loading</code> is <code>false</code>)
 * @param span the span to adapt
 * @param loading <code>true</code> if a loading icon must be displayed in the span, <code>false</code>
 * if it must be erased
 */
function setLoading(span, loading) {
    if (loading) {
        var loadingMessage = $("div#loading img").attr("title");
        span.html('<img src="' + url("/img/busy.gif") + '" class="loading" alt="" title="' + loadingMessage + '"/>');
    }
    else {
        span.html('');
    }
}

/*
 * Installs the handler for error responses from Ajax calls (403 errors make the page
 * redirected to the login page, and other errors are displayed in the body section
 * of the page.
 * Also installs the click handlers for the message panel control links.
 */
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
    $("#loading").ajaxStart(function() {
        $(this).show();
    });
    $("#loading").ajaxStop(function() {
        $(this).hide();
    });
    $(window).unload(function() {
        $("#loading").show();
    });

    $("#hideMessagesLink").click(function() {
        return hideMessagePanel();
    });
    $("#showMessagesLink").click(function() {
        return showMessagePanel();
    });
});

