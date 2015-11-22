package agh.core;

/**
 * Created by P on 22.11.2015.
 */
public class Server {
    private final int port = 1000;
    private ServerGUI serverGUI;

    public Server(ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
    }

    public void start(){

    }

    public static void main(String argsp[]) {
        Server server = new Server(new ServerGUI());
        server.start();
    }
}
