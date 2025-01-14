package mpks.jabia.common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

public class Inventory {
    private Vector<Item> items;

    public Inventory(){
        items = new Vector<>();
    }

    public Inventory(JSONObject json) {
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
        return new JSONObject().put("items", itemsArray);
    }
}
