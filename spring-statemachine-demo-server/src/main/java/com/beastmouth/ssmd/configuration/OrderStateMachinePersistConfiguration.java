package com.beastmouth.ssmd.configuration;

import com.beastmouth.ssmd.api.enums.OrderStateEnums;
import com.beastmouth.ssmd.enums.OrderEventEnum;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huangbangjing
 * @date 2024/12/9 11:13
 */
@Component
public class OrderStateMachinePersistConfiguration implements StateMachinePersist<OrderStateEnums, OrderEventEnum, String> {
    @Resource
    private RedisStateMachineContextRepository<OrderStateEnums, OrderEventEnum> orderRedisStateMachineRepository;

    @Override
    public void write(StateMachineContext<OrderStateEnums, OrderEventEnum> context, String contextObj) throws Exception {
        // TODO 优化：持久化到数据库中，redis键存在时效性
        orderRedisStateMachineRepository.save(context, contextObj);
    }

    @Override
    public StateMachineContext<OrderStateEnums, OrderEventEnum> read(String contextObj) throws Exception {
        return orderRedisStateMachineRepository.getContext(contextObj);
    }
}
