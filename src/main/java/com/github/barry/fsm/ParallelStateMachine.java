package com.github.barry.fsm;

import lombok.extern.slf4j.Slf4j;
import org.squirrelframework.foundation.fsm.*;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @ClassName ParallelStateMachine
 * @Description 并行状态机
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
        @State(name="A", entryCallMethod="entryA", exitCallMethod="exitA", compositeType= StateCompositeType.PARALLEL),
        @State(parent="A", name="A1", entryCallMethod="entryA1", exitCallMethod="exitA1", compositeType= StateCompositeType.PARALLEL),
        @State(parent="A", name="A2", entryCallMethod="entryA2", exitCallMethod="exitA2", compositeType= StateCompositeType.PARALLEL),
        @State(parent="A1", name="A11", entryCallMethod="entryA11", exitCallMethod="exitA11"),
        @State(parent="A1", name="A12", entryCallMethod="entryA12", exitCallMethod="exitA12"),
        @State(parent="A2", name="A21", entryCallMethod="entryA21", exitCallMethod="exitA21"),
        @State(parent="A2", name="A22", entryCallMethod="entryA22", exitCallMethod="exitA22"),
        @State(parent="A2", name="A23", entryCallMethod="entryA23", exitCallMethod="exitA23")
})
@StateMachineParameters(stateType=ParallelState.class, eventType=ParallelEvent.class, contextType=ContextType.class)
public class ParallelStateMachine extends AbstractUntypedStateMachine {
    public void entryA(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========entryA from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA1(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========entryA1 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA2(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========entryA1 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA11(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========entryA11 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA12(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========entryA12 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA21(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========entryA21 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA22(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========entryA22 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void entryA23(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========entryA23 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }


    public void fromBToA(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========fromBToA from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void fromA1ToA2(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========fromA1ToA2 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void fromA11ToA12(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========fromA11ToA12 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }

    public void fromA21ToA23(ParallelState from, ParallelState to, ParallelEvent event, ContextType context) {
        log.info("========fromA21ToA23 from: {}， to:{}，event：{}， context：{}", from, to, event, context);
    }


    public static void main(String[] args){
        ConverterProvider.INSTANCE.register(ParallelEvent.class, new ParallelEventConverter());
        ConverterProvider.INSTANCE.register(ParallelState.class, new ParallelStateConverter());

        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(ParallelStateMachine.class);
        //method 2 注解声明调用关系
        UntypedStateMachine fsm = builder.newStateMachine(ParallelState.B);
        fsm.fire(ParallelEvent.toA, new ContextType("my", 23));
    }
}

enum ParallelState{
    B,A,A1,A2,A11,A12,A21,A22,A23
}

enum ParallelEvent{
    toA,toA1,toA2,toA11,toA12,toA21,toA22,toA23
}

/**
 * 状态类型转换器
 */
class ParallelStateConverter implements Converter<ParallelState>{
    @Override
    public String convertToString(ParallelState obj) {
        return obj.name();
    }

    @Override
    public ParallelState convertFromString(String name) {
        return ParallelState.valueOf(name);
    }
}

/**
 * 事件类型转换器
 */
class ParallelEventConverter implements Converter<ParallelEvent>{
    @Override
    public String convertToString(ParallelEvent obj) {
        return obj.name();
    }

    @Override
    public ParallelEvent convertFromString(String name) {
        return ParallelEvent.valueOf(name);
    }
}