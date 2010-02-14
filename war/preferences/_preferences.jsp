<div id="preferences">
  <script type="text/javascript">
    $("document").ready(function() {
      m.preferences.initPreferencesLinks();
    });
  </script>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePreferredLocaleActionBean"
                  id="changePreferredLocaleLink">
      <img class="icon" alt="" src="<c:url value="/img/locale.png"/>"/>
      <fmt:message key="preferences._preferences.changePreferredLocale"/></stripes:link>
  </div>
  <div id="preferredLocaleDiv" style="display: none;"></div>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePreferredTimeZoneActionBean"
                  id="changePreferredTimeZoneLink">
      <img class="icon" alt="" src="<c:url value="/img/timezone.png"/>"/>
      <fmt:message key="preferences._preferences.changePreferredTimeZone"/></stripes:link>
  </div>
  <div id="preferredTimeZoneDiv" style="display: none;"></div>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePasswordPreferencesActionBean" 
                  id="changePasswordPreferencesLink">
      <img class="icon" alt="" src="<c:url value="/img/key.png"/>"/>
      <fmt:message key="preferences._preferences.changePasswordPreferences"/></stripes:link>
  </div>
  <div id="passwordPreferencesDiv" style="display: none;"></div>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePasswordGenerationPreferencesActionBean" 
                  id="changePasswordGenerationPreferencesLink">
      <img class="icon" alt="" src="<c:url value="/img/generation.png"/>"/>
      <fmt:message key="preferences._preferences.changePasswordGenerationPreferences"/></stripes:link>
  </div>
  <div id="passwordGenerationPreferencesDiv" style="display: none;"></div>
</div>