package agh.core;

/**
 * Created by P on 22.11.2015.
 */
public class Client {
    private ClientGUI clientGUI;

    public Client(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    public void start(){
        new Login().setVisible(true);
    }

    public static void main(String args[]) {
        Client client = new Client(new ClientGUI());
        client.start();
    }
}
