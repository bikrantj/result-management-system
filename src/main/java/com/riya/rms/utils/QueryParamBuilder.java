package com.riya.rms.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * Query param builder class to build query parameters for URLs.
 * Input:
 * <pre>
 * Map<String, Object> params = new HashMap<>("name", "John Doe", "age", 30, "city", "New York");
 * QueryParamBuilder().build(params)
 * </pre>
 * Output: * "?name=John+Doe&age=30&city=New+York"
 */

public class QueryParamBuilder {

    public String build(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("?");
        boolean first = true;

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (!first) {
                sb.append("&");
            }
            first = false;

            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String value = entry.getValue() == null
                    ? ""
                    : URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8);

            sb.append(key).append("=").append(value);
        }

        return sb.toString();
    }
}