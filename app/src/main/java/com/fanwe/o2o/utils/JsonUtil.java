package com.fanwe.o2o.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonUtil {

    private JsonUtil() {
    }

    /**
     * json转对象
     *
     * @param json  json字符串
     * @param clazz 目标对象的class类型
     * @return 目标对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    /**
     * json转对象
     *
     * @param json json字符串
     * @param type 目标对象的class类型
     * @return 目标对象
     */
    public static <T> T fromJson(JsonElement json, Type type) {
        return new Gson().fromJson(json, type);
    }

    /**
     * 对象转json
     *
     * @param object 待转为json的对象
     * @return json字符串
     */
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }


    public static <T> ArrayList<T> jsonToArrayList(JsonElement json, Class<T> clazz) {

        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();

        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();

        for (JsonObject jsonObject : jsonObjects) {
            if (jsonObject.isJsonArray()) {

            } else
                arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

}
