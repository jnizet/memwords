<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>MemWords - <decorator:title default="" /></title>
        <link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="<c:url value="/js/jquery1.3.2.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/js/jquery.blockUI.js" />"></script>
        <script type="text/javascript" src="<c:url value="/js/main.js" />"></script>
        <decorator:head />
    </head>

    <body <decorator:getProperty property="body.onload" writeEntireProperty="true"/>>
        <div id="header">
           MemWords v0.1
        </div>
	    <div id="container">
		    <div id="content">
		        <div id="messagesContainer">
	                <div id="messagesControl">
                        <a id="hideMessagesLink" href="#" onclick="hideMessagePanel();"><img src="<c:url value="/img/up.png"/>" width="16" height="16" alt="Hide message panel" title="Hide message panel"/></a>
                        <a id="showMessagesLink" href="#" onclick="showMessagePanel();"><img src="<c:url value="/img/down.png"/>" width="16" height="16" alt="Show message panel" title="Show message panel"/></a>
                    </div>
                    <div id="messages">
	                    <stripes:errors/>
	                    <stripes:messages/>
	                </div>
                </div>
                <div id="body">
                    <decorator:body />
                </div>
            </div>
            <div id="sidebar">
	          <%@ include file="/sitemesh/menu.jspf" %>
	        </div>
	    </div>
	    <div id="footer">
            Source code freely available at <a href="http://code.google.com/p/memwords/">Google Code</a>
        </div>
    </body>
</html>