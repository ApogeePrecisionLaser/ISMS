package com.apogee.visitor;

/**
 *
 * @author Vikrant
 * 
 */
public class VisitAttendBean {

	private String active;
	private String revision_no;
	private String coming_time;
	private String visitor_status;
	private String status_code;
	private String person_name;
	private String person_mobile_no;
	private String person_id;
	
	// details
	private String person_id_type;
	private String created_date;
	private String details_id;
	
	// temperature logs
	private Double temperature;
	private int normal;
	private int low;
	private int high;
	private int slight;
	private int total;
	private String image_name;
	
	// for device registration
	private String dev_id;
	private String class_name;
	private String designation;
	
	
	
	

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getDev_id() {
		return dev_id;
	}

	public void setDev_id(String dev_id) {
		this.dev_id = dev_id;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public int getNormal() {
		return normal;
	}

	public void setNormal(int normal) {
		this.normal = normal;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getSlight() {
		return slight;
	}

	public void setSlight(int slight) {
		this.slight = slight;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	private String date;
	private String time;
	private String status;
	private String emp_name;
	private String emp_code;
	
	
	private String Login_office;
	

	public String getLogin_office() {
		return Login_office;
	}

	public void setLogin_office(String login_office) {
		Login_office = login_office;
	}

	public String getEmp_code() {
		return emp_code;
	}

	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPerson_id_type() {
		return person_id_type;
	}

	public void setPerson_id_type(String person_id_type) {
		this.person_id_type = person_id_type;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getDetails_id() {
		return details_id;
	}

	public void setDetails_id(String details_id) {
		this.details_id = details_id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRevision_no() {
		return revision_no;
	}

	public void setRevision_no(String revision_no) {
		this.revision_no = revision_no;
	}

	public String getComing_time() {
		return coming_time;
	}

	public void setComing_time(String coming_time) {
		this.coming_time = coming_time;
	}

	public String getVisitor_status() {
		return visitor_status;
	}

	public void setVisitor_status(String visitor_status) {
		this.visitor_status = visitor_status;
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public String getPerson_mobile_no() {
		return person_mobile_no;
	}

	public void setPerson_mobile_no(String person_mobile_no) {
		this.person_mobile_no = person_mobile_no;
	}

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

}
