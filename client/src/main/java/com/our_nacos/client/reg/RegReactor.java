package com.our_nacos.client.reg;

import java.util.Map;

public interface RegReactor {
    RegReactor addReg(NacosDiscoveryProperties nacosDiscoveryProperties);
    RegReactor deleteReg(NacosDiscoveryProperties nacosDiscoveryProperties);
    RegReactor setThreadCount(int threadCount);
}
