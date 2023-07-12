package org.flights;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class OpenSkyClient {
    protected File configurationFile;
    protected OpenSkyConfig config;
    public OpenSkyClient(String configFile) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        this.configurationFile = new File(configFile);
        this.config = objectMapper.readValue(this.configurationFile, OpenSkyConfig.class);
        OpenSkyApi api = new OpenSkyApi(USERNAME, PASSWORD);
        OpenSkyStates os = api.getStates(0, null);
        System.out.println(config.getUsername());
    }



}
