<%-- 
    Document   : loginSuccesseditor
    Created on : 14.Ara.2018, 23:46:29
    Author     : eddy
--%>

<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.javaeddy.userBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/myStyle.css"/>
    <title>Akademik Makale Yönetim Sistemi</title>

</head>
<body>


    <%
        userBean ub = (userBean) request.getSession().getAttribute("user_bean");
        if (ub == null) {
            response.sendRedirect(request.getContextPath() + "/forbidden.jsp");
        } else if (!ub.getType().equals("editor")) {
            response.sendRedirect(request.getContextPath() + "/forbidden.jsp");
        }


    %>



    <nav class="navbar navbar-expand-lg navbar-dark bg-dark rounded fixed-top">
        <a class="navbar-brand" href="#">MAKALE YÖNETİM SİSTEMİ</a>
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
                <li class='nav-item'>
                    <a class='nav-link' href='${pageContext.request.contextPath}/servletControlPoint?Select=AllEditorWaiting'>
                        Makale yönetim
                    </a>
                </li>
                <li class='nav-item active'>
                    <a class='nav-link' href='${pageContext.request.contextPath}/servletUserDataController?selectUser=newbies'>
                        Kullanıcı Yönetim
                    </a>
                </li>
            </ul>
            <div class="form-inline my-2 my-lg-0" style="padding-right: 5%;">
                <ul class="nav mr-auto">
                    <li class="nav-item dropdown">
                        <a class="btn btn-info nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${user_bean.username}
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/editor/profile.jsp">Profili Düzenle</a>
                            <div class="dropdown-divider"></div>
                            <form action="${pageContext.request.contextPath}/servletControlPoint" method="post">
                                <button name="btn_Logout" class="btn btn-outline-danger my-2 my-sm-0" type="submit" style="float:right;">Çıkış</button>
                            </form>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </nav>


    <!-- ==================TEMA==================-->
<div class="" style='text-align: right;'>
    <!--<input type="button"  id="changeColor" class='btn btn-dark' value='Night Mode is Off'/>-->
</div>

<!-- ==================BUTTTONLAR==================-->
<span id="SlectedButtonSpan" style="display:none;">${SelectedButton}</span>
<div class="container" style="text-align: center">
    <a class="btn btn-dark" id="btn_YeniGelen" href="${pageContext.request.contextPath}/servletUserDataController?selectUser=newbies">
        Yeni Gelenler
        <span class="badge badge-warning badge-pill">${FileNumber1}</span>
    </a>

    <a class="btn btn-dark" id="btn_HeyetBekleyen"  name ="" href="${pageContext.request.contextPath}/servletUserDataController?selectUser=writers">
        Yazarlar
        <span class="badge badge-warning badge-pill">${FileNumber2}</span>
    </a>

    <a class="btn btn-dark" id="btn_Onaylanmis" name ="" href="${pageContext.request.contextPath}/servletUserDataController?selectUser=juries">
        Heyet Üyeleri
        <span class="badge badge-warning badge-pill">${FileNumber3}</span>
    </a>

    <a class="btn btn-dark" id="btn_Reddedilmis" name ="" href="${pageContext.request.contextPath}/servletUserDataController?selectUser=rejected">
        Reddedilmiş
        <span class="badge badge-warning badge-pill">${FileNumber4}</span>
    </a>

    <a class="btn btn-dark" id="btn_Reddedilmis" name ="" href="${pageContext.request.contextPath}/servletUserDataController?selectUser=allOfEm">
        Tümü
        <span class="badge badge-warning badge-pill">${FileNumber5}</span>
    </a>
</div>

<div class="container" style="padding:3em;"></div>


<div class="container">

    <table class="table table-sm table-striped table-dark table-bordered table-hover" id="UsersDataTable">
        <caption>List of users</caption>
        <thead class="thead-dark" style="text-align: center;">
            <tr>
                <th scope="col" class="header" style="cursor: pointer;">#</th>
                <th scope="col" class="header" style="cursor: pointer;">İsim</th>
                <th scope="col" class="header" style="cursor: pointer;">Email</th>
                <th scope="col" class="header" style="cursor: pointer;">Kullanıcı Türü</th>
                <th scope="col" class="header" style="cursor: pointer;">Editör Onayı</th>
                <th scope="col" class="header" style="cursor: pointer;">Kayıt Tarihi</th>
                <th scope="col" class="header" style="cursor: pointer;">Onay/Red</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${usersInfo}" var="userData" varStatus="Looper">
                <tr class="bg-closed">
                    <th scope="row">${Looper.count}</th>
                    <td>${userData.getUsername()}</td>
                    <td>${userData.getEmail()}</td>
                    <td>${userData.getType()}</td>
                    <td>${userData.getEditorConfirm()}</td>
                    <td>${userData.getRegDateUTC()}</td>
                    <td style="text-align: center;">
                        <a 
                            class="btn btn-success" 
                            style="text-decoration: none;display:inline-block;width:45%;" 
                            href="${pageContext.request.contextPath}/servletUserDataController?UserConfirmation=confirmed&UserId=${userData.getId()}"
                            >
                            Onayla
                        </a>
                        /
                        <a 
                            class="btn btn-danger" 
                            style="text-decoration: none;display:inline-block;width:45%;" 
                            href="${pageContext.request.contextPath}/servletUserDataController?UserConfirmation=rejected&UserId=${userData.getId()}"
                            >
                            Reddet
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div style="padding: 12em;"></div>
<!-- ================SCRIPTS=================-->
<script src="${pageContext.request.contextPath}/JS/jquery-3.3.1.js" ></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/JS/tablesorter/jquery.tablesorter.js"></script>
<script>
    //Table Sorter
    $(document).ready(function () {
        $("#UsersDataTable").tablesorter();

    });
    //-------------------------arka plan rengini değiştir
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
