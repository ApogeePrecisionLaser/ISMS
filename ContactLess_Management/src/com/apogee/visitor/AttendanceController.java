package com.apogee.visitor;

import java.sql.Connection;
import com.apogee.dbCon.DBConnection;
import com.apogee.visitor.AttendanceReportModel;

import java.io.BufferedInputStream;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class AttendanceController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		ServletContext ctx = getServletContext();
		AttendanceReportModel arm = new AttendanceReportModel();
		String task = request.getParameter("task");
		String getImage = request.getParameter("getImage");
		// System.out.println("get task --" + task);
		int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;

		String login_office = "";
		int login_office_id = 0;
		String device_name_s = "";
		// System.out.println("sessionnn --"+session.toString());

		if (null == session.getAttribute("login_office")) {
			// System.out.println("under ifff");
			RequestDispatcher rd = request.getRequestDispatcher("beforeLoginHomeView");
			rd.forward(request, response);
		} else {
			login_office = session.getAttribute("login_office").toString().toUpperCase();
			login_office_id = Integer.parseInt(session.getAttribute("login_office_id").toString());
			// device_name_s=session.getAttribute("device_name").toString().toUpperCase();
		}
		if (null == session.getAttribute("device_name")) {
			device_name_s = "";
		} else {
			device_name_s = session.getAttribute("device_name").toString().toUpperCase();
		}

		try {

			arm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
		} catch (Exception e) {
			System.out.print(e);
		}

		try {
			if (getImage == null) {
				getImage = "";
			} else {
				// String destinationPath = "C:\\ContactLess Mgmt\\Visitors\\" + getImage;
				String destinationPath = VisitAttendModel.getPath(getImage);
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
				if (JQstring.equals("searchName")) {
					String q2 = request.getParameter("str2");
					list = arm.getPersonName(q, q2, login_office_id);
				}
				if (JQstring.equals("searchRfId")) {
					String q2 = request.getParameter("str2");
					list = arm.getDeviceRfId(q, q2);
				}
				if (JQstring.equals("searchDeviceId")) {
					list = arm.getDeviceId(q);
				}
				if (JQstring.equals("searchDesignation")) {

					list = arm.getDesignation(q);
				}
				if (JQstring.equals("searchClass")) {
					list = arm.getClass(q, login_office_id);
				}
				if (JQstring.equals("searchSection")) {
					String q2 = request.getParameter("str2");
					list = arm.getSection(q, q2, login_office_id);
				}
				if (JQstring.equals("searchEnrollNo")) {
					String q2 = request.getParameter("str2");
					String q3 = request.getParameter("str3");
					list = arm.getEnrollNo(q, q2, q3, login_office_id);
				}

				if (JQstring.equals("getAllDetails")) {
					list = arm.getAllDetails(q, login_office_id);

				}

				JSONObject gson = new JSONObject();
				gson.put("list", list);
				// System.out.println("gson -" + gson);
				out.println(gson);
				arm.closeConnection();
				return;
			}
		} catch (Exception e) {
			System.out.println("AttendanceController.processRequest() -" + e);
		}

		if (task == null || task.isEmpty()) {
			task = "";
		}

		String dataShow = "No";
		String designation_id = "", class_enroll = "", class_name = "", section = "", name = "", enroll_no = "",
				from_date = "", to_date = "", period = "",temp_type="";

		if (task.equals("Submit")) {
			designation_id = request.getParameter("designation_id");
			class_enroll = request.getParameter("class_enroll");
			class_name = request.getParameter("class");
			section = request.getParameter("section");
			name = request.getParameter("name");
			enroll_no = request.getParameter("enroll_no");
			from_date = request.getParameter("from_date");
			to_date = request.getParameter("to_date");
			period = request.getParameter("period");
			temp_type = request.getParameter("temp_type");
			dataShow = "Yes";
		}
		session.setAttribute("dataShow", dataShow);

		System.out.println("dd -" + designation_id + "en -" + class_enroll + " cn- " + class_name + " s -" + section
				+ " n -" + name + " e -" + enroll_no + " f - " + from_date + " t -" + to_date+" tempType -"+temp_type);

		// report

		String requester = request.getParameter("requester");
		String dd = request.getParameter("designation");		
		if (requester != null && requester.equals("PRINT")) {
			
			designation_id = request.getParameter("designation_id");
			class_enroll = request.getParameter("class_enroll");
			class_name = request.getParameter("class");
			section = request.getParameter("section");
			name = request.getParameter("name");
			enroll_no = request.getParameter("enroll_no");
			from_date = request.getParameter("from_date");
			to_date = request.getParameter("to_date");
			period = request.getParameter("period");
			temp_type = request.getParameter("temp_type");
			
			List listAll = null;
			String jrxmlFilePath;
			response.setContentType("application/pdf");
			ServletOutputStream servletOutputStream = response.getOutputStream();
			// listAll = AttendanceReportModel.showDataReport(login_office,
			// login_office_id);
			
			/*System.out.println("dd -" + designation_id + "en -" + class_enroll + " cn- " + class_name + " s -" + section
					+ " n -" + name + " e -" + enroll_no + " f - " + from_date + " t -" + to_date);*/
			
			listAll = AttendanceReportModel.showFilterData(login_office, login_office_id, designation_id, class_enroll,
					class_name, section, name, enroll_no, from_date, to_date, period,temp_type);
			jrxmlFilePath = ctx.getRealPath("/report/Cherry.jrxml");
			byte[] reportInbytes = AttendanceReportModel.generateSiteList(jrxmlFilePath, listAll);
			// System.out.println("reportInbytes"+reportInbytes);
			response.setContentLength(reportInbytes.length);
			servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();

			return;
		}
		
		else if (requester != null && requester.equals("PRINTXls")) {
       	 
			designation_id = request.getParameter("designation_id");
			class_enroll = request.getParameter("class_enroll");
			class_name = request.getParameter("class");
			section = request.getParameter("section");
			name = request.getParameter("name");
			enroll_no = request.getParameter("enroll_no");
			from_date = request.getParameter("from_date");
			to_date = request.getParameter("to_date");
			period = request.getParameter("period");
			temp_type = request.getParameter("temp_type");
			
            String jrxmlFilePath = null;            
            List listAll = null;
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=city.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            //listAll=sm.showDataReport(login_office,login_office_id);
            listAll = AttendanceReportModel.showFilterData(login_office, login_office_id, designation_id, class_enroll,
					class_name, section, name, enroll_no, from_date, to_date, period, temp_type);            
            jrxmlFilePath = ctx.getRealPath("/report/Cherry.jrxml");            
            ByteArrayOutputStream reportInbytes = AttendanceReportModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }

		// report

		try {
			List<String> list1 = null;
			String action = request.getParameter("action1");
			// System.out.println("action --" + action);
			if (action == null) {
				action = "";
			}

			List<VisitAttendBean> list = AttendanceReportModel.showFilterData(login_office, login_office_id,
					designation_id, class_enroll, class_name, section, name, enroll_no, from_date, to_date, period, temp_type);

			List<VisitAttendBean> list2 = AttendanceReportModel.showDataForTempLog2(login_office, login_office_id,
					designation_id, class_enroll, class_name, section, name, enroll_no, from_date, to_date, period, temp_type);

			int normal = 0, low = 0, high = 0, slight = 0, total = 0;
			for (int i = 0; i < list2.size(); i++) {
				normal = list2.get(i).getNormal();
				slight = list2.get(i).getSlight();
				high = list2.get(i).getHigh();
				low = list2.get(i).getLow();
				total = list2.get(i).getTotal();
			}

			request.setAttribute("login_office", login_office);
			
			
			List list3=new ArrayList<String>();
			list3.add("list1");
			list3.add("list2");
			list3.add("list3");
			
			System.out.println("list 3"+list3);
			request.setAttribute("list3", list3);

			request.setAttribute("list", list);
			request.setAttribute("normal", normal);
			request.setAttribute("low", low);
			request.setAttribute("high", high);
			request.setAttribute("slight", slight);
			request.setAttribute("total", total);

			request.setAttribute("designation_id", designation_id);
			request.setAttribute("class_enroll", class_enroll);
			request.setAttribute("class_name", class_name);
			request.setAttribute("section", section);
			request.setAttribute("name", name);
			request.setAttribute("enroll_no", enroll_no);
			request.setAttribute("from_date", from_date);
			request.setAttribute("to_date", to_date);
			request.setAttribute("period", period);
			request.setAttribute("temp_type", temp_type);

			request.setAttribute("message", arm.getMessage());
			request.setAttribute("color", arm.getMsgBgColor());
			arm.closeConnection();
			request.getRequestDispatcher("attendance_report").forward(request, response);
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
