<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Card Details</title>
</head>
<body>
    <h1>Card Details</h1>
    <tags:cardsList cards="${actionBean.cards}" readOnly="${false}"/>
    <div id="cardDetails">
        <c:set var="card" value="${actionBean.card}"/>
        <h2><c:out value="${card.name}"/></h2>
        <table>
          <tr>
            <th>Name :</th>
            <td><c:out value="${card.name}"/></td>
          </tr>
          <tr>
            <td colspan="2">
              <stripes:form beanclass="com.googlecode.memwords.web.cards.DisplayCardActionBean">
                <stripes:url var="cardsUrl" beanclass="com.googlecode.memwords.web.cards.CardsActionBean"/>
                <stripes:submit name="cancel" value="Close" onclick="window.location = '${cardsUrl}'; return false;"/>
              </stripes:form>
            </td>
          </tr>
        </table>
        <div id="createCardLink">
            <stripes:link beanclass="com.googlecode.memwords.web.cards.CreateCardActionBean">Create a new card</stripes:link>
        </div>
    </div>
</body>
</html>