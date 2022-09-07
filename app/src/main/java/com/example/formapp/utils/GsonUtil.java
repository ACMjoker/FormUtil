package com.example.formapp.utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hjq.gson.factory.GsonFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtil<T> {

    /***
     * 对象转换成json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = GsonFactory.getSingletonGson();
        return gson.toJson(obj);
    }


    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String str, Type type) {
        Gson gson = GsonFactory.getSingletonGson();
        return gson.fromJson(str, type);
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = GsonFactory.getSingletonGson();
        return gson.fromJson(str, type);
    }


    /**
     * json字符串转列表
     */
    public static <T> List<T> jsonToList(String str, Class<T> type) {
        List<T> list = new ArrayList<>();
        JsonArray array = new JsonParser().parse(str).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(GsonFactory.getSingletonGson().fromJson(elem, type));
        }
        return list;
    }


    /**
     * json字符串转成列表
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    /*public static <T> List<T>  jsonToList(String str, Class<T[]>  type){
        Gson gson = GsonFactory.getSingletonGson();
        T[] arr = gson.fromJson(str, type);
        return Arrays.asList(arr);
    }*/

    /**
     * 转成list中有map的
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String str) {
        List<Map<String, T>> list = null;
        Gson gson = GsonFactory.getSingletonGson();
        list = gson.fromJson(str,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());

        return list;
    }

    /**
     * 转成map的
     */
    public static <T> Map<String, T> jsonToMaps(String str) {
        Map<String, T> map = null;
        Gson gson = GsonFactory.getSingletonGson();
        map = gson.fromJson(str, new TypeToken<Map<String, T>>() {
        }.getType());

        return map;
    }


}