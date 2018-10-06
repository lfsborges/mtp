package com.lindacare.test.mtp.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lindacare.test.mtp.model.Message;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;


public class MessageDeserializer implements Deserializer<Message> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Message deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        Message object = null;
        try {
            object = mapper.readValue(data, Message.class);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes "+ exception);
        }
        return object;
    }

    @Override
    public void close() {

    }
}
