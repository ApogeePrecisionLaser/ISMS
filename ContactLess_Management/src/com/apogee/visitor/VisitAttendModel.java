package com.apogee.visitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.apogee.visitor.VisitAttendBean;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

//import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Vikrant
 */
public class VisitAttendModel {

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

	public static List<VisitAttendBean> showData(int lowerLimit, int noOfRowsToDisplay, String shift) {
		List list = new ArrayList();
		String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
		if (lowerLimit == -1)
			addQuery = "";
		String query = " select va.visitor_date_time_coming,va.visitor_status,va.status_code,vd.person_name, "
				+ " vd.person_mobile_no,vd.person_id_no " + " from visitor_attendance va, visitor_details vd "
				+ " where va.active='Y' and vd.active='Y' and vd.visitor_details_id=va.visitor_details_id ";
		try {
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VisitAttendBean bean = new VisitAttendBean();
				bean.setComing_time(rs.getString(1));
				bean.setVisitor_status(rs.getString(2));
				bean.setStatus_code(rs.getString(3));
				bean.setPerson_name(rs.getString(4));
				bean.setPerson_mobile_no(rs.getString(5));
				bean.setPerson_id(rs.getString(6));
				list.add(bean);
			}
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list;
	}

	// showDataForDetails
	public static List<VisitAttendBean> showDataForDetails() {
		List list = new ArrayList();
		String query = " select visitor_details_id,person_name,person_mobile_no,person_id_type,person_id_no, "
				+ " created_date from visitor_details ";
		try {
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VisitAttendBean bean = new VisitAttendBean();
				bean.setDetails_id(rs.getString(1));
				bean.setPerson_name(rs.getString(2));
				bean.setPerson_mobile_no(rs.getString(3));
				bean.setPerson_id_type(rs.getString(4));
				bean.setPerson_id(rs.getString(5));
				bean.setCreated_date(rs.getString(6));
				list.add(bean);
			}
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list;
	}

	// start show data for Temperature Logs showDataForTempLog

	public static List<VisitAttendBean> showDataForTempLog(String login_office, int login_office_id, String type,
			String parent, String className) {
		List list = new ArrayList();
		
		if(type==null) {
			type="";
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

				} else if(parent.equals("section")) {
					query += " and oo.org_office_name='" + className + "' ";
				}

			}
			query += " order by ddt.device_data_datetime desc ";

			String qry = " select vd.person_name,date(va.created_date)as date, time(va.created_date)as time,va.temperature "
					+ " ,vd.person_id_image_name from visitor_attendance va, visitor_details vd "
					+ " where va.active='Y' and vd.active='Y' and vd.visitor_details_id=va.visitor_details_id ";

			//System.out.println("query 1 --" + query);

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

