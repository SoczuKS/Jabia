package mpks.jabia.common;

import org.json.JSONObject;

public class Item {
    private final String name;
    private final String textureName;

    public Item(String name, String textureName) {
        this.name = name;
        this.textureName = textureName;
    }

    public Item(JSONObject json) {
        name = json.getString("name");
        textureName = json.getString("textureName");
    }

    public String getTextureName() {
        return textureName;
    }

    public String getName() {
        return name;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("textureName", textureName);
        return json;
    }
}
