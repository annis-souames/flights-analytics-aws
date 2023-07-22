package org.flights;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.metrics.Stat;
import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;
import org.opensky.model.StateVector;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class OpenSkyClient {
    protected File configurationFile;
    protected Config config;
    protected OpenSkyApi api;
    protected Collection<StateVector> states;
    public OpenSkyClient(Config config) throws IOException {
        this.config = config;
        this.api = new OpenSkyApi(
                this.config.getUsername(),
                this.config.getPassword()
        );
    }

    public Collection<StateVector> getStates() throws IOException {

        OpenSkyStates os = this.api.getStates(
                0,
                null,
                this.config.getBoundingBox()
        );
        this.states = os.getStates();
        return this.states;
    }

    public static String serializeStates(Collection<StateVector> states) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString( states );
    }


}
