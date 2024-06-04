package com.example.plantrip;

public class Trip {
    public String tripId;
    public String tripName;
    public String destination;
    public String startDate;
    public String endDate;

    public Trip() {
    }

    public Trip(String tripId, String tripName, String destination, String startDate, String endDate) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}