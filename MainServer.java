import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainServer {
	
	static Connection connection;
	public static void main(String[] args) throws IOException {

		//Opening Server Socket
		ServerSocket serverSocket = new ServerSocket(6745);
		
		while (true) {
			Socket clientSocket = null;

			//Try-Catch for connecting to database
			try {
				String URL = "jdbc:mysql://localhost:3306/covidfreedb";
	            String Username = "root";
	            String Password = "";

				connection = DriverManager.getConnection(URL, Username, Password);

				System.out.println("Connected Successfully");

			} catch (SQLException e) {
				System.out.println("Error while connecting");
				e.printStackTrace();
			}

			//try catch to open new socket [Accept new client]
			try {

				clientSocket = serverSocket.accept();

				//Printing socket information for client
				System.out.println("New Client : " + clientSocket);

				DataInputStream clientInput = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());

				System.out.println("Assigning new thread for this client");

				System.out.println("------------------------------------------");

				Thread thread = new ClientHandler(connection, clientSocket, clientInput, clientOutput);
				thread.start();

			} catch (Exception e) {
				clientSocket.close();
				e.printStackTrace();
			}
		}
	}
}

class ClientHandler extends Thread {
	final Socket clientSocket;
	final DataInputStream clientInput;
	final DataOutputStream clientOutput;
	final Connection connection;
	
	public ClientHandler(Connection connection, Socket clientSocket, DataInputStream inputFromClient, DataOutputStream outputToClient) {
		this.connection = connection;
		this.clientSocket = clientSocket;
		this.clientInput = inputFromClient;
		this.clientOutput = outputToClient;
	}

	
	
	public void run() {
		
		String rec;
		String turn;
		while (true) {
			try {

				while (true) {

					rec = clientInput.readUTF();
					Statement statement = connection.createStatement();

					int resultSet1 = 0;
					ResultSet rset = null;
					String query = rec.substring(0, 6);

					if (query.equalsIgnoreCase("INSERT")) {

						resultSet1 = statement.executeUpdate(rec);
						clientOutput.writeUTF(resultSet1 + "");

					} else if (query.equalsIgnoreCase("SELECT")) {
						rset = statement.executeQuery(rec);
						if (rset.next()) {
							clientOutput.writeUTF("Correct");
						} else {
							clientOutput.writeUTF("Incorrect");
						}

					} else if (query.equalsIgnoreCase("UPDATE")) {
						resultSet1 = statement.executeUpdate(rec);
						clientOutput.writeUTF(resultSet1 + "");
					} else {
						System.out.println("Query Malfunction");
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}