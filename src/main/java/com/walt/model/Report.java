package com.walt.model;

public class Report implements DriverDistance {

    Driver driver;
    Long totalDistance;

    public Report(Driver driver, Long totalDistance) {
        this.driver = driver;
        this.totalDistance = totalDistance;
    }

    @Override
    public Driver getDriver() {
        return driver;
    }

    @Override
    public Long getTotalDistance() {
        return totalDistance;
    }

    @Override
    public String toString() {
        return "Report{" +
                "driver=" + driver +
                ", totalDistance=" + totalDistance +
                '}';
    }
}
