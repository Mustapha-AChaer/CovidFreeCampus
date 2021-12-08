import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MainClient extends JFrame {

	private JPanel contentPane;

	private MainClient frame;
	private JTextField textField, textField2;
	private JTextField nameField, passField, emailField, UsernameField, trustedUserField;
	static DataOutputStream serverOutput;
	static DataInputStream serverInput;
	private JTable UsersTable,UsersTable2;
	private JTextField location_TextField;
	static Connection cnx;
	public String certifPdf, str, imgloc;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainClient frame = new MainClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			InetAddress ip = InetAddress.getByName("localhost");

			Socket socket = new Socket(ip, 6745);
			serverInput = new DataInputStream(socket.getInputStream());
			serverOutput = new DataOutputStream(socket.getOutputStream());

			try {
				String URL = "jdbc:mysql://localhost:3306/covidfreedb";
	            String Username = "root";
	            String Password = "";

				cnx = DriverManager.getConnection(URL, Username, Password);

				System.out.println("Client: Connected With the database successfully.");

			} catch (SQLException e) {
				System.out.println("Client: Error while connecting to the database...");
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


//What is under is related to the Frame
	public static void infoBox(String infoMessage, String titleBar) {
	JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}




	public MainClient() {

/*
Front Page
*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(231, 76, 60));

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
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        setBounds(100, 100, 450, 300);
		        contentPane = new JPanel();
				contentPane.setBackground(new Color(231, 76, 60));
		        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		        setContentPane(contentPane);
		        contentPane.setLayout(null);

		        JLabel mainLabel = new JLabel("Sign in Here!\n");
		        mainLabel.setForeground(new Color(255, 255, 255));
		        mainLabel.setFont(new Font(Font.SERIF, Font.BOLD, 23));
		        mainLabel.setBounds(40, 11, 200, 36);
		        contentPane.add(mainLabel);

		        JLabel nameLabel = new JLabel("Username");
		        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		        nameLabel.setBounds(97, 60, 73, 16);
		        contentPane.add(nameLabel);

		        JLabel passLabel = new JLabel("Password");
		        passLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		        passLabel.setBounds(97, 119, 77, 21);
		        contentPane.add(passLabel);

		        JButton signin_btn = new JButton("Sign in");
		        signin_btn.setForeground(Color.BLACK);
		        signin_btn.setBounds(296, 204, 117, 29);
		        contentPane.add(signin_btn);

		        textField = new JTextField();
		        textField.setBounds(97, 87, 210, 29);
		        contentPane.add(textField);
		        textField.setColumns(10);

		        JPasswordField passfield = new JPasswordField();
		        passfield.setBounds(97, 147, 210, 29);
		        contentPane.add(passfield);
		        passfield.setColumns(10);

		        JLabel emailLabel = new JLabel("");
		        emailLabel.setBounds(10, 87, 95, 29);
		        contentPane.add(emailLabel);

		        JLabel emailLabel_1 = new JLabel("");
		        emailLabel_1.setBounds(10, 140, 55, 36);
		        contentPane.add(emailLabel_1);



				signin_btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String recieved;

						String sqlQuery = "SELECT * FROM covidfreedb.userinfo WHERE UserName='" + textField.getText()
								+ "' AND Password='" + passfield.getText() + "'";
						try {
							serverOutput.writeUTF(sqlQuery);
						} catch (IOException e1) {
							e1.printStackTrace();
							System.out.println("Failed to send signin (SELECT) statement to server");
						}
						try {
							recieved = serverInput.readUTF();
							System.out.println("Message from server: " + recieved);
							System.out.println("Sign in Success");

							if (recieved.equals("Correct")) {
								contentPane.setVisible(false);
								setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								setBounds(0, 0, 800, 800);
								contentPane = new JPanel();
								contentPane.setBackground(new Color(231, 76, 60));
								contentPane.setForeground(Color.WHITE);
								setContentPane(contentPane);
								contentPane.setLayout(null);



								JLabel usernameHomeText = new JLabel(textField.getText());
								usernameHomeText.setBounds(300, 20, 200, 30);
								usernameHomeText.setFont(new Font(Font.SERIF, Font.BOLD, 30));
								contentPane.add(usernameHomeText);

								System.out.println(textField.getText());


								JLabel statusLabelHome = new JLabel("Status");
								statusLabelHome.setHorizontalAlignment(SwingConstants.CENTER);
								statusLabelHome.setBounds(5, 56,150 , 16);
								statusLabelHome.setFont(new Font(Font.SERIF, Font.BOLD, 17));
								contentPane.add(statusLabelHome);

							;

								Object columns[] = { "UserName", "Name", "Vaccinated", "Health Status" };
								String query = "SELECT * FROM covidfreedb.userinfo;";

								Statement stm = null;
								try {
									stm = cnx.createStatement();
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
										data[i][j++] = UserName;
										data[i][j++] = FullName;
										data[i][j++] = vacStatus;
										i++;
									}
								} catch (Exception ee) {
									ee.printStackTrace();
								}

								JLabel nameLabel = new JLabel("Enter Trusted User");
								nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
								nameLabel.setBounds(25, 181, 150, 23);
								nameLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
								contentPane.add(nameLabel);

								String locations[] = { "Upper", "Lower", "BB", "Sage", "Nicol" };
								JComboBox locationCombo = new JComboBox(locations);
								locationCombo.setBounds(350, 228, 130, 23);
								contentPane.add(locationCombo);

								trustedUserField = new JTextField();
								trustedUserField.setColumns(10);
								trustedUserField.setBounds(350, 179, 130, 26);
								contentPane.add(trustedUserField);



								String statuses[] = { "Safe", "Contagious", "Risky" };
								JComboBox statusCombo = new JComboBox(statuses);
								statusCombo.setBounds(350, 53, 130, 23);
								contentPane.add(statusCombo);



								JButton updateStatus_btn = new JButton("Update Status");
								updateStatus_btn.setBounds(550, 50, 117, 29);
								contentPane.add(updateStatus_btn);

								updateStatus_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										System.out.println(statusCombo.getSelectedItem());

										String statement = "";
										String sqlQuery = "UPDATE `covidfreedb`.`userinfo` SET `status` = '%s' WHERE (`UserName` = '%s');";
										statement = String.format(sqlQuery, statusCombo.getSelectedItem(), textField.getText());

										try {
											infoBox("Status Updated", "Success");
											serverOutput.writeUTF(statement);
										} catch (IOException e1) {
											e1.printStackTrace();
											System.out.println("Failed to send update status statement to server");
										}
//										try {
//											System.out.println(serverInput.readUTF());
//										} catch (IOException e1) {
//											e1.printStackTrace();
//										}
									}

								});
								JButton checkUsers_btn = new JButton("Check Friends");
								checkUsers_btn.setBounds(550, 125, 117, 29);
								contentPane.add(checkUsers_btn);

								checkUsers_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										JScrollPane scrollPane = new JScrollPane();
										scrollPane.setBounds(47, 107, 468, 98);
										contentPane.add(scrollPane);

										Object columns2[] = { "CloseFriend", "name","email", "vacStatus","location", "status", "pcr"};

										String statement = "";

										String query= "SELECT CloseFriend.CloseFriend, userinfo.name, userinfo.email,userinfo.vacStatus, userinfo.location, userinfo.status, userinfo.pcr "
												+ " FROM covidfreedb.CloseFriend inner join userinfo on userinfo.UserName = CloseFriend.CloseFriend"
												+ " WHERE CloseFriend.UserName = '%s';";

										statement = String.format(query, textField.getText());

										System.out.println(statement);
										Statement stm = null;
										String data2[][] = new String[8][7];
										try {
											stm = cnx.createStatement();

											ResultSet res = stm.executeQuery(statement);
											System.out.println(res);

											int i = 0;
											while (res.next()) {

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

								JButton addTrusted_btn = new JButton("Add Trusted");
								addTrusted_btn.setBounds(550, 178, 117, 29);
								contentPane.add(addTrusted_btn);

								addTrusted_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {

										String statement = "";
//										INSERT INTO `covidfreedb`.`TrustedUsers` (`trustedUser`, `UserName`) VALUES ('new', '123');
										String sqlQuery = "INSERT INTO `covidfreedb`.`CloseFriend` (`CloseFriend`, `UserName`) VALUES ('%s', '%s');";
										statement = String.format(sqlQuery, trustedUserField.getText(),
												textField.getText());

//								send to server
										try {
											infoBox("User added", "Success");
											serverOutput.writeUTF(statement);
										} catch (IOException e1) {
											e1.printStackTrace();
											System.out.println("Failed to send share location statement to server");
										}
										try {
											System.out.println(serverInput.readUTF());
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									}

								});

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


								JButton shareLocation_btn = new JButton("Share Location");
								shareLocation_btn.setBounds(550, 227, 117, 29);
								contentPane.add(shareLocation_btn);

								shareLocation_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										String statement = "";
										String sqlQuery = "UPDATE `covidfreedb`.`userinfo` SET `location` = '%s' WHERE (`UserName` = '%s')";
										statement = String.format(sqlQuery, locationCombo.getSelectedItem(),
												textField.getText());
										if(locationCombo.getSelectedItem() == "Sage") {
											String statement5 = "";
											String sqlQuery1 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'Sage' WHERE (`UserName` = '%s');";
											statement5 = String.format(sqlQuery1,  textField.getText());

										}
										if(locationCombo.getSelectedItem() == "Upper")
										{
											String statement6 = "";
											String sqlQuery2 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'Upper' WHERE (`UserName` = '%s');";
											statement6 = String.format(sqlQuery2,  textField.getText());
										}
										if(locationCombo.getSelectedItem() == "BB"){
											String statement2 = "";
											String sqlQuery3 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'BB' WHERE (`UserName` = '%s');";
										statement2 = String.format(sqlQuery3, textField.getText());
										}
										if(locationCombo.getSelectedItem() == "Lower"){
											String statement7 = "";
											String sqlQuery3 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'Lower' WHERE (`UserName` = '%s');";
										statement7 = String.format(sqlQuery3, textField.getText());
										}
										if(locationCombo.getSelectedItem() == "Nicol"){
											String statement2 = "";
											String sqlQuery3 = "UPDATE `covidfreedb`.`userinfo` SET `location` = 'Nicol' WHERE (`UserName` = '%s');";
										statement2 = String.format(sqlQuery3, textField.getText());
										}

										try {
											infoBox("Location Shared", "Success");
											serverOutput.writeUTF(statement);
										} catch (IOException e1) {
											e1.printStackTrace();
											System.out.println("Failed to send register statement to server");
										}
										try {

											System.out.println(serverInput.readUTF());
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									}

								});




								JLabel emailLabel = new JLabel("PCR");
								emailLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
								emailLabel.setBounds(25, 91, 150, 16);
								contentPane.add(emailLabel);

								String pcrtest[] = { "Positive", "Negative"};
								JComboBox pcrCombo = new JComboBox(pcrtest);
								pcrCombo.setBounds(350, 88, 130, 22);
								contentPane.add(pcrCombo);

								JButton uploadPCR_btn = new JButton("Update PCR");
								uploadPCR_btn.setBounds(550, 85, 117, 29);
								contentPane.add(uploadPCR_btn);

								uploadPCR_btn.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										System.out.println(pcrCombo.getSelectedItem());
										String statement = "";
										String statement1 = "";

										//Comeback here
									if(pcrCombo.getSelectedItem() == "Positive") {

										String sqlQuery7 = "UPDATE `covidfreedb`.`userinfo` SET `pcr` = 'Positive' WHERE (`UserName` = '%s');";
										statement = String.format(sqlQuery7,  textField.getText());
										System.out.println("first if : ");

										String sqlQuery9 = "UPDATE `covidfreedb`.`userinfo` SET `status` = 'Contagious' WHERE (`UserName` = '%s');";
										statement1 = String.format(sqlQuery9, textField.getText());
									}if(pcrCombo.getSelectedItem() == "Negative")
									{

										String sqlQuery4 = "UPDATE `covidfreedb`.`userinfo` SET `pcr` = 'Negative' WHERE (`UserName` = '%s');";
										statement = String.format(sqlQuery4,  textField.getText());
										System.out.println("second if");

									String sqlQuery20 = "UPDATE `covidfreedb`.`userinfo` SET `status` = 'Safe' WHERE (`UserName` = '%s');";
									statement1 = String.format(sqlQuery20, textField.getText());
									}


//								send pcr result to server
										try {
											infoBox("Pcr Test Updated", "Success");
											serverOutput.writeUTF(statement);
											serverOutput.writeUTF(statement1);
										} catch (IOException e1) {
											e1.printStackTrace();
											System.out.println("Failed to send update pcr result statement to server");
										}




									}

								});


								JLabel vacStatLabel = new JLabel("Friends Status");
								vacStatLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
								vacStatLabel.setBounds(25, 119, 150, 40);
								contentPane.add(vacStatLabel);

								JLabel locationLabel = new JLabel("Location");
								locationLabel.setBounds(25, 60, 150, 361);
								locationLabel.setFont(new Font(Font.SERIF, Font.BOLD, 17));
								contentPane.add(locationLabel);



							} else {
								infoBox("Login Failed", "Failed");
								System.out.println("Login failed");
								JLabel mainLabel = new JLabel("Login failed");
								mainLabel.setBounds(183, 31, 61, 16);
								contentPane.add(mainLabel);

							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				});
			}
		});


		register_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setTitle("No Account? Register");
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setBounds(100, 100, 1000, 800);
				contentPane = new JPanel();
				contentPane.setBackground(new Color(231, 76, 60));
				setContentPane(contentPane);
				contentPane.setLayout(null);
				setResizable(false);

				JLabel mainLabel = new JLabel("Enter Registering Information");
				mainLabel.setFont(new Font(Font.SERIF, Font.BOLD, 27));
				mainLabel.setHorizontalAlignment(JLabel.CENTER);
				mainLabel.setForeground(new Color(255, 255, 255));
				mainLabel.setBounds(183, 19, 500, 40);
				contentPane.add(mainLabel);

				JLabel nameLabel = new JLabel("Full Name");
				nameLabel.setForeground(new Color(255, 255, 255));
				nameLabel.setBounds(33, 73, 76, 16);
				contentPane.add(nameLabel);

				JLabel usernameLabel = new JLabel("UserName");
				usernameLabel.setForeground(new Color(255, 255, 255));
				usernameLabel.setBounds(25, 129, 150, 16);
				contentPane.add(usernameLabel);

				JLabel emailLabel = new JLabel("Email");
				emailLabel.setForeground(new Color(255, 255, 255));
				emailLabel.setBounds(33, 101, 43, 16);
				contentPane.add(emailLabel);

				JLabel vacStatLabel = new JLabel("Vaccinated?");
				vacStatLabel.setForeground(new Color(255, 255, 255));
				vacStatLabel.setBounds(33, 182, 200, 16);
				contentPane.add(vacStatLabel);

				JLabel passLabel = new JLabel("Password");
				passLabel.setForeground(new Color(255, 255, 255));
				passLabel.setBounds(33, 154, 200, 16);
				contentPane.add(passLabel);

				JLabel vacCertifLabel = new JLabel("Vacination Certificate");
				vacCertifLabel.setForeground(new Color(255, 255, 255));
				vacCertifLabel.setBounds(33, 210, 200, 25);
				contentPane.add(vacCertifLabel);

				nameField = new JTextField();

				nameField.setBounds(121, 68, 130, 26);
				contentPane.add(nameField);
				nameField.setColumns(10);

				passField = new JPasswordField();

				passField.setBounds(121, 149, 130, 26);
				contentPane.add(passField);
				passField.setColumns(10);

				emailField = new JTextField();

				emailField.setBounds(121, 96, 130, 26);
				contentPane.add(emailField);
				emailField.setColumns(10);

				UsernameField = new JTextField();

				UsernameField.setBounds(121, 124, 130, 26);
				contentPane.add(UsernameField);
				UsernameField.setColumns(10);
				JRadioButton statusField = new JRadioButton("Vaccinated");
				statusField.setForeground(Color.WHITE);
				statusField.setBackground(new Color(0,0,0, 0));
				statusField.setBounds(121, 178, 130, 20);
				contentPane.add(statusField);
				JLabel photoLabel = new JLabel("Photo");

				photoLabel.setBounds(25, 250, 100, 30);
				photoLabel.setForeground(Color.WHITE);
				contentPane.add(photoLabel);


				JButton UploadImgBTN = new JButton("Upload Certificate");

				UploadImgBTN.setBackground(new Color(255, 255, 255));
				UploadImgBTN.setBounds(178, 206, 200, 29);
				contentPane.add(UploadImgBTN);
				UploadImgBTN.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser file = new JFileChooser();
						file.setCurrentDirectory(new File("Desktop"));
						int res = file.showOpenDialog(null);
						if(res == JFileChooser.APPROVE_OPTION){
							File text = new File(file.getSelectedFile().getAbsolutePath());
							certifPdf = file.getSelectedFile().getAbsolutePath();

							if(str.substring(str.lastIndexOf("."), str.length()).compareTo(".pdf") == 0){
								try {
									JOptionPane.showMessageDialog(null, "Added!" + "","", JOptionPane.INFORMATION_MESSAGE);
								}catch(NumberFormatException error) {
									JOptionPane.showMessageDialog(null, "Enter a Valid Page" + "","Error!", JOptionPane.ERROR_MESSAGE);
								}

							}

						}

					}
				});

				JButton imageUploadBtn = new JButton("Upload Image");
				imageUploadBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser file = new JFileChooser();
						file.setCurrentDirectory(new File("Desktop"));
						int res = file.showOpenDialog(null);
						if(res == JFileChooser.APPROVE_OPTION){
							File text = new File(file.getSelectedFile().getAbsolutePath());
							imgloc = file.getSelectedFile().getAbsolutePath();

							if(str.substring(str.lastIndexOf("."), str.length()).compareTo(".pdf") == 0){
								try {
									JOptionPane.showMessageDialog(null, "Added!" + "","", JOptionPane.INFORMATION_MESSAGE);
								}catch(NumberFormatException error) {
									JOptionPane.showMessageDialog(null, "Please Enter a Valid Page" + "","Error!", JOptionPane.ERROR_MESSAGE);
								}

							}

						}
					}
				});
				imageUploadBtn.setBackground(new Color(255, 255, 255));
				imageUploadBtn.setBounds(75, 250, 200, 29);
				contentPane.add(imageUploadBtn);

				JButton registerBTN = new JButton("Register");

				registerBTN.setBackground(new Color(255, 255, 255));
				registerBTN.setBounds(700, 700, 200, 29);
				registerBTN.setForeground(new Color(0,0,0));
				contentPane.add(registerBTN);

				registerBTN.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String statement = "";
						String sqlQuery = "INSERT INTO userinfo(name, image, email, username, password, certificate, vacStatus) VALUES('%s','%s','%s','%s','%s','%s','%s')";
					if (statusField.isSelected()) {
							if (nameField.getText() != null && emailField.getText() != null && UsernameField.getText() != null
									&& passField.getText() != null){
								statement = String.format(sqlQuery, nameField.getText(),imgloc, emailField.getText(),
										UsernameField.getText(), passField.getText(), certifPdf, "Yes");
							} }

						else {
								statement = String.format(sqlQuery, nameField.getText(), null,  emailField.getText(),
										UsernameField.getText(), passField.getText(), null,  "No");
						}
							try {
								serverOutput.writeUTF(statement);
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
