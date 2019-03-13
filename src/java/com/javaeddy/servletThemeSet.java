/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaeddy;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author eddy
 */
public class servletThemeSet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("nightModeValue").equals("dark")) {
                themeObject themeObj = new themeObject("dark");
                request.getSession().setAttribute("themeObject", themeObj);

                String nightModeValue = "dark";
                request.getSession().setAttribute("nightModeValue", nightModeValue);

               
//                response.sendRedirect(request.getHeader("referer"));
                //  request.getRequestDispatcher(request.getServletPath()).forward(request, response); //!!!! ölümcül bir hataya sebep oldu. apache durmadan hata felan verdi. pcyi yeniden başlatmak zorunda kaldım. sonsuz döngüye girdi ama nasıl girdi anlamafım :/
            } else if (request.getParameter("nightModeValue").equals("light")) {
                themeObject themeObj = new themeObject("light");
                request.getSession().setAttribute("themeObject", themeObj);

                String nightModeValue = "light";
                request.getSession().setAttribute("nightModeValue", nightModeValue);

               
//                response.sendRedirect(request.getHeader("referer")); //javascript ile hallettim. gerek kalmadı :)))
            }

//            request.getRequestDispatcher("/editor/getNewOnes.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
