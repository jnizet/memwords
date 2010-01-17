<div id="cardDetails">
    <script type="text/javascript">
      $(document).ready(function() {
    	  $("#unmaskPasswordLink").show();
          $("#unmaskPasswordLink").click(function() {
              $("#passwordDiv").attr("class", "unmasked");
              $("#maskPasswordLink").show();
              $("#unmaskPasswordLink").hide();
              return false;
          }); 
          $("#maskPasswordLink").click(function() {
              $("#passwordDiv").attr("class", "masked");
              $("#unmaskPasswordLink").show();
              $("#maskPasswordLink").hide();
              return false;
          }); 
      });
    </script>
    <c:set var="card" value="${actionBean.card}"/>
    <h2>
      <tags:cardIcon card="${card}"/>
      <c:out value="${card.name}"/>
    </h2>
    <table>
       <tr>
        <th>Login / User ID :</th>
        <td><c:out value="${card.login}"/></td>
      </tr>
      <tr>
        <th>Password :</th>
        <td>
          <div style="float: left;" id="passwordDiv" class="masked"><c:out value="${card.password}"/></div>
          <a href="#" style="display: none;" id="unmaskPasswordLink">Unmask</a>
          <a href="#" style="display: none;" id="maskPasswordLink">Mask</a>
        </td>
      </tr>
      <tr>
        <th>URL :</th>
        <td>
          <c:if test="${!empty card.url}">
            <a class="external" target="_blank" href="<c:out value="${card.url}"/>"><c:out value="${card.url}"/></a>
          </c:if>
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <stripes:form beanclass="com.googlecode.memwords.web.cards.DisplayCardActionBean">
            <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean"/>
            <stripes:submit name="cancel" value="Close" onclick="return closeCardDetails();"/>
          </stripes:form>
        </td>
      </tr>
    </table>
    <div id="createCardLink">
        <stripes:link beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean"
                      onclick="return createCard();">Create a new card</stripes:link>
    </div>
</div>