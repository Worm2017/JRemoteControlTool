import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by R3DST0RM on 27.08.2014.
 */
public class ClientActionListener implements ActionListener {
    private ClientGUI clientGUI;
    private ClientConnection clientConnection;

    public ClientActionListener(ClientGUI clientGUI){
        this.clientGUI = clientGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == clientGUI.menuExit){
            System.out.println("Exiting application, thanks for using ...");
            System.exit(0);
        }

        if(e.getSource() == clientGUI.menuAboutTool){
            new ClientAboutGUI();
        }

        if(e.getSource() == clientGUI.menuStartListen) {
            clientGUI.LogInformation("Start listening on port 5555");
            clientConnection = new ClientConnection(clientGUI);
            clientConnection.start();
            clientGUI.menuStartListen.setEnabled(false);
        }
    }
}
