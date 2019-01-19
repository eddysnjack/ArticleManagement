<%-- 
    Document   : index
    Created on : 14.Ara.2018, 20:23:38
    Author     : eddy
--%>

<%@page import="com.javaeddy.userBean"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.javaeddy.DatabaseManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="CSS/myStyle.css"/>
    <title>Akademik Makale Yönetim Sistemi</title>
</head>
<body>
    <%
        userBean ub = (userBean) request.getSession().getAttribute("user_bean");
        if (ub != null) {
            response.sendRedirect(request.getContextPath() + "/forbidden.jsp");
        }
    %>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark rounded fixed-top">
        <a class="navbar-brand" href="servletFileUpload?getFinalFiles=home">MAKALE YÖNETİM SİSTEMİ</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="servletFileUpload?getFinalFiles=home">Ana Sayfa<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="servletFileUpload?getFinalFiles=archive">arşiv</a>
                </li>
                <%  if (ub == null) {   %>
                <li class='nav-item active'>                   
                    <a class='nav-link' href='login.jsp'>giriş</a>
                </li>
                <li class='nav-item'>
                    <a class='nav-link' href='signup.jsp'>Üye ol</a>
                </li>
                <%   }  %>

                <%if (ub != null) {%>
                <li class='nav-item'>
                    <a class='nav-link' href= '<%=ub.getType()%>/loginSuccess.jsp'>yönetim</a>
                </li>
                <% }%>
            </ul>
            <%  if (ub != null) {%>

            <div class='form-inline my-2 my-lg-0' style='padding-right: 5%;'>
                <ul class='nav mr-auto'>
                    <li class='nav-item dropdown'>
                        <a class='nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>

                            ${user_bean.username}

                        </a>
                        <div class='dropdown-menu dropdown-menu-right' aria-labelledby='navbarDropdown'>
                            <a class='dropdown-item' href= '<%=ub.getType()%>/profile.jsp'>Profili Düzenle</a>
                            <div class='dropdown-divider'></div>
                            <form action='servletControlPoint' method='post'>
                                <button name='btn_Logout' class='btn btn-outline-danger my-2 my-sm-0' type='submit' style='float:right;'>Çıkış</button>
                            </form>
                        </div>
                    </li>
                </ul>
            </div>
            <%   }%>
        </div>
    </nav>


<div class="container">${otherErrors}</div>
<div class="container rounded border" id="div_form" style="border-width: 2px !important;">

    <form id="form_login" method="post" action="servletControlPoint">
        <div class="form-group">
            <label for="exampleInputEmail1">Email address</label>
            <input type="email" name="email" required  class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email" autofocus>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input type="password" name="password" required  class="form-control" id="exampleInputPassword1" placeholder="Password">
                <small id="loginError" class="form-text text-muted"><span style="color:red;">${errMsg}</span></small>

        </div>

        <button type="submit" name="btn_login" class="btn btn-primary">Submit</button>
    </form>

</div>




<script src="${pageContext.request.contextPath}/JS/jquery-3.3.1.js" ></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>

