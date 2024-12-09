package com.beastmouth.ssmd.configuration;

import com.beastmouth.ssmd.api.enums.OrderStateEnums;
import com.beastmouth.ssmd.enums.OrderEventEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @author huangbangjing
 * @date 2024/12/6 15:02
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfiguration extends StateMachineConfigurerAdapter<OrderStateEnums, OrderEventEnum> {
    @Override
    public void configure(StateMachineStateConfigurer<OrderStateEnums, OrderEventEnum> states) throws Exception {
        states
            .withStates()
            // 初始状态
            .initial(OrderStateEnums.UN_PAID)
            // 所有状态
            .states(EnumSet.allOf(OrderStateEnums.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStateEnums, OrderEventEnum> transitions) throws Exception {
        transitions
            .withExternal().source(OrderStateEnums.UN_PAID).target(OrderStateEnums.PAYING).event(OrderEventEnum.SUBMIT_PAY)
            .and()
            .withExternal().source(OrderStateEnums.PAYING).target(OrderStateEnums.PAID).event(OrderEventEnum.PAY_SUCCESS)
            .and()
            .withExternal().source(OrderStateEnums.PAID).target(OrderStateEnums.DELIVERED).event(OrderEventEnum.DELIVER)
            .and()
            .withExternal().source(OrderStateEnums.PAID).target(OrderStateEnums.REFUNDING).event(OrderEventEnum.REFUND)
            .and()
            .withExternal().source(OrderStateEnums.REFUNDING).target(OrderStateEnums.CANCELED).event(OrderEventEnum.REFUNDED)
            .and()
            .withExternal().source(OrderStateEnums.PAID).target(OrderStateEnums.CANCELED).event(OrderEventEnum.CANCEL)
            .and()
            .withExternal().source(OrderStateEnums.UN_PAID).target(OrderStateEnums.CANCELED).event(OrderEventEnum.PAY_TIME_OUT)
            .and()
            .withExternal().source(OrderStateEnums.PAYING).target(OrderStateEnums.CANCELED).event(OrderEventEnum.PAY_TIME_OUT);
    }

}
