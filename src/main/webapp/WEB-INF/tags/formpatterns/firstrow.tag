<%--
  This creates a set row of headers for a list
  labels - array or list of keys - if key not found will use the text
  style - if  sent in will set the style
  author Steve Lewis
--%>

<%-- Declare a taglib - the xml version is real messy --%>
<%@ tag body-content="empty" %>
<%-- NOTE! type is really important to stop sttribute from
   being evaluated as a string--%>
<jsp:directive.attribute name="labels" required="true" type="java.lang.Object" />
<jsp:directive.attribute name="style" required="false" />
<%-- add taglib declarations --%>

<jsp:directive.include file="/include/declareTags.ToBejsp" />

<%-- default --%>
<c:set var="styleString" value="bgcolor='green'" />
<c:if test="${!(empty style)}" >
    <c:set var="styleString" value="style='${style}'" />
</c:if>

<tr ${styleString} >
    <c:forEach var="label" items="${labels}" >
         <td>${label}</td>
    </c:forEach>
</tr>







