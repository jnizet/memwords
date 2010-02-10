<%@ tag isELIgnored="false"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="formNotice">
  <c:url var="go" value="/img/go.png"/>
  <fmt:message key="form.notice">
    <fmt:param value="${go}"/>
  </fmt:message>
</div>