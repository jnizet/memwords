<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><fmt:message key="account.account.title"/></title>
</head>
<body>
  <h1><fmt:message key="account.account.h1"/></h1>

  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.account.ChangeMasterPasswordActionBean"
                  class="icon changeMasterPassword"><fmt:message key="account.account.changeMasterPassword"/></stripes:link>
  </div>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.account.DestroyAccountActionBean"
                  class="icon destroyAccount"><fmt:message key="account.account.destroyAccount"/></stripes:link>
  </div>
</body>
</html>
