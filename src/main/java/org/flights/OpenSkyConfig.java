package org.flights;

/**
 * DTO class to hold the OpenSky Client configuration : username, password and polling
 * interval.
 */
public class OpenSkyConfig {
    private String username;
    private String password;
    private int pollingInterval;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
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


}
