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
}

function changePreferredLocale() {
    closePreferencesDiv($("#preferredTimeZoneDiv"));
    closePreferencesDiv($("#passwordPreferencesDiv"));
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
    clearMessagePanel();
    $("#passwordPreferencesDiv").load(
        url("/preferences/ChangePasswordPreferences.action?ajaxView="),
        function() {
            $("#passwordPreferencesDiv").slideDown();
        });
    return false;
}