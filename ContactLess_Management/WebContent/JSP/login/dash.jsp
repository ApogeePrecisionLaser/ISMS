<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta content="" name="description" />
<meta content="webthemez" name="author" />
<title>Home</title>

<link rel="shortcut icon" type="image/x-icon" href="http://demo.archiwp.com/light-version/wp-content/themes/archi/images/favicon.png">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<link href="assets/css/bootstrap.css" rel="stylesheet" />
<link href="assets/css/font-awesome.css" rel="stylesheet" />

<!-- <link href="assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" /> -->

<link href="assets/css/custom-styles.css" rel="stylesheet" />

<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="assets/js/Lightweight-Chart/cssCharts.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
p, div, input {
	font: 16px Calibri;
}

.ui-autocomplete {
	cursor: pointer;
	height: 120px;
	width: 150px;
	overflow-y: scroll;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$('#nav li').hover(function() {
			//show submenu
			$('ul', this).show();
		}, function() {
			//hide submenu
			$('ul', this).hide();
		});

		function zoom() {alert("dsajg");
			document.body.style.zoom = "85%"
		}								

	});
</script>

<style>
.all_data {
	height: 100px;
	width: 200px;
}

.list {
	background: #138b85;
}

#DdataTables-example_filter {
	margin-left: 90px;
}

.circle {
	width: 80px;
	height: 80px;
	border-radius: 50%;
	font-size: 30px;
	color: white;
	line-height: 80px;
	text-align: center;
	background: #1a3aea
}

.textColor {
	color: blue;
	font-weight: bold;
	padding: 0px;
}

.selected_row {
	font-weight: bolder;
	color: blue;
	border: 3px solid black;
}

.table tbody tr td {
	padding: 4px;
}

.row {
	margin-right: 0px;
}

.container {
	clear: both;
	background-color: white;
	width: auto;
}

nav {
	float: right;
}

.logo img {
	float: left;
}

ul li {
	display: inline-block;
	padding: 10px;
	font-family: raleway;
}

ul li:hover {
	color: orange;
}

/* #nav {
  margin: 0;
  padding: 0;
  list-style: none;
} */
#nav li {
	float: left;
	display: block;
	position: relative;
	z-index: 500;
	margin: 0 1px;
}

#nav li a {
	display: block;
	height: 20px;
	text-decoration: none;
	color: black;
	text-align: center;
}

#nav ul {
	position: absolute;
	left: 0;
	display: none;
	margin: 0 0 0 -1px;
	padding: 0;
	list-style: none;
}

#nav ul li {
	float: left;
	border-top: 1px solid #fff;
	width: 100%;
	white-space: nowrap;
}

#nav ul a {
	display: block;
	height: 20px;
	padding: 0px 5px;
	color: black;
	text-align: left;
}

#nav a:hover {
	/* text-decoration: none; */
	color: orange;
}

.fontSize {
	font-size: 20px;
}

.table-striped>tbody>tr:nth-of-type(even) {
	background-color: #d9d4d4;
}
</style>

</head>

<body onload="zoom()" style="background:#aeb7b8">
	<div id="wrapper">

		<%-- <%@include file="/menu.jsp" %> --%>

		<!-- /. NAV SIDE  -->


		<div id="page-inner">

<div class="row">
<div class="col-md-6">
			<center>
			<a href="http://120.138.10.146:8080/isms/index">
				<img src="images/school2.jpg" width="500"
					height="420" style="margin-top: 20px;">
					
					<h2 style="background:yellow;border-radius:30px;width:500px">School Data Creation</h2></a>
			</center>
</div>
<div class="col-md-6">
			<center>
			 <a href="http://localhost:8081/ContactLess_Management/VisitTempDetailsController">
				<img src="images/school_temp.jpg" width="500"
					height="420" style="margin-top: 20px;">
					
					<h2 style="background:yellow;border-radius:30px;width:500px">School Temperature Data Visualization</h2></a>
	
			
			 </center>
			
			
</div>
</div>

			<footer style="margin-top:80px;">
			<p style="color: black">
				All right reserved. Template by: <a style="color: white"
					href="http://apogeeprecision.com" target="_blank">Apogee
					Precision LLP</a>
			</p>


			</footer>
		</div>
		<!-- /. PAGE INNER  -->

		<!-- /. PAGE WRAPPER  -->
	</div>
	<!-- /. WRAPPER  -->
	<!-- JS Scripts-->
	<!-- jQuery Js -->
	<!-- <script src="assets/js/jquery-1.10.2.js"></script> -->

	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/jquery.metisMenu.js"></script>
	<!-- Custom Js -->
	<script src="assets/js/custom-scripts.js"></script>

</body>

</html>
