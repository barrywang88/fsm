package com.github.barry.fsm.sample;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * @Author wangxuexing
 * @Date 2020/4/28 20:45
 * @Version 1.0
 */
@Slf4j
public abstract class BasicStateMachine<T extends AbstractStateMachine<T, S, E, C>,
        S, E, C, Entity extends StatefulEntity<S, ?>>
        extends AbstractStateMachine<T, S, E, C> {
    @Getter
    @NonNull
    public final Entity entity;
    @NonNull
    public final ApplicationContext applicationContext;

    @Override
    protected void afterTransitionDeclined(S from, E event, C context) {
        log.info(String.format("Transition from %s on %s is declined", from,event));
        throw new RuntimeException(String.format("Transition from %s on %s is declined", from,event));
    }

    @Override
    @SneakyThrows
    protected void afterTransitionCausedException(S from, S to, E on, C context) {
        final Throwable targetException = getLastException().getTargetException();
        if (targetException != null) {
            handleTargetException(from, to, on, targetException);
        }
        throw getLastException();
    }

    private void handleTargetException(S from, S to, E on, Throwable targetException) throws Throwable {
        final String msg = String.format("Some exception happened during transition of %s from %s to %s on %s", getEntityString(entity), from, to, on);
        log.error(msg, targetException);
        if (targetException instanceof Exception) {
            throw targetException;
        }
    }

    public BasicStateMachine(Entity entity, ApplicationContext applicationContext) {
        this.entity = entity;
        this.applicationContext = applicationContext;
    }

    @Override
    protected void beforeTransitionBegin(S from, E on, C context) {
        log.info("Trying transition of %s from %s on %s", getEntityString(entity), from, on);
    }

    @Override
    protected void afterTransitionEnd(S from, S to, E on, C context) {
        log.info("Successfully transitioned %s from %s to %s on %s", getEntityString(entity), from, to, on);
    }


    @Override
    protected void afterTransitionCompleted(S from, S to, E on, C context) {
        entity.setStatus(to);
        log.info(String.format("Transition from %s to %s on %s", from, to, on));
    }


    private String getEntityString(Entity entity) {
        return String.format("%s with id: %s", entity.getClass().getName(), entity.getId());
    }

}

