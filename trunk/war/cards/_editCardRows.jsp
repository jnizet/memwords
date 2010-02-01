<tr>
  <th><label class="required"><fmt:message key="cards._editCardRows.nameLabel"/></label></th>
  <td>
    <stripes:text name="name" id="nameInput"/>
    <tags:help key="cards._editCardRows.nameHelp"/>
    <%-- fake password field in order to avoid autocomplete and "autoclear" of password field --%>
    <input type="password" name="" style="display: none;"/>
  </td>
</tr>
<tr>
  <fmt:message key="cards._editCardRows.loginTitle" var="loginTitle"/>
  <th><label class="required"><fmt:message key="cards._editCardRows.loginLabel"/></label></th>
  <td>
    <stripes:text name="login"/>
    <tags:help key="cards._editCardRows.loginHelp"/>
  </td>
</tr>
<tr>
  <th><label class="required"><fmt:message key="cards._editCardRows.passwordLabel"/></label></th>
  <td>
    <stripes-d:password name="password" autocomplete="off" repopulate="true"/>
    <tags:help key="cards._editCardRows.passwordHelp"/>
  </td>
</tr>
<tr>
  <th><fmt:message key="cards._editCardRows.urlLabel"/></th>
  <td>
     <stripes:text name="url" id="urlTextField" size="30" title="${urlTitle}"/>
     <tags:help key="cards._editCardRows.urlHelp"/>
     <a class="external" style="display: none;" id="testUrlLink" target="_blank" href="#"><fmt:message key="cards._editCardRows.testUrlLink"/></a>
  </td>
</tr>
<tr>
  <th><fmt:message key="cards._editCardRows.iconLabel"/></th>
  <td>
     <stripes:hidden name="iconUrl" id="iconUrlHiddenField"/>
     <stripes:hidden name="iconUrlFetched" id="iconUrlFetchedHiddenField"/>
     <span id="defaultIconUrlSpan" <tags:hideIf test="${!empty actionBean.iconUrl}"/>>
       <img src="<c:url value="/img/card.png"/>" class="cardIcon" alt=""/>
     </span>
     <span id="iconUrlSpan" <tags:hideIf test="${empty actionBean.iconUrl}"/>>
       <img src="<c:out value="${actionBean.iconUrl}"/>" class="cardIcon" alt=""/>
     </span>
     <span id="iconUrlLoadingSpan"></span>
     <tags:help key="cards._editCardRows.iconHelp"/>
  </td>
</tr>
<tr>
  <th class="top"><fmt:message key="cards._editCardRows.noteLabel"/></th>
  <td>
     <stripes:textarea name="note" cols="45" rows="3"/>
     <tags:help key="cards._editCardRows.noteHelp"/>
  </td>
</tr>