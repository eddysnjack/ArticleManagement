/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaeddy;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eddy
 */
public class DatabaseManager {
    private static Connection conn = null;
    private static final String DB_USER_NAME = "root";
    private static final String PASSWORD ="123456";
    
    
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            conn =  (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/articlemanagement",DB_USER_NAME, PASSWORD);
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch(Exception e){
            return null;
        }
        System.out.println("Connection return işi tamamdır");
        return conn;
        
    }
    public static void closeConnection(){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    
    
    
    public static void main(String[] args) {
        
        //this is just for testing
    }
}
