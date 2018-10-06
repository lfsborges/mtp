package com.lindacare.test.mtp.producer;

import java.util.Properties;
import java.util.concurrent.Future;

import com.lindacare.test.mtp.configurations.KafkaConfig;
import com.lindacare.test.mtp.model.Message;
import com.lindacare.test.mtp.model.MessageSerializer;
import io.dropwizard.lifecycle.Managed;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Producer implements Managed {

    private static final Logger LOG = LoggerFactory.getLogger(com.lindacare.test.mtp.producer.Producer.class);

    private final KafkaConfig config;

    private org.apache.kafka.clients.producer.Producer<String, Message> producer;

    public Producer(KafkaConfig config) {
        this.config = config;
    }

    public void start() throws Exception {
        LOG.info("starting");
        Properties properties = config.getProperties();
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageSerializer.class.getName());

        producer = new KafkaProducer<>(properties);
        LOG.info("started");
    }

    public Future<RecordMetadata> send(Message message) {
        return producer.send(new ProducerRecord<>(config.getTopic(), message.getTimePlaced(), message));
    }

    public void stop() throws Exception {
        LOG.info("stopping");
        org.apache.kafka.clients.producer.Producer<String, Message> producer = this.producer;
        this.producer = null;
        LOG.info("closing producer");
        producer.close();
        LOG.info("stopped");
    }

}