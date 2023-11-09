package com.effortlogger;

public class Entry {

    private String number;
    private String date;
    private String start;
    private String stop;
    private String time;
    private String lifeCycle;
    private String category;
    private String deliverable;

    public Entry(String number, String date, String start, String stop, String time, String lifeCycle, String category, String deliverable) {
        this.number = number;
        this.date = date;
        this.start = start;
        this.stop = stop;
        this.time = time;
        this.lifeCycle = lifeCycle;
        this.category = category;
        this.deliverable = deliverable;
    }

    public String getNumber() {
        return this.number;
    }

    public String getDate() {
        return this.date;
    }
    public String getStart() {
        return this.start;
    }
    public String getStop() {
        return this.stop;
    }
    public String getTime() {
        return this.time;
    }
    public String getLifeCycle() {
        return this.lifeCycle;
    }
    public String getCategory() {
        return this.category;
    }
    public String getDeliverable() {
        return this.deliverable;
    }
}
