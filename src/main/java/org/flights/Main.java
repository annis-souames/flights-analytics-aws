package org.flights;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opensky.model.StateVector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws IOException {
        String configPath = "config.json";
        Config config = loadConfig(configPath);
        OpenSkyClient client = new OpenSkyClient(config);
        RecordsProducer producer = new RecordsProducer(client.getStates(), config);
        producer.run();
    }

    public static Config loadConfig(String configPath) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream configInputStream = classloader.getResourceAsStream(configPath);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(configInputStream, Config.class);
    }
}