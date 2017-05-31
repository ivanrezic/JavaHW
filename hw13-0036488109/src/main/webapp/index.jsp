<!DOCTYPE html>
<html>
  <head>
  	<meta charset = "utf-8">
  </head>
  <body bgcolor="${pickedBgCol}">
  	<p>Background color
  	<a href="public/colors.jsp">chooser</a>.
	</p>
	<hr>
	<form action="trigonometric" method="GET">
		 Starting angle:<input type="number" name="a" min="0" max="360" step="1" value="0">
		 Closing angle:<input type="number" name="b" min="0" max="360" step="1" value="360">
		 <input type="submit" value="Submit"><input type="reset" value="Reset">
	</form>
	<hr>
	<p><a href="public/stories/funny.jsp">Funny story</a></p>
	<hr>
	<p><a href="public/report.jsp">OS usage report</a></p>
	
  </body>
</html>