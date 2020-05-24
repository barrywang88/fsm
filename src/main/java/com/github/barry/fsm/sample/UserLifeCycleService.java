package com.github.barry.fsm.sample;

import com.github.barry.fsm.sample.impl.User;

/**
 * @ClassName UserLifeCycleService
 * @Author wangxuexing
 * @Date 2020/5/6 22:41
 * @Version 1.0
 */
public interface UserLifeCycleService {
    User create(User user);
    User verify(User user);
    User block(User user);
    User anyway(User user);
}
