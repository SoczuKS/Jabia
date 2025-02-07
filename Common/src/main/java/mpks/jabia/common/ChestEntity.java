package mpks.jabia.common;

import org.json.JSONObject;

public class ChestEntity extends Entity {
    private boolean isOpened;

    public ChestEntity(int id, int x, int y, JSONObject jsonObject) {
        super(id, EntityType.CHEST, x, y);
        this.isOpened = false;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = baseJSON();
        jsonObject.put("isOpened", isOpened);
        return jsonObject;
    }
}
