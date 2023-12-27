package com.example.send.topic.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping
public class SendTopicDeviceApp {


    private final BlinkerService blinkerService;

    public SendTopicDeviceApp(BlinkerService blinkerService) {
        this.blinkerService = blinkerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SendTopicDeviceApp.class, args);
    }


    /**
     * 发送事件到指定设备,触发指定按钮绑定的函数
     *
     * @param sourceAuthKey 来源设备密钥
     * @param targetAuthKey 目标设备密钥
     * @param btnKey        目标设备按钮组件eky
     * @return
     */
    @GetMapping("/send")
    public BlinkerRes send(String sourceAuthKey, String targetAuthKey, String btnKey) {
        blinkerService.publishTopic(sourceAuthKey, targetAuthKey, btnKey);
        return new BlinkerRes("发送成功");
    }
}
