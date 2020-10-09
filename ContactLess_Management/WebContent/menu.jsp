<script src="https://kit.fontawesome.com/64d58efce2.js"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="assets/css/style.css">
<header>
	<div class="container">
		<input type="checkbox" name="" id="check">

		<div class="logo-container">
			<div class="logo">
				<a href="http://apogeeprecision.com" target="_blank"><img
					src="images/logo.jpg" /></a>
			</div>
		</div>

		<%-- <c:forEach var="entry" items="${classsectionMap}">
			<option>${entry.key}</option>
			<c:forEach var="val" items="${entry.value}">
				<option>${val }</option>
			</c:forEach>
		</c:forEach> --%>

		<div class="nav-btn">
			<div class="nav-links" style="margin-top: 10px;">
				<ul>
					<li class="nav-link" style="-i: .6s"><a href="LoginController">Home</a>
					</li>
					
					<li class="nav-link" style="-i: .6s"><a href="dashboard.jsp">Dashboard</a>
					</li>
					<li class="nav-link" style="-i: .85s"><a
						href="AttendanceController">Attendance<i
							class="fas fa-caret-down"></i></a>
						<%-- <div class="dropdown">
							<ul>
								<li class="dropdown-link"><a href="#">Staff<i
										class="fas fa-caret-down"></i></a>
									<div class="dropdown second">
										<ul>
											<li class="dropdown-link"><a
												href="VisitTempDetailsController?type=Principal&parent=Principal&class=">Principal</a>
											</li>
											<li class="dropdown-link"><a
												href="VisitTempDetailsController?type=Accountant&parent=Accountant&class=">Accountant</a>
											</li>
											<li class="dropdown-link"><a
												href="VisitTempDetailsController?type=Clerk&parent=Clerk&class=">Clerk</a></li>
											<div class="arrow"></div>
										</ul>
									</div></li>
								<li class="dropdown-link"><a
									href="VisitTempDetailsController?type=Teacher&parent=&class=">Teacher</a></li>

								<!-- start for student multilevel dropdown -->

								<li class="dropdown-link"><a href="#">Student<i
										class="fas fa-caret-down"></i></a>
									<div class="dropdown second">
										<ul>
											<li class="dropdown-link"><a
												href="VisitTempDetailsController?type=Student&parent=singleStudent&class=">Single
													Student</a></li>
											<li class="dropdown-link"><a
												href="VisitTempDetailsController?type=Student&parent=allStudent&class=">All
													Student</a></li>
											<li class="dropdown-link"><a href="#">Class Wise<i
													class="fas fa-caret-down"></i></a>
												<div class="dropdown second">
													<ul>

														<c:forEach var="entry" items="${classsectionMap}">
															<li class="dropdown-link"><a
																href="VisitTempDetailsController?type=Student&parent=class&class=${entry.key }">${entry.key }<i
																	class="fas fa-caret-down"></i></a> <c:if
																	test="${not empty entry.value}">

																	<div class="dropdown second">
																		<ul>
																			<c:forEach var="val" items="${entry.value}">
																				<li class="dropdown-link"><a
																					href="VisitTempDetailsController?type=Student&parent=section&class=${val }">${val }</a></li>
																			</c:forEach>
																		</ul>
																	</div>

																</c:if></li>
															<div class="arrow"></div>
														</c:forEach>

													</ul>
												</div></li>
											<div class="arrow"></div>
										</ul>
									</div></li>


								<!--  end for student multilevel dropdown -->

							</ul>
						</div> --%></li>
					<li class="nav-link" style="-i: 1.1s"><a href="#">Device Registration<i class="fas fa-caret-down"></i>
					</a>
						<div class="dropdown">
							<ul>
								<li class="dropdown-link"><a href="DeviceInfoRegController">Device
										Info</a></li>
								<li class="dropdown-link"><a href="DeviceRegController">RFID
										Allocation</a></li>
								<div class="arrow"></div>
							</ul>
						</div></li>
					<li class="nav-link" style="-i: 1.35s"><a
						href="TempDashController">Status</a></li>
					<li class="nav-link" style="-i: 1.35s"><a href="JSP/policies/read_policies.jsp" target="_blank">Policies</a>
					</li>
					<li class="nav-link" style="-i: 1.35s"><a href="http://apogeeprecision.com/about-us/" target="_blank">About</a></li>
				</ul>
			</div>
			
			<h4 style="color:white">${login_office }</h4>

			<div class="log-sign" style="-i: 1.8s">
			
			
				<a href="#" class="btn transparent">Log in</a> <a href="#"
					class="btn solid">Sign up</a>
			</div>
		</div>

		<div class="hamburger-menu-container">
			<div class="hamburger-menu">
				<div></div>
			</div>
		</div>
	</div>
</header>