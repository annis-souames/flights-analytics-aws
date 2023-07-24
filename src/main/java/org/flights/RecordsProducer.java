package org.flights;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.opensky.model.StateVector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordsProducer {

    KafkaProducer<String, String> producer;
    Collection<StateVector> states;
    String topic;
    private static final Logger log = LoggerFactory.getLogger(RecordsProducer.class);
    public RecordsProducer(Collection<StateVector> states, Config config) throws IOException {
        Properties props = loadProperties();
        this.topic = config.getTopic();
        this.producer = new KafkaProducer<>(props);
        this.states = states;

    }

    public void run() throws JsonProcessingException{
        log.info("Received " + this.states.size() + " states, will start producing to the "+ this.topic+" topic \n");
        for(StateVector state : this.states){
            // The key is the ICAO24, so all messages with the same key are sent to the same partition, we can read the states of the same key in order
            String key = state.getIcao24();
            String value = (new ObjectMapper()).writeValueAsString( state);
            ProducerRecord<String, String> record = new ProducerRecord<>(this.topic, key, value);
            producer.send(
                    record,
                    (recordMetadata, e) -> {
                        // executes every time a record is successfully sent or an exception is thrown
                        onCompletion(record, recordMetadata, e);
                    });
        }
        producer.flush();
        producer.close();
    }

    /**
     * Load properties from the cluster.properties file.
     * @return
     * @throws IOException
     */
    static Properties loadProperties() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream propsInputStream = classloader.getResourceAsStream("cluster.properties");
        Properties props = new Properties();
        props.load(propsInputStream);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }

    static void onCompletion(ProducerRecord record, RecordMetadata recordMetadata, Exception e){
        if (e == null) {
            // the record was successfully sent
            log.info("Received new metadata. \n" +
                    "Topic:" + recordMetadata.topic() + "\n" +
                    "Key:" + record.key() + "\n" +
                    "Partition: " + recordMetadata.partition() + "\n" +
                    "Offset: " + recordMetadata.offset() + "\n" +
                    "Timestamp: " + recordMetadata.timestamp());
        } else {
            log.error("Error while producing", e);
        }
    }
}
