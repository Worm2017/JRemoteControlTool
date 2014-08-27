import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by R3DST0RM on 27.08.2014.
 */
public class ClientConnection extends Thread{
    private ClientGUI clientGUI;
    private ServerSocket server;

    public ClientConnection(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
        clientGUI.LogInformation("Server successfully initialized!");
    }

    public void SendMessage(){

    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(5555);

            while (true){
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client, clientGUI);
                new Thread(handler).run();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}

class ConnectionHandler implements Runnable{
    private Socket socket;
    private BufferedReader streamReader;
    private ClientGUI clientGUI;

    public ConnectionHandler(Socket socket, ClientGUI clientGUI) {
        try {
            this.clientGUI = clientGUI;
            this.socket = socket;
            streamReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String line = "";
            while ((line = streamReader.readLine()) != null) {
                clientGUI.LogInformation(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SendMessage(String message){

    }
}
