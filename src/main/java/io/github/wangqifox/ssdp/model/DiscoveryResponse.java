package io.github.wangqifox.ssdp.model;

import io.github.wangqifox.ssdp.utils.TimeUtils;

import java.time.LocalDateTime;

/**
 * @author wangqi
 * @date 2021/1/8
 */
public class DiscoveryResponse extends AbstractModel {
    private final static String HEAD = "HTTP/1.1 200 OK";

    private String cacheControl;
    private LocalDateTime date;
    private String ext;
    private String location;
    private String server;
    private String st;
    private String usn;

    public DiscoveryResponse() {
        super(HEAD);
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String toContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(HEAD).append(LINE_END);
        appendField(sb, "Cache-Control", cacheControl);
        appendField(sb, "Date", TimeUtils.format(date));
        appendField(sb, "Ext", ext);
        appendField(sb, "Location", location);
        appendField(sb, "Server", server);
        appendField(sb, "ST", st);
        appendField(sb, "USN", usn);
        sb.append(LINE_END);
        return sb.toString();
    }

    public static class Parser extends AbstractParser<DiscoveryResponse> {
        public Parser() {
            super(DiscoveryResponse.class);
        }

        @Override
        protected void parseLine(DiscoveryResponse discoveryResponse, String key, String value) {
            switch (key) {
                case "CACHE-CONTROL":
                    discoveryResponse.setCacheControl(value);
                    break;
                case "DATE":
                    discoveryResponse.setDate(TimeUtils.parse(value));
                    break;
                case "EXT":
                    if (value != null) {
                        discoveryResponse.setExt(value);
                    } else {
                        discoveryResponse.setExt("");
                    }
                    break;
                case "LOCATION":
                    discoveryResponse.setLocation(value);
                    break;
                case "SERVER":
                    discoveryResponse.setServer(value);
                    break;
                case "ST":
                    discoveryResponse.setSt(value);
                    break;
                case "USN":
                    discoveryResponse.setUsn(value);
                    break;
                default:
                    break;
            }
        }
    }
}
