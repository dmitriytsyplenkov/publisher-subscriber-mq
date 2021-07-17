package com.dts.publisher.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MqMessage {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("msisdn")
    private Long msisdn;
    @JsonProperty("action")
    private ActionType action;
    @JsonProperty("timestamp")
    private Date timestamp;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", msisdn=" + msisdn +
                ", action=" + action +
                ", timestamp=" + timestamp +
                '}';
    }
}
