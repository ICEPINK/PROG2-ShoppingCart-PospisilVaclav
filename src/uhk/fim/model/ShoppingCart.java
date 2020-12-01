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
        for (ShoppingCartItem shoppingCartItem : items) {
            if(shoppingCartItem.getName().equals(item.getName())&&shoppingCartItem.getPricePerPiece()==item.getPricePerPiece()){
                shoppingCartItem.setPieces(item.getPieces()+shoppingCartItem.getPieces());
                return;
            }
        }
        items.add(item);
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for(int i=0; i < getItems().size(); i++){
            ShoppingCartItem item = getItems().get(i);
            totalPrice = (totalPrice+(item.getTotalPrice()));
        }
        return totalPrice;
    }

}
