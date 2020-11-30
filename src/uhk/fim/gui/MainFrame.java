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
        JPanel panelMain = new JPanel(new BorderLayout());

        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelTable = new JPanel(new BorderLayout());
        JPanel panelFooter = new JPanel(new BorderLayout());

        //label + textField
        JLabel lblInputName = new JLabel("InputName");
        JTextField txtInputName = new JTextField("",15);

        JLabel lblInputPricePerPiece = new JLabel("InputPricePerPiece");
        JTextField txtInputPricePerPiece = new JTextField("",5);

        JLabel lblInputPieces = new JLabel("lblInputPieces");
        JSpinner spInputPieces = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));

        JLabel lblTotalPrice = new JLabel("lblTotalPrice " + shoppingCart.getTotalPrice());

        //button
        JButton btnInputAdd = new JButton("btnInputAdd");
        btnInputAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem(txtInputName.getText(), Double.parseDouble(txtInputPricePerPiece.getText()), (int) spInputPieces.getValue());
                    if(shoppingCartItem.getName().isEmpty()){
                        throw new Exception("IsEmpty");
                    } else if (shoppingCartItem.getName().length()>100){
                        throw new Exception("IsBiggerThen100");
                    } else if (shoppingCartItem.getPricePerPiece()<0){
                        throw new Exception("IsNegative");
                    } else {
                        shoppingCart.addItem(shoppingCartItem);
                        shoppingCartTableModel.fireTableDataChanged();

                        lblTotalPrice.setText("lblTotalPrice " + shoppingCart.getTotalPrice());

                        JOptionPane.showMessageDialog(mainFrame, "Položka přidána", "JOptionPane - btnInputAdd", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(mainFrame, "NumberFormatException", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //table
        JTable table = new JTable();
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
}
