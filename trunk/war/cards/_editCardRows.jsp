<tr>
  <th><label class="mandatory">Name :</label></th>
  <td>
    <stripes:text name="name"/>
    <%-- fake password field in order to avoid autocomplete and "autoclear" of password field --%>
    <input type="password" name="" style="display: none;"/>
  </td>
</tr>
<tr>
  <th><label class="mandatory">Login :</label></th>
  <td><stripes:text name="login"/></td>
</tr>
<tr>
  <th><label class="mandatory">Password :</label></th>
  <td><stripes-d:password name="password" autocomplete="off" repopulate="true"/></td>
</tr>
<tr>
  <th>URL :</th>
  <td>
     <stripes:text name="url" id="urlTextField" size="30"/>
     <a class="external" style="display: none;" id="testUrlLink" target="_blank" href="#">Test</a>
  </td>
</tr>
<tr>
  <th>Icon :</th>
  <td>
     <stripes:hidden name="iconUrl" id="iconUrlHiddenField"/>
     <span id="defaultIconUrlSpan" <tags:hideIf test="${!empty actionBean.iconUrl}"/>>
       <img src="<c:url value="/img/card.png"/>" class="cardIcon" alt=""/>
     </span>
     <span id="iconUrlSpan" <tags:hideIf test="${empty actionBean.iconUrl}"/>>
       <img src="<c:out value="${actionBean.iconUrl}"/>" class="cardIcon" alt=""/>
     </span>
     <span id="iconUrlLoadingSpan"></span>
  </td>
</tr>
<tr>
  <th style="vertical-align: top;">Note :</th>
  <td>
     <stripes:textarea name="note" cols="45" rows="3"/>
  </td>
</tr>