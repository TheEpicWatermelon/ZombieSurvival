package client;

/* [Client.java]
 * This code runs is used to test the Zombie Survival Game Server
 * @author Sasha Maximovitch
 * @date January 17th, 2018
 */

//imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


class Client {

    // commands
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
    public static final String GAME_ZOMBIE_MOVE = "gzm"; // used for sending the user the coordinates of a zombie and then where it moves to
    private static final String GAME_MAP = "gmp";//used for sending all the users the map
    public static final String GAME_USER_DEAD = "gud";//used for sending the user that they are dead
    public static final String GAME_USER_TURN = "gut";// used for sending to all users which players turn it is
    private static final String GAME_USER_MOVE = "gum";// sent from a user to process their move(attack or move) the first number 0 or 1 will dictate wheter they moved or attacked, the next numbers are coordinates
    public static final String GAME_ZOMBIE_SPAWNS = "gzs";// used for sending all the user's the location of the zombie spawns
    private static final String GAME_USER_CLASS = "guc"; // semt from user when they select their class
    private static final String GAME_USER_SPAWNS = "gus";// used for sending all the user's a list of all their spawns
    private static final String GAME_USER_FAILURE = "guf";// sent to a user when they cannot do an action
    public static final String GAME_ZOMBIE_DEAD = "gzd";// sent to all users to show a zombie has died at a certain coordinate
    public static final String GAME_USER_HEALTH = "guh";// sent to a user to inform a health change
    
    // class variables
    Socket mySocket; //socket for connection
    BufferedReader input; //reader for network stream
    PrintWriter output;  //printwriter for network output
    boolean running = true; //thread status via boolean

    /** Main
     * runs main code
     * @param args parameters from command line
     */
    public static void main (String[] args) {
        new Client();
    }

    // constructor
    public Client() {
        // create thread for client input
        Thread userInput = new Thread(new UserInputThread());
        userInput.start();
        go(); //begin the connection
    }

    /** Go
     * Starts the client and takes input from the server
     */
    public void go() {

        //Create a socket (try-catch required)
        System.out.println("Attempting to make a connection..");

        try {
            mySocket = new Socket("127.0.0.1", 5000); //attempt socket connection (local address). This will wait until a connection is made

            InputStreamReader stream1 = new InputStreamReader(mySocket.getInputStream()); //Stream for network input
            input = new BufferedReader(stream1);

            output = new PrintWriter(mySocket.getOutputStream()); //assign printwriter to network stream

        } catch (IOException e) {  //connection error occured
            System.out.println("Connection to Server Failed");
            e.printStackTrace();
        }

        System.out.println("Connection made.");

        // Send a message to the server (Once)
        write(COMMAND_NEW + "player");

        output.flush();  //flush (very important)

        while (running) {  // loop unit a message is received
            try {

                if (input.ready()) { //check for an incoming messge
                    String message = input.readLine();
                    if (message.startsWith(COMMAND_ACCEPT)){// if user gets accepted to join the server
                        System.out.println("You have been accepted onto the server");
                    }else if(message.startsWith(COMMAND_DECLINE)){// if user gets declined to join the server
                        System.out.println("You have been declined to join the server");
                        running = false;
                    }else if(message.startsWith(GAME_MAP)){// if the server sends the map to the user
                        String map = message.substring(COMMAND_LEN);
                        displayMap(map);
                    }else if (message.startsWith(GAME_START)){// sent when the game is starting
                        System.out.println("Starting Game");
                    }else if (message.startsWith(GAME_USER_FAILURE)){// sent when the user action cannot be completed
                        System.out.println("Can't perform that action");
                    }
                }

            } catch (IOException e) {
                System.out.println("Failed to receive msg from the server");
                e.printStackTrace();
            }
        }
        try {  //after leaving the main loop we need to close all the sockets
            input.close();
            output.close();
            mySocket.close();
        } catch (Exception e) {
            System.out.println("Failed to close socket");
        }
    }// end Go

    /**
     * displayMap
     * displays the game map in the console
     * @param map - string that holds the map
     */
    private void displayMap(String map) {
        System.out.println();
        for (int i = 0; i < 50; i++) {// loop through the dimension of the map and print out the map
            for (int j = 0; j < 50; j++) {
                System.out.print(map.charAt((i*50)+j));
            }
            System.out.println();
        }
    }// end displayMap

    /**
     * write
     * write to the server
     * @param message to be sent
     */
    public void write(String message){
        output.println(message);
        output.flush();
    }// end write

    /**
     * [UserInputThread.java]
     * inner class that takes in console input from the player and processes it
     * @author Sasha Maximovitch
     * @date January 17th, 2018
     */
    class UserInputThread implements Runnable{

        // reader that takes in console input
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

        /**
         * run
         * runs and waits for user's console inputs and then calls handleCommand method to handle that command
         */
        @Override
        public void run() {
            while (true){
                try {
                    System.out.print("Enter Command:");
                    String input = userIn.readLine();
                    System.out.println(input);
                    handleCommand(input);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // check for inputs
            }
        }// end run

        /**
         * handleCommand
         * takes a command to process, then it will send info to the server
         * @param input - command to be processed
         */

        private void handleCommand(String input){
            // write handlers
            if (input.startsWith("Start Game")){// if user wants to start game
                write(GAME_START);
            }else if (input.equals("w")){// move up
                write(GAME_USER_MOVE+0+"w");
            }else if (input.equals("d")){// move right
                write(GAME_USER_MOVE+0+"d");
            }else if (input.equals("s")){// move down
                write(GAME_USER_MOVE+0+"s");
            }else if (input.equals("a")){// move left
                write(GAME_USER_MOVE+0+"a");
            }else if (input.startsWith("attack")){// for attacks
                write(GAME_USER_MOVE+1+input.substring(6));
            }else if (input.startsWith("Set Class")){// user set class
                write(GAME_USER_CLASS + input.substring(9));
            }else if (input.startsWith("End Game")){// end the game
                write(GAME_END);
                running = false;
            }else{// if there is no valid command
                System.out.println("This is not a valid command...");
            }
        }// end handleCommand
    }// end UserInputThread class
}// end Client class