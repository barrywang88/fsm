package com.github.barry.fsm.sample;

/**
 * @ClassName StatefulEntity
 * @Description 状态实体
 * @Author wangxuexing
 * @Date 2020/4/28 20:45
 * @Version 1.0
 */
public interface StatefulEntity<S, ID> {
    S getStatus();
    void setStatus(S status);
    ID getId();
}
