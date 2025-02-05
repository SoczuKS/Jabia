package mpks.jabia.common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

public class Inventory {
    private int money;
    private Vector<Item> items;

    public Inventory() {
        money = 0;
        items = new Vector<>();
    }

    public Inventory(JSONObject json) {
        money = json.getInt("money");
        items = new Vector<>();
        JSONArray itemsArray = json.getJSONArray("items");
        for (int i = 0; i < itemsArray.length(); i++) {
            items.add(new Item(itemsArray.getJSONObject(i)));
        }
    }

    public JSONObject toJSON() {
        JSONArray itemsArray = new JSONArray();
        for (Item item : items) {
            itemsArray.put(item.toJSON());
        }
        return new JSONObject().put("money", money).put("items", itemsArray);
    }
}
