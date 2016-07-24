<%-- Declare a taglib - the xml version is real messy --%>
<%@ tag body-content="empty" %>
<%-- this added two table elements representing a label and a text field
   input is a FilterOption
--%>
<jsp:directive.attribute name="holder" required="true" type="java.lang.Object" />
<%-- add taglib declarations --%>
<jsp:directive.include file="/include/declareTags.inc" />

<c:set var="propString" value="value(${holder.name})" />

<td><b17:label key="${holder.labelKey}" /></td>
<td><html:text property="${propString}" /></td>




