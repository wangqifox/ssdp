package io.github.wangqifox.ssdp.model;

import io.github.wangqifox.ssdp.utils.TimeUtils;

import java.time.LocalDateTime;

/**
 * @author wangqi
 * @date 2021/1/11
 */
public class NotifyAlive extends AbstractModel {
    private static final String HEAD = "NOTIFY * HTTP/1.1";

    private static final String nts = "ssdp:alive";
    private String cacheControl;
    private LocalDateTime date;
    private String location;
    private String server;
    private String nt;
    private String usn;

    public NotifyAlive() {
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

    public String getNt() {
        return nt;
    }

    public void setNt(String nt) {
        this.nt = nt;
    }

    public String getNts() {
        return nts;
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
        appendField(sb, "HOST", host);
        appendField(sb, "CACHE-CONTROL", cacheControl);
        appendField(sb, "DATE", TimeUtils.format(LocalDateTime.now()));
        appendField(sb, "LOCATION", location);
        appendField(sb, "SERVER", server);
        appendField(sb, "NT", nt);
        appendField(sb, "NTS", nts);
        appendField(sb, "USN", usn);
        sb.append(LINE_END);
        return sb.toString();
    }

    public static class Parser extends AbstractParser<NotifyAlive> {
        public Parser() {
            super(NotifyAlive.class);
        }

        @Override
        protected void parseLine(NotifyAlive notifyAlive, String key, String value) {
            switch (key) {
                case "HOST":
                    notifyAlive.setHost(value);
                    break;
                case "CACHE-CONTROL":
                    notifyAlive.setCacheControl(value);
                    break;
                case "DATE":
                    notifyAlive.setDate(TimeUtils.parse(value));
                    break;
                case "LOCATION":
                    notifyAlive.setLocation(value);
                    break;
                case "SERVER":
                    notifyAlive.setServer(value);
                    break;
                case "NT":
                    notifyAlive.setNt(value);
                    break;
                case "USN":
                    notifyAlive.setUsn(value);
                    break;
                default:
                    break;
            }
        }
    }
}
