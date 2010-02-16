/*jslint browser: true */
/*global $: true, window: true */
var memwords = function () {
// private methods
/**
 * Counts the number of chars from password which are in the given chars
 */
function countContain(password, chars) {
    var count = 0;
    for (var i = 0; i < password.length; i++) {
        if (chars.indexOf(password.charAt(i)) >= 0) {
            count++;
        }
    }
    return count; 
} 

/**
 * Generates a random integer between 0 (inclusive) and limit (exclusive)
 * @param limit the limit of the random integer
 * @return the generated integer
 */
function randomInt(limit) {
    return Math.floor(Math.random() * limit);
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
 * Computes the strength (0 to 100) of a password
 * @param password the password
 * @return the strength (from 0 to 100)
 */
function computePasswordStrength(password) {
    if (!password) {
        password = "";
    }
    var upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    var lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    var digits = "0123456789";

    var score = 0;
    if (password.length > 4 && password.length < 8) {
        score += (10 - (7 - password.length));
    }
    else if (password.length >= 8 && password.length < 15) {
        score += (25 - (15 - password.length));
    }
    else if (password.length >= 15) {
        score += 25;
    }

    var upperCount = countContain(password, upperCaseLetters);
    var lowerCount = countContain(password, lowerCaseLetters);
    var letterCount = upperCount + lowerCount;
    if (upperCount === 0 && lowerCount !== 0) {
        score += 10;
    }
    else if (upperCount !== 0 && lowerCount === 0) {
        score += 10;
    }
    else if (upperCount !== 0 && lowerCount !== 0) {
        score += 20;
    }

    var digitCount = countContain(password, digits);
    if (digitCount > 1 && digitCount < 2) {
        score += 10;
    }
    else if (digitCount >= 2) {
        score += 20;
    }

    var specialCharCount = password.length - (digitCount + letterCount);
    if (specialCharCount == 1) {
        score += 10;
    }
    else if (specialCharCount > 1) {
        score += 25;
    }

    if (digitCount !== 0 && letterCount !== 0) {
        score += 2;
    }
    if (digitCount !== 0 && letterCount !== 0 && specialCharCount !== 0) {
        score += 3;
    }
    if (digitCount !== 0 && upperCount !== 0 && lowerCount !== 0 && specialCharCount !== 0) {
        score += 5;
    }

    return score;
}

var m = {};

// properties
m.config = {
    baseUrl : ""
};
// public methods
/**
 * Initializes the message panel and its control links (visible or hidden depending on
 * the presence of messages)
 */
m.initMessages = function() {
    $("#messagesContainer").hide();
    $("#messages").hide();
    $("#messagesControl").hide();
    
    var messagesHtml = $.trim($("#messages").html());
    
    if (messagesHtml.length > 0) {
        $("#messagesContainer").show();
        $("#messagesControl").show();
        showMessagePanel();
    }
};

/**
 * Hides and clears the message panel with a sliding effect
 * @return <code>false</code>
 */
m.clearMessagePanel = function() {
    $("#messages").slideUp(function() {
        $("#messages").html("");
        m.initMessages();
    });
    return false;
};

/**
 * changes the event of a form, in order to ajaxify it afterwards. An input with a 
 * name equal to oldEvent is searched in the form, and its name is replaced by
 * newEvent
 * @param form the form (JQuery object)
 * @param oldEvent the name of the input field which must be changed
 * @param newEvent the new name of the input field
 */
m.changeFormEvent = function(form, oldEvent, newEvent) {
    $("input[name='" + oldEvent + "']", form).attr("name", newEvent);
};

/**
 * iterates through each child element of the root element (typically a body element)
 * of <code>responseText</code> and, if the child element has an ID, replaces the content
 * of the element of the current document which has this ID with the content of the child
 * element in the response. This allows updating multiple HTML elements at once.
 * @param responseText the response of an ajax call
 * @param initMessagesAfterUpdate if <code>true</code> or <code>undefined</code>, then initializes
 * the message panel after the multiple update, as if the page had been completely reloaded
 */
m.htmlMultiple = function (responseText, initMessagesAfterUpdate) {
    var dom = $(responseText);
    dom.each(function(index) { 
        var idAttr = $(this).attr("id");
        if (idAttr && idAttr.length > 0) {
            $("#" + idAttr).html($(this).html());
        }
    });
    if (initMessagesAfterUpdate === undefined || initMessagesAfterUpdate) {
        m.initMessages();
    }
};

/**
 * Executes an ajax call to the given URL, and calls <code>htmlMultiple</code> with the
 * response when the call succeeds
 * @param url the URL to call
 * @param callback an optional callback function called after everything is loaded
 */
m.loadMultiple = function(url, callback) {
    $.ajax({
      url: url,
      success : function(responseText){
          m.htmlMultiple(responseText, true);
          if (callback) {
              callback.call();
          }
      }
    });
};

/**
 * ajaxifies a form, so that its submission results in an ajax call. When the ajax call
 * succeeds, <code>htmlMultiple</code> is called with the response
 * @param form the form (JQuery object)
 */
m.ajaxifyForm = function(form) {
    form.ajaxForm(
        { success: function(responseText) {m.htmlMultiple(responseText, true);}
        });
};

/**
 * Transforms an URL relative to the context path of the webapp (example : /cards/Cards.action)
 * into an URL relative to the root of the web server. This has in fact no effect in GAE, since 
 * the context path is also the root of the web server
 * @param u the URL to transform
 * @return the transformed URL
 */
m.url = function(u) {
    var result = m.config.baseUrl;
    if (u.charAt(0) != '/') {
        result = result + "/";
    }
    result = result + u;
    return result;
};

/**
 * Takes a span, and replaces its content with a loading icon (if <code>loading</code> is 
 * <code>true</code> or with an empty string (if <code>loading</code> is <code>false</code>)
 * @param span the span to adapt
 * @param loading <code>true</code> if a loading icon must be displayed in the span, <code>false</code>
 * if it must be erased
 */
m.setLoading = function(span, loading) {
    if (loading) {
        var loadingMessage = $("div#loading img").attr("title");
        span.html('<img src="' + m.url("/img/busy.gif") + '" class="loading" alt="" title="' + loadingMessage + '"/>');
    }
    else {
        span.html('');
    }
};

/**
 * Computes the strength of the given password, initialized the bar div if not done yet,
 * and animates the background color of the bar div and changes the value of the score contained in 
 * the bar div to reflect the new score.
 * By default, the display of the div is set to block. Use the display parameter to set it to another value.
 */
m.displayPasswordStrength = function(password, barDiv, display) {
    if ($.trim(barDiv.html()).length === 0) {
        barDiv.html('<div class="bar"><div></div></div><div class="score"></div>');
    }
    if (!display) {
        display = "block";
    }
    barDiv.css("display", display);
    var score = computePasswordStrength(password);
    if (score > 100) {
        score = 100;
    }

    var color = "#ffffff";

    if (score >= 90) {
        color = "#3bce08";
    }
    else if (score >= 80) {
        color = "#7ff67c";
    }
    else if (score >= 70) {
        color = "#c6ff63";
    }
    else if (score >= 60) {
        color = "#ffff00";
    }
    else if (score >= 50) {
        color = "orange";
    }
    else if (score >= 25) {
        color = "#ff2c2c";
    }
    else if (score > 0) {
        color = "red";
    }

    var backgroundDiv = $("div.bar div", barDiv);
    var scoreDiv = $("div.score", barDiv);
    backgroundDiv.animate( {
        width : score,
        backgroundColor : color
    }, 
    "fast",
    function() {
        scoreDiv.html(score + "&nbsp;%");
    });
    return false;
};

/**
 * Generates a semi-random password. The algorithm tries to include two symbols of each kind,
 * starting with the ones with the biggest population. Then the symbols are chosen randomly
 * across the whole population. Finally, the result is shuffled.
 * @param size the size of the password
 * @param includeLowerCaseLetters indicates if the password may contain lower case letters
 * @param includeUpperCaseLetters indicates if the password may contain uppercase case letters
 * @param includeDigits indicates if the password may contain digits
 * @param includeSpecial indicates if the password may contain special characters
 * @return the generated password
 */
m.generatePassword = function(size, 
                              includeLowerCaseLetters, 
                              includeUpperCaseLetters, 
                              includeDigits, 
                              includeSpecial) {
    var lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    var upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    var digits = "0123456789";
    var special = "&\"#'{([-|_\\@)]=+}$%*!:/;.,?<>";
    
    var blocks = [];
    if (includeLowerCaseLetters) {
        blocks.push(lowerCaseLetters);
    }
    if (includeUpperCaseLetters) {
        blocks.push(upperCaseLetters);
    }
    if (includeSpecial) {
        blocks.push(special);
    }
    if (includeDigits) {
        blocks.push(digits);
    }
    
    if (blocks.length === 0) {
        return "";
    }
    
    var chars = "";
    var i = 0;
    while (i < size && i < blocks.length * 2) {
       var block = blocks[i % blocks.length];
       chars += block.charAt(randomInt(block.length));
       i++;
    }
    var all = "";
    for (var j = 0; j < blocks.length; j++) {
        all += blocks[j];
    }
    while (i < size) {
        chars += all.charAt(randomInt(all.length));
        i++;
    }
    var result = "";
    for (i = 0; i < size; i++) {
        var index = randomInt(chars.length);
        result += chars.charAt(index);
        chars = chars.substring(0, index) + chars.substring(index + 1);
    }
    return result;
};

/*
 * Installs the handler for error responses from Ajax calls (403 errors make the page
 * redirected to the login page, and other errors are displayed in the body section
 * of the page.
 * Also installs the click handlers for the message panel control links.
 */
m.install = function() {
    $(document).ready(function () {
        m.initMessages();
        $("#body").ajaxError(function(event, xmlHttpRequest, ajaxOptions, thrownError) {
            if (xmlHttpRequest.status == 403) {
                window.location = memwords.url("/account/Login.action");
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
        
        function adjustSideBarHeight() {
            var bodyHeight = $("body").outerHeight();
            var viewportHeight = $(window).height();
            if (bodyHeight < viewportHeight) {
                var sideBar = $("#sidebar");
                var newMinHeight = (sideBar.height() + (viewportHeight - bodyHeight)) + "px";
                sideBar.css("min-height", newMinHeight);
            }
        };
        adjustSideBarHeight();
        $(window).resize(adjustSideBarHeight);
    });
};

return m;
}();
var m = memwords;