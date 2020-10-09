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
<title>Attendance Report</title>

<link rel="shortcut icon" type="image/x-icon"
	href="http://demo.archiwp.com/light-version/wp-content/themes/archi/images/favicon.png">
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

	function showClassEnroll(val) {
		if (val == 'Student') {//alert("khskjhdkh");
			$('#class_enroll').attr("disabled", false);
			$('#btn_submit').attr("disabled", true);
			$('#from_date').attr("disabled", true);
			$('#to_date').attr("disabled", true);
		} else {
			$('#class_enroll').attr("disabled", true);
			$('#btn_submit').attr("disabled", false);
			$('#from_date').attr("disabled", false);
			$('#to_date').attr("disabled", false);
		}
	}

	function showClass(val) {
		if (val == 'Class') {//alert("khskjhdkh");
			$('#class').attr("disabled", false);
			$('#enroll_no').attr("disabled", true);
		} else {
			$('#enroll_no').attr("disabled", false);
			$('#class').attr("disabled", true);
		}
	}

	function showSection(val) {
		if (val != '') {//alert("khskjhdkh");
			$('#section').attr("disabled", false);
		} else {
			$('#section').attr("disabled", true);
		}
	}

	function showName(val) {
		var section = $("#section").val();
		if (section == "ALL") {
			$('#from_date').attr("disabled", false);
		}
		if (section != "ALL") {
			$('#from_date').attr("disabled", true);
		}
		if (val != 'ALL') {
			$('#name').attr("disabled", false);
		} else {
			$('#name').attr("disabled", true);
		}
	}

	function showEnrollNo(val) {
		if (val != '') {//alert("khskjhdkh");
			$('#enroll_no').attr("disabled", false);
		} else {
			$('#enroll_no').attr("disabled", true);
		}
	}

	function showFromDate(val) {
		if (val != '') {//alert("khskjhdkh");
			$('#from_date').attr("disabled", false);
		} else {
			$('#from_date').attr("disabled", true);
		}
	}

	function showToDate(val) {
		$("#to_date").val("")
		if (val != '') {
			$('#to_date').attr("disabled", false);
			/* $('#to_date').datepicker({				
				endDate: val
			}); */
		} else {
			$('#to_date').val();
			$('#to_date').attr("disabled", true);
		}
	}

	function showButton(val) {
		if (val != '') {//alert("khskjhdkh");
			$('#btn_submit').attr("disabled", false);
		} else {
			$('#btn_submit').attr("disabled", true);
		}
	}

	$(function() {
		$("#designation_id").autocomplete({
			source : function(request, response) {
				var random = document.getElementById("designation_id").value;
				$.ajax({
					url : "AttendanceController",
					dataType : "json",
					data : {
						action1 : "searchDesignation",
						str : random

					},
					success : function(data) {

						console.log(data);
						response(data.list);
					},
					error : function(error) {
						console.log(error.responseText);
						response(error.responseText);
					}
				});
			},
			select : function(events, ui) {
				console.log(ui);
				$('#designation_id').val(ui.item.label); // display the selected text
				return false;
			}
		});

		$("#class").autocomplete({
			source : function(request, response) {
				var random = document.getElementById("class").value;
				$.ajax({
					url : "AttendanceController",
					dataType : "json",
					data : {
						action1 : "searchClass",
						str : random

					},
					success : function(data) {

						console.log(data);
						response(data.list);
					},
					error : function(error) {
						console.log(error.responseText);
						response(error.responseText);
					}
				});
			},
			select : function(events, ui) {
				console.log(ui);
				$('#class').val(ui.item.label); // display the selected text
				return false;
			}
		});

		$("#section").autocomplete({
			source : function(request, response) {
				var random = document.getElementById("section").value;
				var random2 = document.getElementById("class").value;

				$.ajax({
					url : "AttendanceController",
					dataType : "json",
					data : {
						action1 : "searchSection",
						str : random,
						str2 : random2

					},
					success : function(data) {

						console.log(data);
						response(data.list);
					},
					error : function(error) {
						console.log(error.responseText);
						response(error.responseText);
					}
				});
			},
			select : function(events, ui) {
				console.log(ui);
				$('#section').val(ui.item.label);
				// display the selected text
				return false;
			}
		});

		$("#name").autocomplete({
			source : function(request, response) {
				var random = document.getElementById("name").value;
				var random2 = document.getElementById("section").value;
				$.ajax({
					url : "AttendanceController",
					dataType : "json",
					data : {
						action1 : "searchName",
						str : random,
						str2 : random2

					},
					success : function(data) {

						console.log(data);
						response(data.list);
					},
					error : function(error) {
						console.log(error.responseText);
						response(error.responseText);
					}
				});
			},
			select : function(events, ui) {
				console.log(ui);
				$('#name').val(ui.item.label); // display the selected text
				return false;
			}
		});

		$("#enroll_no").autocomplete({
			source : function(request, response) {
				var random = document.getElementById("enroll_no").value;
				var random2 = document.getElementById("name").value;
				var random3 = document.getElementById("section").value;
				$.ajax({
					url : "AttendanceController",
					dataType : "json",
					data : {
						action1 : "searchEnrollNo",
						str : random,
						str2 : random2,
						str3 : random3

					},
					success : function(data) {

						console.log(data);
						response(data.list);
					},
					error : function(error) {
						console.log(error.responseText);
						response(error.responseText);
					}
				});
			},
			select : function(events, ui) {
				console.log(ui);
				$('#enroll_no').val(ui.item.label); // display the selected text
				return false;
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
						<b>${login_office } ATTENDANCE REPORT</b>
					</h2>
				</div>
			</div>
		</center>

		<div class="row" style="margin-top: 0px" id="icons_pc_view">
			<form action="AttendanceController" method="POST" name="form">
				<div class="col-md-2">
					<label>Designation</label> <input class="form-control" type="text"
						id="designation_id" name="designation_id"
						value="${designation_id }" size="45"
						onchange="showClassEnroll(this.value);">
				</div>
				<div class="col-md-2">
					<label>Class/Enroll.No.</label> <select class="form-control"
						type="text" id="class_enroll" name="class_enroll"
						value="${class_enroll }" onchange="showClass(this.value);"
						disabled>
						<option></option>
						<c:if test="${not empty class_enroll}">
							<option value="${class_enroll }" selected>${class_enroll }</option>
						</c:if>
						<option value="Class">Class</option>
						<option value="enrollment_no">Enrollment No.</option>
					</select>
				</div>
				<div class="col-md-2">
					<label>Class</label> <input class="form-control" type="text"
						id="class" name="class" value="${class_name }" size="45"
						onchange="showSection(this.value);" disabled>
				</div>
				<div class="col-md-2">
					<label>Section</label> <input class="form-control" type="text"
						id="section" name="section" value="${section }" size="45"
						onchange="showName(this.value);" disabled>
				</div>
				<div class="col-md-2">
					<label>Name</label> <input class="form-control" type="text"
						id="name" name="name" value="${name }" size="45"
						onchange="showEnrollNo(this.value);" disabled>
				</div>
				<div class="col-md-2">
					<label>Enroll. No.</label> <input class="form-control" type="text"
						id="enroll_no" name="enroll_no" value="${enroll_no }" size="45"
						onchange="showFromDate(this.value);" disabled>
				</div>
				<div class="col-md-2">
					<label>From Date</label> <input class="form-control" type="date"
						id="from_date" name="from_date" value="${from_date }" size="45"
						disabled onchange="showToDate(this.value);">
				</div>
				<div class="col-md-2">
					<label>To Date</label> <input class="form-control" type="date"
						id="to_date" name="to_date" value="${to_date }" size="45" disabled
						onchange="showButton(this.value);">
				</div>

				<div class="col-md-2">
					<label>Period</label> <select class="form-control" type="text"
						id="period" name="period" value="${period }"
						onchange="showClass(this.value);">
						<option></option>
						<option value="daily">Daily</option>
						<option value="weekly">Weekly</option>
						<option value="monthly">Monthly</option>
						<option value="yearly">Yearly</option>
					</select>
				</div>

				<%-- <div class="col-md-2">
					<label>Period</label> <select class="form-control" type="text"
						id="period" name="period" value="${period }">
						<option></option>
						<c:forEach var="party" items="${list3}">
							<option value="${party}">
								${party}
							</option>
						</c:forEach>

					</select>
				</div> --%>

				<div class="col-md-2" style="margin-top: 25px;">
					<input type="submit" value="Submit" id="btn_submit" name="task"
						class="btn btn-primary" disabled>
				</div>

				<div class="col-md-2" style="margin-top: 25px;">
					<input class="btn btn-primary" type="button" name="viewPdf"
						id="viewPdf" value="Generate PDF" onclick="displayMapList(id)">
				</div>

				<div class="col-md-2" style="margin-top: 25px;">
					<input class="btn btn-primary" type="button" name="viewXLS"
						id="viewXLS" value="Generate Excel" onclick="displayMapList(id)">
				</div>

			</form>

		</div>

		<c:if test="${dataShow == 'Yes' }">

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


			<div class="row" style="margin-top: 0px;" id="icons_pc_view">
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

		</c:if>

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
		
		function displayMapList(id) {	      
			var designation_id=$('#designation_id').val();
			var class_enroll=$('#class_enroll').val();
			var class_name=$('#class').val();
			var section=$('#section').val();
			var name=$('#name').val();
			var enroll_no=$('#enroll_no').val();
			var from_date=$('#from_date').val();
			var to_date=$('#to_date').val();
			var period=$('#period').val();
									
	            if (id === 'viewPdf'){	            		            	
	                queryString = "requester=PRINT" + "&designation_id=" +designation_id + "&class_enroll=" 
	                +class_enroll +"&class_name="+class_name+"&section="+section+"&name="+name+"&enroll_no="+enroll_no+"&from_date="+from_date+
	                "&to_date="+to_date+"&period="+period;
	            }
	            if (id === 'viewXLS'){	            		            	
	                queryString = "requester=PRINTXls" + "&designation_id=" +designation_id + "&class_enroll=" 
	                +class_enroll +"&class_name="+class_name+"&section="+section+"&name="+name+"&enroll_no="+enroll_no+"&from_date="+from_date+
	                "&to_date="+to_date+"&period="+period;
	            }
	           
	            var url = "AttendanceController?" + queryString;
	            popupwin = openPopUp(url, "AttendanceController", 600, 900);
	        }  
	        
	         function openPopUp(url, window_name, popup_height, popup_width) {
	            var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
	            var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
	            var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
	            return window.open(url, window_name, window_features);
	        }
		
	</script>
	<!-- Custom Js -->
	<script src="assets/js/custom-scripts.js"></script>
</body>
</html>
