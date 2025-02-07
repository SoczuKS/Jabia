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

    public WorldInfo() throws IOException {
        this.entities = new ArrayList<>();
        loadMapInfo();
    }

    public JSONObject toJSON() {
        JSONObject worldInfo = new JSONObject();
        JSONArray entitiesJson = new JSONArray();
        for (Entity entity : entities) {
            entitiesJson.put(entity.toJSON());
        }
        worldInfo.put("entities", entitiesJson);
        return worldInfo;
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
    }
}
