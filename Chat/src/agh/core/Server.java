package agh.core;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {
    private final int port = 1000;
    private static int uniqueId = 0;
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

    public void display(String msg) {
        String time = simpleDateFormat.format(new Date()) + ": " + msg;
        serverGUI.append(time);
    }

    public static void main(String args[]) {
        Server server = new Server(new ServerGUI());
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
                username = (String) objectInputStream.readObject();
                display(username +  " has just connected");
            } catch (Exception e) {
                display("CREATING DATA STREAM ERROR: " + e);
                return;
            }

            boolean keepGoing = true;
            String msg;
            while (keepGoing) {
                try {
                    msg = (String) objectInputStream.readObject();
                } catch (Exception e) {
                    break;
                }
                display(username + "> " + msg);
            }
        }
    }

}
