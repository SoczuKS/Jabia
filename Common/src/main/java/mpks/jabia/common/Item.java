package mpks.jabia.common;

import org.json.JSONObject;

public class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public Item(JSONObject json) {
        name = json.getString("name");
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }
}
