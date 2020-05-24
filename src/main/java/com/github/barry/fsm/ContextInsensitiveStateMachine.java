package com.github.barry.fsm;

import lombok.extern.slf4j.Slf4j;
import org.squirrelframework.foundation.fsm.*;
import org.squirrelframework.foundation.fsm.annotation.ContextInsensitive;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * @ClassName ContextInsensitiveStateMachine
 * @Description 不关心上下文状态机
 * @Author wangxuexing
 * @Date 2020/5/6 14:42
 * @Version 1.0
 */
@Slf4j
@Transitions({
        @Transit(from="A", to="B", on="toB", callMethod="fromAToB"),
        @Transit(from="B", to="C", on="toC"),
        @Transit(from="C", to="D", on="toD"),
        @Transit(from="D", to="A", on="toA", callMethod="fromDToA")
})
@ContextInsensitive
public class ContextInsensitiveStateMachine
        extends AbstractStateMachine<ContextInsensitiveStateMachine, ATMState, ATMEvent, Void> {
    public void fromAToB(ATMState from, ATMState to, ATMEvent event) {
        log.info("fromAToB from: {}， to:{}，event：{}", from, to, event);
    }
    public void fromDToA(ATMState from, ATMState to, ATMEvent event) {
        log.info("fromDToA from: {}， to:{}，event：{}", from, to, event);
        //int s = 23/0;//test the exception occur
    }
    public void transitFromIdleToLoadingOnConnected(ATMState from, ATMState to, ATMEvent event) {
        log.info("transitFromIdleToLoadingOnConnected from: {}， to:{}，event：{}", from, to, event);
    }
    public void entryLoading(ATMState from, ATMState to, ATMEvent event) {
        log.info("entryLoading from: {}， to:{}，event：{}", from, to, event);
    }
    public static void main(String[] args){
        StateMachineBuilder<ContextInsensitiveStateMachine, ATMState, ATMEvent, Void> builder =
                StateMachineBuilderFactory.create(ContextInsensitiveStateMachine.class, ATMState.class, ATMEvent.class, null);
        //method 1 显示声明状态，事件及调用关系
        //builder.externalTransition().from(ATMState.D).to(ATMState.A).on(ATMEvent.toA).callMethod("fromDToA");
        //method 2 注解声明调用关系
        ContextInsensitiveStateMachine fsm = builder.newStateMachine(ATMState.D);
        fsm.fire(ATMEvent.toA);
    }
}

enum ATMState {
    A, B, C, D
}

/**
 * 事件定义
 */
enum ATMEvent {
    toA, toB, toC, toD
}