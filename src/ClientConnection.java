import javax.swing.*;
import java.io.*;
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

    @Override
    public void run() {
        try {
            server = new ServerSocket(5555);

            while (true){
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client, clientGUI);
                clientGUI.LogInformation("Remote Host connected: " + client.getInetAddress().toString());
                new Thread(handler).start();
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
    private BufferedWriter streamWriter;
    private ClientGUI clientGUI;

    public ConnectionHandler(Socket socket, ClientGUI clientGUI) {
        try {
            this.clientGUI = clientGUI;
            this.socket = socket;
            streamReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            streamWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
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
                // received a system command to run
                if(line.startsWith("1:")) {
                    line = line.replace("1:", "");
                    if(line.equals("cmd") || line.equals("cmd.exe") || line.equals("command.com")){
                        Runtime.getRuntime().exec("cmd /c start");
                        SendMessage("Executed: cmd /c start");
                    }else {
                        Runtime.getRuntime().exec(line);
                        clientGUI.LogInformation(line);
                        SendMessage("Executed: " + line);
                    }
                }
            }
        }catch (Exception e){
            //JOptionPane.showMessageDialog(clientGUI, "There was a error communicating with the remote control",
              //      "Error while communicating", JOptionPane.ERROR_MESSAGE);
            System.err.println("Host disconnected?");
            clientGUI.LogInformation("Remote Host disconnected!");
        }
    }

    public void SendMessage(String message) throws Exception{
        streamWriter.write(message + "\n");
        streamWriter.flush();
    }
}
