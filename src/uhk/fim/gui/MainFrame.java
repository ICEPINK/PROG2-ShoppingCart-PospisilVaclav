package uhk.fim.gui;

import uhk.fim.model.ShoppingCart;
import uhk.fim.model.ShoppingCartItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    MainFrame mainFrame = this;
    ShoppingCart shoppingCart;
    ShoppingCartTableModel shoppingCartTableModel;

    //components
    JPanel panelMain;
    JPanel panelInput;
    JPanel panelTable;
    JPanel panelFooter;

    JLabel lblInputName;
    JLabel lblInputPricePerPiece;
    JLabel lblInputPieces;
    JLabel lblTotalPrice;
    JTextField txtInputName;
    JTextField txtInputPricePerPiece;

    JSpinner spInputPieces;

    JButton btnInputAdd;

    JTable table;

    JMenuBar menuBar;

    JMenu fileMenu;
    JMenu aboutMenu;

    public MainFrame(int width,int height) {
        super("MainFrame");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        shoppingCart = new ShoppingCart();
        shoppingCartTableModel = new ShoppingCartTableModel();
        shoppingCartTableModel.setShoppingCart(shoppingCart);

        initGUI();
    }

    public void initGUI() {
        //panel
        panelMain = new JPanel(new BorderLayout());

        createMenuBar();

        panelInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTable = new JPanel(new BorderLayout());
        panelFooter = new JPanel(new BorderLayout());

        //label + textField
        lblInputName = new JLabel("InputName");
        txtInputName = new JTextField("",15);

        lblInputPricePerPiece = new JLabel("InputPricePerPiece");
        txtInputPricePerPiece = new JTextField("",5);

        lblInputPieces = new JLabel("lblInputPieces");
        spInputPieces = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));

        lblTotalPrice = new JLabel("lblTotalPrice " + shoppingCart.getTotalPrice());

        //button
        btnInputAdd = new JButton("btnInputAdd");
        btnInputAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double price;
                    try{
                        price = Double.parseDouble(txtInputPricePerPiece.getText().replace(",","."));
                    }
                    catch (Exception ex){
                        throw new Exception("NotDouble");
                    }
                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem(txtInputName.  getText(), price, (int) spInputPieces.getValue());
                    if(shoppingCartItem.getName().trim().isEmpty()){
                        throw new Exception("IsEmpty");
                    } else if (shoppingCartItem.getName().length()>100){
                        throw new Exception("IsBiggerThen100");
                    } else if (shoppingCartItem.getPricePerPiece()<0){
                        throw new Exception("IsNegative");
                    } else {
                        shoppingCart.addItem(shoppingCartItem);
                        shoppingCartTableModel.fireTableDataChanged();

                        updateFooter();

                        JOptionPane.showMessageDialog(mainFrame, "Položka přidána", "JOptionPane - btnInputAdd", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //table
        table = new JTable();
        table.setModel(shoppingCartTableModel);

        //.add
        panelInput.add(lblInputName);
        panelInput.add(txtInputName);
        panelInput.add(lblInputPricePerPiece);
        panelInput.add(txtInputPricePerPiece);
        panelInput.add(lblInputPieces);
        panelInput.add(spInputPieces);
        panelInput.add(btnInputAdd);

        panelTable.add(new JScrollPane(table), BorderLayout.CENTER);

        panelFooter.add(lblTotalPrice, BorderLayout.WEST);

        panelMain.add(panelInput, BorderLayout.NORTH);
        panelMain.add(panelTable, BorderLayout.CENTER);
        panelMain.add(panelFooter, BorderLayout.SOUTH);

        add(panelMain);
    }

    private void createMenuBar(){
        menuBar = new JMenuBar();

        fileMenu = new JMenu("FILE");
        fileMenu.add(new AbstractAction("NEW") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "NEW", "filemenu - NEW", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        fileMenu.add(new AbstractAction("OPEN") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "OPEN", "filemenu - OPEN", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        fileMenu.add(new AbstractAction("SAVE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "SAVE", "filemenu - SAVE", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuBar.add(fileMenu);

        aboutMenu = new JMenu("ABOUT");
        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);
    }

    private void updateFooter(){
        lblTotalPrice.setText("lblTotalPrice " + Math.round(shoppingCart.getTotalPrice()));
    }
}
