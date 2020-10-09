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
<title>Device Info Registration</title>

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



<!-- <script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDOT5yBi-LAmh9P2X0jQmm4y7zOUaWRXI0&sensor=false"></script>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/fire_station_js.js"></script>
<script type="text/javascript" src="assets/jS/show_hide.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script> -->

<style>
p, div, input {
	font: 16px;
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

	function zoom() {
		document.body.style.zoom = "90%"
	}
	
	
	$(function(){
		
		$("#designation_id").autocomplete({
			source : function(request, response) {
				//alert(3321);
				var random = document.getElementById("designation_id").value;
				$.ajax({
					url : "DeviceRegController",
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
		
		//var desig_id = document.getElementById("designation_id").value;
		
		$("#class_id").autocomplete({
			source : function(request, response) {
				//alert(3321);
				var random = document.getElementById("class_id").value;
				$.ajax({
					url : "DeviceRegController",
					dataType : "json",
					data : {
						action1 : "searchClass",
						str : random,
						//str2: desig_id
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
				$('#class_id').val(ui.item.label); // display the selected text
				return false;
			}
		});
				
		
		$("#section_id").autocomplete({
			source : function(request, response) {
				//var class_name = document.getElementById("class_id").value;
				var class_name=$('input[name="viewCheck"]:checked').val();				
				var random = document.getElementById("section_id").value;
				$.ajax({
					url : "DeviceRegController",
					dataType : "json",
					data : {
						action1 : "searchSection",
						str : random,
						str2: class_name
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
				$('#section_id').val(ui.item.label); // display the selected text
				return false;
			}
		});
		
		
		$("#rollno_id").autocomplete({
			source : function(request, response) {
				var section = document.getElementById("section_id").value;
				var name = document.getElementById("person_id").value;
				var random = document.getElementById("rollno_id").value;
				var random3 = document.getElementById("designation_id").value;
				
				$.ajax({
					url : "DeviceRegController",
					dataType : "json",
					data : {
						action1 : "searchRollNo",
						str : random,
						str2: name,
						str3: section,
						str4 :random3
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
				$('#rollno_id').val(ui.item.label); // display the selected text
				return false;
			}
		});
		
		
		$("#device_id").autocomplete({
			source : function(request, response) {
				//alert(3321);
				var random = document.getElementById("device_id").value;
				$.ajax({
					url : "DeviceRegController",
					dataType : "json",
					data : {
						action1 : "searchDeviceId",
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
				$('#device_id').val(ui.item.label); // display the selected text
				return false;
			}
		});
		
		
		
		
		$("#person_id").autocomplete({
			source : function(request, response) {
					var designation = document.getElementById("designation_id").value;
					var section = document.getElementById("section_id").value;														
					var random = document.getElementById("person_id").value;
				$.ajax({
					url : "DeviceRegController",
					dataType : "json",
					data : {
						action1 : "searchPersonId",
						str : random,
						str2: section,
						str3: designation
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
				$('#person_id').val(ui.item.label); // display the selected text
				return false;
			}
		});
		
		
		/* $("#rf_id").autocomplete({
			source : function(request, response) {
				//alert(3321);
				var random = document.getElementById("scan_time").value;
				var random2 = document.getElementById("device_id").value;
				$.ajax({
					url : "DeviceRegController",
					dataType : "json",
					data : {
						action1 : "searchRfId",
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
				$('#rf_id').val(ui.item.label); // display the selected text
				return false;
			}
		}); */
		
		
	});
	
	function getRFID(){
		$('#rf_id').attr('disabled', false);
		
		// get rfid
		var random = document.getElementById("scan_time").value;
		var random2 = document.getElementById("device_id").value;
		$.ajax({
		url : "DeviceRegController",
		contentType: "application/json",
		dataType: 'json',
		data : {
			action1 : "searchRfId",
			str : random,
			str2 : random2
		},
		success: function(result){
			//alert("rfid ---"+result.list);	
			if(result.list!='No such rfid Id exists.......'){
				$('#rf_id').val(result.list);
			}
		}
	});		
	var rfid=$('#rf_id').val();
	if(rfid==''){
		setTimeout(getRFID, 500);
	}
		// end get rfid		
		
	}
	
	function saveTime(){
		var dt = new Date();
		var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();		
		$('#scan_time').val(time);
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
</style>


<script>
	function filterTablee(row_class) {
		if (row_class == 'Low') {
			$('.slight_row').hide();
			$('.normal_row').hide();
			$('.high_row').hide();
		}
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
				
				
				   
				   
				   //start radio button click
				     $("input[name='viewCheck']").on("change", function () {
						   //alert(this.value);
					});

				   
				   
				   // end radio button click
				
				
			});
	

	function makeEditable(id) {		
			$('#device_id').attr('disabled', false);
			$('#device_name').attr('disabled', false);
			$('#device_purpose').attr('disabled', false);		
			$('#save').attr('disabled', false);			
	}
	
	function showHidden(val){
		if(val=='Student'){//alert("khskjhdkh");
			$('#class_row').show();
			$('#section_row').show();
		}else{
			$('#class_row').hide();
			$('#section_row').hide();
		}
	}
	
	function showImage(val){
		$("#my_image2")
		.attr(
				"src",
				"http://localhost:8081/ContactLess_Management/DeviceRegController?getImage="
						+ val + "");
	}
	
	function getImgeIcon(image_path) {		
		$("#my_image1")
				.attr(
						"src",
						"http://localhost:8081/ContactLess_Management/DeviceRegController?getImage="
								+ image_path + "");
	}
	
	function generateReport() {//alert("get id -"+id);        
        var dropdown = "EPASS";
        var id="device";
        var queryString = "task=generateMapReport" + "&e_PassID=" + id;
        var url = "DeviceRegController?" + queryString;
        popupwin = openPopUp(url, "RFID Allocation", 500, 1000);
    }
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        return window.open(url, window_name, window_features);
    }
    
    function fillColumn(device_info_id,id){//alert("device iddd --"+device_info_id+"  idd--"+id);
    	var device_name=$("#" + id + '3').html();
    	$('#device_id').val($("#" + id + '2').html());
    	$('#device_name').val($("#" + id + '3').html());
    	
    	if($("#" + id + '4').html()=='Registration'){
    		$('#device_purpose').val('R');
    	}else{
    		$('#device_purpose').val('A');
    	}
    	
    	$('#device_info_id').val(device_info_id);
    	$('#edit').attr('disabled', false);
    }       
	
	
</script>


</head>

<body onload="zoom()" style="background-color: rgb(56, 165, 238, 0.5);">
	<!-- <div id="wrapper"> -->

		<%@include file="/menu.jsp"%>



		<div id="page-inner" style="background-color: rgb(56, 165, 238, 0.02);">

			<center>
				<div class="row" style="margin-top: -20px;">
					<div class="col-sm-12">
						<h2 style="color: white">
							<b>${login_office } Device Info Registration</b>
						</h2>
					</div>
				</div>
			</center>



			<div class="row" style="margin-top: 8px;" id="table_pc_view">
				<div class="col-md-10">
					<!-- Advanced Tables -->
					<div class="panel panel-default">
						<div class="panel-heading">Device Registered</div>
						<div class="panel-body" style="padding: 0px;">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-example" style="margin-bottom: 5px;">
									<thead>
										<tr>
											<th>S.No</th>
											<th>Device ID</th>
											<th>Device Name</th>
											<th>Device Purpose</th>
											<th>Status</th>
											<th>Date & Time</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="tempModel" items="${requestScope['list']}"
											varStatus="loopCounter">
											<tr
												onclick="fillColumn('${tempModel.device_info_id}','${loopCounter.count }');">
												<td>${loopCounter.count }</td>
												<td id="${loopCounter.count }2">${tempModel.device_id}</td>
												<td id="${loopCounter.count }3">${tempModel.device_name}</td>
												<td id="${loopCounter.count }4">${tempModel.device_purpose}</td>
												<td id="${loopCounter.count }5">${tempModel.status}</td>
												<td id="${loopCounter.count }6">${tempModel.created_date}</td>
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
					<!-- <div class="panel panel-default"
						style="background-color: #aeb7b8; border: none">
						<div class="panel-body easypiechart-panel">
							<div data-percent="100">
								<img id="my_image1" alt="image" src="images/person_logo.jpg"
									width=215px; height=220px; />
							</div>
						</div>
					</div> -->
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
				<div class="col-md-10">
					<form action="DeviceInfoRegController" method="POST" name="form">
						<table class="table table-bordered table-hover  table-striped">

							<tr>
								<th class="heading1">Device ID</th>
								<td><input class="form-control" type="text" id="device_id"
									name="device_id" value="" size="45" disabled></td>
							</tr>



							<tr>
								<th class="heading1">Device Name</th>
								<td><input class="form-control" type="text"
									id="device_name" name="device_name" value="" size="45" disabled></td>
							</tr>

							<tr>
								<th class="heading1">Device Purpose</th>
								<td><select class="form-control" name="device_purpose"
									id="device_purpose" disabled>
										<option disabled selected value></option>
										<option value="A">Attendance</option>
										<option value="R">Registration</option>
								</select></td>
							</tr>


							<tr>
								<td align='center' colspan="4"><input type="button"
									class="btn btn-primary" name="edit" id="edit" value="Edit"
									onclick="makeEditable(id)" disabled> <input
									type="submit" class="btn btn-info" name="task" id="save"
									value="Save" disabled> <input type="reset"
									class=" btn btn-warning" name="new" id="new" value="New"
									onclick="makeEditable(id)"> <input type="submit"
									class="btn btn-danger" name="task" id="delete" value="Delete"
									disabled> <input type="button" class="btn btn-success"
									name="task" id="scan" value="Scan"
									onclick=saveTime();getRFID();> <input type="button"
									class="btn btn-success" name="task" id="generatea-pdf"
									value="Generate PDF" onclick=generateReport();>
									<input type="button"
									class="btn btn-success" name="task" id="generatea-excel"
									value="Generate Excel" onclick=displayMapList(id);>
									</td>
							</tr>
							<input type="hidden" name="device_info_id" id="device_info_id"
								value="">

							<c:if test="${not empty message}">
								<tr id="message">
									<td colspan="2" bgcolor="${color}"><b>Result:
											${message}</b></td>
								</tr>
							</c:if>
						</table>
					</form>

				</div>
				<div class="col-md-2 col-sm-12 col-xs-12">
					<!-- <div class="panel panel-default"
						style="background-color: #aeb7b8; border: none">
						<div class="panel-body easypiechart-panel">
							<div data-percent="100">
								<img id="my_image2" alt="image" src="images/person_logo.jpg"
									width=215px; height=220px; />
							</div>
						</div>
					</div> -->
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
