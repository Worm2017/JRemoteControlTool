import javax.swing.*;
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
            new ServerAboutGUI(serverGUI);
        }

        if(e.getSource() == serverGUI.menuWindowsCtrlShowDriveLetters){
            serverGUI.InsertCommand("cmd fsutil fsinfo drives", 0, 0);
        }

        if(e.getSource() == serverGUI.menuWindowsCtrlShowDirectoryContent){
            serverGUI.InsertCommand("cmd cd {DIRECTORY} && dir", 7, 18);
        }

        if(e.getSource() == serverGUI.menuWindowsCtrlDeleteDirectory){
            serverGUI.InsertCommand("cmd rd /s /q {DIRECTORY}", 13, 24);
        }

        if(e.getSource() == serverGUI.btnConnect){
            try {
                serverGUI.LogInformation("Trying connect to: " + serverGUI.txtConnectionIP.getText());
                serverConnection = new ServerConnection(serverGUI.txtConnectionIP.getText(), serverGUI);
                serverGUI.LogInformation("Status: " + serverConnection.getConnectionStatus() + " ;IP=" +
                        serverConnection.getIp() + " (" + serverConnection.getConnectionAddress() + ")");
                serverGUI.btnConnect.setEnabled(false);

                new Thread(serverConnection).start();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(serverGUI, "Could not connect to given address!", "Error while connecting",
                        JOptionPane.ERROR_MESSAGE);
                serverGUI.LogInformation("Could not connect to given address: " + serverGUI.getTryIp()
                        + " - Client unreachable!");
            }
        }

        if(e.getSource() == serverGUI.btnSendCmd){
            try {
                if (!serverGUI.txtCommandLine.getText().isEmpty()) {
                    ExecuteCommand("1:" + serverGUI.txtCommandLine.getText(), true);
                    // Clean message line and add to history
                    CleanAndAddToHistory();
                }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(serverGUI, "Error while trying to process your entered command." +
                                System.lineSeparator() +
                        "Please make sure you are connected to a client." + System.lineSeparator() +
                        "If this error persists please try to restart the application otherwise please create a issue" +
                        " on http://github.com/R3DST0RM/JRemoteControlTool", "Message could not be processed",
                        JOptionPane.ERROR_MESSAGE);
                serverGUI.txtCommandLine.setText("");
            }
        }
    }

    private void CleanAndAddToHistory() {
        String oldCMD = serverGUI.txtCommandLine.getText();
        serverGUI.txtCommandLine.setText("");
        serverGUI.cmdHistory.append(System.lineSeparator() + "You: " + oldCMD + System.lineSeparator());
    }

    /**
     * ExecuteCommand logs whether a command was executed or not by the client
     * @param cmd command which should be executed by the client
     * @param message boolean value if message is a command or a message
     * @deprecated This method needs a review! (There is no alternative!)
     */
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
