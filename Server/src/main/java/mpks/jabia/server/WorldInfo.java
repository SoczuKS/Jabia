package mpks.jabia.server;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static mpks.jabia.server.Entity.createEntity;

public class WorldInfo {
    private Logger logger;
    private List<Entity> entities;
    public WorldInfo(Logger logger){
        this.logger = logger;
    }

    JSONObject loadMapInfo() throws IOException{
        String filePath = "Server/src/main/resources/map_info.json";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject mapInfo = new JSONObject(content);


        JSONObject worldInfo = new JSONObject();
        int idCounter = 1;
        var posX=0;
        var posY=0;
        var tnpc = mapInfo.getJSONArray("tnpc");
        for (int i = 0; i < tnpc.length(); i++) {
            posX=tnpc.getJSONObject(i).getInt("x");
            posY=tnpc.getJSONObject(i).getInt("y");
            entities.add(createEntity(idCounter, "trader", posX, posY));

            worldInfo.put("id", idCounter++);
            worldInfo.put("EntityType", "trader");
            worldInfo.put("x", posX);
            worldInfo.put("y", posY);
        }

        var npc = mapInfo.getJSONArray("npc");
        for (int i = 0; i < npc.length(); i++) {
            posX=tnpc.getJSONObject(i).getInt("x");
            posY=tnpc.getJSONObject(i).getInt("y");
            entities.add(createEntity(idCounter, "npc", posX, posY));

            worldInfo.put("id", idCounter++);
            worldInfo.put("EntityType", "npc");
            worldInfo.put("x", posX);
            worldInfo.put("y", posY);
        }

        var enemy = mapInfo.getJSONArray("enemy");
        for (int i = 0; i < enemy.length(); i++) {
            posX=tnpc.getJSONObject(i).getInt("x");
            posY=tnpc.getJSONObject(i).getInt("y");
            entities.add(createEntity(idCounter, "enemy", posX, posY));

            worldInfo.put("id", idCounter++);
            worldInfo.put("EntityType", "enemy");
            worldInfo.put("x", posX);
            worldInfo.put("y", posY);
            worldInfo.put("level", 1);
            worldInfo.put("intelligence", 1);
            worldInfo.put("strength", 1);
            worldInfo.put("agility", 1);
            worldInfo.put("vitality", 1);
            worldInfo.put("luck", 1);
            worldInfo.put("hp", 1);
            worldInfo.put("mana", 1);
            worldInfo.put("defence", 1);
            worldInfo.put("magicResistance", 1);
        }

        var chest = mapInfo.getJSONArray("chest");
        for (int i = 0; i < chest.length(); i++) {
            posX=tnpc.getJSONObject(i).getInt("x");
            posY=tnpc.getJSONObject(i).getInt("y");
            entities.add(createEntity(idCounter, "chest", posX, posY));

            worldInfo.put("id", idCounter++);
            worldInfo.put("EntityType", "chest");
            worldInfo.put("x", posX);
            worldInfo.put("y", posY);
            worldInfo.put("isOpened", false);
        }


        return worldInfo;
    }

    public List<Entity> getEntities(){
        return entities;
    }

    public void setEntities(List<Entity> entities){
        this.entities = entities;
    }
}
