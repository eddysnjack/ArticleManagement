/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaeddy;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eddy
 */
public class GetFilesFromDatabase {

    public List<fileBean> GetFilesForEditor(String searchQuery) {
        List<fileBean> fileList = new ArrayList();

        try {
            Connection conn = DatabaseManager.getConnection();
            if (conn != null) {
                PreparedStatement ps;
                ps = conn.prepareStatement(searchQuery);
                ResultSet rs = ps.executeQuery();

                if (rs.first()) { //boş mu dolu mu kontrolü beni bunu yapmaya zorladı. hiç bir komut resultset'i okumadan boş dolu kontrolünü doğru ypmaya yetmiyor. isbeforefirst is afterlast felan hikaye, getrow'lu bir çözüm bulmuştum o da bir yerde patladı. böyle yaprım daha sağlam oldu
                    fileBean fbFirst = new fileBean();

                    fbFirst.setId(rs.getInt("id"));
                    fbFirst.setName(rs.getString("name"));
                    fbFirst.setWriter(rs.getInt("writer"));
                    fbFirst.setEditorComments(rs.getString("editorComments"));
                    fbFirst.setEditorStatus(rs.getString("editorStatus"));
                    fbFirst.setFilePath(rs.getString("file"));
                    fbFirst.setJury(rs.getInt("jury"));
                    fbFirst.setJuryComments(rs.getString("juryComments"));
                    fbFirst.setJuryStatus(rs.getString("juryStatus"));
                    fbFirst.setSendingDate(rs.getString("sendingDateUTC"));

                    //-------------Get Writername
                    PreparedStatement psWriter = conn.prepareStatement("SELECT * FROM users where id=" + rs.getInt("writer"));
                    ResultSet rsWriter = psWriter.executeQuery();
                    userBean ubFirstWriter = new userBean();
                    if (rsWriter.first()) {
                        ubFirstWriter.setUsername(rsWriter.getString("username"));
                        ubFirstWriter.setEmail(rsWriter.getString("email"));
                        ubFirstWriter.setId(rsWriter.getInt("id"));
                    }
                    fbFirst.setWriterData(ubFirstWriter);

                    //-------------Get Jury name
                    int juri = rs.getInt("jury");
                    if (!rs.wasNull()) {//eğer dosyaya atanmış jüri varsa(null değilse) juri bilgilerini de al. NOT: javanın bu konuda bu kadar kötü olması üzücü. isNull gibi basit bir metot olabilirdi. ama yok işte.
                        userBean ubFirstJury = new userBean();
                        PreparedStatement psJury = conn.prepareStatement("SELECT * FROM users WHERE id=" + juri);
                        ResultSet rsJury = psJury.executeQuery();
                        if (rsJury.first()) {
                            ubFirstJury.setUsername(rsJury.getString("username"));
                            ubFirstJury.setEmail(rsJury.getString("email"));
                            ubFirstJury.setId(rsJury.getInt("id"));
                        }
                        fbFirst.setJuryData(ubFirstJury);
                    }

                    fileList.add(fbFirst);

                    while (rs.next()) {
                        fileBean fb = new fileBean();

                        fb.setId(rs.getInt("id"));
                        fb.setName(rs.getString("name"));
                        fb.setWriter(rs.getInt("writer"));
                        fb.setEditorComments(rs.getString("editorComments"));
                        fb.setEditorStatus(rs.getString("editorStatus"));
                        fb.setFilePath(rs.getString("file"));
                        fb.setJury(rs.getInt("jury"));
                        fb.setJuryComments(rs.getString("juryComments"));
                        fb.setJuryStatus(rs.getString("juryStatus"));
                        fb.setSendingDate(rs.getString("sendingDateUTC"));

                        //-------------Get Writername
                        psWriter = conn.prepareStatement("SELECT * FROM users where id=" + rs.getInt("writer"));
                        rsWriter = psWriter.executeQuery();
                        userBean ubWriter = new userBean();
                        if (rsWriter.first()) {
                            ubWriter.setUsername(rsWriter.getString("username"));
                            ubWriter.setEmail(rsWriter.getString("email"));
                            ubWriter.setId(rsWriter.getInt("id"));
                        }
                        fb.setWriterData(ubWriter);

                        //-------------Get Jury name
                        juri = rs.getInt("jury");
                        if (!rs.wasNull()) {//eğer dosyaya atanmış jüri varsa(null değilse) juri bilgilerini de al. NOT: javanın bu konuda bu kadar kötü olması üzücü. isNull gibi basit bir metot olabilirdi. ama yok işte.
                            userBean ubJury = new userBean();
                            PreparedStatement psJury = conn.prepareStatement("SELECT * FROM users WHERE id=" + juri);
                            ResultSet rsJury = psJury.executeQuery();
                            if (rsJury.first()) {
                                ubJury.setUsername(rsJury.getString("username"));
                                ubJury.setEmail(rsJury.getString("email"));
                                ubJury.setId(rsJury.getInt("id"));
                            }
                            fb.setJuryData(ubJury);
                        }

                        fileList.add(fb);
                    }
                }

                conn.close();
                DatabaseManager.closeConnection();
            }

//                response.sendRedirect("editor/loginSuccess.jsp");
        } catch (SQLException ex) {

        }

        return fileList;

    }

