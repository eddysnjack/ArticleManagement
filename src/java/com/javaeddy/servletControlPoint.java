/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaeddy;

import com.mysql.jdbc.Connection;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author eddy
 */
public class servletControlPoint extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processLoginRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            Connection conn = DatabaseManager.getConnection();

            if (conn != null) {
                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
                    ps.setString(1, email);
                    ps.setString(2, pass);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        if (rs.getString("editorConfirm").equals("waiting")) {

                            request.setAttribute("otherErrors", "<div class='alert alert-info alert-dismissible fade show' role='alert' style='text-align:center;'>\n"
                                    + "Editör Onayı Bekliyorum. Gelsin, hemen alacağım içeri hocam.\n"
                                    + "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                                    + "<span aria-hidden='true'>&times;</span>\n"
                                    + "</button>\n"
                                    + "</div>");
                            // The following will keep you in the login page
                            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                            rd.forward(request, response);

                        } else if (rs.getString("editorConfirm").equals("rejected")) {

                            request.setAttribute("otherErrors", "<div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center;'>\n"
                                    + "<strong>Laf aramızda ama galiba buralarda fazla sevilmiyorsun.</strong><br>Editör onay vermemiş abi.\n"
                                    + "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                                    + "<span aria-hidden='true'>&times;</span>\n"
                                    + "</button>\n"
                                    + "</div>");
                            // The following will keep you in the login page
                            RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                            rd.forward(request, response);

                        } else {
                            request.setAttribute("errMsg", "");

                            userBean ub = new userBean();
                            ub.setUsername(rs.getString("username"));
                            ub.setEmail(rs.getString("email"));
                            ub.setProfession(rs.getString("profession"));
                            ub.setType(rs.getString("type"));
                            ub.setId(rs.getInt("id"));
                            request.getSession().setAttribute("user_bean", ub);
                            //Theme Value'yu ayarla
                            String nightModeValue = "dark";
                            request.getSession().setAttribute("nightModeValue", nightModeValue);

                            String userType = rs.getString("type");
                            if (userType.equals("editor")) {
//                                response.sendRedirect(request.getContextPath()+"/servletControlPoint?Select=AllEditorWaiting");
                                getEditorAllEditorWaiting(request, response);
                            } else if (userType.equals("writer")) {
                                getWriterAllWaiting(request, response, ub);
                            } else if (userType.equals("jury")) {
                                getJuryAllWaiting(request, response, ub);
                            } else {
                                response.sendRedirect("index.jsp");
                            }
                        }

                    } else {
                        request.setAttribute("errMsg", "Kullanici Adı veya Şifre Hatalı");
                        // The following will keep you in the login page
                        RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
                        rd.forward(request, response);
                    }

                    conn.close();
                    DatabaseManager.closeConnection();
                } catch (Exception e) {
                    System.out.println("Exception" + e);
                }

            }

            //=================================END METHOD
        }
    }

    protected void processLogOutRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            request.getRequestDispatcher("/index.jsp").forward(request, response);

            //=================================END METHOD
        }
    }

    protected void processSignUpRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/plain;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            try {
                String email = request.getParameter("email");
                Connection conn = DatabaseManager.getConnection();
                if (conn != null) {
                    PreparedStatement psFirst = conn.prepareStatement("SELECT * FROM users WHERE type='editor';");
                    ResultSet rsFirst = psFirst.executeQuery();
                    //hiç editör yoksa ilk editör olarak kaydolan kişi beklemeden onaylı editör olsun
                    if (!rsFirst.first() && request.getParameter("usertype").equals("editor")) {
                        psFirst.close();

                        String username = request.getParameter("name");
                        String pass = request.getParameter("password");
                        String type = request.getParameter("usertype");
                        String profession = request.getParameter("profession");
                        psFirst = conn.prepareStatement("INSERT INTO users(username, password, email, type, profession, editorConfirm) "
                                + "VALUES(?,?,?,?,?,?)");
                        psFirst.setString(1, username);
                        psFirst.setString(2, pass);
                        psFirst.setString(3, email);
                        psFirst.setString(4, type);
                        psFirst.setString(5, profession);
                        psFirst.setString(6, "SelfApproved");

                        psFirst.execute();
                        //eğer editör varsa standart prosedüre devam et
                    } else {
                        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email=?");
                        ps.setString(1, email);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            request.setAttribute("errMsg", "email is exist");
                            // The following will keep you in the login page
                            RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
                            rd.forward(request, response);
                        } else {
                            String username = request.getParameter("name");
                            String pass = request.getParameter("password");
                            String type = request.getParameter("usertype");
                            String profession = request.getParameter("profession");
                            ps = conn.prepareStatement("INSERT INTO users(username, password, email, type, profession, editorConfirm) "
                                    + "VALUES(?,?,?,?,?,?)");
                            ps.setString(1, username);
                            ps.setString(2, pass);
                            ps.setString(3, email);
                            ps.setString(4, type);
                            ps.setString(5, profession);
                            ps.setString(6, "waiting");

                            ps.execute();

                        }
                    }

                    conn.close();
                    DatabaseManager.closeConnection();
                    request.setAttribute("SuccesfullMessage", "<div class='alert alert-success alert-dismissible fade show' role='alert' style=\"text-align:center;\">\n"
                            + "  <strong>Kayıt Başarılı!</strong> Editör Onayı Aldıktan Sonra Giriş Yapabilirsiniz.\n"
                            + "  <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                            + "    <span aria-hidden='true'>&times;</span>\n"
                            + "  </button>\n"
                            + "</div>");
                    // The following will keep you in the login page
                    RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
                    rd.forward(request, response);

                }
                //=================================END METHOD
            } catch (SQLException ex) {
                Logger.getLogger(servletControlPoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//
//
//----------------------------------------------------------------------------------------------------------------------------
//                                                   EDITOR STUFFF 
//----------------------------------------------------------------------------------------------------------------------------

    protected void getEditorAllEditorWaiting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*
            burada normalde diğer tüm metotlarda olduğu gibi şartlı bir try vardı.
            response objesinin writer'ı alınıp(diğer metotlardaki durum) işi 
            bitince de kapatılıyordu. şu "try with resources" meselesi işte. tam da bu yüzden
            yaklaşık şöyle rahat iki saattir kafayı yiyorum :) jsp başladığında
            <jsp:include..> ile bu servleti ve bu metodu çağırıyorum ama stream closed
            hatası alıyorum ve sayfam yüklenmiyor. nette iki saattir araştırıyorum ve sebebini bulamıyorum
            sadece get metodu içerisinde denemeler yapınca attribute ayarlıyorum
            sorun yok, out.print diyorum yine sorun yok. ama buraya gönderince hata
            alıyorum. debug yapıyorum hiç bir yerde hata yok. sql komutları şakır şakır çalışıyor
            çıldırmak üzereydim ki şöyle sakin kafayla bir an durup debug yaparken hatayı aldığım son satırı düşündüm.
            en son servlet işini bitirince direkt jsp'nin son satırına geliyorduk.
            sonra birden dang etti response'un writer objesi ile işimiz bitti sanıp
            kapatıyporduk ya la! writer olmayınca jsp nasıl yazsın yazacağını O_O
            Holy F Sh! diyorum :) 18.12.2018-23.50
            
            Ertesi gün gelen Ekleme:
            aynı hatayı başka bir yoldan daha alınca tekrar googleDa bir arama yaptım ve mucizevi 
            bir şekilde karşıma şu sayfa çıktı :https://stackoverflow.com/questions/10431674/getting-exception-while-invoking-jsp-from-a-servlet
            geçen bu sayfaya denk gelmek için neler vermezdim :) her neyse
            sorun düşündüğümden daha farklı imiş. request ve response objelerinin kapatılmması kısmını
            sanırım kısmen de olsa tutturmuşum ama asıl soruna neden olan şey kapatılması değil
            bir şekilde servletin jsp'yi, jspnin ise servleti çağırdığı bir kısır döngüye
            sebep olmam imiş. jsp servleti çağırıyor o da sonucu alıp tekrar jsp'e yolluyor.
            bu böyle dönüp gidiyormuş meğer. doğrusu ise önce direkt servleti çağırmk ardından jspi çağırmak imiş.
            şu include bilmem ne zımbırtısını da kaldıracakmışız tabi ki. neyse doğru yolu bulduk sonunda :)
            kurtuluşa erenlerdeniz artık :) 19.12.2018-23.43
             */

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForEditor("SELECT *, CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files where editorStatus='waiting'");

            if (!fileList.isEmpty()) {
                request.setAttribute("fileListAttribute", fileList);

                //eğer dosya varsa o zaman juri üyelerini de ekle
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                request.setAttribute("ListOfJuries", getStat.getJuryMembers());
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE editorstatus='waiting';");
                request.setAttribute("FileNumber1", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }

            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "YeniGelen");

            request.getRequestDispatcher("editor/getNewOnes.jsp").forward(request, response);
            //=================================END METHOD

            /* NOT:
            stackoverflowdan hatırladığım kadarıyla include response ve requesti 
            olduğu gibi iletiyordu. forward ise yeni bir response ve request oluşturuyordu. 
            burda include metodu response'u korur demiş.(şağıdaki dökümantasyon) hangisini kullanmak daha 
            mantıklı diye merak ettim de. yine de pek bir şey anlamadım. ben işimi gördüğü
            sürece forward kullanacağım sanırım. dökümantasyonu da aşağıya bırakıyorum. ilerde lazım olur belki :)
            
                ===============================request.getRequestDispatcher("/").include(request, response);

                public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException
                Includes the content of a resource (servlet, JSP page, HTML file) in the response. In essence, this method enables programmatic server-side includes.
                The ServletResponse object has its path elements and parameters remain unchanged from the caller's. The included servlet cannot change the response status code or set headers; any attempt to make a change is ignored.
                The request and response parameters must be either the same objects as were passed to the calling servlet's service method or be subclasses of the ServletRequestWrapper or ServletResponseWrapper classes that wrap them.
                This method sets the dispatcher type of the given request to DispatcherType.INCLUDE.
                Parameters:
                request - a ServletRequest object that contains the client's request
                response - a ServletResponse object that contains the servlet's response
                Throws:
                ServletException - if the included resource throws this exception
                IOException - if the included resource throws this exception
                See Also:
                ServletRequest.getDispatcherType()

                ===============================request.getRequestDispatcher("/").forward(request, response);

                public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException
                Forwards a request from a servlet to another resource (servlet, JSP file, or HTML file) on the server. This method allows one servlet to do preliminary processing of a request and another resource to generate the response.
                For a RequestDispatcher obtained via getRequestDispatcher(), the ServletRequest object has its path elements and parameters adjusted to match the path of the target resource.
                forward should be called before the response has been committed to the client (before response body output has been flushed). If the response already has been committed, this method throws an IllegalStateException. Uncommitted output in the response buffer is automatically cleared before the forward.
                The request and response parameters must be either the same objects as were passed to the calling servlet's service method or be subclasses of the ServletRequestWrapper or ServletResponseWrapper classes that wrap them.
                This method sets the dispatcher type of the given request to DispatcherType.FORWARD.
                Parameters:
                request - a ServletRequest object that represents the request the client makes of the servlet
                response - a ServletResponse object that represents the response the servlet returns to the client
                Throws:
                ServletException - if the target resource throws this exception
                IOException - if the target resource throws this exception
                IllegalStateException - if the response was already committed
                See Also:
                ServletRequest.getDispatcherType()
             */
        }
    }

    protected void getEditorAllJuryWaiting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForEditor("SELECT * , CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files WHERE juryStatus='waiting' AND editorStatus='confirmed'");

            if (!fileList.isEmpty()) {
                request.setAttribute("fileListAttribute", fileList);

                //eğer dosya varsa o zaman juri üyelerini de ekle
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                request.setAttribute("ListOfJuries", getStat.getJuryMembers());
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE juryStatus='waiting' AND editorStatus='confirmed';");
                request.setAttribute("FileNumber2", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }

            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "HeyetOnayiBekleyen");

            request.getRequestDispatcher("editor/getNewOnes.jsp").forward(request, response);
            //=================================END METHOD
        }
    }

    protected void getEditorAllPublished(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForEditor("SELECT *, CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC`  FROM files WHERE finalStatus='confirmed'");

            if (!fileList.isEmpty()) {

                request.setAttribute("fileListAttribute", fileList);
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE finalStatus='confirmed';");
                request.setAttribute("FileNumber3", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }

            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "Onaylanmis");

            request.getRequestDispatcher("editor/getPublished.jsp").forward(request, response);
            //=================================END METHOD
        }
    }

    protected void getEditorAllRejected(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForEditor("SELECT * , CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files WHERE finalStatus='rejected'");

            if (!fileList.isEmpty()) {
                request.setAttribute("fileListAttribute", fileList);

                //eğer dosya varsa o zaman juri üyelerini de ekle
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                request.setAttribute("ListOfJuries", getStat.getJuryMembers());
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE finalStatus='rejected';");
                request.setAttribute("FileNumber4", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }

            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "Reddedilmis");

            request.getRequestDispatcher("editor/getPublished.jsp").forward(request, response);
            //=================================END METHOD
        }
    }

    protected void processEditorCommentChanged(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started    
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps;
            if (conn != null) {
                try {
                    if (request.getParameter("editorStatusFile").equals("confirmed")) {
                        ps = conn.prepareStatement("UPDATE files SET editorComments= ? , editorStatus= ?, jury= ? WHERE id = ?");
                    } else {//editör reddetti ise direkt reddedilmiş olsun
                        ps = conn.prepareStatement("UPDATE files SET editorComments= ? , editorStatus= ?, finalStatus='rejected', jury= ? WHERE id = ?");
                    }
                    ps.setString(1, request.getParameter("editorComment"));
                    ps.setString(2, request.getParameter("editorStatusFile"));
                    //Eğer reddedilmiş ise boşuna juri id'si ayarlamış olmayalım diye böyle bir çözüm geliştirdik. aksi halde farklı bir sql statement yazmak gerekecekti
                    String juryid = request.getParameter("juryMember");
                    if (juryid.equals("null")) {
                        ps.setNull(3, java.sql.Types.NULL);
                    } else if (request.getParameter("editorStatusFile").equals("rejected")) {
                        ps.setNull(3, java.sql.Types.NULL);
                    } else {
                        ps.setInt(3, Integer.parseInt(juryid));
                    }

                    ps.setInt(4, Integer.parseInt(request.getParameter("fileID")));
                    if (ps.execute()) {
                        conn.close();
                        DatabaseManager.closeConnection();

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(servletControlPoint.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

            getEditorAllEditorWaiting(request, response);
            //=================================END METHOD
        }
    }
//
//
//----------------------------------------------------------------------------------------------------------------------------
//                                                   WRITER STUFFF 
//----------------------------------------------------------------------------------------------------------------------------

    protected void getWriterAllWaiting(HttpServletRequest request, HttpServletResponse response, userBean requestUB) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForWriter("SELECT * , CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files WHERE editorStatus='waiting' AND writer= ? ", requestUB);

            if (!fileList.isEmpty()) {

                request.setAttribute("fileListAttribute", fileList);
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE editorStatus='waiting' AND writer= " + requestUB.getId());
                request.setAttribute("FileNumber1", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }
            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "YeniGelen");

            request.getRequestDispatcher("writer/writerGetFiles.jsp").forward(request, response);
            //=================================END METHOD
        }
    }

    protected void getWriterAllEditorApproved(HttpServletRequest request, HttpServletResponse response, userBean requestUB) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForWriter("SELECT * , CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files WHERE editorStatus='confirmed' AND juryStatus='waiting' AND writer= ? ", requestUB);

            if (!fileList.isEmpty()) {

                request.setAttribute("fileListAttribute", fileList);
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE editorStatus='confirmed' AND juryStatus='waiting' AND writer= " + requestUB.getId());
                request.setAttribute("FileNumber2", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }
            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "HeyetOnayiBekleyen");

            request.getRequestDispatcher("writer/writerGetFiles.jsp").forward(request, response);
            //=================================END METHOD
        }
    }

    protected void getWriterAllPublished(HttpServletRequest request, HttpServletResponse response, userBean requestUB) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForWriter("SELECT * , CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files WHERE finalStatus='confirmed' AND writer= ? ", requestUB);

            if (!fileList.isEmpty()) {

                request.setAttribute("fileListAttribute", fileList);
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE finalStatus='confirmed' AND writer= " + requestUB.getId());
                request.setAttribute("FileNumber3", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }

            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "Onaylanmis");

            request.getRequestDispatcher("writer/writerGetFiles.jsp").forward(request, response);
            //=================================END METHOD
        }
    }

    protected void getWriterAllRejected(HttpServletRequest request, HttpServletResponse response, userBean requestUB) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForWriter("SELECT * , CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files WHERE finalStatus='rejected' AND writer= ? ", requestUB);

            if (!fileList.isEmpty()) {

                request.setAttribute("fileListAttribute", fileList);
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE finalStatus='rejected' AND writer= " + requestUB.getId());
                request.setAttribute("FileNumber4", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }

            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "Reddedilmis");

            request.getRequestDispatcher("writer/writerGetFiles.jsp").forward(request, response);
            //=================================END METHOD
        }
    }
