package View;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Help.DbConnection;
import Help.Helper;
import Objects.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import Help.*;
public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txF_UserFin;
	private JPasswordField UserPasField;
	private JTextField txF_DoctorFin;
	private JPasswordField DocPasField;
	private DbConnection con = new DbConnection();
	private int find = 0;
	private Patient user;
	private JTextField txF_bossFin;
	private JPasswordField PasField_Boss;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame;
					frame = new LoginGUI();
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
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 570);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_Logo = new JLabel(new ImageIcon(getClass().getResource("Hospital (2).png")));
		lbl_Logo.setBounds(224, 10, 132, 85);
		contentPane.add(lbl_Logo);
		
		JTabbedPane UserLoginGUI = new JTabbedPane(JTabbedPane.TOP);
		UserLoginGUI.setBounds(10, 168, 566, 355);
		contentPane.add(UserLoginGUI);
		
		JPanel Panel_User = new JPanel();
		UserLoginGUI.addTab("User login", null, Panel_User, null);
		Panel_User.setLayout(null);
		
		JLabel lbl_UserFinNumber = new JLabel("FIN Number: ");
		lbl_UserFinNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_UserFinNumber.setForeground(Color.BLACK);
		lbl_UserFinNumber.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_UserFinNumber.setBounds(10, 63, 220, 53);
		Panel_User.add(lbl_UserFinNumber);
		
		JLabel lbl_UserPassword = new JLabel("Password:");
		lbl_UserPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_UserPassword.setForeground(Color.BLACK);
		lbl_UserPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_UserPassword.setBounds(32, 161, 146, 53);
		Panel_User.add(lbl_UserPassword);
		
		txF_UserFin = new JTextField();
		txF_UserFin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txF_UserFin.setBounds(244, 67, 307, 53);
		Panel_User.add(txF_UserFin);
		txF_UserFin.setColumns(10);
		
		UserPasField = new JPasswordField();
		UserPasField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		UserPasField.setBounds(244, 165, 307, 53);
		Panel_User.add(UserPasField);
		
		JButton btn_SignIn = new JButton("Sign In");
		btn_SignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				UserSignInGUI userSign = new UserSignInGUI();
				userSign.setVisible(true);
				dispose();
			}
		});
		btn_SignIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_SignIn.setBounds(10, 240, 263, 50);
		Panel_User.add(btn_SignIn);
		
		JButton btn_UserLogin = new JButton("Log In");
		btn_UserLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txF_UserFin.getText().length() == 0 || UserPasField.getText().length() == 0)
				{
					Helper.showMsg("fillUp");
				}else {			 
					try {
						Connection cn = con.connDB();
						Statement st = cn.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM userdata");
						while(rs.next())
						{			
							find = 0;
							if(txF_UserFin.getText().equals(rs.getString("FIN")) && UserPasField.getText().equals(rs.getString("Password"))&&rs.getString("Type").equals("Patient"))
							{
								//System.out.println(rs.getString("Name"));
								find ++;
								user = new Patient(rs.getInt("ID"), rs.getString("Name") ,rs.getString("Password"), rs.getString("FIN"), rs.getString("Type"));						
								UserMainGUI use = new UserMainGUI(user);
								use.setVisible(true);
								dispose();
								break;
							}		
						}
						if(find == 0)
						{
							Helper.showMsg("WrongInput");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		btn_UserLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_UserLogin.setBounds(284, 240, 263, 50);
		Panel_User.add(btn_UserLogin);
		
		JPanel Panel_DoctorLogin = new JPanel();
		UserLoginGUI.addTab("Doctor Login", null, Panel_DoctorLogin, null);
		Panel_DoctorLogin.setLayout(null);
		
		JLabel lbl_doctorFinNumber = new JLabel("FIN Number: ");
		lbl_doctorFinNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_doctorFinNumber.setForeground(Color.BLACK);
		lbl_doctorFinNumber.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_doctorFinNumber.setBounds(10, 50, 220, 53);
		Panel_DoctorLogin.add(lbl_doctorFinNumber);
		
		txF_DoctorFin = new JTextField();
		txF_DoctorFin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txF_DoctorFin.setColumns(10);
		txF_DoctorFin.setBounds(244, 54, 307, 53);
		Panel_DoctorLogin.add(txF_DoctorFin);
		
		JLabel lbl_DoctorPassword = new JLabel("Password:");
		lbl_DoctorPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_DoctorPassword.setForeground(Color.BLACK);
		lbl_DoctorPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_DoctorPassword.setBounds(32, 148, 146, 53);
		Panel_DoctorLogin.add(lbl_DoctorPassword);
		
		DocPasField = new JPasswordField();
		DocPasField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		DocPasField.setBounds(244, 152, 307, 53);
		Panel_DoctorLogin.add(DocPasField);
		
		JButton btn_DoctorLogin = new JButton("Log In");
		btn_DoctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lbl_doctorFinNumber.getText().length()==0 || lbl_DoctorPassword.getText().length()==0 )
				{
					Helper.showMsg("fillUp");
				}else {		
					Statement st;
					try {
						Connection cn = con.connDB();
						st = cn.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM userdata");
						find = 0;
						while(rs.next())
						{		
								if(txF_DoctorFin.getText().equals(rs.getString("FIN")) && DocPasField.getText().equals(rs.getString("Password"))
										&& rs.getString("Type").equals("Doctor"))
								{
									find ++;
									Doctor doctor = new Doctor(rs.getInt("ID"), rs.getString("Name") ,rs.getString("Password"), rs.getString("FIN"), rs.getString("Type"));						
									DoctorMainGUI use = new DoctorMainGUI(doctor);
									use.setVisible(true);
									dispose();
									break;
								}		
							}
							if(find == 0)
							{
								Helper.showMsg("WrongInput");
							}
						}catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				
			}
		});
		btn_DoctorLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_DoctorLogin.setBounds(32, 248, 515, 50);
		Panel_DoctorLogin.add(btn_DoctorLogin);
		
		JPanel panel = new JPanel();
		UserLoginGUI.addTab("Boss login", null, panel, null);
		panel.setLayout(null);
		
		JLabel lbl_bossFin = new JLabel("FIN Number: ");
		lbl_bossFin.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_bossFin.setForeground(Color.BLACK);
		lbl_bossFin.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_bossFin.setBounds(10, 33, 220, 53);
		panel.add(lbl_bossFin);
		
		txF_bossFin = new JTextField();
		txF_bossFin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txF_bossFin.setColumns(10);
		txF_bossFin.setBounds(244, 37, 307, 53);
		panel.add(txF_bossFin);
		
		JLabel lbl_BossPassword = new JLabel("Password:");
		lbl_BossPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_BossPassword.setForeground(Color.BLACK);
		lbl_BossPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_BossPassword.setBounds(32, 131, 146, 53);
		panel.add(lbl_BossPassword);
		
		PasField_Boss = new JPasswordField();
		PasField_Boss.setFont(new Font("Tahoma", Font.PLAIN, 15));
		PasField_Boss.setBounds(244, 135, 307, 53);
		panel.add(PasField_Boss);
		
		JButton btn_DoctorLogin_1 = new JButton("Log In");
		btn_DoctorLogin_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txF_bossFin.getText().length() == 0 || PasField_Boss.getText().length() == 0)
				{
					Helper.showMsg("fillUp");
				}else {			 
					try {
						Connection cn = con.connDB();
						Statement st = cn.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM bossdata");
						find = 0;
						while(rs.next())
						{			
							
							if(txF_bossFin.getText().equals(rs.getString("FIN")) && PasField_Boss.getText().equals(rs.getString("Password")))
							{
								System.out.println(rs.getString("Name"));
								find ++;
								Boss boss = new Boss(rs.getInt("ID"), rs.getString("Name") ,rs.getString("Password"), rs.getString("FIN"), rs.getString("Type"));						
								BossGUI bossg = new BossGUI(boss);
								bossg.setVisible(true);
								dispose();
								break;
							}		
						}
						if(find == 0)
						{
							Helper.showMsg("WrongInput");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			}
				
			
		});
		btn_DoctorLogin_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_DoctorLogin_1.setBounds(32, 231, 515, 50);
		panel.add(btn_DoctorLogin_1);
		
		JLabel lbl_Welcome = new JLabel("Welcome to Hospital");
		lbl_Welcome.setBounds(187, 105, 220, 53);
		lbl_Welcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Welcome.setForeground(Color.BLACK);
		lbl_Welcome.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lbl_Welcome);
	}
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
