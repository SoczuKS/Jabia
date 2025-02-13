package mpks.jabia.common;

import org.json.JSONObject;

public class RequestBuilder {
    public static String buildLoginRequest(String username, String password) {
        JSONObject request = new JSONObject();
        request.put("type", "request");
        request.put("action", "login");
        request.put("username", username);
        request.put("password", password);

        return request.toString();
    }

    public static String buildPlayerJoinRequest(String username, int x, int y) {
        JSONObject request = new JSONObject();
        request.put("type", "request");
        request.put("action", "playerJoin");
        request.put("username", username);
        request.put("x", x);
        request.put("y", y);

        return request.toString();
    }

    public static String buildMoveRequest(String username, int x, int y) {
        JSONObject request = new JSONObject();
        request.put("type", "request");
        request.put("action", "move");
        request.put("username", username);
        request.put("x", x);
        request.put("y", y);

        return request.toString();
    }

    public static String buildAttackRequest(String username, int id) {
        JSONObject request = new JSONObject();
        request.put("type", "request");
        request.put("action", "attack");
        request.put("username", username);
        request.put("id", id);

        return request.toString();
    }

    public static String buildMonsterDefeatedRequest(int id) {
        JSONObject request = new JSONObject();
        request.put("type", "request");
        request.put("action", "monsterDefeated");
        request.put("id", id);

        return request.toString();
    }
}
