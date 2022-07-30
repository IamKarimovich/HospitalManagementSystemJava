package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Help.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class UserSignInGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txF_SignUserName;
	private JTextField txF_SignUserSurname;
	private JTextField txF_SignUserFIN;
	private JPasswordField UserSignPasfield;
	private DbConnection con = new DbConnection();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserSignInGUI frame = new UserSignInGUI();
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
	public UserSignInGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 570);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sign In");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(268, 10, 123, 83);
		contentPane.add(lblNewLabel);
		
		JLabel lbl_signUserName = new JLabel("Name:");
		lbl_signUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_signUserName.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_signUserName.setBounds(171, 103, 88, 68);
		contentPane.add(lbl_signUserName);
		
		JLabel lbl_SignUserSurname = new JLabel("Surname:");
		lbl_SignUserSurname.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_SignUserSurname.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_SignUserSurname.setBounds(145, 181, 114, 68);
		contentPane.add(lbl_SignUserSurname);
		
		JLabel lbl_SignUserFin = new JLabel("FIN:");
		lbl_SignUserFin.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_SignUserFin.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_SignUserFin.setBounds(145, 259, 114, 68);
		contentPane.add(lbl_SignUserFin);
		
		JLabel lbl_SignUserPass = new JLabel("Password:");
		lbl_SignUserPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_SignUserPass.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_SignUserPass.setBounds(111, 337, 148, 68);
		contentPane.add(lbl_SignUserPass);
		
		txF_SignUserName = new JTextField();
		txF_SignUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txF_SignUserName.setBounds(269, 103, 338, 68);
		contentPane.add(txF_SignUserName);
		txF_SignUserName.setColumns(10);
		
		txF_SignUserSurname = new JTextField();
		txF_SignUserSurname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txF_SignUserSurname.setColumns(10);
		txF_SignUserSurname.setBounds(269, 181, 338, 68);
		contentPane.add(txF_SignUserSurname);
		
		txF_SignUserFIN = new JTextField();
		txF_SignUserFIN.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txF_SignUserFIN.setColumns(10);
		txF_SignUserFIN.setBounds(269, 257, 338, 68);
		contentPane.add(txF_SignUserFIN);
		
		UserSignPasfield = new JPasswordField();
		UserSignPasfield.setFont(new Font("Tahoma", Font.PLAIN, 15));
		UserSignPasfield.setBounds(269, 337, 338, 68);
		contentPane.add(UserSignPasfield);
		
		JButton btn_BackToLogin = new JButton("Back");
		btn_BackToLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_BackToLogin.setBounds(31, 432, 323, 50);
		contentPane.add(btn_BackToLogin);
		
		JButton btn_UserSignUp = new JButton("Sign Up");
		btn_UserSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txF_SignUserName.getText().length() == 0 || txF_SignUserSurname.getText().length() == 0 
					|| txF_SignUserFIN.getText().length()==0 || UserSignPasfield.getText().length() == 0)
				{
					Helper.showMsg("fillUp");
				}else {
					Statement st;
					try {
						Connection cn = con.connDB();
						st = cn.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM userdata");
						int error = 0;
						while(rs.next())
						{
							if(rs.getString("FIN").equals(txF_SignUserFIN.getText()))
							{
								Helper.showMsg("ExistFIN");
								error++;
								break;
							}
						}
						if(error == 0)
						{
							try {
								 PreparedStatement Pst = cn.prepareStatement("INSERT INTO userdata (Name,Password,FIN,Type) VALUES (?, ?, ?, ?)");
								 Pst.setString(1,txF_SignUserName.getText()+ " "+ txF_SignUserSurname.getText());
								 Pst.setString(2,UserSignPasfield.getText() );
								 Pst.setString(3,txF_SignUserFIN.getText() );	
								 Pst.setString(4,"Patient");
								 Pst.executeUpdate();
								 Helper.showMsg("SuccesfullySign");
								 LoginGUI lg = new LoginGUI();
								 dispose();
								 
								 lg.setVisible(true);
								 
								}catch (SQLException e2) {
								
									e2.printStackTrace();
								}
						}
						
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				
				
				
				
				
			}
		});
		btn_UserSignUp.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_UserSignUp.setBounds(364, 432, 323, 50);
		contentPane.add(btn_UserSignUp);
	}

}
