package com.beastmouth.ssmd.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态
 *
 * @author huangbangjing
 * @date 2024/12/6 15:01
 */
@Getter
@AllArgsConstructor
public enum OrderStateEnums {
    /**
     * 待支付
     */
    UN_PAID(100, "待支付"),
    /**
     * 支付中
     */
    PAYING(200, "支付中"),
    /**
     * 已支付
     */
    PAID(300, "已支付"),
    /**
     * 已发货
     */
    DELIVERED(400, "已发货"),
    /**
     * 已签收
     */
    RECEIVED(500, "已签收"),
    /**
     * 退款中
     */
    REFUNDING(700, "退款中"),
    /**
     * 已取消
     */
    CANCELED(900, "已取消"),
    ;

    private final Integer code;
    private final String value;
}
