package io.github.wangqifox.ssdp.model;

/**
 * @author wangqi
 * @date 2021/1/8
 */
public class DiscoveryRequest extends AbstractModel {
    private final static String HEAD = "M-SEARCH * HTTP/1.1";

    private String man = "ssdp:discover";
    private Integer mx = 3;
    private String st = "ssdp:all";

    public DiscoveryRequest() {
        super(HEAD);
    }

    public String getMan() {
        return man;
    }

    public void setMan(String man) {
        this.man = man;
    }

    public Integer getMx() {
        return mx;
    }

    public void setMx(Integer mx) {
        this.mx = mx;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String toContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(HEAD).append(LINE_END);
        appendField(sb, "HOST", host);
        appendField(sb, "MAN", "\"" + man + "\"");
        appendField(sb, "MX", String.valueOf(mx));
        appendField(sb, "ST", st);
        sb.append(LINE_END);
        return sb.toString();
    }

    public static class Parser extends AbstractParser<DiscoveryRequest> {
        public Parser() {
            super(DiscoveryRequest.class);
        }

        @Override
        protected void parseLine(DiscoveryRequest discoveryRequest, String key, String value) {
            switch (key) {
                case "HOST":
                    discoveryRequest.setHost(value);
                    break;
                case "MAN":
                    discoveryRequest.setMan(value);
                    break;
                case "MX":
                    discoveryRequest.setMx(Integer.valueOf(value));
                    break;
                case "ST":
                    discoveryRequest.setSt(value);
                    break;
                default:
                    break;
            }
        }
    }
}
