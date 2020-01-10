package com.unict.riganozito.spout.entity;

public class Record {
    private String request;
    private Double number;
    private Double time;

    public Record(String r, Double n, Double t){
        this.request = r;
        this.number = n;
        this.time = t;
    }

    public String getRequest() {
        return request;
    }

    public Double getNumber() {
        return number;
    }

    public Double getTime() {
        return time;
    }
}
