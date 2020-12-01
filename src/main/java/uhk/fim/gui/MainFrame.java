package uhk.fim.gui;

import uhk.fim.model.IOFile;
import uhk.fim.model.ShoppingCart;
import uhk.fim.model.ShoppingCartItem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.*;
import java.util.Objects;

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

    public MainFrame(int width, int height) {
        super("UHK Václav pospíšil");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        shoppingCart = new ShoppingCart(Objects.requireNonNull(IOFile.load(new File("src/main/java/uhk/fim/model/storage.csv"))));
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
        lblInputName = new JLabel("Název");
        txtInputName = new JTextField("", 15);

        lblInputPricePerPiece = new JLabel("Cena/kus");
        txtInputPricePerPiece = new JTextField("", 5);

        lblInputPieces = new JLabel("Počet kusů");
        spInputPieces = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));

        lblTotalPrice = new JLabel("Celková cena: " +Math.round(shoppingCart.getTotalPrice())+" Kč");

        //button
        btnInputAdd = new JButton("Přidat");
        btnInputAdd.addActionListener(e -> {
            try {
                double price;
                try {
                    price = Double.parseDouble(txtInputPricePerPiece.getText().replace(",", "."));
                } catch (Exception ex) {
                    throw new Exception("Zadejte Double");
                }
                ShoppingCartItem shoppingCartItem = new ShoppingCartItem(txtInputName.getText(), price, (int) spInputPieces.getValue());
                if (shoppingCartItem.getName().trim().isEmpty()) {
                    throw new Exception("Prázdné pole");
                } else if (shoppingCartItem.getName().length() > 100) {
                    throw new Exception("název je větší než 100");
                } else if (shoppingCartItem.getPricePerPiece() < 0) {
                    throw new Exception("Cena je záporná");
                } else {
                    shoppingCart.addItem(shoppingCartItem);
                    shoppingCartTableModel.fireTableDataChanged();

                    updateFooter();

                    JOptionPane.showMessageDialog(mainFrame, "Položka přidána", "JOptionPane - btnInputAdd", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, ex.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
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

    private void createMenuBar() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("Soubor");
        fileMenu.add(new AbstractAction("Nový") {
            @Override
            public void actionPerformed(ActionEvent e) {
                IOFile.save(new ShoppingCart(), new File("src/main/java/uhk/fim/model/storage.csv"));
                shoppingCart.setItems((Objects.requireNonNull(IOFile.load(new File("src/main/java/uhk/fim/model/storage.csv")))).getItems());
                updateAll();
            }
        });
        fileMenu.add(new AbstractAction("Otevřít") {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCart.setItems(IOFile.load(chooseFile()).getItems());
                updateAll();
            }
        });
        fileMenu.add(new AbstractAction("Uložit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                IOFile.save(shoppingCart, new File("src/main/java/uhk/fim/model/storage.csv"));
            }
        });
        fileMenu.add(new AbstractAction("Uložit jako") {
            @Override
            public void actionPerformed(ActionEvent e) {
                IOFile.save(shoppingCart, chooseFile());
            }
        });
        menuBar.add(fileMenu);

        aboutMenu = new JMenu("O programu");
        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);
    }

    private void updateAll() {
        updateFooter();
        shoppingCartTableModel.fireTableDataChanged();
    }

    private void updateFooter() {
        lblTotalPrice.setText("Celková cena: " + Math.round(shoppingCart.getTotalPrice()) + " Kč");
    }

    private File chooseFile() {
        JFileChooser fc = new JFileChooser();

        fc.addChoosableFileFilter(new FileNameExtensionFilter(".json soubor", "json"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter(".csv soubor", "csv"));
        fc.setAcceptAllFileFilterUsed(false);

        int returnVal = fc.showDialog(this, "Vybrat");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (file.getName().endsWith("json")) {
                return file;
            } else if (file.getName().endsWith("csv")) {
                return file;
            }
            JOptionPane.showMessageDialog(fc, "Vlozte spravny format\n Koncovku souboru .json nebo .csv", "JOptionPane - btnInputAdd", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }
}