package mpks.jabia.common;

import org.json.JSONObject;

public class ResponseBuilder {
    public static String buildLoginResponse(User user) {
        boolean success = user != null;
        JSONObject response = new JSONObject();
        response.put("type", "response");
        response.put("action", "login");
        response.put("status", success ? "success" : "error");
        if (success) {
            response.put("user", user.toJSON());
        }
        return response.toString();
    }
}
