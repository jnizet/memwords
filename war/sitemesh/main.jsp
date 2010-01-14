<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Password Manager - <decorator:title default="" /></title>
        <link href="<c:url value="/css/main.css"/>" rel="stylesheet" type="text/css">
        <decorator:head />
    </head>

    <body <decorator:getProperty property="body.onload" writeEntireProperty="true"/>>
        <div id="header">
           Password Manager v0.1
        </div>
	    <div id="container">
		    <div id="content">
                <c:if test="${!empty actionBean.context.validationErrors || !empty actionBean.context.messages}">
                    <div id="messages">
                        <stripes:errors/>
                        <stripes:messages/>
                    </div>
                </c:if>
                <div id="body">
                    <decorator:body />
                </div>
            </div>
            <div id="sidebar">
	          <%@ include file="/sitemesh/menu.jspf" %>
	        </div>
	    </div>
	    <div id="footer">
            Source code available at <a href="http://sourceforge.net">SourceForge</a>
        </div>
    </body>
</html>