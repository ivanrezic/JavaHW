<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
	  <style>
		  th,td {
			  text-align: center;
			}
	  </style>
  </head>
  <body bgcolor="#999966">
  	<h1>Available polls</h1>

	<table border="1">
		<tr>
			<th>Title</th>
			<th>Message</th>
			<th>Click to vote!</th>
		</tr>
		<c:forEach var="poll" items="${polls}">
			<tr>
				<td>${poll.title}</td>
				<td>${poll.message}</td>
				<td><a href="glasanje?pollID=${poll.id}">Vote</a></td>
			</tr>
		</c:forEach>
	</table>
  </body>
</html>