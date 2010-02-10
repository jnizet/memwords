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
 * Binds the events for the create and modify card sections
 * @param modification true if it's a modification, false if it's a creation
 * @param password the current password, which must populate the password field
 * @param preferredPasswordLength the preferred password length to use when generating a password
 * @param includeLowerCaseLetters whether to include lower-case letters in generated passwords
 * @param includeUpperCaseLetters whether to include upper-case letters in generated passwords
 * @param includeDigits whether to include digits in generated passwords
 * @param includeSpecial whether to include special characters in generated passwords
 */
function bindEditCardEvents(modification, 
                            password,
                            preferredPasswordLength, 
                            includeLowerCaseLetters, 
                            includeUpperCaseLetters,
                            includeDigits,
                            includeSpecial) {
    $("#urlTextField").change(function() {
        changeTestUrlVisibility(); 
        loadCardIcon();
    });
    $("#testUrlLink").click(function() {
        window.open(absolutizeUrl($("#urlTextField").val()));
        return false;
      });
    // in IE, password fields are not auto-populated. We populate is with JavaScript
    $("#password").val(password);
      
    $("#password").keyup(function() {
        displayPasswordStrength($("#password").val(), $("#strength"), "inline-block");
    });
    displayPasswordStrength($("#password").val(), $("#strength"), "inline-block");
      
    $("#generatePasswordLink").click(function() {
        var generatedPassword = 
            generatePassword(preferredPasswordLength,
                             includeLowerCaseLetters,
                             includeUpperCaseLetters,
                             includeDigits,
                             includeSpecial);
        $("#password").val(generatedPassword);
        displayPasswordStrength(generatedPassword, $("#strength"), "inline-block");
        return false;
    });
    $("#generatePasswordWithOptionsLink").click(function() {
        $("#generatePasswordDiv").load(url("/cards/CreateCard.action?ajaxGetPasswordGenerationForm"),
                                       function() {
                                           $("#generatePasswordDiv").slideDown();
                                       });
    });
    $("#generatePasswordLinksDiv").show();
    
    $("#cancelButton").click(function() {
        return closeCardDetails();
    });
    
    if (modification) {
        // since JavaScript is activated, the password field is populated, and there is 
        // no need to display the change password div anymore. We check the checkbox and hide the div
        $("#changePasswordCheckBox").attr("checked", true);
        $("#changePasswordSpan").hide();
    }
}

/**
 * Binds the events on the generate password form
 */
function bindGeneratePasswordFormEvents() {
    var generatePasswordEnabledHandler = function() {
        var enabled = ($("#lowerCaseLettersIncluded").attr("checked")
                       || $("#upperCaseLettersIncluded").attr("checked")
                       || $("#digitsIncluded").attr("checked") 
                       || $("#specialCharactersIncluded").attr("checked"));
        $("#generatePasswordButton").attr("disabled", !enabled);
    }
    $("#lowerCaseLettersIncluded").change(generatePasswordEnabledHandler);
    $("#upperCaseLettersIncluded").change(generatePasswordEnabledHandler);
    $("#digitsIncluded").change(generatePasswordEnabledHandler);
    $("#specialCharactersIncluded").change(generatePasswordEnabledHandler);

    $("#generatePasswordButton").click(
        function() {
            var generatedPassword = 
                generatePassword($("#passwordLength").val(), 
                                 $("#lowerCaseLettersIncluded").attr("checked"),
                                 $("#upperCaseLettersIncluded").attr("checked"), 
                                 $("#digitsIncluded").attr("checked"), 
                                 $("#specialCharactersIncluded").attr("checked"));
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