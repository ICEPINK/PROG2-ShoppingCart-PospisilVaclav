package uhk.fim.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    JButton btnInputAdd;
    JTextField txtInputName;
    JTextField txtInputNameCK;
    JTextField txtInputNamePK;

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

        JPanel panelImput = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelTabel = new JPanel();
        JPanel panelFooter = new JPanel();

        JLabel lbl = new JLabel("Nazev: ");
        txtInputName = new JTextField("",15);

        JLabel lblCK = new JLabel("Cena/Kus: ");
        txtInputNameCK = new JTextField("",5);

        JLabel lblPK = new JLabel("Pocet kusu: ");
        txtInputNamePK = new JTextField("",5);

        btnInputAdd = new JButton("Pridat: ");
        btnInputAdd.addActionListener(this);


        panelImput.add(lbl);
        panelImput.add(txtInputName);
        panelImput.add(lblCK);
        panelImput.add(txtInputNameCK);
        panelImput.add(lblPK);
        panelImput.add(txtInputNamePK);
        panelImput.add(btnInputAdd);


        panelMain.add(panelImput, BorderLayout.NORTH);
        panelMain.add(panelTabel, BorderLayout.CENTER);
        panelMain.add(panelFooter, BorderLayout.SOUTH);

        add(panelMain);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==btnInputAdd) {
            System.out.println("Polozka: " + txtInputName.getText() +" Cena za kus: "+ txtInputNameCK.getText() +" Po4et kusu: "+ txtInputNamePK.getText());
        }
    }
}
