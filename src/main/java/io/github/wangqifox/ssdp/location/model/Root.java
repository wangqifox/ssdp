package io.github.wangqifox.ssdp.location.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author wangqi
 * @date 2021/1/11
 */
@XStreamAlias("root")
public class Root {
    @XStreamAsAttribute
    private String xmlns = "urn:schemas-upnp-org:device-1-0";
    private SpecVersion specVersion;
    private Device device;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public SpecVersion getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(SpecVersion specVersion) {
        this.specVersion = specVersion;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
