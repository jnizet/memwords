$("document").ready(function() {
  $("#urlTextField").change(function() {
    changeTestUrlVisibility(); 
    loadCardIcon();
  });
  $("#testUrlLink").click(function() {
    window.open($.trim($("#urlTextField").val()));
    return false;
  });
});