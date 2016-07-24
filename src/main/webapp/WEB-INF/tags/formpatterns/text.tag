<%-- Declare a taglib - the xml version is real messy --%>
<%@ tag body-content="empty" %>
<jsp:directive.attribute name="property" required="true" />
<jsp:directive.attribute name="readOnly" required="false" />
<jsp:directive.attribute name="displayWidth" required="false" />
<jsp:directive.attribute name="maxlength" required="false" />
<!%-- add taglib declarations --%>
<jsp:directive.include file="/include/declareTags.inc" />

<c:if test="${empty displayWidth}" >
<c:set var="displayWidth" value="0"  />
</c:if>
<c:if test="${empty maxlength}" >
<c:set var="maxlength" value="0"  />
</c:if>
<html:text property="${property}"
            disabled="${readOnly?'true':'false'}"
            size="${displayWidth}"
            maxlength="${maxLength}"
    />




