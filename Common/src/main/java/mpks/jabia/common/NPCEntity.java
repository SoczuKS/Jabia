package mpks.jabia.common;

import org.json.JSONObject;

public class NPCEntity extends Entity {
    public NPCEntity(int id, int x, int y, JSONObject jsonObject) {
        super(id, EntityType.NPC, x, y);
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = baseJSON();
        return jsonObject;
    }
}
