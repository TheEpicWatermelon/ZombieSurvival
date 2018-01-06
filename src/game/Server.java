package game;//imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * [game.Server.java]
 * Runs a server for a game that will hold up to 4 users, will handle all input/output to the users
 * @author Sasha Maximovitch
 * @date November 9th, 2017
 */

class Server {

    //global variables
    private static ServerSocket serverSock;// server socket for connection
    private static boolean running = true;  // controls if the server is accepting clients
    private static boolean gameRunning = false;
    private static List<User> users = Collections.synchronizedList(new ArrayList<>()); // synchronized list of users
    public static List<ConnectionHandler> connectionHandlers = Collections.synchronizedList(new ArrayList<>());// list of all the users connections
    private static final int COMMAND_LEN = 3;// length of the command - default length is 3
    private static final String COMMAND_QUIT = "svt"; // command for user disconnect
    private static final String COMMAND_NEW = "svn";// command used when user first joins the server
    private static final String COMMAND_MESSAGE = "msg"; // command when user wants to send a msg to another user
    private static final String COMMAND_ACCEPT = "sap";// will be sent to a connected user if they have been accepted on to the server
    private static final String COMMAND_DECLINE = "sde"; // will be sent to a connected user if they are not allowed to join the server
    private static final String COMMAND_UPDATE_USER = "sud"; // command that will be accompanied by a list of all the users on the server
    private static final String COMMAND_KICK = "kck";//sent to user that is being kicked
    private static final String GAME_START = "gst"; // user calls this to start the game
    private static final String GAME_END = "ged"; // user calls this to end the game
    private static final String ZOMBIE_MOVE = "gzm"; // sends user the coordinates of a zombie and then where it moves to
    private static GUI GUI; // game.GUI
    volatile static String serverIn; // string that will hold the console input
    private static StringBuilder previousChat;

    /**
     * Main
     * starts game.GUI, loads the previous chat and starts the server
     * @param args
     */
    public static void main(String[] args) throws IOException {
        // initialize game.GUI
        GUI = new GUI();
        // Start console input listener
        Thread console = new Thread(new consoleThread());
        console.start();
        // start the server
        new Server().go(); //start the server
    }

    /**
     * Go
     * Starts the server and accepts users
     */
    public void go() {
        // debug System.out.println("Waiting for a client connection..");

        Socket client = null;//hold the client connection

        try {
            serverSock = new ServerSocket(5000);  //assigns a port to the server
            GUI.appendToConsole("Port: " + serverSock.getLocalPort());
            //serverSock.setSoTimeout(60000);  //60 second timeout
            while (running) {  //loops to accepts client
                client = serverSock.accept();  //wait for connection
                GUI.appendToConsole("Client Connected - " + client.getLocalAddress());
                // debug System.out.println("Client connected");
                if(connectionHandlers.size()>=4 || gameRunning){// if the player count is already at the limit, or game has started, reject the user
                    ConnectionHandler connect = new ConnectionHandler(client);
                    Thread t = new Thread(connect);
                    t.start();
                }else {// if there is still space on the server
                    // create a new user
                    User user = new User();
                    users.add(user); // add user to the list
                    // create a connection handler for the user
                    ConnectionHandler connect = new ConnectionHandler(client, user);
                    connectionHandlers.add(connect);
                    Thread t = new Thread(connect); //create a thread for the new client and pass in the connection handler
                    t.start(); //start the new thread
                }
            }
        } catch (Exception e) {
            System.out.println("Error accepting connection");
            //close all and quit
            try {
                client.close();
            } catch (Exception e1) {
                System.out.println("Failed to close socket");
            }
            System.exit(-1);
        }
    }

    //***** Inner class - thread for client connection

    /**
     * ConnectionHandler
     * handles a users connection to the server and handles their inputs
     */
    class ConnectionHandler implements Runnable {
        private PrintWriter output; //assign printwriter to network stream
        private BufferedReader input; //Stream for network input
        private Socket client;  //keeps track of the client socket
        private boolean running; // boolean that will be true when client is not quitting
        private final User user; // holds the user for this connection

