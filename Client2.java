import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Client2 extends JFrame {

	private JPanel contentPane;

	private Client2 frame;
	private JTextField textField, textField2;
	private JTextField name_id, Password_id;
	private JTextField Email_id;
	private JTextField UserName_id;
	static DataOutputStream outputToServer;
	static DataInputStream inputFromServer;
	private JTable UsersTable,UsersTable2;
	private JTextField location_TextField;
	private JTextField trusted_TextField;
	static Connection connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client2 frame = new Client2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			InetAddress ip = InetAddress.getByName("localhost");

			// Establish the connection with Server on port 5056
			Socket socket = new Socket(ip, 5056);
			inputFromServer = new DataInputStream(socket.getInputStream());
			outputToServer = new DataOutputStream(socket.getOutputStream());

//			Scanner scan = new Scanner(System.in);
			try {
				String URL = "jdbc:mysql://localhost:3306/covidfreedb";
	           String Username = "root";
	            String Password = "";

				connection = DriverManager.getConnection(URL, Username, Password);

				System.out.println("Client: Connected With the database successfully.");

			} catch (SQLException e) {
				System.out.println("Client: Error while connecting to the database...");
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */

	public static void infoBox(String infoMessage, String titleBar) {
	JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

	public Client2() {
// ----------------------------------FRONT PAGE--------------------------------------------------------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel Title1 = new JLabel("Covid Free Campus DB");
		Title1.setBounds(111, 27, 234, 50);
		Title1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(Title1);

		JButton Signin_btn = new JButton("Sign in");
		Signin_btn.setBounds(163, 111, 117, 29);
		contentPane.add(Signin_btn);

		JButton register_btn = new JButton("Register");
		register_btn.setBounds(163, 164, 117, 29);
		contentPane.add(register_btn);

		Signin_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
//---------------------------------------------------END OF FRONT PAGE --------------------------------------------------------------------
// -----------------------------------------------SIGN IN FORM AFTER FIRST SIGN IN BTN  -------------------------------------------------------------------------------------
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        setBounds(100, 100, 450, 300);
		        contentPane = new JPanel();
		        contentPane.setBackground(new Color(119, 136, 153));
		        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		        setContentPane(contentPane);
		        contentPane.setLayout(null);

		        JLabel lblNewLabel = new JLabel("Sign in Here!\r\n");
		        lblNewLabel.setForeground(new Color(0, 0, 0));
		        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
		        lblNewLabel.setBounds(40, 11, 147, 36);
		        contentPane.add(lblNewLabel);

		        JLabel lblNewLabel_1 = new JLabel("UserName");
		        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		        lblNewLabel_1.setBounds(97, 60, 73, 16);
		        contentPane.add(lblNewLabel_1);

		        JLabel lblNewLabel_2 = new JLabel("Password");
		        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		        lblNewLabel_2.setBounds(97, 119, 77, 21);
		        contentPane.add(lblNewLabel_2);

		        JButton signin_btn = new JButton("Sign in");
		        signin_btn.setForeground(Color.BLACK);
//		        signin_btn.setBackground(Color.BLACK);
		        signin_btn.setBounds(296, 204, 117, 29);
		        contentPane.add(signin_btn);

		        textField = new JTextField();
		        textField.setBounds(97, 87, 210, 29);
		        contentPane.add(textField);
		        textField.setColumns(10);

		        textField2 = new JTextField();
		        textField2.setBounds(97, 147, 210, 29);
		        contentPane.add(textField2);
		        textField2.setColumns(10);

		        JLabel lblNewLabel_3 = new JLabel("");
		        lblNewLabel_3.setIcon(new ImageIcon("/Users/rabihyamani/eclipse-workspace/NetworksProject/CovidApp/Covid4uni/img/login.png"));
		        lblNewLabel_3.setBounds(10, 87, 95, 29);
		        contentPane.add(lblNewLabel_3);
		        
		        JLabel lblNewLabel_3_1 = new JLabel("");
		        lblNewLabel_3_1.setIcon(new ImageIcon("/Users/rabihyamani/eclipse-workspace/NetworksProject/CovidApp/Covid4uni/img/password.png"));
		        lblNewLabel_3_1.setBounds(10, 140, 55, 36);
		        contentPane.add(lblNewLabel_3_1);
		        
		        JLabel lblNewLabel_4 = new JLabel("New label");
		        lblNewLabel_4.setIcon(new ImageIcon("/Users/rabihyamani/eclipse-workspace/NetworksProject/CovidApp/Covid4uni/img/Understanding-Coronavirus-Disease.jpg"));
		        lblNewLabel_4.setBounds(0, 0, 434, 261);
		        contentPane.add(lblNewLabel_4);
		        
//--------------------------------------------------- end of sign in form -------------------------------------------------------------------------------------

				signin_btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
//------------------------------------------- CLICKING SIGN IN SO U GET VER TO LOG IN ---------------------------------------------------
						String recieved;

						String sql = "SELECT * FROM covidfreedb.userinfo WHERE UserName='" + textField.getText()
								+ "' AND Password='" + textField2.getText() + "'";
						try {
							outputToServer.writeUTF(sql);
						} catch (IOException e1) {
							e1.printStackTrace();
							System.out.println("Failed to send signin (SELECT) statement to server");
						}
						try {
							recieved = inputFromServer.readUTF();
							System.out.println("Message from server: " + recieved);
							System.out.println("Sign in Success");

							if (recieved.equals("Correct")) {
//---------------------------------------------------CREATE USER PAGE AFTER CONFRM LOG IN ---------------------------------------------------
								contentPane.setVisible(false);
								setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								setBounds(0, 0, 650, 400);
								contentPane = new JPanel();
								contentPane.setBackground(new Color(255, 255, 255));
								contentPane.setForeground(Color.WHITE);
								contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
								setContentPane(contentPane);
								contentPane.setLayout(null);

								JLabel lblNewLabel = new JLabel("Welcome!");
								lblNewLabel.setFont(new Font("DialogInput", Font.BOLD, 20));
								lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
								lblNewLabel.setBounds(222, 11, 130, 29);
								contentPane.add(lblNewLabel);
								
//								String statement = "";
//								String sql = "UPDATE `covidfreedb`.`userinfo` SET `healthStatus` = '%s' WHERE (`UserName` = '%s');";
//								statement = String.format(sql, comboBox.getSelectedItem(), textField.getText());
								JLabel lblNewLabel_6 = new JLabel(textField.getText());
								lblNewLabel_6.setBounds(364, 20, 79, 16);
								contentPane.add(lblNewLabel_6);

								JLabel lblNewLabel_1 = new JLabel("UserName");
								lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
								lblNewLabel_1.setBounds(0, 181, 79, 23);
								contentPane.add(lblNewLabel_1);

								JLabel lblNewLabel_2 = new JLabel("Status:");
								lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
								lblNewLabel_2.setBounds(6, 56, 61, 16);
								contentPane.add(lblNewLabel_2);

								JLabel ActiveCases_Label = new JLabel("Active Cases: ");
								ActiveCases_Label.setForeground(new Color(0, 0, 0));
								ActiveCases_Label.setHorizontalAlignment(SwingConstants.CENTER);
								ActiveCases_Label.setBounds(74, 306, 161, 21);
								contentPane.add(ActiveCases_Label);

//-------------table start

//								JScrollPane scrollPane = new JScrollPane();
//								scrollPane.setBounds(510, 123, 4, 4);
//								contentPane.add(scrollPane);

								Object columns[] = { "UserName", "Name", "Vaccinated", "Health Status" };
//								UsersTable = new JTable();
//								UsersTable.setBounds(426, 46, 146, 181);
//								contentPane.add(UsersTable);
//								scrollPane.setViewportView(UsersTable);
								String query = "SELECT * FROM covidfreedb.userinfo;";

								Statement stm = null;
								try {
									stm = connection.createStatement();
								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								String data[][] = new String[8][4];
								
								try {
									ResultSet res = stm.executeQuery(query);

									
									int i = 0;
									while (res.next()) {
										int j = 0;
										String UserName = res.getString("UserName");
										String FullName = res.getString("name");
										String vacStatus = res.getString("status");
										//String healthStatus = res.getString("healthStatus");
										data[i][j++] = UserName;
										data[i][j++] = FullName;
										data[i][j++] = vacStatus;
										//data[i][j++] = healthStatus;

										i++;
									}
								} catch (Exception ee) {
									ee.printStackTrace();
								}

//								DefaultTableModel model = new DefaultTableModel(data, columns);
//								JTable table = new JTable(model);
//								table.setShowGrid(true);
//								table.setShowVerticalLines(true);
//								JScrollPane pane = new JScrollPane(table);
//								JFrame f = new JFrame("Populate JTable from Database");
//								JPanel panel = new JPanel();
//								panel.add(pane);
//								f.add(panel);
//								f.setSize(500, 250);
////								f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//								f.setVisible(true);

//---------------------------------------------------TABLE END	---------------------------------------------------							

//--------------------------INSERT LOCATION/ TRUSTED TO BE UPDATED ONCE ITS PRESSED DOWN IN THE BTN ---------------------------------------------------
								String locations[] = { "Upper", "Lower", "BB", "Sage", "Nicol" };
								JComboBox comboBox_2 = new JComboBox(locations);
								comboBox_2.setBounds(84, 228, 130, 23);
								contentPane.add(comboBox_2);

								trusted_TextField = new JTextField();
								trusted_TextField.setColumns(10);
								trusted_TextField.setBounds(74, 179, 130, 26);
								contentPane.add(trusted_TextField);



//	---------------------------------------------------COMBO BOX ---------------------------------------------------
								String statuses[] = { "Safe", "Contagious", "Risky" };
								JComboBox comboBox = new JComboBox(statuses);
								comboBox.setBounds(74, 53, 130, 23);
								contentPane.add(comboBox);

//	-----------------------------------------------------COMBO BOX---------------------------------------------------

//---------------------------------STATUS UPDATE--------------------------------------------------------------------

								JButton updateStatus_btn = new JButton("Update Status");
								updateStatus_btn.setBounds(222, 50, 117, 29);
								contentPane.add(updateStatus_btn);

								updateStatus_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										System.out.println(comboBox.getSelectedItem());

										String statement = "";
										String sql = "UPDATE `covidfreedb`.`userinfo` SET `status` = '%s' WHERE (`UserName` = '%s');";
										statement = String.format(sql, comboBox.getSelectedItem(), textField.getText());
										
//								send to server
										try {
											infoBox("Status Updated", "Success");
											outputToServer.writeUTF(statement);
										} catch (IOException e1) {
											e1.printStackTrace();
											System.out.println("Failed to send update status statement to server");
										}
//										try {
//											System.out.println(inputFromServer.readUTF());
//										} catch (IOException e1) {
//											e1.printStackTrace();
//										}
									}

								});
//---------------------------------- CHECK CLOSE FRIENDS AND THEIR INFO AND OPENING A TABLE  ----------------------------------
								JButton checkUsers_btn = new JButton("Check Friends");
								checkUsers_btn.setBounds(222, 125, 117, 29);
								contentPane.add(checkUsers_btn);

								checkUsers_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										JScrollPane scrollPane = new JScrollPane();
										scrollPane.setBounds(47, 107, 468, 98);
										contentPane.add(scrollPane);

										Object columns2[] = { "CloseFriend", "name","email", "vacStatus","location", "status", "pcr"};
//										UsersTable2 = new JTable();
//										UsersTable2.setBounds(47, 107, 468, 98);
//										contentPane.add(UsersTable);
//										scrollPane.setViewportView(UsersTable2);
										String statement = "";
//										String query = "SELECT * FROM covidfreedb.CloseFriends;";
										String query= "SELECT CloseFriend.CloseFriend, userinfo.name, userinfo.email,userinfo.vacStatus, userinfo.location, userinfo.status, userinfo.pcr "
												+ " FROM covidfreedb.CloseFriend inner join userinfo on userinfo.UserName = CloseFriend.CloseFriend"
												+ " WHERE CloseFriend.UserName = '%s';";
//										String query = "SELECT * FROM covidfreedb.CloseFriends WHERE UserName = \"%s\";";
//										userinfo.status, userinfo.pcr
										statement = String.format(query, textField.getText());
										
										System.out.println(statement);
										Statement stm = null;
										String data2[][] = new String[8][7];
										try {
											stm = connection.createStatement();
										 
											ResultSet res = stm.executeQuery(statement);
											System.out.println(res);
											
											int i = 0;
											while (res.next()) {
//												System.out.println(res);
												
												int j = 0;
												String CloseFriend = res.getString("CloseFriend");
												String FullName = res.getString("name");
												String Email = res.getString("Email");
												String location = res.getString("location");
												String healthStatus = res.getString("status");
												String PcrResult = res.getString("pcr");
												data2[i][j++] = CloseFriend;
												data2[i][j++] = FullName;
												data2[i][j++] = Email;
												data2[i][j++] = location;
												data2[i][j++] = healthStatus;
												data2[i][j++] = PcrResult;
												i++;
											}
											
										}catch (SQLException e2) {
											System.out.println("error");
											// TODO Auto-generated catch block
											e2.printStackTrace();
										} 
										

										DefaultTableModel model = new DefaultTableModel(data2, columns2);
										JTable table = new JTable(model);
										table.setShowGrid(true);
										table.setShowVerticalLines(true);
										JScrollPane pane = new JScrollPane(table);
										JFrame f = new JFrame("Close Friends");
										JPanel panel = new JPanel();
										panel.add(pane);
										f.add(panel);
										f.setSize(500, 250);
										f.setVisible(true);
								}
									});
//---------------------------------- END OF CHECK CLOSE FRIENDS ---------------------------------------------------
//---------------------------------- ADDING TRUSTED FRIENDS TO DB --------------------------------------------------------------------								
								JButton addTrusted_btn = new JButton("Add Trusted");
								addTrusted_btn.setBounds(222, 178, 117, 29);
								contentPane.add(addTrusted_btn);

								addTrusted_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										String statement = "";
//										INSERT INTO `covidfreedb`.`TrustedUsers` (`trustedUser`, `UserName`) VALUES ('new', '123');
										String sql = "INSERT INTO `covidfreedb`.`CloseFriend` (`CloseFriend`, `UserName`) VALUES ('%s', '%s');";
										statement = String.format(sql, trusted_TextField.getText(),
												textField.getText());

//								send to server
										try {
											infoBox("User added", "Success");
											outputToServer.writeUTF(statement);
										} catch (IOException e1) {
											e1.printStackTrace();
											System.out.println("Failed to send share location statement to server");
										}
										try {
											System.out.println(inputFromServer.readUTF());
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									}

								});