    public List<fileBean> GetFilesForWriter(String searchQuery, userBean requestUB) {
        List<fileBean> fileList = new ArrayList();

        try {
            Connection conn = DatabaseManager.getConnection();
            if (conn != null) {
                PreparedStatement ps;
                ps = conn.prepareStatement(searchQuery);
                ps.setInt(1, requestUB.getId()); // writer için editör kısmında farklı olarak yapılan tek iş bu
                ResultSet rs = ps.executeQuery();

                if (rs.first()) {
                    fileBean fbFirst = new fileBean();

                    fbFirst.setId(rs.getInt("id"));
                    fbFirst.setName(rs.getString("name"));
                    fbFirst.setWriter(rs.getInt("writer"));
                    fbFirst.setEditorComments(rs.getString("editorComments"));
                    fbFirst.setEditorStatus(rs.getString("editorStatus"));
                    fbFirst.setFilePath(rs.getString("file"));
                    fbFirst.setJury(rs.getInt("jury"));
                    fbFirst.setJuryComments(rs.getString("juryComments"));
                    fbFirst.setJuryStatus(rs.getString("juryStatus"));
                    fbFirst.setSendingDate(rs.getString("sendingDateUTC"));

                    //-------------Get Writername
                    PreparedStatement psWriter = conn.prepareStatement("SELECT * FROM users where id=" + rs.getInt("writer"));
                    ResultSet rsWriter = psWriter.executeQuery();
                    userBean ubFirst = new userBean();
                    if (rsWriter.first()) {
                        ubFirst.setUsername(rsWriter.getString("username"));
                        ubFirst.setEmail(rsWriter.getString("email"));
                        ubFirst.setId(rsWriter.getInt("id"));
                    }

                    fbFirst.setWriterData(ubFirst);


                    fileList.add(fbFirst);

                    while (rs.next()) {
                        fileBean fb = new fileBean();

                        fb.setId(rs.getInt("id"));
                        fb.setName(rs.getString("name"));
                        fb.setWriter(rs.getInt("writer"));
                        fb.setEditorComments(rs.getString("editorComments"));
                        fb.setEditorStatus(rs.getString("editorStatus"));
                        fb.setFilePath(rs.getString("file"));
                        fb.setJury(rs.getInt("jury"));
                        fb.setJuryComments(rs.getString("juryComments"));
                        fb.setJuryStatus(rs.getString("juryStatus"));
                        fb.setSendingDate(rs.getString("sendingDateUTC"));

                        //-------------Get Writername
                        psWriter = conn.prepareStatement("SELECT * FROM users where id=" + rs.getInt("writer"));
                        rsWriter = psWriter.executeQuery();
                        userBean ub = new userBean();
                        if (rsWriter.first()) {
                            ub.setUsername(rsWriter.getString("username"));
                            ub.setEmail(rsWriter.getString("email"));
                            ub.setId(rsWriter.getInt("id"));
                        }
                        fb.setWriterData(ub);

                        
                        fileList.add(fb);
                    }
                }

                conn.close();
                DatabaseManager.closeConnection();
            }

//                response.sendRedirect("editor/loginSuccess.jsp");
        } catch (SQLException ex) {

        }

        return fileList;

    }

