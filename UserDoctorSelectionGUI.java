package View;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Help.DbConnection;
import Help.Helper;
import Objects.Patient;
import Objects.User;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import java.awt.Color;
import java.util.*;
import java.util.Date;
public class UserDoctorSelectionGUI extends JFrame {

	private JPanel contentPane;
	private DbConnection con = new DbConnection();
	static Patient pat = new Patient();
	static String doctype;
	private int doc_id;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserDoctorSelectionGUI frame = new UserDoctorSelectionGUI(pat,doctype);
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
	public UserDoctorSelectionGUI(Patient pat,String doctype) {
		setResizable(false);
		setTitle("Hospital");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 10, 534, 202);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Doctors");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(10, 24, 176, 44);
		panel.add(lblNewLabel);
		
		JDateChooser dateChooserUser = new JDateChooser();
		dateChooserUser.setBounds(204, 65, 168, 21);
		panel.add(dateChooserUser);
		
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date currentDate = new Date();
		dateChooserUser.setDate(currentDate);
		
		String strDate = dateFormat.format(dateChooserUser.getDate()); 
		
		JComboBox select_hours = new JComboBox();
		select_hours.setBounds(382, 64, 142, 21);
		panel.add(select_hours);

		JLabel lblDate = new JLabel("Date");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblDate.setBounds(193, 24, 191, 44);
		panel.add(lblDate);
		
		JLabel lblTimes = new JLabel("Hours");
		lblTimes.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimes.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTimes.setBounds(382, 24, 136, 44);
		panel.add(lblTimes);

		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(11, 65, 183, 21);
		panel.add(select_doctor);
		
		
		Statement st;
		
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
			
			Helper.displayDoctorsToComboBox(select_doctor, con, doctype);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JButton btn_addAppointment = new JButton("Add Appointment");
		
		btn_addAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(Helper.askQuestion("sureAddAppointment")==1)
				{
					boolean check = Helper.checkAppForUser(con, doc_id, pat.getID(), (String) dateFormat.format(dateChooserUser.getDate()));
					System.out.println(check);
					System.out.println((String) select_hours.getSelectedItem());
					if(check==true)
					{
						
						boolean check1 = Helper.changeToPassiveAppointment(con, doctype, (String) select_doctor.getSelectedItem(),(String) dateFormat.format(dateChooserUser.getDate()),
								(String) select_hours.getSelectedItem(),pat);
						
						if(check1 == true)
						{
							Helper.showMsg("Success");
							UserDoctorSelectionGUI usG = new UserDoctorSelectionGUI(pat, doctype);
							usG.setVisible(true);
							dispose();
						}
					}
					
				}
			}
		});
		btn_addAppointment.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_addAppointment.setBounds(266, 118, 258, 55);
		panel.add(btn_addAppointment);
		
		select_hours.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				select_hours.removeAllItems();
				doc_id = Helper.displayDoctorAppointmentsTimeToComboBox(select_hours, con, doctype, (String) select_doctor.getSelectedItem(),(String) dateFormat.format(dateChooserUser.getDate()));
	}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Helper.displayDoctorAppointmentsTimeToComboBox(select_hours, con, doctype, (String) select_doctor.getSelectedItem(),(String) dateFormat.format(dateChooserUser.getDate()));
		
		JButton btn_back = new JButton("Back");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.askQuestion("sureBack")==1)
				{
					UserMainGUI us = new UserMainGUI(pat);
					us.setVisible(true);
					dispose();
				}
				
			}
		});
		
		dateChooserUser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if("date".equals(evt.getPropertyName()))
				{
					select_hours.removeAllItems();
				}
				
			}
		});
		
		
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_back.setBounds(11, 118, 258, 55);
		panel.add(btn_back);
	}
}
