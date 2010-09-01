<div id="preferences">
  <script type="text/javascript">
    $("document").ready(function() {
      m.preferences.initPreferencesLinks();
    });
  </script>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePreferredLocaleActionBean"
                  id="changePreferredLocaleLink"
                  class="icon changePreferredLocale"><fmt:message 
      key="preferences._preferences.changePreferredLocale"/></stripes:link>
  </div>
  <div id="preferredLocaleDiv" style="display: none;"></div>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePreferredTimeZoneActionBean"
                  id="changePreferredTimeZoneLink"
                  class="icon changePreferredTimeZone"><fmt:message 
      key="preferences._preferences.changePreferredTimeZone"/></stripes:link>
  </div>
  <div id="preferredTimeZoneDiv" style="display: none;"></div>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePasswordPreferencesActionBean" 
                  id="changePasswordPreferencesLink"
                  class="icon changePasswordPreferences"><fmt:message 
      key="preferences._preferences.changePasswordPreferences"/></stripes:link>
  </div>
  <div id="passwordPreferencesDiv" style="display: none;"></div>
  <div>
    <stripes:link beanclass="com.googlecode.memwords.web.preferences.ChangePasswordGenerationPreferencesActionBean" 
                  id="changePasswordGenerationPreferencesLink"
                  class="icon changePasswordGenerationPreferences"><fmt:message 
      key="preferences._preferences.changePasswordGenerationPreferences"/></stripes:link>
  </div>
  <div id="passwordGenerationPreferencesDiv" style="display: none;"></div>
</div>