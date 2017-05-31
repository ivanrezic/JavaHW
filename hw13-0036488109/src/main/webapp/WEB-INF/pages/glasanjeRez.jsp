<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
  	<style type="text/css">
 		table.rez td {text-align: center;}
 		table { border-collapse: separate; border-spacing: 0px; }
 	</style>
  </head>
  <body bgcolor="${pickedBgCol}">
<!-- --------------------------------------------------------------- -->  
  	<h3><a href="/webapp2">Home</a></h3>
	<h1>Voting results</h1>
	<p>These are voting results.</p>
	<table border="1" class="rez">
		<tr>
			<th>Bend</th>
			<th>Broj glasova</th>
		</tr>
		<c:forEach var="score" items="${scores}">
			<tr>
				<td>${bands[score.key - 1].name}</td>
				<td>${score.value}</td>
			</tr>
		</c:forEach>
	</table>
<!-- --------------------------------------------------------------- --> 
	<h2>Graphical results chart</h2>
	<img alt="Pie-chart" src="/webapp2/glasanje-grafika" />
<!-- --------------------------------------------------------------- --> 
	<h2>Results in XLS format</h2>
	<p>Results in XLS format are available <a href="glasanje-xls">here</a>.</p>
<!-- --------------------------------------------------------------- --> 
	<h2>Other</h2>
	<p>Song example/s of winning band/s:</p>
	<ul>
		<c:forEach var="id" items="${winning}">
			<li><a href="${bands[id - 1].song}">${bands[id - 1].name}</a></li>
		</c:forEach>
	</ul>
</body>
</html>