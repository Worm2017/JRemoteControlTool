import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by R3DST0RM on 26.08.2014.
 */
public class ServerActionListener implements ActionListener {
    private ServerGUI serverGUI;
    private ServerConnection serverConnection;

    public ServerActionListener(ServerGUI serverGUI){
        this.serverGUI = serverGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == serverGUI.menuExit){
            System.out.println("Exiting application, thanks for using ...");
            System.exit(0);
        }

        if(e.getSource() == serverGUI.menuAboutTool){
            new ServerAboutGUI();
        }

        if(e.getSource() == serverGUI.btnConnect){
            serverGUI.LogInformation("Trying connect to: " + serverGUI.txtConnectionIP.getText());
            serverConnection = new ServerConnection(serverGUI.txtConnectionIP.getText());
            serverGUI.LogInformation("Status: " + serverConnection.getConnectionStatus() + " ;IP=" +
                    serverConnection.getIp() + " (" + serverConnection.getConnectionAddress() + ")");
            serverGUI.btnConnect.setEnabled(false);
        }

        if(e.getSource() == serverGUI.btnSendCmd){
            if(!serverGUI.txtCommandLine.getText().isEmpty()) {
                ExecuteCommand("1:" + serverGUI.txtCommandLine.getText(), true);
                // Clean message line and add to history
                CleanAndAddToHistory();
            }
        }
    }

    private void CleanAndAddToHistory() {
        String oldCMD = serverGUI.txtCommandLine.getText();
        serverGUI.txtCommandLine.setText("");
        serverGUI.cmdHistory.append(System.lineSeparator() + "You: " + oldCMD);
    }

    public void ExecuteCommand(String cmd, boolean message){
        boolean cmdExecuted = serverConnection.ExecuteCommand(cmd);
        if(!message) {
            serverGUI.LogInformation("Command: " + cmd + " ;executed=" + cmdExecuted);
        }
        else {
            serverGUI.LogInformation("Message: " + cmd + " ;send=" + cmdExecuted);
        }
    }
}
