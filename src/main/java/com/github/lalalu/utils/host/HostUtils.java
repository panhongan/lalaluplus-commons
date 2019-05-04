package com.github.lalalu.utils.host;

import javax.annotation.Nonnull;

import java.net.InetAddress;
import java.util.function.Supplier;

/**
 * lalalu plus
 */
public class HostUtils {

	private static Supplier<String> hostNameSupplier = () -> getHostExec();

	private static Supplier<String> ipSupplier = () -> getIpExec();

	@Nonnull
	public static String getHostName() {
		return hostNameSupplier.get();
	}

	@Nonnull
	public static String getIp() {
		return ipSupplier.get();
	}

	private static String getHostExec() {
		try {
			InetAddress host = InetAddress.getLocalHost();
			return (host != null ? host.getHostName() : null);
		} catch (Throwable t) {
			return null;
		}
	}

	private static String getIpExec() {
		try {
			return InetAddress.getByName(getHostName()).getHostAddress();
		} catch (Throwable t) {
			return null;
		}
	}

}
