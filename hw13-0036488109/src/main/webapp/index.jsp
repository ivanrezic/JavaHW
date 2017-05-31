<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
  </head>
  <body bgcolor="${pickedBgCol}">
  	<p>Background color
  	<a href="colors.jsp">chooser</a>.
	</p>
	<form action="trigonometric" method="GET">
		 Starting angle:<input type="number" name="a" min="0" max="360" step="1" value="0">
		 Closing angle:<input type="number" name="b" min="0" max="360" step="1" value="360">
		 <input type="submit" value="Submit"><input type="reset" value="Reset">
	</form>
	<p><a href="stories/funny.jsp">Funny story</a></p>
	<p><a href="report.jsp">OS usage report</a></p>
	<form action="powers" method="GET">
		 First number:<input name="a" value="1">
		 Last number:<input name="b" value="100">
		 Number of sheets:<input name="n" value="3">
		 <input type="submit" value="Submit"><input type="reset" value="Reset">
	</form>
	<p><a href="appinfo.jsp">App runtime</a></p>
	<p><a href="glasanje">Favorite band voting</a></p>
  </body>
</html>