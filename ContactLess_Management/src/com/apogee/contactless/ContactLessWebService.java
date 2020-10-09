package com.apogee.contactless;

import java.sql.DriverManager;

import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.*;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Base64Data;

import sun.misc.BASE64Decoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

// for Aadhaar APi
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

//import org.apache.commons.codec.binary.Base64;

@Path("/apiServices")
public class ContactLessWebService {

	@Context
	ServletContext serveletContext;
	Connection connection = null;

	/*
	 * @GET
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public String sayPlainTextHello() { return
	 * "Hello World RESTful Jersey!"; }
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.TEXT_XML) public String sayXMLHello() { return
	 * "<?xml version=\"1.0\"?>" + "<hello> Hello World RESTful Jersey" +
	 * "</hello>"; }
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.TEXT_HTML) public String sayHtmlHello() { return
	 * "<html> " + "<title>" + "Hello World RESTful Jersey" + "</title>" +
	 * "<body><h1>" + "Hello World RESTful Jersey" + "</body></h1>" + "</html> "; }
	 */

	@POST
	@Path("/checkMobileNo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject sendData(JSONObject obj) throws JSONException {
		String result = "notregister";
		int count = 0;
		JSONObject jsonObj = new JSONObject();

		try {
			PreparedStatement psmt;
			ResultSet rst;
			String url = "", name = "", pass = "", person_mobile_no = "", id_type = "", id_no = "", aadhar_kyc = "";
			String mobile_no = obj.get("mobile_no").toString();
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(serveletContext.getInitParameter("connectionString"),
					serveletContext.getInitParameter("db_user_name"),
					serveletContext.getInitParameter("db_user_password"));

			// String query = "select count(*) from visitor_details where person_mobile_no="
			// + mobile_no + " ";
			String query = " select count(*), person_name,person_id_type,person_id_no,aadhar_kyc,person_mobile_no "
					+ " from visitor_details where person_mobile_no='" + mobile_no + "' ";
			psmt = connection.prepareStatement(query);
			rst = psmt.executeQuery();
			while (rst.next()) {
				count = rst.getInt(1);
				name = rst.getString(2);
				id_type = rst.getString(3);
				id_no = rst.getString(4);
				aadhar_kyc = rst.getString(5);
				person_mobile_no = rst.getString(6);
			}

			if (count > 0) {
				result = "success";
				jsonObj.put("result", result);
				jsonObj.put("name", name);
				jsonObj.put("id_type", id_type);
				jsonObj.put("id_no", id_no);
				jsonObj.put("aadhar_kyc", aadhar_kyc);
				jsonObj.put("person_mobile_no", person_mobile_no);
			} else {
				jsonObj.put("result", result);
			}

		} catch (Exception e) {
			System.out.println("exception - " + e);
			result = "error";
			jsonObj.put("result", result);
		}
		return jsonObj;
	}

