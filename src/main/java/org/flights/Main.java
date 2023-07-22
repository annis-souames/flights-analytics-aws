package org.flights;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opensky.model.StateVector;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws IOException {
        String configPath = "C:\\Users\\souam\\Documents\\Data Engineering\\FlightAnalyticsAWS\\config\\config.json";
        Config config = loadConfig(configPath);

        OpenSkyClient client = new OpenSkyClient(config);
        //System.out.println(OpenSkyClient.serializeStates(client.getStates()));
        RecordsProducer producer = new RecordsProducer(client.getStates(), config);
        producer.run();
    }

    public static Config loadConfig(String configPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File configurationFile = new File(configPath);
        return objectMapper.readValue(configurationFile, Config.class);
    }
}