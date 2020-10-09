<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="assets/css/bootstrap.css" rel="stylesheet" />
<link href="assets/css/custom-styles.css" rel="stylesheet" />
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">


<style>
nav {
	margin: 27px auto 0;
	position: relative;
	width: 57.25%;
	height: 50px;
	background-color: #55777e73;
	border-radius: 8px;
	font-size: 0;
}

nav a {
	line-height: 50px;
	height: 100%;
	font-size: 15px;
	display: inline-block;
	position: relative;
	z-index: 1;
	text-decoration: none;
	text-transform: uppercase;
	text-align: center;
	color: white;
	cursor: pointer;
}

nav .animation {
	position: absolute;
	height: 100%;
	top: 0;
	z-index: 0;
	transition: all .5s ease 0s;
	border-radius: 8px;
}

nav a:nth-child(1) {
	width: 100px;	
}

a:nth-child(2) {
	width: 200px;	
}

a:nth-child(3) {
	width: 180px;
}

a:nth-child(4) {
	width: 160px;
	background-color: cyan;
}

a:nth-child(5) {
	width: 120px;
}

a:nth-child(6) {
	width: 100px;
}


/* nav .start-home, a:nth-child(1):hover ~.animation {
	width: 100px;
	left: 0;
	background-color: #1abc9c;
}

nav .start-about, a:nth-child(2):hover ~.animation {
	width: 200px;
	left: 100px;
	background-color: #e74c3c;
}

nav .start-blog, a:nth-child(3):hover ~.animation {
	width: 180px;
	left: 300px;
	background-color: #3498db;
}

nav .start-portefolio, a:nth-child(4):hover ~.animation {
	width: 160px;
	left: 480px;
	background-color: #9b59b6;
}

nav .start-contact, a:nth-child(5):hover ~.animation {
	width: 120px;
	left: 640px;
	background-color: #e67e22;
}

nav .start-logout, a:nth-child(6):hover ~.animation {
	width: 100px;
	left: 760px;
	background-color: #1abc9c;
} */


body {
	font-size: 12px;
	font-family: sans-serif;
	background: #aeb7b8;
}

h1 {
	text-align: center;
	margin: 40px 0 40px;
	text-align: center;
	font-size: 50px;
	color: #fff;
	text-shadow: 2px 2px 4px #000000;
	font-family: 'Cherry Swash',;
}

p {
	position: absolute;
	width: 100%;
	text-align: center;
	color: black;
	font-family: 'Cherry Swash',;
	font-size: 16px;
}

span {
	color: white;
}
</style>

<title>JSmart Mobile App Policies</title>
</head>
<body>
	<h1>Contact-Less Management System</h1>

	<div>
		<nav> <a
			href="LoginController">Home</a>
		<a href="VisitAttendController">Visitor's Attendance</a> <a href="VisitDetailsController">Visitor's
			Details</a> <a href="PoliciesController">App Policies</a> <a href="#">Contact</a> <a href="#">Logout</a>
		<div class="animation start-home"></div>
		</nav>
	</div>

	<div>
	
	</div>

	<footer>
	<p>
		All rights reserved. Template by: <a href="http://apogeeprecision.com"
			style="color: white">Apogee Precision LLP</a>
	</p>
	</footer>

	<!-- jQuery Js -->
	<script src="assets/js/jquery-1.10.2.js"></script>
	<!-- Bootstrap Js -->
	<script src="assets/js/bootstrap.min.js"></script>
	<!-- DATA TABLE SCRIPTS -->
	<script src="assets/js/dataTables/jquery.dataTables.js"></script>
	<script src="assets/js/dataTables/dataTables.bootstrap.js"></script>

	<script>
		$(document).ready(function() {
			$('#dataTables-example').dataTable();
		});
	</script>

</body>
</html>
