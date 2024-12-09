package com.beastmouth.ssmd.application;

import com.beastmouth.ssmd.BootstrapApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author huangbangjing
 * @date 2024/12/6 17:25
 */
@Slf4j
public class OrderApplicationTest extends BootstrapApplicationTest {
    @Autowired
    private OrderApplication orderApplication;

    @Test
    public void test() throws InterruptedException {

        // 测试并发场景
        CountDownLatch count = new CountDownLatch(1);

        new Thread(() -> {
            Long order1 = orderApplication.createOrder();
            orderApplication.submitPay(order1);
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            orderApplication.paySuccess(order1);
        }).start();

        new Thread(() -> {
            Long order2 = orderApplication.createOrder();
            // 测试状态非法异常
            // orderApplication.paySuccess(order2);
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            orderApplication.submitPay(order2);
        }).start();

        TimeUnit.SECONDS.sleep(10);
        count.countDown();
        TimeUnit.SECONDS.sleep(20);
    }
}
