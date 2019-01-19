<%-- 
    Document   : sendFile
    Created on : 20.Ara.2018, 14:26:35
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/myStyle.css"/>
    <title>Akademik Makale Yönetim Sistemi</title>

</head>
<body>


    <%
        userBean ub = (userBean) request.getSession().getAttribute("user_bean");
        if (ub == null) {
            response.sendRedirect(request.getContextPath() + "/forbidden.jsp");
        } else if (!ub.getType().equals("writer")) {
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
                <li class="nav-item ">
                    <a class="nav-link" href="servletFileUpload?getFinalFiles=home">Ana Sayfa</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="servletFileUpload?getFinalFiles=archive">arşiv</a>
                </li>

                <%if (ub != null && (ub.getType()).equals("editor")) {%>
                <li class='nav-item'>
                    <a class='nav-link' href='${pageContext.request.contextPath}/servletControlPoint?Select=AllEditorWaiting'>
                        Makale yönetim
                    </a>
                </li>
                <% }%>

                <%if (ub != null && (ub.getType()).equals("writer")) {%>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/servletControlPoint?Select=WriterAllWaiting">
                        Makale yönetim
                    </a>
                </li>
                <li class='nav-item active'>
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
            <div class="form-inline my-2 my-lg-0" style="padding-right: 5%;">
                <ul class="nav mr-auto">
                    <li class="nav-item dropdown">
                        <a class="btn btn-info nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${user_bean.username}
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="profile.jsp">Profili Düzenle</a>
                            <div class="dropdown-divider"></div>
                            <form action="../servletControlPoint" method="post">
                                <button name="btn_Logout" class="btn btn-outline-danger my-2 my-sm-0" type="submit" style="float:right;">Çıkış</button>
                            </form>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </nav>


    <span id="resultMessage">${UploadMessage}</span>

<div class="form-group" style="margin:0 auto;text-align: center;padding-top: 10em;">
    <form style="" class="was-validated" action="${pageContext.request.contextPath}/servletFileUpload" method="post" accept-charset="UTF-8" enctype="multipart/form-data">

        <input type="text" class="form-control mx-auto" name="fileTitle" placeholder="Dosya Adını Giriniz" required style="width:50%;"/>
        <br>
        <div class="custom-file" style="width:50%"> <!-- Bu element komple bir file input!!!!!! bir bütün halindeler -->
            <input
                type="file"
                class="custom-file-input"
                id="validatedCustomFile"
                required
                name="SelectedFile"
                />
            <label class="custom-file-label" for="validatedCustomFile" id="inputGroupFile02">
                Choose file...
            </label>

        </div>


        <br>
        <input type="hidden" name="userid" value="${user_bean.id}"/>
        <br/>
        
        <br>
        <input class="btn btn-outline-info" type="submit" name="btn_FileUpload" value="Gönder"/>
    </form>
</div>




<!-- ================SCRIPTS=================-->
<script src="${pageContext.request.contextPath}/JS/jquery-3.3.1.js" ></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>



<script>
    $("[name=btn_FileUpload]").click(
            setTimeout(function () {
                $("div.alert").fadeOut(500);
            }, 2000)
            );
</script>

</body>
</html>
