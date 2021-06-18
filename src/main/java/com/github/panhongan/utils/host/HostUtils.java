package com.github.panhongan.utils.host;

import java.net.InetAddress;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

/**
 * @author lalalu plus
 * @since 2019.5.6
 */
public class HostUtils {

    private static final Supplier<String> HOST_NAME_SUPPLIER = HostUtils::getHostExec;

    private static final Supplier<String> IP_SUPPLIER = HostUtils::getIpExec;

    @Nonnull
    public static String getHostName() {
        return HOST_NAME_SUPPLIER.get();
    }

    @Nonnull
    public static String getIp() {
        return IP_SUPPLIER.get();
    }

    private static String getHostExec() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            return (host != null ? host.getHostName() : null);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static String getIpExec() {
        try {
            return InetAddress.getByName(getHostName()).getHostAddress();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
