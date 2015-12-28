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

    /**
     * ClientConnection manages the connections incoming for every new connection there is a new thread
     * @param clientGUI Pass a valid client gui handle
     */
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

    /**
     * ConnectionHandler is a sub class of ClientConnection
     * @param socket incoming connection socket
     * @param clientGUI ClientGUI handle
     */
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
                    }else if(line.contains("cmd")) {
                        line = line.replace("cmd ", "").replace("cmd", "").replace("cmd.exe", "").replace("cmd.exe ", "");
                        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", line);
                        Process proc = pb.start();
                        InputStream inputStream = proc.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                        String output = null;
                        SendMessage("###### Command output #####");
                        while((output = br.readLine()) != null){
                            SendMessage(output);
                        }
                        SendMessage("###### Command output #####");
                        int retVal = proc.waitFor();
                        if(retVal != 0){ SendMessage("Error while executing your process!"); }

                    }else if(line.startsWith("view-output")){
                        line = line.replace("view-output ", "").replace("view-output", "");

                        ProcessBuilder pb = new ProcessBuilder(line);
                        Process proc = pb.start();
                        InputStream inputStream = proc.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                        String output = null;
                        SendMessage("###### Command output #####");
                        while((output = br.readLine()) != null){
                            SendMessage(output);
                        }
                        SendMessage("###### Command output #####");
                        int retVal = proc.waitFor();
                        if(retVal != 0){ SendMessage("Error while executing your process!"); }
                    }
                    else {
                        Runtime.getRuntime().exec(line);
                        clientGUI.LogInformation(line);
                        SendMessage("Executed: " + line);
                    }
                }
            }
        }catch (Exception e){
            //JOptionPane.showMessageDialog(clientGUI, "There was a error communicating with the remote control",
              //      "Error while communicating", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.err.println("Host disconnected?");
            clientGUI.LogInformation("Remote Host disconnected!");
        }
    }

    /**
     * Sends message back to the host which is connected
     * @param message Message send back to the host
     * @throws Exception The exception occurs when a host is disconnected and this routine is called.
     */
    public void SendMessage(String message) throws Exception{
        streamWriter.write(message + "\n");
        streamWriter.flush();
    }
}
