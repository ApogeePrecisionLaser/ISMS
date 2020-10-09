/**
 *
 * @author Vikrant
 * 
 */

package com.apogee.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class LoginModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    // HttpSession session = request.getSession();

    public int checkLogin(String user_name, String password) {
        int login_id = 0;
        String designation = "";
        int count = 0;
        String query = " select user_name,password from login where user_name='" + user_name + "' and password='" + password + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                designation = rs.getString("password");
                count++;
                //   session.setAttribute("key_person_id", key_person_id);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return count;

    }
    
    public String getDesignation(String user_name, String password) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select d.designation from designation d, key_person kp, login l "
                + " where l.user_name='" + user_name + "' and l.password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and kp.designation_id=d.designation_id and d.active='Y' ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getDesignation ERROR inside orderMgmtModel - " + e);
        }
        return str;
    }
    
    public String getOffice(String user_name, String password) {
        String str = "";
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select onn.organisation_name from designation d, key_person kp, login l , org_office oo, organisation_name onn " + 
        		" where l.user_name='"+user_name+"' and l.password='"+password+"' " + 
        		" and l.key_person_id=kp.key_person_id and kp.designation_id=d.designation_id and d.active='Y' " + 
        		" and oo.active='Y' and kp.org_office_id=oo.org_office_id and oo.organisation_id=onn.organisation_id ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getString(1);
            }
        } catch (Exception e) {
            System.out.println("getOffice ERROR inside loginModel - " + e);
        }
        return str;
    }
    
    public int getOfficeId(String user_name, String password) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select onn.organisation_id from designation d, key_person kp, login l , org_office oo, organisation_name onn " + 
        		" where l.user_name='"+user_name+"' and l.password='"+password+"' " + 
        		" and l.key_person_id=kp.key_person_id and kp.designation_id=d.designation_id and d.active='Y' " + 
        		" and oo.active='Y' and kp.org_office_id=oo.org_office_id and oo.organisation_id=onn.organisation_id ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getOffice ERROR inside loginModel - " + e);
        }
        return str;
    }

    public int getKeyPersonId(String user_name, String password) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select distinct kp.key_person_id from designation d, key_person kp, login l "
                + " where l.user_name='" + user_name + "' and l.password='" + password + "' "
                + " and l.key_person_id=kp.key_person_id and d.active='Y' ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getDesignation ERROR inside orderMgmtModel - " + e);
        }
        return str;
    }
    
    public int getOrgNameId(String user_name, String password) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        String query = "select oo.organisation_id from org_office oo, key_person kp, login l "
                + " where l.user_name='"+user_name+"' and l.password='"+password+"' "
                + " and l.key_person_id=kp.key_person_id and kp.org_office_id=oo.org_office_id and oo.active='Y'; ";
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                str = rst.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("getDesignation ERROR inside orderMgmtModel - " + e);
        }
        return str;
    }
    
    public List<String> getClasses(int office_id ) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        List<String> list=new ArrayList<String>();
        
        try {
            connection.setAutoCommit(false);            
            String query = " select org_office_id, org_office_name " + 
            		" from org_office " + 
            		" where organisation_id="+office_id+" and parent_org_office_id=(select org_office_id " +
            		" from org_office where org_office_name='SuperClass') ";            
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {
                list.add(rst.getString(2)); 
            }
        } catch (Exception e) {
            System.out.println("getOffice ERROR inside loginModel - " + e);
        }
        return list;
    }
    
    //Map<String, List<String>> map = new HashMap<String, List<String>>();
    
    public Map<String, List<String>> getClassSection(int office_id ) {
        int str = 0;
        PreparedStatement pstmt;
        ResultSet rst;
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();                      
        try {
            connection.setAutoCommit(false);            
            String query = " select org_office_id, org_office_name " + 
            		" from org_office " + 
            		" where organisation_id="+office_id+" and parent_org_office_id=(select org_office_id " +
            		" from org_office where org_office_name='SuperClass') order by org_office_id ";            
            pstmt = connection.prepareStatement(query);
            rst = pstmt.executeQuery();
            while (rst.next()) {            	
            	List<String> list=new ArrayList<String>();  
                //map.put(rst.getString(2), value);
                String qry=" select org_office_id,org_office_name from org_office "
                		+ " where parent_org_office_id="+rst.getInt(1)+" ";
                PreparedStatement psmt2=connection.prepareStatement(qry);
                ResultSet rst2=psmt2.executeQuery();
                while(rst2.next()) {
                	list.add(rst2.getString(2));
                }
                map.put(rst.getString(2), list);
                //System.out.println("map in model -"+map.toString());
                
            }
        } catch (Exception e) {
            System.out.println("getOffice ERROR inside loginModel - " + e);
        }
        return map;
    }
    

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
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
