package org.example.utils

import org.json.JSONObject

open class JsonUtils {
    companion object {
        @JvmStatic
        val successDeleteJO: JSONObject = JSONObject().put("message", "Deleted successfully")
    }
}