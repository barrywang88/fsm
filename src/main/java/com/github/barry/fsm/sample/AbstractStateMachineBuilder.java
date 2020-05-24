package com.github.barry.fsm.sample;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;

/**
 * @Author wangxuexing
 * @Date 2020/4/28 20:45
 * @Version 1.0
 */
@Slf4j
public abstract class AbstractStateMachineBuilder <T extends BasicStateMachine<T, S, E, C, Entity>, S, E, C,
        Entity extends StatefulEntity<S, ?>>
        implements InitializingBean {
    @Getter
    @Setter
    public StateMachineBuilder<T, S, E, C> stateMachineBuilder;

    @Autowired
    ApplicationContext applicationContext;

    public Entity trigger(@NonNull Entity entity, @NonNull E on, C context) {
        final T newStateMachine = createNewStateMachine(entity);
        newStateMachine.fire(on, context);
        return entity;
    }

    protected T createNewStateMachine(Entity entity) {
        return stateMachineBuilder.newStateMachine(entity.getStatus(), entity, applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        stateMachineBuilder = createBasicStateMachine();
        Assert.notNull(stateMachineBuilder, "StateMachineBuilder need to be initialized");
    }

    protected abstract StateMachineBuilder<T, S, E, C> createBasicStateMachine();
}
