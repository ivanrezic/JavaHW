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
		</c:when>
		<c:otherwise>
			<b>Name:</b> ${currentUser.firstName} <b>Surname:</b> ${currentUser.lastName} <b>Nickname:</b> ${currentUser.nick}
			<b class="logout"><a href="main?logout">Logout</a></b>
			<hr>
		</c:otherwise>
	</c:choose>
	
	<form action="register" method="POST">
		First name:
		<br> <input type="text" name="fname" value="${error.firstName}"><br>
		Last name:
		<br> <input type="text" name="lname" value="${error.lastName}"><br>
		Email:
		<br><input type="email" name="email" value="${error.email}"><br>
		Nickname:
		<br><input type="text" name="nick" value="${error.nick}"><br>
		Password:
		<br><input type="password" name="password" value="${error.password}"><br>
		<div class="error">${error.errorMsg}</div>
		<br>
		<input type="submit" value="Register">
		<input type="reset"	value="Reset">
	</form>
	
</body>
</html>