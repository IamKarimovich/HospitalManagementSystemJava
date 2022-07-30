package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Help.DbConnection;
import Help.Helper;
import Objects.Boss;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import java.awt.Component;

public class BossGUI extends JFrame {
	private DbConnection con = new DbConnection();
	private JPanel contentPane;
	private JTable table_docList;
	private int dataRowDoctor = 0,doc_id;
	private JTable table,table2;
	static Boss boss;
	private JTextField txF_FirstNameDoc;
	private JTextField txF_SecondNameDoc;
	private JTextField txF_FinDoc;
	private JPasswordField PasFieldDoc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BossGUI frame = new BossGUI(boss);
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
	public BossGUI(Boss boss) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 807, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 783, 424);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 65, 763, 349);
		panel.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Doctors", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 738, 289);
		panel_1.add(scrollPane);
		
		String headerDoctor[] = {"ID","Name","Type"};
		String[][] dataDoctor = new String[Helper.countRowFromSql(con,"doctordata")][3];
		
		Statement st;
		
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
			int k = 0;
				while(rs.next())
				{		
					dataDoctor[dataRowDoctor][k] = Integer.toString(rs.getInt("ID"));
					dataDoctor[dataRowDoctor][k+1] = rs.getString("Name");
					dataDoctor[dataRowDoctor][k+2] = rs.getString("Type");
					dataRowDoctor ++;	
				}
							
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		table_docList = new JTable(dataDoctor,headerDoctor);
		scrollPane.setViewportView(table_docList);
		
		JPopupMenu popMenu = new JPopupMenu();
		JMenuItem i1 = new JMenuItem("Delete row");
		popMenu.add(i1);
		
		table_docList.setComponentPopupMenu(popMenu);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Show appointments", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 57, 738, 265);
		panel_2.add(scrollPane_1);

		JComboBox select_doctorForAppointments = new JComboBox();
		select_doctorForAppointments.setBounds(180, 10, 194, 26);
		panel_2.add(select_doctorForAppointments);
		
		
		
		
		Helper.displayDoctorsToComboBoxNameAndType(select_doctorForAppointments, con, "nameOfDoctors");
		
		JLabel lblNewLabel = new JLabel("Select Doctor Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 10, 168, 26);
		panel_2.add(lblNewLabel);
		
		JLabel lblSelectDoctorType = new JLabel("Select Doctor Type");
		lblSelectDoctorType.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSelectDoctorType.setBounds(384, 10, 161, 26);
		panel_2.add(lblSelectDoctorType);
		
		
		JComboBox select_doctorType = new JComboBox();
		select_doctorType.setBounds(555, 10, 193, 26);
		panel_2.add(select_doctorType);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		tabbedPane.addTab("Doctor Add", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Enter First Name");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(118, 10, 213, 41);
		panel_3.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Enter Second Name");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(118, 58, 213, 41);
		panel_3.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Enter FIN Number");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_2.setBounds(118, 109, 213, 41);
		panel_3.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Enter Password");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_3.setBounds(118, 212, 213, 41);
		panel_3.add(lblNewLabel_1_3);
		
		txF_FirstNameDoc = new JTextField();
		txF_FirstNameDoc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_FirstNameDoc.setBounds(369, 12, 281, 41);
		panel_3.add(txF_FirstNameDoc);
		txF_FirstNameDoc.setColumns(10);
		
		txF_SecondNameDoc = new JTextField();
		txF_SecondNameDoc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_SecondNameDoc.setColumns(10);
		txF_SecondNameDoc.setBounds(369, 60, 281, 41);
		panel_3.add(txF_SecondNameDoc);
		
		txF_FinDoc = new JTextField();
		txF_FinDoc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_FinDoc.setColumns(10);
		txF_FinDoc.setBounds(369, 111, 281, 41);
		panel_3.add(txF_FinDoc);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 20));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Heart", "Head", "Skin", "Simple Problems", "Neurology", "Physiotherapy", ""}));
		comboBox.setBounds(369, 162, 281, 41);
		panel_3.add(comboBox);
		
		PasFieldDoc = new JPasswordField();
		PasFieldDoc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		PasFieldDoc.setBounds(369, 214, 281, 41);
		panel_3.add(PasFieldDoc);
		
		JButton btnNewButton = new JButton("Add Doctor");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txF_FirstNameDoc.getText().length() == 0 || txF_SecondNameDoc.getText().length()==0 || txF_FinDoc.getText().length()==0 
						|| PasFieldDoc.getText().length()==0 || comboBox.getSelectedItem() == null)
				{
					Helper.showMsg("fillUp");
				}else {
					Connection cn = con.connDB();
					try {
						Statement st = cn.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM userdata");
						int find = 0;
						while(rs.next())
						{
							if(rs.getString("FIN").equals(txF_FinDoc.getText()))
							{
								Helper.showMsg("ExistFIN");
								find++;
							}
						}
						if(find == 0)
						{
							PreparedStatement pSt = cn.prepareStatement("INSERT INTO userdata(Name,Type,Password,FIN) VALUES (?,?,?,?)");
							PreparedStatement pStToDoctor = cn.prepareStatement("INSERT INTO doctordata(Name,Type) VALUES (?,?)");
							pSt.setString(1, txF_FirstNameDoc.getText()+" "+txF_SecondNameDoc.getText());
							pSt.setString(2, "Doctor");
							pSt.setString(3, PasFieldDoc.getText());
							pSt.setString(4, txF_FinDoc.getText());
							pSt.executeUpdate();
							pStToDoctor.setString(1, txF_FirstNameDoc.getText()+" "+txF_SecondNameDoc.getText());
							pStToDoctor.setString(2,(String) comboBox.getSelectedItem());
							pStToDoctor.executeUpdate();
							Helper.showMsg("SuccesfullySign");
							BossGUI bsG = new BossGUI(boss);
							bsG.setVisible(true);
							dispose();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(242, 265, 326, 47);
		panel_3.add(btnNewButton);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Enter Doctor Field");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_2_1.setBounds(118, 159, 213, 41);
		panel_3.add(lblNewLabel_1_2_1);
		
		
		
		select_doctorType.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				select_doctorType.removeAllItems();
				Helper.displayDoctorTypesToComboBoxWithName(select_doctorType, con, (String)select_doctorForAppointments.getSelectedItem());
				Statement st;
				try {
					Connection cn = con.connDB();
					st = cn.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM doctordata");
					
				        while(rs.next())
				        {
				        	if(rs.getString("Name").equals((String)select_doctorForAppointments.getSelectedItem()) && 
				        			rs.getString("Type").equals((String) select_doctorType.getSelectedItem()))
				        			{
				        				doc_id = rs.getInt("ID");
				        			}
				        }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String[] header = {"Free Appointment ID","Patient ID","Patient Name","Appointment date","Appointment time","Status"};
				String [][] data = new String[Helper.countRowFromSql(con, "wdate")][6];		
				Helper.fillUpArrayOfAppointmentsForDocId(con, data, doc_id);
				table = new JTable(data,header);
				scrollPane_1.setViewportView(table);	
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
			
			select_doctorForAppointments.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				select_doctorType.removeAllItems();
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
			
		
		//Helper.displayDoctorTypesToComboBoxWithName(select_doctorType, con, (String)select_doctorForAppointments.getSelectedItem());

		i1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int SelRow = table_docList.getSelectedRow();
					int value = Integer.parseInt((String) table_docList.getValueAt(SelRow, 0));			

						Connection cn = con.connDB();
						PreparedStatement pSt = cn.prepareStatement("DELETE FROM doctordata WHERE ID="+value);
						pSt.executeUpdate();
						Helper.showMsg("Success");
						BossGUI dmG = new BossGUI(boss);
						dmG.setVisible(true);
						dispose();
						
					} catch (Exception e1) {
						Helper.showMsg("Please select item from table!");
					}
				
			}
		});
		
		JLabel lbl_BossName = new JLabel("Boss:"+boss.getName());
		lbl_BossName.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_BossName.setBounds(10, 10, 367, 45);
		panel.add(lbl_BossName);
		
		JButton btn_back = new JButton("Back");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.askQuestion("sureBack")==1)
				{
					LoginGUI lG = new LoginGUI();
					dispose();
					lG.setVisible(true);
				}
				
			}
		});
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_back.setBounds(618, 10, 155, 45);
		panel.add(btn_back);
	}
}