//---------------------------------------END TRUSTED BTN ---------------------------------------------------
//---------------------------------------------------LOG OUT ---------------------------------------------------								
								JButton logout_btn = new JButton("Logout");
								logout_btn.setBackground(new Color(255, 255, 255));
								logout_btn.setBounds(485, 302, 117, 29);
								contentPane.add(logout_btn);

								logout_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										int response = JOptionPane.showConfirmDialog(frame, "Are you sure?!", "Confirm",
												JOptionPane.YES_NO_OPTION);
										if (response == JOptionPane.YES_OPTION) {
											System.exit(0);
										}
									}
								});
								
//----------------------------------END OF LOG OUT --------------------------------------------------------------------								
//----------------------------------UPDATING YOUR LOCATION TO DB ---------------------------------------------------								
								JButton shareLocation_btn = new JButton("Share Location");
								shareLocation_btn.setBounds(222, 227, 117, 29);
								contentPane.add(shareLocation_btn);

								shareLocation_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										String statement = "";
										String sql = "UPDATE `covidfreedb`.`userinfo` SET `location` = '%s' WHERE (`UserName` = '%s')";
										statement = String.format(sql, comboBox_2.getSelectedItem(),
												textField.getText());
										if(comboBox_2.getSelectedItem() == "Sage") {
											String statement5 = "";
											String sql1 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'Sage' WHERE (`UserName` = '%s');";
											statement5 = String.format(sql1,  textField.getText());
											
										}
										if(comboBox_2.getSelectedItem() == "Upper")
										{ 
											String statement6 = "";
											String sql2 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'Upper' WHERE (`UserName` = '%s');";
											statement6 = String.format(sql2,  textField.getText());
										}
										if(comboBox_2.getSelectedItem() == "BB"){
											String statement2 = "";
											String sql3 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'BB' WHERE (`UserName` = '%s');";
										statement2 = String.format(sql3, textField.getText());
										}
										if(comboBox_2.getSelectedItem() == "Lower"){
											String statement7 = "";
											String sql3 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'Lower' WHERE (`UserName` = '%s');";
										statement7 = String.format(sql3, textField.getText());
										}
										if(comboBox_2.getSelectedItem() == "Nicol"){
											String statement2 = "";
											String sql3 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'Nicol' WHERE (`UserName` = '%s');";
										statement2 = String.format(sql3, textField.getText());
										}

