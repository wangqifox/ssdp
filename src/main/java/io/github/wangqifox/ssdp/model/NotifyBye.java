package io.github.wangqifox.ssdp.model;

/**
 * @author wangqi
 * @date 2021/1/11
 */
public class NotifyBye extends AbstractModel {
    private static final String HEAD = "NOTIFY * HTTP/1.1";

    private final String nts = "ssdp:byebye";
    private String nt;
    private String usn;

    public NotifyBye() {
        super(HEAD);
    }

    public String getNts() {
        return nts;
    }

    public String getNt() {
        return nt;
    }

    public void setNt(String nt) {
        this.nt = nt;
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
        appendField(sb, "NT", nt);
        appendField(sb, "NTS", nts);
        appendField(sb, "USN", usn);
        sb.append(LINE_END);
        return sb.toString();
    }

    public static class Parser extends AbstractParser<NotifyBye> {
        public Parser() {
            super(NotifyBye.class);
        }

        @Override
        protected void parseLine(NotifyBye notifyBye, String key, String value) {
            switch (key) {
                case "HOST":
                    notifyBye.setHost(value);
                    break;
                case "NT":
                    notifyBye.setNt(value);
                    break;
                case "USN":
                    notifyBye.setUsn(value);
                    break;
                default:
                    break;
            }
        }
    }
}
