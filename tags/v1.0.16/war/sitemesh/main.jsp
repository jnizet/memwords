<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="${pageContext.request.locale.language}">
  <head>
    <title><fmt:message key="main.titlePrefix"/><decorator:title default="" /></title>
    <meta http-equiv="Content-Language" content="${pageContext.request.locale.language}"/>    
    <link rel="shortcut icon" href="<c:url value="/img/mw.png"/>" type="image/png"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>"/>
    <!--[if IE]>
      <link rel="stylesheet" type="text/css" href="<c:url value="/css/ie.css"/>"/>
    <![endif]-->
    <script type="text/javascript" src="<c:url value="/js/jquery-1.3.2.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-ui-1.7.2.custom.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.form.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/main.min.js" />"></script>
    <script type="text/javascript">
      <c:if test="${header.ajaxSync == true}">$.ajaxSetup({ async: false });</c:if>
      m.config.baseUrl = '${request.contextPath}';
      m.install(); 
    </script>
    <decorator:head />
  </head>

  <body>
    <div id="header">
      <a href="<c:url value="/Index.action"/>"><fmt:message key="main.header.appNameAndVersion"/></a>
      <div id="loading"><img src="<c:url value="/img/busy_big.gif"/>" width="32" height="32" alt="<fmt:message key="main.loadingMessage"/>" title="<fmt:message key="main.loadingMessage"/>"/></div>
    </div>
    <div id="container">
      <div id="content">
        <%@ include file="_menuBar.jsp" %>
        <div id="messagesContainer">
          <div id="messagesControl">
            <a id="hideMessagesLink" href="#" title="<fmt:message key="main.hideMessagePanelLinkTitle"/>"><img src="<c:url value="/img/up.png"/>" width="16" height="16" alt="<fmt:message key="main.hideMessagePanelLinkTitle"/>"/></a>
            <a id="showMessagesLink" href="#" title="<fmt:message key="main.showMessagePanelLinkTitle"/>"><img src="<c:url value="/img/down.png"/>" width="16" height="16" alt="<fmt:message key="main.showMessagePanelLinkTitle"/>"/></a>
          </div>
          <tags:messages/>
        </div>
        <div id="body">
          <decorator:body />
        </div>
      </div>
    </div>
    <div id="footer">
      <fmt:message key="main.footer.text"/>
    </div>
  </body>
</html>