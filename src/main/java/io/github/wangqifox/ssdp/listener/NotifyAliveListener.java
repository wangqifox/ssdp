package io.github.wangqifox.ssdp.listener;

import io.github.wangqifox.ssdp.model.NotifyAlive;

/**
 * @author wangqi
 * @date 2021/1/12
 */
public interface NotifyAliveListener {
    NotifyAlive onAlive();
}
