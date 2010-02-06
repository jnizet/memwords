$("document").ready(function() {
  $("#urlTextField").change(function() {
    changeTestUrlVisibility(); 
    loadCardIcon();
  });
  $("#testUrlLink").click(function() {
    window.open(absolutizeUrl($("#urlTextField").val()));
    return false;
  });
  // in IE, password fields are not auto-populated. We populate is with JavaScript
  $("#password").val("${actionBean.javaScriptEscapedPassword}");
  
  $("#password").keyup(function() {
    displayPasswordStrength($("#password").val(), $("#strength"), "inline-block");
  });
  displayPasswordStrength($("#password").val(), $("#strength"), "inline-block");
  
  $("#generatePasswordLink").click(function() {
    var generatedPassword = 
        generatePassword(${actionBean.passwordGenerationPreferences.length},
                         ${actionBean.passwordGenerationPreferences.lowerCaseLettersIncluded},
                         ${actionBean.passwordGenerationPreferences.upperCaseLettersIncluded},
                         ${actionBean.passwordGenerationPreferences.digitsIncluded},
                         ${actionBean.passwordGenerationPreferences.specialCharactersIncluded});
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
  
  <c:if test="${actionBean.modification}">
    // since JavaScript is activated, the password field is populated, and there is 
    // no need to display the change password div anymore. We check the checkbox and hide the div
    $("#changePasswordCheckBox").attr("checked", true);
    $("#changePasswordSpan").hide();
  </c:if>
});