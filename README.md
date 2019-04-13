1. **消息队列解决问题**

* 日志收集：系统日志不是主体逻辑，属于辅助性功能，日志系统即使挂了也不能影响主业务逻辑，所以需要单独处理；

* 异步处理：对非实时性功能采用异步处理，例如系统需要发送优惠消息给客户，那么可以采用异步推送；

* 异步解耦：两个系统对接，可以采用实时接口调用，也可以采用MQ中间层解耦；

* 流量消费：在流量高峰时期将待处理内容发送到MQ，后台消费服务平滑处理，避免实时高峰流量造成系统崩溃，达到削峰填谷的目的；

### 2. RabbitMQ安装 

* 具体可以参考网络，先安装erlang，再安装RabbitMQ：https://blog.csdn.net/hzw19920329/article/details/53156015

* 默认用户名、密码：guest

  ![1555119896515](README.assets/1555119896515.png)

* 添加用户

  ![1555119759146](README.assets/1555119759146.png)

* virtual hosts管理

  virtual hosts 相当于mysql 的 db

  ![1555120087927](README.assets/1555120087927.png)

  一般以/开头，然后对用户授权

  ![1555120125325](README.assets/1555120125325.png)

  

  可以看大授权后用户有对该virtual的权限

  ![1555120164045](README.assets/1555120164045.png)