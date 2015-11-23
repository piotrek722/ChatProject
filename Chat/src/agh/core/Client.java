package agh.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    // GUI
    private ClientGUI clientGUI;

    // Server, port
    private String server;
    private int port;

    // IO
    private Socket socket;
    private ObjectInputStream objectInputStream; //to read from the socket
    private ObjectOutputStream objectOutputStream; //to write on the socket

    public Client(ClientGUI clientGUI, String server, int port) {
        this.clientGUI = clientGUI;
        this.server = server;
        this.port = port;
    }

    public void start() {
        new Login().setVisible(true);

        // THINGS TO DO AFTER LOGIN

        //Try to connect to the server
        try {
            socket = new Socket(server, port);
        } catch (Exception e) {
            display("Connection to the server failed:\n\t" + e);
            return;
        }
        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

        //Creating both Data Stream
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            display("Creating Data Streams failed:\n\t" + e);
            return;
        }

        //Listen Thread
        new ListenFromServer().start();

        //Login
        try {
            objectOutputStream.writeObject("USER NAME");
        } catch (IOException e) {
            return;
        }
    }

    private void display(String msg) {
        clientGUI.append(msg);
    }

    public void sendMessage(String msg) {
        try {
            objectOutputStream.writeObject(msg);
        } catch (IOException e) {
            return;
        }
    }

    public static void main(String args[]) {
        ClientGUI clientGUI = new ClientGUI();
        Client client = new Client(clientGUI, "localhost", 1000);
        clientGUI.setClient(client);
        client.start();
    }

    /**
     * Tread is just listening the Server
     */
    class ListenFromServer extends Thread {
        public void run() {
            while (true) {
                try {
                    String msg = (String) objectInputStream.readObject();
                    //Should appear in particular Tab
                    clientGUI.append(msg);
                } catch (IOException e) {
                    display("Server has close the connection: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                }
            }
        }
    }
}
