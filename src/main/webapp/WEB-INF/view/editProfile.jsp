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
                        <div class="col-md-6 col-sm-6">
                            <form action="${servletRoot}/updateUserProfile" method="post">
                                <div class="form-group"><input id="usernameInput" type="text" class="form-control"
                                                               name="username"
                                                               placeholder="Username"
                                                               value="${userProfileModel.username}"></div>
                                <div class="form-group"><input id="weightInput" type="text" class="form-control"
                                                               name="weight"
                                                               placeholder="Weight" value="${userProfileModel.weight}">
                                </div>


                                <div class="form-group"><input id="heightInput" type="text" class="form-control"
                                                               name="height"
                                                               placeholder="Height" value="${userProfileModel.height}">
                                </div>


                                <div class="form-group"><input id="oldPasswordInput" type="password"
                                                               class="form-control" name="oldPassword"
                                                               placeholder="Old password"></div>
                                <div class="form-group"><input id="newPasswordInput" type="password"
                                                               class="form-control" name="password"
                                                               placeholder="New password"></div>
                                <input type="submit" class="button_medium add_top" onclick="return updateUserProfile()"
                                       value="Update">
                            </form>
                        </div><!-- End col-md-6 -->


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
<script>
    function updateUserProfile() {
        var username = $('#usernameInput').val();
        var weight = $('#weightInput').val();
        var height = $('#heightInput').val();
        var oldPassword = $('#oldPasswordInput').val();
        var newPassword = $('#newPasswordInput').val();
        if (!username) {
            alert("Username can not be empty");
            return false;
        } else if (!weight) {
            alert("Weight can not be empty");
            return false;
        } else if (!height) {
            alert("Height can not be empty");
            return false;
        }
        if (newPassword && !oldPassword) {
            alert("You can not change your password without entering old password");
            return false;
        }
        if (!newPassword && oldPassword) {
            alert("You should enter new password if you wish to change your old password")
            return false;
        }
        if (isNaN(weight)) {
            alert("Please enter numeric weight value");
            return false;
        }
        if (isNaN(height)) {
            alert("Please enter numeric height value");
            return false;
        }
        return true;
    }
</script>

</body>
</html>