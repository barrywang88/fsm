package com.github.barry.fsm;

import lombok.extern.slf4j.Slf4j;
import org.squirrelframework.foundation.fsm.*;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @ClassName HierarchicalStateMachine
 * @Description 分层状态机
 * @Author wangxuexing
 * @Date 2020/5/6 16:24
 * @Version 1.0
 */
@Slf4j
@Transitions({
        @Transit(from="A11", to="A12", on="toA12", callMethod="fromA11ToA12"),
        @Transit(from="A21", to="A23", on="toA23", callMethod="fromA21ToA23"),
        @Transit(from="A1", to="A2", on="toA2", callMethod="fromA1ToA2"),
        @Transit(from="B", to="A", on="toA", callMethod="fromBToA")
})
@States({
        @State(name="B", entryCallMethod="entryB", exitCallMethod="exitB"),
        @State(name="A", entryCallMethod="entryA", exitCallMethod="exitA", compositeType= StateCompositeType.SEQUENTIAL),
        @State(parent="A", name="A1", entryCallMethod="entryA1", exitCallMethod="exitA1", compositeType= StateCompositeType.SEQUENTIAL),
        @State(parent="A", name="A2", entryCallMethod="entryA2", exitCallMethod="exitA2", compositeType= StateCompositeType.PARALLEL, initialState=true),
        @State(parent="A1", name="A11", entryCallMethod="entryA11", exitCallMethod="exitA11"),
        @State(parent="A1", name="A12", entryCallMethod="entryA12", exitCallMethod="exitA12"),
        @State(parent="A2", name="A21", entryCallMethod="entryA21", exitCallMethod="exitA21"),
        @State(parent="A2", name="A22", entryCallMethod="entryA22", exitCallMethod="exitA22"),
        @State(parent="A2", name="A23", entryCallMethod="entryA23", exitCallMethod="exitA23")
})
@StateMachineParameters(stateType=HierarchicalState.class, eventType=HierarchicalEvent.class, contextType=ContextType.class)
public class HierarchicalStateMachine extends AbstractUntypedStateMachine {
    public void entryA(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========entryA from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA1(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========entryA1 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA2(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========entryA2 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA11(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========entryA11 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA12(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========entryA12 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA21(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========entryA21 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA22(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========entryA22 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA23(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========entryA23 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }


    public void fromBToA(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========fromBToA from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void fromA1ToA2(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========fromA1ToA2 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void fromA11ToA12(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========fromA11ToA12 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void fromA21ToA23(HierarchicalState from, HierarchicalState to, HierarchicalEvent event, ContextType context) {
        log.info("========fromA21ToA23 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }


    public static void main(String[] args){
        ConverterProvider.INSTANCE.register(HierarchicalEvent.class, new HierarchicalEventConverter());
        ConverterProvider.INSTANCE.register(HierarchicalState.class, new HierarchicalStateConverter());

        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(HierarchicalStateMachine.class);
        //method 2 注解声明调用关系
        UntypedStateMachine fsm = builder.newStateMachine(HierarchicalState.B);
        fsm.fire(HierarchicalEvent.toA, new ContextType("my", 23));
    }
}

enum HierarchicalState{
    B,A,A1,A2,A11,A12,A21,A22,A23
}

enum HierarchicalEvent{
    toA,toA1,toA2,toA11,toA12,toA21,toA22,toA23
}

/**
 * 状态类型转换器
 */
class HierarchicalStateConverter implements Converter<HierarchicalState>{
    @Override
    public String convertToString(HierarchicalState obj) {
        return obj.name();
    }

    @Override
    public HierarchicalState convertFromString(String name) {
        return HierarchicalState.valueOf(name);
    }
}

/**
 * 事件类型转换器
 */
class HierarchicalEventConverter implements Converter<HierarchicalEvent>{
    @Override
    public String convertToString(HierarchicalEvent obj) {
        return obj.name();
    }

    @Override
    public HierarchicalEvent convertFromString(String name) {
        return HierarchicalEvent.valueOf(name);
    }
}