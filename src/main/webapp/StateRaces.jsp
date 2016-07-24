<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="include/CeepHeader.inc" %>
  </head>
<%@ include file="include/CeepMenu.inc" %>

<body bgcolor="white">
<%@ page import="com.lordjoe.voter.*" %>

<% String state =  (String) request.getParameter ("state"); %>
<h1><%= state %></h1>
<Table  border="1" left = "5%" >
    <col width="160">
    <col width="160">
    <col width="160">
    <col width="160">

 <%
        State stateValue = State.fromString(state);
        for (Race race : Races.getRace(stateValue)) {

    %>
    <TR><TD> <%= race.district %></TD> </TR>

    <%
           for (Candidate c : race.getCandidates()) {
               Politician pol = c.pol;
     %>
    <TR><TD> <%=  pol %></TD> </TR>
    <% } %>
    <% } %>
    </TR>
</Table>
<%@ include file="include/CeepFooter.inc" %>
</body>
</html>