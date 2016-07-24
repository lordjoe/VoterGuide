
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>
<H1>THIS IS GUESTBOOK </H1>

<form action="/sign" method="post">
    <div><textarea name="content" rows="3" cols="60"></textarea></div>
    <div><input type="submit" value="Post Greeting"/></div>
    <input type="hidden" name="guestbookName" value=" "/>
</form>

<form action="/guestbook.jsp" method="get">
    <div><input type="text" name="guestbookName" value=" "/></div>
    <div><input type="submit" value="Switch Guestbook"/></div>
</form>

</body>
</html>
