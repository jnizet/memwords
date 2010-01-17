<%@ taglib prefix="stripes-d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<div id="cardDetails">
	<script type=text/javascript>
	  var loadIconHandler = function () {
		  var cardUrl = $("#urlTextField").val();
		  if (isUrlValid(cardUrl)) {
			  loadUrlIcon(cardUrl, 
					      $("#iconUrlHiddenField"),
					      $("#iconUrlSpan"), 
					      $("#defaultIconUrlSpan"), 
	                      $("#iconUrlLoading"),
	                      $("#createCardButton"));
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
		var form = $("#createCardForm")
		changeFormEvent(form, "createCard", "ajaxCreateCard");
		ajaxifyForm(form);
		$("#urlTextField").change(loadIconHandler);
		$("#urlTextField").change(testUrlHandler);
        $("#testUrlLink").click(function() {
            window.open($.trim($("#urlTextField").val()));
            return false;
        });
	  });
	</script>
	<stripes:form id="createCardForm"
	              beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean">
	<table>
	  <tr>
	    <th><label class="mandatory">Name :</label></th>
	    <td><stripes:text name="name"/></td>
	  </tr>
	  <tr>
        <th><label class="mandatory">Login / User ID :</label></th>
        <td><stripes:text name="login"/></td>
      </tr>
      <tr>
        <th><label class="mandatory">Password :</label></th>
        <td><stripes-d:password name="password" autocomplete="off"/></td>
      </tr>
	  <tr>
	    <th>URL :</th>
	    <td>
	       <stripes:text name="url" id="urlTextField" size="30"/>
	       <a class="external" style="display: none;" id="testUrlLink" target="_blank" href="#">Test</a>
	    </td>
	  </tr>
	  <tr>
        <th>Icon :</th>
        <td>
           <stripes:hidden name="iconUrl" id="iconUrlHiddenField"/>
           <span id="defaultIconUrlSpan" <tags:hideIf test="${!empty actionBean.iconUrl}"/>>
             <img src="<c:url value="/img/card.png"/>" class="cardIcon" alt=""/>
           </span>
           <span id="iconUrlSpan" <tags:hideIf test="${empty actionBean.iconUrl}"/>>
             <img src="<c:out value="${actionBean.iconUrl}"/>" class="cardIcon" alt=""/>
           </span>
           <span id="iconUrlLoading"></span>
        </td>
      </tr>
	  <tr>
	    <td colspan="2">
	      <stripes:submit name="createCard" value="Create card" id="createCardButton"/>
	      <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean"/>
	      <stripes:submit name="cancel" value="Cancel" onclick="window.location = '${cardsUrl}'; return false;"/>
	    </td>
	  </tr>
	</table>
	</stripes:form>
  </div>
</body>