//								send to server
										try {
											infoBox("Location Shared", "Success");
											outputToServer.writeUTF(statement);
										} catch (IOException e1) {
											e1.printStackTrace();
											System.out.println("Failed to send register statement to server");
										}
										try {

											System.out.println(inputFromServer.readUTF());
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									}

								});
//----------------------------END OF LOCATION --------------------------------------------------------------------
//--------------------------------UPDATE PCR RESULT ---------------------------------------------------	
								
								JButton getActive_btn = new JButton("Get Active Cases");
								getActive_btn.setBounds(74, 266, 170, 29);
								contentPane.add(getActive_btn);
//								buttons end

								JLabel lblNewLabel_3 = new JLabel("PCR Result");
								lblNewLabel_3.setBounds(16, 91, 83, 16);
								contentPane.add(lblNewLabel_3);
//--------------------------------UPDATE PCR RESULT ---------------------------------------------------	
								
								String pcrtest[] = { "Positive", "Negative"};
								JComboBox comboBox1 = new JComboBox(pcrtest);
								comboBox1.setBounds(74, 88, 130, 22);
								contentPane.add(comboBox1);
								
								JButton uploadPCR_btn = new JButton("Update PCR");
								uploadPCR_btn.setBounds(222, 85, 117, 29);
								contentPane.add(uploadPCR_btn);
								
								uploadPCR_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										System.out.println(comboBox1.getSelectedItem());
										String statement = "";
										String statement1 = "";

										//Comeback here
									if(comboBox1.getSelectedItem() == "Positive") {
										
										String sql7 = "UPDATE `covidfreedb`.`userinfo` SET `pcr` = 'Positive' WHERE (`UserName` = '%s');";
										statement = String.format(sql7,  textField.getText());
										System.out.println("first if : ");
									
										String sql9 = "UPDATE `covidfreedb`.`userinfo` SET `status` = 'Contagious' WHERE (`UserName` = '%s');";
										statement1 = String.format(sql9, textField.getText());
									}if(comboBox1.getSelectedItem() == "Negative")
									{ 
										
										String sql4 = "UPDATE `covidfreedb`.`userinfo` SET `pcr` = 'Negative' WHERE (`UserName` = '%s');";
										statement = String.format(sql4,  textField.getText());
										System.out.println("second if");
										
									String sql20 = "UPDATE `covidfreedb`.`userinfo` SET `status` = 'Safe' WHERE (`UserName` = '%s');";
									statement1 = String.format(sql20, textField.getText());
									}

										
