<%-- Declare a taglib - the xml version is real messy --%>
<%@ tag body-content="empty" %>
<jsp:directive.attribute name="property" required="true" />
<%-- NOTE! type is really important to stop sttribute from
   being evaluated as a string--%>
<jsp:directive.attribute name="options" required="true" type="java.lang.Object" />
<jsp:directive.attribute name="readOnly" required="false" />
<jsp:directive.attribute name="size" required="false" />
<jsp:directive.attribute name="displayWidth" required="false" />
<%-- add taglib declarations --%>
<jsp:directive.include file="/include/declareTags.inc" />

<c:if test="${empty displayWidth}" >
<c:set var="displayWidth" value="0" />
</c:if>
<c:if test="${empty size}" >
<c:set var="size" value="1" />
</c:if>

<html:select property="${property}"
            disabled="${readOnly?'true':'false'}"
            size="${size}"
              multiple="true"
          >
<html:options collection="options"  property="value" labelProperty="label" />

</html:select>





