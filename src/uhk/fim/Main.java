package uhk.fim;

import uhk.fim.gui.MainFrame;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame(800,600);
                mainFrame.setVisible(true);
            }
        });
    }
}
