<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<c:choose>
  <c:when test="${actionBean.context.loggedIn}">
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.account.LoginActionBean"
                                    event="logout">Log out</stripes:link></div>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.cards.CardsActionBean">Cards</stripes:link></div>
    <div class="menu">Preferences</div>
  </c:when>
  <c:otherwise>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.account.LoginActionBean">Log in</stripes:link></div>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.account.CreateAccountActionBean">Create account</stripes:link></div>
  </c:otherwise>
</c:choose>