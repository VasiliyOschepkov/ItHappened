package ru.lod_misis.ithappened.model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Event extends RealmObject{
    @PrimaryKey
    private long id;
    private String name;
    private RealmList<PastEvent> listHappenedEvent;
    private Date lastModified;
    private RealmList<Parameter> parameters;
    private boolean isMain;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<PastEvent> getListHappenedEvent() {
        return listHappenedEvent;
    }

    public void setListHappenedEvent(RealmList<PastEvent> listHappenedEvent) {
        this.listHappenedEvent = listHappenedEvent;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public RealmList<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(RealmList<Parameter> parameters) {
        this.parameters = parameters;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}
