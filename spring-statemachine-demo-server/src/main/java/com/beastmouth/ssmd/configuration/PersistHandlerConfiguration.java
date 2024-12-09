package com.beastmouth.ssmd.configuration;

import com.beastmouth.ssmd.api.enums.OrderStateEnums;
import com.beastmouth.ssmd.enums.OrderEventEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.redis.RedisStateMachinePersister;

import javax.annotation.Resource;

/**
 * @author huangbangjing
 * @date 2024/12/6 17:57
 */
@Configuration
public class PersistHandlerConfiguration {

    @Resource
    private StateMachinePersist<OrderStateEnums, OrderEventEnum, String> orderStateMachinePersist;



    @Bean(name = "orderStateMachinePersister")
    public RedisStateMachinePersister<OrderStateEnums, OrderEventEnum> orderStateMachineRuntimePersister() {
        return new RedisStateMachinePersister<>(orderStateMachinePersist);
    }
}
