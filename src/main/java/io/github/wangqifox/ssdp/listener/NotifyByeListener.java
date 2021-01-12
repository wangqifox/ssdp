package io.github.wangqifox.ssdp.listener;

import io.github.wangqifox.ssdp.model.NotifyBye;

/**
 * @author wangqi
 * @date 2021/1/12
 */
public interface NotifyByeListener {
    NotifyBye onBye();
}
