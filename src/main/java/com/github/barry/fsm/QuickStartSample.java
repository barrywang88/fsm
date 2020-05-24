package com.github.barry.fsm;

import lombok.Data;
import org.squirrelframework.foundation.fsm.*;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @ClassName QuickStartSample
 * @Description 有限状态机简单示例
 * @Author wangxuexing
 * @Date 2020/4/28 20:45
 * @Version 1.0
 */
public class QuickStartSample {
    // 1. Define State Machine Event
    enum FSMEvent {
        ToA, ToB, ToC, ToD
    }

    enum FSMState {
        A, B, C, D
    }

    // 2. Define State Machine Class
    @StateMachineParameters(stateType=String.class, eventType=FSMEvent.class, contextType=Integer.class)
    static class StateMachineSample extends AbstractUntypedStateMachine {
        protected void fromAToB(String from, String to, FSMEvent event, Integer context) {
            System.out.println("Transition from '"+from+"' to '"+to+"' on event '"+event+
                    "' with context '"+context+"'.");
        }

        protected void ontoB(String from, String to, FSMEvent event, Integer context) {
            System.out.println("Entry State \'"+to+"\'.");
        }
    }

    // 2. Define State Machine Class
    @StateMachineParameters(stateType=FSMState.class, eventType=FSMEvent.class, contextType=Integer.class)
    static class StateMachine extends AbstractUntypedStateMachine {
        protected void fromAToC(FSMState from, FSMState to, FSMEvent event, Integer context) {
            System.out.println("Transition from '"+from+"' to '"+to+"' on event '"+event+
                    "' with context '"+context+"'.");
        }

        protected void toC(FSMState from, FSMState to, FSMEvent event, Integer context) {
            System.out.println("Entry State \'"+to+"\'.");
        }

        /**
         * transitFrom[fromStateName]To[toStateName]On[eventName]When[conditionName]
         * transitFrom[fromStateName]To[toStateName]On[eventName]
         * transitFromAnyTo[toStateName]On[eventName]
         * transitFrom[fromStateName]ToAnyOn[eventName]
         * transitFrom[fromStateName]To[toStateName]
         * on[eventName]
         */
        protected void transitFromAToBOnToBWhenMyCondition(FSMState from, FSMState to, FSMEvent event, Integer context){
            System.out.println("transitFrom[fromStateName]To[toStateName]On[eventName]When[conditionName] from '"+from+"' to '"+to+"' on event '"+event+
                    "' with context '"+context+"'.");
        }

        protected void transitFromAToBOnToB(FSMState from, FSMState to, FSMEvent event, Integer context){
            System.out.println("transitFrom[fromStateName]To[toStateName]On[eventName] from '"+from+"' to '"+to+"' on event '"+event+
                    "' with context '"+context+"'.");
        }

        protected void transitFromAnyToBOnToB(FSMState from, FSMState to, FSMEvent event, Integer context){
            System.out.println("transitFromAnyTo[toStateName]On[eventName] from '"+from+"' to '"+to+"' on event '"+event+
                    "' with context '"+context+"'.");
        }

        protected void transitFromAToAnyOnToC(FSMState from, FSMState to, FSMEvent event, Integer context){
            System.out.println("transitFrom[fromStateName]ToAnyOn[eventName] from '"+from+"' to '"+to+"' on event '"+event+
                    "' with context '"+context+"'.");
        }

        protected void transitFromBToC(FSMState from, FSMState to, FSMEvent event, Integer context){
            System.out.println("transitFrom[fromStateName]To[toStateName] from '"+from+"' to '"+to+"' on event '"+event+
                    "' with context '"+context+"'.");
        }
    }

    public class MyStateMachine extends AbstractStateMachine {
        protected void transitFromAToBOnToBWhenMyCondition(FSMState from, FSMState to, FSMEvent event, MyContext context){
            System.out.println("transitFrom[fromStateName]To[toStateName]On[eventName]When[conditionName] from '"+from+"' to '"+to+"' on event '"+event+
                    "' with context '"+context.name+"'.");
        }
    }

    static class MyCondition implements Condition<Integer>{
        @Override
        public boolean isSatisfied(Integer context) {
            return context > 10;
        }
        @Override
        public String name() {
            return "MyCondition";
        }
    }

    @Data
    class MyContext{
        String name;
        Integer count;
    }

    public static void main(String[] args) {
       /* // 3. Build State Transitions
        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(StateMachineSample.class);
        builder.externalTransition().from("A").to("B").on(FSMEvent.ToB).callMethod("fromAToB");
        builder.onEntry("B").callMethod("ontoB");

        // 4. Use State Machine
        UntypedStateMachine fsm = builder.newStateMachine("A");
        fsm.fire(FSMEvent.ToB, 10);

        System.out.println("Current state is "+fsm.getCurrentState());
*/

        //显示调用指定方法
        UntypedStateMachineBuilder builder2 = StateMachineBuilderFactory.create(StateMachine.class);
        builder2.externalTransition().from(FSMState.A).to(FSMState.C).on(FSMEvent.ToC).callMethod("fromAToC");
        builder2.onEntry(FSMState.C).callMethod("toC");
        UntypedStateMachine fsm = builder2.newStateMachine(FSMState.A);
        fsm.fire(FSMEvent.ToC, 11);
        System.out.println("1. Current state is "+fsm.getCurrentState());

        //根据约定方法名称：transitFromAToBOnToB
        UntypedStateMachineBuilder builder3 = StateMachineBuilderFactory.create(StateMachine.class);
        builder3.externalTransition().from(FSMState.A).to(FSMState.B).on(FSMEvent.ToB);
        builder3.onEntry(FSMState.B);
        UntypedStateMachine fsm2 = builder3.newStateMachine(FSMState.A);
        fsm2.fire(FSMEvent.ToB, 11);
        System.out.println("2. Current state is "+fsm2.getCurrentState());

       /* //根据约定方法名称:transitFromAToBOnToBWhenMyCondition
        StateMachineBuilder<MyStateMachine, FSMState, FSMEvent, MyContext> builder =
                StateMachineBuilderFactory.create(MyStateMachine.class, FSMState.class, FSMEvent.class, MyContext.class);
        UntypedStateMachineBuilder builder4 = StateMachineBuilderFactory.create(StateMachine.class);
        builder4.externalTransition().from(FSMState.A).to(FSMState.B).on(FSMEvent.ToB).when(new Condition<MyContext>() {
            @Override
            public boolean isSatisfied(MyContext context) {
                return false;
            }

            @Override
            public String name() {
                return "MyCondition";
            }
        });
        builder4.onEntry(FSMState.B);
        UntypedStateMachine fsm3 = builder4.newStateMachine(FSMState.A);
        fsm3.fire(FSMEvent.ToB, 11);
        System.out.println("3. Current state is "+fsm3.getCurrentState());*/
    }
}
