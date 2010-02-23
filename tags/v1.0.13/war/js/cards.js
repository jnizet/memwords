/*jslint browser: true */
/*global memwords: true, $: true, window: true */
(function(m) {
// private functions
/**
 * Make a URL absolute, as defined by the method com.googlecode.memwords.domain.UrlUtils.absolutizeUrl()
 */
function absolutizeUrl(u) {
    var s = $.trim(u);
    if (s.length === 0) {
        return null;
    }
    if (s.indexOf("://") >= 0) {
        return s;
    }
    return "http://" + s;
}

/**
 * Tests if an URL is valid (starts with http:// or https://)
 * @param u the URL to test
 * @return <code>true</code> if valid, <code>false</code> otherwise
 */
function isUrlValid(u) {
    return u && 
           (((u.indexOf("http://") === 0) && (u.length > 7)) || 
            ((u.indexOf("https://") === 0) && (u.length > 8)));
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
        m.setLoading($("#iconUrlLoadingSpan"), true);
        
        $.ajax({ url: m.url("/cards/CreateCard.action"),
                 data: {url : cardUrl,
                        ajaxGetIcon : ""},
                 success: function(responseText) {
                     $("#messages").html($(responseText).filter("#messages").html());
                     var iconUrl = $.trim($(responseText).filter("#body").html());
                     if (iconUrl.length > 0) {
                         $("#iconUrlSpan img").attr('src', iconUrl);
                         $("#iconUrlSpan").show();
                         $("#iconUrlHiddenField").val(iconUrl);
                     }
                     else {
                         $("#defaultIconUrlSpan").show();
                     }
                     m.setLoading($("#iconUrlLoadingSpan"), false);
                     $("#iconUrlFetchedHiddenField").val("true");
                     m.initMessages();
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
 * @return false
 */
function closeCardDetails() {
    m.loadMultiple(m.url("/cards/Cards.action?ajaxCancel="));
    return false;
}

/**
 * Changes the visibility of the Test URL link depending on the value entered in
 * the URL text field of the create or modify card form. The link is displayed if the 
 * URL is valid
 */
function changeTestUrlVisibility() {
    var absolutizedCardUrl = absolutizeUrl($("#urlTextField").val());
    if (absolutizedCardUrl !== null && isUrlValid(absolutizedCardUrl)) {
        $("#testUrlLink").show();
    } 
    else {
        $("#testUrlLink").hide();
    }
}

/**
 * displays the create card form in the card details section, using ajax
 * @return false
 */
function createCard() {
    m.loadMultiple(m.url("/cards/CreateCard.action?ajaxView="));
    return false;
}

/**
 * displays the delete card form in the card details section, using ajax
 * @return false
 */
function deleteCard(cardId) {
    m.loadMultiple(m.url("/cards/DeleteCard.action?ajaxView=&cardId=" + cardId));
    return false;
}

/**
 * displays the card details in the card details section, using ajax
 * @return false
 */
function displayCard(cardId) {
    m.loadMultiple(m.url("/cards/DisplayCard.action?ajaxView=&cardId=" + cardId));
    return false;
}

/**
 * displays the modify card form in the card details section, using ajax
 * @return false
 */
function modifyCard(cardId) {
    m.loadMultiple(m.url("/cards/ModifyCard.action?ajaxView=&cardId=" + cardId));
    return false;
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
function initEditCards(modification, 
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
        m.displayPasswordStrength($("#password").val(), $("#strength"), "inline-block");
    });
    m.displayPasswordStrength($("#password").val(), $("#strength"), "inline-block");
      
    $("#generatePasswordLink").click(function() {
        var generatedPassword = 
            m.generatePassword(preferredPasswordLength,
                               includeLowerCaseLetters,
                               includeUpperCaseLetters,
                               includeDigits,
                               includeSpecial);
        $("#password").val(generatedPassword);
        m.displayPasswordStrength(generatedPassword, $("#strength"), "inline-block");
        return false;
    });
    $("#generatePasswordWithOptionsLink").click(function() {
        $("#generatePasswordDiv").load(m.url("/cards/CreateCard.action?ajaxGetPasswordGenerationForm"),
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

var c = {};

/**
 * Binds the click events of the links found in the cards list
 * @param cardIds the IDs of the cards in the list
 */
c.initCardsList = function(cardIds) {
    function displayHandler(event) {
        return displayCard(event.data.cardId);
    }
    function modifyHandler(event) {
        return modifyCard(event.data.cardId);
    }
    function deleteHandler(event) {
        return deleteCard(event.data.cardId);
    }
    for (var i = 0; i < cardIds.length; i++) {
        var data = {cardId: cardIds[i]};
        $("#displayCardLink_" + cardIds[i]).bind("click", data, displayHandler);
        $("#modifyCardLink_" + cardIds[i]).bind("click", data, modifyHandler);
        $("#deleteCardLink_" + cardIds[i]).bind("click", data, deleteHandler);
    }
};

/**
 * Binds the events for the empty card details section
 */
c.initEmptyCardDetails = function() {
    $("#createCardLink").click(function() {
        return createCard();
    });
};

/**
 * Inits the create card section
 * @param password the current password, which must populate the password field
 * @param preferredPasswordLength the preferred password length to use when generating a password
 * @param includeLowerCaseLetters whether to include lower-case letters in generated passwords
 * @param includeUpperCaseLetters whether to include upper-case letters in generated passwords
 * @param includeDigits whether to include digits in generated passwords
 * @param includeSpecial whether to include special characters in generated passwords
 */
c.initCreateCard = function(password,
                            preferredPasswordLength, 
                            includeLowerCaseLetters, 
                            includeUpperCaseLetters,
                            includeDigits,
                            includeSpecial) {
    initEditCards(false, 
                  password,
                  preferredPasswordLength, 
                  includeLowerCaseLetters, 
                  includeUpperCaseLetters,
                  includeDigits,
                  includeSpecial);
    var form = $("#createCardForm");
    m.changeFormEvent(form, "createCard", "ajaxCreateCard");
    m.ajaxifyForm(form);
    $("#nameInput").focus();
};

/**
 * Inits the modify card section
 * @param password the current password, which must populate the password field
 * @param preferredPasswordLength the preferred password length to use when generating a password
 * @param includeLowerCaseLetters whether to include lower-case letters in generated passwords
 * @param includeUpperCaseLetters whether to include upper-case letters in generated passwords
 * @param includeDigits whether to include digits in generated passwords
 * @param includeSpecial whether to include special characters in generated passwords
 */
c.initModifyCard = function(password, 
                            preferredPasswordLength, 
                            includeLowerCaseLetters, 
                            includeUpperCaseLetters,
                            includeDigits,
                            includeSpecial) {
    initEditCards(true, 
                  password,
                  preferredPasswordLength, 
                  includeLowerCaseLetters, 
                  includeUpperCaseLetters,
                  includeDigits,
                  includeSpecial);
    var form = $("#modifyCardForm");
    m.changeFormEvent(form, "modifyCard", "ajaxModifyCard");
    m.ajaxifyForm(form);
    $("#nameInput").focus();
};

/**
 * Binds the events on the generate password form
 */
c.initGeneratePasswordForm = function() {
    var generatePasswordEnabledHandler = function() {
        var enabled = ($("#lowerCaseLettersIncluded").attr("checked") || 
                       $("#upperCaseLettersIncluded").attr("checked") || 
                       $("#digitsIncluded").attr("checked") || 
                       $("#specialCharactersIncluded").attr("checked"));
        $("#generatePasswordButton").attr("disabled", !enabled);
    };
    $("#lowerCaseLettersIncluded").change(generatePasswordEnabledHandler);
    $("#upperCaseLettersIncluded").change(generatePasswordEnabledHandler);
    $("#digitsIncluded").change(generatePasswordEnabledHandler);
    $("#specialCharactersIncluded").change(generatePasswordEnabledHandler);

    $("#generatePasswordButton").click(
        function() {
            var generatedPassword = 
                m.generatePassword($("#passwordLength").val(), 
                                 $("#lowerCaseLettersIncluded").attr("checked"),
                                 $("#upperCaseLettersIncluded").attr("checked"), 
                                 $("#digitsIncluded").attr("checked"), 
                                 $("#specialCharactersIncluded").attr("checked"));
            $("#password").val(generatedPassword);
            m.displayPasswordStrength(generatedPassword, $("#strength"), "inline-block");
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
};

/**
 * Binds the events for the card details section
 * @param passwordsUnmasked the passwords unmasked preference, which determines
 * which of the mask or unmask links must be initially shown
 * @param cardId the ID of the card
 */
c.initCardDetails = function(passwordsUnmasked, cardId) {
    if (passwordsUnmasked) {
      $("#maskPasswordLink").show();
    }
    else {
      $("#unmaskPasswordLink").show();
    }
    $("#unmaskPasswordLink").click(function() {
      return unmaskPassword();
    }); 
    $("#maskPasswordLink").click(function() {
      return maskPassword();
    });
    $("#cancelButton").click(function() {
      return closeCardDetails();
    });
    $("#createCardLink").click(function() {
      return createCard();
    });
    $("#modifyCardLink").click(function() {
      return modifyCard(cardId);
    });
    $("#deleteCardLink").click(function() {
      return deleteCard(cardId);
    });
};

c.initDeleteCard = function() {
    var form = $("#deleteCardForm");
    m.changeFormEvent(form, "deleteCard", "ajaxDeleteCard");
    m.ajaxifyForm(form);
    $("#cancelButton").click(function() {
        return closeCardDetails();
    });
};

m.cards = c;
})(memwords);
