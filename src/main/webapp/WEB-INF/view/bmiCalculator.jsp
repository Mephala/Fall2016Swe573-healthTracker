<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if IE 9 ]><html class="ie ie9" lang="en"> <![endif]-->
<html>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<jsp:include page="htmlHeader.jsp"/>
<body>
<jsp:include page="preloader.jsp"/>

<div id="main-wrapper">

    <jsp:include page="header.jsp"/>

    <div class="row" id="row-main">
        <div class="col-lg-6 col-md-5" id="main-img-container">
            <div id="main-img">
                <img src="img/healthyImage1.jpg" alt=""/>
            </div><!-- End main-img -->
        </div><!-- End main-img-container -->

        <div class="col-lg-6 col-lg-offset-6 col-md-7 col-md-offset-5">
            <div class="row" id="content-row">

                <div class="col-md-12">
                    <h1>Please Enter Name of the Food</h1>
                    <p class="lead">
                        You can search for a food and select the collapsable panel to see it's nutrition details.
                        Please notice that only 5 of the food results are being shown at the moment.
                    </p>
                    <p> All calculations and estimations are done based on this project on github. Please refer <a
                            href="https://github.com/Mephala/Fall2016Swe573_healthTracker">my github project</a>. </p>

                    <hr class="add_bottom_30">

                    <div class="row">
                        <div class="col-md-6">
                            <c:choose>
                                <c:when test="${userSession.login}">
                                    <form action="#">
                                        <div class="form-group"><input id="weightInput" type="text" class="form-control"
                                                                       name="weight"
                                                                       placeholder="Weight"
                                                                       value="${userSession.weight}"></div>

                                        <div class="form-group">
                                            <div class="styled-select">
                                                <select id="weightType" class="form-control col-md-3"
                                                        name="weight_select">
                                                    <c:choose>
                                                        <c:when test="${userSession.weightUnit eq 'METRIC'}">
                                                            <option value="kilo" selected>By Kilo (KG)</option>
                                                            <option value="pounds">By Pounds</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="kilo">By Kilo (KG)</option>
                                                            <option value="pounds" selected>By Pounds</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group"><input id="heightInput" type="text" class="form-control"
                                                                       name="height"
                                                                       placeholder="Height"
                                                                       value="${userSession.heigth}"></div>

                                        <div class="form-group">
                                            <div class="styled-select">
                                                <select id="heightType" class="form-control" name="height_select">
                                                    <c:choose>
                                                        <c:when test="${userSession.weightUnit eq 'METRIC'}">
                                                            <option value="cm" selected>By Centimeters (cm)</option>
                                                            <option value="inches">By Inches</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="cm">By Centimeters (cm)</option>
                                                            <option value="inches" selected>By Inches</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </select>
                                            </div>
                                        </div>
                                        <input type="submit" class="button_medium add_top" value="Calculate"
                                               onClick="return calculateBMI();" id="calculateBMIButton">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="#">
                                        <div class="form-group"><input id="weightInput" type="text" class="form-control"
                                                                       name="weight"
                                                                       placeholder="Weight"></div>

                                        <div class="form-group">
                                            <div class="styled-select">
                                                <select id="weightType" class="form-control col-md-3"
                                                        name="weight_select">
                                                    <option value="kilo">By Kilo (KG)</option>
                                                    <option value="pounds">By Pounds</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group"><input id="heightInput" type="text" class="form-control"
                                                                       name="height"
                                                                       placeholder="Height"></div>

                                        <div class="form-group">
                                            <div class="styled-select">
                                                <select id="heightType" class="form-control" name="height_select">
                                                    <option value="cm">By Centimeters (cm)</option>
                                                    <option value="inches">By Inches</option>
                                                </select>
                                            </div>
                                        </div>

                                        <input type="submit" class="button_medium add_top" value="Calculate"
                                               onClick="return calculateBMI();" id="calculateBMIButton">
                                    </form>
                                </c:otherwise>
                            </c:choose>


                        </div>

                        <div class="col-md-6">
                            <c:choose>
                                <c:when test="${userSession.login}">
                                    <div class="result">
                                        <h3>BMI Value for you</h3>
                                        <div id="bmi_value">${userSession.bmi}</div>
                                        <div id="indicator">${userSession.bmiIndicator}</div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="result">
                                        <h3>BMI Value for you</h3>
                                        <div id="bmi_value">0</div>
                                        <div id="indicator"></div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div><!-- End row -->

                    <hr>

                    <h3>The Indication for BMI Range</h3>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>BMI Range</th>
                                <th>Category</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>Less than 16.5</td>
                                <td>Serverely Underweight</td>
                            </tr>
                            <tr>
                                <td>16.5 - 18.5</td>
                                <td>Underweight</td>
                            </tr>
                            <tr>
                                <td>18.5 - 25</td>
                                <td>Normal</td>
                            </tr>
                            <tr>
                                <td>25 - 30</td>
                                <td>Overweight</td>
                            </tr>
                            <tr>
                                <td>30+</td>
                                <td>Obese</td>
                            </tr>
                            </tbody>
                        </table>
                    </div><!-- End table responsive -->


                    <hr>

                    <h3>What are the references of this website ?</h3>
                    <p>This website is prepared as a course-project in Bogazici University / Turkey. All calculations
                        and estimations are done based on the following references. </p>
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
                                <li><a href="http://www.nutristrategy.com/activitylist4.htm">Activity List
                                    reference </a></li>
                                <li><a href="https://ndb.nal.usda.gov/ndb/doc/">NDB API</a></li>
                                <li><a href="https://en.wikipedia.org/wiki/Body_mass_index">Wikipedia Body/Mass
                                    Index </a></li>
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


<!-- OTHER JS -->
<script src="js/calories_calculators.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/retina-replace.min.js"></script>
<script src="js/jquery.placeholder.js"></script>
<script src="js/functions.js"></script>
<script src="js/jquery-ui.js"></script>
<script>
    function calculateBMI() {
        var w = $('#weightInput').val();
        var wu = $('#weightType').find(":selected").text();
        var h = $('#heightInput').val();
        var hu = $('#heightType').find(":selected").text();
        if (!(w && h)) {
            alert("Weight and Height can not be empty");
        } else if (isNaN(w) || isNaN(h)) {
            alert("Weight and Height information must be numerical");
        } else if (!(wu && hu)) {
            alert("Please describe weight and height metrics correctly");
        } else {
            if (wu == 'By Pounds') {
                w = w * 0.453592;
            }
            if (hu == 'By Inches') {
                h = h * 2.54;
            }
            h = h / 100; //cm to meter.
            var bmi = (w / (h * h));
            bmi = bmi.toPrecision(3);
            $('#bmi_value').html(bmi);
            var indicator = "";
            if (bmi < 16.5) {
                indicator = "You are severely underweight"
            } else if (bmi >= 16.5 && bmi < 18.5) {
                indicator = "You are underweight";
            } else if (bmi >= 18.5 && bmi < 25) {
                indicator = "You are normal";
            } else if (bmi >= 25 && bmi < 30) {
                indicator = "You are overweight"
            } else {
                indicator = "You are Obese : (";
            }
            $('#indicator').html(indicator);
        }
        return false;
    }

</script>

</body>
</html>