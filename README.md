### spring 状态机demo 模拟订单流程
#### 参考文档：https://docs.spring.io/spring-statemachine/docs/3.2.1/reference/#preface
#### 持久化方案：使用redis
#### 注意事项
* spring的状态机并不是无状态，在使用过程中，如果有多个订单使用到状态机，需要针对每个订单的状态进行缓存处理
* 于是引申出需要使用redis，mysql之类的方式持久化每个订单的信息，以便于状态机进行状态的恢复
* 经过测试，spring的状态机是通过单线程序列化来控制并发安全，所以可能会存在性能上的问题
#### 其他方案考虑：https://github.com/alibaba/COLA/tree/master/cola-components/cola-component-statemachine