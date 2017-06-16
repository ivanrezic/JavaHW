<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<style type="text/css">	
		.logout{
			float: right;
			margin-right: 5px;
			padding-right: 5px;
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
	
	<h4>Blogs by selected author.</h4>
	
	<ul>
	<c:forEach var="blog" items="${blogs}">
		<li><a href="${selectedUser}/${blog.id}">${blog.title}</a></li>
	</c:forEach>	
	</ul>
	
	<br>
	
	<c:choose>
		<c:when test="${currentUser.nick == selectedUser}">
			<a href="${currentUser.nick}/new">Add new blog</a>
		</c:when>
	</c:choose>
	
</body>
</html>