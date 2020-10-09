package com.apogee.visitor;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.apogee.visitor.VisitAttendBean;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;*/
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author Vikrant
 */
public class AttendanceReportModel {

	private static Connection connection;
	private String message;
	private String msgBgColor;
	private final String COLOR_OK = "yellow";
	private final String COLOR_ERROR = "red";

	/*
	 * public static int getNoOfRows() { int noOfRows = 0; try { String query =
	 * "SELECT count(*) from shift_type where active='Y'"; PreparedStatement pstmt =
	 * (PreparedStatement) connection.prepareStatement(query); ResultSet rset =
	 * pstmt.executeQuery(); rset.next(); noOfRows =
	 * Integer.parseInt(rset.getString(1)); } catch (Exception e) {
	 * System.out.println(e); }
	 * System.out.println("No of Rows in Table for search is****....." + noOfRows);
	 * return noOfRows; }
	 */

	public byte[] generateMapReport(String jrxmlFilePath, List<VisitAttendBean> listAll) {
		byte[] reportInbytes = null;
		try {

			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll, false);
			JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
			reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
		} catch (Exception e) {
			Logger.getLogger(VisitAttendModel.class.getName()).log(Level.SEVERE, null, e);
		}
		return reportInbytes;
	}

	// report

	public static byte[] generateSiteList(String jrxmlFilePath, List listAll) {
		byte[] reportInbytes = null;
		try {

			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
			JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
			reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
		} catch (Exception e) {
			System.out.println("Error: in  generateMapReport() JRException: " + e);
		}
		return reportInbytes;
	}
	
	
	// pdf
	public static  ByteArrayOutputStream generateOrginisationXlsRecordList(String jrxmlFilePath,List list) {
        ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
      //  HashMap mymap = new HashMap();
        try {
        	System.out.println("list     "+list);
            JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            System.out.println("compiledReport     "+compiledReport);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            System.out.println("compiledReport     "+compiledReport);
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("OrginisationTypeStatusModel generateOrgnisitionXlsRecordList() JRException: " + e);
        }
        return bytArray;
    }
	// end pdf

	// report

	// show data report

	public static List<VisitAttendBean> showDataReport(String login_office, int login_office_id) {
		List list = new ArrayList();
		VisitAttendBean bean = null;

		String query = " select distinct kp.key_person_name,ddt.device_temperature, ddt.device_data_datetime, ddt.image_path "
				+ " from device_data_tcp ddt, rfid_registration rr, key_person kp, org_office oo "
				+ " where rr.mapped_rfid=ddt.device_rfid and kp.key_person_id=rr.key_person_id and kp.active='Y' "
				+ " and kp.org_office_id=oo.org_office_id and oo.organisation_id='" + login_office_id + "' "
				+ " order by ddt.device_data_datetime desc ";

		String qry = " select vd.person_name,date(va.created_date)as date, time(va.created_date)as time,va.temperature "
				+ " ,vd.person_id_image_name from visitor_attendance va, visitor_details vd "
				+ " where va.active='Y' and vd.active='Y' and vd.visitor_details_id=va.visitor_details_id ";

		// System.out.println("query 1 --"+query);

		try {
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new VisitAttendBean();
				bean.setEmp_name(rs.getString(1));
				bean.setTemperature(rs.getDouble(2));
				bean.setDate(rs.getString(3));
				// bean.setTime(rs.getString(4));
				bean.setImage_name(rs.getString(4));
				bean.setLogin_office(login_office);

				// System.out.println("temppp ---"+bean.getTemperature());
				if (bean.getTemperature() > 98 && bean.getTemperature() < 100) {
					bean.setStatus("Slight Fever");
				} else if (bean.getTemperature() < 90) {
					bean.setStatus("Low");
				} else if (bean.getTemperature() > 100) {
					bean.setStatus("High Fever");
				} else if (bean.getTemperature() > 90 && bean.getTemperature() < 98) {
					bean.setStatus("Normal");
				}

				list.add(bean);
			}

			PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(qry);
			ResultSet rst = psmt.executeQuery();
			while (rst.next()) {
				VisitAttendBean vBean = new VisitAttendBean();
				vBean.setEmp_name(rst.getString(1));
				vBean.setDate(rst.getString(2));
				vBean.setTime(rst.getString(3));
				vBean.setTemperature(rst.getDouble(4));
				vBean.setImage_name(rst.getString(5));
				if (vBean.getTemperature() > 98 && vBean.getTemperature() < 100) {
					vBean.setStatus("Slight Fever");
				} else if (vBean.getTemperature() < 90) {
					vBean.setStatus("Low");
				} else if (vBean.getTemperature() > 100) {
					vBean.setStatus("High Fever");
				} else if (vBean.getTemperature() > 90 && vBean.getTemperature() < 98) {
					vBean.setStatus("Normal");
				}

				list.add(vBean);
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list;
	}

	// show data report

	public static List<VisitAttendBean> showDataForTempLog(String login_office, int login_office_id, String type,
			String parent, String className) {
		List list = new ArrayList();

		if (type == null) {
			type = "";
		}

		try {
			String query = " select distinct kp.key_person_name,ddt.device_temperature, ddt.device_data_datetime, ddt.image_path "
					+ " from device_data_tcp ddt, rfid_registration rr, key_person kp, org_office oo, designation d "
					+ " where rr.mapped_rfid=ddt.device_rfid and kp.key_person_id=rr.key_person_id and kp.active='Y' "
					+ " and kp.org_office_id=oo.org_office_id and oo.organisation_id='" + login_office_id + "' ";

			if (!type.equals("")) {
				query += " and d.designation_id=kp.designation_id and d.designation='" + type + "' ";

				if (parent.equals("class")) {
					int parent_id = 0;
					String qry = " select org_office_id from org_office where org_office_name='" + className + "' ";
					PreparedStatement psmt = connection.prepareStatement(qry);
					ResultSet rst = psmt.executeQuery();
					while (rst.next()) {
						parent_id = rst.getInt(1);
					}

					query += " and oo.parent_org_office_id=" + parent_id + " ";

				} else if (parent.equals("section")) {
					query += " and oo.org_office_name='" + className + "' ";
				}

			}
			query += " order by ddt.device_data_datetime desc ";

			String qry = " select vd.person_name,date(va.created_date)as date, time(va.created_date)as time,va.temperature "
					+ " ,vd.person_id_image_name from visitor_attendance va, visitor_details vd "
					+ " where va.active='Y' and vd.active='Y' and vd.visitor_details_id=va.visitor_details_id ";

			System.out.println("query 1 --" + query);

			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VisitAttendBean bean = new VisitAttendBean();
				bean.setEmp_name(rs.getString(1));
				bean.setTemperature(rs.getDouble(2));
				bean.setDate(rs.getString(3));
				// bean.setTime(rs.getString(4));
				bean.setImage_name(rs.getString(4));
				// System.out.println("temppp ---"+bean.getTemperature());
				if (bean.getTemperature() > 98 && bean.getTemperature() < 100) {
					bean.setStatus("Slight Fever");
				} else if (bean.getTemperature() < 90) {
					bean.setStatus("Low");
				} else if (bean.getTemperature() > 100) {
					bean.setStatus("High Fever");
				} else if (bean.getTemperature() > 90 && bean.getTemperature() < 98) {
					bean.setStatus("Normal");
				}

				list.add(bean);
			}

			PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(qry);
			ResultSet rst = psmt.executeQuery();
			while (rst.next()) {
				VisitAttendBean vBean = new VisitAttendBean();
				vBean.setEmp_name(rst.getString(1));
				vBean.setDate(rst.getString(2));
				vBean.setTime(rst.getString(3));
				vBean.setTemperature(rst.getDouble(4));
				vBean.setImage_name(rst.getString(5));
				if (vBean.getTemperature() > 98 && vBean.getTemperature() < 100) {
					vBean.setStatus("Slight Fever");
				} else if (vBean.getTemperature() < 90) {
					vBean.setStatus("Low");
				} else if (vBean.getTemperature() > 100) {
					vBean.setStatus("High Fever");
				} else if (vBean.getTemperature() > 90 && vBean.getTemperature() < 98) {
					vBean.setStatus("Normal");
				}

				list.add(vBean);
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list;
	}

	// end data for temp log

	public static List<VisitAttendBean> showDataForTempLog2(String login_office, int login_office_id,
			String designation_id, String class_enroll, String class_name, String section, String name,
			String enroll_no, String from_date, String to_date, String period) {
		List list = new ArrayList();
		int countSlight = 0, countHigh = 0, countNormal = 0, countLow = 0, totalCount = 0;
		if (class_enroll == null) {
			class_enroll = "";
		}
		if (class_name == null) {
			class_name = "";
		}
		if (section == null) {
			section = "";
		}
		if (name == null) {
			name = "";
		}
		if (enroll_no == null) {
			enroll_no = "";
		}
		if (from_date == null) {
			from_date = "";
		}
		if (to_date == null) {
			to_date = "";
		}
		if (period == null) {
			period = "";
		}

		try {
			String query = " select distinct kp.key_person_name,ddt.device_temperature, ddt.device_data_datetime, ddt.image_path "
					+ " from device_data_tcp ddt, rfid_registration rr, key_person kp, org_office oo, designation d "
					+ " where rr.mapped_rfid=ddt.device_rfid and kp.key_person_id=rr.key_person_id and kp.active='Y' "
					+ " and kp.org_office_id=oo.org_office_id and oo.organisation_id='" + login_office_id + "' ";

			if (class_enroll == "" && class_name == "" && section == "") {
				query += " and d.designation_id=kp.designation_id and d.designation='" + designation_id + "' ";
			}

			if (section == "ALL") {
				query += "";

			}

			if (enroll_no != "") {
				query += " and kp.emp_code='" + enroll_no + "' ";
			}

			if (from_date != "" && to_date != "") {
				query += " and date(ddt.created_date) between '" + from_date + "' and '" + to_date + "' ";
			}
			if (from_date != "" && to_date == "") {
				query += " and date(ddt.created_date) between '" + from_date + "' and now() ";
			}

			query += " order by ddt.device_data_datetime desc ";

			String qry = " select vd.person_name,date(va.created_date)as date, time(va.created_date)as time,va.temperature "
					+ " ,vd.person_id_image_name from visitor_attendance va, visitor_details vd "
					+ " where va.active='Y' and vd.active='Y' and vd.visitor_details_id=va.visitor_details_id ";

			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			VisitAttendBean bean1 = new VisitAttendBean();
			while (rs.next()) {
				VisitAttendBean bean = new VisitAttendBean();
				bean.setEmp_name(rs.getString(1));
				bean.setTemperature(rs.getDouble(2));
				bean.setDate(rs.getString(3));
				bean.setTime(rs.getString(4));
				// System.out.println("temppp ---"+bean.getTemperature());
				if (bean.getTemperature() > 98 && bean.getTemperature() < 100) {
					bean.setStatus("Slight Fever");
					countSlight++;
				} else if (bean.getTemperature() < 90) {
					bean.setStatus("Low");
					countLow++;
				} else if (bean.getTemperature() > 100) {
					bean.setStatus("High Fever");
					countHigh++;
				} else if (bean.getTemperature() > 90 && bean.getTemperature() < 98) {
					bean.setStatus("Normal");
					countNormal++;
				}
				totalCount++;
			}
			/*
			 * bean1.setNormal(countNormal); bean1.setLow(countLow);
			 * bean1.setHigh(countHigh); bean1.setSlight(countSlight);
			 * bean1.setTotal(totalCount); list.add(bean1);
			 */

			PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(qry);
			ResultSet rst = psmt.executeQuery();
			while (rst.next()) {
				VisitAttendBean vBean = new VisitAttendBean();
				vBean.setEmp_name(rst.getString(1));
				vBean.setDate(rst.getString(2));
				vBean.setTime(rst.getString(3));
				vBean.setTemperature(rst.getDouble(4));
				vBean.setImage_name(rst.getString(5));
				if (vBean.getTemperature() > 98 && vBean.getTemperature() < 100) {
					vBean.setStatus("Slight Fever");
					countSlight++;
				} else if (vBean.getTemperature() < 90) {
					vBean.setStatus("Low");
					countLow++;
				} else if (vBean.getTemperature() > 100) {
					vBean.setStatus("High Fever");
					countHigh++;
				} else if (vBean.getTemperature() > 90 && vBean.getTemperature() < 98) {
					vBean.setStatus("Normal");
					countNormal++;
				}

				totalCount++;

			}

			bean1.setNormal(countNormal);
			bean1.setLow(countLow);
			bean1.setHigh(countHigh);
			bean1.setSlight(countSlight);
			bean1.setTotal(totalCount);
			list.add(bean1);

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list;
	}

	// end data for temp log22

	// start filter data
	// login_office, login_office_id,
	// designation_id,class_enroll,class_name,section,name,enroll_no,from_date,to_date,yearly,monthly,weekly

	public static List<VisitAttendBean> showFilterData(String login_office, int login_office_id, String designation_id,
			String class_enroll, String class_name, String section, String name, String enroll_no, String from_date,
			String to_date, String period) {
		List list = new ArrayList();

		if (class_enroll == null) {
			class_enroll = "";
		}
		if (class_name == null) {
			class_name = "";
		}
		if (section == null) {
			section = "";
		}
		if (name == null) {
			name = "";
		}
		if (enroll_no == null) {
			enroll_no = "";
		}
		if (from_date == null) {
			from_date = "";
		}
		if (to_date == null) {
			to_date = "";
		}
		if (period == null) {
			period = "";
		}

		try {
			String query = " select distinct kp.key_person_name,ddt.device_temperature, ddt.device_data_datetime, ddt.image_path "
					+ " from device_data_tcp ddt, rfid_registration rr, key_person kp, org_office oo, designation d "
					+ " where rr.mapped_rfid=ddt.device_rfid and kp.key_person_id=rr.key_person_id and kp.active='Y' "
					+ " and kp.org_office_id=oo.org_office_id and oo.organisation_id='" + login_office_id + "' ";

			if (class_enroll == "" && class_name == "" && section == "") {
				query += " and d.designation_id=kp.designation_id and d.designation='" + designation_id + "' ";
			}

			if (section.equals("ALL")) {			
				query += " and d.designation_id=kp.designation_id and d.designation='" + designation_id + "' ";

			}

			if (enroll_no != "") {
				query += " and kp.emp_code='" + enroll_no + "' ";
			}

			if (from_date != "" && to_date != "") {
				query += " and date(ddt.created_date) between '" + from_date + "' and '" + to_date + "' ";
			}
			if (from_date != "" && to_date == "") {
				query += " and date(ddt.created_date) between '" + from_date + "' and now() ";
			}

			query += " order by ddt.device_data_datetime desc ";

			// data from android app
			String qry = " select vd.person_name,date(va.created_date)as date, time(va.created_date)as time,va.temperature "
					+ " ,vd.person_id_image_name from visitor_attendance va, visitor_details vd "
					+ " where va.active='Y' and vd.active='Y' and vd.visitor_details_id=va.visitor_details_id ";

			System.out.println("query 1 --" + query);

			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VisitAttendBean bean = new VisitAttendBean();
				bean.setEmp_name(rs.getString(1));
				bean.setTemperature(rs.getDouble(2));
				bean.setDate(rs.getString(3));
				// bean.setTime(rs.getString(4));
				bean.setImage_name(rs.getString(4));

				bean.setLogin_office(login_office);

				// System.out.println("temppp ---"+bean.getTemperature());
				if (bean.getTemperature() > 98 && bean.getTemperature() < 100) {
					bean.setStatus("Slight Fever");
				} else if (bean.getTemperature() < 90) {
					bean.setStatus("Low");
				} else if (bean.getTemperature() > 100) {
					bean.setStatus("High Fever");
				} else if (bean.getTemperature() > 90 && bean.getTemperature() < 98) {
					bean.setStatus("Normal");
				}

				list.add(bean);
			}

			PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(qry);
			ResultSet rst = psmt.executeQuery();
			while (rst.next()) {
				VisitAttendBean vBean = new VisitAttendBean();
				vBean.setEmp_name(rst.getString(1));
				vBean.setDate(rst.getString(2));
				vBean.setTime(rst.getString(3));
				vBean.setTemperature(rst.getDouble(4));
				vBean.setImage_name(rst.getString(5));
				if (vBean.getTemperature() > 98 && vBean.getTemperature() < 100) {
					vBean.setStatus("Slight Fever");
				} else if (vBean.getTemperature() < 90) {
					vBean.setStatus("Low");
				} else if (vBean.getTemperature() > 100) {
					vBean.setStatus("High Fever");
				} else if (vBean.getTemperature() > 90 && vBean.getTemperature() < 98) {
					vBean.setStatus("Normal");
				}

				list.add(vBean);
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list;
	}

	// wnd filter data

	public List<String> getDeviceRfId(String q, String device_name) {
		List<String> list = new ArrayList<String>();

		String qry = " select device_info_id from device_info where device_purpose='R' and active='Y' "
				+ " and device_name='" + device_name + "' ";

		try {
			// System.out.println("queryyy --"+query);
			String device_info_id = "";
			ResultSet rst = connection.prepareStatement(qry).executeQuery();
			while (rst.next()) {
				device_info_id = rst.getString(1);
			}

			String query = " select rfid_value from rfid_temp_data where time(created_date) " + " between '" + q
					+ "' and current_time() and device_info_id='" + device_info_id + "' ";

			// System.out.println("queryy rfid -"+query);
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			q = q.trim();
			while (rset.next()) { // move cursor from BOR to valid record.
				String rfid_value = rset.getString(1);
				/*
				 * if (rfid_value.toUpperCase().startsWith(q.toUpperCase())) {
				 * list.add(rfid_value); count++; System.out.println("counttt-- " + count); }
				 */

				if (!rfid_value.equals("")) {
					list.add(rfid_value);
					count++;
				}
			}
			if (count == 0) {
				list.add("No such rfid Id exists.......");
			}
		} catch (Exception e) {
			System.out.println("getDeviceRfId ERROR inside VisitAttendModel - " + e);
		}
		return list;
	}

	public List<String> getDeviceId(String q) {
		List<String> list = new ArrayList<String>();
		String query = " select device_name from device_info where device_purpose='R' and active='Y' ";
		try {
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			q = q.trim();
			while (rset.next()) { // move cursor from BOR to valid record.
				String device_id = rset.getString(1);
				if (device_id.toUpperCase().startsWith(q.toUpperCase())) {
					list.add(device_id);
					count++;
				}
				// System.out.println("key person name " + key_person_name);
			}
			if (count == 0) {
				list.add("No such device Id exists.......");
			}
		} catch (Exception e) {
			System.out.println("getDeviceId ERROR inside VisitAttendModel - " + e);
		}
		return list;
	}

	public List<String> getDesignation(String q) {
		List<String> list = new ArrayList<String>();
		String query = " select designation from designation where active='Y' ";
		try {
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			q = q.trim();
			while (rset.next()) { // move cursor from BOR to valid record.
				String designation = rset.getString(1);
				if (designation.toUpperCase().startsWith(q.toUpperCase())) {
					list.add(designation);
					count++;
				}
			}
			if (count == 0) {
				list.add("No such designation exists.......");
			}
		} catch (Exception e) {
			System.out.println("getDesignation ERROR inside VisitAttendModel - " + e);
		}
		return list;
	}

	public List<String> getClass(String q, int login_id) {
		List<String> list = new ArrayList<String>();
		String query = " select org_office_id, org_office_name " + " from org_office" + " where organisation_id="
				+ login_id + " and parent_org_office_id=(select org_office_id "
				+ " from org_office where org_office_name='SuperClass') order by org_office_id ";
		try {
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			q = q.trim();
			while (rset.next()) { // move cursor from BOR to valid record.
				String class_name = rset.getString(2);
				if (class_name.toUpperCase().startsWith(q.toUpperCase())) {
					list.add(class_name);
					count++;
				}
				// System.out.println("key person name " + key_person_name);
			}
			if (count == 0) {
				list.add("No such class exists.......");
			}
		} catch (Exception e) {
			System.out.println("getClass ERROR inside VisitAttendModel - " + e);
		}
		return list;
	}

	public List<String> getSection(String q, String q2, int login_id) {
		List<String> list = new ArrayList<String>();
		String class_id = "";
		String query = "select org_office_id from org_office where org_office_name='" + q2 + "' and active='Y'  ";
		try {
			// System.err.println("query --"+query);
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			while (rset.next()) { // move cursor from BOR to valid record.
				class_id = rset.getString(1);
			}
			// System.err.println("class id --"+class_id);
			rset = null;
			// String query2=" select substring_index(org_office_name,'-','-1') from
			// org_office where parent_org_office_id='"+class_id+"' and active='Y' ";
			String query2 = " select org_office_name from org_office where parent_org_office_id='" + class_id
					+ "' and active='Y' " + " and organisation_id='" + login_id + "' ";
			// System.err.println("query 2 --"+query2);
			rset = connection.prepareStatement(query2).executeQuery();
			q = q.trim();
			list.add("ALL");

			while (rset.next()) {
				String section_name = rset.getString(1);
				if (section_name.toUpperCase().startsWith(q.toUpperCase())) {
					list.add(section_name);
					count++;
				}
			}

			if (count == 0) {
				list.add("No such section exists.......");
			}
		} catch (Exception e) {
			System.out.println("getSection ERROR inside VisitAttendModel - " + e);
		}
		return list;
	}

	public List<String> getPersonName(String q, String q2, int login_id) {
		List<String> list = new ArrayList<String>();
		String query = " select kp.key_person_name from key_person kp, org_office oo "
				+ " where kp.active='Y' and kp.org_office_id=oo.org_office_id and oo.org_office_name='" + q2 + "' ";
		try {
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			q = q.trim();
			while (rset.next()) { // move cursor from BOR to valid record.
				String name = rset.getString(1);
				if (name.toUpperCase().startsWith(q.toUpperCase())) {
					list.add(name);
					count++;
				}
			}
			if (count == 0) {
				list.add("No such name exists.......");
			}
		} catch (Exception e) {
			System.out.println("getPersonName ERROR inside AttendanceReportModel - " + e);
		}
		return list;
	}

	public List<String> getEnrollNo(String q, String q2, String q3, int login_id) {
		List<String> list = new ArrayList<String>();
		String query = " select emp_code from "
				+ " key_person kp, org_office oo where kp.active='Y' and kp.key_person_name='" + q2 + "' "
				+ " and oo.org_office_id=kp.org_office_id and org_office_name='" + q3 + "' ";
		try {
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			q = q.trim();
			while (rset.next()) { // move cursor from BOR to valid record.
				String enroll = rset.getString(1);
				if (enroll.toUpperCase().startsWith(q.toUpperCase())) {
					list.add(enroll);
					count++;
				}
			}
			if (count == 0) {
				list.add("No such Enrollment no exists.......");
			}
		} catch (Exception e) {
			System.out.println("getPersonName ERROR inside AttendanceReportModel - " + e);
		}
		return list;
	}

	public List<String> getRollNo(String q, String q2, String q3, String q4, int login_office_id) {
		List<String> list = new ArrayList<String>();
		// System.out.println("q--- "+q+" q2--- "+q2+" q3---- "+q3);
		String query = "";
		try {
			if (q4 == "Student") {

				query = " select kp.emp_code from key_person kp, org_office oo , designation d where kp.active='Y' and kp.key_person_name='"
						+ q2 + "' " + " and oo.org_office_id=kp.org_office_id and oo.org_office_name='" + q3 + "' and "
						+ " oo.organisation_id='" + login_office_id
						+ "' and kp.designation_id= d.designation_id and d.designation='Student'";
			} else {
				query = " select kp.emp_code from key_person kp, org_office oo , designation d where kp.active='Y' and kp.key_person_name='"
						+ q2 + "' " + " and oo.org_office_id=kp.org_office_id  and " + " oo.organisation_id='"
						+ login_office_id + "' and kp.designation_id= d.designation_id and d.designation='" + q4 + "' ";

			}
			// System.out.println("query --"+query);
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			q = q.trim();
			while (rset.next()) {
				String rollno = rset.getString(1);
				// if (rollno.toUpperCase().startsWith(q.toUpperCase())) {
				list.add(rollno);
				count++;
				// }
			}

			if (count == 0) {
				list.add("No such roll no exists.......");
			}
		} catch (Exception e) {
			System.out.println("getRollNo ERROR inside VisitAttendModel - " + e);
		}
		return list;
	}

	public List<String> getAllDetails(String q, int login_office_id) {
		List<String> list = new ArrayList<String>();
		// System.out.println("q--- "+q+" q2--- "+q2+" q3---- "+q3);
		String query = "";
		try {

			query = " select kp.key_person_name, d.designation, oo.org_office_name as section, "
					+ " (select org_office_name from org_office where org_office_id=oo.parent_org_office_id and active='Y') as class_name "
					+ " from key_person kp, org_office oo, designation d "
					+ " where kp.active='Y' and oo.active='Y' and d.active='Y' and kp.designation_id=d.designation_id and "
					+ " kp.org_office_id=oo.org_office_id and kp.emp_code='" + q + "' ";
			// System.out.println("query --"+query);
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			q = q.trim();
			while (rset.next()) {
				list.add(rset.getString(1));
				list.add(rset.getString(2));
				list.add(rset.getString(3));
				list.add(rset.getString(4));
				count++;
			}

			if (count == 0) {
				list.add("No such roll no exists.......");
			}
		} catch (Exception e) {
			System.out.println("getAllDetails ERROR inside VisitAttendModel - " + e);
		}
		return list;
	}

	public static String getPath(String q) {
		String path = "";
		String query = " select image_path from key_person where emp_code='" + q + "' and active='Y' ";
		try {
			System.out.println("query -" + query);
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			System.out.println("query 22 -" + query);
			int count = 0;
			q = q.trim();
			while (rset.next()) { // move cursor from BOR to valid record.
				path = rset.getString(1);
			}
		} catch (Exception e) {
			System.out.println("getPath ERROR inside VisitAttendModel - " + e);
		}
		return path;
	}

	public void saveData(String name, String rfid, String device_name) {
		int count = 0;
		String name_id = "";
		String device_info_id = "";
		PreparedStatement psmt;
		try {

			String qry = " select key_person_id from key_person where key_person_name='" + name + "' "
					+ " and active='Y' ";
			psmt = connection.prepareStatement(qry);
			ResultSet rst = psmt.executeQuery();
			while (rst.next()) {
				name_id = rst.getString(1);
			}

			psmt = null;
			rst = null;
			String qry2 = " select device_info_id from device_info where device_name='" + device_name
					+ "' and active='Y' ";
			psmt = connection.prepareStatement(qry2);
			rst = psmt.executeQuery();
			while (rst.next()) {
				device_info_id = rst.getString(1);
			}

			psmt = null;
			connection.setAutoCommit(false);
			String query = " insert into rfid_registration(key_person_id,mapped_rfid,device_info_id,created_by) "
					+ " values(?,?,?,?) ";
			psmt = connection.prepareStatement(query);
			psmt.setString(1, name_id);
			psmt.setString(2, rfid);
			psmt.setString(3, device_info_id);
			psmt.setString(4, "Vikrant");
			count = psmt.executeUpdate();

			if (count > 0) {
				connection.commit();
				// connection.close();
				message = "Record updated successfully";
				msgBgColor = COLOR_OK;
			} else {
				connection.rollback();
				// connection.close();
				message = "Error";
				msgBgColor = COLOR_ERROR;
			}

		} catch (Exception e) {
			System.out.println("VisitAttendModel.saveData() -" + e);
		}
	}

	// To show registered Data

	public static List<AttendanceReportModel> showDesignation(String login_office, int login_office_id) {
		List list = new ArrayList();
		/*
		 * String query =
		 * " select dr.dev_reg_id, dr.device_rfid_id, kp.key_person_name, " +
		 * " if (dr.active ='Y' ,'active' ,'inactive') as status " +
		 * " from device_registration dr, key_person kp " +
		 * " where kp.key_person_id=dr.key_person_id and kp.active='Y' ";
		 */
		String query = " select * from designation where active='Y' ";

		try {
			// System.out.println("query -"+query);
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				AttendancrReportBean bean = new AttendancrReportBean();
				bean.setDesignation(rs.getString(1));
				list.add(bean);
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list;
	}

	// end to show reg data

	/*
	 * public static int getShiftID(String shift_name) { shift_name = (shift_name);
	 * String query = "SELECT shift_type_id FROM shift_type WHERE shift_type = ?";
	 * int id = 0; try { PreparedStatement pstmt = (PreparedStatement)
	 * connection.prepareStatement(query); pstmt.setString(1, shift_name); ResultSet
	 * rset = pstmt.executeQuery(); while (rset.next()) { // move cursor from BOR to
	 * valid record. id = rset.getInt("shift_type_id"); } } catch (Exception e) {
	 * System.out.println("Error: " + e); } return id; }
	 * 
	 * public boolean insertRecord(VisitAttendBean bean) { boolean status = false;
	 * String query = ""; int rowsAffected = 0; int shift_type_id =
	 * bean.getShift_type_id(); if (shift_type_id == 0) query =
	 * "insert into shift_type (shift_type,starting_time,ending_time,active) values(?,?,?,?)"
	 * ;
	 * 
	 * try { PreparedStatement ps = (PreparedStatement)
	 * connection.prepareStatement(query); ps.setString(1, (bean.getShift_type()));
	 * ps.setString(2, bean.getStarting_time()); ps.setString(3,
	 * bean.getEnding_time());
	 * 
	 * ps.setString(4, "Y");
	 * 
	 * rowsAffected = ps.executeUpdate(); if (rowsAffected > 0) status = true; }
	 * catch (Exception e) { System.out.println("ERROR: " + e); } if (rowsAffected >
	 * 0) { message = "Record Inserted successfully......"; msgBgColor = COLOR_OK;
	 * System.out.println("Inserted"); } else { message =
	 * "Record Not Inserted Some Error!"; msgBgColor = COLOR_ERROR;
	 * System.out.println("not Inserted"); } return status; }
	 * 
	 * public int updateRecord(VisitAttendBean org_office_type, int shift_type_id) {
	 * int revision = VisitAttendModel.getRevisionno(org_office_type,
	 * shift_type_id); int updateRowsAffected = 0; boolean status = false; String
	 * query1 = "SELECT max(revision_no)  FROM shift_type WHERE shift_type_id = " +
	 * shift_type_id + "  && active=? "; String query2 =
	 * "UPDATE shift_type SET active =? WHERE shift_type_id =? and revision_no=? ";
	 * String query3 =
	 * "INSERT INTO shift_type(shift_type_id,shift_type,starting_time,ending_time,active,revision_no) VALUES(?,?,?,?,?,?)"
	 * ; int rowsAffected = 0; try { PreparedStatement pstmt =
	 * connection.prepareStatement(query1); // pstmt.setInt(1,organisation_type_id);
	 * pstmt.setString(1, "Y"); ResultSet rs = pstmt.executeQuery(); if (rs.next())
	 * { PreparedStatement pstm = connection.prepareStatement(query2);
	 * 
	 * pstm.setString(1, "n");
	 * 
	 * pstm.setInt(2, shift_type_id); pstm.setInt(3, revision); updateRowsAffected =
	 * pstm.executeUpdate(); if (updateRowsAffected >= 1) { revision =
	 * rs.getInt("max(revision_no)") + 1; PreparedStatement psmt =
	 * (PreparedStatement) connection.prepareStatement(query3); psmt.setInt(1,
	 * (shift_type_id)); psmt.setString(2, org_office_type.getShift_type());
	 * psmt.setString(3, org_office_type.getStarting_time()); psmt.setString(4,
	 * org_office_type.getEnding_time()); psmt.setString(5, "Y"); psmt.setInt(6,
	 * revision); rowsAffected = psmt.executeUpdate(); if (rowsAffected > 0) status
	 * = true; else status = false; }
	 * 
	 * } } catch (Exception e) {
	 * System.out.println("OrgOfficeTypeModel updateRecord() Error: " + e); } if
	 * (rowsAffected > 0) { message = "Record updated successfully."; msgBgColor =
	 * COLOR_OK; } else { message = "Cannot update the record, some error.";
	 * msgBgColor = COLOR_ERROR; } return rowsAffected; }
	 */

	/*
	 * public static int getRevisionno(VisitAttendBean bean, int shift_type_id) {
	 * int revision = 0; try {
	 * 
	 * String query =
	 * " SELECT max(revision_no) as revision_no FROM shift_type WHERE shift_type_id ="
	 * + shift_type_id + "  && active='Y';";
	 * 
	 * PreparedStatement pstmt = (PreparedStatement)
	 * connection.prepareStatement(query);
	 * 
	 * ResultSet rset = pstmt.executeQuery();
	 * 
	 * while (rset.next()) { revision = rset.getInt("revision_no");
	 * 
	 * } } catch (Exception e) { } return revision; }
	 * 
	 * public boolean deleteRecord(int shift_type_id) { boolean status = false; int
	 * rowsAffected = 0; try { rowsAffected =
	 * connection.prepareStatement("Delete from shift_type where shift_type_id=" +
	 * shift_type_id) .executeUpdate(); if (rowsAffected > 0) status = true; else
	 * status = false; } catch (Exception e) { System.out.println("ERROR: " + e); }
	 * if (rowsAffected > 0) { message = "Record Deleted successfully......";
	 * msgBgColor = COLOR_OK; System.out.println("Deleted"); } else { message =
	 * "Record Not Deleted Some Error!"; msgBgColor = COLOR_ERROR;
	 * System.out.println("not Deleted"); } return status; }
	 * 
	 * public List<String> getShift(String q) { List<String> list = new
	 * ArrayList<String>(); String query =
	 * " SELECT distinct shift_type FROM shift_type o where o.active='Y' ORDER BY shift_type "
	 * ; try { ResultSet rset = connection.prepareStatement(query).executeQuery();
	 * int count = 0; q = q.trim(); while (rset.next()) { // move cursor from BOR to
	 * valid record. String office_type = (rset.getString("shift_type")); if
	 * (office_type.toUpperCase().startsWith(q.toUpperCase())) {
	 * list.add(office_type); count++; } } if (count == 0) {
	 * list.add("No such Organisation Office Type exists."); } } catch (Exception e)
	 * { System.out.println("getOrgOfficeType ERROR - " + e); } return list; }
	 */

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMsgBgColor() {
		return msgBgColor;
	}

	public void setMsgBgColor(String msgBgColor) {
		this.msgBgColor = msgBgColor;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			System.out.println("getOrgOfficeType closeConnection() Error: " + e);
		}
	}
}