package agh.core;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by P on 22.11.2015.
 */
public class Server {
    private final int port = 1000;
    private ServerGUI serverGUI;

    ServerSocket serverSocket;
    Socket socket;

    public Server(ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(1000);
            socket = serverSocket.accept();
        } catch (Exception e) {
            return;
        }
    }

    public static void main(String args[]) {
        Server server = new Server(new ServerGUI());
        server.start();
    }
}
