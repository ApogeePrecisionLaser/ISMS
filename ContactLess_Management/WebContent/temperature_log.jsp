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
<title>Temperature Log</title>

<link rel="shortcut icon" type="image/x-icon" href="http://demo.archiwp.com/light-version/wp-content/themes/archi/images/favicon.png">
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

			setTimeout(function(){
				   window.location.reload(1);
				}, 5000);
	}
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

#my_image{
transform: rotate(90deg);
}
</style>

<script>
	function filterTablee(row_class) {
		if (row_class == 'Low') {
			$('.slight_row').hide();
			$('.normal_row').hide();
			$('.high_row').hide();
		}
	}

	function getImgeIcon(image_path) {
		//alert("image [athhh -" + image_path);
		//$("#my_image").attr("src", "images/person_logo.jpg");
		//http://localhost:8081/ContactLess_Management/VisitTempDetailsController?getImage='NewImage111'
		$("#my_image")
				.attr(
						"src",
						"http://localhost:8081/ContactLess_Management/VisitTempDetailsController?getImage="
								+ image_path + "");

	}

	$(document).ready(
			function() {
				//var table = $('#dataTables-example').DataTable();

				$('#dataTables-example tbody').on(
						'click',
						'tr',
						function() {
							if ($(this).hasClass('selected_row')) {
								$(this).removeClass('selected_row');
							} else {
								$("#dataTables-example").DataTable().$(
										'tr.selected_row').removeClass(
										'selected_row');
								$(this).addClass('selected_row');
							}
						});
			});
</script>


