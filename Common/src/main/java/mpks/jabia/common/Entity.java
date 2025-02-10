package mpks.jabia.common;

import org.json.JSONObject;

public abstract class Entity {
    private static int nextId = 0;
    int id;
    EntityType entityType;
    int x;
    int y;

    protected Entity(int id, EntityType entityType, int x, int y) {
        this.id = id;
        this.entityType = entityType;
        this.x = x;
        this.y = y;
    }

    public static Entity createEntity(EntityType entityType, JSONObject jsonObject) {
        int posX = jsonObject.getInt("x");
        int posY = jsonObject.getInt("y");
        return switch (entityType) {
            case TRADER -> new TraderNPCEntity(nextId++, posX, posY, jsonObject);
            case MONSTER -> new MonsterEntity(nextId++, posX, posY, jsonObject);
            case NPC -> new NPCEntity(nextId++, posX, posY, jsonObject);
            case CHEST -> new ChestEntity(nextId++, posX, posY, jsonObject);
        };
    }

    public static Entity createEntity(JSONObject jsonObject) {
        EntityType entityType = EntityType.valueOf(jsonObject.getString("entityType"));
        return createEntity(entityType, jsonObject);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected JSONObject baseJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entityType", entityType);
        jsonObject.put("id", id);
        jsonObject.put("x", x);
        jsonObject.put("y", y);
        return jsonObject;
    }

    public abstract JSONObject toJSON();
}
