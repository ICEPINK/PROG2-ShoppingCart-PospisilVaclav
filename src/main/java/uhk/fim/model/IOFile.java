package uhk.fim.model;

import com.google.gson.Gson;

import java.io.*;


public class IOFile {
    public static void save(ShoppingCart shoppingCart, File file){
        if(file.getName().endsWith("json")) {
            Gson gson = new Gson();
            try (FileWriter fileWriter = new FileWriter(file.getPath())) {
                String obj = gson.toJson(shoppingCart);
                fileWriter.write(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.getName().endsWith("csv")){
            try (BufferedWriter bfw = new BufferedWriter(new FileWriter(file.getPath()))){
                for (ShoppingCartItem item : shoppingCart.getItems()){
                    bfw.write(item.getName()+";"+item.getPricePerPiece()+";"+item.getPieces());
                    bfw.newLine();
                }
            }
            catch (IOException e){
            }
        }
    }

    public static ShoppingCart load(File file){
        ShoppingCart shoppingCart = new ShoppingCart();
        if(file.getName().endsWith("json")) {
            Gson gson = new Gson();
            try (FileReader fileReader = new FileReader(file.getPath())) {
                shoppingCart = gson.fromJson(fileReader, ShoppingCart.class);
            } catch (IOException e) {
                e.printStackTrace();
                shoppingCart = null;
            }
            return shoppingCart;
        } else if (file.getName().endsWith("csv")){
            try (BufferedReader bfr = new BufferedReader(new FileReader(file.getPath()))){
                String node = "";
                while ((node = bfr.readLine()) != null){
                    String[] item = node.split(";");
                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem(item[0],Double.parseDouble(item[1]),Integer.parseInt(item[2]));
                    shoppingCart.addItem(shoppingCartItem);
                }
                return shoppingCart;
            }
            catch (IOException e){
            }
        }
        return null;
    }
}
