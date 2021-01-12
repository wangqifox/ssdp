package io.github.wangqifox.ssdp.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author wangqi
 * @date 2021/1/11
 */
public class NetworkUtils {
    public static NetworkInterface getNetworkInterface(String name) {
        try {
            return NetworkInterface.getByName(name);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InetAddress getLocalAddress(NetworkInterface networkInterface) {
        InetAddress localAddress = null;
        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
        while (addresses.hasMoreElements()) {
            InetAddress address = addresses.nextElement();
            if (address instanceof Inet4Address) {
                localAddress = address;
            }
        }
        return localAddress;
    }

    public static InetAddress getLocalAddress(String name) {
        NetworkInterface ni = getNetworkInterface(name);
        if (ni == null) {
            return null;
        }
        return getLocalAddress(ni);
    }

    public static String getMacAddress(NetworkInterface networkInterface) {
        try {
            final byte[] mac = networkInterface.getHardwareAddress();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            // 把字符串所有小写字母改为大写成为正规的mac地址并返回
            return sb.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMacAddress(String name) {
        NetworkInterface ni = getNetworkInterface(name);
        if (ni == null) {
            return null;
        }
        return getMacAddress(ni);
    }
}
