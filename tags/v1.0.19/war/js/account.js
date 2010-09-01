/*jslint browser: true */
/*global memwords: true, $: true */
(function(m) {
// private functions
/**
 * Loads the user availability, based on the value entered in the userId input field
 */
function loadUserIdAvailability() {
    var userId = $.trim($("#userId").val());
    if (userId.length === 0) {
        $("#userIdAvailability").html("");
    } 
    else {
        m.setLoading($("#userIdAvailability"));
        $.ajax( {
            url : m.url("/account/CreateAccount.action"),
            data : {
                ajaxGetUserIdAvailability : "",
                userId : userId
            },
            success : function(html) {
                $("#userIdAvailability").html(html);
            }
        });
    }
}

var a = {};
/**
 * Inits the create account page
 */
a.initCreateAccount = function() {
    $("#userId").bind("blur", function () {
        loadUserIdAvailability();
    });
    $("#userId").focus();
    $("#masterPassword").keyup(function() {
      m.displayPasswordStrength($("#masterPassword").val(), $("#strength"), "inline-block");
    });
    m.displayPasswordStrength($("#masterPassword").val(), $("#strength"), "inline-block");
};

m.account = a;
})(memwords);