</head>
<body onload="zoom();" style="background-color: rgb(56, 165, 238, 0.5);">
	<!-- <div id="wrapper"> -->

	<%@include file="/menu.jsp"%>
	<div id="page-inner" style="background-color: rgb(56, 165, 238, 0.02);">
		<center>
			<div class="row" style="margin-top: -20px;">
				<div class="col-sm-12">
					<h2 style="color: white">
						<b>${login_office } WALL MOUNT INFRARED THERMOMETER</b>
					</h2>
				</div>
			</div>
		</center>

		<c:if test="${parent == 'singleStudent' }">
			<center>
				<div class="row" style="margin-top: 10px;" id="table_pc_view">
					<label>Roll No. </label> <input type="text" id="roll_no"
						name="roll_no">
					<button id="btn_roll_no" name="btn_roll_no">Submit</button>
				</div>
			</center>
		</c:if>

		<div class="row" style="margin-top: 8px;" id="table_pc_view">
			<div class="col-md-10">
				<!-- Advanced Tables -->
				<div class="panel panel-default">
					<div class="panel-heading">Temperature Table</div>
					<div class="panel-body" style="padding: 0px;">
						<div class="table-responsive">
							<table class="table table-striped table-bordered table-hover"
								id="dataTables-example" style="margin-bottom: 5px;">
								<thead>
									<tr>
										<th>S.No</th>
										<th>Employee Name</th>
										<th>Temperature</th>
										<th>Status</th>
										<th>Time</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="tempModel" items="${requestScope['list']}"
										varStatus="loopCounter">
										<c:if test="${tempModel.status == 'Slight Fever'}">
											<tr data-user="slightR" class="slight_row"
												onclick="getImgeIcon('${tempModel.image_name}');"
												style="background-color: #EAD421">
										</c:if>
										<c:if test="${tempModel.status == 'High Fever'}">
											<tr data-user="highR" class="high_row"
												onclick="getImgeIcon('${tempModel.image_name}');"
												style="background-color: red">
										</c:if>
										<c:if test="${tempModel.status == 'Low'}">
											<tr data-user="lowR" class="low_row"
												onclick="getImgeIcon('${tempModel.image_name}');"
												style="background-color: pink">
										</c:if>
										<c:if test="${tempModel.status == 'Normal'}">
											<tr data-user="normalR" class="normal_row"
												onclick="getImgeIcon('${tempModel.image_name}');"
												style="background-color: #49EE72CF">
										</c:if>
										<td>${loopCounter.count }</td>
										<td>${tempModel.emp_name}</td>
										<td>${tempModel.temperature}</td>
										<td>${tempModel.status}</td>
										<td>${tempModel.date}</td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div>
				</div>
				<!--End Advanced Tables -->
			</div>

			<div class="col-md-2 col-sm-12 col-xs-12">
				<div class="panel panel-default"
					style="margin-top: 94px; background-color: #aeb7b8; border: none">
					<div class="panel-body easypiechart-panel">
						<div data-percent="100">
							<img id="my_image" alt="image" src="" width=215px; height=220px; />
						</div>
					</div>
				</div>
			</div>

		</div>
		<%-- <div class="row" id="temp_details_pc_view">
				<div class="col-lg-10">
					<center>
						<h3 style="color: white; font-family: initial">TEMPERATURE
							DETAILS</h3>
					</center>
				</div>
			</div> --%>


		<div class="row" style="margin-top: 0px" id="icons_pc_view">
			<div class="col-md-2 col-sm-12 col-xs-12"
				onclick="filterTable('total_person');">
				<div class="panel panel-default"
					style="background-color: #ECECEC; height: 243px;">
					<div class="panel-body easypiechart-panel">
						<h4 class="textColor">Total No.Of Person</h4>
						<div data-percent="100">
							<center>
								<div class="circle">${total}</div>
							</center>
						</div>
						<div style="margin-top: 10px">
							<img src="images/person_logo.jpg" width=100px; height=100px; />
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2 col-sm-12 col-xs-12"
				onclick="filterTable('Low');">
				<div class="panel panel-default"
					style="background-color: #EAC2E2; height: 243px;">
					<div class="panel-body easypiechart-panel">
						<h4 class="textColor">Total Low</h4>
						<div data-percent="100">
							<center>
								<div class="circle">${low}</div>
							</center>
						</div>
						<div style="margin-top: 10px">
							<img src="images/lo_hi.jpg" width=100px; height=100px; />
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2 col-sm-12 col-xs-12"
				onclick="filterTable('Normal');">
				<div class="panel panel-default"
					style="background-color: #49EE72CF; height: 243px;">
					<div class="panel-body easypiechart-panel">
						<h4 class="textColor">Total Normal Temp</h4>
						<div data-percent="100">
							<center>
								<div class="circle">${normal}</div>
							</center>
						</div>
						<div style="margin-top: 10px">
							<img src="images/normal.jpg" width=100px; height=100px; />
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2 col-sm-12 col-xs-12"
				onclick="filterTable('Slight');">
				<div class="panel panel-default"
					style="background-color: #EAD421; height: 243px;">
					<div class="panel-body easypiechart-panel">
						<h4 class="textColor">Total Slight Fever</h4>
						<div data-percent="100">
							<center>
								<div class="circle">${slight}</div>
							</center>
						</div>
						<div style="margin-top: 10px">
							<img src="images/slight.jpg" width=100px; height=100px; />
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-2 col-sm-12 col-xs-12"
				onclick="filterTable('High');">
				<div class="panel panel-default"
					style="background-color: red; height: 243px;">
					<div class="panel-body easypiechart-panel">
						<h4 class="textColor">Total High Fever</h4>
						<div data-percent="100">
							<center>
								<div class="circle">${high}</div>
							</center>
						</div>
						<div style="margin-top: 10px">
							<img src="images/high.jpg" width=100px; height=100px; />
						</div>
					</div>
				</div>
			</div>

		</div>

		<!-- <footer>
			<p style="color: black">
				All right reserved. Template by: <a style="color: white"
					href="http://apogeeprecision.com" target="_blank">Apogee
					Precision LLP</a>
			</p>


			</footer> -->
	</div>
	<!-- /. PAGE INNER  -->

	<!-- /. PAGE WRAPPER  -->
	<!-- </div> -->
	<!-- /. WRAPPER  -->

	<footer>
	<p style="color: black">
		All right reserved. Template by: <a style="color: white"
			href="http://apogeeprecision.com" target="_blank">Apogee
			Precision LLP</a>
	</p>


	</footer>
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
