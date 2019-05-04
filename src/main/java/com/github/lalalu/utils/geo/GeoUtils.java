package com.github.lalalu.utils.geo;

import com.github.davidmoten.geo.GeoHash;
import com.github.davidmoten.geo.LatLong;

/**
 * lalalu plus
 */

public class GeoUtils {

    private static final int MAX_LEN = 12;

    private static final int MIN_LEN = 4;

    private static final int MAX_LATITUDE = 90;

    /**
     * 给定经纬度和geohash编码的长度, 生成对应的编码
     *
     * @param lat    纬度
     * @param lng    经度
     * @param length geohash编码长度
     * @return 指定位数的geohash编码
     */
    public static String encode(double lat, double lng, Integer length) {
        if (length > MAX_LEN) {
            length = MAX_LEN;
        }
        if (length < 0) {
            length = MIN_LEN;
        }

        if (lat >= -MAX_LATITUDE && lat <= MAX_LATITUDE) {
            return GeoHash.encodeHash(lat, lng, length);
        }

        return "0";
    }

    public static LatLong decode(String geoHash) {
        return GeoHash.decodeHash(geoHash);
    }

}