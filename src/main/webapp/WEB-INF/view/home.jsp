<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if IE 9 ]><html class="ie ie9" lang="en"> <![endif]-->
<html>
<jsp:include page="htmlHeader.jsp"/>
<body>
<jsp:include page="preloader.jsp"/>

<div id="main-wrapper">

    <jsp:include page="header.jsp"/>


    <div class="flexslider">
        <ul class="slides">
            <li style="background: url(img/healthyImage1.jpg) center"></li>
            <li style="background: url(img/healthyImage1.jpg) center"></li>
            <li style="background: url(img/healthyImage1.jpg) center"></li>
            <li style="background: url(img/healthyImage1.jpg) center"></li>
        </ul>
    </div><!-- End slider -->

    <div id="calculators_home">
        <div class="container">
            <div class="row">
                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="box_calculator">
                        <a href="#">
                            <img src="img/icon-1.png" alt="" data-retina="true">
                            <h3>Daily Calorie<br>Calculator</h3>
                        </a>
                    </div><!-- End box-calculator -->
                </div><!-- End col-md-3 -->

                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="box_calculator">
                        <a href="${servletRoot}/showReporting">
                            <img src="img/icon-2.png" alt="" data-retina="true">
                            <h3>View Activity<br>Histogram</h3>
                        </a>
                    </div><!-- End box-calculator -->
                </div><!-- End col-md-3 -->

                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="box_calculator">
                        <a href="${servletRoot}/queryFood">
                            <img src="img/icon-3.png" alt="" data-retina="true">
                            <h3>Calorie Balance<br>Food and Activities</h3>
                        </a>
                    </div><!-- End box-calculator -->
                </div><!-- End col-md-3 -->

                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="box_calculator">
                        <a href="${servletRoot}/calculateBMI">
                            <img src="img/icon-4.png" alt="" data-retina="true">
                            <h3>Bmi Body mass<br>Calculator</h3>
                        </a>
                    </div><!-- End box-calculator -->
                </div><!-- End col-md-3 -->
                <%--<div class="col-md-3 col-sm-6 col-xs-6">--%>
                    <%--<div class="box_calculator">--%>
                        <%--<a href="${servletRoot}/queryFood">--%>
                            <%--<img src="img/foodIcon.png" alt="" data-retina="true">--%>
                            <%--<h3>Look for Tasty<br>Food</h3>--%>
                        <%--</a>--%>
                    <%--</div><!-- End box-calculator -->--%>
                <%--</div><!-- End col-md-3 -->--%>

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

