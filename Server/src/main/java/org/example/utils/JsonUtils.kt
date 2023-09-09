package org.example.utils

import org.json.JSONObject

object JsonUtils {
    @JvmField
    val successDeleteJO: JSONObject = JSONObject()

    init {
        successDeleteJO.put("message", "Deleted successfully")
    }
}