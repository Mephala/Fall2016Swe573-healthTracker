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
                <img src="img/healthyImage1.jpg" alt=""/>
            </div><!-- End main-img -->
        </div><!-- End main-img-container -->

        <div class="col-lg-6 col-lg-offset-6 col-md-7 col-md-offset-5">
            <div class="row" id="content-row">

                <div class="col-md-12">
                    <h1>Updating your profile</h1>
                    <p class="lead">
                        Here you can find information regarding to your profile. Here you can change your information
                        and update accordingly.
                    </p>
                    <p> All calculations and estimations are done based on this project on github. Please refer <a
                            href="https://github.com/Mephala/Fall2016Swe573_healthTracker">my github project</a>. </p>

                    <hr class="add_bottom_30">

                    <div class="row">
                        <%--<div class="col-md-6 col-sm-6">--%>
                        <%----%>
                        <%--</div><!-- End col-md-6 -->--%>
                        <div id="chart_div"></div>

                    </div><!-- End row-->

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

<!-- JQUERY -->
<script src="js/jquery-1.10.min.js"></script>

<!-- OTHER JS -->
<script src="js/calories_calculators.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/retina-replace.min.js"></script>
<script src="js/jquery.placeholder.js"></script>
<script src="js/functions.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
    // A $( document ).ready() block.
    $(document).ready(function () {
        drawGraph();
    });

    function drawGraph() {

        function drawLineColors() {
            $.ajax
            ({
                type: "GET",
                url: "http://localhost:8080/healthTracker/getGraphData?variable=calorie",
                dataType: 'JSON',
                contentType: "application/json",
                async: false,
                beforeSend: function (xhr) {
//                    userAuthToken = make_base_auth(username, password);
//                    xhr.setRequestHeader('Authorization', userAuthToken);
                },
                success: function (responseData) {
                    if (responseData.loginSuccess == true) {
                        if (responseData.dataFound == true) {
                            console.log("Response:" + JSON.stringify(responseData));
                            var data = new google.visualization.DataTable();
                            data.addColumn('date', 'Time of Day');
                            data.addColumn('number', 'Calorie Intake');
                            data.addColumn('number', 'Calorie Burned');
                            data.addColumn('number', 'Daily Need');

                            var graphData = responseData.graphResponseList;
                            for (var i = 0; i < graphData.length; i++) {
                                data.addRow([new Date(graphData[i].year, graphData[i].month, graphData[i].day), graphData[i].calorieTaken, graphData[i].calorieBurned, graphData[i].dailyCalorieNeed]);
                            }

                            var options = {
                                hAxis: {
                                    title: 'Time'
                                },
                                vAxis: {
                                    title: 'Calorie'
                                },
                                colors: ['#a52714', '#097138', '#FFA500']
                            };

                            var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
                            chart.draw(data, options);
                        } else {
                            $('#chart_div').html("No data to show. Please enter food and activities.");
                        }
                    } else {
                        alert("Please login to see your activity history");
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert("ErrorCode: HT005 : " + thrownError);
                    console.log(xhr.responseText);
                    $('#addExerciseButton').val("Add Exercise");
                }
            });
        }

        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback(drawLineColors);
    }
</script>

</body>
</html>