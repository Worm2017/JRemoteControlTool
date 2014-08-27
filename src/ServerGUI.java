import javax.swing.*;
import java.awt.*;

/**
 * Created by R3DST0RM on 26.08.2014.
 */
public class ServerGUI extends JFrame {
    private ServerActionListener actionListener;

    // Menus and Items
    JMenu menuFile = new JMenu("File");
    JMenuItem menuExit = new JMenuItem("Exit");
    JMenu menuRemoteCtrl = new JMenu("Remote Ctrl");
    JMenu menuWindowsCtrl = new JMenu("Windows");           // submenu
    JMenu menuLinuxCtrl = new JMenu("Linux");               // submenu
    JMenu menuProgrammCtrl = new JMenu("Programm Ctrl");    // submenu
    JMenu menuTools = new JMenu("Tools");
    JMenu menuSettings = new JMenu("Settings");
    JMenu menuAbout = new JMenu("?");
    JMenuItem menuAboutTool = new JMenuItem("About");

    // logging console
    JTextArea console;

    // command panel
    JTextArea cmdHistory = new JTextArea("Use this console to easily communicate with your remote PC");
    JTextField txtCommandLine = new JTextField();
    JButton btnSendCmd = new JButton("Send command");

    // connection items
    JTextField txtConnectionIP = new JTextField("127.0.0.1");
    JButton btnConnect = new JButton("Connect");

    public ServerGUI(){
        InitializeGUI();
        setWindowPrefs();
    }

    private void InitializeGUI() {
        // initialize listeners
        actionListener = new ServerActionListener(this);

        // initialize real gui
        JMenuBar menu = new JMenuBar();
        menu.add(menuFile);
        menuExit.addActionListener(actionListener);
        menuFile.add(menuExit);
        menu.add(menuRemoteCtrl);
        menuRemoteCtrl.add(menuWindowsCtrl);
        menuRemoteCtrl.add(menuLinuxCtrl);
        menuRemoteCtrl.add(menuProgrammCtrl);
        menu.add(menuTools);
        menu.add(menuSettings);
        menu.add(menuAbout);
        menuAbout.add(menuAboutTool);
        menuAboutTool.addActionListener(actionListener);

        // center design
        JPanel centerPanel = new JPanel(new BorderLayout());
        GridBagConstraints b = new GridBagConstraints();
        JPanel connectionPanel = new JPanel(new GridBagLayout());
        connectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Connection information"));
        b.fill = GridBagConstraints.HORIZONTAL;
        b.gridx = 0;
        b.gridy = 0;
        b.weightx = 0.025;
        connectionPanel.add(new JLabel("IP:"), b);
        txtConnectionIP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        b.fill = GridBagConstraints.HORIZONTAL;
        b.gridx = 1;
        b.gridy = 0;
        b.weightx = 0.775;
        connectionPanel.add(txtConnectionIP, b);
        b.fill = GridBagConstraints.HORIZONTAL;
        b.gridx = 2;
        b.gridy = 0;
        b.weightx = 0.2;
        connectionPanel.add(btnConnect, b);
        btnConnect.addActionListener(actionListener);

        JPanel commandPanel = new JPanel(new BorderLayout());
        JScrollPane scrollHistory = new JScrollPane(cmdHistory);
        cmdHistory.setForeground(Color.WHITE);
        cmdHistory.setBackground(Color.BLACK);
        cmdHistory.setEditable(false);
        scrollHistory.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        commandPanel.add(scrollHistory, BorderLayout.CENTER);
        JPanel commandLinePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.90;
        commandLinePanel.add(txtCommandLine, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.10;
        commandLinePanel.add(btnSendCmd, c);
        btnSendCmd.addActionListener(actionListener);
        commandPanel.add(commandLinePanel, BorderLayout.SOUTH);

        // finish center panel
        centerPanel.add(connectionPanel, BorderLayout.NORTH);
        centerPanel.add(commandPanel, BorderLayout.CENTER);

        // south design
        JPanel panelConsole = new JPanel(new BorderLayout());
        panelConsole.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Logging console"));
        panelConsole.setPreferredSize(new Dimension(200,200));
        console = new JTextArea("All important information will be logged here..." + System.lineSeparator() +
                "Status: Not connected" + System.lineSeparator());
        console.setBackground(Color.BLACK);
        console.setForeground(Color.WHITE);
        console.setEditable(false);
        JScrollPane scrollConsole = new JScrollPane(console);
        scrollConsole.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelConsole.add(scrollConsole);

        // add to current frame
        getContentPane().add(menu, BorderLayout.NORTH);
        getContentPane().add(panelConsole, BorderLayout.SOUTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
    }

    private void setWindowPrefs() {
        // set window preferences
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Remote Control Tool - Server");
        setSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void LogInformation(String text){
        console.append(text + System.lineSeparator());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new ServerGUI();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
