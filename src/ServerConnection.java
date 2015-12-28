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

    public String getIp() {
        return ip;
    }

    public boolean getConnectionStatus(){
        return connection.isConnected();
    }

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
