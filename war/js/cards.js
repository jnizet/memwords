/**
 * displays the create card form in the card details section, using ajax
 * @return false
 */
function createCard() {
    loadMultiple(url("/cards/CreateCard.action?ajaxView="));
    return false;
}

/**
 * displays the card details in the card details section, using ajax
 * @return false
 */
function displayCard(cardId) {
    loadMultiple(url("/cards/DisplayCard.action?ajaxView=&cardId=" + cardId));
    return false;
}

/**
 * displays the modify card form in the card details section, using ajax
 * @return false
 */
function modifyCard(cardId) {
    loadMultiple(url("/cards/ModifyCard.action?ajaxView=&cardId=" + cardId));
    return false;
}

/**
 * displays the delete card form in the card details section, using ajax
 * @return false
 */
function deleteCard(cardId) {
    loadMultiple(url("/cards/DeleteCard.action?ajaxView=&cardId=" + cardId));
    return false;
}

/**
 * unmasks the password, hides the unmask link, and shows the mask link.
 * Used in the card details and delete card pages.
 * @return false
 */
function unmaskPassword() {
    $("#passwordDiv").attr("class", "unmasked");
    $("#passwordHelp").hide();
    $("#maskPasswordLink").show();
    $("#unmaskPasswordLink").hide();
    return false;
} 

/**
 * masks a password, hides the mask link, and shows the unmask link.
 * Used in the card details and delete card pages.
 * @return false
 */
function maskPassword() {
    $("#passwordDiv").attr("class", "masked");
    $("#passwordHelp").show();
    $("#unmaskPasswordLink").show();
    $("#maskPasswordLink").hide();
    return false;
} 

/**
 * displays the big create cards link in the card details section, using ajax
 * @return
 */
function closeCardDetails() {
    loadMultiple(url("/cards/Cards.action?ajaxCancel="));
    return false;
}

/**
 * Loads the icon associated to the URL entered in the URL text field of the create or modify
 * card form. A loading icon is displayed during the operation, and the submit button is
 * disabled. The hidden field is also updated by this function.
 */
function loadCardIcon() {
    var cardUrl = $("#urlTextField").val();
    var absolutizedCardUrl = absolutizeUrl($("#urlTextField").val());
    if (isUrlValid(absolutizedCardUrl)) {
        $("#iconUrlHiddenField").val("");
        $("#iconUrlSpan").hide();
        $("#defaultIconUrlSpan").hide();
        setLoading($("#iconUrlLoadingSpan"), true);
        
        $.ajax({ url: url("/cards/CreateCard.action"),
                 data: {url : cardUrl,
                        ajaxGetIcon : ""},
                 success: function(responseText) {
                     $("#messages").html($(responseText).filter("#messages").html());
                     var iconUrl = $.trim($(responseText).filter("#body").html());
                     if (iconUrl.length > 0) {
                         $("#iconUrlSpan").children("img").attr('src', iconUrl);
                         $("#iconUrlSpan").show();
                         $("#iconUrlHiddenField").val(iconUrl);
                     }
                     else {
                         $("#defaultIconUrlSpan").show();
                     }
                     setLoading($("#iconUrlLoadingSpan"), false);
                     $("#iconUrlFetchedHiddenField").val("true");
                     initMessages();
                 }
               });
    } 
    else {
        $("#iconUrlHiddenField").val("");
        $("#iconUrlSpan").hide();
        $("#defaultIconUrlSpan").show();
    }
}

/**
 * Changes the visibility of the Test URL link depending on the value entered in
 * the URL text field of the create or modify card form. The link is displayed if the 
 * URL is valid
 */
function changeTestUrlVisibility() {
    var absolutizedCardUrl = absolutizeUrl($("#urlTextField").val());
    if (absolutizedCardUrl != null) {
        $("#testUrlLink").show();
    } 
    else {
        $("#testUrlLink").hide();
    }
}

/**
 * Tests if an URL is valid (starts with http:// or https://)
 * @param u the URL to test
 * @return <code>true</code> if valid, <code>false</code> otherwise
 */
function isUrlValid(u) {
    return (u != null) && (u.indexOf("http://") == 0) || (u.indexOf("https://") == 0);
}

/**
 * Make a URL absolute, as defined by the method com.googlecode.memwords.domain.UrlUtils.absolutizeUrl()
 */
function absolutizeUrl(u) {
    var s = $.trim(u);
    if (s.length == 0) {
        return null;
    }
    if (s.indexOf("://") >= 0) {
        return s;
    }
    return "http://" + s;
}

/**
 * Binds the click events of the links found in the cards list
 * @param cardIds the IDs of the cards in the list
 */
function bindCardsListEvents(cardIds) {
    for (var i = 0; i < cardIds.length; i++) {
        $("#displayCardLink_" + cardIds[i]).bind("click", {cardId: cardIds[i]}, function(event) {
            return displayCard(event.data.cardId);
        });
        $("#modifyCardLink_" + cardIds[i]).bind("click", {cardId: cardIds[i]}, function(event) {
            return modifyCard(event.data.cardId);
        });
        $("#deleteCardLink_" + cardIds[i]).bind("click", {cardId: cardIds[i]}, function(event) {
            return deleteCard(event.data.cardId);
        });
    }
}

/**
 * Binds the events on the generate password form
 */
function bindGeneratePasswordFormEvents() {
    var generatePasswordEnabledHandler = function() {
        var enabled = ($("#includeLowerCaseLetters").attr("checked")
                       || $("#includeUpperCaseLetters").attr("checked")
                       || $("#includeDigits").attr("checked") 
                       || $("#includeSpecial").attr("checked"));
        $("#generatePasswordButton").attr("disabled", !enabled);
    }
    $("#includeLowerCaseLetters").change(generatePasswordEnabledHandler);
    $("#includeUpperCaseLetters").change(generatePasswordEnabledHandler);
    $("#includeDigits").change(generatePasswordEnabledHandler);
    $("#includeSpecial").change(generatePasswordEnabledHandler);

    $("#generatePasswordButton").click(
        function() {
            var generatedPassword = 
                generatePassword($("#passwordLength").val(), 
                                 $("#includeLowerCaseLetters").attr("checked"),
                                 $("#includeUpperCaseLetters").attr("checked"), 
                                 $("#includeDigits").attr("checked"), 
                                 $("#includeSpecial").attr("checked"));
            $("#password").val(generatedPassword);
            displayPasswordStrength(generatedPassword, $("#strength"), "inline-block");
            $("#generatePasswordDiv").slideUp(function() {
                $("#generatePasswordDiv").html("");
            });
            return false;
        });
    $("#cancelPasswordGenerationButton").click(function() {
        $("#generatePasswordDiv").slideUp(function() {
            $("#generatePasswordDiv").html("");
        });
        return false; 
    });
}