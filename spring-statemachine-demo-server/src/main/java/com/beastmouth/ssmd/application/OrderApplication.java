package com.beastmouth.ssmd.application;

import cn.hutool.core.util.IdUtil;
import com.beastmouth.ssmd.api.enums.OrderStateEnums;
import com.beastmouth.ssmd.entity.OrderDO;
import com.beastmouth.ssmd.enums.OrderEventEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author huangbangjing
 * @date 2024/12/6 15:43
 */
@Slf4j
@Component
public class OrderApplication {
    @Resource
    private StateMachine<OrderStateEnums, OrderEventEnum> stateMachine;
    @Resource
    private StateMachinePersister<OrderStateEnums, OrderEventEnum, String> orderStateMachinePersister;

    public Long createOrder() {
        // 创建状态机实例
        long orderId = IdUtil.getSnowflake().nextId();
        State<OrderStateEnums, OrderEventEnum> initState = stateMachine.getInitialState();
        Integer orderState = initState.getId().getCode();
        if (orderState == null) {
            throw new RuntimeException("[订单] 订单创建失败");
        }
        log.info("[订单] 创建执行完成, id:{}, orderState:{}", orderId, orderState);
        return orderId;
    }

    public Long submitPay(Long orderId) {
        OrderDO order = new OrderDO();
        order.setId(orderId);
        order.setState(OrderStateEnums.UN_PAID.getCode());
        Map<String, Object> map = new HashMap<>();
        map.put("order", order);
        GenericMessage<OrderEventEnum> message = new GenericMessage<>(OrderEventEnum.SUBMIT_PAY, map);
        Integer orderState = processEvent(message);
        if (orderState == null) {
            throw new RuntimeException("[订单] 订单提交支付失败, id:" + orderId);
        }
        log.info("[订单] 提交支付执行完成, id:{}, orderState:{}", orderId, orderState);
        return orderId;
    }

    public Long paySuccess(Long orderId) {
        OrderDO order = new OrderDO();
        order.setId(orderId);
        order.setState(OrderStateEnums.PAYING.getCode());
        Map<String, Object> map = new HashMap<>();
        map.put("order", order);
        GenericMessage<OrderEventEnum> message = new GenericMessage<>(OrderEventEnum.PAY_SUCCESS, map);
        Integer orderState = processEvent(message);
        if (orderState == null) {
            throw new RuntimeException("[订单] 订单支付成功操作执行失败, id:" + orderId);
        }
        log.info("[订单] 支付成功执行完成, id:{}, orderState:{}", orderId, orderState);
        return orderId;
    }

    private synchronized Integer processEvent(Message<OrderEventEnum> message) {
        // 根据初始状态创建状态机
        try {
            // 序列化，仅有一个线程能够成功
            stateMachine.start();
            OrderDO order = (OrderDO) message.getHeaders().get("order");
            log.info("[状态机] 开始执行, id:{}, event:{}", order.getId(), message.getPayload());
            orderStateMachinePersister.restore(stateMachine, "ssmd:statemachine:order:" + order.getId());
            State<OrderStateEnums, OrderEventEnum> state = stateMachine.getState();
            log.info("[状态机] 状态机执行前, id:{}, state:{}", order.getId(), state.getId().getCode());
            Integer beforeState = state.getId().getCode();
            stateMachine.sendEvent(message.getPayload());
            state = stateMachine.getState();
            log.info("[状态机] 状态机执行后, id:{}, state:{}", order.getId(), state.getId().getCode());
            OrderStateEnums curState = stateMachine.getState().getId();
            Integer afterState = curState.getCode();
            if (Objects.equals(beforeState, afterState)) {
                log.info("[状态机] 状态未发生变更, before:{}, after:{}", beforeState, afterState);
                return null;
            }
            order.setState(curState.getCode());
            orderStateMachinePersister.persist(stateMachine, "ssmd:statemachine:order:" + order.getId());
            return curState.getCode();
        } catch (Exception e) {
            log.error("[状态机] 当前订单状态机报错, msg:{}", e.getMessage());
            return null;
        } finally {
            stateMachine.stop();
        }
    }
}
