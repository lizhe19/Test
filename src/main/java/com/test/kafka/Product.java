package com.test.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2020/7/2 14:49
 *  
 **/
//@RestController
public class Product {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    @RequestMapping("message/send")
    public String send(String msg){
        //使用kafka模板发送信息
        kafkaTemplate.send("demo", msg);
        return "success";
    }

    /*
    * 如果是实体类的消息传递，可以采用自定义序列化和反序列化器进行实体类的序列化和反序列化，
    * 由于Serializer和Deserializer影响到上下游系统，导致牵一发而动全身。
    * 自定义序列化&反序列化实现不是能力的体现，而是逗比的体现。
    *
    * 所以强烈不建议自定义实现序列化&反序列化，推荐直接使用StringSerializer和StringDeserializer，然后使用json作为标准的数据传输格式
    * */

    /*
    spring:
  kafka:
    #指定kafka server的地址，集群配多个，中间，逗号隔开
    bootstrap-servers: 127.0.0.1:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      # 写入失败时，重试次数。当leader节点失效，一个repli节点会替代成为leader节点，此时可能出现写入失败，
      #当retris为0时，produce不会重复。retirs重发，此时repli节点完全成为leader节点，不会产生消息丢失
      retries: 0
      # 每次批量发送消息的数量,produce积累到一定数据，一次发送
      batch-size: 16384
      # produce积累数据一次发送，缓存大小达到buffer.memory就发送数据
      buffer-memory: 33554432
      #acks = 0 如果设置为零，则生产者将不会等待来自服务器的任何确认，该记录将立即添加到套接字缓冲区并视为已发送。在这种情况下，无法保证服务器已收到记录，并且重试配置将不会生效（因为客户端通常不会知道任何故障），为每条记录返回的偏移量始终设置为-1。
      #acks = 1 这意味着leader会将记录写入其本地日志，但无需等待所有副本服务器的完全确认即可做出回应，在这种情况下，如果leader在确认记录后立即失败，但在将数据复制到所有的副本服务器之前，则记录将会丢失。
      #acks = all 这意味着leader将等待完整的同步副本集以确认记录，这保证了只要至少一个同步副本服务器仍然存活，记录就不会丢失，这是最强有力的保证，这相当于acks = -1的设置。
      #可以设置的值为：all, -1, 0, 1
      acks: 1
    consumer:
      #群组ID  指定默认消费者group id --> 由于在kafka中，同一组中的consumer不会读取到同一个消息，依靠groud.id设置组名
      group-id: default_consumer_group
      #enable.auto.commit:true --> 设置自动提交offset
      enable-auto-commit: true
      #如果'enable.auto.commit'为true，则消费者偏移自动提交给Kafka的频率（以毫秒为单位），默认值为5000
      auto-commit-interval: 1000
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      #smallest和largest才有效，如果smallest重新0开始读取，如果是largest从logfile的offset读取。一般情况下我们都是设置smallest
      auto-offset-reset: earliest
  main:
    allow-bean-definition-overriding: true
     */
}
