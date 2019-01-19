/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaeddy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eddy
 */
public class GetStatsFromDatabase {

    public List<userBean> getJuryMembers() {
        List<userBean> result = new ArrayList();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE type='jury'");
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                userBean firstUB = new userBean();

                firstUB.setId(rs.getInt("id"));
                firstUB.setEmail(rs.getString("email"));
                firstUB.setProfession(rs.getString("profession"));
                firstUB.setUsername(rs.getString("username"));

                result.add(firstUB);
                while (rs.next()) {
                    userBean UB = new userBean();

                    UB.setId(rs.getInt("id"));
                    UB.setEmail(rs.getString("email"));
                    UB.setProfession(rs.getString("profession"));
                    UB.setUsername(rs.getString("username"));

                    result.add(UB);
                }
            }
        } catch (SQLException ex) {
            System.out.println("getStastFromDatabase > GetJuryMembers=" + ex);
        }

        return result;
    }

    public int getFileCount(String Query) {
        Connection conn = DatabaseManager.getConnection();
        int result = -1;
        try {
            PreparedStatement ps = conn.prepareStatement(Query);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                result = rs.getInt("FileNumber");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetStatsFromDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<userBean> getUsersData(String Query) {
        List<userBean> result = new ArrayList();
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(Query);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                userBean ubFirst = new userBean();
                ubFirst.setId(rs.getInt("id"));
                ubFirst.setEmail(rs.getString("email"));
                ubFirst.setType(rs.getString("type"));
                ubFirst.setUsername(rs.getString("username"));
                ubFirst.setEditorConfirm(rs.getString("editorConfirm"));
                ubFirst.setRegDateUTC(rs.getString("regDateUTC"));
                if (!rs.getString("editorConfirm").equals("SelfApproved")) {
                    result.add(ubFirst);
                }

            }
            while (rs.next()) {
                userBean ubNext = new userBean();
                ubNext.setId(rs.getInt("id"));
                ubNext.setEmail(rs.getString("email"));
                ubNext.setType(rs.getString("type"));
                ubNext.setUsername(rs.getString("username"));
                ubNext.setEditorConfirm(rs.getString("editorConfirm"));
                ubNext.setRegDateUTC(rs.getString("regDateUTC"));
                if (!rs.getString("editorConfirm").equals("SelfApproved")) {
                    result.add(ubNext);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetStatsFromDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public void setUserConfirmation(String Query) {
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(GetStatsFromDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public userBean getUser(String Query) {
        userBean result = new userBean();
        Connection conn = DatabaseManager.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(Query);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                userBean ubFirst = new userBean();
                ubFirst.setId(rs.getInt("id"));
                ubFirst.setEmail(rs.getString("email"));
                ubFirst.setType(rs.getString("type"));
                ubFirst.setUsername(rs.getString("username"));
                ubFirst.setEditorConfirm(rs.getString("editorConfirm"));
                ubFirst.setRegDateUTC(rs.getString("regDateUTC"));
                
                result = ubFirst;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetStatsFromDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
