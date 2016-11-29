<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
    <div id="logo" data-retina="true">
        <a href="${servletRoot}/"><img src="img/bogazici_universitesi_logo_small.png" alt="" data-retina="true"
                                       width="40" height="40"></a>
    </div>
    <div class="col-md-12 col-sm-12">
        <a id="menu-button-mobile" href="#"><i class=" icon-menu"></i></a><!-- Menu button responsive-->

        <nav class="menu_container">
            <ul class="menu">
                <%--<li class="drop-normal"><a href="#" class="drop-down">Calculators</a>--%>
                <%--<div class="drop-down-container normal">--%>
                <%--<ul>--%>
                <%--<li><a href="daily-calorie-calculator.html">Daily calories calculator</a></li>--%>
                <%--<li><a href="calories-burned-by-heart-rate.html">Calories Burned by Heart rate</a></li>--%>
                <%--<li><a href="calories-burned-by-activities.html">Calories Burned by Activities</a></li>--%>
                <%--<li><a href="BMI-body-mass-calculator.html">BMI Body Mass Calculator</a></li>--%>
                <%--</ul>--%>
                <%--</div><!-- End dropdown normal -->--%>
                <%--</li>--%>
                <%--<li class="drop-normal"><a href="#" class="drop-down">Programs</a>--%>
                <%--<div class="drop-down-container normal">--%>
                <%--<ul>--%>
                <%--<li><a href="cardio_program.html">Cardio Program</a></li>--%>
                <%--<li><a href="toning_program.html">Toning Program</a></li>--%>
                <%--<li><a href="functionality_program.html">Functionaly Program</a></li>--%>
                <%--<li><a href="body_and_mind_program.html">Body and Mind Program</a></li>--%>
                <%--</ul>--%>
                <%--</div><!-- End dropdown normal -->--%>
                <%--</li>--%>
                <%--<li class="drop-normal"><a href="#" class="drop-down">Pages</a>--%>
                <%--<div class="drop-down-container normal">--%>
                <%--<ul>--%>
                <%--<li><a href="about.html">About</a></li>--%>
                <%--<li><a href="programs.html">Programs</a></li>--%>
                <%--<li><a href="pricing.html">Pricing</a></li>--%>
                <%--<li><a href="blog.html">Blog</a></li>--%>
                <%--<li><a href="shortcodes.html">Shortcodes</a></li>--%>
                <%--<li><a href="contacts.html">Contacts</a></li>--%>
                <%--</ul>--%>
                <%--</div><!-- End dropdown normal -->--%>
                <%--</li>--%>
                <c:if test="${userSession.login == true}">
                    <li class="drop-normal"><a href="#" class="drop-down">${userSession.username}</a>
                        <div class="drop-down-container normal">
                            <ul>
                                <li><a href="${servletRoot}/editProfile">Edit Profile</a></li>
                                <li><a href="${servletRoot}/setTargets">Set Targets</a></li>
                                <li><a href="${servletRoot}/viewHistory">My Food and Activities</a></li>
                                <li><a href="${servletRoot}/logout">Logout</a></li>
                            </ul>
                        </div><!-- End dropdown normal -->
                    </li>
                </c:if>
                <c:if test="${userSession.login  == false}">
                    <li><a href="${servletRoot}/loginOrRegister">Login or Register</a></li>
                </c:if>
                    <li><a href="${servletRoot}/queryFood">Add Food and Activities</a></li>
                    <li><a href="${servletRoot}/calculateBMI">Calculate BMI</a></li>
                    <li><a href="${servletRoot}/showReporting">Activity Histogram</a></li>
            </ul>
        </nav>
    </div> <!-- End col lg 12 -->
    <div id="header_shadow"></div>
    <input type="hidden" value="${serverBuildTime}">
    <input type="hidden" value="${errorPrompt}" id="errorPrompt">
    <!-- JQUERY -->
    <script src="js/jquery-1.10.min.js"></script>
    <script>
        $(document).ready(function () {
            var promptError = function () {
                var errorMsg = $('#errorPrompt').val();
                if (errorMsg != null && errorMsg.length > 0) {
                    $('#errorPrompt').val("");
                    alert(errorMsg);
                }
            };
            setTimeout(promptError, 1000);

        });
    </script>
</header>


