package uhk.fim.gui;

import uhk.fim.model.ShoppingCart;
import uhk.fim.model.ShoppingCartItem;

import javax.swing.table.AbstractTableModel;

public class ShoppingCartTableModel extends AbstractTableModel {

    private ShoppingCart shoppingCart;

    @Override
    public int getRowCount() {
        return shoppingCart.getItems().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ShoppingCartItem item = shoppingCart.getItems().get(rowIndex);
        return switch (columnIndex) {
            case 0 -> item.getName();
            case 1 -> item.getPricePerPiece();
            case 2 -> item.getPieces();
            case 3 -> item.getTotalPrice();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Název";
            case 1 -> "Cena/kus";
            case 2 -> "Počet kusů";
            case 3 -> "Cena za všechny kusy";
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> String.class;
            case 1, 3 -> Double.class;
            case 2 -> Integer.class;
            default -> null;
        };
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
