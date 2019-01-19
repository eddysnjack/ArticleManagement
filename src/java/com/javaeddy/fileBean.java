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
public class fileBean {
    private int id;
    private String name;
    private String editorStatus;
    private String juryStatus;
    private String finalstatus;
    private int writer;
    private int jury;
    private String editorComments;
    private String juryComments;
    private String sendingDate;
    private String filePath;
    private userBean writerData;
    private userBean juryData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEditorStatus() {
        return editorStatus;
    }

    public void setEditorStatus(String editorStatus) {
        this.editorStatus = editorStatus;
    }

    public String getJuryStatus() {
        return juryStatus;
    }

    public void setJuryStatus(String juryStatus) {
        this.juryStatus = juryStatus;
    }

    public String getFinalstatus() {
        return finalstatus;
    }

    public void setFinalstatus(String finalstatus) {
        this.finalstatus = finalstatus;
    }

    public int getWriter() {
        return writer;
    }

    public void setWriter(int writer) {
        this.writer = writer;
    }

    public int getJury() {
        return jury;
    }

    public void setJury(int jury) {
        this.jury = jury;
    }

    public String getEditorComments() {
        return editorComments;
    }

    public void setEditorComments(String editorComments) {
        this.editorComments = editorComments;
    }

    public String getJuryComments() {
        return juryComments;
    }

    public void setJuryComments(String juryComments) {
        this.juryComments = juryComments;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public userBean getWriterData() {
        return writerData;
    }

    public void setWriterData(userBean writerData) {
        this.writerData = writerData;
    }

    public userBean getJuryData() {
        return juryData;
    }

    public void setJuryData(userBean juryData) {
        this.juryData = juryData;
    }

}
