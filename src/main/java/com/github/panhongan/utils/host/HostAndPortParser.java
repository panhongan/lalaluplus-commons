package com.github.panhongan.utils.host;

import com.google.common.net.HostAndPort;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author lalalu plus
 * @since 2017.5.2
 */

public class HostAndPortParser {

    /**
     * @param uri ip:port,ip:port,ip:port
     * @return host and port list
     */
    public static List<HostAndPort> parse(String uri) {
        try {
            String[] arr = uri.split("[,]");
            if (ArrayUtils.isNotEmpty(arr)) {
                List<HostAndPort> list = new ArrayList<>();
                for (String ele : arr) {
                    list.add(HostAndPort.fromString(ele));
                }
                return list;
            } else {
                throw new RuntimeException("Invalid URI : " + uri);
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid URI : " + uri, e);
        }
    }
}
