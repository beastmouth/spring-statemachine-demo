package com.beastmouth.ssmd.enums;

/**
 * 订单事件
 *
 * @author huangbangjing
 * @date 2024/12/6 15:03
 */
public enum OrderEventEnum {
    /**
     * 提交支付
     */
    SUBMIT_PAY,
    /**
     * 支付成功
     */
    PAY_SUCCESS,
    /**
     * 发货
     */
    DELIVER,
    /**
     * 签收
     */
    RECEIVE,
    /**
     * 退款申请
     */
    REFUND,
    /**
     * 退款成功
     */
    REFUNDED,
    /**
     * 支付超时
     */
    PAY_TIME_OUT,
    /**
     * 订单取消
     */
    CANCEL,
    ;
}