//								send pcr result to server
										try {
											infoBox("Pcr Test Updated", "Success");
											outputToServer.writeUTF(statement);
											outputToServer.writeUTF(statement1);
										} catch (IOException e1) {
											e1.printStackTrace();
											System.out.println("Failed to send update pcr result statement to server");
										}

										

										
									}

								});

								
								JLabel lblNewLabel_4 = new JLabel("Check On My Friends :)");
								lblNewLabel_4.setBounds(10, 119, 130, 40);
								contentPane.add(lblNewLabel_4);
								
								JLabel lblNewLabel_5 = new JLabel("");
								lblNewLabel_5.setIcon(new ImageIcon("/Users/rabihyamani/eclipse-workspace/NetworksProject/CovidApp/Covid4uni/img/background-userpage.jpg"));
								lblNewLabel_5.setBounds(0, 0, 634, 361);
								contentPane.add(lblNewLabel_5);
								
								
		
							} else {
// login failed
//								setTitle("Login failed");
								infoBox("Login Failed", "Failed");
								System.out.println("Login failed");
								JLabel lblNewLabel = new JLabel("Login failed");
								lblNewLabel.setBounds(183, 31, 61, 16);
								contentPane.add(lblNewLabel);

							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				});
			}
		});
// ---------------------------------- FOR NEW USERS TO REGISTER ---------------------------------------------------
		register_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setTitle("Register");
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(100, 100, 450, 300);
				contentPane = new JPanel();
				contentPane.setBackground(new Color(102, 0, 51));
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				contentPane.setLayout(null);
				setResizable(false);
