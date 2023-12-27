package com.example.send.topic.device;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BlinkerService {
    public static final String BLINKER_DEVICE_URL = "https://iot.diandeng.tech/api/v1/user/device/diy/auth?authKey={authKey}&protocol={protocol}";
    public static final String PUBLISH_TOPIC = "/device/%s/s";
    public static final String SUBSCRIPTION_TOPIC = "/device/&s/r";

    Logger logger = Logger.getLogger(BlinkerService.class.getName());


    private final RestTemplate restTemplate;

    public BlinkerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * @param sourceAuthKey 来源设备
     * @param targetAuthKey 目标设备
     */
    public void publishTopic(String sourceAuthKey, String targetAuthKey, String btnKey) {
        // 获取目标设备设备编码
        String deviceName = getDeviceName(targetAuthKey);

        // 获取来源设备设备信息
        BrokerDto sourceDevice = getDevice(sourceAuthKey);

        // 创建mqtt客户端
        MqttClient mqttClient = createMqttClient(sourceDevice);

        // 构建Topic
        String topic = String.format(PUBLISH_TOPIC, sourceDevice.getDeviceName());

        // 构建数据
        TopicMsg topicMsg = getTopicMsg(deviceName, btnKey);

        // 发布事件
        publishEvent(topicMsg, mqttClient, topic);
    }

    private void publishEvent(TopicMsg topicMsg, MqttClient mqttClient, String topic) {
        logger.log(Level.INFO, "发布事件: topic: {0}, msg: {1}", new Object[]{topic, beanToJSON(topicMsg)});
        MqttMessage message = new MqttMessage(beanToBytes(topicMsg));
        // 发布事件
        try {
            mqttClient.publish(topic, message);
            // 断开连接
            mqttClient.disconnect();
            mqttClient.close();
        } catch (MqttException e) {
            logger.log(Level.WARNING, "发布事件: {0} 失败", new Object[]{e.getLocalizedMessage()});
            throw new RuntimeException(e);
        }
    }

    private TopicMsg getTopicMsg(String deviceName, String btnKey) {
        TopicMsg topicMsg = new TopicMsg(deviceName);
        Map<String, String> data = new HashMap<>();
        data.put(btnKey, "tap");
        topicMsg.setData(data);
        return topicMsg;
    }


    private String getDeviceName(String targetAuthKey) {
        BrokerDto brokerDto = getDevice(targetAuthKey);
        return brokerDto.getDeviceName();
    }

    private static byte[] beanToBytes(TopicMsg topicMsg) {
        ObjectMapper mapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            bytes = mapper.writeValueAsBytes(topicMsg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    private String beanToJSON(Object ob) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(ob);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取设备信息
     *
     * @param authKey 设备authKey
     * @return
     */
    public BrokerDto getDevice(String authKey) {
        logger.log(Level.INFO, "获取设备信息: {0}", new Object[]{authKey});
        try {
            BlinkerRes res = restTemplate.getForObject(BLINKER_DEVICE_URL, BlinkerRes.class, authKey, "mqtt");
            if (Objects.isNull(res)) {
                throw new RuntimeException(String.format("获取设备信息失败为空: %s", authKey));
            }
            return res.getDetail();
        } catch (Exception e) {
            logger.log(Level.WARNING, "获取设备信息失败: {0}", new Object[]{authKey});
            throw new RuntimeException(e);
        }
    }


    /**
     * 创建mqtt客户端
     *
     * @param dto
     * @return
     */
    public MqttClient createMqttClient(BrokerDto dto) {

        if (Objects.isNull(dto)) {
            logger.log(Level.WARNING, "设备连接信息为空,无法创建MQTT客户端");
            throw new RuntimeException("设备连接信息为空,无法创建MQTT客户端");
        }
        logger.log(Level.INFO, "创建mqtt客户端: {0}", new Object[]{beanToJSON(dto)});
        String clientId = dto.getDeviceName();
        String username = dto.getIotId();
        String pwd = dto.getIotToken();
        String port = dto.getPort();

        // 服务器地址
        String host = dto.getHost().replaceAll("mqtt", "tcp");
        String broker = host + ":" + port;
        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient client = null;
        try {
            client = new MqttClient(broker, clientId, persistence);
            // 配置账号密码
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(username);
            connOpts.setPassword(pwd.toCharArray());
            connOpts.setCleanSession(true);
            // 建立连接
            client.connect(connOpts);
            return client;

        } catch (MqttException e) {
            logger.log(Level.WARNING, "创建MQTT客户端失败: {0}", e.getLocalizedMessage());
            throw new RuntimeException(e);
        }

    }

}