	public static List<String> showDataForTempLogForMob() {
		List list = new ArrayList();
		List<String> list1 = new ArrayList<>();
		String query = " select ds.device_emp_name,dd.temperature,date(dd.created_date)as date, time(dd.created_date)as time, "
				+ " dd.image_path from device_data dd, device_status ds "
				+ " where dd.device_status_id=ds.device_status_id and dd.active='Y' and ds.active='Y' ";
		try {
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VisitAttendBean bean = new VisitAttendBean();
				bean.setEmp_name(rs.getString(1));
				bean.setTemperature(rs.getDouble(2));
				bean.setDate(rs.getString(3));
				bean.setTime(rs.getString(4));
				bean.setImage_name(rs.getString(5));
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
				list1.add(rs.getString(1));
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list1;
	}

	// start show data for Temperature Logs showDataForTempLog22

	public static List<VisitAttendBean> showDataForTempLog2(String login_office, int login_office_id, String type,
			String parent, String className) {
		List list = new ArrayList();
		int countSlight = 0, countHigh = 0, countNormal = 0, countLow = 0, totalCount = 0;			
			if(type==null) {
				type="";
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

					} else if(parent.equals("section")) {
						query += " and oo.org_office_name='" + className + "' ";
					}

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

	public List<String> getPersonId(String q, String q2, String q3, int login_id) {
		List<String> list = new ArrayList<String>();

		// System.out.println("q3 val --"+q3);
		if (q3.equals("Student")) {
			String qry = " select org_office_id from org_office where active='Y' " + " and organisation_id='" + login_id
					+ "' and org_office_name='" + q2 + "' ";
			String office_id = "";
			try {
				ResultSet rset = connection.prepareStatement(qry).executeQuery();
				while (rset.next()) { // move cursor from BOR to valid record.
					office_id = rset.getString(1);
				}

				rset = null;
				String query = " select key_person_name from key_person where org_office_id='" + office_id
						+ "' and active='Y' and designation_id=38 ";
				rset = connection.prepareStatement(query).executeQuery();
				int count = 0;
				q = q.trim();
				while (rset.next()) {
					String key_person_name = rset.getString("key_person_name");
					if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
						list.add(key_person_name);
						count++;
					}
				}
				if (count == 0) {
					list.add("No such key Person Id exists.......");
				}
			} catch (Exception e) {
				System.out.println("getPersonId ERROR inside VisitAttendModel - " + e);
			}
		} else {
			try {
				String query = " select kp.key_person_name " + " from key_person kp, designation d, org_office oo "
						+ " where kp.designation_id=d.designation_id and oo.org_office_id=kp.org_office_id and "
						+ " oo.organisation_id='" + login_id + "' and d.designation='" + q3 + "' ";
				ResultSet rset = connection.prepareStatement(query).executeQuery();
				int count = 0;
				q = q.trim();
				while (rset.next()) {
					String key_person_name = rset.getString(1);
					if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
						list.add(key_person_name);
						count++;
					}
				}
				if (count == 0) {
					list.add("No such name exists.......");
				}
			} catch (Exception e) {
				System.out.println("getPersonId ERROR inside VisitAttendModel - " + e);
			}

		}
		return list;
	}

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
				// System.out.println("key person name " + key_person_name);
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
		String query = " select org_office_name from org_office where org_office_id "
				+ " in(63,64,65,66,67,68,69,70,71,72,73,74,75,76) and parent_org_office_id=62 and organisation_id="
				+ login_id + " ";
		try {
			ResultSet rset = connection.prepareStatement(query).executeQuery();
			int count = 0;
			q = q.trim();
			while (rset.next()) { // move cursor from BOR to valid record.
				String class_name = rset.getString(1);
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

	public static List<VisitAttendModel> showRegData(String login_office, int login_office_id) {
		List list = new ArrayList();
		/*
		 * String query =
		 * " select dr.dev_reg_id, dr.device_rfid_id, kp.key_person_name, " +
		 * " if (dr.active ='Y' ,'active' ,'inactive') as status " +
		 * " from device_registration dr, key_person kp " +
		 * " where kp.key_person_id=dr.key_person_id and kp.active='Y' ";
		 */
		String query = " select rr.mapped_rfid,kp.key_person_name,if (rr.active ='Y' ,'active' ,'inactive') as status,rr.rfid_reg_id "
				+ " ,kp.emp_code,kp.image_path,"
				+ " (select org_office_name from org_office where org_office_id=oo.parent_org_office_id and active='Y') as class_name,"
				+ " d.designation "
				+ " from rfid_registration rr, key_person kp, device_info di,org_office oo, designation d "
				+ " where kp.key_person_id=rr.key_person_id and kp.active='Y' and rr.active='Y' "
				+ " and oo.org_office_id=kp.org_office_id and oo.organisation_id='" + login_office_id + "' "
				+ " and di.device_info_id=rr.device_info_id and di.active='Y' "
				+ " and kp.designation_id=d.designation_id order by rr.rfid_reg_id desc ";

		try {
			// System.out.println("query -"+query);
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VisitAttendBean bean = new VisitAttendBean();
				bean.setDev_id(rs.getString(1));
				bean.setEmp_name(rs.getString(2));
				bean.setStatus(rs.getString(3));
				bean.setEmp_code(rs.getString(5));
				bean.setImage_name(rs.getString(6));
				bean.setClass_name(rs.getString(7));
				bean.setDesignation(rs.getString(8));
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