package com.github.barry.fsm.sample.impl;

import com.github.barry.fsm.sample.UserLifeCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author wangxuexing
 * @Date 2020/4/28 20:45
 * @Version 1.0
 */
@Service
public class UserLifeCycleServiceImpl implements UserLifeCycleService {
    @Autowired
    UserStateMachineBuilder stateMachineBuilder;

    public User create(User user) {
        return (User)stateMachineBuilder.trigger(user, UserEvent.CREATE, null);
    }

    public User verify(User user) {
        return (User)stateMachineBuilder.trigger(user, UserEvent.VERIFY, null);
    }

    public User block(User user) {
        return (User)stateMachineBuilder.trigger(user, UserEvent.BLOCK, null);
    }

    /**
     * 非容器调用状态机
     * @param user
     * @return
     */
    public User anyway(User user){
        UserStateMachineBuilder stateMachineBuilder = new UserStateMachineBuilder();
        stateMachineBuilder.createBasicStateMachine();
        return (User)stateMachineBuilder.trigger(user, UserEvent.BLOCK, null);
    }
}
