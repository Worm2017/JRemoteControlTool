import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by R3DST0RM on 26.08.2014.
 */
public class ServerAboutGUI extends JDialog {

    public ServerAboutGUI(ServerGUI gui){
        InitializeGUI();
        setWindowPrefs(gui);
    }

    private void InitializeGUI() {
        getContentPane().add(new JLabel("<html><h1>About Remote Control Tool</h1>" +
                "<br />" +
                "<p>Remote Control Tool allows you to control your pc remotely.<br />" +
                "It's completely written in Java by Dominik Schwarzbauer.<br />" +
                "There's no screen recorder and stream to the server panel.<br />" +
                "It's intention is to control your pc through commandline just to keep it easy.<br />" +
                "There's no warranty for any damage that tool may cause.<br />" +
                "<br />" +
                "Licensed under: GNU General Public License v2<br />" +
                "Have fun! :)</p>"));

        JButton btn = new JButton("Close");
                btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(btn, BorderLayout.SOUTH);
    }

    private void setWindowPrefs(ServerGUI gui) {
        // set window preferences
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Remote Control Tool - About");
        pack();
        setLocationRelativeTo(gui);
        setModal(true);
        setVisible(true);
    }
}
