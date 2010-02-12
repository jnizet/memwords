function loadUserIdAvailability() {
    var userId = $.trim($("#userId").val());
    if (userId.length === 0) {
        $("#userIdAvailability").html("");
    } 
    else {
        setLoading($("#userIdAvailability"));
        $.ajax( {
            url : url("/account/CreateAccount.action"),
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