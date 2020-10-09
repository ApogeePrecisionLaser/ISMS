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
public class TempDashController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ServletContext ctx = getServletContext();
		DeviceInfoRegModel dm = new DeviceInfoRegModel();
		String task = request.getParameter("task");
		String getImage = request.getParameter("getImage");
		// System.out.println("get task --" + task);
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

			dm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
		} catch (Exception e) {
			System.out.print(e);
		}

		try {
			if (getImage == null) {
				getImage = "";
			} else {
				//System.out.println("get omage --"+getImage);
				String destinationPath = dm.getPath(getImage);
				System.out.println("pathh --" + destinationPath);
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
			String JQstring = request.getParameter("action1");
			String q = request.getParameter("str");
			if (JQstring != null) {
				PrintWriter out = response.getWriter();
				List<String> list = null;
				if (JQstring.equals("searchTempData")) {					
					list = dm.searchTempData(q, login_office_id);
				}

				JSONObject gson = new JSONObject();
				gson.put("list", list);
				// System.out.println("gson -" + gson);
				out.println(gson);
				dm.closeConnection();
				return;
			}
		} catch (Exception e) {
			System.out.println("DeviceRegController.processRequest() -" + e);
		}

		if (task == null || task.isEmpty()) {
			task = "";
		}

		if (task.equals("Save")) {
			String device_id = request.getParameter("device_id");
			String device_name = request.getParameter("device_name");
			String device_purpose = request.getParameter("device_purpose");
			String device_info_id = request.getParameter("device_info_id");
			//System.out.println("device info iddd ---" + device_info_id);			
			dm.saveData(device_id, device_purpose, device_name, device_info_id);			
		} else if (task.equals("generateMapReport"))// start from here
		{
			// System.out.println(" aa gaya ");
			List listAll = null;
			String jrxmlFilePath;
			// String search = request.getParameter("searchOrderId");
			String e_PassID = request.getParameter("e_PassID");
			response.setContentType("application/pdf");
			ServletOutputStream servletOutputStream = response.getOutputStream();
			listAll = dm.showRegData(login_office, login_office_id);
			// System.out.println("get real path -"+ctx.getRealPath("JSP"));
			jrxmlFilePath = ctx.getRealPath("/report/Blank_A4.jrxml");
			byte[] reportInbytes = dm.generateMapReport(jrxmlFilePath, listAll);
			response.setContentLength(reportInbytes.length);
			servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			return;
		}

		try {
			List<String> list1 = null;
			String action = request.getParameter("action1");
			// System.out.println("action --" + action);
			if (action == null) {
				action = "";
			}

			// List<VisitAttendBean> list =
			// VisitAttendModel.showDataForTempLog(login_office, login_office_id);

			// List<VisitAttendBean> list2 =
			// VisitAttendModel.showDataForTempLog2(login_office, login_office_id);
			// System.out.println("list 222- "+list2.size());

			List<DeviceInfoRegModel> list = DeviceInfoRegModel.showRegData(login_office, login_office_id);

			int normal = 0, low = 0, high = 0, slight = 0, total = 0;
			request.setAttribute("list", list);

			request.setAttribute("login_office", login_office);

			request.setAttribute("message", dm.getMessage());
			request.setAttribute("color", dm.getMsgBgColor());
			dm.closeConnection();
			request.getRequestDispatcher("temp_dash").forward(request, response);
			// response.sendRedirect("device_registration");
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
