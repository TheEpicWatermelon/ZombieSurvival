import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Client.java
 * runs the client class, sends and recieves messages from the server and 
 * @author Izzy
 *
 */
public class Client {
	//client constructor variables
	private Socket userSocket;
	private static BufferedReader input;
	private PrintWriter output;
	private String username;
	
	//constants and runtime variables
	private static boolean running;
	String clientIn;
	static ArrayList<String> userList = new ArrayList<>();
	//Commands
	//private static final String COMMAND_NEW = "svn"; // client sends to server to connect to server
	private static final int COMMAND_LENGTH = 3; // sets the lengths of the commands
	private static final String COMMAND_UPDATE_USER = "sud"; //sends the list of users

	Client(Socket userSocket, BufferedReader input, PrintWriter output, String username){
		//setting the client constructor variables
		this.userSocket = userSocket;
		this.input = input;
		this.output = output;
		this.username = username;
		//output.println(COMMAND_NEW+ username);
		//output.flush();
		//create gui
		Gui game = new Gui();
		running = true;
		Thread serverInput = new Thread(new ServerCommands()); // Start listening for commands and input from server
		serverInput.start();
		Thread guiInput =  new Thread(new guiInputThread());//Starts listening for updates from gui to send to server
		guiInput.start();
		
		
	}
	
	class ServerCommands implements Runnable{

		
		String message;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (running) {
				try {
					if (input.ready()) {
						message = input.readLine();
						System.out.println("read in");
						if (message.startsWith(COMMAND_UPDATE_USER)) {
							message = message.substring(COMMAND_LENGTH);
							System.out.println("hi");
							String[] tempUsers = message.split(",");
							System.out.println("done");
							for (int i = 0; i< tempUsers.length; i++) {
								userList.add(tempUsers[i]);
							}
							
						
					}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	private static class guiInputThread implements Runnable{
		@Override
		public void run() {
			
			// TODO Auto-generated method stub
			
		}
		
	}
}
