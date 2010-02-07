<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<c:choose>
  <c:when test="${actionBean.context.loggedIn}">
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.account.LoginActionBean"
                                    event="logout"><fmt:message key="menu.logout"/></stripes:link></div>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.cards.CardsActionBean"><fmt:message key="menu.cards"/></stripes:link></div>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.account.AccountActionBean"><fmt:message key="menu.account"/></stripes:link></div>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.loginhistory.LoginHistoryActionBean"><fmt:message key="menu.loginHistory"/></stripes:link></div>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.preferences.PreferencesActionBean"><fmt:message key="menu.preferences"/></stripes:link></div>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.tools.ToolsActionBean"><fmt:message key="menu.tools"/></stripes:link></div>
  </c:when>
  <c:otherwise>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.account.LoginActionBean"><fmt:message key="menu.login"/></stripes:link></div>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.account.CreateAccountActionBean"><fmt:message key="menu.createAccount"/></stripes:link></div>
    <div class="menu"><stripes:link beanclass="com.googlecode.memwords.web.tools.ToolsActionBean"><fmt:message key="menu.tools"/></stripes:link></div>
  </c:otherwise>
</c:choose>