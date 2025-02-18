package mpks.jabia.common;

import org.json.JSONObject;

public class User {
    public static final int DEFAULT_LEVEL = 1;
    public static final int DEFAULT_INTELLIGENCE = 1;
    public static final int DEFAULT_STRENGTH = 1;
    public static final int DEFAULT_AGILITY = 1;
    public static final int DEFAULT_VITALITY = 1;
    public static final int DEFAULT_LUCK = 1;
    public static final int DEFAULT_FREE_SKILL_POINTS = 0;
    public static final int DEFAULT_HP = 100;
    public static final int DEFAULT_MANA = 100;
    public static final int DEFAULT_DEFENCE = 0;
    public static final int DEFAULT_MAGIC_RESISTANCE = 0;
    public static final Inventory DEFAULT_INVENTORY = new Inventory();

    private final int id;
    private final String username;
    private final String password;
    private int level;
    private int intelligence; //  spells power and mana
    private int strength; // melee weapon damage
    private int agility; // distance weapon and dodge
    private int vitality; // hp
    private int luck; // chance of critical hit
    private int freeSkillPoints;
    private int hp;
    private int mana;
    private int defence; // raise with level + items
    private int magicResistance; // raise with level + items
    private Inventory inventory;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = DEFAULT_LEVEL;
        this.intelligence = DEFAULT_INTELLIGENCE;
        this.strength = DEFAULT_STRENGTH;
        this.agility = DEFAULT_AGILITY;
        this.vitality = DEFAULT_VITALITY;
        this.luck = DEFAULT_LUCK;
        this.freeSkillPoints = DEFAULT_FREE_SKILL_POINTS;
        this.hp = DEFAULT_HP;
        this.mana = DEFAULT_MANA;
        this.defence = DEFAULT_DEFENCE;
        this.magicResistance = DEFAULT_MAGIC_RESISTANCE;
        this.inventory = DEFAULT_INVENTORY;
    }

    public User(int id, String username, String password, int level, int intelligence, int strength, int agility, int vitality, int luck, int freeSkillPoints, int hp, int mana, int defence, int magicResistance, Inventory inventory) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
        this.intelligence = intelligence;
        this.strength = strength;
        this.agility = agility;
        this.vitality = vitality;
        this.luck = luck;
        this.freeSkillPoints = freeSkillPoints;
        this.hp = hp;
        this.mana = mana;
        this.defence = defence;
        this.magicResistance = magicResistance;
        this.inventory = inventory;
    }

    public User(JSONObject json) {
        this.id = json.getInt("id");
        this.username = json.getString("username");
        this.password = "";
        this.level = json.getInt("level");
        this.intelligence = json.getInt("intelligence");
        this.strength = json.getInt("strength");
        this.agility = json.getInt("agility");
        this.vitality = json.getInt("vitality");
        this.luck = json.getInt("luck");
        this.freeSkillPoints = json.getInt("freeSkillPoints");
        this.hp = json.getInt("hp");
        this.mana = json.getInt("mana");
        this.defence = json.getInt("defence");
        this.magicResistance = json.getInt("magicResistance");
        this.inventory = new Inventory(json.getJSONObject("inventory"));
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("id", id)
                .put("username", username)
                .put("level", level)
                .put("intelligence", intelligence)
                .put("strength", strength)
                .put("agility", agility)
                .put("vitality", vitality)
                .put("luck", luck)
                .put("freeSkillPoints", freeSkillPoints)
                .put("hp", hp)
                .put("mana", mana)
                .put("defence", defence)
                .put("magicResistance", magicResistance)
                .put("inventory", inventory.toJSON());
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getVitality() {
        return vitality;
    }

    public int getLuck() {
        return luck;
    }

    public int getDefence() {
        return defence;
    }

    public int getMagicResistance() {
        return magicResistance;
    }
}
