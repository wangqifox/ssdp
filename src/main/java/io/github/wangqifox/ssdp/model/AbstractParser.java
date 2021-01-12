package io.github.wangqifox.ssdp.model;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author wangqi
 * @date 2021/1/11
 */
public abstract class AbstractParser<T extends AbstractModel> {
    private T t;

    public AbstractParser(Class<T> clz)  {
        try {
            t = clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T parse(byte[] data) {
        int start = 0, end = 0;
        while (end < data.length) {
            end = lineEnd(data, start);
            if (start == end) {
                break;
            }
            final String line = new String(Arrays.copyOfRange(data, start, end), UTF_8);
            if (start == 0) {
                if (!t.HEAD.equals(line)) {
                    return null;
                }
            } else {
                final String[] segments = line.split(T.SEPARATOR, 2);
                parseLine(t, segments[0].toUpperCase().trim(), segments.length > 1 ? segments[1].trim() : null);
            }
            start = end + T.CRLF.length;
        }
        return t;
    }

    protected abstract void parseLine(T t, String key, String value);

    private int lineEnd(byte[] data, int start) {
        while (start < data.length) {
            if (data[start] == T.CRLF[0] && data[start + 1] == T.CRLF[1]) {
                return start;
            } else {
                start++;
            }
        }
        return -1;
    }
}
