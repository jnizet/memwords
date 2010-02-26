<%@ tag isELIgnored="false"  %>
<%@ attribute name="test" required="true" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${test}"> style="display: none;"</c:if>