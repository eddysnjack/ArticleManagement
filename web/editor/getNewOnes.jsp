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
<body class="${themeObject.getBody_background()}">


    <%
        userBean ub = (userBean) request.getSession().getAttribute("user_bean");
        if (ub == null) {
            response.sendRedirect(request.getContextPath() + "/forbidden.jsp");
        } else if (!ub.getType().equals("editor")) {
            response.sendRedirect(request.getContextPath() + "/forbidden.jsp");
        }

//        out.print(request.getRequestURL().toString());
//        out.println("<br>");
//        out.print(request.getQueryString());
//        out.println("<br>");
//        out.print(request.getHeader("referer"));
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
                <li class='nav-item active'>
                    <a class='nav-link' href='${pageContext.request.contextPath}/servletControlPoint?Select=AllEditorWaiting'>
                        Makale yönetim
                    </a>
                </li>
                <li class='nav-item'>
                    <a class='nav-link' href='${pageContext.request.contextPath}/servletUserDataController?selectUser=newbies'>
                        Kullanıcı Yönetim
                    </a>
                </li>
            </ul>
            <div class="form-inline my-2 my-lg-0" style="padding-right: 5%;">
                <ul class="nav mr-auto">
                    <li class="nav-item dropdown ">
                        <a class="btn btn-dark nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${user_bean.username}
                        </a>
                        <div class="dropdown-menu dropdown-menu-right bg-dark text-white" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item bg-dark text-white" href="${pageContext.request.contextPath}/editor/profile.jsp">Profili Düzenle</a>
                            <div class="dropdown-divider" ></div>
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
    <!--<a id="changeColor" class='btn btn-dark' href="${pageContext.request.contextPath}/servletThemeSet?nightModeValue=dark"/>Night Mode is Off</a>-->
    <input type="button" value="${themeObject.getTheme_button_text()}" id="changeColor" class='btn ${themeObject.getTheme_button_class()}' onClick='changeThemeValue()'/>
</div>
<!-- ==================BUTTTONLAR==================-->
<span id="SlectedButtonSpan" style="display:none;">${SelectedButton}</span>
<div class="container" style="text-align: center">
    <a class="btn btn-primary" id="btn_YeniGelen" href="${pageContext.request.contextPath}/servletControlPoint?Select=AllEditorWaiting">
        Yeni Gelenler
        <span class="badge badge-warning badge-pill">${FileNumber1}</span>
    </a>
    <a class="btn btn-info" id="btn_HeyetBekleyen"  name ="" href="${pageContext.request.contextPath}/servletControlPoint?Select=AllJuryWaiting">
        Heyet Onayı Bekleyenler
        <span class="badge badge-warning badge-pill">${FileNumber2}</span>
    </a>
    <a class="btn btn-success" id="btn_Onaylanmis" name ="" href="${pageContext.request.contextPath}/servletControlPoint?Select=Published">
        Yayınlanmış
        <span class="badge badge-warning badge-pill">${FileNumber3}</span>
    </a>
    <a class="btn btn-danger" id="btn_Reddedilmis" name ="" href="${pageContext.request.contextPath}/servletControlPoint?Select=EditorRejected">
        Reddedilmiş
        <span class="badge badge-warning badge-pill">${FileNumber4}</span>
    </a>
</div>


