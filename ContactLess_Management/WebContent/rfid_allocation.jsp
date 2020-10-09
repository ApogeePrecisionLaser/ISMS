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
<title>RFID Allocation</title>

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
		
		
		/*  $("#rollno_id").autocomplete({
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
		}); */
		
		
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
	
	function getRollNo(){
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
				$('#rollno_id').val(data.list);
				showImage(data.list);
			}
		});
		
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
		if (id == 'new') {
			$('#designation_id').attr('disabled', false);
			$('#rollno_id').attr('disabled', false);
			$('#person_id').attr('disabled', false);
			//$('#rf_id').attr('disabled', false);
			$('#save').attr('disabled', false);
			$('#device_id').attr('disabled', false);
		}
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
    
    function getAllDetails(roll_no){
    	
    	
    	$.ajax({
    		url : "DeviceRegController",
    		contentType: "application/json",
    		dataType: 'json',
    		data : {
    			action1 : "getAllDetails",
    			str : roll_no
    		},
    		success: function(result){    			
    			if(result.list[3]==null){
    				$('#section_row').hide();
    				$('#class_row').hide();
    				$('#person_id').val(result.list[0]);
    				$('#designation_id').val(result.list[1]);
    			}
    			else{
    				$('#section_row').show();
    				$('#class_row').show();
    				$('#person_id').val(result.list[0]);
    				$('#designation_id').val(result.list[1]);
    				$('#section_id').val(result.list[2]);
    				$("input[name=viewCheck][value=" + result.list[3] + "]").attr('checked', 'checked');

    			}
    			
    			
    		}
    	});
    	
    }
	
	
</script>


</head>

<body onload="zoom()" style="background-color: rgb(56, 165, 238, 0.5);">
	<!-- <div id="wrapper"> -->

