package io.github.wangqifox.ssdp.location.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author wangqi
 * @date 2021/1/11
 */
@XStreamAlias("specVersion")
public class SpecVersion {
    private int major = 1;
    private int minor = 0;

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }
}
