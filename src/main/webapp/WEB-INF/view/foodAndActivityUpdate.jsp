<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<hr>

<h3>What are the references of this website ?</h3>
<p>This website is prepared as a course-project in Bogazici University / Turkey. All
    calculations
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
        <div class="progress">
            <div class="progress-bar progress-bar-danger" role="progressbar" data-percentage="100">
            </div>
        </div>
        <p>Cardio</p>
    </div>
    <div class="col-md-6 col-sm-6">
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a class="accordion-toggle" data-toggle="collapse"
                           data-parent="#accordion"
                           href="#gokhan">Dev Muzikal<i
                                class="indicator icon-plus pull-right"></i></a>
                    </h4>
                </div>
                <div id="gokhan" class="panel-collapse collapse">
                    <div class="panel-body">
                        Haldun Dormen
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a class="accordion-toggle" data-toggle="collapse"
                           data-parent="#accordion"
                           href="#gokhan2">Dev Muzikal 2 <i
                                class="indicator icon-plus pull-right"></i></a>
                    </h4>
                </div>
                <div id="gokhan2" class="panel-collapse collapse">
                    <div class="panel-body">
                        Haldun Dormen 2
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End row -->