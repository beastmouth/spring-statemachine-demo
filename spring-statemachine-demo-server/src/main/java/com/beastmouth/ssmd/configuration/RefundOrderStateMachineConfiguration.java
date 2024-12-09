package com.beastmouth.ssmd.configuration;

import com.beastmouth.ssmd.api.enums.RefundOrderStateEnums;
import com.beastmouth.ssmd.enums.RefundOrderEventEnum;
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
@EnableStateMachine(name = "refundOrderStateMachine")
public class RefundOrderStateMachineConfiguration extends StateMachineConfigurerAdapter<RefundOrderStateEnums, RefundOrderEventEnum> {
    @Override
    public void configure(StateMachineStateConfigurer<RefundOrderStateEnums, RefundOrderEventEnum> states) throws Exception {
        states
            .withStates()
            // 初始状态
            .initial(RefundOrderStateEnums.REFUND_SUBMITTED)
            // 所有状态
            .states(EnumSet.allOf(RefundOrderStateEnums.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<RefundOrderStateEnums, RefundOrderEventEnum> transitions) throws Exception {
        transitions
            .withExternal().source(RefundOrderStateEnums.REFUND_SUBMITTED).target(RefundOrderStateEnums.REFUND_AUDIT).event(RefundOrderEventEnum.SUBMIT_REFUND_AUDIT)
            .and()
            .withExternal().source(RefundOrderStateEnums.REFUND_AUDIT).target(RefundOrderStateEnums.REFUNDING).event(RefundOrderEventEnum.SUBMIT_REFUND_AUDIT_PASS)
            .and()
            .withExternal().source(RefundOrderStateEnums.REFUND_AUDIT).target(RefundOrderStateEnums.REFUND_SUBMITTED).event(RefundOrderEventEnum.SUBMIT_REFUND_AUDIT_REFUSE)
            .and()
            .withExternal().source(RefundOrderStateEnums.REFUND_SUBMITTED).target(RefundOrderStateEnums.REFUNDING).event(RefundOrderEventEnum.START_REFUND)
            .and()
            .withExternal().source(RefundOrderStateEnums.REFUNDING).target(RefundOrderStateEnums.REFUNDED).event(RefundOrderEventEnum.REFUND_SUCCESS)
            .and()
            .withExternal().source(RefundOrderStateEnums.REFUNDING).target(RefundOrderStateEnums.REFUND_FAIL).event(RefundOrderEventEnum.REFUND_FAIL)
            .and()
            .withExternal().source(RefundOrderStateEnums.REFUND_FAIL).target(RefundOrderStateEnums.REFUNDING).event(RefundOrderEventEnum.START_REFUND)
            .and()
            .withExternal().source(RefundOrderStateEnums.REFUND_SUBMITTED).target(RefundOrderStateEnums.CANCEL).event(RefundOrderEventEnum.CANCEL_REFUND)
            .and()
            .withExternal().source(RefundOrderStateEnums.REFUND_FAIL).target(RefundOrderStateEnums.CANCEL).event(RefundOrderEventEnum.CANCEL_REFUND);
    }

}
