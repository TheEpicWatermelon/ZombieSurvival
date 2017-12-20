import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.awt.Color;

/**
 * ClientLogin.java
 * Logs in the user and send the users information to client
 * @author Izzy
 * @date 12/19/2017
 * @teacher Mr. Mangat
 * Version 1.0
 */
public class ClientLogin {
	private JTextField ipAddressTF;
	private JTextField portNumberTF;
	private JTextField usernameTF;
	private JButton connectButton;
	JFrame loginWindow;
	
	// debugging private static final String COMMAND_NEW = "svn";
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    new ClientLogin().create();
  }
  
  /**
   * create()
   * creates all of the gui and the connection to the server
   */
  private void create() {
    // TODO Auto-generated method stub
	    // Add event handler to connect button
    LoginGUI();
    
  }
  
  
  
  /**
   * @wbp.parser.entryPoint
   */
  private void LoginGUI(){
    JPanel mainPanel;
    
    
    loginWindow = new JFrame("Login");
    loginWindow.setSize(400,300);
    loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainPanel = new JPanel();
    mainPanel.setBackground(new Color(189, 183, 107));
    
    loginWindow.getContentPane().add(mainPanel, BorderLayout.WEST);
    
    JLabel ipAddressLabel = new JLabel("IP Address:");
    ipAddressLabel.setFont(new Font("VL Gothic", Font.BOLD, 15));
    
    ipAddressTF = new JTextField();
    ipAddressTF.setBackground(new Color(85, 107, 47));
    ipAddressTF.setFont(new Font("VL PGothic", Font.BOLD, 14));
    ipAddressTF.setText("127.0.0.1");
    ipAddressTF.setColumns(10);
    
    JLabel portNumberLabel = new JLabel("Port Number:");
    portNumberLabel.setFont(new Font("VL Gothic", Font.BOLD, 15));
    
    portNumberTF = new JTextField();
    portNumberTF.setBackground(new Color(85, 107, 47));
    portNumberTF.setFont(new Font("VL PGothic", Font.BOLD, 14));
    portNumberTF.setText("5000");
    portNumberTF.setColumns(10);
    
    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setFont(new Font("VL Gothic", Font.BOLD, 15));
    
    usernameTF = new JTextField();
    usernameTF.setBackground(new Color(85, 107, 47));
    usernameTF.setText("Your Username");
    usernameTF.setFont(new Font("VL PGothic", Font.BOLD, 14));
    usernameTF.setColumns(10);
    
    connectButton = new JButton("Connect");
    connectButton.setBackground(new Color(34, 139, 34));
    connectButton.setFont(new Font("VL PGothic", Font.BOLD, 15));
    connectButton.addActionListener(new connectButtonListener());
    GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
    gl_mainPanel.setHorizontalGroup(
    	gl_mainPanel.createParallelGroup(Alignment.LEADING)
    		.addGroup(gl_mainPanel.createSequentialGroup()
    			.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
    				.addGroup(gl_mainPanel.createSequentialGroup()
    					.addContainerGap(51, Short.MAX_VALUE)
    					.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
    						.addComponent(ipAddressLabel, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
    						.addGroup(gl_mainPanel.createParallelGroup(Alignment.TRAILING, false)
    							.addComponent(usernameLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    							.addComponent(portNumberLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
    					.addGap(41)
    					.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
    						.addComponent(portNumberTF, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
    						.addComponent(ipAddressTF, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
    						.addComponent(usernameTF, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)))
    				.addGroup(gl_mainPanel.createSequentialGroup()
    					.addGap(207)
    					.addComponent(connectButton, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)))
    			.addContainerGap(47, Short.MAX_VALUE))
    );
    gl_mainPanel.setVerticalGroup(
    	gl_mainPanel.createParallelGroup(Alignment.LEADING)
    		.addGroup(gl_mainPanel.createSequentialGroup()
    			.addGap(45)
    			.addGroup(gl_mainPanel.createParallelGroup(Alignment.BASELINE)
    				.addComponent(ipAddressLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
    				.addComponent(ipAddressTF, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
    			.addGap(18)
    			.addGroup(gl_mainPanel.createParallelGroup(Alignment.BASELINE)
    				.addComponent(portNumberLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
    				.addComponent(portNumberTF, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
    			.addGap(18)
    			.addGroup(gl_mainPanel.createParallelGroup(Alignment.BASELINE)
    				.addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
    				.addComponent(usernameTF, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
    			.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
    			.addComponent(connectButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
    			.addContainerGap())
    );
    mainPanel.setLayout(gl_mainPanel);
    loginWindow.setVisible(true);
    
    
    
  }
  class connectButtonListener implements ActionListener {

	@Override
    public void actionPerformed(ActionEvent ev) {
        try {
          Socket mySocket = new Socket(ipAddressTF.getText(), Integer.parseInt(portNumberTF.getText()));
          InputStreamReader stream = new InputStreamReader(mySocket.getInputStream());
          BufferedReader input = new BufferedReader(stream);
          PrintWriter output = new PrintWriter(mySocket.getOutputStream());
          //new Client(mySocket, input, output, usernameTF.getText());
          // debugging output.println(COMMAND_NEW+usernameTF.getText());
          // debugging output.flush();
          
          loginWindow.dispose();
        } catch (IOException e) {
          JLabel lblErrorMsg;
          lblErrorMsg = new JLabel();
		lblErrorMsg.setText("Server Connection Error!");
          e.printStackTrace();
        } // End try catch statement
      }
  }
 }

