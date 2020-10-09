package com.apogee.visitor;

import java.sql.Connection;
import com.apogee.dbCon.DBConnection;
import com.apogee.visitor.VisitAttendModel;

import java.io.BufferedInputStream;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

/**
 *
 * @author Vikrant
 */
public class VisitTempDetailsController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ServletContext ctx = getServletContext();
		VisitAttendModel sm = new VisitAttendModel();
		String task = request.getParameter("task");
		String getImage = request.getParameter("getImage");

		String type = request.getParameter("type");
		// System.out.println("request aa gayi --"+type);

		int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;

		String login_office = "";
		int login_office_id = 0;
		// System.out.println("sessionnn --"+session.toString());

		if (null == session.getAttribute("login_office")) {
			// System.out.println("under ifff");
			RequestDispatcher rd = request.getRequestDispatcher("beforeLoginHomeView");
			rd.forward(request, response);
		} else {
			login_office = session.getAttribute("login_office").toString().toUpperCase();
			login_office_id = Integer.parseInt(session.getAttribute("login_office_id").toString());
		}

		try {
			if (getImage == null) {
				getImage = "";
			} else {

				String destinationPath = "C:\\ContactLess Mgmt\\Visitors\\" + getImage;
				File f = new File(destinationPath);
				FileInputStream fis = null;
				if (!f.exists()) {
					destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
					f = new File(destinationPath);
				}
				fis = new FileInputStream(f);
				if (destinationPath.contains("pdf")) {
					response.setContentType("pdf");
				} else {
					response.setContentType("image/jpeg");
				}

				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
				response.setContentLength(fis.available());
				ServletOutputStream os = response.getOutputStream();
				BufferedOutputStream out = new BufferedOutputStream(os);
				int ch = 0;
				while ((ch = bis.read()) != -1) {
					out.write(ch);
				}
				bis.close();
				fis.close();
				out.close();
				os.close();
				response.flushBuffer();
				return;
			}

		} catch (Exception e) {
			System.out.println("errorr -" + e);
			return;
		}

		try {

			sm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
		} catch (Exception e) {
			System.out.print(e);
		}

		if (task == null || task.isEmpty()) {
			task = "";
		}

		try {
			List<String> list1 = null;
			String action = request.getParameter("action1");
			// System.out.println("action --"+action);
			if (action == null) {
				action = "";
			}
			if (action.equals("sendRequestForMobile")) {
				PrintWriter out = response.getWriter();
				list1 = VisitAttendModel.showDataForTempLogForMob();
				JSONObject gson = new JSONObject();
				gson.put("list", list1);
				System.out.println("gson -" + gson);
				out.println(gson);
				return;
			}

			String parent = "", className = "";

			parent = request.getParameter("parent");
			className = request.getParameter("class");						

			List<VisitAttendBean> list = VisitAttendModel.showDataForTempLog(login_office, login_office_id, type, parent, className);

			List<VisitAttendBean> list2 = VisitAttendModel.showDataForTempLog2(login_office, login_office_id, type, parent, className);
			// System.out.println("list 222- "+list2.size());

			int normal = 0, low = 0, high = 0, slight = 0, total = 0;
			for (int i = 0; i < list2.size(); i++) {
				// System.out.println("normal -"+list2.get(i).getNormal());
				normal = list2.get(i).getNormal();
				// System.out.println("slight -- "+list2.get(i).getSlight());
				slight = list2.get(i).getSlight();
				// System.out.println("high --"+list2.get(i).getHigh());
				high = list2.get(i).getHigh();
				low = list2.get(i).getLow();
				total = list2.get(i).getTotal();
			}

			if(parent==null) {
				parent="";
			}
			//System.out.println("parent --"+parent);
			
			request.setAttribute("list", list);

			request.setAttribute("normal", normal);
			request.setAttribute("low", low);
			request.setAttribute("high", high);
			request.setAttribute("slight", slight);
			request.setAttribute("total", total);
			
			request.setAttribute("parent", parent);

			request.setAttribute("login_office", login_office);

			request.setAttribute("message", sm.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("temperature_log");
			/*
			 * RequestDispatcher rd = null;
			 * 
			 * //String action = request.getParameter("action1");
			 * //System.out.println("action --"+action);
			 * if(action.equals("sendRequestForMobile")) {
			 * System.out.println("under mobile =view--"); rd =
			 * request.getRequestDispatcher("temperature_log_mob"); }else
			 * if(action.equals("sendRequestForPC")) {
			 * System.out.println("under pccc view--"); rd =
			 * request.getRequestDispatcher("temperature_log"); }
			 */

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
