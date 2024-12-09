package com.beastmouth.ssmd.configuration;

import com.beastmouth.ssmd.api.enums.OrderStateEnums;
import com.beastmouth.ssmd.enums.OrderEventEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;

import javax.annotation.Resource;

/**
 * @author huangbangjing
 * @date 2024/12/9 11:19
 */
@Configuration
public class OrderRedisStateMachineRepositoryConfiguration {
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "orderRedisStateMachineRepository")
    public RedisStateMachineContextRepository<OrderStateEnums, OrderEventEnum> orderRedisStateMachineRepository() {
        return new RedisStateMachineContextRepository<>(redisConnectionFactory);
    }

}
