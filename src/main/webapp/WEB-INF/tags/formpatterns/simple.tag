<%--
  This  is a test tag which takes an attribute called name and returns 
  display Hello ${name} used for setting up taglibs
  author Steve Lewis
--%>

<%-- Declare a taglib - the xml version is real messy --%>
<%@ tag body-content="empty" %>
<%-- NOTE! type is really important to stop sttribute from
   being evaluated as a string--%>
<jsp:directive.attribute name="name" required="true" />
<%-- add taglib declarations --%>

<jsp:directive.include file="/include/declareTags.ToBejsp" />

<%-- default --%>
<h1>Hello ${name}</h1>
</tr>







