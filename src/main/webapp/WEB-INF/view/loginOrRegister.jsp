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

    <div class="row" id="row-main">
        <div class="col-lg-6 col-md-5" id="main-img-container">
            <div id="main-img">
                <img src="img/01.jpg" alt=""/>
            </div><!-- End main-img -->
        </div><!-- End main-img-container -->

        <div class="col-lg-6 col-lg-offset-6 col-md-7 col-md-offset-5">
            <div class="row" id="content-row">

                <div class="col-md-12">
                    <h1>Daily calories calculator</h1>
                    <p class="lead">
                        Cu affert populo neglegentur has, labore nostrum periculis ius in, singulis electram ad vel.
                        Ubique ceteros mediocritatem eos .
                    </p>
                    <p> Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea
                        proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw
                        denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable
                        VHS.</p>

                    <hr class="add_bottom_30">

                    <div class="row">
                        <div class="col-md-6 col-sm-6">
                            <form action="${servletRoot}/register" method="post">
                                <div class="form-group"><input type="text" class="form-control" name="Age"
                                                               placeholder="Age (Years)"></div>
                                <div class="form-group"><input type="text" class="form-control" name="Weight"
                                                               placeholder="Weight"></div>

                                <div class="form-group">
                                    <div class="styled-select">
                                        <select class="form-control" name="weight_select">
                                            <option value="kilo">By Kilo (KG)</option>
                                            <option value="pounds">By Pounds</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group"><input type="text" class="form-control" name="Height"
                                                               placeholder="Height"></div>

                                <div class="form-group">
                                    <div class="styled-select">
                                        <select class="form-control" name="height_select">
                                            <option value="cm">By Centimeters (cm)</option>
                                            <option value="inches">By Inches</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group add_top">
                                    <input type="radio" value="Male" name="Male" id="Male"><label for="Male"
                                                                                                  style="margin-right:30px;"><span></span>Male</label>
                                    <input type="radio" value="Female" name="Female" id="Female"><label
                                        for="Female"><span></span>Female</label>
                                </div>

                                <div class="form-group">
                                    <div class="styled-select">
                                        <select class="form-control" name="exercise_level">
                                            <option value="nospec">None (stay in bed all day)</option>
                                            <option value="sedentary">Sedentariness (very little)</option>
                                            <option value="light">Light (1 to 3 days per week)</option>
                                            <option value="moderate">Moderate (3 to 5 days per week)</option>
                                            <option value="hard">Hard (6 days per week)</option>
                                            <option value="nonstop">Non-Stop (You are Energizer Bunny.)</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group"><input type="text" class="form-control" name="username"
                                                               placeholder="Username"></div>
                                <div class="form-group"><input type="password" class="form-control" name="password"
                                                               placeholder="Password"></div>
                                <input type="submit" class="button_medium add_top" value="Register">
                            </form>
                        </div><!-- End col-md-6 -->

                        <div class="col-md-6 col-sm-6">
                            <form action="${servletRoot}/login" method="post">
                                <div class="form-group"><input type="text" class="form-control" name="username"
                                                               placeholder="Username"></div>
                                <div class="form-group"><input type="password" class="form-control" name="password"
                                                               placeholder="Password"></div>
                                <input type="submit" class="button_medium add_top" value="Login">
                            </form>
                        </div><!-- End col-md-6-->
                    </div><!-- End row-->

                    <hr>

                    <h3>Your block lists</h3>
                    <p>Cu affert populo neglegentur has, labore nostrum periculis ius in, singulis electram ad vel
                        labore nostrum periculis ius in. Nihil anim keffiyeh helvetica, craft beer labore wes anderson
                        cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. </p>
                    <div class="row">
                        <%--<div class="col-md-6 col-sm-6">--%>
                        <%--<ul class="list_1">--%>
                        <%--<li><a href="#">Ceteros mediocritatem</a></li>--%>
                        <%--<li><a href="#">Labore nostrum</a></li>--%>
                        <%--<li><a href="#">Primis bonorum</a></li>--%>
                        <%--</ul>--%>
                        <%--</div>--%>
                        <div class="col-md-6 col-sm-6">
                            <ul class="list_2">
                                <li><a href="#">Electram instructior </a></li>
                                <li><a href="#">Electram</a></li>
                                <li><a href="#">Melius eruditi </a></li>
                            </ul>
                        </div>
                    </div><!-- End row -->

                    <%--<footer>--%>
                    <%--<p>Copyright © 2014</p>--%>
                    <%--<ul id="contact_follow">--%>
                    <%--<li><a href="#"><span class="icon-instagram"></span></a></li>--%>
                    <%--<li><a href="#"><span class="icon-facebook"></span></a></li>--%>
                    <%--<li><a href="#"><span class="icon-twitter"></span></a></li>--%>
                    <%--<li><a href="#"><span class=" icon-googleplus"></span></a></li>--%>
                    <%--</ul>--%>
                    <%--</footer><!-- End footer -->--%>

                </div><!-- End col-md-12 -->
            </div><!-- End content-row -->
        </div><!-- End col-lg-6-->
    </div><!-- End main-row -->
</div><!-- End main-wrapper  -->

<!-- JQUERY -->
<script src="js/jquery-1.10.min.js"></script>

<!-- OTHER JS -->
<script src="js/calories_calculators.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/retina-replace.min.js"></script>
<script src="js/jquery.placeholder.js"></script>
<script src="js/functions.js"></script>

</body>
</html>