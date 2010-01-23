function createCard() {
    loadMultiple(url("/cards/CreateCard.action?ajaxView="));
    return false;
}

function displayCard(cardId) {
    loadMultiple(url("/cards/DisplayCard.action?ajaxView=&cardId=" + cardId));
    return false;
}

function modifyCard(cardId) {
    loadMultiple(url("/cards/ModifyCard.action?ajaxView=&cardId=" + cardId));
    return false;
}

function deleteCard(cardId) {
    loadMultiple(url("/cards/DeleteCard.action?ajaxView=&cardId=" + cardId));
    return false;
}

function unmaskPassword() {
    $("#passwordDiv").attr("class", "unmasked");
    $("#maskPasswordLink").show();
    $("#unmaskPasswordLink").hide();
    return false;
} 

function maskPasswordField() {
    $("#passwordDiv").attr("class", "masked");
    $("#unmaskPasswordLink").show();
    $("#maskPasswordLink").hide();
    return false;
} 

function closeCardDetails() {
    loadMultiple(url("/cards/Cards.action?ajaxCancel="));
    return false;
}

function loadCardIcon() {
    var cardUrl = $("#urlTextField").val();
    if (isUrlValid(cardUrl)) {
        $("#submitButton").attr("disabled", true);
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
                     $("#submitButton").attr("disabled", false);
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

function changeTestUrlVisibility() {
    var cardUrl = $("#urlTextField").val();
    if (isUrlValid(cardUrl)) {
        $("#testUrlLink").show();
    } 
    else {
        $("#testUrlLink").hide();
    }
}

function isUrlValid(u) {
    return (u.indexOf("http://") == 0) || (u.indexOf("https://") == 0);
}