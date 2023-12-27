package com.example.send.topic.device;

public class BrokerDto {

    private String broker;
    private String deviceName;
    private String host;
    private String iotId;
    private String iotToken;
    private String port;
    private String productKey;
    private String realDeviceName;
    private String uuid;

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIotId() {
        return iotId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public String getIotToken() {
        return iotToken;
    }

    public void setIotToken(String iotToken) {
        this.iotToken = iotToken;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getRealDeviceName() {
        return realDeviceName;
    }

    public void setRealDeviceName(String realDeviceName) {
        this.realDeviceName = realDeviceName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
