import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by R3DST0RM on 26.08.2014.
 */
public class ServerConnection implements Runnable{
    private String ip;
    private Socket connection;
    private InputStreamReader streamReader;
    private OutputStreamWriter streamWriter;
    private BufferedReader bufStreamReader;
    private ServerGUI serverGUI;

    public ServerConnection(String ip, ServerGUI serverGUI) throws Exception{
        this.ip = ip;
        this.serverGUI = serverGUI;
        connection = new Socket(this.ip, 5555);
        streamReader = new InputStreamReader(connection.getInputStream());
        streamWriter = new OutputStreamWriter(connection.getOutputStream());
        bufStreamReader = new BufferedReader(streamReader);
    }

    /**
     *
     * @return Returns the IP where the user wants to connect to
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @return returns true if ServerGUI is connected to a client otherwise false
     */
    public boolean getConnectionStatus(){
        return connection.isConnected();
    }

    /**
     * Sends a command/message to the client where its connected
     * @param cmd command string which will be sent
     * @return returns true if there was no error otherwise false
     */
    public boolean ExecuteCommand(String cmd){
        try {
            streamWriter.write(cmd + "\n");
            streamWriter.flush();
            return true;
        } catch (Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @return returns the remote socket address
     */
    public String getConnectionAddress() {
        return connection.getRemoteSocketAddress().toString();
    }

    @Override
    public void run() {
        try{
            String line = "";
            while((line = bufStreamReader.readLine()) != null){
                serverGUI.LogRemoteAnswer(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
