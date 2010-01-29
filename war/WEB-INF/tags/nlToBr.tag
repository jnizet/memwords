<%@ tag import="com.googlecode.memwords.web.util.HtmlUtils"
        isELIgnored="false" %><%@ attribute name="value" 
                                            required="true" 
                                            type="java.lang.String" %><%= HtmlUtils.nlToBr(value, true) %>