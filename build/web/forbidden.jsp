<%-- 
    Document   : forbidden
    Created on : 15.Ara.2018, 14:09:20
    Author     : eddy
--%>

<%@page import="com.javaeddy.userBean"%>
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
    %>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark rounded fixed-top">
        <a class="navbar-brand" href="servletFileUpload?getFinalFiles=home">MAKALE YÖNETİM SİSTEMİ</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item ">
                    <a class="nav-link" href="servletFileUpload?getFinalFiles=home">Ana Sayfa</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="servletFileUpload?getFinalFiles=archive">arşiv</a>
                </li>

                <%  if (ub == null) {   %>
                <li class='nav-item'>                   
                    <a class='nav-link' href='login.jsp'>giriş</a>
                </li>
                <li class='nav-item'>
                    <a class='nav-link' href='signup.jsp'>Üye ol</a>
                </li>
                <%   }  %>


                <%if (ub != null && (ub.getType()).equals("editor")) {%>
                <li class='nav-item'>
                    <a class='nav-link' href='${pageContext.request.contextPath}/servletControlPoint?Select=AllEditorWaiting'>
                        Makale yönetim
                    </a>
                </li>
                <li class='nav-item'>
                    <a class='nav-link' href='${pageContext.request.contextPath}/servletUserDataController?selectUser=newbies'>
                        Kullanıcı Yönetim
                    </a>
                </li>
                <% }%>

                <%if (ub != null && (ub.getType()).equals("writer")) {%>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servletControlPoint?Select=WriterAllWaiting">
                        Makale yönetim
                    </a>
                </li>
                <li class='nav-item'>
                    <a class='nav-link' href='${pageContext.request.contextPath}/writer/sendFile.jsp'>DosyaGönder</a>
                </li>
                <% }%>
                <%if (ub != null && (ub.getType()).equals("jury")) {%>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servletControlPoint?Select=JuryAllWaiting">
                        Makale yönetim
                    </a>
                </li>
                <% }%>
            </ul>
            <%  if (ub != null) {%>

            <div class='form-inline my-2 my-lg-0' style='padding-right: 5%;'>
                <ul class='nav mr-auto'>
                    <li class='nav-item dropdown'>
                        <a class='btn btn-info nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>

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



<div style="border:1px solid chocolate; padding: 15px;text-align: center; margin-top: 10em;" class="alert alert-danger" role="alert">
    Bu Sayfaya Erişiminiz Yok
</div>




<script src="${pageContext.request.contextPath}/JS/jquery-3.3.1.js" ></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>

<!-- ===================== JSP =======================-->
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
