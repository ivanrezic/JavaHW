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
  <body  bgcolor="#999966">
<!-- --------------------------------------------------------------- -->  
  	<h3><a href="/voting-app/index.jsp">Home</a></h3>
	<h1>Voting results</h1>
	<p>These are voting results.</p>
	<table border="1" class="rez">
		<tr>
			<th>Option</th>
			<th>Number of votes</th>
		</tr>
		<c:forEach var="option" items="${sorted}">
			<tr>
				<td>${option.optionTitle}</td>
				<td>${option.votesCount}</td>
			</tr>
		</c:forEach>
	</table>
<!-- --------------------------------------------------------------- --> 
	<h2>Graphical results chart</h2>
	<img alt="Pie-chart" src="glasanje-grafika" />
<!-- --------------------------------------------------------------- --> 
	<h2>Results in XLS format</h2>
	<p>Results in XLS format are available <a href="glasanje-xls">here</a>.</p>
<!-- --------------------------------------------------------------- --> 
	<h2>Other</h2>
	<p>Example/s of winning option/s:</p>
	<ul>
		<c:forEach var="option" items="${maxValues}">
			<li><a href="${option.optionLink}">${option.optionTitle}</a></li>
		</c:forEach>
	</ul>
</body>
</html>