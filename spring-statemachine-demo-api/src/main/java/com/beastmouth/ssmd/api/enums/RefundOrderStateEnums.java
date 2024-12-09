package com.beastmouth.ssmd.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author huangbangjing
 * @date 2024/12/6 15:21
 */
@Getter
@AllArgsConstructor
public enum RefundOrderStateEnums {
    REFUND_SUBMITTED(100, "退款已提交"),
    REFUND_AUDIT(200, "退款审批中"),
    REFUNDING(300, "退款中"),
    REFUND_FAIL(700, "退款失败"),
    REFUNDED(800, "已退款"),
    CANCEL(900, "已取消"),
    ;

    private final Integer code;
    private final String value;
}
