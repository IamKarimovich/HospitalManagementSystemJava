package Help;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.time.format.DateTimeFormatter;  

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Objects.Patient;

public class Helper {

	public int ap_id;
	public static void showMsg(String str)
	{
		String msg;
		
		switch (str) {
		case "fillUp":
			msg = "Fill all of empty place!";
			break;
		case "WrongInput":
			msg = "Wrong FIN or password";	
			break;
		case "ExistFIN":
			msg = "This FIN already exist!";
			break;
		case "SuccesfullySign":
			msg = "Successfully addition!";
			break;
		case "Success":
			msg = "Successfully Process";
			break;
		default:
			msg = str;
			break;
		}
		JOptionPane.showMessageDialog(null, msg,"Message",JOptionPane.INFORMATION_MESSAGE);	
	}
	
	public static int askQuestion(String str)
	{
		String msg;
		switch (str) {
		case "sureBack":
			msg = "Are you sure to back?";
			break;
		case "sureAddAppointment":
			msg = "Are you sure to add appointment?";
			break;
		default:
			msg = str;
			break;
		}
		
		int answer = JOptionPane.showConfirmDialog(null, msg, null,JOptionPane.YES_NO_OPTION);
		if(answer == JOptionPane.NO_OPTION)
		{
			return 0;
		}else {
			return 1;
		}
	}
	
