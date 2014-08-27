import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by R3DST0RM on 26.08.2014.
 */
public class ServerConnection {
    private String ip;
    private Socket connection;
    private InputStreamReader streamReader;
    private OutputStreamWriter streamWriter;

    public ServerConnection(String ip){
        try {
            this.ip = ip;
            connection = new Socket(this.ip, 5555);
            streamReader = new InputStreamReader(connection.getInputStream());
            streamWriter = new OutputStreamWriter(connection.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
