<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0"/>
<meta content="" name="description" />
<meta content="webthemez" name="author" />
<title>Temperature Dashboard</title>


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



<!-- <script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/fire_station_js.js"></script>
<script type="text/javascript" src="assets/jS/show_hide.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script> -->

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

		//document.body.style.zoom="60%";								
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
	padding: 0px;
}

.row {
	margin-right: 0px;
}


.table-striped>tbody>tr:nth-of-type(even) {
	background-color: #d9d4d4;
}

html, body {
  height: 100%;
  margin: 0;
}
</style>

<script type="text/javascript">
	function getTempData() {				
		$.ajax({
			url : "TempDashController",
			contentType : "application/json",
			dataType : 'json',
			data : {
				action1 : "searchTempData",	
				str: "searchTempData"
			},
			success : function(result) {
				//alert("rfid ---"+result.list);
				$('#temp_name').html(result.list[0]);
				$('#temp_class').html(result.list[3]);
				$('#temp_temp').html(result.list[1]+"&#8457;");
								
				var image=result.list[2];				
				getImgeIcon(image);
				
				
				if(result.list[1]>100){
					$('#temp_msg').html("You are <span style='color:red'>NOT ALLOWED</span> to enter!");
				}else{
					$('#temp_msg').html("You are <span style='color:#4afc00'>ALLOWED</span> to enter!");
				}
				
			}
		});
		
		
			setTimeout(getTempdata, 60000);
	}
	
	function getImgeIcon(image_path) {		
		$("#my_image1")
				.attr(
						"src",
						"http://localhost:8081/ContactLess_Management/TempDashController?getImage="
								+ image_path + "");
	}
</script>


</head>

<body onload="getTempData()">
	<div id="wrapper">

		<%@include file="/menu.jsp"%>


		<div id="page-inner" style="background-color: #6843ea">

			<center>
				<div class="row" style="margin-top: -20px;">
					<div class="col-sm-12">
						<h1 style="color: white">
							<b style="font-size:70px">APOGEE COVID MANAGEMENT SOLUTION</b> </br> <b style="font-size:60px"><u>VINAYAK INSTITUTE</u></b>
						</h1>
					</div>
				</div>
			</center>



			<div class="row" style="margin-top: 30px;" id="table_pc_view">
				<div class="col-md-4 col-sm-12 col-xs-12">
					<div class="panel panel-default"
						style="background-color: #aeb7b8; border: none">
						<div class="panel-body easypiechart-panel" style="height: 400px;">
							<div data-percent="100">
								<img id="my_image1" alt="image" src="images/unnamed.jpg"
									style="width: 100%; height: 400px;" />
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-8" style="margin-top: 60px;">

					<table class="table table-striped table-bordered table-hover" id=""
						style="margin-bottom: 5px; height: 275px; text-align: center; font-size: 22px">
						<tbody>
							<tr>
								<td style="width: 40%; font-size:55px"><b>Name</b></td>
								<td id="temp_name" style="font-size:60px">abcd</td>
							</tr>

							<tr>
								<td style="font-size:55px"><b>Class</b></td>
								<td id="temp_class" style="font-size:60px">xyz</td>
							</tr>

							<tr>
								<td style="font-size:55px"><b>Temperature</b></td>
								<td id="temp_temp" style="font-size:60px">98</td>
							</tr>
					</table>

				</div>

			</div>
			<div class="row" id="temp_details_pc_view" style="margin-top: 15px;">
				<div class="col-lg-12">
					<center>
						<h1 id="temp_msg" style="color: white; font-family: initial; font-size:70px">You Are
							"Allowed" To Enter</h1>
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
	</div>
	<!-- /. WRAPPER  -->
	<!-- JS Scripts-->
	<!-- jQuery Js -->
	<!-- <script src="assets/js/jquery-1.10.2.js"></script> -->

	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/jquery.metisMenu.js"></script>

	<script src="assets/js/dataTables/jquery.dataTables.js"></script>
	<script src="assets/js/dataTables/dataTables.bootstrap.js"></script>

	<script src="assets/js/easypiechart.js"></script>
	<script src="assets/js/easypiechart-data.js"></script>

	<script>
		$(document).ready(function() {
			/* $('#dataTables-example').dataTable();
			"pageLength": 25 */
			$('#dataTables-example').dataTable({
				"autoWidth" : true,
				//"lengthChange": false,
				"pageLength" : 5,

			});
		});
	</script>
	<!-- Custom Js -->
	<script src="assets/js/custom-scripts.js"></script>

</body>

</html>