<!-- ==================SONUÇLAR==================-->
<span>${NoResults}</span>
<div class="accordion  " id="accordionExample" style="width:90%; margin:0 auto; padding:5%;">                
    <c:forEach items="${fileListAttribute}" var="item" varStatus="LoopIndex">

        <div class="card ${themeObject.getCard_classes()}">
            <div class="card-header" id="headingOne"><!-- for cntering!!! margin:0 auto;float:none;-->
                <h5 class="mb-0">
                    <button
                        class="btn btn-sm cardButonu ${themeObject.getCard_button()}"
                        type="button"
                        data-toggle="collapse"
                        data-target="#collapseOne${LoopIndex.count}"
                        aria-expanded="true"
                        aria-controls="collapseOne"

                        >
                        ${item.getName()}
                    </button>
                </h5>
            </div>

            <div
                id="collapseOne${LoopIndex.count}"
                class="collapse show grid-container"
                aria-labelledby="headingOne"
                data-parent="#accordionExample"

                >

                <!-- ============================Document View MODAL -->
                <div class="whole-modal grid-item" style="display: inline-grid;grid-template-columns: 60% 40%;">
                    <button type="button" class="btn dosyaButonu ${themeObject.getFile_button()}" data-toggle="modal" data-target="#DocumentViewModal${LoopIndex.count}">
                        <img src="${pageContext.request.contextPath}/images/icons8-document-500px.svg" title="Dosyayı Görüntüle" class="img-thumbnail rounded" style="max-width:100px;"/>
                        <br>Dosyayı Görüntüle
                    </button>

                    <div class="modal fade bd-example-modal-lg-deneme" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="DocumentViewModal${LoopIndex.count}">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content ${themeObject.getModal_content()}" style="height: 500px;">
                                <div class="modal-header">
                                    <h6 class="modal-title" id="exampleModalLongTitle">${item.getName()}</h6>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <object style="width:100%;height: 100%;" src="${pageContext.request.contextPath}/servletFileUpload?Stream=Embed&filePath=${item.getFilePath()}">
                                    <embed style="width:100%;height: 100%;" src="${pageContext.request.contextPath}/servletFileUpload?Stream=Embed&filePath=${item.getFilePath()}"></embed>
                                </object>
                            </div>
                        </div>
                    </div>
                    <div class="container">
                        <a href="${pageContext.request.contextPath}/servletFileUpload?Stream=Download&filePath=${item.getFilePath()}" title="Dosyayı indir">
                            <img src="${pageContext.request.contextPath}/images/download_light-grey.svg" style="max-height: 15px;"/>
                        </a>
                    </div>               
                </div>
                <!-- ============================ORTA KISIM, Dosya Bilgileri / middle section file information-->

                <div style="">
                    <!-- BAŞLIK -->
                    <span style="display: inline-grid;grid-template-columns: 100%;width:100%; height: 2em;text-align: center;">
                        <label style="">Dosya Bilgileri:</label>
                    </span>
                    <!-- GÖVDE -->
                    <div  style="display: table;align-items: center;margin: auto; padding:5px;">
                        <div style="display:table-row;">
                            <hr style="display: table-cell; background-color: #dadada">
                            <hr style="display: table-cell; background-color: #dadada">
                        </div>
                        <!-- ----------yazar---------- -->
                        <div style="display: table-row;">
                            <label style="display: table-cell;" class="px-1">Yazar Adı:</label>
                            <label style="display: table-cell;" class="px-1">${item.getWriterData().getUsername()}</label>
                        </div>
                        <div style="display: table-row;">
                            <label style="display: table-cell;" class="px-1">Yazar Email:</label>
                            <label style="display: table-cell;" class="px-1">${item.getWriterData().getEmail()}</label>
                        </div>

                        <div style="display:table-row;">
                            <hr style="display: table-cell; background-color: #dadada">
                            <hr style="display: table-cell; background-color: #dadada">
                        </div>
                        <!-- ----------jüri---------- -->
                        <div style="display: table-row;">
                            <label style="display: table-cell;" class="px-1">Heyet Adı:</label>
                            <label style="display: table-cell;" class="px-1">${item.getJuryData().getUsername()}</label>
                        </div>
                        <div style="display: table-row;">
                            <label style="display: table-cell;" class="px-1">Heyet Email:</label>
                            <label style="display: table-cell;" class="px-1">${item.getJuryData().getEmail()}</label>
                        </div>
                        <div style="display:table-row;">
                            <hr style="display: table-cell; background-color: #dadada">
                            <hr style="display: table-cell; background-color: #dadada">
                        </div>
                        <!-- ----------tarih---------- -->
                        <div style="display:table-row;">
                            <label style="display:table-cell;" class="px-1">Gönderilme Zamanı(UTC):</label>
                            <label style="display:table-cell;;padding-right: 4px;padding-bottom: 4px;" class="px-1">${item.getSendingDate()} UTC</label>
                        </div>
                    </div>
                </div>

                <!-- ============================COMMENT View MODAL style="border:2px solid black;"-->
                <div class="whole-modal grid-item" style="position:absolute;bottom:0; right:0;" >
                    <!-- Button trigger modal -->
                    <button type="button" class="yorumButonu btn ${themeObject.getComment_button()}" data-toggle="modal" data-target="#exampleModalCenter${LoopIndex.count}">
                        Yorumları Göster/Düzenle
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModalCenter${LoopIndex.count}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content ${themeObject.getModal_content()}">
                                <form action="${pageContext.request.contextPath}/servletControlPoint" method="post">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle">Yorumlar</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="card-body" style="text-align: center;">
                                            <label for="editorComment">EDITOR COMMENT:</label><br>
                                            <textarea class="form-control ${themeObject.getEditor_comment()}"  id="editorComment" name="editorComment">${item.getEditorComments()}</textarea><br>
                                            <label for="juryComment">JURY COMMENT:</label><br>
                                            <textarea class="form-control ${themeObject.getJury_comment()}"  id="juryComment" readonly>${item.getJuryComments()}</textarea>

                                        </div>

                                        <!-- CheckBox Kısmı KABUL VE RED ${LoopIndex.count}-->
                                        <div class="container form-check-inline">    
                                            <div class="custom-control custom-radio btn btn-success mx-5">
                                                <input type="radio" id="RadioKabul${LoopIndex.count}" name="editorStatusFile" value="confirmed" class="custom-control-input" onClick="showJury(${LoopIndex.count})"/>
                                                <label class="custom-control-label " for="RadioKabul${LoopIndex.count}">KABUL ET</label>
                                            </div>
                                            <div class="custom-control custom-radio btn btn-danger mx-5">
                                                <!-- mx anlamı x ekseninde(sağve sol) margin ekle 0-5 arası 0-3em arası margin ekliyor https://getbootstrap.com/docs/4.1/utilities/spacing/-->
                                                <input type="radio" id="RadioRet${LoopIndex.count}" name="editorStatusFile" value="rejected" class="btn custom-control-input" onClick="hideJury(${LoopIndex.count})">
                                                    <label class="custom-control-label" for="RadioRet${LoopIndex.count}">REDDET</label>
                                            </div>
                                        </div>

                                        <!-- JURI SELECTION KISMI -->
                                        <div class="container" id="jurySelectionContainer${LoopIndex.count}" style="text-align: left; display:none;">
                                            <label for="jurySelection">Juriyi Seç</label>
                                            <select name="juryMember" id="jurySelection" class="jurySelection custom-select ${themeObject.getJury_selection()}">
                                                <option value="null">Seçilmedi</option>
                                                <c:forEach items="${ListOfJuries}" var="jury" varStatus="Looper">

                                                    <option value="${jury.getId()}">

                                                        ${jury.getUsername()} &num; ${jury.getEmail()}

                                                    </option>

                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <input type="hidden" name="fileID" value="${item.getId()}"/>
                                        <input type="submit" class="btn btn-primary" name="EditorCommentEdit" value="Save changes" />
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    </c:forEach>
</div>


