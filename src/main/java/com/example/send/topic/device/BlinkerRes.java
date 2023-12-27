
package com.example.send.topic.device;

public class BlinkerRes {

    private BrokerDto detail;
    private String message;

    public BlinkerRes() {
    }

    public BlinkerRes(String message) {
        this.message = message;
    }

    public BrokerDto getDetail() {
        return detail;
    }

    public void setDetail(BrokerDto detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
