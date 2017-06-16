<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<style type="text/css">	
		.logout{
			float: right;
			margin-right: 5px;
		}
	</style>
<head>
</head>
<body bgcolor="#d3d2b5">

	<c:choose>
		<c:when test="${currentUser == null}">
			Not logged in.<hr>
		</c:when>
		<c:otherwise>
			<b>Name:</b> ${currentUser.firstName} <b>Surname:</b> ${currentUser.lastName} <b>Nickname:</b> ${currentUser.nick}
			<b class="logout"><a href="/blog/servleti/main?logout">Logout</a></b>
			<hr>
		</c:otherwise>
	</c:choose>
	
	<h3>Add blog:</h3>
	
	<form action="/blog/servleti/new" method="POST">
		<input type="hidden" value="${currentUser.id }" name="userID" />
		Title: <br><input type="text" name="title" placeholder="your title.."> <br>
		Text: <br><textarea name="text" placeholder="your text.." rows="3" cols="40"></textarea><br>
		<input type="submit" value="Post">
		<input type="reset" value="Reset">
	</form>

</body>
</html>