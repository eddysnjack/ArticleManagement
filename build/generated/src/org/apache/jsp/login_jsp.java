package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.javaeddy.userBean;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.javaeddy.DatabaseManager;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"UTF-8\" />\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n");
      out.write("        <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\" />\n");
      out.write("    <link rel=\"stylesheet\" href=\"bootstrap-4.1.3-dist/css/bootstrap.min.css\"/>\n");
      out.write("    <link rel=\"stylesheet\" href=\"CSS/myStyle.css\"/>\n");
      out.write("    <title>Akademik Makale Yönetim Sistemi</title>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    ");

        userBean ub = (userBean) request.getSession().getAttribute("user_bean");
        if (ub != null) {
            response.sendRedirect(request.getContextPath() + "/forbidden.jsp");
        }
    
      out.write("\n");
      out.write("    <nav class=\"navbar navbar-expand-lg navbar-dark bg-dark rounded fixed-top\">\n");
      out.write("        <a class=\"navbar-brand\" href=\"servletFileUpload?getFinalFiles=home\">MAKALE YÖNETİM SİSTEMİ</a>\n");
      out.write("        <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n");
      out.write("            <span class=\"navbar-toggler-icon\"></span>\n");
      out.write("        </button>\n");
      out.write("\n");
      out.write("        <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n");
      out.write("            <ul class=\"navbar-nav mr-auto\">\n");
      out.write("                <li class=\"nav-item\">\n");
      out.write("                    <a class=\"nav-link\" href=\"servletFileUpload?getFinalFiles=home\">Ana Sayfa<span class=\"sr-only\">(current)</span></a>\n");
      out.write("                </li>\n");
      out.write("                <li class=\"nav-item\">\n");
      out.write("                    <a class=\"nav-link\" href=\"servletFileUpload?getFinalFiles=archive\">arşiv</a>\n");
      out.write("                </li>\n");
      out.write("                ");
  if (ub == null) {   
      out.write("\n");
      out.write("                <li class='nav-item active'>                   \n");
      out.write("                    <a class='nav-link' href='login.jsp'>giriş</a>\n");
      out.write("                </li>\n");
      out.write("                <li class='nav-item'>\n");
      out.write("                    <a class='nav-link' href='signup.jsp'>Üye ol</a>\n");
      out.write("                </li>\n");
      out.write("                ");
   }  
      out.write("\n");
      out.write("\n");
      out.write("                ");