	public static void FindDoctor(Statement st,ActionEvent e,DbConnection con)
	{
		
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
			while(rs.next())
			{
				if(rs.getString("Type").equals(e.getActionCommand()))
				{
					System.out.println(rs.getString("Name"));
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public static void FillListDoctorName(Statement st,ActionEvent e,DbConnection con)
	{
		
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
			while(rs.next())
			{
				if(rs.getString("Type").equals(e.getActionCommand()))
				{
					System.out.println(rs.getString("Name"));
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	public static void displayDoctorsToComboBox(JComboBox combo,DbConnection con,String doctype)
	{
		
		Statement st;
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
			while(rs.next())
			{
				if(rs.getString("Type").equals(doctype))
				{
					combo.addItem(rs.getString("Name"));			
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
	
	public static void displayDoctorsToComboBoxNameAndType(JComboBox combo,DbConnection con,String type)
	{
		
		Statement st;
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
			if(type.equals("typeOfDoctors"))
			{
				while(rs.next())
				{
						combo.addItem(rs.getString("Type"));			
				}
			}
			else if(type.equals("nameOfDoctors"))
			{
				while(rs.next())
				{
						combo.addItem(rs.getString("Name"));			
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
	
	public static void displayDoctorTypesToComboBoxWithName(JComboBox combo,DbConnection con,String doc_name)
	{
		Statement st;
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
			
			while(rs.next())
			{
				if(rs.getString("Name").equals(doc_name))
				{
					combo.addItem(rs.getString("Type"));
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
	
	public static int displayDoctorAppointmentsTimeToComboBox(JComboBox combo,DbConnection con,String doctype,String docName,String date)
	{
		Statement st;
		int doc_id = 0;
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
			while(rs.next())
			{
				if(rs.getString("Type").equals(doctype) && rs.getString("Name").equals(docName))
				{
					doc_id = rs.getInt("ID");
					ResultSet newRs = st.executeQuery("SELECT * FROM wdate");	
					while(newRs.next())
					{
						if(doc_id == newRs.getInt("Doctor_id") && newRs.getString("wdate").equals(date) && newRs.getString("Status").equals("a"))
						{
							combo.addItem(newRs.getString("whour"));
							
						}
					}
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return doc_id;
	}
	
	public static boolean changeToPassiveAppointment(DbConnection con,String doctype,String docName,String date,String time,Patient pat)
	{
		if(time == null)
		{
			showMsg("fillUp");
			return false;
		}else {
			Statement st;
			try {
				Connection cn = con.connDB();
				st = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
				while(rs.next())
				{
					if(rs.getString("Type").equals(doctype) && rs.getString("Name").equals(docName))
					{
						int doc_id = rs.getInt("ID");
						Statement nSt = cn.createStatement();
						ResultSet newRs = nSt.executeQuery("SELECT * FROM wdate");	
						while(newRs.next())
						{

							if(doc_id == newRs.getInt("Doctor_id") && newRs.getString("wdate").equals(date)&& newRs.getString("whour").equals(time) && newRs.getString("Status").equals("a"))
							{
								int ID = newRs.getInt("ID");
								//System.out.println(ID);
								//System.out.println(newRs.getString("whour"));
								PreparedStatement pSt = cn.prepareStatement("UPDATE wdate SET Status=? WHERE ID="+ID);
								pSt.setString(1, "p");
								PreparedStatement PstInsert = cn.prepareStatement("INSERT INTO appointments (doc_id,doc_name,pat_id,pat_name,a_date,a_time) VALUES (?,?, ?, ?, ?, ?)");
								PstInsert.setInt(1, doc_id);
								PstInsert.setString(2, rs.getString("Name"));
								PstInsert.setInt(3, pat.getID());
								PstInsert.setString(4, pat.getName());
								PstInsert.setString(5, date);
								PstInsert.setString(6, time);
								PstInsert.executeUpdate();
								pSt.executeUpdate();
								break;
							}
						}
						break;
					}
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return true;
		}
	}
	
	public static String[][] findPassiveAppointmentsForUser(DbConnection con,Patient pat,String[][] arr)
	{
		Statement st;
		int j = 0;
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM appointments");
			ResultSet doctorRs = st.executeQuery("SELECT * FROM doctordata");
			while(rs.next())
			{
				int k = 0;
				if(rs.getInt("pat_id") == pat.getID() && rs.getString("status").equals("p"))
				{
					for(int i = 0;i<arr[0].length;i++)
					{
						//String[] header = {"Doctor ID","Doctor name","Patient ID","Patient Name","Appointment date","Time"};
						arr[j][k] = Integer.toString(rs.getInt("doc_id"));
						while(doctorRs.next())
						{
							if(doctorRs.getInt("ID")==rs.getInt("doc_id"))
							{
								arr[j][k+1] = doctorRs.getString("Name");
								break;
							}
						}
						arr[j][k+2] = Integer.toString(pat.getID());
						arr[j][k+3] = pat.getName();
						arr[j][k+4] = rs.getString("a_date");
						arr[j][k+5] = rs.getString("a_time");		
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
		
	}
	
	public static int countRowFromSql(DbConnection con,String tableName)
	{
		Statement st;
		int size = 0;
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM "+tableName);
			
		        while(rs.next()){
		            size++;
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size;
	    
	   
	}
	
	public static boolean checkAppForUser(DbConnection con,int doc_id,int pat_id,String date)
	{
		Statement st;
		int check = 0;
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM appointments");
			
			while(rs.next())
			{
				System.out.println(doc_id);
				System.out.println(rs.getString("doc_id"));
				if(rs.getInt("doc_id") == doc_id && rs.getInt("pat_id") == pat_id &&  rs.getString("a_date").equals(date) && rs.getString("status").equals("p"))
				{
					showMsg("You can't get an appointment in the same day!");
					check=1;
					break;
				}
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block		
			e.printStackTrace();
			
		}
		//return false;
		System.out.println(check);
		if(check != 0)
		{
			return false;
		}else
		{
			return true;
		}	
	}
	
	public static void fillUpArrayOfAppointmentsForDocId(DbConnection con,String[][] data,int doc_id)
	{
		Statement st;
		try {
			int row = 0;
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM appointments");
			while(rs.next())
			{
				int k = 0;
				if(rs.getInt("doc_id") == doc_id )
				{
					for(int i = 0;i<data[0].length;i++)
					{
						data[row][k] = Integer.toString(rs.getInt("ID"));
						data[row][k+1] = Integer.toString(rs.getInt("pat_id"));		
						data[row][k+2] = rs.getString("pat_name");	
						data[row][k+3] = rs.getString("a_date");
						data[row][k+4] = rs.getString("a_time");
						data[row][k+5] = rs.getString("status");
					}		
					row ++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