//				labels start
				JLabel lblNewLabel = new JLabel("Register Now");
				lblNewLabel.setForeground(new Color(255, 255, 255));
				lblNewLabel.setBounds(183, 19, 94, 22);
				contentPane.add(lblNewLabel);

				JLabel lblNewLabel_1 = new JLabel("Full Name");
				lblNewLabel_1.setForeground(new Color(255, 255, 255));
				lblNewLabel_1.setBounds(33, 73, 76, 16);
				contentPane.add(lblNewLabel_1);

				JLabel lblNewLabel_2 = new JLabel("UserName");
				lblNewLabel_2.setForeground(new Color(255, 255, 255));
				lblNewLabel_2.setBounds(33, 129, 76, 16);
				contentPane.add(lblNewLabel_2);

				JLabel lblNewLabel_3 = new JLabel("Email");
				lblNewLabel_3.setForeground(new Color(255, 255, 255));
				lblNewLabel_3.setBounds(33, 101, 43, 16);
				contentPane.add(lblNewLabel_3);

				JLabel lblNewLabel_4 = new JLabel("Vaccinated");
				lblNewLabel_4.setForeground(new Color(255, 255, 255));
				lblNewLabel_4.setBounds(33, 182, 43, 16);
				contentPane.add(lblNewLabel_4);

				JLabel lblNewLabel_5 = new JLabel("Password");
				lblNewLabel_5.setForeground(new Color(255, 255, 255));
				lblNewLabel_5.setBounds(33, 154, 61, 16);
				contentPane.add(lblNewLabel_5);

				JLabel lblNewLabel_6 = new JLabel("Vacination Certificate");
				lblNewLabel_6.setForeground(new Color(255, 255, 255));
				lblNewLabel_6.setBounds(33, 210, 144, 25);
				contentPane.add(lblNewLabel_6);
				
