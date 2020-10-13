package uhk.fim.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(int width,int height) {
        super("PRO2 - Shopping cart");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initGUI();
    }

    /* init bez Super
    public void initFrame() {
        setTitle("PRO2 - Shopping cart");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    */

    public void initGUI() {
        JPanel panelMain = new JPanel(new BorderLayout());

        add(panelMain);

    }
}
