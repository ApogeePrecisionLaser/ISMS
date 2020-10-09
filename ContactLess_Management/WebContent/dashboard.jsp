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
<title>Dashboard</title>

<link rel="shortcut icon" type="image/x-icon" href="http://demo.archiwp.com/light-version/wp-content/themes/archi/images/favicon.png">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<!-- Bootstrap Styles-->
<link href="assets/css/bootstrap.css" rel="stylesheet" />
<!-- FontAwesome Styles-->
<link href="assets/css/font-awesome.css" rel="stylesheet" />
<!-- Custom Styles-->
<link href="assets/css/custom-styles.css" rel="stylesheet" />
<!-- Google Fonts-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="assets/js/Lightweight-Chart/cssCharts.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- <script src='https://kit.fontawesome.com/a076d05399.js'></script> -->
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
<!-- <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script> -->

<!-- <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script> -->
<!-- <script src="assets/js/jquery-1.10.2.js"></script> -->

<script type="text/javascript">
	function zoom() {
		document.body.style.zoom = "90%"
	}
</script>

<style>
.ui-autocomplete {
	cursor: pointer;
	height: 120px;
	width: 150px;
	overflow-y: scroll;
}

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

.table-striped>tbody>tr:nth-of-type(even) {
	background-color: #d9d4d4;
}

label {
	margin-bottom: 0px;
}

.col-md-10 {
	width: 82%;
}
</style>

</head>
<body onload="zoom();" style="background-color: rgb(56, 165, 238, 0.5);">
	<!-- <div id="wrapper"> -->

	<%@include file="/menu.jsp"%>
	<div id="page-inner" style="background-color: rgb(56, 165, 238, 0.02);">
		<center>
			<div class="row" style="margin-top: -20px;">
				<div class="col-sm-12">
					<h2 style="color: white">
						<b>${login_office } Dashboard</b>
					</h2>
				</div>
			</div>
		</center>

		<div class="row">
			<div class="col-md-2">
				<center>
					<a href="LoginController"> <img src="images/school3.jpg"
						width="250" height="250" style="margin-top: 20px;">

						<h2 style="background: yellow; border-radius: 30px; width: 250px">Home</h2></a>
				</center>
			</div>
			<div class="col-md-2">
				<center>

					<a href="AttendanceController"> <img src="images/attendance2.jpg"
						width="250" height="250" style="margin-top: 20px;">

						<h2 style="background: yellow; border-radius: 30px; width: 250px">Attendance</h2></a>

				</center>


			</div>

			<div class="col-md-2">
				<center>
					<a href="live_dashboard.jsp"> <img
						src="images/live_dash2.jpg" width="250" height="250"
						style="margin-top: 20px;">

						<h2 style="background: yellow; border-radius: 30px; width: 250px">Live Dashboard</h2></a>
				</center>
			</div>

			<div class="col-md-2">
				<center>
					<a href="DeviceInfoRegController"> <img
						src="images/device_info.jpg" width="250" height="250"
						style="margin-top: 20px;">

						<h2 style="background: yellow; border-radius: 30px; width: 250px">Device
							Info</h2></a>
				</center>
			</div>


			<div class="col-md-2">
				<center>
					<a href="DeviceRegController"> <img src="images/RFID.jpg"
						width="250" height="250" style="margin-top: 20px;">

						<h2 style="background: yellow; border-radius: 30px; width: 250px">RFID
							Allocation</h2></a>
				</center>
			</div>


			<div class="col-md-2">
				<center>
					<a href="TempDashController"> <img src="images/status.jpg"
						width="250" height="250" style="margin-top: 20px;">

						<h2 style="background: yellow; border-radius: 30px; width: 250px">Status</h2></a>
				</center>
			</div>

		</div>

		<div class="row">
			<div class="col-md-2">
				<center>
					<a href="JSP/policies/read_policies.jsp" target="_blank"> <img src="images/policies.jpg" width="250"
						height="250" style="margin-top: 20px;">

						<h2 style="background: yellow; border-radius: 30px; width: 250px">Policies</h2></a>
				</center>
			</div>


			<div class="col-md-2">
				<center>
					<a href="http://apogeeprecision.com/about-us/" target="_blank"> <img src="images/about.jpg" width="250"
						height="250" style="margin-top: 20px;">

						<h2 style="background: yellow; border-radius: 30px; width: 250px">About
						</h2></a>
				</center>
			</div>

		</div>
		<footer>
		<p style="color: black">
			All right reserved. Template by: <a style="color: white"
				href="http://apogeeprecision.com" target="_blank">Apogee
				Precision LLP</a>
		</p>


		</footer>


	</div>
	<!-- /. PAGE INNER  -->

	<!-- /. PAGE WRAPPER  -->
	<!-- </div> -->
	<!-- /. WRAPPER  -->

	<!-- Bootstrap Js -->
	<!-- <script src="assets/js/bootstrap.min.js"></script> -->
	<!-- Metis Menu Js -->
	<script src="assets/js/jquery.metisMenu.js"></script>
	<!-- DATA TABLE SCRIPTS -->
	<script src="assets/js/dataTables/jquery.dataTables.js"></script>
	<script src="assets/js/dataTables/dataTables.bootstrap.js"></script>

	<script src="assets/js/easypiechart.js"></script>
	<script src="assets/js/easypiechart-data.js"></script>

	<script>
		$(document).ready(function() {
			/* $('#dataTables-example').dataTable();
			"pageLength": 25 */
			$('#dataTables-example').dataTable({
				//"autoWidth": false,
				//"lengthChange": false,
				"pageLength" : 5,

			});
			$('#dataTables_mob_view').dataTable({
				//"autoWidth": false,
				//"lengthChange": false,
				"pageLength" : 1
			});
		});
	</script>
	<!-- Custom Js -->
	<script src="assets/js/custom-scripts.js"></script>
</body>
</html>
