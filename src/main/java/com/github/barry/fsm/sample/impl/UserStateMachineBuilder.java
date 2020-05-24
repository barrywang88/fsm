package com.github.barry.fsm.sample.impl;

import com.github.barry.fsm.sample.AbstractStateMachineBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;

/**
 * @ClassName UserStateMachineBuilderBuilder
 * @Description
 * @Author wangxuexing
 * @Date 2020/5/6 21:47
 * @Version 1.0
 */
@Component
public class UserStateMachineBuilder extends AbstractStateMachineBuilder {
    @Override
    protected StateMachineBuilder<UserStateMachine, UserStatus, UserEvent, UserContext> createBasicStateMachine() {
        return  StateMachineBuilderFactory.create(UserStateMachine.class,
                UserStatus.class, UserEvent.class, UserContext.class, User.class,
                ApplicationContext.class);
    }
}
