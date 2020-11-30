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
        switch (columnIndex){
            case 0:
                return item.getName();
            case 1:
                return item.getPricePerPiece();
            case 2:
                return item.getPieces();
            case 3:
                return item.getPieces()*item.getPricePerPiece();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Name";
            case 1:
                return "PricePerPiece";
            case 2:
                return "Pieces";
            case 3:
                return "TotalPrice";
            default:
                return null;
        }
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
