function closePreferencesDiv(divToClose) {
    divToClose.slideUp(function() {
        divToClose.html("");
        divToClose.hide();
    });
    return false;
}

function initPreferencesLinks() {
    $("#changePreferredLocaleLink").click(function() {
        return changePreferredLocale();
    });
    $("#changePreferredTimeZoneLink").click(function() {
        return changePreferredTimeZone();
    });
    $("#changePasswordPreferencesLink").click(function() {
        return changePasswordPreferences();
    });
    $("#changePasswordGenerationPreferencesLink").click(function() {
        return changePasswordGenerationPreferences();
    });
}

function changePreferredLocale() {
    closePreferencesDiv($("#preferredTimeZoneDiv"));
    closePreferencesDiv($("#passwordPreferencesDiv"));
    closePreferencesDiv($("#passwordGenerationPreferencesDiv"));
    clearMessagePanel();
    $("#preferredLocaleDiv").load(
        url("/preferences/ChangePreferredLocale.action?ajaxView="),
        function() {
            $("#preferredLocaleDiv").slideDown();
        });
    return false;
}

function changePreferredTimeZone() {
    closePreferencesDiv($("#preferredLocaleDiv"));
    closePreferencesDiv($("#passwordPreferencesDiv"));
    closePreferencesDiv($("#passwordGenerationPreferencesDiv"));
    clearMessagePanel();
    $("#preferredTimeZoneDiv").load(
        url("/preferences/ChangePreferredTimeZone.action?ajaxView="),
        function() {
            $("#preferredTimeZoneDiv").slideDown();
        });
    return false;
}

function changePasswordPreferences() {
    closePreferencesDiv($("#preferredLocaleDiv"));
    closePreferencesDiv($("#preferredTimeZoneDiv"));
    closePreferencesDiv($("#passwordGenerationPreferencesDiv"));
    clearMessagePanel();
    $("#passwordPreferencesDiv").load(
        url("/preferences/ChangePasswordPreferences.action?ajaxView="),
        function() {
            $("#passwordPreferencesDiv").slideDown();
        });
    return false;
}

function changePasswordGenerationPreferences() {
    closePreferencesDiv($("#preferredLocaleDiv"));
    closePreferencesDiv($("#preferredTimeZoneDiv"));
    closePreferencesDiv($("#passwordPreferencesDiv"));
    clearMessagePanel();
    loadMultiple(url("/preferences/ChangePasswordGenerationPreferences.action?ajaxView="),
                 function() {
                     $("#passwordGenerationPreferencesDiv").slideDown();
                 });
    return false;
}

function bindChangePasswordGenerationPreferencesEvents() {
    changeFormEvent($("#changePasswordGenerationPreferencesForm"), "change", "ajaxChange");
    ajaxifyForm($("#changePasswordGenerationPreferencesForm"));
    $("#cancelButton").unbind();
    $("#cancelButton").click(function() {
        return closePreferencesDiv($("#passwordGenerationPreferencesDiv"));
    });
    
    var changeHandler = function() {
        var enabled = ($("#lowerCaseLettersIncluded").attr("checked")
                         || $("#upperCaseLettersIncluded").attr("checked")
                         || $("#digitsIncluded").attr("checked") 
                         || $("#specialCharactersIncluded").attr("checked"));
        $("#submitButton").attr("disabled", !enabled);
        var generatedPassword = 
            generatePassword($("#length").val(), 
                             $("#lowerCaseLettersIncluded").attr("checked"),
                             $("#upperCaseLettersIncluded").attr("checked"), 
                             $("#digitsIncluded").attr("checked"), 
                             $("#specialCharactersIncluded").attr("checked"));
        displayPasswordStrength(generatedPassword, $("#strength"), "inline-block");
    }
    
    $("#lowerCaseLettersIncluded").change(changeHandler);
    $("#upperCaseLettersIncluded").change(changeHandler);
    $("#digitsIncluded").change(changeHandler);
    $("#specialCharactersIncluded").change(changeHandler);
    $("#length").change(changeHandler);
    
    $("#strengthSection").show();
    changeHandler.call();
    $("#length").focus();
}