/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vikrant
 */

package com.apogee.login;

import com.apogee.dbCon.DBConnection;
import com.apogee.login.LoginModel;
import com.apogee.visitor.VisitAttendBean;
import com.apogee.visitor.VisitAttendModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class LoginController extends HttpServlet {

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
        ServletContext ctx = getServletContext();
        LoginModel am = new LoginModel();
        HttpSession session = request.getSession();
        String task = request.getParameter("task");
        if (task == null || task.isEmpty()) {
            task = "";
        }
        try {
            am.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print(e);
        }
        try {
            //System.out.println("tassskkkk -"+task);

            if (task.equals("login")) {
                String user_name = request.getParameter("user_name");
                String password = request.getParameter("password");
                int count = am.checkLogin(user_name, password);

                String designation = "", office="";
                int office_id=0;                                

                session.setAttribute("user_name", user_name);
                session.setAttribute("password", password);
                
                designation=am.getDesignation(user_name,password);
                if(designation.equals("")){
                    designation="normal_user";
                }
                
                office=am.getOffice(user_name,password);
                if(office.equals("")){
                    office="unknown";
                }
                
                office_id=am.getOfficeId(user_name,password);
                
                List<String> listClasses=am.getClasses(office_id);
                
                /*Multimap<String, String> multiMap = ArrayListMultimap.create();*/
                /*Map<String, List<String>> map = new HashMap<String, List<String>>();*/
                Map<String, List<String>> map = am.getClassSection(office_id);                               
                
                //System.out.println("officeee idd --"+office_id);
                
                int logged_key_person_id=am.getKeyPersonId(user_name,password);
                int logged_org_name_id=am.getOrgNameId(user_name,password);
                
                if(count>0){
                	session.setAttribute("login_office", office);
                	session.setAttribute("login_office_id", office_id);
                    session.setAttribute("login_designation", designation);
                    session.setAttribute("logged_org_name_id", logged_org_name_id);
                    session.setAttribute("logged_key_person_id", logged_key_person_id);
                    
                    session.setAttribute("classsectionMap", map);
                    
                    //request.getRequestDispatcher("/JSP/login/visitors_attendance_home.jsp").forward(request, response);
                    request.getRequestDispatcher("/JSP/login/dash.jsp").forward(request, response);
                }else{
                    request.setAttribute("message", "Credentials mis-match!");
                    request.getRequestDispatcher("beforeLoginHomeView").forward(request, response);
                }
            }
            if (task.equals("logout")) {
                session.invalidate();
                request.getRequestDispatcher("beforeLoginHomeView").forward(request, response);
            }else {
            	//request.getRequestDispatcher("/JSP/login/visitors_attendance_home.jsp").forward(request, response);            	
            	request.getRequestDispatcher("/JSP/login/dash.jsp").forward(request, response);
            }
            //request.getRequestDispatcher("index").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
