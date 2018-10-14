package com.fanwe.o2o.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 保存单条字段所需类
 */

public class SharePreferencesUtil {

    private static final String MAIN_INIT_VALUE = "lottery_yjf";

    /**
     * 存储String类型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void addString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        return context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).getString(key, defValue);
    }

    /**
     * 存储int类型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void addInteger(Context context, String key, int value) {
        context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    public static int getInteger(Context context, String key, int defValue) {
        return context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).getInt(key, defValue);
    }

    /**
     * 存储布尔类型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void addBoolean(Context context, String key, boolean value) {
        context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

    /**
     * 清除
     *
     * @param context
     */
    public static void deleteData(Context context) {
        context.getSharedPreferences(MAIN_INIT_VALUE, Context.MODE_PRIVATE).edit().clear().apply();
    }

}
