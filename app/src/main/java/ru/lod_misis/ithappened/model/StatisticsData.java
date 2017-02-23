package ru.lod_misis.ithappened.model;


import java.util.HashMap;

public class StatisticsData {
    private String month;
    private HashMap<Integer, Integer> data;

    public StatisticsData(String month, HashMap<Integer, Integer> data) {
        this.month = month;
        this.data = data;
    }

    public String getMonth() {
        return month;
    }

    public HashMap<Integer, Integer> getData() {
        return data;
    }
}
