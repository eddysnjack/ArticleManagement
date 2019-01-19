/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaeddy;

/**
 *
 * @author eddy
 */
public class userBean {

    private int id;
    private String password;
    private String email;
    private String username;
    private String type;
    private String profession;
    private String editorConfirm;
    private String regDateUTC;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEditorConfirm() {
        return editorConfirm;
    }

    public void setEditorConfirm(String editorConfirm) {
        this.editorConfirm = editorConfirm;
    }

    public String getRegDateUTC() {
        return regDateUTC;
    }

    public void setRegDateUTC(String regDateUTC) {
        this.regDateUTC = regDateUTC;
    }


}
