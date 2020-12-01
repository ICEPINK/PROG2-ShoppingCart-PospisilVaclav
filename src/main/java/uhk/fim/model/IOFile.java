package uhk.fim.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class IOFile {
    //csv

    //json
    public static void saveJson(ShoppingCart shoppingCart, String path){
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(path);){
            String obj = gson.toJson(shoppingCart);
            fileWriter.write(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ShoppingCart loadJson(String path){
        Gson gson = new Gson();
        ShoppingCart shoppingCart;
        try (FileReader fileReader = new FileReader(path)){
            shoppingCart = gson.fromJson(fileReader,ShoppingCart.class);
        } catch (IOException e) {
            e.printStackTrace();
            shoppingCart = null;
        }
        return shoppingCart;
    }
}
