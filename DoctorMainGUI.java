package View;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import Objects.*;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Help.DbConnection;
import Help.Helper;


import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;



public class DoctorMainGUI extends JFrame {

	static Doctor doctor = new Doctor();
	private JPanel contentPane;
	private DbConnection con = new DbConnection();
	private int dataRow = 0,dataRowDoctor = 0;
	private JTable table_appointment;
	private JTable table_currentAppointments;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorMainGUI frame = new DoctorMainGUI(doctor);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DoctorMainGUI(Doctor doctor) {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 499);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_DoctorName = new JLabel("Doctor: "+doctor.getName());
		lbl_DoctorName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbl_DoctorName.setBounds(10, 10, 250, 41);
		contentPane.add(lbl_DoctorName);
		
		JButton btn_BackDocMenu = new JButton("Back");
		btn_BackDocMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.askQuestion("sureBack")==1)
				{
					LoginGUI lG = new LoginGUI();
					lG.setVisible(true);
					dispose();
				}
			}
		});
		btn_BackDocMenu.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_BackDocMenu.setBounds(475, 10, 149, 41);
		contentPane.add(btn_BackDocMenu);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 85, 614, 371);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Current appointments", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 589, 324);
		panel.add(scrollPane_1);
		
		
		////////////////////////////////////////
		
		String[] headerDoctor = {"Appointment ID","Patient ID","Patient Name","Appointment date","Time"};
		String [][] dataDoctor = new String[Helper.countRowFromSql(con, "appointments")][5];
		Statement stDoc;
		
		try {
			Connection cn = con.connDB();
			stDoc = cn.createStatement();
			ResultSet rs = stDoc.executeQuery("SELECT * FROM appointments");
			while(rs.next())
			{
				int k = 0;
				if(rs.getInt("doc_id") == doctor.getID() && rs.getString("status").equals("p"))
				{
					for(int i = 0;i<dataDoctor[0].length;i++)
					{
						dataDoctor[dataRowDoctor][k] = Integer.toString(rs.getInt("ID"));
						dataDoctor[dataRowDoctor][k+1] = Integer.toString(rs.getInt("pat_id"));
						dataDoctor[dataRowDoctor][k+2] = rs.getString("pat_name");
						dataDoctor[dataRowDoctor][k+3] = rs.getString("a_date");
						dataDoctor[dataRowDoctor][k+4] = rs.getString("a_time");		
					}
					dataRowDoctor ++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table_currentAppointments = new JTable(dataDoctor,headerDoctor);
		scrollPane_1.setViewportView(table_currentAppointments);
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Choose appointment time", null, panel_1, null);
		
		JDateChooser select_dateDoctor = new JDateChooser();
		select_dateDoctor.setBounds(10, 20, 178, 26);
		Date date = new Date();
		panel_1.setLayout(null);
		select_dateDoctor.setDate(date);
		panel_1.add(select_dateDoctor);
		
		
		
		JComboBox select_timeDoctor = new JComboBox();
		select_timeDoctor.setBounds(198, 20, 85, 26);
		select_timeDoctor.setFont(new Font("Tahoma", Font.BOLD, 15));
		select_timeDoctor.setModel(new DefaultComboBoxModel(new String[] {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "14:00", "14:30", "15:00", "15:30", "16:00"}));
		panel_1.add(select_timeDoctor);
		
		JButton btn_addWorkHour = new JButton("Add");
		btn_addWorkHour.setBounds(293, 20, 127, 26);
		btn_addWorkHour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdformat.format(select_dateDoctor.getDate());
				String time = (String) select_timeDoctor.getSelectedItem();
						try {
							Statement st;
							Connection cn = con.connDB();
							st = cn.createStatement();
							ResultSet rs = st.executeQuery("SELECT * FROM wdate WHERE Doctor_id="+doctor.getID());
							int i = 0;
							while(rs.next())
							{
								if(rs.getString("wdate").equals(date) && rs.getString("whour").equals(time))
								{
									Helper.showMsg("You have added an appointment to this date ");
									i++;
									break;
								}
							}
							if(i==0)
							{			
									st = cn.createStatement();
									PreparedStatement Pst = cn.prepareStatement("INSERT INTO wdate (Doctor_id,Name,whour,wdate) VALUES (?,?,?,?)");
									Pst.setInt(1, doctor.getID());
									Pst.setString(2, doctor.getName());
									Pst.setString(3, time);
									Pst.setString(4, date);
									Pst.executeUpdate();
			
									Helper.showMsg("Success");
									DoctorMainGUI dmG = new DoctorMainGUI(doctor);
									dmG.setVisible(true);
									dispose();
							}
							
							
					
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						 }
					}
			}	
	);
		
		
		
		String[] header = {"Free Appointment ID","Work date","Work time"};
		String [][] data = new String[Helper.countRowFromSql(con, "wdate")][3];
		Statement st;
		
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM wdate");
			while(rs.next())
			{
				int k = 0;
				if(rs.getInt("Doctor_id") == doctor.getID() && rs.getString("status").equals("a"))
				{
					for(int i = 0;i<data[0].length;i++)
					{
						data[dataRow][k] = Integer.toString(rs.getInt("ID"));
						
						data[dataRow][k+1] = rs.getString("wdate");
						
						data[dataRow][k+2] = rs.getString("whour");	
					}
					
					
					dataRow ++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		btn_addWorkHour.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(btn_addWorkHour);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 589, 278);
		panel_1.add(scrollPane);
		
		table_appointment = new JTable(data,header);
		table_appointment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table_appointment);
	
		JPopupMenu popMenu = new JPopupMenu();
		JMenuItem i1 = new JMenuItem("Delete row");
		popMenu.add(i1);
		
		table_appointment.setComponentPopupMenu(popMenu);
		
		
		i1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int SelRow = table_appointment.getSelectedRow();
					int value = Integer.parseInt((String) table_appointment.getValueAt(SelRow, 0));			

						Connection cn = con.connDB();
						PreparedStatement pSt = cn.prepareStatement("DELETE FROM wdate WHERE ID="+value);
						pSt.executeUpdate();
						Helper.showMsg("Success");
						DoctorMainGUI dmG = new DoctorMainGUI(doctor);
						dmG.setVisible(true);
						dispose();
						
					} catch (Exception e1) {
						Helper.showMsg("Please select item from table!");
					}
				
			}
		});
		
	
		
		
		
		}
	}