<script>  
    //
    //
    //
    ////-------------------------arka plan rengini değiştir
    //Ready ile eşdeğer:
    $(function controlTheme() {
        if ($("#NightModeValue").text() === "true") {
            nightOn();
        } else if ($("#NightModeValue").text() === "false") {
            nightOff();
        }
    });
    //-----aydınlık
    function nightOff() {
        //body
        $('body').removeClass('dark-theme');
        $('body').addClass('light-theme');
        //card
        $('.card').removeClass('text-white bg-dark');
        //modal content
        $(".modal-content").removeClass("bg-dark text-white");
        //textarea's
        $("#editorComment").removeClass("bg-dark text-white");
        $("#juryComment").removeClass("bg-dark text-white");
        //jury select
        $(".jurySelection").removeClass("bg-dark text-white");
        //comment button
        $(".yorumButonu").removeClass("btn-secondary");
        $(".yorumButonu").addClass("btn-primary");
        //toggle button
        $(".cardButonu").addClass("btn-success");
        $(".cardButonu").removeClass("btn-secondary");
        //file button
        $(".dosyaButonu").removeClass("btn-dark");
        $(".dosyaButonu").addClass("btn-light");
        //theme button
        $("#changeColor").val("Night Mode is Off");
        $("#changeColor").addClass("btn-dark");
        $("#changeColor").removeClass("btn-light");
    }
    //----karanlık
    function nightOn() {
//body
        $('body').addClass('dark-theme');
        $('body').removeClass('light-theme');
        //card
        $('.card').addClass('text-white bg-dark');
        //modal content
        $(".modal-content").addClass("bg-dark text-white");
        //textarea's
        $("#editorComment").addClass("bg-dark text-white");
        $("#juryComment").addClass("bg-dark text-white");
        //jury select
        $(".jurySelection").addClass("bg-dark text-white");
        //comment button
        $(".yorumButonu").addClass("btn-secondary");
        $(".yorumButonu").removeClass("btn-primary");
        //toggle button
        $(".cardButonu").removeClass("btn-success");
        $(".cardButonu").addClass("btn-secondary");
        //file button
        $(".dosyaButonu").addClass("btn-dark");
        $(".dosyaButonu").removeClass("btn-light");
        //theme button
        $("#changeColor").val("Night Mode is On");
        $("#changeColor").addClass("btn-ligt");
        $("#changeColor").removeClass("btn-dark");
    }
    function changeThemeValue(attrbute) {
        $.get('http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/servletThemeSet?NightModeValue=' + attrbute);
    }
    jQuery(document).ready(
            //When ThemeButton Click
                    function () {
                        jQuery('#changeColor').click(
                                function () {
                                    //make light
                                    if ($('body').hasClass('dark-theme')) {
                                        //body
                                        $('body').removeClass('dark-theme');
                                        $('body').addClass('light-theme');
                                        //card
                                        $('.card').removeClass('text-white bg-dark');
                                        //modal content
                                        $(".modal-content").removeClass("bg-dark text-white");
                                        //textarea's
                                        $("#editorComment").removeClass("bg-dark text-white");
                                        $("#juryComment").removeClass("bg-dark text-white");
                                        //jury select
                                        $(".jurySelection").removeClass("bg-dark text-white");
                                        //comment button
                                        $(".yorumButonu").removeClass("btn-secondary");
                                        $(".yorumButonu").addClass("btn-primary");
                                        //toggle button
                                        $(".cardButonu").addClass("btn-success");
                                        $(".cardButonu").removeClass("btn-secondary");
                                        //file button
                                        $(".dosyaButonu").removeClass("btn-dark");
                                        $(".dosyaButonu").addClass("btn-light");
                                        //theme button
                                        $("#changeColor").val("Night Mode is Off");
                                        $("#changeColor").addClass("btn-dark");
                                        $("#changeColor").removeClass("btn-light");
                                        //call the servlet and set the attriute on session level
                                        changeThemeValue(false);





                                        //make dark
                                    } else {
                                        //body
                                        $('body').addClass('dark-theme');
                                        $('body').removeClass('light-theme');
                                        //card
                                        $('.card').addClass('text-white bg-dark');
                                        //modal content
                                        $(".modal-content").addClass("bg-dark text-white");
                                        //textarea's
                                        $("#editorComment").addClass("bg-dark text-white");
                                        $("#juryComment").addClass("bg-dark text-white");
                                        //jury select
                                        $(".jurySelection").addClass("bg-dark text-white");
                                        //comment button
                                        $(".yorumButonu").addClass("btn-secondary");
                                        $(".yorumButonu").removeClass("btn-primary");
                                        //toggle button
                                        $(".cardButonu").removeClass("btn-success");
                                        $(".cardButonu").addClass("btn-secondary");
                                        //file button
                                        $(".dosyaButonu").addClass("btn-dark");
                                        $(".dosyaButonu").removeClass("btn-light");
                                        //theme button
                                        $("#changeColor").val("Night Mode is On");
                                        $("#changeColor").addClass("btn-ligt");
                                        $("#changeColor").removeClass("btn-dark");
                                        //call the servlet and set the attriute on session level
                                        changeThemeValue(true);
                                    }
                                }
                        );
                    }
            );


</script>

<span style='display:none' id='NightModeValue'>${sessionScope.NightModeValue}</span>
</body>
</html>
