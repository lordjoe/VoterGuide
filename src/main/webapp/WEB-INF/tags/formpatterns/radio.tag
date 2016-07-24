<%-- Declare a taglib - the xml version is real messy --%>
<%@ tag body-content="empty" %>
<jsp:directive.attribute name="property" required="true" />
<%-- NOTE! type is really important to stop sttribute from
   being evaluated as a string--%>
<jsp:directive.attribute name="options" required="true" type="java.lang.Object" />
<jsp:directive.attribute name="readOnly" required="false" />
<%-- add taglib declarations --%>
<jsp:directive.include file="/include/declareTags.inc" />


<c:forEach var="option" items="${options}" >
<html:radio property="${property}" idName="option"  value="value" />${option.label}&nbsp
</c:forEach>







