package org.example.java_core.annotation.JsonTool;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectToJsonConverter {

    public String convertToJson(Object object) throws IllegalArgumentException, IllegalAccessException {

        if (checkIfSerializable(object)) {
            return getJsonString(object);
        }
        return "";
    }

    private boolean checkIfSerializable(Object object) {
        Class<?> clazz = object.getClass();
        boolean result = !(Objects.isNull(object) || !clazz.isAnnotationPresent(JsonSerializable.class));

        return result;
    }

    private String getJsonString(Object object) throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonElements = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(JsonElement.class)) {
                jsonElements.put(field.getName(), String.valueOf(field.get(object)));
            }
        }

        String json = jsonElements.entrySet().stream().map(
                entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"").collect(Collectors.joining(","));

        return "{" + json + "}";
    }
}
