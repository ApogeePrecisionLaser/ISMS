package com.apogee.sms;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
//import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Context;

public class CheckHighTemperature implements ServletContextListener {
	
	@Context
	static
	ServletContext serveletContext;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		// Your code here
		//System.out.println("HelloWorld Listener has been shutdown");

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		// Your code here
		//System.out.println("HelloWorld Listener initialized.");

		TimerTask vodTimer = new VodTimerTask();

		Timer timer = new Timer();
		timer.schedule(vodTimer, 1000, (2 * 1000));

	}
	
	class VodTimerTask extends TimerTask {

		@Override
		public void run() {
			String id="";
			id = checkHighTemp();
			System.out.println("get status --" + id);

			// for sms send to admin
			if (id!="") {
				//System.out.println("under iff --"+id);
				String tosend = "false";
				//String number = "8882349596";
				String number = id;
				System.out.println("=numberrr --"+number);
				String body = "Hi Vikrant! Your child has High Temperature. Pls visit soon.";
				String unknum = sendSmsToAssignedFor(number, body);
				//String unknum2 = sendSmsToAssignedFor("8368595005", body);
				if (unknum.equals("OK")) {
					//System.out.println("OTP HAS BEEN SENT");
					tosend = "true";
				}
			} else {
				//System.out.println("sahi hai");
			}
			// end sms sent to admin

		}
	}

	/*public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new CheckHighTemperature(), 3000, 1*60*1000);
	}*/

	public static String checkHighTemp() {
		String rfid="";
		String mob_no="";
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection connection=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/school","root","root");
			
			//String query=" select * from device_data_tcp where device_temperature>100 and created_date='2020-09-17 19:26:59' ";
			String query=" select * from device_data_tcp where " + 
					" created_date > now() - interval 3 second and device_temperature>100 ";
			//System.out.println("query -"+query);
			PreparedStatement psmt = connection.prepareStatement(query);
			ResultSet rst = psmt.executeQuery();
			while (rst.next()) {
				rfid=rst.getString(4);
			}
			
			if(rfid!="") {
				String qry=" select kp.emergency_contact_mobile " + 
						" from key_person kp, rfid_registration rr " + 
						" where rr.mapped_rfid='"+rfid+"' and kp.active='Y' " +
						" and kp.key_person_id=rr.key_person_id and rr.active='Y' ";
				//System.out.println("query 22 -"+qry);
				PreparedStatement psmt2 = connection.prepareStatement(qry);
				ResultSet rst2 = psmt2.executeQuery();
				while (rst2.next()) {
					mob_no=rst2.getString(1);
				}
			}
			
			
			
		}catch(Exception e) {
			System.out.println("Exception in CheckHighTemperature service -"+e);
		}
		/*if(temp !="") {
			return rfid;
		}else {
			return rfid;
		}*/
		System.out.println("mob no --"+mob_no);
		return mob_no;
	}

	public static String random(int size) {
		StringBuilder generatedToken = new StringBuilder();
		try {
			SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
			// Generate 20 integers 0..20
			for (int i = 0; i < size; i++) {
				generatedToken.append(number.nextInt(9));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return generatedToken.toString();
	}

	public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
		String result = "";
		try {
			String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";
			messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
			String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number="
					+ numberStr1 + "&text=" + messageStr1 + "&route=";
			String url = host_url + queryString;
			result = callURL(url);
			//System.out.println("SMS URL: " + url);
		} catch (Exception e) {
			result = e.toString();
			System.out.println("SMSModel sendSMS() Error: " + e);
		}
		return result;
	}

	private String callURL(String strURL) {
		String status = "";
		try {
			java.net.URL obj = new java.net.URL(strURL);
			HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
			httpReq.setDoOutput(true);
			httpReq.setInstanceFollowRedirects(true);
			httpReq.setRequestMethod("GET");
			status = httpReq.getResponseMessage();
		} catch (MalformedURLException me) {
			status = me.toString();
		} catch (IOException ioe) {
			status = ioe.toString();
		} catch (Exception e) {
			status = e.toString();
		}
		return status;
	}

}