//
//
//----------------------------------------------------------------------------------------------------------------------------
//                                                   JURY STUFFF 
//----------------------------------------------------------------------------------------------------------------------------

    protected void getJuryAllWaiting(HttpServletRequest request, HttpServletResponse response, userBean requestUB) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForJury("SELECT * , CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files WHERE juryStatus='waiting' AND jury= ? ", requestUB);

            if (!fileList.isEmpty()) {

                request.setAttribute("fileListAttribute", fileList);
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE juryStatus='waiting' AND jury= " + requestUB.getId());
                request.setAttribute("FileNumber1", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }

            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "YeniGelen");

            request.getRequestDispatcher("jury/juryGetFiles.jsp").include(request, response);

            //=================================END METHOD
        }
    }

    protected void getJuryAllPublished(HttpServletRequest request, HttpServletResponse response, userBean requestUB) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForJury("SELECT *, CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files WHERE juryStatus='confirmed' AND jury= ? ", requestUB);

            if (!fileList.isEmpty()) {

                request.setAttribute("fileListAttribute", fileList);
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE juryStatus='confirmed' AND jury= " + requestUB.getId());
                request.setAttribute("FileNumber2", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }

            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "Onaylanmis");

            request.getRequestDispatcher("jury/juryGetFinished.jsp").include(request, response);
            //=================================END METHOD
        }
    }

    protected void getJuryAllRejected(HttpServletRequest request, HttpServletResponse response, userBean requestUB) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started     
            List<fileBean> fileList = new ArrayList();

            GetFilesFromDatabase gf = new GetFilesFromDatabase();
            fileList = gf.GetFilesForWriter("SELECT * , CONVERT_TZ(`sendingDate`,@@session.time_zone,'+00:00') as `sendingDateUTC` FROM files WHERE juryStatus='rejected' AND jury= ? ", requestUB);

            if (!fileList.isEmpty()) {

                request.setAttribute("fileListAttribute", fileList);
                //dosya sayısı bilgisin pakete dahil et kardeşim:
                GetStatsFromDatabase getStat = new GetStatsFromDatabase();
                int FileNumber = getStat.getFileCount("SELECT COUNT(id) as FileNumber FROM files WHERE juryStatus='rejected' AND jury= " + requestUB.getId());
                request.setAttribute("FileNumber3", FileNumber);
            } else {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Sonuç Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            }

            //seçili buton belirlemek için etiket ekle
            request.setAttribute("SelectedButton", "Reddedilmis");

            request.getRequestDispatcher("jury/juryGetFinished.jsp").include(request, response);
            //=================================END METHOD
        }
    }

    protected void processJuryCommentChanged(HttpServletRequest request, HttpServletResponse response, userBean requestUB) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started    
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps;
            if (conn != null) {
                try {
                    // juri de onaylamışsa tamamdır bu iş final statusu de confirmed yap.
                    if (request.getParameter("juryStatusFile").equals("confirmed")) {
                        ps = conn.prepareStatement("UPDATE files SET juryComments= ? , juryStatus= ? , finalStatus='confirmed' WHERE id = ?");
                        //onay yoksa final rejected olsun
                    } else {
                        ps = conn.prepareStatement("UPDATE files SET juryComments= ? , juryStatus= ? , finalStatus='rejected' WHERE id = ?");
                    }

                    ps.setString(1, request.getParameter("juryComment"));
                    ps.setString(2, request.getParameter("juryStatusFile"));

                    ps.setInt(3, Integer.parseInt(request.getParameter("fileID")));
                    if (ps.execute()) {
                        conn.close();
                        DatabaseManager.closeConnection();

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(servletControlPoint.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }

            getJuryAllWaiting(request, response, requestUB);
//        request.getRequestDispatcher("editor/loginSuccess.jsp").include(request, response);
            //=================================END METHOD
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
//        request.setAttribute("deneme", request.getServletContext().getEffectiveMinorVersion() +"AND"+request.getServletContext().getEffectiveMajorVersion());

        if (request.getSession().getAttribute("user_bean") != null) {

            userBean ub = (userBean) request.getSession().getAttribute("user_bean");
            if (request.getParameter("Select") != null) {

                if (ub.getType().equals("editor")) {
                    switch (request.getParameter("Select")) {
                        case "AllEditorWaiting":
                            getEditorAllEditorWaiting(request, response);
                            break;
                        case "AllJuryWaiting":
                            getEditorAllJuryWaiting(request, response);
                            break;
                        case "Published":
                            getEditorAllPublished(request, response);
                            break;
                        case "EditorRejected":
                            getEditorAllRejected(request, response);
                            break;
                        default:
                            break;
                    }
                }

                if (ub.getType().equals("writer")) {
                    if (request.getParameter("Select").equals("WriterAllWaiting")) {
                        getWriterAllWaiting(request, response, ub);
                    } else if (request.getParameter("Select").equals("WriterAllEditorApproved")) {
                        getWriterAllEditorApproved(request, response, ub);
                    } else if (request.getParameter("Select").equals("WriterAllPublished")) {
                        getWriterAllPublished(request, response, ub);
                    } else if (request.getParameter("Select").equals("WriterAllRejected")) {
                        getWriterAllRejected(request, response, ub);
                    }
                }
//
                if (ub.getType().equals("jury")) {
                    if (request.getParameter("Select").equals("JuryAllWaiting")) {
                        getJuryAllWaiting(request, response, ub);
                    }
                    if (request.getParameter("Select").equals("JuryAllPublished")) {
                        getJuryAllPublished(request, response, ub);
                    }
                    if (request.getParameter("Select").equals("JuryAllRejected")) {
                        getJuryAllRejected(request, response, ub);
                    }
                }
            }

        }
        if (request.getSession().getAttribute("user_bean") == null) {
            response.sendRedirect("forbidden.jsp");
        }
        try (PrintWriter out = response.getWriter()) {
            out.print("BAŞARILI");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // türkçe karakterleri okuyabilmek için.
        //giriş çıkış işlemleri ile ilgili kısımlar
        if (request.getParameter("btn_login") != null) {
            processLoginRequest(request, response);
        } else if (request.getParameter("btn_Logout") != null) {
            processLogOutRequest(request, response);
        } else if (request.getParameter("btn_signup") != null) {
            processSignUpRequest(request, response);
        }

        //Giriş yapıldıktan sonraki işlemler
        if (request.getSession().getAttribute("user_bean") != null) {
            userBean ub = (userBean) request.getSession().getAttribute("user_bean");
            if (ub.getType().equals("editor")) {
                if (request.getParameter("EditorCommentEdit") != null) {
                    processEditorCommentChanged(request, response);
                }
            } else if (ub.getType().equals("jury")) {
                if (request.getParameter("JuryCommentEdit") != null) {
                    processJuryCommentChanged(request, response, ub);
                }
            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
