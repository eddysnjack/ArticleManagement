/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaeddy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author eddy
 */
public class servletUserDataController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void getUsersData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<userBean> users = new ArrayList();
            GetStatsFromDatabase gsfd = new GetStatsFromDatabase();
            if (request.getParameter("selectUser").equals("newbies")) {
                users = gsfd.getUsersData("SELECT * FROM users WHERE editorConfirm='waiting'");

                request.setAttribute("usersInfo", users);
                request.getRequestDispatcher("editor/userManagement.jsp").forward(request, response);

            } else if (request.getParameter("selectUser").equals("writers")) {
                users = gsfd.getUsersData("SELECT * FROM users WHERE type='writer'");

                request.setAttribute("usersInfo", users);
                request.getRequestDispatcher("editor/userManagement.jsp").forward(request, response);

            } else if (request.getParameter("selectUser").equals("juries")) {
                users = gsfd.getUsersData("SELECT * FROM users WHERE type='jury'");

                request.setAttribute("usersInfo", users);
                request.getRequestDispatcher("editor/userManagement.jsp").forward(request, response);

            } else if (request.getParameter("selectUser").equals("rejected")) {
                users = gsfd.getUsersData("SELECT * FROM users WHERE editorConfirm='rejected'");

                request.setAttribute("usersInfo", users);
                request.getRequestDispatcher("editor/userManagement.jsp").forward(request, response);

            } else if (request.getParameter("selectUser").equals("allOfEm")) {
                users = gsfd.getUsersData("SELECT * FROM users");

                request.setAttribute("usersInfo", users);
                request.getRequestDispatcher("editor/userManagement.jsp").forward(request, response);
            }

        }
    }

    protected void setConfirmation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            GetStatsFromDatabase gtsf = new GetStatsFromDatabase();
            if (request.getParameter("UserConfirmation").equals("confirmed")) {
                gtsf.setUserConfirmation("UPDATE users SET editorConfirm='confirmed' WHERE id=" + request.getParameter("UserId"));
            } else if (request.getParameter("UserConfirmation").equals("rejected")) {
                gtsf.setUserConfirmation("UPDATE users SET editorConfirm='rejected' WHERE id=" + request.getParameter("UserId"));
            }

            request.getRequestDispatcher(request.getServletPath()).forward(request, response);
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
        if (request.getSession().getAttribute("user_bean") != null) {
            userBean ub = (userBean) request.getSession().getAttribute("user_bean");
            if (ub.getType().equals("editor")) {
                if (request.getParameter("selectUser") != null) {
                    getUsersData(request, response);
                } else if (request.getParameter("UserConfirmation") != null) {
                    setConfirmation(request, response);
                }

            }
        } else {
            response.sendRedirect("forbidden.jsp");

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
