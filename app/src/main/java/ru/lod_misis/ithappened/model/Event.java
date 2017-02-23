package ru.lod_misis.ithappened.model;


import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

public class Event extends SugarRecord {
    private Long id;
    private String name;
    private List<PastEvent> listHappenedEvent;
    private Date lastModified;
    private List<Parameter> parameters;
    private boolean isMain;

    public Event() {}

    public Event(String name, List<PastEvent> listHappenedEvent, Date lastModified, List<Parameter> parameters, boolean isMain) {
        this.name = name;
        this.listHappenedEvent = listHappenedEvent;
        this.lastModified = lastModified;
        this.parameters = parameters;
        this.isMain = isMain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PastEvent> getListHappenedEvent() {
        return listHappenedEvent;
    }

    public void setListHappenedEvent(List<PastEvent> listHappenedEvent) {
        this.listHappenedEvent = listHappenedEvent;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    @Override
    public Long getId() {
        return id;
    }
}
