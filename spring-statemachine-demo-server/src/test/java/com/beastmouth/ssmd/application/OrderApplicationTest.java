package com.beastmouth.ssmd.application;

import com.beastmouth.ssmd.BootstrapApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author huangbangjing
 * @date 2024/12/6 17:25
 */
@Slf4j
public class OrderApplicationTest extends BootstrapApplicationTest {
    @Autowired
    private OrderApplication orderApplication;

    @Test
    public void test() {
        Long order1 = orderApplication.createOrder();
        Long order2 = orderApplication.createOrder();

        orderApplication.submitPay(order1);
        orderApplication.paySuccess(order1);

        // 测试状态非法异常
        // orderApplication.paySuccess(order2);

        orderApplication.submitPay(order2);
    }
}
