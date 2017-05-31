<%@page import="java.text.DateFormat"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%!private void zapisiDatum(javax.servlet.jsp.JspWriter out, long startTime) throws java.io.IOException {
		long now = System.currentTimeMillis();

		long timePassed = now - startTime;
		long sec = (timePassed / 1000) % 60;
		long min = (timePassed / (1000 * 60)) % 60;
		long hrs = (timePassed / (1000 * 60 * 60)) % 24;
		long days = (timePassed / (1000 * 60 * 60 * 24)) % 365;
		long milis = (timePassed % 1000);

		out.print(String.format("%d days, %d hours, %d minutes, %d seconds and %d miliseconds.", days, hrs, min, sec,
				milis));
	}%>

<!DOCTYPE html>
<html>
  <head>
  </head>
  <body bgcolor="${pickedBgCol}">
  	<h3><a href="/webapp2">Home</a></h3>
  	<h3>This app has been running for:</h3>
	<h2><% zapisiDatum(out, (long)application.getAttribute("startTime")); %></h2>
</body>
</html>