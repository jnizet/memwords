<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>

<ul id="menuBar">
<c:choose>
  <c:when test="${actionBean.context.loggedIn}">
    <li><stripes:link beanclass="com.googlecode.memwords.web.account.LoginActionBean"
                                    event="logout"><fmt:message key="menu.logout"/></stripes:link></li>
    <li><stripes:link beanclass="com.googlecode.memwords.web.cards.CardsActionBean"><fmt:message 
      key="menu.cards"/></stripes:link></li>
    <li><stripes:link beanclass="com.googlecode.memwords.web.account.AccountActionBean"><fmt:message 
      key="menu.account"/></stripes:link></li>
    <li><stripes:link beanclass="com.googlecode.memwords.web.loginhistory.LoginHistoryActionBean"><fmt:message 
      key="menu.loginHistory"/></stripes:link></li>
    <li><stripes:link beanclass="com.googlecode.memwords.web.preferences.PreferencesActionBean"><fmt:message 
      key="menu.preferences"/></stripes:link></li>
    <li><stripes:link beanclass="com.googlecode.memwords.web.tools.ToolsActionBean"><fmt:message 
      key="menu.tools"/></stripes:link></li>
  </c:when>
  <c:otherwise>
    <li><stripes:link beanclass="com.googlecode.memwords.web.account.LoginActionBean"><fmt:message 
      key="menu.login"/></stripes:link></li>
    <li><stripes:link beanclass="com.googlecode.memwords.web.account.CreateAccountActionBean"><fmt:message 
      key="menu.createAccount"/></stripes:link></li>
    <li><stripes:link beanclass="com.googlecode.memwords.web.tools.ToolsActionBean"><fmt:message 
      key="menu.tools"/></stripes:link></li>
  </c:otherwise>
</c:choose>
</ul>