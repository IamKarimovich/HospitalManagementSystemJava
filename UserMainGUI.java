package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;

import Help.DbConnection;
import Help.Helper;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridLayout;
import Objects.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
public class UserMainGUI extends JFrame {

	private DbConnection con = new DbConnection();
	private JPanel contentPane;
	static Patient user = new Patient();
	private JTable table;
	private int dataRow = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//User user = new User();
					UserMainGUI frame = new UserMainGUI(user);
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
	public UserMainGUI(Patient user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 504);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_Back = new JButton("Back");
		btn_Back.setBackground(Color.LIGHT_GRAY);
		btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.askQuestion("sureBack")==1)
				{
					LoginGUI log = new LoginGUI();
					log.setVisible(true);
					dispose();
				}			
			}
		});
		btn_Back.setBounds(516, 10, 158, 34);
		contentPane.add(btn_Back);
		
		//User user = new User(WIDTH, getTitle(), getWarningString(), getName())
		
		JLabel lblNewLabel_1 = new JLabel("User : " + user.getName());
		lblNewLabel_1.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 10, 326, 34);
		contentPane.add(lblNewLabel_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 125, 664, 332);
		contentPane.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Select problem", null, panel_1, null);
		panel_1.setLayout(new GridLayout(3, 0, 0, 0));
		
		JButton btn_HeartProb = new JButton("Heart");
		btn_HeartProb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println(btn_HeartProb.getText());
				UserDoctorSelectionGUI uds = new UserDoctorSelectionGUI(user,btn_HeartProb.getText());
				uds.setVisible(true);
				dispose();	
			}
		});
		btn_HeartProb.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_1.add(btn_HeartProb);
		
		JButton btn_HeadProb = new JButton("Head");
		btn_HeadProb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDoctorSelectionGUI uds = new UserDoctorSelectionGUI(user,btn_HeadProb.getText());
				uds.setVisible(true);
				dispose();
			}
		});
		btn_HeadProb.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_1.add(btn_HeadProb);
		
		JButton btn_SkinProb = new JButton("Skin");
		btn_SkinProb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDoctorSelectionGUI uds = new UserDoctorSelectionGUI(user,btn_SkinProb.getText());
				uds.setVisible(true);
				dispose();
			}
		});
		btn_SkinProb.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_1.add(btn_SkinProb);
		
		JButton btn_SimpleProb = new JButton("Simple Problems");
		btn_SimpleProb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDoctorSelectionGUI uds = new UserDoctorSelectionGUI(user,btn_SimpleProb.getText());
				uds.setVisible(true);
				dispose();
			}
		});
		btn_SimpleProb.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_1.add(btn_SimpleProb);
		
		JButton btn_NeurologyProb = new JButton("Neurology");
		btn_NeurologyProb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDoctorSelectionGUI uds = new UserDoctorSelectionGUI(user,btn_NeurologyProb.getText());
				uds.setVisible(true);
				dispose();
			}
		});
		btn_NeurologyProb.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_1.add(btn_NeurologyProb);
		
		JButton btn_PhyProb = new JButton("Physiotherapy");
		btn_PhyProb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDoctorSelectionGUI uds = new UserDoctorSelectionGUI(user,btn_PhyProb.getText());
				uds.setVisible(true);
				dispose();
			}
		});
		btn_PhyProb.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel_1.add(btn_PhyProb);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Current appointments", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 639, 285);
		panel.add(scrollPane);
		
			String[] header = {"Appointment ID","Doctor ID","Doctor name","Appointment date","Time"};
			String [][] data = new String[Helper.countRowFromSql(con, "appointments")][5];
			Statement st,st2;
			
			try {
				Connection cn = con.connDB();
				st = cn.createStatement();
				st2 = cn.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM appointments");
				while(rs.next())
				{
					int k = 0;
					if(rs.getInt("pat_id") == user.getID() && rs.getString("status").equals("p"))
					{
						for(int i = 0;i<data[0].length;i++)
						{
							data[dataRow][k] = Integer.toString(rs.getInt("ID"));
							data[dataRow][k+1] = Integer.toString(rs.getInt("doc_id"));
							data[dataRow][k+2] = rs.getString("doc_name");
							data[dataRow][k+3] = rs.getString("a_date");
							data[dataRow][k+4] = rs.getString("a_time");		
						}
						dataRow ++;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JPopupMenu popMenu = new JPopupMenu();
			JMenuItem i1 = new JMenuItem("Delete row");
			popMenu.add(i1);
			
			table = new JTable(data,header);
			
			
			table.setComponentPopupMenu(popMenu);
			scrollPane.setViewportView(table);
			i1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int SelRow = table.getSelectedRow();
						int value = Integer.parseInt((String) table.getValueAt(SelRow, 0));			

							Connection cn = con.connDB();
							PreparedStatement pSt = cn.prepareStatement("UPDATE appointments SET status=? WHERE ID="+value);
							pSt.setString(1, "a");
							pSt.executeUpdate();
							
							Helper.showMsg("Success");
							UserMainGUI usG = new UserMainGUI(user);
							usG.setVisible(true);
							dispose();
							
						} catch (Exception e1) {
							Helper.showMsg("Please select item from table!");
						}
					}
					
				
				
			});
			
			
			
		
		
		
		
		
		
		
		
	}
}
