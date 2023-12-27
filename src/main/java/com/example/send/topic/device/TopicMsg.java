package com.example.send.topic.device;

import java.util.Map;

public class TopicMsg {
    private String toDevice;
    private String fromDevice;
    private Map<String, String> data;

    public TopicMsg() {
    }

    public TopicMsg(String toDevice) {
        this.toDevice = toDevice;
    }

    public TopicMsg(String toDevice, Map<String, String> data) {
        this.toDevice = toDevice;
        this.data = data;
    }

    public String getToDevice() {
        return toDevice;
    }

    public void setToDevice(String toDevice) {
        this.toDevice = toDevice;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
