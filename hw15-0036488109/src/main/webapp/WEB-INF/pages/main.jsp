<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<style type="text/css">
		.error {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		}
		
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
			<div class="error">${error}</div>
			<form action="main" method="POST">
				Nickname: <input type="text" name="nick" placeholder="Upisi: Ivo">
				Password: <input type="password" name="password" placeholder="Upisi: sifra1">
				<input type="submit" value="Login">
				<input type="reset"	value="Reset">
			</form>
			<h4>New users register <a href="register">here</a>.</h4>
		</c:when>
		<c:otherwise>
			<b>Name:</b> ${currentUser.firstName} <b>Surname:</b> ${currentUser.lastName} <b>Nickname:</b> ${currentUser.nick}
			<b class="logout"><a href="main?logout">Logout</a></b>
			<hr>
		</c:otherwise>
	</c:choose>
	
	<h4>List of registered authors:</h4>
	<ul>
	<c:forEach var="author" items="${users}">
		<li><a href="author/${author.nick}">${author.firstName} ${author.lastName}</a></li>
	</c:forEach>	
	</ul>
	
</body>
</html>