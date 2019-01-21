/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaeddy;

import com.sun.istack.internal.FinalArrayList;

/**
 *
 * @author eddy
 */
public class themeObject {

    String body_background;
    String card_classes;
    String modal_content;
    String editor_comment;
    String jury_comment;
    String jury_selection;
    String comment_button;
    String card_button;
    String file_button;
    String theme_button_text;
    String theme_button_class;

    themeObject(String option) {
        if (option.equals("dark")) {
            setDarkTheme();
        } else if (option.equals("light")) {
            setLightTheme();
        }
    }

    private void setDarkTheme() {
        body_background = "dark-theme";
        card_classes = "text-white bg-dark";
        modal_content = "bg-dark text-white";
        editor_comment = "bg-dark text-white";
        jury_comment = "bg-dark text-white";
        jury_selection = "bg-dark text-white";
        comment_button = "btn-secondary";
        card_button = "btn-secondary";
        file_button = "btn-dark";
        theme_button_text = "Night Mode is On";
        theme_button_class = "btn-ligt";
    }

    private void setLightTheme() {
        body_background = "light-theme";
        card_classes = "";// NO dark classes AKA LIGHT!
        modal_content = "";// NO dark classes AKA LIGHT!
        editor_comment = "";// NO dark classes AKA LIGHT!
        jury_comment = "";// NO dark classes AKA LIGHT!
        jury_selection = "";// NO dark classes AKA LIGHT!
        comment_button = "btn-primary";
        card_button = "btn-success";
        file_button = "btn-light";
        theme_button_text = "Night Mode is Off";
        theme_button_class = "btn-dark";
    }

    public String getBody_background() {
        return body_background;
    }

    public String getCard_classes() {
        return card_classes;
    }

    public String getModal_content() {
        return modal_content;
    }

    public String getEditor_comment() {
        return editor_comment;
    }

    public String getJury_comment() {
        return jury_comment;
    }

    public String getJury_selection() {
        return jury_selection;
    }

    public String getComment_button() {
        return comment_button;
    }

    public String getCard_button() {
        return card_button;
    }

    public String getFile_button() {
        return file_button;
    }

    public String getTheme_button_text() {
        return theme_button_text;
    }

    public String getTheme_button_class() {
        return theme_button_class;
    }
}
