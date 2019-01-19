/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaeddy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author eddy
 */
public class servletFileUpload extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processFileUploadRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            //=====================Method Started  
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);//form elementinin bilgileri parça parça göndermesi lazım

            String formitemUserID = "";

            String fileName = "";
            File uploadedFile = null;
            String fileTitle = "";
            File path = new File("/_temp files/ServerUploads");//uploads klasörünü ekle
            if (isMultipart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();//standart commons örneklerindeki disk objesi
                factory.setSizeThreshold(1024 * 1024); //ara bellek boyutu(byte cinsinden yazılıyor)Burdaki 1MB Ediyor. https://commons.apache.org/proper/commons-fileupload/javadocs/api-release/org/apache/commons/fileupload/disk/DiskFileItemFactory.html#setSizeThreshold-int-

                ServletFileUpload upload = new ServletFileUpload(factory);//upload objesi
                upload.setHeaderEncoding("UTF-8");//dosya isimlerinde türkçe karakter olması için
                upload.setFileSizeMax(1024 * 1024 * 500);//Maximum dosya boyutu 500 MB
                try {
                    List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
                    //listenin üzerinde direkt gezme imkanımız yok. iterator ile gezeceğiz.
                    //gelen parçaları Fileitem cinsinden alıyoruz. burda request objesini iki aşamada parse etmişiz. direkt çeviremiyoruz. denedim olmadı

                    for (FileItem item : items) {
                        //Listedeki her bir parçayı al.
                        if (!item.isFormField()) {
                            fileName = item.getName();

//      iptal edildi                      String root = getServletContext().getRealPath("/");//gerçek disk üzerndeki kök dizini yolunu al
//      iptal edildi                      String parentDir = new File(root).getParent();
                            if (!path.exists()) {//bu klasör yoksa
                                path.mkdir();//oluştur.
                            }
                            //DOSYA SONUNA TARIH EKLEME ŞEYSİ -- getting time (offset time is newer time method of java 8)
                            String ofsTime = OffsetDateTime.now(ZoneId.of("Etc/GMT+0")).format(DateTimeFormatter.ofPattern("YYYY-MM-dd-HH-mm-ss"));

                            //UZANTI AYARLAMALARI ile ilgili kısım -- Extension workaround stuffs
                            String filenameSplitted[] = fileName.split("\\."); // burası regEx aslında. o yüzden nokta özel bir karakter. kaçınılması gerek. sadece nokta koyunca split çalışmıyor.
//                            System.out.println("FilenamaSplitted lengt=" + filenameSplitted.length); // buna gerek kalmadı artık. deney amaçlıydı
                            String extension = filenameSplitted[filenameSplitted.length - 1];
                            fileName = "";
                            for (int i = 0; i < filenameSplitted.length - 1; i++) {
                                fileName += filenameSplitted[i];
                            }
                            //DOSYA OLUŞTURMA
                            uploadedFile = new File(path + "/" + fileName + "_" + ofsTime + "." + extension);//bu dizinde yeni dosya oluştur
                            System.out.println(uploadedFile.getAbsolutePath());//dosya yolunu konsola yaz :)
                            item.write(uploadedFile);//listedeki dosya parçasını bu dosyaya yaz.
                        } else if (item.getFieldName().equals("userid")) {
                            formitemUserID = item.getString("UTF-8");
                        } else if (item.getFieldName().equals("fileTitle")) {
                            fileTitle = item.getString("UTF-8");
                        }
                    }
                } catch (FileUploadException e) {
                    System.out.println("File Upload Exception=" + e);
                } catch (Exception e) {
                    System.out.println("Just An Exception=" + e);

                } finally {
                    try {
                        if (!formitemUserID.equals("")) { //giriş yapmadan yaptığım denemelerde userid bilgisi gelmediği için. çok gerek yok ama olsun
                            Connection conn = DatabaseManager.getConnection();
                            String finalFileName = uploadedFile.getName();
                            PreparedStatement ps = conn.prepareStatement(""
                                    + "INSERT INTO files(name,editorStatus,jurystatus,finalstatus,writer,file)"
                                    + "VALUES(?,?,?,?,?,?)");
                            ps.setString(1, fileTitle);
                            ps.setString(2, "waiting");
                            ps.setString(3, "waiting");
                            ps.setString(4, "waiting");
                            ps.setInt(5, Integer.parseInt(formitemUserID));
                            ps.setString(6, "/_temp files/ServerUploads/" + finalFileName);
                            if (ps.execute()) {
                                DatabaseManager.closeConnection();
                                conn.close();
                            }
                        }
                    } catch (SQLException ex) {
                        System.out.println("SQL EXCEPTION HATASU =" + ex);
                    }
                    request.setAttribute("UploadMessage", "<div class='alert alert-success alert-dismissible fade show' role='alert' style=\"text-align:center;\">\n"
                            + "  <strong>Dosya Yükleme Başarılı</strong>\n"
                            + "  <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                            + "    <span aria-hidden='true'>&times;</span>\n"
                            + "  </button>\n"
                            + "</div>");
                    request.getRequestDispatcher("/writer/sendFile.jsp").forward(request, response);
                }

            } else {
                out.print("huston, looks like we have a problem");//mayday mayday!!! :) hata var :)[dosya multi part değil]
            }
            //=================================END METHOD
        }
    }

    protected void getFileForEmbed(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/plain;charset=UTF-8");
        try (OutputStream out = response.getOutputStream()) {
            String path = request.getParameter("filePath");
            File my_file = new File(path);

            try (FileInputStream in = new FileInputStream(my_file)) {
                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }

        }
    }

    protected void getFileForDownload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/plain;charset=UTF-8");
        try (OutputStream out = response.getOutputStream()) {
            String path = request.getParameter("filePath");
            File my_file = new File(path);

//        String fileName = URLDecoder.decode(my_file.getName(), "ISO8859_1");
            String fileName = my_file.getName().replace(" ", "%20");
//        response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);

            try (FileInputStream in = new FileInputStream(my_file)) {
                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }

        }
    }

    protected void getFinalFiles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<fileBean> ListofFiles = new ArrayList();
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps;
            try {
                ps = conn.prepareStatement("SELECT * FROM files WHERE finalStatus='confirmed' ");
                ResultSet rs = ps.executeQuery();
                if (rs.first()) {
                    fileBean fbFirst = new fileBean();
                    fbFirst.setId(rs.getInt("id"));
                    fbFirst.setName(rs.getString("name"));
                    fbFirst.setFilePath(rs.getString("file"));
                    //writer info
                    GetStatsFromDatabase gsf = new GetStatsFromDatabase();
                    userBean writer = gsf.getUser("SELECT * FROM users WHERE id =" + rs.getString("writer"));
                    fbFirst.setWriterData(writer);

                    ListofFiles.add(fbFirst);

                }
                while (rs.next()) {
                    fileBean fbNext = new fileBean();
                    fbNext.setId(rs.getInt("id"));
                    fbNext.setName(rs.getString("name"));
                    fbNext.setFilePath(rs.getString("file"));
                    //writer info
                    GetStatsFromDatabase gsf = new GetStatsFromDatabase();
                    userBean writer = gsf.getUser("SELECT * FROM users WHERE id =" + rs.getString("writer"));
                    fbNext.setWriterData(writer);

                    ListofFiles.add(fbNext);
                }
            } catch (SQLException ex) {
                Logger.getLogger(servletFileUpload.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (ListofFiles.isEmpty()) {
                request.setAttribute("NoResults", "<div style='justify-content: center;display: flex;width:100%;margin-top:10rem;'>\n"
                        + "    <div class='alert alert-warning alert-dismissible fade show' role='alert' style='text-align:center; width:50%;'>\n"
                        + "                                                 <strong>Henüz Bir Şey Yok</strong>\n"
                        + "        <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n"
                        + "            <span aria-hidden='true'>&times;</span>\n"
                        + "        </button>\n"
                        + "    </div>\n"
                        + "</div>");
            } else {
                request.setAttribute("ListofFiles", ListofFiles);
            }

            if (request.getParameter("getFinalFiles").equals("home")) {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else if (request.getParameter("getFinalFiles").equals("archive")) {
                request.getRequestDispatcher("archive.jsp").forward(request, response);
            }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //Dosya indirme isteklerini farklı farklı ele al;
        if (request.getParameter("Stream") != null) {
            if (request.getParameter("Stream").equals("Embed")) {
                getFileForEmbed(request, response);
            } else if (request.getParameter("Stream").equals("Download")) {
                getFileForDownload(request, response);
            }
        }
        if (request.getParameter("getFinalFiles") != null) {
            getFinalFiles(request, response);
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
        processFileUploadRequest(request, response);
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
