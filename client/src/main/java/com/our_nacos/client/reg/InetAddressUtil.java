package com.our_nacos.client.reg;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @Author: 乐哥
 * @Date: 2023/5/9
 * @Time: 14:45
 * @Description:  获取ip地址的工具类
 */
public class InetAddressUtil {
    /** 获取主机地址 */
    public static String getHostIp(){

        String realIp = null;

        try {
            InetAddress address = InetAddress.getLocalHost();

            // 如果是回环网卡地址, 则获取ipv4 地址
            if (address.isLoopbackAddress()) {
                address = getInet4Address();
            }

            realIp = address.getHostAddress();

            System.out.println("获取主机ip地址成功, 主机ip地址:{}"+ address);
            return address.getHostAddress();
        } catch (Exception e) {
            System.out.println("获取主机ip地址异常"+e.getMessage());
        }

        return realIp;
    }

    /** 获取IPV4网络配置 */
    private static InetAddress getInet4Address() throws SocketException {
        // 获取所有网卡信息
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) networkInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = (InetAddress) addresses.nextElement();
                if (ip instanceof Inet4Address) {
                    return ip;
                }
            }
        }
        return null;
    }

}
