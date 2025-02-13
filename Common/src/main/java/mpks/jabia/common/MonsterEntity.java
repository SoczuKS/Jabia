package mpks.jabia.common;

import org.json.JSONObject;

public class MonsterEntity extends Entity {
    private boolean isDefeated;
    private int level;
    private int intelligence;
    private int strength;
    private int agility;
    private int vitality;
    private int luck;
    private int hp;
    private int mana;
    private int defence;
    private int magicResistance;

    public MonsterEntity(int id, int x, int y, JSONObject jsonObject) {
        super(id, EntityType.MONSTER, x, y);
        this.isDefeated = false;
        this.level = 1;
        this.intelligence = 1;
        this.strength = 1;
        this.agility = 1;
        this.vitality = 1;
        this.luck = 1;
        this.hp = 1;
        this.mana = 1;
        this.defence = 1;
        this.magicResistance = 1;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = baseJSON();
        jsonObject.put("isDefeated", isDefeated);
        jsonObject.put("level", level);
        jsonObject.put("intelligence", intelligence);
        jsonObject.put("strength", strength);
        jsonObject.put("agility", agility);
        jsonObject.put("vitality", vitality);
        jsonObject.put("luck", luck);
        jsonObject.put("hp", hp);
        jsonObject.put("mana", mana);
        jsonObject.put("defence", defence);
        jsonObject.put("magicResistance", magicResistance);
        return jsonObject;
    }

    public void setDefeated(boolean b) {
        isDefeated = b;
    }
}
