package mpks.jabia.common;

import org.json.JSONObject;

public class TraderNPCEntity extends Entity {
    public TraderNPCEntity(int id, int x, int y, JSONObject jsonObject) {
        super(id, EntityType.TRADER, x, y);
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = baseJSON();
        return jsonObject;
    }
}
