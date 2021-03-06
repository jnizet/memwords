/*jslint browser: true */
/*global memwords: true, $: true */
(function(m) {
var t = {};

t.initTools = function(passwordsUnmasked) {
    $("#noScript").hide();
    $("#tools").show();

    $("#evalPasswordStrengthForm").submit(function() {
        return false;
    });
    $("#generatePasswordForm").submit(function() {
        return false;
    });
    $("#passwordToEval").keyup(function() {
        m.displayPasswordStrength($("#passwordToEval").val(), $("#passwordToEvalStrength"), "inline-block");
    });
    m.displayPasswordStrength($("#passwordToEval").val(), $("#passwordToEvalStrength"), "inline-block");

    if (passwordsUnmasked) {
        $("#maskPasswordLink").show();
    }
    else {
        $("#unmaskPasswordLink").show();
    }
    
    $("#unmaskPasswordLink").click(function() {
        $("#generatedPassword").attr("class", "unmasked");
        $("#unmaskPasswordLink").hide();
        $("#maskPasswordLink").show();
        $("#maskedPasswordHelp").hide();
        return false;
    }); 
    $("#maskPasswordLink").click(function() {
        $("#generatedPassword").attr("class", "masked");
        $("#maskPasswordLink").hide();
        $("#unmaskPasswordLink").show();
        $("#maskedPasswordHelp").show();
        return false;
    });
    $("#generatePasswordButton").click(function() {
        var generatedPassword = 
            m.generatePassword(
                $("#length").val(), 
                $("#lowerCaseLettersIncluded").attr("checked"),
                $("#upperCaseLettersIncluded").attr("checked"), 
                $("#digitsIncluded").attr("checked"), 
                $("#specialCharactersIncluded").attr("checked"));
        m.displayPasswordStrength(generatedPassword, $("#generatedPasswordStrength"));
        $("#generatedPassword").text(generatedPassword);
        $("#generatedPasswordDiv").slideDown();
    });
    
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
    
    $("#passwordToEval").focus();
};

m.tools = t;
}
)(memwords);