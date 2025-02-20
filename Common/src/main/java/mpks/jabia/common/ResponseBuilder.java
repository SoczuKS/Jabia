package mpks.jabia.common;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseBuilder {
    public static String buildLoginResponse(User user, WorldInfo worldInfo, JSONArray currentUsers) {
        boolean success = user != null;
        JSONObject response = new JSONObject();
        response.put("type", "response");
        response.put("action", "login");
        response.put("status", success ? "success" : "error");
        if (success) {
            response.put("user", user.toJSON());
            response.put("worldInfo", worldInfo.toJSON());
            response.put("currentUsers", currentUsers);
        }
        return response.toString();
    }
}
