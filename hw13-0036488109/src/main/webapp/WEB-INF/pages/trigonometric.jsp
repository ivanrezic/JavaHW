<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
  </head>
  <body bgcolor="${pickedBgCol}">
  	<h3><a href=".">Home</a></h3>
  	<h3>Trigonometric results:</h3>
	<table border="1">
		<tr>
			<th>Number</th>
			<th>Sinus</th>
			<th>Cosinus</th>
		</tr>
		<c:forEach var="result" items="${results}">
			<tr>
				<td>${result.number}</td>
				<td>${result.sinus}</td>
				<td>${result.cosinus}</td>
			</tr>
		</c:forEach>
	</table>
  </body>
</html>
