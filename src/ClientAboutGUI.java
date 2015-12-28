import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ClientAboutGUI creates a new window and shows some information about the program
 *
 * @author R3DST0RM
 * @version 0.1
 */
public class ClientAboutGUI extends JDialog {

    public ClientAboutGUI(){
        InitializeGUI();
        setWindowPrefs();
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

    private void setWindowPrefs() {
        // set window preferences
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Remote Control Tool - About");
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
    }
}
