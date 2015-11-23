package agh.core;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {
    private final int port = 1000; //Server port
    private static int uniqueId = 0; //unique ID for Client threads
    private ServerGUI serverGUI;
    private ArrayList<ClientThread> clientThreadList;
    private SimpleDateFormat simpleDateFormat;

    ServerSocket serverSocket;

    public Server(ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
        this.clientThreadList = new ArrayList<ClientThread>();
        this.simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public void start(){
        boolean keepGoing = true;
        try {
            serverSocket = new ServerSocket(port);
            while (keepGoing) {
                //Accept new connection and add client to online list.
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(socket);
                clientThreadList.add(clientThread);
                clientThread.start();
            }
        } catch (Exception e) {
            display("ERROR: " + e);
            return;
        }

    }

    /**
     * Method display message on ServerGUI's text area.
     * @param msg Message to display.
     */
    public void display(String msg) {
        String time = simpleDateFormat.format(new Date()) + ": " + msg;
        serverGUI.append(time);
    }

    /**
     * Method sends message to all users in clientThreadList.
     * @param msg Message to send.
     */
    //Should show in MainPane of ClientGUI.
    public void broadcast(String msg) {
        String time = simpleDateFormat.format(new Date());
        for (ClientThread client : clientThreadList){
            client.writeMsg(time + ": " + msg);
        }
    }

    public static void main(String args[]) {
        ServerGUI serverGUI = new ServerGUI();
        Server server = new Server(serverGUI);
        serverGUI.setServer(server);
        server.start();
    }

    public class ClientThread extends Thread {
        private int id;
        private String username;

        private Socket socket;
        private ObjectInputStream objectInputStream;
        private ObjectOutputStream objectOutputStream;

        public ClientThread(Socket socket) {
            this.socket = socket;
            this.id = ++uniqueId;
        }

        public void run() {
            try {
                this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
                //Will be type of Message?
                username = (String) objectInputStream.readObject();
                display(username +  " has just connected");
                serverGUI.addClientToJList(username);
            } catch (Exception e) {
                display("CREATING DATA STREAM ERROR: " + e);
                return;
            }

            boolean keepGoing = true;
            String msg;
            while (keepGoing) {
                try {
                    //Will be type of Message
                    msg = (String) objectInputStream.readObject();
                } catch (Exception e) {
                    break;
                }
                //Should appear in particular TabPane
                display(username + "> " + msg);
            }
        }

        public void writeMsg(String msg) {
            if (!socket.isConnected()) {
                display("USER: " + username + " is offline");
                return;
            }
            try {
                //Will be type of Message
                objectOutputStream.writeObject(msg);
            } catch (Exception e) {
                display("Error sending message to: " + username);
                return;
            }
        }
    }

}
