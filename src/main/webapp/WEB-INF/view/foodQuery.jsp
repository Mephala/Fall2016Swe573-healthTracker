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
                        <div class="col-md-6 col-sm-6">
                            <form action="${servletRoot}/doQueryFood" method="post" id="foodSearchForm">
                                <div class="form-group"><input type="text" class="form-control" name="foodName"
                                                               id="foodQueryInput"
                                                               placeholder="Food Name"></div>
                                <div class="form-group" id="foodAmountDiv" style="display: none;"><input type="text"
                                                                                                         class="form-control"
                                                                                                         name="foodAmount"
                                                                                                         id="foodQueryAmount"
                                                                                                         placeholder="Amount in (mgs)">
                                </div>

                                <%--<input type="submit" class="button_medium add_top" value="Search"--%>
                                <%--onClick="this.form.submit(); this.disabled=true; this.value='SEARCHING…'; ">--%>
                                <input type="submit" class="button_medium add_top" value="AddFood"
                                       onClick="return addFood();" id="addFoodButton">
                            </form>
                        </div><!-- End col-md-6 -->

                        <div class="col-md-6 col-sm-6">
                            <c:if test="${searchResultFound}">
                                <div class="panel-group" id="accordion">
                                    <c:forEach items="${foodResults.basicFoodResponseList}" var="item">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a class="accordion-toggle" data-toggle="collapse"
                                                       data-parent="#accordion"
                                                       href="#${item.hrefId}">${item.title}<i
                                                            class="indicator icon-plus pull-right"></i></a>
                                                </h4>
                                            </div>
                                            <div id="${item.hrefId}" class="panel-collapse collapse">
                                                <div class="panel-body">
                                                        ${item.json}
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div><!-- End col-md-6-->
                    </div><!-- End row-->
                    <button class="button_medium" data-toggle="modal" data-target="#myModal" style="display: none;"
                            id="nonRegisteredUserButton">
                        Launch demo modal
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">&times;</button>
                                    <h4 class="modal-title" id="myModalLabel">You are not Registered!</h4>
                                </div>
                                <div class="modal-body">
                                    <h4>You must register to our system in order to use food & activity reports.</h4>
                                    <p>
                                        Please follow <a href="${servletRoot}/loginOrRegister">this link</a> to login or
                                        register.
                                    </p>
                                    <p>
                                        You only need to enter a username and password information to register, no
                                        sensitive data
                                        is asked.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="userActivitiesDiv">

                    </div>


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


<input type="hidden" id="protocol" value="http://"/>
<input type="hidden" id="serverRootUrl" value="${serverBase}"/>
<input type="hidden" id="ajaxSearchUrl" value="${serverContext}/ajax/queryFoodName"/>
<input type="hidden" id="ajaxAddFoodUrl" value="${serverContext}/ajax/addFood"/>
<input type="hidden" id="checkLoginUrl" value="${serverContext}/ajax/isLogin"/>

<!-- OTHER JS -->
<script src="js/calories_calculators.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/retina-replace.min.js"></script>
<script src="js/jquery.placeholder.js"></script>
<script src="js/functions.js"></script>
<script src="js/jquery-ui.js"></script>

<script>
    $(function () {
        $("#foodQueryInput").keydown(function () {
            var input = $("#foodQueryInput").val();
            if (input.length >= 2) {
                var data = {
                    searchKeyword: input
                };
                var protocol = $("#protocol").val();
                var serverRootUrl = $("#serverRootUrl").val();
                var loginPostUri = $("#ajaxSearchUrl").val();
                $.ajax
                ({
                    type: "POST",
                    url: protocol + serverRootUrl + loginPostUri,
                    dataType: 'JSON',
                    contentType: "application/json; charset=utf8",
                    async: true,
                    data: JSON.stringify(data),
                    beforeSend: function (xhr) {
//                    userAuthToken = make_base_auth(username, password);
//                    xhr.setRequestHeader('Authorization', userAuthToken);
                    },
                    success: function (data) {
                        console.log(data.availableKeywords);
                        var availableNames = data.availableKeywords;
                        $("#foodQueryInput").autocomplete({
                            source: availableNames,
                            select: function (event, ui) {
                                var selectedFoodName = ui.item.value;
                                $('#foodAmountDiv').show();
                                $('#foodQueryAmount').val('');
                                $('#foodQueryAmount').attr('placeholder', 'Amount with unit:' + data.unitMap[selectedFoodName]);
                            }
                        });
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert("ErrorCode: HT001 : " + thrownError);
                        console.log(xhr.responseText);
                    }
                });
            }

        });
    });

    function addFood() {
        var protocol = $("#protocol").val();
        var serverRootUrl = $("#serverRootUrl").val();
        var checkLoginUrl = $("#checkLoginUrl").val();
        $.ajax
        ({
            type: "GET",
            url: protocol + serverRootUrl + checkLoginUrl,
            dataType: 'json',
            contentType: "application/json; charset=utf8",
            async: false,
            data: '',
            beforeSend: function (xhr) {
//                    userAuthToken = make_base_auth(username, password);
//                    xhr.setRequestHeader('Authorization', userAuthToken);
            },
            success: function (data) {
                console.log("User Login status:" + data.login);
                var login = data.login;
                if (login) {
                    var input = $("#foodQueryInput").val();
                    var amount = $('#foodQueryAmount').val();
                    $('#addFoodButton').val("Adding...");
                    var protocol = $("#protocol").val();
                    var serverRootUrl = $("#serverRootUrl").val();
                    var addFoodUrl = $("#ajaxAddFoodUrl").val();
                    if (!(amount && input)) {
                        alert("Please enter valid food name and amount");
                        $('#addFoodButton').val("Add Food");
                        return;
                    }
                    if (isNaN(amount)) {
                        alert("Please enter numerical amount");
                        $('#addFoodButton').val("Add Food");
                        return;
                    }
                    var data = {
                        addedFood: input,
                        amount: amount
                    };
                    $.ajax
                    ({
                        type: "POST",
                        url: protocol + serverRootUrl + addFoodUrl,
                        dataType: 'html',
                        contentType: "application/json; charset=utf8",
                        async: false,
                        data: JSON.stringify(data),
                        beforeSend: function (xhr) {
//                    userAuthToken = make_base_auth(username, password);
//                    xhr.setRequestHeader('Authorization', userAuthToken);
                        },
                        success: function (data) {
                            console.log("Completed adding food.");
                            $('#userActivitiesDiv').html(data);
                            $('.progress .progress-bar').each(function () {
                                var me = $(this);
                                var perc = me.attr("data-percentage");

                                var current_perc = 0;

                                var progress = setInterval(function () {
                                    if (current_perc >= perc) {
                                        clearInterval(progress);
                                    } else {
                                        current_perc += 1;
                                        me.css('width', (current_perc) + '%');
                                    }

                                    me.text((current_perc) + '%');

                                }, 50);

                            });
                            $('#addFoodButton').val("Add Food");
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                            alert("ErrorCode: HT001 : " + thrownError);
                            console.log(xhr.responseText);
                            $('#addFoodButton').val("Add Food");
                        }
                    });

                } else {
                    $("#nonRegisteredUserButton").click();
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert("ErrorCode: HT002 : " + thrownError);
                console.log(xhr.responseText);
            }
        });


        return false;
    }
</script>


</body>
</html>