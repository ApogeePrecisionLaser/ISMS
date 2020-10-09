<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<style>

    .nav-link {
        color: #333;
        font-weight: 400;
        padding: .5rem 1rem;
        padding-right: 1rem;
        padding-left: 1rem;
    }
    .navbar-btn {
        background-color: #E7475E;
        border-color: #E7475E;
        font-weight: 400;
        border: 1px solid transparent;
        border-top-color: transparent;
        border-right-color: transparent;
        border-bottom-color: transparent;
        border-left-color: transparent;
        text-transform: uppercase;
        letter-spacing: 0.08rem;
        padding: .45rem 2.4rem;
        font-size: .92rem;
        line-height: 1.5;
        border-radius: 3rem;
        -webkit-transition: color 0.15s ease-in-out,background-color 0.15s ease-in-out,border-color 0.15s ease-in-out,-webkit-box-shadow 0.15s ease-in-out;
        transition: color 0.15s ease-in-out,background-color 0.15s ease-in-out,border-color 0.15s ease-in-out,-webkit-box-shadow 0.15s ease-in-out;
        transition: color 0.15s ease-in-out,background-color 0.15s ease-in-out,border-color 0.15s ease-in-out,box-shadow 0.15s ease-in-out;
        transition: color 0.15s ease-in-out,background-color 0.15s ease-in-out,border-color 0.15s ease-in-out,box-shadow 0.15s ease-in-out,-webkit-box-shadow 0.15s ease-in-out;
    }
    .header{
        margin-bottom: 5%;
    }
</style>

