<div id="cardDetails">
  <script type="text/javascript">
    $("document").ready(function() {
      $("#createCardLink").click(function() {
        return createCard();
      });
    });
  </script>
  <h2>
    <stripes:link beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean"
                  id="createCardLink" 
                  title="Create a new card"><img src="<c:url value="/img/add.png"/>" class="cardIcon" alt=""/>Create a new card</stripes:link>
  </h2>
</div>