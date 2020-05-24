package com.github.barry.fsm;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.squirrelframework.foundation.fsm.*;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @ClassName StateMachineSample1
 * @Description
 * @Author wangxuexing
 * @Date 2020/5/6 13:37
 * @Version 1.0
 */
@Slf4j
@Transitions({
        @Transit(from="A", to="B", on="toB", callMethod="fromAToB"),
        @Transit(from="B", to="C", on="toC"),
        @Transit(from="C", to="D", on="toD"),
        @Transit(from="D", to="A", on="toA", callMethod="fromDToA")
})
@StateMachineParameters(stateType=MyState.class, eventType=MyEvent.class, contextType=ContextType.class)
public class UntypedStateMachineSample extends AbstractUntypedStateMachine {
    //////////////////////////////////自定义方法 begin////////////////////////////////////////
    public void fromAToB(MyState from, MyState to, MyEvent event, ContextType context) {
        log.info("========fromAToB from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void fromDToA(MyState from, MyState to, MyEvent event, ContextType context) {
        log.info("========fromDToA from: {}， to:{}，event：{}， context：{}", from, to, event, context);
        //int s = 23/0;//test the exception occur
    }

    public void transitFromDToAOntoA(MyState from, MyState to, MyEvent event, ContextType context) {
        log.info("========transitFromDToAOntoA from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }
    //////////////////////////////////自定义方法 end////////////////////////////////////////

    ///////////////////////////////////自带方法 begin//////////////////////////////////////////////
    /**
     * 操作异常处理
     */
    @Override
    public void afterTransitionCausedException(Object fromState, Object toState, Object event, Object context) {
        Throwable targeException = getLastException().getTargetException();
        // recover from IllegalArgumentException thrown out from state 'A' to 'B' caused by event 'ToB'
        if(targeException instanceof IllegalArgumentException &&
                fromState.equals(MyState.A) && toState.equals(MyState.B) && event.equals("ToB")) {
            // do some error clean up job here
            // ...
            // after recovered from this exception, reset the state machine status back to normal
            setStatus(StateMachineStatus.IDLE);
        } else if(fromState.equals(MyState.D) && toState.equals(MyState.A)){
            log.error("from:{} to:{} event:{} error", fromState, toState, event);
        } else {
            super.afterTransitionCausedException(fromState, toState, event, context);
        }
    }

    @Override
    public void beforeTransitionBegin(Object from, Object event, Object context) {
        log.info("========beforeTransitionBegin from: {}， event：{}， context：{}", from, event, context);
    }

    @Override
    public void afterTransitionCompleted(Object from, Object to, Object event, Object context) {
        log.info("========afterTransitionCompleted from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    @Override
    public void afterTransitionEnd(Object from, Object to, Object event, Object context) {
        log.info("========afterTransitionEnd from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    @Override
    public void afterTransitionDeclined(Object from, Object event, Object context) {
        log.info("========afterTransitionDeclined from: {}， event：{}， context：{}", from, event, context);
    }

    @Override
    public void beforeActionInvoked(Object from, Object to, Object event, Object context) {
        log.info("========afterTransitionDeclined from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }
    ///////////////////////////////////自带方法 end//////////////////////////////////////////////

    public static void main(String[] args){
        ConverterProvider.INSTANCE.register(MyEvent.class, new MyEventConverter());
        ConverterProvider.INSTANCE.register(MyState.class, new MyStateConverter());

        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(UntypedStateMachineSample.class);
        //method 1 显示声明状态，事件及调用关系
//        builder.externalTransition().from(MyState.D).to(MyState.A).on(MyEvent.toA).callMethod("fromDToA");
        //method 2 注解声明调用关系
        UntypedStateMachine fsm = builder.newStateMachine(MyState.D);

        //add the listener
        fsm.addStateMachineListener((StateMachineListener) event -> {
            log.info("========StateMachineListener event: {}", event);
        });
        fsm.fire(MyEvent.toA, new ContextType("my", 23));
    }
}

/**
 * 状态枚举定义
 */
enum MyState {
    A, B, C, D
}

/**
 * 事件定义
 */
enum MyEvent {
    toA, toB, toC, toD
}

/**
 * 上线文定义
 */
@Data
class ContextType{
    private String key;
    private Integer value;
    public ContextType(String key, Integer value){
        this.key = key;
        this.value = value;
    }
}

/**
 * 状态类型转换器
 */
class MyStateConverter implements Converter<MyState>{
    @Override
    public String convertToString(MyState obj) {
        return obj.name();
    }

    @Override
    public MyState convertFromString(String name) {
        return MyState.valueOf(name);
    }
}

/**
 * 事件类型转换器
 */
class MyEventConverter implements Converter<MyEvent>{
    @Override
    public String convertToString(MyEvent obj) {
        return obj.name();
    }

    @Override
    public MyEvent convertFromString(String name) {
        return MyEvent.valueOf(name);
    }
}