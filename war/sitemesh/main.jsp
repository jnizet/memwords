<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>MemWords - <decorator:title default="" /></title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
        <!--[if IE]>
		    <link rel="stylesheet" type="text/css" href="<c:url value="/css/ie.css"/>"/>
		<![endif]-->
        <script type="text/javascript" src="<c:url value="/js/jquery-1.3.2.js" />"></script>
        <script type="text/javascript" src="<c:url value="/js/jquery.form.js" />"></script>
        <script type="text/javascript">
            <c:set var="baseUrl" value="${request.contextPath}"/>
            <c:if test="${empty baseUrl}"><c:set var="baseUrl" value="/"/></c:if>
            var baseUrl = '${baseUrl}';
            <c:if test="${header.integrationTesting == true}">$.ajaxSetup({ async: false });</c:if>
        </script>
        <script type="text/javascript" src="<c:url value="/js/main.js" />"></script>
        <decorator:head />
    </head>

    <body <decorator:getProperty property="body.onload" writeEntireProperty="true"/>>
        <div id="header">
           <a href="<c:url value="/Index.action"/>">MemWords v0.1</a>
        </div>
	    <div id="container">
		    <div id="content">
		        <div id="messagesContainer">
	                <div id="messagesControl">
                        <a id="hideMessagesLink" href="#" onclick="hideMessagePanel();"><img src="<c:url value="/img/up.png"/>" width="16" height="16" alt="Hide message panel" title="Hide message panel"/></a>
                        <a id="showMessagesLink" href="#" onclick="showMessagePanel();"><img src="<c:url value="/img/down.png"/>" width="16" height="16" alt="Show message panel" title="Show message panel"/></a>
                    </div>
                    <tags:messages/>
                </div>
                <div id="body">
                    <decorator:body />
                </div>
            </div>
            <div id="sidebar">
	          <%@ include file="_menu.jsp" %>
	        </div>
	    </div>
	    <div id="footer">
            Source code freely available at <a href="http://code.google.com/p/memwords/">Google Code</a>
        </div>
    </body>
</html>