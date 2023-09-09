package org.example.utils;

import org.json.JSONObject;

public class JsonUtils {
    public static final JSONObject successDeleteJO;

    static {
        successDeleteJO = new JSONObject();
        successDeleteJO.put("message", "Deleted successfully");
    }
}