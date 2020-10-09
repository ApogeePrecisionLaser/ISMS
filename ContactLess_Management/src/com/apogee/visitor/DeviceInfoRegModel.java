package com.apogee.visitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.apogee.visitor.VisitAttendBean;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class DeviceInfoRegModel {

	private static Connection connection;
	private String message;
	private String msgBgColor;
	private final String COLOR_OK = "yellow";
	private final String COLOR_ERROR = "red";

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

	public void saveData(String device_id, String device_purpose, String device_name, String device_info_id) {
		int count = 0;
		String name_id = "";
		PreparedStatement psmt;
		ResultSet rst;
		try {
			connection.setAutoCommit(false);
			if (device_info_id.equals("")) {
				String query = " insert into device_info(device_id,device_name,device_purpose,created_by) "
						+ " values(?,?,?,?) ";
				psmt = connection.prepareStatement(query);
				psmt.setString(1, device_id);
				psmt.setString(2, device_name);
				psmt.setString(3, device_purpose);
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
			} else {
				String query = " select * from device_info where device_info_id='" + device_info_id
						+ "' and active='Y' ";
				psmt = connection.prepareStatement(query);
				rst = psmt.executeQuery();
				while (rst.next()) {
					psmt = null;
					String query2 = " update device_info set active='N' where device_info_id='" + device_info_id
							+ "' and revision_no=? ";
					psmt = connection.prepareStatement(query2);
					psmt.setString(1, rst.getString("revision_no"));
					count = psmt.executeUpdate();
					if (count > 0) {
						count = 0;
						psmt = null;
						String query3 = " insert into device_info(device_info_id,device_id,device_name,device_purpose,created_by,revision_no) "
								+ " values(?,?,?,?,?,?)";
						psmt = connection.prepareStatement(query3);
						psmt.setString(1, device_info_id);
						psmt.setString(2, device_id);
						psmt.setString(3, device_name);
						psmt.setString(4, device_purpose);
						psmt.setString(5, "Vikrant");
						psmt.setInt(6, Integer.parseInt(rst.getString("revision_no")) + 1);
						// System.out.println("psmt2 -"+psmt);
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
					} else {
						connection.rollback();
						// connection.close();
						message = "Error";
						msgBgColor = COLOR_ERROR;
					}
				}
			}

		} catch (Exception e) {
			System.out.println("DeviceInfoRegModel.saveData() -" + e);
			message = e.toString();
			msgBgColor = COLOR_ERROR;
		}
	}

	// To show registered Data

	public static List<DeviceInfoRegModel> showRegData(String login_office, int login_office_id) {
		List list = new ArrayList();
		/*
		 * String query =
		 * " select dr.dev_reg_id, dr.device_rfid_id, kp.key_person_name, " +
		 * " if (dr.active ='Y' ,'active' ,'inactive') as status " +
		 * " from device_registration dr, key_person kp " +
		 * " where kp.key_person_id=dr.key_person_id and kp.active='Y' ";
		 */
		String query = " select * from device_info where active='Y' ";

		try {
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				DeviceInfoRegBean bean = new DeviceInfoRegBean();
				bean.setDevice_info_id(rs.getString(1));
				bean.setDevice_id(rs.getString(2));
				bean.setDevice_name(rs.getString(3));

				if (rs.getString(4).equals("R")) {
					bean.setDevice_purpose("Registration");
				}else {
					bean.setDevice_purpose("Attendance");
				}

				bean.setStatus(rs.getString(8));
				bean.setRevision_no(rs.getString(9));
				bean.setCreated_date(rs.getString(11));
				list.add(bean);
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list;
	}

	// end to show reg data
	
	
	// start search temp data	
	public static List<String> searchTempData(String q, int login_office_id) {
		List list = new ArrayList();
		String query = " select distinct kp.key_person_name,ddt.device_temperature, kp.emp_code,oo.org_office_name as section " + 
				" from device_data_tcp ddt, rfid_registration rr, key_person kp, org_office oo " + 
				" where rr.mapped_rfid=ddt.device_rfid and kp.key_person_id=rr.key_person_id and kp.active='Y' " + 
				" and kp.org_office_id=oo.org_office_id and oo.organisation_id='"+login_office_id+"' " + 
				" and device_data_datetime =(select max(device_data_datetime) from device_data_tcp) ";

		try {
			PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
				list.add(rs.getString(2));
				list.add(rs.getString(3));
				list.add(rs.getString(4));				
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return list;
	}
	// end search temp data
	
	//start get image
	public static String getPath(String q) {
		String path="";
		String query = " select image_path from key_person where emp_code='"+q+"' and active='Y' ";
		try {
			ResultSet rset = connection.prepareStatement(query).executeQuery();
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
	// end get image
	

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