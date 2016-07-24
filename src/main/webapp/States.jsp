<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="include/CeepHeader.inc" %>
  </head>
<%@ include file="include/CeepMenu.inc" %>
 <body bgcolor="white">
<%@ page import="com.lordjoe.voter.State" %>
<%@ page import="com.lordjoe.voter.votesmart.MyVoteSmart" %>
<%@ page import="com.lordjoe.voter.votesmart.GoogleDatabase" %>

<h1>States</h1>
<Table  border="1" left = "5%" >
    <col width="160">
    <col width="160">
    <col width="160">
    <col width="160">
      <TR>
 <%
     State[] values = State.values();
        for (int i = 0; i < values.length; i++) {
            State value = values[i];
            String rowEnd = (i > 0 && ((i % 4) == 3)) ? "</TR><TR>" : "";
    %>
            <TD><a href="/races?state=<%= value %>"><%= value %></a></TD> <%= rowEnd%>


    <% } %>
    </TR>
</Table>
<%@ include file="include/CeepFooter.inc" %>
</body>
</html>