        /* ConnectionHandler
         * Constructor, initializing input/output streams
         * @param the socket belonging to this client connection
         */
        ConnectionHandler(Socket s, User user) {
            this.user = user;// set user
            this.client = s;  //constructor assigns client to this
            try {  //assign all connections to client
                this.output = new PrintWriter(client.getOutputStream());
                InputStreamReader stream = new InputStreamReader(client.getInputStream());
                this.input = new BufferedReader(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            running = true;
        } //end of constructor

        /**
         * ConnectionHandler
         * Constructor - this runs if there are 4 players already on the server, or game has started. It disconnects the user and tells them there are too many players
         * @param client
         */
        public ConnectionHandler(Socket client) {
            this.client = client;
            this.user = null;
            try{
                this.output = new PrintWriter(client.getOutputStream());
                InputStreamReader stream = new InputStreamReader(client.getInputStream());
                this.input = new BufferedReader(stream);
                write(COMMAND_DECLINE);
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /* run
         * executed on start of thread
         * it will handle the user inputs and will close when user leaves
         */
        public void run() {

            //Get a message from the client
            String userInput = "";

            //Get a message from the client
            while (running) {  // loop unit a message is received
                try {
                    if (this.input.ready()) { //check for an incoming message
                        userInput = this.input.readLine();  //get a message from the client

                        // debug System.out.println("msg from client: " + userInput);

                        // if the user sends a disconnect
                        if (userInput.startsWith(COMMAND_QUIT)) {
                            writeToUsers(COMMAND_MESSAGE+0+ user.getName() + " has disconnected");// placeholder
                            running = false; //stop receiving messages
                        }else if(userInput.startsWith(COMMAND_NEW)){
                            write(COMMAND_ACCEPT);
                            user.setName(userInput.substring(COMMAND_LEN));
                            user.setListNum(connectionHandlers.size());
                            GUI.appendToConsole("game.User "+ user.getListNum() + " name set: " + user.getName());
                            updateUserList();
                            writeToUsers(COMMAND_MESSAGE +0+ user.getName() + " has connected");
                        }else if(userInput.startsWith(COMMAND_MESSAGE)){
                            String msg = userInput.substring(COMMAND_LEN);
                            String send = COMMAND_MESSAGE + user.getListNum() + msg;
                            writeToUsers(send);
                            GUI.appendToConsole(user.getListNum() + ":" + user.getName()+" sent message: " + send);
                        }
                        else {
                            output.println("err Command Not Available");
                            output.flush();
                        }

                    }
                } catch (IOException e) {
                    System.out.println("Failed to receive userInput from the client");
                    e.printStackTrace();
                }
            }

            updateUserRemoved();

            updateUserList();

            close();
        } // end of run()

        /**
         * updateUserRemoved
         * remove user from the list of user's and the user's connection handler
         */
        private void updateUserRemoved() {
            //game.GUI.appendToConsole(user.getListNum() + "- disconnecting");

            users.remove(user);
            connectionHandlers.remove(this);

            // loop through all the connectionHandlers and update the user's numbers
            for (int i = 1; i <= connectionHandlers.size(); i++) {
                connectionHandlers.get(i).user.setListNum(i);
            }
        }

        /**
         * updateUserList
         * create a list of all the users on the server and send it to every user
         */
        private void updateUserList() {
            StringBuilder string = new StringBuilder();
            string.append(COMMAND_UPDATE_USER); // start with the update user command
            for (int i = 0; i < connectionHandlers.size(); i++) {
                if (i < connectionHandlers.size()-1){// if this is not the last user add a comma
                    string.append(connectionHandlers.get(i).user.getName()+",");
                }else{
                    string.append(connectionHandlers.get(i).user.getName());
                }
            }
            writeToUsers(string.toString());
        }

        /**
         * writeToUser
         * write to users, can be to general chat or via private message, message will be sent to the sender as well to confirm that their message has gone through the server
         * @param msg - message user wants to send
         */
        public void writeToUsers(String msg) {
            for (int i = 0; i < connectionHandlers.size(); i++) {
                connectionHandlers.get(i).write(msg);// write the message to every user
            }
        }

        /**
         * write
         * output to the user a message
         * @param msg - message to be sent
         */
        public void write(String msg) {
            output.println(msg);
            output.flush();
        }

        /**
         * close
         * close the current connection handler
         */
        private void close() {
            //close the socket
            try {
                GUI.appendToConsole(user.getListNum() + " - " + user.getName() + " has disconnected.");
                this.input.close();
                output.close();
                client.close();
            } catch (Exception e) {
                System.out.println("Failed to close socket");
            }
        }
    } //end of inner class

    /**
     * [consoleThread.java]
     * gets and processes inputs from the console
     */
    private static class consoleThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (serverIn == null) {// if there is no message keep looping
                    continue;
                }
                if (serverIn.equals("close")) {// shuts down server
                    for (int i = 0; i < connectionHandlers.size(); i++) {// send message to every user that server is closing
                        connectionHandlers.get(i).write(COMMAND_MESSAGE + "~~SERVER CLOSING IN 20 SECONDS~~");
                        connectionHandlers.get(i).write(COMMAND_QUIT);
                    }
                    GUI.appendToConsole("Shutting down server...");
                    // debug System.out.println("Starting shut down");
                    try {// wait 5 seconds
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                    // debug System.out.println("Waiting Done");
                    for (int i = 0; i < connectionHandlers.size(); i++) {// turn off all users connection handler's
                        connectionHandlers.get(i).running = false;
                    }
                    // close the server socket
                    try {
                        serverSock.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                    // debug System.out.println("Shutting down");
                    System.exit(1);// close
                } else if (serverIn.startsWith("kick")) { // kick a yser
                    try {
                        serverIn = serverIn.substring(5);// substring kick and number after
                    } catch (StringIndexOutOfBoundsException e) {
                        GUI.appendToConsole("No user specified");
                        serverIn = null;
                        continue;
                    }
                    int kickNum;
                    try {// get the list number of the kick user
                        kickNum = Integer.parseInt(serverIn);
                    } catch (StringIndexOutOfBoundsException e) {// if there is no valid number
                        GUI.appendToConsole("No user specified");
                        serverIn = null;
                        continue;
                    }
                    ConnectionHandler connectionHandler = null;
                    for (int i = 0; i < connectionHandlers.size(); i++) {// get the connection handler of that user
                        if (connectionHandlers.get(i).user.getListNum() == kickNum) {
                            connectionHandler = connectionHandlers.get(i);
                            break;
                        }
                    }
                    if (connectionHandler == null) {// if there is no connection handler
                        GUI.appendToConsole("This is not a valid user");
                        serverIn = null;
                        continue;
                    }
                    // send out that user has been kicked
                    connectionHandler.writeToUsers(COMMAND_MESSAGE+0+connectionHandler.user.getName() + " has been kicked");// place holder
                    connectionHandler.write(COMMAND_KICK);
                    connectionHandler.running = false; // stop user's connection handler
                    GUI.appendToConsole("Kicked user: " + kickNum + " - " + connectionHandler.user.getName());
                    serverIn = null;
                } else if (serverIn.startsWith("list members")) {// to display the list of all the members on the server
                    StringBuilder listOfMembers = new StringBuilder();
                    // if there are no users online
                    if (users.size() == 0){
                        GUI.appendToConsole("There are no users online.");
                        break;
                    }
                    // get the names of all the users in the server
                    for (int i = 0; i < users.size(); i++) {
                        listOfMembers.append(" " + users.get(i).getListNum() + " - " + users.get(i).getName() + " ");
                        if (i < users.size() - 1) {
                            listOfMembers.append(",");
                        }
                    }
                    GUI.appendToConsole(listOfMembers.toString());
                    serverIn = null;
                } else if (serverIn.startsWith("broadcast")) {// broadcast a message to general chat
                    String msg = COMMAND_MESSAGE + serverIn.substring(9);// add command and get the message, substring first 9 characters of serverIn to remove broadcast
                    for (int i = 0; i < connectionHandlers.size(); i++) {// send to each user
                        connectionHandlers.get(i).write(msg);
                    }
                    serverIn = null;
                } else {// if the input is not valid
                    GUI.appendToConsole("This is not a valid command!");
                    serverIn = null;
                }
            }
        }
    }

} //end of game.Server class