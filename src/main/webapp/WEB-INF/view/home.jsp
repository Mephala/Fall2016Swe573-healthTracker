<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if IE 9 ]><html class="ie ie9" lang="en"> <![endif]-->
<html>
<head>
    <meta charset="utf-8">
    <title>Activity - Calories calculators and sport activity site template</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Favicons-->
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>
    <link rel="apple-touch-icon" type="image/x-icon" href="img/apple-touch-icon-57x57-precomposed.png">
    <link rel="apple-touch-icon" type="image/x-icon" sizes="72x72" href="img/apple-touch-icon-72x72-precomposed.png">
    <link rel="apple-touch-icon" type="image/x-icon" sizes="114x114"
          href="img/apple-touch-icon-114x114-precomposed.png">
    <link rel="apple-touch-icon" type="image/x-icon" sizes="144x144"
          href="img/apple-touch-icon-144x144-precomposed.png">

    <!-- CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/menu.css" rel="stylesheet">
    <link href="fontello/css/fontello.css" rel="stylesheet">
    <link href="css/flexslider.css" rel="stylesheet">
    <link href="css/slider.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="http://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="http://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

</head>
<body>
<!-- Preloader -->
<div id="preloader">
    <div id="status"><img src="img/bogazici_universitesi_logo.png" alt="" data-retina="true" width="160" height="160">
    </div>
</div>
<!--  end Preloader -->

<div id="main-wrapper">

    <header>
        <div id="logo" data-retina="true">
            <a href="${servletRoot}/"><img src="img/bogazici_universitesi_logo_small.png" alt="" data-retina="true" width="40" height="40"></a>
        </div>
        <div class="col-md-12 col-sm-12">
            <a id="menu-button-mobile" href="#"><i class=" icon-menu"></i></a><!-- Menu button responsive-->

            <nav class="menu_container">
                <ul class="menu">
                    <li class="drop-normal"><a href="#" class="drop-down">Calculators</a>
                        <div class="drop-down-container normal">
                            <ul>
                                <li><a href="daily-calorie-calculator.html">Daily calories calculator</a></li>
                                <li><a href="calories-burned-by-heart-rate.html">Calories Burned by Heart rate</a></li>
                                <li><a href="calories-burned-by-activities.html">Calories Burned by Activities</a></li>
                                <li><a href="BMI-body-mass-calculator.html">BMI Body Mass Calculator</a></li>
                            </ul>
                        </div><!-- End dropdown normal -->
                    </li>
                    <li class="drop-normal"><a href="#" class="drop-down">Programs</a>
                        <div class="drop-down-container normal">
                            <ul>
                                <li><a href="cardio_program.html">Cardio Program</a></li>
                                <li><a href="toning_program.html">Toning Program</a></li>
                                <li><a href="functionality_program.html">Functionaly Program</a></li>
                                <li><a href="body_and_mind_program.html">Body and Mind Program</a></li>
                            </ul>
                        </div><!-- End dropdown normal -->
                    </li>
                    <li class="drop-normal"><a href="#" class="drop-down">Pages</a>
                        <div class="drop-down-container normal">
                            <ul>
                                <li><a href="about.html">About</a></li>
                                <li><a href="programs.html">Programs</a></li>
                                <li><a href="pricing.html">Pricing</a></li>
                                <li><a href="blog.html">Blog</a></li>
                                <li><a href="shortcodes.html">Shortcodes</a></li>
                                <li><a href="contacts.html">Contacts</a></li>
                            </ul>
                        </div><!-- End dropdown normal -->
                    </li>
                    <li><a href="#">Purchase this template</a></li>
                </ul>
            </nav>
        </div> <!-- End col lg 12 -->
        <div id="header_shadow"></div>
    </header>

    <div class="flexslider">
        <ul class="slides">
            <li style="background: url(img/01.jpg) center"></li>
            <li style="background: url(img/02.jpg) center"></li>
            <li style="background: url(img/03.jpg) center"></li>
            <li style="background: url(img/04.jpg) center"></li>
        </ul>
    </div><!-- End slider -->

    <div id="calculators_home">
        <div class="container">
            <div class="row">
                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="box_calculator">
                        <a href="daily-calorie-calculator.html">
                            <img src="img/icon-1.png" alt="" data-retina="true">
                            <h3>Daily Calorie<br>Calculator</h3>
                        </a>
                    </div><!-- End box-calculator -->
                </div><!-- End col-md-3 -->

                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="box_calculator">
                        <a href="calories-burned-by-heart-rate.html">
                            <img src="img/icon-2.png" alt="" data-retina="true">
                            <h3>Calorie Burned<br>by heart/rate</h3>
                        </a>
                    </div><!-- End box-calculator -->
                </div><!-- End col-md-3 -->

                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="box_calculator">
                        <a href="calories-burned-by-activities.html">
                            <img src="img/icon-3.png" alt="" data-retina="true">
                            <h3>Calorie Burned<br>by activities</h3>
                        </a>
                    </div><!-- End box-calculator -->
                </div><!-- End col-md-3 -->

                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="box_calculator">
                        <a href="BMI-body-mass-calculator.html">
                            <img src="img/icon-4.png" alt="" data-retina="true">
                            <h3>Bmi Body mass<br>Calculator</h3>
                        </a>
                    </div><!-- End box-calculator -->
                </div><!-- End col-md-3 -->

            </div><!-- End row -->
        </div><!-- End container -->
    </div><!--calculators_home -->


</div><!-- End main-wrapper  -->

<!-- JQUERY -->
<script src="js/jquery-1.10.min.js"></script>

<!-- FlexSlider -->
<script src="js/jquery.flexslider.js"></script>
<script src="js/slider_func.js"></script>

<!-- OTHER JS -->
<script src="js/calories_calculators.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/retina-replace.min.js"></script>
<script src="js/jquery.placeholder.js"></script>
<script src="js/functions.js"></script>

</body>
</html>