<header class="header">
    <nav class="navbar navbar-expand-lg  bg-white fixed-top">
        <div class="container"><a href="./" class="navbar-brand"><img src="images/l3.png" width="100"  alt="" class="img-fluid"></a>
            <button type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation" class="navbar-toggler navbar-toggler-right">Menu<i class="fa fa-bars ml-2"></i></button>
            <div id="navbarSupportedContent" class="collapse navbar-collapse">
                <ul class="navbar-nav ml-auto">
                    <!-- Link-->
                    <li class="nav-item"> <a href="index" class="nav-link active">Home</a></li>
                    <!-- Link-->
                    <!--                  <li class="nav-item"> <a href="faq.html" class="nav-link">FAQ</a></li>
                                       Link
                                      <li class="nav-item"> <a href="contact.html" class="nav-link">Contact</a></li>
                                       Link
                                      <li class="nav-item"> <a href="text.html" class="nav-link">Text Page</a></li>-->
                    <li class="nav-item dropdown"><a id="pages" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle">Organization</a>
                        <div class="dropdown-menu">
                            <a href="orgNameCont.do" class="dropdown-item">Org. Name</a>
                            <a href="orgTypeCont.do" class="dropdown-item">Org. Type</a>
                            <!--                            <a href="organisationSubTypeCont.do" class="dropdown-item">Org. Sub Type</a>-->
                            <a href="orgOfficeTypeCont.do" class="dropdown-item">Org. Office Type</a>
                            <a href="organisationCont.do" class="dropdown-item">Org. Office</a>
                            <a href="designationCont.do" class="dropdown-item">Designation</a>
                            <a href="personCount.do" class="dropdown-item">Org Person's Name</a>
                            <!--                            <a href="OrgOfficeHierarchyController" class="dropdown-item">Org Office Hierarchy</a>-->
                            <a href="OrganisationDesignationNewController" class="dropdown-item">Designation Organisation</a>
                            <a href="OrganisationTypeDesignationController" class="dropdown-item">Designation Organisation Type</a>
                            <a href="formdata" class="dropdown-item">FormData</a>
                            <!--                            <a href="OrganisationDesignationHierarchyController" class="dropdown-item">Desig Org Hierarchy</a>-->

                            <a href="orgDetailEntryCont.do" class="dropdown-item">Org Detail Entry</a>
                            <a href="allinoneCont.do" class="dropdown-item">All in One</a>
                            <a href="keypersonnewCont.do" class="dropdown-item">KeyPerson New</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown"><a id="pages" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle">Location</a>
                        <div class="dropdown-menu">
                            <a href="cityTypeCont" class="dropdown-item">City</a>
                            <a href="tehsilTypeCont" class="dropdown-item">Tehsil</a>
                            <a href="countryCont" class="dropdown-item">Country</a>
                            <a href="stateutTypeCont" class="dropdown-item">State</a>
                            <a href="districtTypeCont" class="dropdown-item">District</a>
                            <a href="divisionTypeCont" class="dropdown-item">Division</a>
                            <a href="zoneTypeCont" class="dropdown-item">Zone</a>
                            <a href="wardTypeCont" class="dropdown-item">Ward</a>
                            <a href="areaTypeCont" class="dropdown-item">Area</a>
                            <a href="cityLocationCont" class="dropdown-item">Location</a>

                        </div>
                    </li>

                    <li class="nav-item dropdown"><a id="pages" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle">Shift</a>
                        <div class="dropdown-menu">


                            <a href="ShiftController"  class="dropdown-item">Shift Type</a>

                            <a href="ShiftDesinationLocationController"  class="dropdown-item">Shift Designation Map</a>
                            <a href="ShiftKeyPersonMapController"  class="dropdown-item">Shift Key Person Map</a>
                            <!--<a href="makeTypeCont" class="dropdown-item">Manufacturer</a>
                                           <a href="modelTypeCont" class="dropdown-item">Model</a>
                                                    
                                         <a href="VehicleTypeController" class="dropdown-item">Vehicle Type</a>
                                             <a href="VehicleSubTypeController" class="dropdown-item">Vehicle Sub Type</a>-->
                            <a href="VehicleNewController" class="dropdown-item">Vehicle</a>
                            <!--               <a href="VehicleLocationController" class="dropdown-item">Vehicle Location</a>-->
                            <a href="VehicleKeyPersonController" class="dropdown-item">Vehicle Key Person</a>
                            <!--                   <a href="PointController" class="dropdown-item">Point</a>
                                                  <a href="VehicleKeyPersonPoint" class="dropdown-item">VehicleKeyPerson Point</a>
                                             <a href="ShiftVehicleDetail" class="dropdown-item">Shift Vehicle Detail</a>
                                                <a href="VehicleWeightCont.do" class="dropdown-item">Vehicle Weight</a>
                                              <a href="VehicleModelMap" class="dropdown-item">Vehicle Model Map</a>-->


                        </div>
                    </li>

                    <li class="nav-item dropdown"><a id="pages" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle">E-Pass</a>
                        <div class="dropdown-menu">
                            <c:if test="${login_designation == 'Director'}">
                                <a href="workTypeCont" class="dropdown-item">Work Type</a>
                            </c:if>
                            <a href="ePassCont" class="dropdown-item">e-pass</a>

                            <c:if test="${login_designation != 'normal_user'}">
                                <c:if test="${login_designation == 'Director'}">
                                    <a href="vendorCont" class="dropdown-item">Vendor</a>
                                </c:if>
                                <a href="orderMgmtCont" class="dropdown-item">Order Management</a>
                            </c:if>

                        </div>
                    </li>
                    <li class="nav-item dropdown"><a id="pages" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle">Quarantine Management</a>
                        <ul class="dropdown-menu">

                            <li><a href="Quarentine">Quarantine</a></li>
                            <li><a href="MedicalRecordController">Medical Record</a></li>
                            <li><a href="CoronaContactListController">Corona Contacted List</a></li>
                            <c:if test="${login_designation == 'Director'}">
                                <li><a href="ReasonDescriptionController">Reason Description</a></li>
                                <li><a href="SymptomsController">Symptoms</a></li>
                                <li><a href="SurvellianceController">Survelliance</a></li>
                                  <li><a href="CovidType">Covid Type</a></li>
                                  <li><a href="CovidTypeOfficeMapController">Covid Type & Office Map</a></li>
                            </c:if>






                        </ul>
                    </li>

                    <li class="nav-item dropdown"><a id="pages" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle">Document</a>
                        <ul class="dropdown-menu">
                            <!-- <li><a href="zonalCont">Zonal Council</a></li>
                             <li><a href="stateutTypeCont">State & UT</a></li>-->
                            <li><a href="Document">Document</a></li>
                            <li><a href="DocumentTypeController">Document Type</a></li>



                        </ul>
                    </li>
                    
                    <li class="nav-item dropdown"><a id="pages" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" class="nav-link dropdown-toggle">Medical Survey</a>
                        <ul class="dropdown-menu">
                            <!-- <li><a href="zonalCont">Zonal Council</a></li>
                             <li><a href="stateutTypeCont">State & UT</a></li>-->
                            <li><a href="MedicalSurveyController">Survey</a></li>
                              <li><a href="MedicalSurveyTableController">Medical Survey</a></li>
                                 <li><a href="HistoryController">History</a></li>
                           



                        </ul>
                    </li>
                    <li>  <a href="LoginController?task=logout"  class="btn btn-primary navbar-btn ml-0 ml-lg-3">Logout </a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>
