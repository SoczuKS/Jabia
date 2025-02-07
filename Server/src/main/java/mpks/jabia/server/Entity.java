package mpks.jabia.server;

public class Entity {
    private int id;
    private String entityType;
    private Boolean isDefeated;
    private Boolean isOpened;
    private String entityName;
    private int x;
    private int y;
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


    public Entity(int id, String entityType, int x, int y, int level, int intelligence,
                  int strength, int agility, int vitality, int luck, int hp, int mana,
                  int defence, int magicResistance, Boolean isDefeated, Boolean isOpened, String entityName) {
        this.id = id;
        this.entityType = entityType;
        this.x = x;
        this.y = y;
        this.level = level;
        this.intelligence = intelligence;
        this.strength = strength;
        this.agility = agility;
        this.vitality = vitality;
        this.luck = luck;
        this.hp = hp;
        this.mana = mana;
        this.defence = defence;
        this.magicResistance = magicResistance;
        this.isDefeated = isDefeated;
        this.isOpened = isOpened;
        this.entityName = entityName;
    }

    public static Entity createEntity(int id, String entityType, int x, int y) {
        int defaultLevel = 0;
        int defaultIntelligence = 0;
        int defaultStrength = 0;
        int defaultAgility = 0;
        int defaultVitality = 0;
        int defaultLuck = 0;
        int defaultHp = 0;
        int defaultMana = 0;
        int defaultDefence = 0;
        int defaultMagicResistance = 0;
        boolean defaultIsDefeated = false;
        boolean defaultIsOpened = false;
        String defaultEntityName = "";

        return new Entity(id, entityType, x, y, defaultLevel, defaultIntelligence, defaultStrength,
                defaultAgility, defaultVitality, defaultLuck, defaultHp, defaultMana, defaultDefence,
                defaultMagicResistance, defaultIsDefeated, defaultIsOpened, defaultEntityName);
    }

    public int getId() {
        return id;
    }

    public String getEntityType() {
        return entityType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLevel() {
        return level;
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

    public int getHp() {
        return hp;
    }

    public int getMana() {
        return mana;
    }

    public int getDefence() {
        return defence;
    }

    public int getMagicResistance() {
        return magicResistance;
    }

    public Boolean getIsDefeated() {
        return isDefeated;
    }

    public Boolean getIsOpened() {
        return isOpened;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setMagicResistance(int magicResistance) {
        this.magicResistance = magicResistance;
    }

    public void setIsDefeated(Boolean isDefeated) {
        this.isDefeated = isDefeated;
    }

    public void setIsOpened(Boolean isOpened) {
        this.isOpened = isOpened;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
