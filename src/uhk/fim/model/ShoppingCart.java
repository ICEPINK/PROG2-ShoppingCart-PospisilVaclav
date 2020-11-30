package uhk.fim.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<ShoppingCartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<ShoppingCartItem>();
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void addItem(ShoppingCartItem item){
        items.add(item);
        for (int i = 0;i<items.size()-1;i++) {
            if(items.get(i).getName().equals(item.getName())&&items.get(i).getPricePerPiece()==item.getPricePerPiece()){
                items.remove(items.size()-1);
                items.get(i).setPieces(item.getPieces()+items.get(i).getPieces());
                break;
            }
        }
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for(int i=0; i < getItems().size(); i++){
            ShoppingCartItem item = getItems().get(i);
            totalPrice = (totalPrice+(item.getPieces()* item.getPricePerPiece()));
        }
        return totalPrice;
    }

}
