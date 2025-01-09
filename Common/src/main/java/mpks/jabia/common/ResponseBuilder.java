package mpks.jabia.common;

import org.json.JSONObject;

public class ResponseBuilder {
    public static String buildLoginResponse(boolean success) {
        JSONObject response = new JSONObject();
        response.put("type", "response");
        response.put("action", "login");
        response.put("status", success ? "success" : "error");

        return response.toString();
    }
}
