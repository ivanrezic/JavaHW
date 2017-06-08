<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
  </head>
  <body bgcolor="#999966">
  	<h3><a href="/voting-app/index.jsp">Home</a></h3>
	<h1>Voting page</h1>
	<p>
		Of the following choices, which one is your favorite? Click on the link to vote!
	</p>	
	<ol>
		<c:forEach var="option" items="${options}">
			<li><a href="glasanje-glasaj?id=${option.id}">${option.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>
</html>