<%--
  This creates a set of checkboxes representing a property which is a list
  or array of values -
  property - the name of the property
  options - array or list of labelvalue beans
  readOnly - if true the collection is set to readonly (not yet implemented)
  author Steve Lewis
--%>

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
<html:multibox property="${property}" >${option.value}</html:multibox>${option.label}&nbsp;
</c:forEach>






