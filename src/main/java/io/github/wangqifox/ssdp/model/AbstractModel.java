package io.github.wangqifox.ssdp.model;

import static io.github.wangqifox.ssdp.config.SsdpConfig.ADDRESS;
import static io.github.wangqifox.ssdp.config.SsdpConfig.PORT;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author wangqi
 * @date 2021/1/11
 */
public class AbstractModel {
    public static final String LINE_END = "\r\n";
    public static final byte[] CRLF = LINE_END.getBytes(UTF_8);
    public static final String SEPARATOR = ":";
    public String host = ADDRESS + ":" + PORT;

    public final String HEAD;

    public AbstractModel(String head) {
        this.HEAD = head;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    protected void appendField(StringBuilder sb, String key, String value) {
        if (value != null) {
            sb.append(key).append(SEPARATOR).append(" ").append(value).append(LINE_END);
        }
    }

}
