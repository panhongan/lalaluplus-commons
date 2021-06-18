package com.github.panhongan.utils.json;

import com.github.panhongan.utils.datetime.DateUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author lalaluplus
 * @since 2021.2.25
 * @version 1.0
 */
@SuppressWarnings("ALL")
public class GsonUtils {

    public static Gson create() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public static Gson create(String dateFormatPatter) {
        return new GsonBuilder().setDateFormat(dateFormatPatter).create();
    }

    public static String getAsString(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsString();
    }

    public static int getAsInt(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsInt();
    }

    public static BigDecimal getAsBigDecimal(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsBigDecimal();
    }

    public static BigInteger getAsBigInteger(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsBigInteger();
    }

    public static boolean getAsBoolean(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsBoolean();
    }

    public static long getAsLong(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsLong();
    }

    public static short getAsShort(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsShort();
    }

    public static double getAsDouble(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsDouble();
    }

    public static float getAsFloat(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsFloat();
    }

    public static byte getAsByte(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsByte();
    }

    public static Date getAsDate(JsonObject jsonObject, String key, String datePattern) {
        return getAsDate(jsonObject, key, datePattern, ZoneId.systemDefault());
    }

    public static Date getAsDate(JsonObject jsonObject,
                                 String key,
                                 String datePattern,
                                 ZoneId zoneId) {
        return DateUtils.str2Date(getAsString(jsonObject, key), datePattern, zoneId);
    }

    public static LocalDateTime getAsDateTime(JsonObject jsonObject,
                                              String key,
                                              String dateTimePattern) {
        return DateUtils.str2LocalDateTime(getAsString(jsonObject, key), dateTimePattern);
    }
}
