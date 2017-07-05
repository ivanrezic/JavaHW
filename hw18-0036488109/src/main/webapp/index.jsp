<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<!-- Methods -->
	<script type="text/javascript">
	$( document ).ready(function() {
		$.get( "tagovi", function( data ) {
			novo = "";
			$.each(data, function (key, value) {
				novo += "<input class='btn btn-info ' type='button' id=" + value + " value=" + value + " onclick=prikaziThumb(this.id); return false;' /> ";
			});
			$("#button").html(novo);
		}, "json");
	});

	function rasiri(slika) {
		$.get("slikaInfo?info="+slika, function(data) {
			$("#resized").html(
					"<img class='img-rounded' src='resized?info="+data[0]+"'/> " + 
					" <div class='col-md-4 bg-danger'><p><u>" + data[1] + "</u></p>" + 
					"<p class = 'bg-success text-white center'>" + data[2] + "</p>"
					);
		}, "json");
	}
	

	function prikaziThumb(id) {
		$('#resized').html("");
		$.get( "createThumbnail?tag=" + id  , function(data) {
			novo = ""
			$.each(data, function(key, value) {
				novo += "<img class='img-thumbnail' name='"+value+"'src='thumbnail?info="+ value + "' onclick='rasiri(this.name)'/>"
			});
			$("#thumbnail").html(novo);
		}, "json");
	}
	</script>
</head>

<body style="background: black !important;">
	<div class="container">
		<div class="row" id="button"></div>
		
		<div class="row" id="thumbnail"></div>
	
		<div class="row" id="resized"></div>
	</div>
</body>
</html>