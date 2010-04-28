/*jslint browser: true */
/*global memwords: true, $: true */
(function(m) {

function closePreferencesDiv(divToClose) {
    divToClose.slideUp(function() {
        divToClose.html("");
        divToClose.hide();
    });
    return false;
}

function changePreferredLocale() {
    closePreferencesDiv($("#preferredTimeZoneDiv"));
    closePreferencesDiv($("#passwordPreferencesDiv"));
    closePreferencesDiv($("#passwordGenerationPreferencesDiv"));
    m.clearMessagePanel();
    $("#preferredLocaleDiv").load(
        m.url("/preferences/ChangePreferredLocale.action?ajaxView="),
        function() {
            $("#preferredLocaleDiv").slideDown();
        });
    return false;
}

function changePreferredTimeZone() {
    closePreferencesDiv($("#preferredLocaleDiv"));
    closePreferencesDiv($("#passwordPreferencesDiv"));
    closePreferencesDiv($("#passwordGenerationPreferencesDiv"));
    m.clearMessagePanel();
    $("#preferredTimeZoneDiv").load(
        m.url("/preferences/ChangePreferredTimeZone.action?ajaxView="),
        function() {
            $("#preferredTimeZoneDiv").slideDown();
        });
    return false;
}

function changePasswordPreferences() {
    closePreferencesDiv($("#preferredLocaleDiv"));
    closePreferencesDiv($("#preferredTimeZoneDiv"));
    closePreferencesDiv($("#passwordGenerationPreferencesDiv"));
    m.clearMessagePanel();
    $("#passwordPreferencesDiv").load(
        m.url("/preferences/ChangePasswordPreferences.action?ajaxView="),
        function() {
            $("#passwordPreferencesDiv").slideDown();
        });
    return false;
}

function changePasswordGenerationPreferences() {
    closePreferencesDiv($("#preferredLocaleDiv"));
    closePreferencesDiv($("#preferredTimeZoneDiv"));
    closePreferencesDiv($("#passwordPreferencesDiv"));
    m.clearMessagePanel();
    m.loadMultiple(m.url("/preferences/ChangePasswordGenerationPreferences.action?ajaxView="),
                   function() {
                       $("#passwordGenerationPreferencesDiv").slideDown();
                   });
    return false;
}
    
var p = {};

p.initPreferencesLinks = function() {
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
};

p.initChangePreferredLocale = function() {
    $("#cancelButton").click(function() {
        return closePreferencesDiv($("#preferredLocaleDiv"));
    });
    $("#locale").focus();
};

p.initChangePreferredTimeZone = function() {
    m.changeFormEvent($("#changePreferredTimeZoneForm"), "change", "ajaxChange");
    m.ajaxifyForm($("#changePreferredTimeZoneForm"));
    $("#cancelButton").unbind();
    $("#cancelButton").click(function() {
        return closePreferencesDiv($("#preferredTimeZoneDiv"));
    });
    $("#timeZone").focus();
};

p.initChangePasswordPreferences = function() {
    m.changeFormEvent($("#changePasswordPreferencesForm"), "change", "ajaxChange");
    m.ajaxifyForm($("#changePasswordPreferencesForm"));
    $("#cancelButton").unbind();
    $("#cancelButton").click(function() {
        return closePreferencesDiv($("#passwordPreferencesDiv"));
    });
    $("#masked").focus();
};

p.initChangePasswordGenerationPreferences = function() {
    m.changeFormEvent($("#changePasswordGenerationPreferencesForm"), "change", "ajaxChange");
    m.ajaxifyForm($("#changePasswordGenerationPreferencesForm"));
    $("#cancelButton").unbind();
    $("#cancelButton").click(function() {
        return closePreferencesDiv($("#passwordGenerationPreferencesDiv"));
    });
    
    var changeHandler = function() {
        var enabled = ($("#lowerCaseLettersIncluded").attr("checked") || 
                       $("#upperCaseLettersIncluded").attr("checked") || 
                       $("#digitsIncluded").attr("checked") || 
                       $("#specialCharactersIncluded").attr("checked"));
        $("#submitButton").attr("disabled", !enabled);
        var generatedPassword = 
            m.generatePassword($("#length").val(), 
                               $("#lowerCaseLettersIncluded").attr("checked"),
                               $("#upperCaseLettersIncluded").attr("checked"), 
                               $("#digitsIncluded").attr("checked"), 
                               $("#specialCharactersIncluded").attr("checked"));
        m.displayPasswordStrength(generatedPassword, $("#strength"), "inline-block");
    };
    
    $("#lowerCaseLettersIncluded").change(changeHandler);
    $("#upperCaseLettersIncluded").change(changeHandler);
    $("#digitsIncluded").change(changeHandler);
    $("#specialCharactersIncluded").change(changeHandler);
    $("#length").change(changeHandler);
    
    $("#strengthSection").show();
    changeHandler.call();
    $("#length").focus();
};

m.preferences = p;
}
)(memwords);