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
<title>Live Dashboard</title>

<link rel="shortcut icon" type="image/x-icon" href="http://demo.archiwp.com/light-version/wp-content/themes/archi/images/favicon.png">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<!-- <script src="//code.jquery.com/jquery-1.10.2.js"></script> -->
<!-- <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script> -->

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
<script src="assets/js/jquery-1.10.2.js"></script>

<script type="text/javascript">
	function zoom() {
		document.body.style.zoom = "90%"
	}
</script>

<style>

.number small{
font-size:20px;
color:#299BE3
}

.board .panel .icon .fa{
padding:40px	;
font-size:65px
}
.number h3{
font-size:60px
}

</style>

</head>
<body onload="zoom();" style="background-color: rgb(56, 165, 238, 0.5);">
	<!-- <div id="wrapper"> -->

	<%@include file="/menu.jsp"%>
	<div id="page-inner" style="background-color: rgb(56, 165, 238, 0.02);">
		<center>
			<div class="row">
				<div class="col-sm-12">
					<h2 style="color: white;font-size:65px">
						<b>${login_office } Live Dashboard</b>
					</h2>
				</div>
			</div>
		
		
		<div class="row" style="margin-top:25px"> 
					<div class="col-md-3 col-sm-12 col-xs-12">					
						<div class="board" onclick="showData('total_teacher');">
						<!-- <input type="checkbox" /> -->
							<div class="panel panel-primary all_data">
								<div class="number">
									<h3>
										<h3 id="total_teacher">10</h3>
										<small>Total Teacher</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-chalkboard-teacher fa-5x blue"></i>
								</div>

							</div>
						</div>
					</div>

					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board" onclick="showData('present_teacher');">
							<div class="panel panel-primary all_data">
								<div class="number">
									<h3>
										<h3 id="present_teacher">36</h3>
										<small>Present Teacher</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-check fa-5x green"></i>
								</div>

							</div>
						</div>
					</div>

					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board" onclick="showData('absent_teacher');">
							<div class="panel panel-primary all_data">
								<div class="number">
									<h3>
										<h3 id="absent_teacher">30</h3>
										<small>Absent Teacher</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-ban fa-5x red"></i>
								</div>

							</div>
						</div>
					</div>

					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board" onclick="showData('total_student');">
							<div class="panel panel-primary all_data">
								<div class="number">
									<h3>
										<h3 id="total_student">10</h3>
										<small>Total Student</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-graduation-cap fa-5x blue"></i>
								</div>

							</div>
						</div>
					</div>

				</div>
				
				
						<div class="row" style="margin-top:50px"> 
					<div class="col-md-3 col-sm-12 col-xs-12">					
						<div class="board" onclick="showData('present_student');">
						<!-- <input type="checkbox" /> -->
							<div class="panel panel-primary all_data">
								<div class="number">
									<h3>
										<h3 id="present_student">9073</h3>
										<small>Present Student</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-check fa-5x green"></i>
								</div>

							</div>
						</div>
					</div>

					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board" onclick="showData('absent_student');">
							<div class="panel panel-primary all_data">
								<div class="number">
									<h3>
										<h3 id="absent_student">36948</h3>
										<small>Absent Student</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-ban fa-5x red"></i>
								</div>

							</div>
						</div>
					</div>

					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board" onclick="showData('total_vehicles');">
							<div class="panel panel-primary all_data">
								<div class="number">
									<h3>
										<h3 id="total_vehicles">30</h3>
										<small>Total Vehicles</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-car fa-5x blue"></i>
								</div>

							</div>
						</div>
					</div>

					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="board" onclick="showData('active_vehicles');">
							<div class="panel panel-primary all_data">
								<div class="number">
									<h3>
										<h3 id="active_vehicles">10</h3>
										<small>Active Vehicles</small>
									</h3>
								</div>
								<div class="icon">
									<i class="fa fa-check fa-5x green"></i>
								</div>

							</div>
						</div>
					</div>

				</div>


               <div class="row" id="div_map_view" style="display:none">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<div id="map" style="width: 100%; height: 700px;"></div>
					</div>
				</div>


</center>
		
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
	<!-- <script src="assets/js/dataTables/jquery.dataTables.js"></script>
	<script src="assets/js/dataTables/dataTables.bootstrap.js"></script>

	<script src="assets/js/easypiechart.js"></script>
	<script src="assets/js/easypiechart-data.js"></script> -->

		<!-- Custom Js -->
	<!-- <script src="assets/js/custom-scripts.js"></script> -->
	
<script type="text/javascript" src="JS/live_dash.js"></script>
	
</body>
</html>
