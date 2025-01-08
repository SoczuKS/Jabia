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
}