if (ub != null) {
      out.write("\n");
      out.write("                <li class='nav-item'>\n");
      out.write("                    <a class='nav-link' href= '");
      out.print(ub.getType());
      out.write("/loginSuccess.jsp'>yönetim</a>\n");
      out.write("                </li>\n");
      out.write("                ");
 }
      out.write("\n");
      out.write("            </ul>\n");
      out.write("            ");
  if (ub != null) {
      out.write("\n");
      out.write("\n");
      out.write("            <div class='form-inline my-2 my-lg-0' style='padding-right: 5%;'>\n");
      out.write("                <ul class='nav mr-auto'>\n");
      out.write("                    <li class='nav-item dropdown'>\n");
      out.write("                        <a class='nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>\n");
      out.write("\n");
      out.write("                            ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${user_bean.username}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\n");
      out.write("\n");
      out.write("                        </a>\n");
      out.write("                        <div class='dropdown-menu dropdown-menu-right' aria-labelledby='navbarDropdown'>\n");
      out.write("                            <a class='dropdown-item' href= '");
      out.print(ub.getType());
      out.write("/profile.jsp'>Profili Düzenle</a>\n");
      out.write("                            <div class='dropdown-divider'></div>\n");
      out.write("                            <form action='servletControlPoint' method='post'>\n");
      out.write("                                <button name='btn_Logout' class='btn btn-outline-danger my-2 my-sm-0' type='submit' style='float:right;'>Çıkış</button>\n");
      out.write("                            </form>\n");
      out.write("                        </div>\n");
      out.write("                    </li>\n");
      out.write("                </ul>\n");
      out.write("            </div>\n");
      out.write("            ");
   }
      out.write("\n");
      out.write("        </div>\n");
      out.write("    </nav>\n");
      out.write("\n");
      out.write("\n");
      out.write("<div class=\"container\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${otherErrors}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</div>\n");
      out.write("<div class=\"container rounded border\" id=\"div_form\" style=\"border-width: 2px !important;\">\n");
      out.write("\n");
      out.write("    <form id=\"form_login\" method=\"post\" action=\"servletControlPoint\">\n");
      out.write("        <div class=\"form-group\">\n");
      out.write("            <label for=\"exampleInputEmail1\">Email address</label>\n");
      out.write("            <input type=\"email\" name=\"email\" required  class=\"form-control\" id=\"exampleInputEmail1\" aria-describedby=\"emailHelp\" placeholder=\"Enter email\" autofocus>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"form-group\">\n");
      out.write("            <label for=\"exampleInputPassword1\">Password</label>\n");
      out.write("            <input type=\"password\" name=\"password\" required  class=\"form-control\" id=\"exampleInputPassword1\" placeholder=\"Password\">\n");
      out.write("                <small id=\"loginError\" class=\"form-text text-muted\"><span style=\"color:red;\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${errMsg}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</span></small>\n");
      out.write("\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <button type=\"submit\" name=\"btn_login\" class=\"btn btn-primary\">Submit</button>\n");
      out.write("    </form>\n");
      out.write("\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/JS/jquery-3.3.1.js\" ></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/bootstrap-4.1.3-dist/popper.min.js\"></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/bootstrap-4.1.3-dist/js/bootstrap.min.js\"></script>\n");
      out.write("\n");
      out.write("<script>  \n");
      out.write("    //\n");
      out.write("    //\n");
      out.write("    //\n");
      out.write("    ////-------------------------arka plan rengini değiştir\n");
      out.write("    //Ready ile eşdeğer:\n");
      out.write("    $(function controlTheme() {\n");
      out.write("        if ($(\"#NightModeValue\").text() === \"true\") {\n");
      out.write("            nightOn();\n");
      out.write("        } else if ($(\"#NightModeValue\").text() === \"false\") {\n");
      out.write("            nightOff();\n");
      out.write("        }\n");
      out.write("    });\n");
      out.write("    //-----aydınlık\n");
      out.write("    function nightOff() {\n");
      out.write("        //body\n");
      out.write("        $('body').removeClass('dark-theme');\n");
      out.write("        $('body').addClass('light-theme');\n");
      out.write("        //card\n");
      out.write("        $('.card').removeClass('text-white bg-dark');\n");
      out.write("        //modal content\n");
      out.write("        $(\".modal-content\").removeClass(\"bg-dark text-white\");\n");
      out.write("        //textarea's\n");
      out.write("        $(\"#editorComment\").removeClass(\"bg-dark text-white\");\n");
      out.write("        $(\"#juryComment\").removeClass(\"bg-dark text-white\");\n");
      out.write("        //jury select\n");
      out.write("        $(\".jurySelection\").removeClass(\"bg-dark text-white\");\n");
      out.write("        //comment button\n");
      out.write("        $(\".yorumButonu\").removeClass(\"btn-secondary\");\n");
      out.write("        $(\".yorumButonu\").addClass(\"btn-primary\");\n");
      out.write("        //toggle button\n");
      out.write("        $(\".cardButonu\").addClass(\"btn-success\");\n");
      out.write("        $(\".cardButonu\").removeClass(\"btn-secondary\");\n");
      out.write("        //file button\n");
      out.write("        $(\".dosyaButonu\").removeClass(\"btn-dark\");\n");
      out.write("        $(\".dosyaButonu\").addClass(\"btn-light\");\n");
      out.write("        //theme button\n");
      out.write("        $(\"#changeColor\").val(\"Night Mode is Off\");\n");
      out.write("        $(\"#changeColor\").addClass(\"btn-dark\");\n");
      out.write("        $(\"#changeColor\").removeClass(\"btn-light\");\n");
      out.write("    }\n");
      out.write("    //----karanlık\n");
      out.write("    function nightOn() {\n");
      out.write("//body\n");
      out.write("        $('body').addClass('dark-theme');\n");
      out.write("        $('body').removeClass('light-theme');\n");
      out.write("        //card\n");
      out.write("        $('.card').addClass('text-white bg-dark');\n");
      out.write("        //modal content\n");
      out.write("        $(\".modal-content\").addClass(\"bg-dark text-white\");\n");
      out.write("        //textarea's\n");
      out.write("        $(\"#editorComment\").addClass(\"bg-dark text-white\");\n");
      out.write("        $(\"#juryComment\").addClass(\"bg-dark text-white\");\n");
      out.write("        //jury select\n");
      out.write("        $(\".jurySelection\").addClass(\"bg-dark text-white\");\n");
      out.write("        //comment button\n");
      out.write("        $(\".yorumButonu\").addClass(\"btn-secondary\");\n");
      out.write("        $(\".yorumButonu\").removeClass(\"btn-primary\");\n");
      out.write("        //toggle button\n");
      out.write("        $(\".cardButonu\").removeClass(\"btn-success\");\n");
      out.write("        $(\".cardButonu\").addClass(\"btn-secondary\");\n");
      out.write("        //file button\n");
      out.write("        $(\".dosyaButonu\").addClass(\"btn-dark\");\n");
      out.write("        $(\".dosyaButonu\").removeClass(\"btn-light\");\n");
      out.write("        //theme button\n");
      out.write("        $(\"#changeColor\").val(\"Night Mode is On\");\n");
      out.write("        $(\"#changeColor\").addClass(\"btn-ligt\");\n");
      out.write("        $(\"#changeColor\").removeClass(\"btn-dark\");\n");
      out.write("    }\n");
      out.write("    function changeThemeValue(attrbute) {\n");
      out.write("        $.get('http://");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.serverName}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write(':');
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.localPort}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/servletThemeSet?NightModeValue=' + attrbute);\n");
      out.write("    }\n");
      out.write("    jQuery(document).ready(\n");
      out.write("            //When ThemeButton Click\n");
      out.write("                    function () {\n");
      out.write("                        jQuery('#changeColor').click(\n");
      out.write("                                function () {\n");
      out.write("                                    //make light\n");
      out.write("                                    if ($('body').hasClass('dark-theme')) {\n");
      out.write("                                        //body\n");
      out.write("                                        $('body').removeClass('dark-theme');\n");
      out.write("                                        $('body').addClass('light-theme');\n");
      out.write("                                        //card\n");
      out.write("                                        $('.card').removeClass('text-white bg-dark');\n");
      out.write("                                        //modal content\n");
      out.write("                                        $(\".modal-content\").removeClass(\"bg-dark text-white\");\n");
      out.write("                                        //textarea's\n");
      out.write("                                        $(\"#editorComment\").removeClass(\"bg-dark text-white\");\n");
      out.write("                                        $(\"#juryComment\").removeClass(\"bg-dark text-white\");\n");
      out.write("                                        //jury select\n");
      out.write("                                        $(\".jurySelection\").removeClass(\"bg-dark text-white\");\n");
      out.write("                                        //comment button\n");
      out.write("                                        $(\".yorumButonu\").removeClass(\"btn-secondary\");\n");
      out.write("                                        $(\".yorumButonu\").addClass(\"btn-primary\");\n");
      out.write("                                        //toggle button\n");
      out.write("                                        $(\".cardButonu\").addClass(\"btn-success\");\n");
      out.write("                                        $(\".cardButonu\").removeClass(\"btn-secondary\");\n");
      out.write("                                        //file button\n");
      out.write("                                        $(\".dosyaButonu\").removeClass(\"btn-dark\");\n");
      out.write("                                        $(\".dosyaButonu\").addClass(\"btn-light\");\n");
      out.write("                                        //theme button\n");
      out.write("                                        $(\"#changeColor\").val(\"Night Mode is Off\");\n");
      out.write("                                        $(\"#changeColor\").addClass(\"btn-dark\");\n");
      out.write("                                        $(\"#changeColor\").removeClass(\"btn-light\");\n");
      out.write("                                        //call the servlet and set the attriute on session level\n");
      out.write("                                        changeThemeValue(false);\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("                                        //make dark\n");
      out.write("                                    } else {\n");
      out.write("                                        //body\n");
      out.write("                                        $('body').addClass('dark-theme');\n");
      out.write("                                        $('body').removeClass('light-theme');\n");
      out.write("                                        //card\n");
      out.write("                                        $('.card').addClass('text-white bg-dark');\n");
      out.write("                                        //modal content\n");
      out.write("                                        $(\".modal-content\").addClass(\"bg-dark text-white\");\n");
      out.write("                                        //textarea's\n");
      out.write("                                        $(\"#editorComment\").addClass(\"bg-dark text-white\");\n");
      out.write("                                        $(\"#juryComment\").addClass(\"bg-dark text-white\");\n");
      out.write("                                        //jury select\n");
      out.write("                                        $(\".jurySelection\").addClass(\"bg-dark text-white\");\n");
      out.write("                                        //comment button\n");
      out.write("                                        $(\".yorumButonu\").addClass(\"btn-secondary\");\n");
      out.write("                                        $(\".yorumButonu\").removeClass(\"btn-primary\");\n");
      out.write("                                        //toggle button\n");
      out.write("                                        $(\".cardButonu\").removeClass(\"btn-success\");\n");
      out.write("                                        $(\".cardButonu\").addClass(\"btn-secondary\");\n");
      out.write("                                        //file button\n");
      out.write("                                        $(\".dosyaButonu\").addClass(\"btn-dark\");\n");
      out.write("                                        $(\".dosyaButonu\").removeClass(\"btn-light\");\n");
      out.write("                                        //theme button\n");
      out.write("                                        $(\"#changeColor\").val(\"Night Mode is On\");\n");
      out.write("                                        $(\"#changeColor\").addClass(\"btn-ligt\");\n");
      out.write("                                        $(\"#changeColor\").removeClass(\"btn-dark\");\n");
      out.write("                                        //call the servlet and set the attriute on session level\n");
      out.write("                                        changeThemeValue(true);\n");
      out.write("                                    }\n");
      out.write("                                }\n");
      out.write("                        );\n");
      out.write("                    }\n");
      out.write("            );\n");
      out.write("\n");
      out.write("\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("<span style='display:none' id='NightModeValue'>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${sessionScope.NightModeValue}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</span>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
