import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Server2 {
	
	static Connection connection;
	public static void main(String[] args) throws IOException {

		//Opening Server Socket
		ServerSocket serverSocket = new ServerSocket(5056);
		
		while (true) {
			Socket clientSocket = null;

			//Try-Catch for connecting to database
			try {
				String URL = "jdbc:mysql://localhost:3306/covidfreedb";
	            String Username = "root";
	            String Password = "";

				connection = DriverManager.getConnection(URL, Username, Password);

				System.out.println("Connected With the database successfully.");

			} catch (SQLException e) {
				System.out.println("Error while connecting to the database...");
				e.printStackTrace();
			}

			//try catch to open new socket [Accept new client]
			try {

				clientSocket = serverSocket.accept();

				//Printing socket information for client
				System.out.println("A new client is connected : " + clientSocket);

				DataInputStream inputFromClient = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(clientSocket.getOutputStream());

				System.out.println("Assigning new thread for this client");

				System.out.println("------------------------------------------");

				Thread thread = new ClientHandler(connection, clientSocket, inputFromClient, outputToClient);
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
	final DataInputStream inputFromClient;
	final DataOutputStream outputToClient;
	final Connection connection;
	
	public ClientHandler(Connection connection, Socket clientSocket, DataInputStream inputFromClient, DataOutputStream outputToClient) {
		this.connection = connection;
		this.clientSocket = clientSocket;
		this.inputFromClient = inputFromClient;
		this.outputToClient = outputToClient;
	}

	
	
	public void run() {
		
		// Variables
		String received;
		String toreturn;
		while (true) {
			try {
				// Initiate communication with Client

				// Receive the answer from Client
//				received = inputFromClient.readUTF();

				while (true) {

					// Receive the answer from Client
					received = inputFromClient.readUTF();
					Statement statement = connection.createStatement();

					int resultSet1 = 0;
					ResultSet rset = null;
					String query = received.substring(0, 6);

					if (query.equalsIgnoreCase("INSERT")) {

						resultSet1 = statement.executeUpdate(received);
						outputToClient.writeUTF(resultSet1 + "");

					} else if (query.equalsIgnoreCase("SELECT")) {
						rset = statement.executeQuery(received);
						if (rset.next()) {
							outputToClient.writeUTF("Correct");
							System.out.println("Correct password");
						} else {
							outputToClient.writeUTF("Incorrect");
							System.out.println("Incorrect password");
						}

					} else if (query.equalsIgnoreCase("UPDATE")) {
						resultSet1 = statement.executeUpdate(received);
						outputToClient.writeUTF(resultSet1 + "");
					} else {
						System.out.println("Error in Query");
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