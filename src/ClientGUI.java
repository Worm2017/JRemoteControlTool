import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class defines the ClientGUI
 *
 * @author R3DST0RM
 * @version 0.1
 */
public class ClientGUI extends JFrame {
    private ClientActionListener actionListener;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    // Menus and Items
    JMenu menuFile = new JMenu("File");
    JMenuItem menuStartListen = new JMenuItem("Start Listening");
    JMenuItem menuExit = new JMenuItem("Exit");
    JMenu menuTools = new JMenu("Tools");
    JMenu menuSettings = new JMenu("Settings");
    JMenu menuAbout = new JMenu("?");
    JMenuItem menuAboutTool = new JMenuItem("About");

    // Console Logging
    JTextArea console = new JTextArea("Welcome, you are using Remote Control Tool...");

    /**
     * Creates a new ClientGUI
     * @param hidden Defines whether this gui is shown or hidden (true - window is hidden)
     */
    public ClientGUI(boolean hidden){
        InitializeGUI();
        setWindowPrefs();
        // simulate doClick()
        setVisible(!hidden);
        menuStartListen.doClick();
    }

    private void InitializeGUI() {
        actionListener = new ClientActionListener(this);

        // Menu Bar
        JMenuBar menu = new JMenuBar();
        menu.add(menuFile);
        menuFile.add(menuStartListen);
        menuFile.add(menuExit);
        menuStartListen.addActionListener(actionListener);
        menuExit.addActionListener(actionListener);
        menu.add(menuTools);
        menu.add(menuSettings);
        menu.add(menuAbout);
        menuAbout.add(menuAboutTool);
        menuAboutTool.addActionListener(actionListener);

        // center design
        JPanel centerPanel = new JPanel(new BorderLayout());
        JScrollPane scrollHistory = new JScrollPane(console);
        scrollHistory.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        centerPanel.add(scrollHistory);
        centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Logging console"));
        console.setForeground(Color.WHITE);
        console.setBackground(Color.BLACK);
        console.setEditable(false);

        // add all to gui
        getContentPane().add(menu, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
    }

    private void setWindowPrefs() {
        // set window preferences
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Remote Control Tool - Client");
        setSize(new Dimension(500,400));
        setLocationRelativeTo(null);
        //setVisible(true);
    }

    /**
     * Prints a text to the console defined
     * @param text Text to be displayed
     */
    public void LogInformation(String text){
        Calendar now = Calendar.getInstance();
        console.append(System.lineSeparator() + "[" + dateFormat.format(now.getTime()) + "]: " + text);
    }

    public static void main(String[] args) {
        /*try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new ClientGUI();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        } */
        boolean hidden = false;
        for (int i = 0; i < args.length; i++) {
            if(args[i].contains("hidden") || args[i].contains("nogui"))
                hidden = true;
        }
        new ClientGUI(hidden);
    }
}
