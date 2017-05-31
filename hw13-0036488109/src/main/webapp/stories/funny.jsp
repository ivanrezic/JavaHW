<%@ page import="java.util.Random" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%! 
private void getColor(javax.servlet.jsp.JspWriter out, Random random) throws java.io.IOException {
	String[] colors = new String[]{" #ff00ff", " #ff8c1a"," #809fff", " #4d0000"};
	out.print(colors[random.nextInt(4)]);
}
%>

<!DOCTYPE html>
<html>
  <head>
  </head>
  <body bgcolor="${pickedBgCol}">
  	<h3><a href="/webapp2">Home</a></h3>
  	<h3>Funny story</h3>
	<p><font color="<% getColor(out, new Random()); %>>">
		A curious child asked his mother: “Mommy, why are some of your hairs turning grey?”
		The mother tried to use this occasion to teach her child: “It is because of you, dear. Every bad action of yours will turn one of my hairs grey!”
		The child replied innocently: “Now I know why grandmother has only grey hairs on her head.”
	</font></p>
  </body>
</html>