    public List<fileBean> GetFilesForJury(String searchQuery, userBean requestUB) {//writer ile aynı. zaten aynı olmaması için hiç bir sebeb yok. en azından şimdilik
        List<fileBean> fileList = new ArrayList();

        try {
            Connection conn = DatabaseManager.getConnection();
            if (conn != null) {
                PreparedStatement ps;
                ps = conn.prepareStatement(searchQuery);
                ps.setInt(1, requestUB.getId());
                ResultSet rs = ps.executeQuery();

                if (rs.first()) {
                    fileBean fbFirst = new fileBean();

                    fbFirst.setId(rs.getInt("id"));
                    fbFirst.setName(rs.getString("name"));
                    fbFirst.setWriter(rs.getInt("writer"));
                    fbFirst.setEditorComments(rs.getString("editorComments"));
                    fbFirst.setEditorStatus(rs.getString("editorStatus"));
                    fbFirst.setFilePath(rs.getString("file"));
                    fbFirst.setJury(rs.getInt("jury"));
                    fbFirst.setJuryComments(rs.getString("juryComments"));
                    fbFirst.setJuryStatus(rs.getString("juryStatus"));
                    fbFirst.setSendingDate(rs.getString("sendingDateUTC"));

                    //-------------Get Writername
                    PreparedStatement psWriter = conn.prepareStatement("SELECT * FROM users where id=" + rs.getInt("writer"));
                    ResultSet rsWriter = psWriter.executeQuery();
                    userBean ubFirst = new userBean();
                    if (rsWriter.first()) {
                        ubFirst.setUsername(rsWriter.getString("username"));
                        ubFirst.setEmail(rsWriter.getString("email"));
                        ubFirst.setId(rsWriter.getInt("id"));
                    }
                    fbFirst.setWriterData(ubFirst);


                    fileList.add(fbFirst);

                    while (rs.next()) {
                        fileBean fb = new fileBean();

                        fb.setId(rs.getInt("id"));
                        fb.setName(rs.getString("name"));
                        fb.setWriter(rs.getInt("writer"));
                        fb.setEditorComments(rs.getString("editorComments"));
                        fb.setEditorStatus(rs.getString("editorStatus"));
                        fb.setFilePath(rs.getString("file"));
                        fb.setJury(rs.getInt("jury"));
                        fb.setJuryComments(rs.getString("juryComments"));
                        fb.setJuryStatus(rs.getString("juryStatus"));
                        fb.setSendingDate(rs.getString("sendingDateUTC"));

                        //-------------Get Writername
                        psWriter = conn.prepareStatement("SELECT * FROM users where id=" + rs.getInt("writer"));
                        rsWriter = psWriter.executeQuery();
                        userBean ub = new userBean();
                        if (rsWriter.first()) {
                            ub.setUsername(rsWriter.getString("username"));
                            ub.setEmail(rsWriter.getString("email"));
                            ub.setId(rsWriter.getInt("id"));
                        }

                        fb.setWriterData(ub);

                        fileList.add(fb);
                    }
                }

                conn.close();
                DatabaseManager.closeConnection();
            }

//                response.sendRedirect("editor/loginSuccess.jsp");
        } catch (SQLException ex) {

        }

        return fileList;

    }
}
