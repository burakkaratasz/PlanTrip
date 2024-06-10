package com.example.plantrip;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    public String tripId;
    public String tripName;
    public String destination;
    public String startDate;
    public String endDate;
    public String tripType;
    public List<String> participants;
    public String transportation;

    public Trip() {
        this.participants = new ArrayList<>();
    }

    public Trip(String tripId, String tripName, String destination, String startDate, String endDate, String tripType, List<String> participants, String transportation) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tripType = tripType;
        this.participants = participants != null ? participants : new ArrayList<String>();
        this.transportation = transportation;
    }
}
