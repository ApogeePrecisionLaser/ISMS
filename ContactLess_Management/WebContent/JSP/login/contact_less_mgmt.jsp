<%--
    Document   : login page
    Created on : 24th July, 2020, 11:01:48 AM
    Author     : Vikrant
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contact-Less Management</title>
        <link rel="shortcut icon" type="image/x-icon" href="http://demo.archiwp.com/light-version/wp-content/themes/archi/images/favicon.png">
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/login.css" type="text/css" rel="stylesheet" media="Screen"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <!--  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>-->
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <style>




            body { 
                background: url('images/login5.jpg') no-repeat center center fixed; 
                -webkit-background-size: cover;
                -moz-background-size: cover;
                -o-background-size: cover;
                background-size: cover;    
                overflow: hidden;
                            
            }

            .panel-default {
                opacity: 0.9;

            }
            .form-group.last {

            }
            h2 
            { 
                /*    font-size: 50px;*/
                /*text-decoration: underline;*/
                text-align: center;
                /* To place the text on the image*/
                top: 10px; /* The exact location of the text from the top of the image*/
                left: 0; /* Other beautification stuff */
                width: 100%; 
            }
            /* Coloring time */
            h2 span /* decorating the text within the span tag */
            { 

                color: white; 
                font: bold 50px/50px Helvetica, Sans-Serif; 
                letter-spacing: -1px; 
                /*background: rgb(0, 0, 0);  fallback color  */
                /*background: rgba(0, 0, 0, 0.7); padding: 5px; */
            }
            h2 span.spacer { padding:0 5px; } /* to pad the background color of text to make it look more elegant */


            .row {

            }

        </style>
        <script>
            var TxtType = function (el, toRotate, period) {
                this.toRotate = toRotate;
                this.el = el;
                this.loopNum = 0;
                this.period = parseInt(period, 10) || 2000;
                this.txt = '';
                this.tick();
                this.isDeleting = false;
            };

            TxtType.prototype.tick = function () {
                var i = this.loopNum % this.toRotate.length;
                var fullTxt = this.toRotate[i];

                if (this.isDeleting) {
                    this.txt = fullTxt.substring(0, this.txt.length - 1);
                } else {
                    this.txt = fullTxt.substring(0, this.txt.length + 1);
                }

                this.el.innerHTML = '<span class="wrap">' + this.txt + '</span>';

                var that = this;
                var delta = 200 - Math.random() * 100;

                if (this.isDeleting) {
                    delta /= 2;
                }

                if (!this.isDeleting && this.txt === fullTxt) {
                    delta = this.period;
                    this.isDeleting = true;
                } else if (this.isDeleting && this.txt === '') {
                    this.isDeleting = false;
                    this.loopNum++;
                    delta = 500;
                }

                setTimeout(function () {
                    that.tick();
                }, delta);
            };

            window.onload = function () {
                var elements = document.getElementsByClassName('typewrite');
                for (var i = 0; i < elements.length; i++) {
                    var toRotate = elements[i].getAttribute('data-type');
                    var period = elements[i].getAttribute('data-period');
                    if (toRotate) {
                        new TxtType(elements[i], JSON.parse(toRotate), period);
                    }
                }
                // INJECT CSS
                var css = document.createElement("style");
                css.type = "text/css";
                css.innerHTML = ".typewrite > .wrap { border-right: 0.08em solid #fff}";
                document.body.appendChild(css);
            };

        </script>

        <style> 
            .hp{
                align-self: center;
                color: whitesmoke;


            }</style>
    </head>
    <body>
        <div class="container">
            <div class="row" >
                <div class="col-md-12  col-sm-12 col-xs-10 " >
                    <h2>
                        <span>Contact-Less Management System<span class='spacer'></span><br /></span> 
                    </h2>
                </div>
            </div>
           <!--  <div class="row" >
                <div class="col-md-12  col-sm-12 col-xs-10 " >
                    <center> 
                        <h1 class="hp">

                            <a href="" class="typewrite" style="color:black" data-period="2000" data-type='[ "All Information at one place","We make it happen"]'>
                                <span class="wrap"></span>
                            </a>

                        </h1>
                    </center>
                </div>
            </div> -->

            <div class="row" >
                <div class="col-md-4 col-md-offset-10 col-sm-10 col-xs-10 " >

                    <div class="panel panel-default" >
                        <div class="panel-heading">
                            <strong>Login</strong>&nbsp;&nbsp;&nbsp;&nbsp;<strong style="color: red;">${message}</strong>
                        </div>

                        <div class="panel-body">
                            <form class="form-horizontal" role="form" action="LoginController" method="post">
                                <div class="form-group">
                                    <label for="inputEmail3" class="col-sm-3 control-label">
                                        Username</label>
                                    <div class="col-sm-9">
                                        <input id="user_name" name="user_name" type="text" class="text" placeholder="Enter User Name.." />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="inputPassword3" class="col-sm-3 control-label">
                                        Password</label>
                                    <div class="col-sm-9">
                                        <input id="password" name="password" class="text" type="password" placeholder="Enter Password.." />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-offset-3 col-sm-9">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox">
                                                Remember me
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group last">
                                    <div class="col-sm-offset-3 col-sm-9">
                                        <input  type="submit" class="btn btn-success btn-sm" name="task" id="login12" value="login"/>

                                        <button type="reset" class="btn btn-default btn-sm">
                                            Reset</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="panel-footer">
                            Not Registered? <a href="#">Register here</a></div>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