<%@include file="/menu.jsp" %>

		<!-- /. NAV SIDE  -->


		<div id="page-inner" style="background-color: rgb(56, 165, 238, 0.02);">

			<center>
				<div class="row" style="margin-top: -20px;">
					<div class="col-sm-12">
						<h2 style="color: white">
							<b>${login_office } RFID Allocation</b>
						</h2>
					</div>
				</div>
			</center>



			<div class="row" style="margin-top: 8px;" id="table_pc_view">
				<div class="col-md-10">
					<!-- Advanced Tables -->
					<div class="panel panel-default">
						<div class="panel-heading">Allocated RFID</div>
						<div class="panel-body" style="padding: 0px;">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-example" style="margin-bottom: 5px;">
									<thead>
										<tr>
											<th>S.No</th>
											<th style="width:300px;">Name</th>
											<th>Designation</th>
											<th>Code</th>
											<th>Class</th>
											<th>RFID</th>
											<th>Status</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="tempModel" items="${requestScope['list']}"
											varStatus="loopCounter">
											<tr data-user="normalR"
												onclick="getImgeIcon('${tempModel.emp_code}');">
												<td>${loopCounter.count }</td>
												<td>${tempModel.emp_name}</td>
												<td>${tempModel.designation}</td>
												<td>${tempModel.emp_code}</td>
												<td>${tempModel.class_name}</td>
												<td>${tempModel.dev_id}</td>
												<td>${tempModel.status}</td>
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
						style="background-color: #aeb7b8; border: none">
						<div class="panel-body easypiechart-panel">
							<div data-percent="100">
								<img id="my_image1" alt="image" src="images/person_logo.jpg"
									width=215px; height=220px; />
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
				<div class="col-md-10">
					<form action="DeviceRegController" method="POST" name="form">
						<table class="table table-bordered table-hover  table-striped">
						
						 <tr id="rollNo_row" style="display:">
								<th class="heading1">Roll No/Code</th>
								<td><input class="form-control" type="text" id="rollno_id"
									name="rollno_id" value="" size="45"
									onchange="getAllDetails(this.value);showImage(this.value);" disabled></td>
							</tr> 

							<tr>
								<th class="heading1">Designation</th>
								<td><input class="form-control" type="text"
									id="designation_id" name="designation_id" value="" size="45"
									onchange="showHidden(this.value);" disabled></td>
							</tr>

							<tr id="class_row" style="display: none">
								<!-- <th class="heading1">Class</th>
								<td><input class="form-control" type="text" id="class_id"
									name="class_id" value="" size="45"></td> -->
								<td colspan="2"><input type="radio" id="ClassLKG"
									name="viewCheck" value="ClassLKG"> <label
									for="ClassLKG">ClassLKG</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="ClassUKG" name="viewCheck" value="ClassUKG">
									<label for="ClassUKG">ClassUKG</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class1" name="viewCheck" value="Class1">
									<label for="Class1">Class1</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class2" name="viewCheck" value="Class2">
									<label for="Class2">Class2</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class3" name="viewCheck" value="Class3">
									<label for="Class3">Class3</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class4" name="viewCheck" value="Class4">
									<label for="Class4">Class4</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class5" name="viewCheck" value="Class5">
									<label for="Class5">Class5</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class6" name="viewCheck" value="Class6">
									<label for="Class6">Class6</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class7" name="viewCheck" value="Class7">
									<label for="Class7">Class7</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class8" name="viewCheck" value="Class8">
									<label for="Class8">Class8</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class9" name="viewCheck" value="Class9">
									<label for="Class9">Class9</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class10" name="viewCheck" value="Class10">
									<label for="Class10">Class10</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class11" name="viewCheck" value="Class11">
									<label for="Class11">Class11</label> &nbsp;&nbsp; 
									
									<input
									type="radio" id="Class12" name="viewCheck" value="Class12">
									<label for="Class12">Class12</label></td>
							</tr>

							<tr id="section_row" style="display: none">
								<th class="heading1">Section</th>
								<td><input class="form-control" type="text" id="section_id"
									name="section_id" value="" size="45"></td>
							</tr>

							
							<!-- <tr id="rollNo_row" style="display:">
								<th class="heading1">Roll No/Code</th>
								<td><input class="form-control" type="text" id="rollno_id"
									name="rollno_id" value="" size="45"
									onchange="showImage(this.value);" disabled></td>
							</tr> -->
							
							<tr>
								<th class="heading1">Name</th>
								<td><input class="form-control" type="text" id="person_id"
									name="person_id" value="" size="45" onchange="getRollNo();" disabled></td>
							</tr>
							
							<tr>
								<th class="heading1">Select Device</th>
								<td><input class="form-control" type="text" id="device_id"
									name="device_id" value="${device_name }" size="45" disabled></td>
							</tr>

							<!-- <tr>
							<th class="heading1">Key Person Name</th>
							<td><input class="form-control" type="text" id="person_id"
								name="person_id" value="" size="45" disabled></td>
						</tr> -->

							<tr>
								<th class="heading1">RFID</th>
								<td><input class="form-control" type="text" id="rf_id"
									name="rf_id" value="" size="45" disabled></td>
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
									onclick=saveTime();getRFID();>
									<input type="button" class="btn btn-success"
									name="task" id="generatea-pdf" value="Generate PDF" onclick=generateReport();>
									</td>
							</tr>
							<input type="hidden" name="scan_time" id="scan_time" value="">

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
					<div class="panel panel-default"
						style="background-color: #aeb7b8; border: none">
						<div class="panel-body easypiechart-panel">
							<div data-percent="100">
								<img id="my_image2" alt="image" src="images/person_logo.jpg"
									width=215px; height=220px; />
							</div>
						</div>
					</div>
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
				"autoWidth": true,
				//"lengthChange": false,
				"pageLength" : 5,

			});
		});
	</script>
	<!-- Custom Js -->
	<script src="assets/js/custom-scripts.js"></script>

</body>

</html>
