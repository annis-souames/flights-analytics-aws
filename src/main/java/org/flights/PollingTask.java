package org.flights;

import java.io.IOException;
import java.util.TimerTask;

public class PollingTask extends TimerTask {
    protected Config config;
    public PollingTask(Config config){
        this.config = config;
    }
    @Override
    public void run() {
        try{
            OpenSkyClient client = new OpenSkyClient(this.config);
            RecordsProducer producer = new RecordsProducer(client.getStates(), config);
            producer.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
