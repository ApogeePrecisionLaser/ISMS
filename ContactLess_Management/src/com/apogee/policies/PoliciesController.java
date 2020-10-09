package com.apogee.policies;

import java.sql.Connection;
import com.apogee.dbCon.DBConnection;
import com.apogee.visitor.VisitAttendBean;
import com.apogee.visitor.VisitAttendModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 *
 * @author Vikrant
 */
public class PoliciesController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		ServletContext ctx = getServletContext();
		VisitAttendModel sm = new VisitAttendModel();
		String task = request.getParameter("task");		
		try {

			sm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
		} catch (Exception e) {
			System.out.print(e);
		}		

		try {
			List<VisitAttendBean> list = new ArrayList<VisitAttendBean>();
			request.setAttribute("list", list);
			request.setAttribute("message", sm.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("policies_details");
			rd.forward(request, response);
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
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