<div style="padding: 12em;"></div>
<!-- ================SCRIPTS=================-->
<script src="${pageContext.request.contextPath}/JS/jquery-3.3.1.js" ></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
<-- Jquery'de animate fonksiyonuna renk değiştirme özelliği eklemek için-->
<script src="${pageContext.request.contextPath}/JS/jquery.color-2.1.2"></script>





<script>
//buton basılı gösterme efekti
                                                    $(document).ready(
                                                            function () {
                                                                if ($("#SlectedButtonSpan").text() === "YeniGelen") {
                                                                    $("#btn_YeniGelen").addClass("active");
                                                                } else if ($("#SlectedButtonSpan").text() === "HeyetOnayiBekleyen") {
                                                                    $("#btn_HeyetBekleyen").addClass("active");
                                                                } else if ($("#SlectedButtonSpan").text() === "Onaylanmis") {
                                                                    $("#btn_Onaylanmis").addClass("active");
                                                                } else if ($("#SlectedButtonSpan").text() === "Reddedilmis") {
                                                                    $("#btn_Reddedilmis").addClass("active");
                                                                }
                                                            }
                                                    );
                                                    //Jüri seçim kutusunu göster/gizle
                                                    function hideJury(idIndex) {
                                                        $("#jurySelectionContainer" + idIndex).slideUp();
                                                        // document.getElementById("jurySelectionContainer").style.display = "none";

                                                    }
                                                    function showJury(idIndex) {
                                                        $("#jurySelectionContainer" + idIndex).slideDown();
                                                    }

//-------------------------arka plan rengini değiştir
                                                    //Ready ile eşdeğer:
//                                                    $(function controlTheme() {
//                                                        if ($("#nightModeValue").text() === "true") {
//                                                            nightOn();
//                                                        } else if ($("#nightModeValue").text() === "false") {
//                                                            nightOff();
//                                                        }
//                                                    });
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