	@POST
	@Path("/saveVisitorsData")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String sendDataObj(JSONObject jObj) throws JSONException {
		// String str1 = jObj.get("number").toString();
		// System.out.println("string 1 --" + str1);
		String result = "";
		// obj.put("result", str1);
		int count = 0;

		try {
			PreparedStatement psmt;
			ResultSet rst;
			String name = "", number = "", idtype = "", idnumber = "", image_name = "", image = "";
			name = jObj.get("user_name").toString();
			number = jObj.get("user_number").toString();
			idtype = jObj.get("user_idtype").toString();
			idnumber = jObj.get("user_idnumber").toString();
			image_name = jObj.get("user_imagename").toString();
			image = jObj.get("user_image").toString();

			System.out.println("image ---" + image);

			OutputStream out = null;
			List<File> fileList = new ArrayList<File>();

			byte[] imageAsBytes = new BASE64Decoder().decodeBuffer(image);
			String file = "C:\\ContactLess Mgmt\\Visitors\\";
			File directory = new File(file);
			if (!directory.exists()) {
				directory.mkdir();
			}
			/*
			 * file.concat(image_name); fileList.add(directory);
			 */

			out = new FileOutputStream(file + "/" + image_name);
			out.write(imageAsBytes);
			out.close();

			Class.forName("com.mysql.jdbc.Driver");
			// Connection con=DriverManager.getConnection(url,name,pass);
			Connection connection = DriverManager.getConnection(serveletContext.getInitParameter("connectionString"),
					serveletContext.getInitParameter("db_user_name"),
					serveletContext.getInitParameter("db_user_password"));

			String query = "insert into visitor_details(person_name,person_mobile_no,person_id_type,person_id_no,"
					+ " person_id_image_name,created_by) " + " values(?,?,?,?,?,?); ";
			psmt = connection.prepareStatement(query);
			psmt.setString(1, name);
			psmt.setString(2, number);
			psmt.setString(3, idtype);
			psmt.setString(4, idnumber);
			psmt.setString(5, image_name);
			psmt.setString(6, "vikrant");

			count = psmt.executeUpdate();
			if (count > 0) {
				result = "sucess";
			} else {
				result = "failure";
			}

		} catch (Exception e) {
			System.out.println("exception - " + e);
		}
		return result;
	}

