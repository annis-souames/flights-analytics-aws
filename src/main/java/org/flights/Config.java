package org.flights;

import org.opensky.api.OpenSkyApi.BoundingBox;

/**
 * DTO class to hold the OpenSky Client configuration : username, password and polling
 * interval.
 */
public class Config {
    private String username;
    private String password;
    private int pollingInterval;
    private BoundingBox boundingBox;
    private String topic;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
    }
    public void setBoundingBox(double[] boundingBoxArray){
        this.boundingBox = new BoundingBox(
                boundingBoxArray[0],
                boundingBoxArray[1],
                boundingBoxArray[2],
                boundingBoxArray[3]
        );
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getPollingInterval() {
        return pollingInterval;
    }
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
