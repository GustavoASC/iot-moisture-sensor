package com.cassel.function.sensorhandler;

public class Settings {

    private final int lower;
    private final int higher;
    private final String recipient;

    private Settings(int lower, int higher, String recipient) {
        this.lower = lower;
        this.higher = higher;
        this.recipient = recipient;
    }

    public static Settings of(int lower, int higher, String recipient) {
        return new Settings(lower, higher, recipient);
    }
    
    public int getHigher() {
        return higher;
    }

    public int getLower() {
        return lower;
    }

    public String getRecipient() {
        return recipient;
    }

}
