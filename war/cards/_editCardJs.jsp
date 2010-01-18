var loadIconHandler = function () {
    var cardUrl = $("#urlTextField").val();
    if (isUrlValid(cardUrl)) {
        loadUrlIcon(cardUrl, 
                    $("#iconUrlHiddenField"),
                    $("#iconUrlSpan"), 
                    $("#defaultIconUrlSpan"), 
                    $("#iconUrlLoading"),
                    $("#submitButton"));
    }
    else {
        $("#iconUrlHiddenField").val("");
        $("#iconUrlSpan").hide(); 
        $("#defaultIconUrlSpan").show();
    }
}

var testUrlHandler = function () {
    var cardUrl = $("#urlTextField").val();
    if (isUrlValid(cardUrl)) {
        $("#testUrlLink").show();
    }
    else {
        $("#testUrlLink").hide();
    }
}
  
$("document").ready(function() {
    $("#urlTextField").change(loadIconHandler);
    $("#urlTextField").change(testUrlHandler);
    $("#testUrlLink").click(function() {
        window.open($.trim($("#urlTextField").val()));
        return false;
    });
});