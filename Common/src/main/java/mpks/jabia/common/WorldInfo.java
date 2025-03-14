package mpks.jabia.common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WorldInfo {
    private final List<Entity> entities;
    private int spawnPointX = 0;
    private int spawnPointY = 0;

    public WorldInfo() throws IOException {
        this.entities = new ArrayList<>();
        loadMapInfo();
    }

    public WorldInfo(JSONObject worldInfo) {
        this.entities = new ArrayList<>();
        var entitiesJson = worldInfo.getJSONArray("entities");
        for (int i = 0; i < entitiesJson.length(); i++) {
            entities.add(Entity.createEntity(entitiesJson.getJSONObject(i)));
        }
        var spawnPoint = worldInfo.getJSONObject("spawnPoint");
        spawnPointX = spawnPoint.getInt("x");
        spawnPointY = spawnPoint.getInt("y");
    }

    public JSONObject toJSON() {
        JSONObject worldInfo = new JSONObject();
        JSONArray entitiesJson = new JSONArray();
        for (Entity entity : entities) {
            entitiesJson.put(entity.toJSON());
        }
        worldInfo.put("entities", entitiesJson);
        worldInfo.put("spawnPoint", new JSONObject().put("x", spawnPointX).put("y", spawnPointY));
        return worldInfo;
    }

    public int getSpawnPointX() {
        return spawnPointX;
    }

    public int getSpawnPointY() {
        return spawnPointY;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getMonsters() {
        return entities.stream().filter(entity -> entity.getEntityType() == EntityType.MONSTER).toList();
    }

    public List<Entity> getChests() {
        return entities.stream().filter(entity -> entity.getEntityType() == EntityType.CHEST).toList();
    }

    public List<Entity> getNPCs() {
        return entities.stream().filter(entity -> entity.getEntityType() == EntityType.TRADER || entity.getEntityType() == EntityType.NPC).toList();
    }

    private String readWorldInfoFile() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("map_info.json");
        assert inputStream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append(System.lineSeparator());
        }
        return content.toString();
    }

    private void loadMapInfo() throws IOException {
        String content = readWorldInfoFile();
        JSONObject mapInfo = new JSONObject(content);

        var traders = mapInfo.getJSONArray("trader");
        for (int i = 0; i < traders.length(); i++) {
            entities.add(Entity.createEntity(EntityType.TRADER, traders.getJSONObject(i)));
        }

        var npc = mapInfo.getJSONArray("npc");
        for (int i = 0; i < npc.length(); i++) {
            entities.add(Entity.createEntity(EntityType.NPC, npc.getJSONObject(i)));
        }

        var enemies = mapInfo.getJSONArray("enemy");
        for (int i = 0; i < enemies.length(); i++) {
            entities.add(Entity.createEntity(EntityType.MONSTER, enemies.getJSONObject(i)));
        }

        var chests = mapInfo.getJSONArray("chest");
        for (int i = 0; i < chests.length(); i++) {
            entities.add(Entity.createEntity(EntityType.CHEST, chests.getJSONObject(i)));
        }

        var spawnPoint = mapInfo.getJSONObject("spawnPoint");
        spawnPointX = spawnPoint.getInt("x");
        spawnPointY = spawnPoint.getInt("y");
    }

    public void setMonsterToDefeated(int id){
        var monsters = getMonsters();
        for(Entity entity : monsters){
            if(entity.getId() == id){
                MonsterEntity monster = (MonsterEntity) entity;
                monster.setDefeated(true);
            }
        }
    }
}
