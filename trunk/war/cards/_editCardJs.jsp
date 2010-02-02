$("document").ready(function() {
  $("#urlTextField").change(function() {
    changeTestUrlVisibility(); 
    loadCardIcon();
  });
  $("#testUrlLink").click(function() {
    window.open($.trim($("#urlTextField").val()));
    return false;
  });
  // in IE, password fields are not auto-populated. We populate is with JavaScript
  $("#password").val("${actionBean.javaScriptEscapedPassword}");
  
  $("#password").keyup(function() {
    displayPasswordStrength($("#password").val(), $("#strength"), "inline-block");
  });
  displayPasswordStrength($("#oldPassword").val(), $("#strength"), "inline-block");
  
  <c:if test="${actionBean.modification}">
    // since JavaScript is activated, the password field is populated, and there is 
    // no need to display the change password div anymore. We check the checkbox and hide the div
    $("#changePasswordCheckBox").attr("checked", true);
    $("#changePasswordSpan").hide();
  </c:if>
});