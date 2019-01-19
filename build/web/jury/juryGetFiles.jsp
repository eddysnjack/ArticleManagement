<%-- 
    Document   : loginSuccessjury
    Created on : 16.Ara.2018, 12:41:50
    Author     : eddy
--%>

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
        } else if (!ub.getType().equals("jury")) {
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
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servletControlPoint?Select=JuryAllWaiting">
                        Makale yönetim
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
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/jury/profile.jsp">Profili Düzenle</a>
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
    <!-- ==================BUTTTONLAR==================-->
    <span id="SlectedButtonSpan" style="display:none;">${SelectedButton}</span>
<div class="container mx-auto" style="text-align: center;">
    <a class="btn btn-primary" id="btn_YeniGelen" href="${pageContext.request.contextPath}/servletControlPoint?Select=JuryAllWaiting">
        Onay Bekleyenler
        <span class="badge badge-warning badge-pill">${FileNumber1}</span>
    </a>
    <a class="btn btn-success" id="btn_Onaylanmis"name ="heyetOnayi" href="${pageContext.request.contextPath}/servletControlPoint?Select=JuryAllPublished">
        Onaylananlar
        <span class="badge badge-warning badge-pill">${FileNumber2}</span>
    </a>
    <a class="btn btn-danger" id="btn_Reddedilmis" name ="heyetOnayi" href="${pageContext.request.contextPath}/servletControlPoint?Select=JuryAllRejected">
        Reddedlienler
        <span class="badge badge-warning badge-pill">${FileNumber3}</span>
    </a>
</div>


<span>${NoResults}</span>
<div class="accordion  " id="accordionExample" style="width:90%; margin:0 auto; padding:5%;">                
    <c:forEach items="${fileListAttribute}" var="item" varStatus="LoopIndex">
        <div class="card">
            <div class="card-header" id="headingOne"><!-- for cntering!!! margin:0 auto;float:none;-->
                <h5 class="mb-0">
                    <button
                        class="btn btn-success btn-sm"
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
                    <button type="button" class="btn" data-toggle="modal" data-target="#DocumentViewModal${LoopIndex.count}">
                        <img src="${pageContext.request.contextPath}/images/icons8-document-500px.svg" title="Dosyayı Görüntüle" class="img-thumbnail rounded" style="max-width:100px;"/>
                        <br>Dosyayı Görüntüle
                    </button>

                    <div class="modal fade bd-example-modal-lg-deneme" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" id="DocumentViewModal${LoopIndex.count}">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content" style="height: 500px;">
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
                            <img src="${pageContext.request.contextPath}/images/downloadicon.svg" style="max-height: 15px;"/>
                        </a>
                    </div>                </div>
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
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter${LoopIndex.count}">
                        Yorumları Göster/Düzenle
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModalCenter${LoopIndex.count}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
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
                                            <textarea class="form-control"  id="editorComment" name="editorComment" readonly>${item.getEditorComments()}</textarea><br>
                                            <label for="juryComment">JURY COMMENT:</label><br>
                                            <textarea class="form-control"  id="juryComment" name="juryComment">${item.getJuryComments()}</textarea>

                                        </div>

                                        <!-- CheckBox Kısmı KABUL VE RED ${LoopIndex.count}-->
                                        <div class="container form-check-inline">    
                                            <div class="custom-control custom-radio btn btn-success mx-5">
                                                <input type="radio" id="RadioKabul${LoopIndex.count}" name="juryStatusFile" value="confirmed" class="custom-control-input"/>
                                                <label class="custom-control-label " for="RadioKabul${LoopIndex.count}">KABUL ET</label>
                                            </div>
                                            <div class="custom-control custom-radio btn btn-danger mx-5"><!-- mx anlamı x ekseninde(sağve sol) margin ekle 0-5 arası 0-3em arası margin ekliyor https://getbootstrap.com/docs/4.1/utilities/spacing/-->
                                                <input type="radio" id="RadioRet${LoopIndex.count}" name="juryStatusFile" value="rejected" class="btn custom-control-input"/>
                                                <label class="custom-control-label" for="RadioRet${LoopIndex.count}">REDDET</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <input type="hidden" name="fileID" value="${item.getId()}"/>
                                        <input type="submit" class="btn btn-primary" name="JuryCommentEdit" value="Save changes" />
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


<script>
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
</script>

</body>
</html>
