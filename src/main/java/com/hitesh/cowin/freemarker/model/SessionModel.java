package com.hitesh.cowin.freemarker.model;

public class SessionModel {
    private final String date;
    private final int availableCap;
    private final int minAge;

    private SessionModel(String date, int availableCapacity, int minAge) {
        this.date = date;
        this.availableCap = availableCapacity;
        this.minAge = minAge;
    }

    public static SessionModel with(String date, int availableCapacity, int minAge) {
        return new SessionModel(date, availableCapacity, minAge);
    }

    public String getDate() {
        return date;
    }

    public int getAvailableCapacity() {
        return availableCap;
    }

    public int getMinAge() {
        return minAge;
    }
}
