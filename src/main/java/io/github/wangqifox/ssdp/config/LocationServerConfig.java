package io.github.wangqifox.ssdp.config;

import io.github.wangqifox.ssdp.location.model.Device;
import io.github.wangqifox.ssdp.location.model.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangqi
 * @date 2021/1/12
 */
public class LocationServerConfig {
    private final int serverPort;
    private Device device;
    private final List<Service> serviceList = new ArrayList<>();

    private static final int DEFAULT_LOCATION_SERVER_PORT = 49152;

    public LocationServerConfig() {
        this(DEFAULT_LOCATION_SERVER_PORT);
    }

    public LocationServerConfig(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void addService(Service service) {
        serviceList.add(service);
    }
}
