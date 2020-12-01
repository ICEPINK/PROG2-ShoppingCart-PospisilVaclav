package uhk.fim.gui;

import com.google.gson.Gson;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import uhk.fim.model.IOFile;
import uhk.fim.model.ShoppingCart;
import uhk.fim.model.ShoppingCartItem;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

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
        btnInputAdd.addActionListener(e -> {
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

            }
        });
        fileMenu.add(new AbstractAction("OPEN") {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCart.setItems(IOFile.load(chooseFile()).getItems());
                updateAll();
            }
        });
        fileMenu.add(new AbstractAction("SAVE") {
            @Override
            public void actionPerformed(ActionEvent e) {
                IOFile.save(shoppingCart, chooseFile());
            }
        });
        menuBar.add(fileMenu);

        aboutMenu = new JMenu("ABOUT");
        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);
    }

    private void updateAll(){
        updateFooter();
        shoppingCartTableModel.fireTableDataChanged();
    }

    private void updateFooter(){
        lblTotalPrice.setText("lblTotalPrice " + Math.round(shoppingCart.getTotalPrice()));
    }

    private File chooseFile(){
        JFileChooser fc = new JFileChooser();

        fc.addChoosableFileFilter(new FileNameExtensionFilter(".json file","json"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter(".csv file","csv"));
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




/*
    private void safeFileCsv(){
        JFileChooser fc = new JFileChooser();
        int result = fc.showSaveDialog(this);

        if(result == JFileChooser.APPROVE_OPTION){
            String fileName = fc.getSelectedFile().getAbsolutePath();

            try (BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName))){
                for (ShoppingCartItem item : shoppingCart.getItems()){
                    bfw.write(item.getName()+";"+item.getPricePerPiece()+";"+item.getPieces());
                    bfw.newLine();
                }
            }
            catch (IOException e){
                JOptionPane.showMessageDialog(mainFrame, "ERROR", "BufferedWriter - Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void loadFileXmlSax(){
        try {
            CharArrayWriter content = new CharArrayWriter();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(new File("src/uhk/fim/shoppingCart"), new DefaultHandler(){
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    content.reset();
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    System.out.println(qName + ": " + content.toString());
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    super.characters(ch, start, length);
                    content.write(ch, start, length);
                }
            });
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadFileXmlDom4j(){
        DocumentFactory df = DocumentFactory.getInstance();
        SAXReader reader = new SAXReader(df);

        try {
            Document doc = reader.read(new File("src/shoppingCart.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

 */
}
