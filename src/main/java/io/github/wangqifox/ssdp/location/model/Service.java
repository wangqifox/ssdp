package io.github.wangqifox.ssdp.location.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author wangqi
 * @date 2021/1/11
 */
@XStreamAlias("service")
public class Service {
    private String URLBase;
    private String serviceType;
    private String serviceId;
    private String controlURL;
    private String eventSubURL;
    private String SCPDURL;

    public String getURLBase() {
        return URLBase;
    }

    public void setURLBase(String URLBase) {
        this.URLBase = URLBase;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getControlURL() {
        return controlURL;
    }

    public void setControlURL(String controlURL) {
        this.controlURL = controlURL;
    }

    public String getEventSubURL() {
        return eventSubURL;
    }

    public void setEventSubURL(String eventSubURL) {
        this.eventSubURL = eventSubURL;
    }

    public String getSCPDURL() {
        return SCPDURL;
    }

    public void setSCPDURL(String SCPDURL) {
        this.SCPDURL = SCPDURL;
    }
}