//                                                    ==============================================
//                                                    function changeThemeValue() {
//                                                        var themeVal = document.getElementById("nightModeValue").textContent;
//
//                                                        const link = document.createElement('a'); //elent oluştur
//
//                                                        if (themeVal == "dark") {
//                                                            link.href = "${pageContext.request.contextPath}/servletThemeSet?nightModeValue=light";
//                                                            event = new MouseEvent('click');
//                                                            link.dispatchEvent(event);//linke git
//
//                                                            // $.get('http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/servletThemeSet?nightModeValue=light');
//                                                            //ajax işe yaramadı çünkü sayfa yenilemesini window.location.refresh yerine servlet tarafında yaptım. aslında tarayıcının form gönderme işlemini onayla uyarısı olmasa teme değiştirme tam bir javascript işi. çok basit oluyor o vakit işler. ama o uyarı canımı sıktı :/
//                                                        } else if (themeVal == "light") {
//                                                            link.href = "${pageContext.request.contextPath}/servletThemeSet?nightModeValue=dark";
//                                                            event = new MouseEvent('click');
//                                                            link.dispatchEvent(event);
//                                                            //$.get('http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/servletThemeSet?nightModeValue=dark');
//                                                        }
//                                                    }
//                                                    =======================================================
                                                    function changeThemeValue() {
                                                        var themeVal = document.getElementById("nightModeValue").textContent;
                                                        if (themeVal == "dark") {
                                                            $.get('http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/servletThemeSet?nightModeValue=light',
                                                                    function () {
                                                                        window.location = window.location.href.split("#")[0];
                                                                    }
                                                            );
                                                            //ajax işe yaramadı çünkü sayfa yenilemesini window.location.refresh yerine servlet tarafında yaptım. aslında tarayıcının form gönderme işlemini onayla uyarısı olmasa teme değiştirme tam bir javascript işi. çok basit oluyor o vakit işler. ama o uyarı canımı sıktı :/
                                                            //aslında uyarı vermeden yenileme şansımız olursa eğer.... :)
                                                            //çalıştı :))
                                                        } else if (themeVal == "light") {
                                                            $.get('http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/servletThemeSet?nightModeValue=dark',
                                                                    function () {
                                                                        window.location = window.location.href.split("#")[0];
                                                                    }
                                                            );
                                                        }
                                                    }



//                                                    jQuery(document).ready(
//                                                            //When ThemeButton Click
//                                                                    function () {
//                                                                        jQuery('#changeColor').click(
//                                                                                function () {
//                                                                                    //make light
//                                                                                    if ($('body').hasClass('dark-theme')) {
//                                                                                        //body
//                                                                                        $('body').removeClass('dark-theme');
//                                                                                        $('body').addClass('light-theme');
//                                                                                        //card
//                                                                                        $('.card').removeClass('text-white bg-dark');
//                                                                                        //modal content
//                                                                                        $(".modal-content").removeClass("bg-dark text-white");
//                                                                                        //textarea's
//                                                                                        $("#editorComment").removeClass("bg-dark text-white");
//                                                                                        $("#juryComment").removeClass("bg-dark text-white");
//                                                                                        //jury select
//                                                                                        $(".jurySelection").removeClass("bg-dark text-white");
//                                                                                        //comment button
//                                                                                        $(".yorumButonu").removeClass("btn-secondary");
//                                                                                        $(".yorumButonu").addClass("btn-primary");
//                                                                                        //toggle button
//                                                                                        $(".cardButonu").addClass("btn-success");
//                                                                                        $(".cardButonu").removeClass("btn-secondary");
//                                                                                        //file button
//                                                                                        $(".dosyaButonu").removeClass("btn-dark");
//                                                                                        $(".dosyaButonu").addClass("btn-light");
//                                                                                        //theme button
//                                                                                        $("#changeColor").val("Night Mode is Off");
//                                                                                        $("#changeColor").addClass("btn-dark");
//                                                                                        $("#changeColor").removeClass("btn-light");
//                                                                                        //call the servlet and set the attriute on session level
//                                                                                        changeThemeValue(false);
//
//
//
//
//
//                                                                                        //make dark
//                                                                                    } else {
//                                                                                        //body
//                                                                                        $('body').addClass('dark-theme');
//                                                                                        $('body').removeClass('light-theme');
//                                                                                        //card
//                                                                                        $('.card').addClass('text-white bg-dark');
//                                                                                        //modal content
//                                                                                        $(".modal-content").addClass("bg-dark text-white");
//                                                                                        //textarea's
//                                                                                        $("#editorComment").addClass("bg-dark text-white");
//                                                                                        $("#juryComment").addClass("bg-dark text-white");
//                                                                                        //jury select
//                                                                                        $(".jurySelection").addClass("bg-dark text-white");
//                                                                                        //comment button
//                                                                                        $(".yorumButonu").addClass("btn-secondary");
//                                                                                        $(".yorumButonu").removeClass("btn-primary");
//                                                                                        //toggle button
//                                                                                        $(".cardButonu").removeClass("btn-success");
//                                                                                        $(".cardButonu").addClass("btn-secondary");
//                                                                                        //file button
//                                                                                        $(".dosyaButonu").addClass("btn-dark");
//                                                                                        $(".dosyaButonu").removeClass("btn-light");
//                                                                                        //theme button
//                                                                                        $("#changeColor").val("Night Mode is On");
//                                                                                        $("#changeColor").addClass("btn-ligt");
//                                                                                        $("#changeColor").removeClass("btn-dark");
//                                                                                        //call the servlet and set the attriute on session level
//                                                                                        changeThemeValue(true);
//                                                                                    }
//                                                                                }
//                                                                        );
//                                                                    }
//                                                            );


</script>
<span style='display:none' id='nightModeValue'>${sessionScope.nightModeValue}</span>
</body>
</html>
