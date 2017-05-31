<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
  <head>
  </head>
  <body bgcolor="${pickedBgCol}">
  	<h3><a href="/webapp2">Home</a></h3>
  	<h3>Pick background color</h3>
	<ul>
	  <li> <a href="/webapp2/setcolor?color=white">WHITE</a></li>
	  <li> <a href="/webapp2/setcolor?color=red">RED</a></li>
	  <li> <a href="/webapp2/setcolor?color=green">GREEN</a></li>
	  <li> <a href="/webapp2/setcolor?color=cyan">CYAN</a></li>
	</ul>
  </body>
</html>


