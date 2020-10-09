<%-- 
    Document   : footer
    Created on : Nov 3, 2012, 2:03:36 PM
    Author     : Neha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
        .full {
    width: 100%;    
}
.gap {
	height: 30px;
	width: 100%;
	clear: both;
	display: block;
}
.footer {
	background: #fff;
	height: auto;
        margin-top:5%;
	padding-bottom: 30px;
	position: relative;
	width: 100%;
	border-bottom: 1px solid #CCCCCC;
	border-top: 1px solid #DDDDDD;
}
.footer p {
	margin: 0;
}
.footer img {
	max-width: 100%;
}
.footer h3 {
	color: #000;
	font-size: 18px;
	font-weight: 600;
	line-height: 27px;
	padding: 40px 0 0px;
	text-transform: uppercase;
  margin-bottom: 15px;
}

.footer h4 {
	color: #E7475E;
	font-size: 2em;
	font-weight: 600;
	line-height: 38px;
	padding: 40px 0 10px;
	font-family: cursive;
  font-weight: lighter
}

.footer ul {
	font-size: 13px;
	list-style-type: none;
	margin-left: 0;
	padding-left: 0;
	margin-top: 0px;
	color: #000;
  padding: 0 0 8px 0;
}

.email{
  border-bottom: 3px solid #fff;
}
.footer ul li a {
	padding: 0 0 12px 0;
	display: block;
}
.footer a {
	color: #000;
  font-weight: lighter;
}

.footer p {
	color: #000;
  font-weight: lighter;
  
}

.footer a:hover {
	text-decoration:none;
  font-weight: bold;
}
.supportLi h4 {
	font-size: 20px;
	font-weight: lighter;
	line-height: normal;
	margin-bottom: 0 !important;
	padding-bottom: 0;
}

.bg-gray {
	background-image: -moz-linear-gradient(center bottom, #BBBBBB 0%, #F0F0F0 100%);
	box-shadow: 0 1px 0 #B4B3B3;
}


.footer a {
	color: #000;
}

.footer-bottom {
    

	border-top: 1px solid #DDDDDD;
	padding-top: 20px;
	
}
.footer-bottom p.pull-left {
	margin-top: 2em;
	border-top: 1px solid #DDDDDD;
	padding-top: 20px;
	padding-bottom: 10px;
        color:white;
}
.bottomcolor
{
     background-color: #E7475E;;
}

    </style>
    <body>
        <footer>
    <div class="footer" id="footer">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
                    <h4> Contact-Less Management System </h4>
                </div>
                <div class="col-lg-3 col-sm-2 col-xs-3">
                    <h3> Contact </h3>
                    <ul>
                        <li><a class="email"href="#"> vikrant.apogee@gmail.com </a></li>
                        <br/>
                        <li> <p> H-97,Sector-63,Noida (U.P.) </p> </li>
                        <li> <p> Anand,Gujarat </p> </li>
                    </ul>
                </div>
                <div class="col-lg-3 col-sm-2 col-xs-3">
                    <ul>
                        <li> <h5> <a href="#" style="margin-top: 5em"> ABOUT US</a> <h5></li>
                        <li> <h5><a href="#"> CURRENT SITUATION </a> <h5></li>
                        <li> <h5><a href="#"> HELP </a> <h5></li>
                        <li> <h5><a href="#"> LOCATE US </a> <h5></li>
                    </ul>
                </div>
               
            <!--/.row--> 
        </div>
        <!--/.container--> 
    </div>
    <!--/.footer-->
           
   
    <div class="footer-bottom">
        <div class="container">
            <p class="pull-left copyright"> Copyright © Apogee 2020. All right reserved .</p>
       
        </div>
    </div>
    <!--/.footer-bottom--> 
    
</footer>
    </body>
</html>