//				text fields start
				name_id = new JTextField();
				
				name_id.setBounds(121, 68, 130, 26);
				contentPane.add(name_id);
				name_id.setColumns(10);

				Password_id = new JTextField();
				
				Password_id.setBounds(121, 149, 130, 26);
				contentPane.add(Password_id);
				Password_id.setColumns(10);

				Email_id = new JTextField();
				
				Email_id.setBounds(121, 96, 130, 26);
				contentPane.add(Email_id);
				Email_id.setColumns(10);

				UserName_id = new JTextField();
			
				UserName_id.setBounds(121, 124, 130, 26);
				contentPane.add(UserName_id);
				UserName_id.setColumns(10);
//				radio btn start
				JRadioButton status_id = new JRadioButton("Vaccinated");
				status_id.setForeground(new Color(255, 255, 255));
				status_id.setBackground(new Color(0, 0, 0));
				status_id.setBounds(121, 178, 130, 20);
				contentPane.add(status_id);
//				radio btn end
				JLabel lblNewLabel_7 = new JLabel("Photo");
			
				lblNewLabel_7.setBounds(263, 73, 43, 16);
				contentPane.add(lblNewLabel_7);

//				buttons start

				// button to upload Vaccination certificate
				JButton UploadImgBTN = new JButton("Upload");
			
				UploadImgBTN.setBackground(new Color(0, 0, 0));
				UploadImgBTN.setBounds(178, 206, 117, 29);
				contentPane.add(UploadImgBTN);

//				button to upload personal photo
				JButton UploadImgBTN_personal = new JButton("Upload");
				
				UploadImgBTN_personal.setBackground(new Color(0, 0, 0));
				UploadImgBTN_personal.setBounds(307, 67, 117, 29);
				contentPane.add(UploadImgBTN_personal);

				JButton RegisterBTN = new JButton("Register");
	
				RegisterBTN.setBackground(new Color(0, 0, 0));
				RegisterBTN.setBounds(178, 237, 117, 29);
				contentPane.add(RegisterBTN);

//---------------------------------- AFTER PRESSING THE REGISTER FORM TO ADD NEW USER ---------------------------------------------------
				RegisterBTN.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String statement = "";
						String sql = "INSERT INTO userinfo(name, image, email, username, password, certificate, status) VALUES('%s','%s','%s','%s','%s','%s','%s')";
					if (status_id.isSelected()) {
							if (name_id.getText() != null && Email_id.getText() != null && UserName_id.getText() != null
									&& Password_id.getText() != null){
								statement = String.format(sql, name_id.getText(),null, Email_id.getText(),
										UserName_id.getText(), Password_id.getText(), null, "Yes");
							} } 
							
						else {
								statement = String.format(sql, name_id.getText(), null,  Email_id.getText(),
										UserName_id.getText(), Password_id.getText(), null,  "No");
						}
//					send to server
							try {
								outputToServer.writeUTF(statement);
							} catch (IOException e1) {
								e1.printStackTrace();
								System.out.println("Failed to send register statement to server");
							}
							

						}
					});

			}
		});
	}

}