	@POST
	@Path("/saveVisitorsAttendance")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveVisitorsAttendance(JSONObject obj) throws JSONException {
		String result = "fail";
		PreparedStatement psmt;
		ResultSet rst;
		int count = 0, visitor_details_id = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(serveletContext.getInitParameter("connectionString"),
					serveletContext.getInitParameter("db_user_name"),
					serveletContext.getInitParameter("db_user_password"));

			String query0 = "select visitor_details_id from visitor_details where active='Y' "
					+ " and person_mobile_no=" + obj.get("mobile_no").toString() + " ";
			psmt = connection.prepareStatement(query0);
			rst = psmt.executeQuery();
			while (rst.next()) {
				visitor_details_id = rst.getInt(1);
			}
			psmt = null;
			rst = null;

			String query = "insert into visitor_attendance(visitor_date_time_coming,visitor_status,status_code,created_by,visitor_details_id,temperature)"
					+ " values(?,?,?,?,?,?)";
			psmt = connection.prepareStatement(query);
			psmt.setString(1, obj.get("coming_time").toString());
			psmt.setString(2, obj.get("status").toString());
			psmt.setString(3, obj.get("status_code").toString());
			psmt.setString(4, "vikrant");
			psmt.setInt(5, visitor_details_id);
			if (obj.get("temperature").toString() != null || obj.get("temperature").toString() != "") {
				psmt.setString(6, obj.get("temperature").toString());
			} else {
				psmt.setString(6, "null");
			}
			count = psmt.executeUpdate();
			if (count > 0) {
				result = "success";
			}

		} catch (Exception e) {
			System.out.println("saveVisitorsAttendance exception -" + e);
			result = "exception";
		}
		return result;
	}

	@POST
	@Path("/encodeImage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject sendEncodeImage(String str) {

		JSONObject obj = new JSONObject();
		int count = 0;

		try {
			PreparedStatement psmt;
			ResultSet rst;
			String url = "", name = "", pass = "";

			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(serveletContext.getInitParameter("connectionString"),
					serveletContext.getInitParameter("db_user_name"),
					serveletContext.getInitParameter("db_user_password"));

			String query = "select order_image_path from order_management where order_mgmt_id=3 and active='Y' ";
			psmt = connection.prepareStatement(query);
			rst = psmt.executeQuery();
			while (rst.next()) {
				name = rst.getString(1);
			}

			byte[] fileContent = null;
			String encodedString = "";
			java.nio.file.Path path = Paths.get(name);
			//

			try {
				fileContent = Files.readAllBytes(path);
			} catch (IOException ex) {

			}

			try {
				encodedString = Base64.getEncoder().encodeToString(fileContent);
				obj.put("image_base64", encodedString);
				// System.out.println("encoded string -" + encodedString);
			} catch (JSONException ex) {

			}
			// end for image

		} catch (Exception e) {
			System.out.println("exception - " + e);
		}
		return obj;
	}

	// Start For Aadhaar Api URL Authenticate
	@POST
	@Path("/apiUrlAuthenticateAadhaar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject apiUrlAuthenticateAadhaar(String str) throws JSONException {
		String result = "";
		JSONObject obj = new JSONObject();
		try {
			obj.put("id_number", str);

			String webPage = "https://kyc-api.aadhaarkyc.io/api/v1/aadhaar-validation/aadhaar-validation";
			String authStringEnc = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1OTc0NzI1MzcsIm5iZiI6MTU5Nz"
					+ "Q3MjUzNywianRpIjoiOTRlODdjMmItMGYwNy00OTlhLTg4MmEtNDQyZTYzZmY3NGU4IiwiZXhwIjoxOTEyODMyN"
					+ "TM3LCJpZGVudGl0eSI6ImRldi52aW5heUBhYWRoYWFyYXBpLmlvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNj"
					+ "ZXNzIiwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.ZdHToer-hTMntCNlF_jS1xVstjv--SZAAAEzJZONkb8";

			URL url = new URL(webPage);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Authorization", "Bearer " + authStringEnc);
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoOutput(true);

			try (OutputStream os = urlConnection.getOutputStream()) {
				byte[] input = str.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				// System.out.println("response --"+response.toString());
				result = response.toString();
			}

			JSONObject jsonObj = new JSONObject(result);
			obj = jsonObj;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
	// End For Aadhaar Api URL Authenticate

	// Start For PAN Card Api URL Authenticate
	@POST
	@Path("/apiUrlAuthenticatePAN")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject apiUrlAuthenticatePAN(String str) throws JSONException {
		String result = "";
		JSONObject obj = new JSONObject();
		try {
			obj.put("id_number", str);

			String webPage = "https://kyc-api.aadhaarkyc.io/api/v1/pan/pan";
			String authStringEnc = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1OTc0NzI1MzcsIm5iZiI6MTU5Nz"
					+ "Q3MjUzNywianRpIjoiOTRlODdjMmItMGYwNy00OTlhLTg4MmEtNDQyZTYzZmY3NGU4IiwiZXhwIjoxOTEyODMyN"
					+ "TM3LCJpZGVudGl0eSI6ImRldi52aW5heUBhYWRoYWFyYXBpLmlvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNj"
					+ "ZXNzIiwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.ZdHToer-hTMntCNlF_jS1xVstjv--SZAAAEzJZONkb8";

			URL url = new URL(webPage);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Authorization", "Bearer " + authStringEnc);
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoOutput(true);

			try (OutputStream os = urlConnection.getOutputStream()) {
				byte[] input = str.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				// System.out.println("response --"+response.toString());
				result = response.toString();
			}

			JSONObject jsonObj = new JSONObject(result);
			obj = jsonObj;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
	// End For PAN Card Api URL Authenticate

	// Start For Voter ID Card Api URL Authenticate
	@POST
	@Path("/apiUrlAuthenticateVoter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject apiUrlAuthenticateVoter(String str) throws JSONException {
		String result = "";
		JSONObject obj = new JSONObject();
		try {
			obj.put("id_number", str);

			String webPage = "https://kyc-api.aadhaarkyc.io/api/v1/voter-id/voter-id";
			String authStringEnc = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1OTc0NzI1MzcsIm5iZiI6MTU5Nz"
					+ "Q3MjUzNywianRpIjoiOTRlODdjMmItMGYwNy00OTlhLTg4MmEtNDQyZTYzZmY3NGU4IiwiZXhwIjoxOTEyODMyN"
					+ "TM3LCJpZGVudGl0eSI6ImRldi52aW5heUBhYWRoYWFyYXBpLmlvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNj"
					+ "ZXNzIiwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.ZdHToer-hTMntCNlF_jS1xVstjv--SZAAAEzJZONkb8";

			URL url = new URL(webPage);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Authorization", "Bearer " + authStringEnc);
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoOutput(true);

			try (OutputStream os = urlConnection.getOutputStream()) {
				byte[] input = str.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				// System.out.println("response --"+response.toString());
				result = response.toString();
			}

			JSONObject jsonObj = new JSONObject(result);
			obj = jsonObj;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
	// End For Voter ID Card Api URL Authenticate

	// Start For DL Api URL Authenticate
	@POST
	@Path("/apiUrlAuthenticateDL")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject apiUrlAuthenticateDL(String str) throws JSONException {
		String result = "";
		JSONObject obj = new JSONObject();
		try {
			obj.put("id_number", str);

			String webPage = "https://kyc-api.aadhaarkyc.io/api/v1/driving-license/driving-license";
			String authStringEnc = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1OTc0NzI1MzcsIm5iZiI6MTU5Nz"
					+ "Q3MjUzNywianRpIjoiOTRlODdjMmItMGYwNy00OTlhLTg4MmEtNDQyZTYzZmY3NGU4IiwiZXhwIjoxOTEyODMyN"
					+ "TM3LCJpZGVudGl0eSI6ImRldi52aW5heUBhYWRoYWFyYXBpLmlvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNj"
					+ "ZXNzIiwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.ZdHToer-hTMntCNlF_jS1xVstjv--SZAAAEzJZONkb8";

			URL url = new URL(webPage);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Authorization", "Bearer " + authStringEnc);
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoOutput(true);

			try (OutputStream os = urlConnection.getOutputStream()) {
				byte[] input = str.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				// System.out.println("response --"+response.toString());
				result = response.toString();
			}

			JSONObject jsonObj = new JSONObject(result);
			obj = jsonObj;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
	// End For DL Api URL Authenticate

	// Start For All Api URL Authenticate
	@POST
	@Path("/apiUrlAuthenticateAll")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject apiUrlAuthenticateAll(JSONObject jObj) throws JSONException {
		String result = "", str = "", type = "", webPage = "";
		JSONObject obj = new JSONObject();
		try {
			type = jObj.get("type").toString();
			str = jObj.get("id_number").toString();

			obj.put("id_number", str);
			str = obj.toString();

			if (type.equals("VOTER")) {
				webPage = "https://kyc-api.aadhaarkyc.io/api/v1/voter-id/voter-id";
			} else if (type.equals("PAN")) {
				webPage = "https://kyc-api.aadhaarkyc.io/api/v1/pan/pan";
			} else if (type.equals("DL")) {
				webPage = "https://kyc-api.aadhaarkyc.io/api/v1/driving-license/driving-license";
			} else if (type.equals("AADHAAR")) {
				webPage = "https://kyc-api.aadhaarkyc.io/api/v1/aadhaar-validation/aadhaar-validation";
			}
			// String webPage =
			// "https://kyc-api.aadhaarkyc.io/api/v1/driving-license/driving-license";

			String authStringEnc = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1OTc0NzI1MzcsIm5iZiI6MTU5Nz"
					+ "Q3MjUzNywianRpIjoiOTRlODdjMmItMGYwNy00OTlhLTg4MmEtNDQyZTYzZmY3NGU4IiwiZXhwIjoxOTEyODMyN"
					+ "TM3LCJpZGVudGl0eSI6ImRldi52aW5heUBhYWRoYWFyYXBpLmlvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNj"
					+ "ZXNzIiwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.ZdHToer-hTMntCNlF_jS1xVstjv--SZAAAEzJZONkb8";

			URL url = new URL(webPage);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Authorization", "Bearer " + authStringEnc);
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoOutput(true);

			try (OutputStream os = urlConnection.getOutputStream()) {
				byte[] input = str.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				// System.out.println("response --"+response.toString());
				result = response.toString();
			}

			JSONObject jsonObj = new JSONObject(result);
			obj = jsonObj;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
	// End For ALL Api URL Authenticate

	@POST
	@Path("/sendOTP")
	@Produces(MediaType.APPLICATION_JSON) // http://192.168.1.15:8084/trafficSignals_new/api/service/hello
	@Consumes(MediaType.APPLICATION_JSON)
	public String sendOTP(String number) {
		/*
		 * GeneralModel gm = new GeneralModel();
		 * gm.setDriver(serveletContext.getInitParameter("driverClass"));
		 * gm.setUrl(serveletContext.getInitParameter("connectionString"));
		 * gm.setUser(serveletContext.getInitParameter("db_user_name"));
		 * gm.setPassword(serveletContext.getInitParameter("db_user_password"));
		 * gm.setConnection(); String tosend = "false";
		 */

		// String otp = gm.random(6);
		String tosend = "false";
		String otp = random(6);
		System.out.println("OTP is :" + otp);
		/*
		 * if (otpMap.containsKey(number)) { otpMap.remove(number); } otpMap.put(number,
		 * otp);
		 */

		// String unknum = gm.sendSmsToAssignedFor(number, otp);
		String unknum = sendSmsToAssignedFor(number, otp);
		if (unknum.equals("OK")) {
			System.out.println("OTP HAS BEEN SENT");
			tosend = "true";
		}

		System.out.println("Response on mobile number :" + tosend);
		return tosend;
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
			System.out.println("SMS URL: " + url);
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

	@POST
	@Path("/callAnotherApi")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String callAnotherApi(String jObj) throws JSONException {
		String result = "get result", str = "", type = "", webPage = "";
		JSONObject obj = new JSONObject();
		try {
			result = "i love  you " + jObj;
		} catch (Exception e) {
			System.out.println("error -" + e);
		}

		return result;
	}

	@POST
	@Path("/callApi")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String callApi(String jObj) throws JSONException {
		String result = "", str = "", type = "", webPage = "";
		JSONObject obj = new JSONObject();
		try {
			result = callAnotherApi(jObj);

		} catch (Exception e) {
			System.out.println("error -" + e);
		}

		return result;
	}

	// aadhaar kyc
	@POST
	@Path("/aadhaarKYC")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject aadhaarKYC(String str) throws JSONException {
		String result = "";
		JSONObject obj = new JSONObject();
		try {
			// obj.put("id_number", str);
			JSONObject obj2 = new JSONObject(str);
			String id_number = obj2.getString("id_number");

			String webPage = "https://kyc-api.aadhaarkyc.io/api/v1/aadhaar-v2/generate-otp";
			String authStringEnc = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1OTc0NzI1MzcsIm5iZiI6MTU5Nz"
					+ "Q3MjUzNywianRpIjoiOTRlODdjMmItMGYwNy00OTlhLTg4MmEtNDQyZTYzZmY3NGU4IiwiZXhwIjoxOTEyODMyN"
					+ "TM3LCJpZGVudGl0eSI6ImRldi52aW5heUBhYWRoYWFyYXBpLmlvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNj"
					+ "ZXNzIiwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.ZdHToer-hTMntCNlF_jS1xVstjv--SZAAAEzJZONkb8";

			URL url = new URL(webPage);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Authorization", "Bearer " + authStringEnc);
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoOutput(true);

			try (OutputStream os = urlConnection.getOutputStream()) {
				byte[] input = str.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				// System.out.println("response --"+response.toString());
				result = response.toString();
			}

			JSONObject jsonObj = new JSONObject(result);
			obj = jsonObj;

			// System.out.println("obj length --"+obj.length());
			// System.out.println("dataaa --"+obj.getString("data"));

			JSONObject client = new JSONObject(obj.getString("data"));
			String client_id = client.getString("client_id");
			// System.out.println("clinet idd --"+client.getString("client_id"));

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(
						serveletContext.getInitParameter("connectionString"),
						serveletContext.getInitParameter("db_user_name"),
						serveletContext.getInitParameter("db_user_password"));

				String query = "update visitor_details set aadhaar_client_id='" + client_id + "' "
						+ " where person_id_no='" + id_number + "' ";
				PreparedStatement psmt = connection.prepareStatement(query);
				int count = psmt.executeUpdate();

			} catch (Exception e) {
				System.out.println("error in aadhaar kyc -" + e);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
	// end aadhaar kyc

	// submit otp for aadhaar kyc
	@POST
	@Path("/aadhaarOtpVerify")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject aadhaarOtpVerify(JSONObject jsonObj) throws JSONException {
		String result = "";
		JSONObject obj = new JSONObject();
		try {
			// obj.put("id_number", str);

			String id_number = "", client_id = "", otp = "", str = "";
			id_number = jsonObj.getString("id_number");
			otp = jsonObj.getString("otp");

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection(
						serveletContext.getInitParameter("connectionString"),
						serveletContext.getInitParameter("db_user_name"),
						serveletContext.getInitParameter("db_user_password"));

				String query = "select aadhaar_client_id from visitor_details " + " where person_id_no='" + id_number
						+ "' ";
				PreparedStatement psmt = connection.prepareStatement(query);
				ResultSet rst = psmt.executeQuery();
				while (rst.next()) {
					client_id = rst.getString(1);
				}

			} catch (Exception e) {
				System.out.println("error in aadhaar kyc -" + e);
			}

			JSONObject obj2 = new JSONObject();
			obj2.put("client_id", client_id);
			obj2.put("otp", otp);

			str = obj2.toString();
			System.out.println("strr -" + str);

			String webPage = "https://kyc-api.aadhaarkyc.io/api/v1/aadhaar-v2/submit-otp";
			String authStringEnc = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1OTc0NzI1MzcsIm5iZiI6MTU5Nz"
					+ "Q3MjUzNywianRpIjoiOTRlODdjMmItMGYwNy00OTlhLTg4MmEtNDQyZTYzZmY3NGU4IiwiZXhwIjoxOTEyODMyN"
					+ "TM3LCJpZGVudGl0eSI6ImRldi52aW5heUBhYWRoYWFyYXBpLmlvIiwiZnJlc2giOmZhbHNlLCJ0eXBlIjoiYWNj"
					+ "ZXNzIiwidXNlcl9jbGFpbXMiOnsic2NvcGVzIjpbInJlYWQiXX19.ZdHToer-hTMntCNlF_jS1xVstjv--SZAAAEzJZONkb8";

			URL url = new URL(webPage);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Authorization", "Bearer " + authStringEnc);
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setDoOutput(true);

			try (OutputStream os = urlConnection.getOutputStream()) {
				byte[] input = str.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				// System.out.println("response --"+response.toString());
				result = response.toString();
			}

			JSONObject jsonObj2 = new JSONObject(result);
			obj = jsonObj2;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
	// end submit otp for aadhaar kyc

	// live dash data
	@GET
	@Path("/liveDashData")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public JSONObject liveDashData() throws JSONException {
		String result = "";
		JSONObject obj = new JSONObject();
		try {
			PreparedStatement psmt;
			ResultSet rst;
			String url = "", name = "", pass = "", person_mobile_no = "", id_type = "", id_no = "", aadhar_kyc = "";			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(serveletContext.getInitParameter("connectionString"),
					serveletContext.getInitParameter("db_user_name"),
					serveletContext.getInitParameter("db_user_password"));

			String query = " select count(*) from key_person where designation_id=38 ";
			psmt = connection.prepareStatement(query);
			rst = psmt.executeQuery();
			while (rst.next()) {				
				result=rst.getString(1);
				obj.put("res", result);
				obj.put("res1", "resss1");
				obj.put("res2", "resss2");
				
			}

		} catch (Exception e) {
			System.out.println("Exception in liveDashdata -"+e);			
		}
		System.err.println("len obj -"+obj.length());
		return obj;
	}
	// end

}
