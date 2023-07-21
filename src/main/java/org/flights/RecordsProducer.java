package org.flights;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.opensky.model.StateVector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

public class RecordsProducer {

    KafkaProducer<String, String> producer;
    Collection<StateVector> states;
    String topic;
    public RecordsProducer(Collection<StateVector> states, Config config) throws IOException {
        Properties props = loadProperties();
        this.topic = config.getTopic();
        this.producer = new KafkaProducer<>(props);
        this.states = states;

    }

    public void run() throws JsonProcessingException {
        for(StateVector state : states){
            // The key is the ICAO24, so all messages with the same key are sent to the same partition, we can read the states of the same key in order
            String key = state.getIcao24();
            String value = (new ObjectMapper()).writeValueAsString( state);
            producer.send(new ProducerRecord<String, String>(this.topic, key, value));
        }
        producer.close();
    }

    /**
     * Load properties from the cluster.properties file.
     * @return
     * @throws IOException
     */
    static Properties loadProperties() throws IOException {
        File propsFile = new File("cluster.properties");
        final Properties props = new Properties();
        InputStream inputStream = new FileInputStream(propsFile);
        props.load(inputStream);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }
}
