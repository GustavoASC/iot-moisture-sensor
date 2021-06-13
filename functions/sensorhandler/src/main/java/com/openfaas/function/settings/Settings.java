package com.openfaas.function.settings;

/**
 * Class representing the settings for the sensoring system,
 * with minimum moisture percentage and e-mail address to be
 * notified when needed.
 * 
 * <p>
 * This class is immutable. Once an instance is created, it cannot have
 * the values changed.
 */
public class Settings {

    private final int minMoisture;
    private final String email;

    private Settings(int lower, String email) {
        this.minMoisture = lower;
        this.email = email;
    }

    public static Settings of(int lower, String email) {
        return new Settings(lower, email);
    }

    public int getMinMoisture() {
        return minMoisture;
    }

    public String getEmail() {
        return email;
    }

}
