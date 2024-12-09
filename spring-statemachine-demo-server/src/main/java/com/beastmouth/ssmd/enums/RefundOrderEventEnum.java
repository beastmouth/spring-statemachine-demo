package com.beastmouth.ssmd.enums;

/**
 * @author huangbangjing
 * @date 2024/12/6 15:27
 */
public enum RefundOrderEventEnum {
    /**
     * 提交退款审核申请
     */
    SUBMIT_REFUND_AUDIT,
    /**
     * 退款审核通过
     */
    SUBMIT_REFUND_AUDIT_PASS,
    /**
     * 退款审核拒绝
     */
    SUBMIT_REFUND_AUDIT_REFUSE,
    /**
     * 退款
     */
    START_REFUND,
    /**
     * 退款成功
     */
    REFUND_SUCCESS,
    /**
     * 退款失败
     */
    REFUND_FAIL,
    /**
     * 取消退款
     */
    CANCEL_REFUND,
}
