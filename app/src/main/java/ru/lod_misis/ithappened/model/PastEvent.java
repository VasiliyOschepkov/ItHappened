package ru.lod_misis.ithappened.model;


import com.orm.SugarRecord;

import java.util.Date;

public class PastEvent extends SugarRecord {
    private Long id;
    private Date dateEvent;
    private  Date lastModified;
    private int mark;
    private int number;
    private String comment;
    private boolean isDelete;
    private int idServer;

    public PastEvent() {}

    public PastEvent(Date dateEvent, Date lastModified, int mark, int number, String comment, boolean isDelete, int idServer) {
        this.dateEvent = dateEvent;
        this.lastModified = lastModified;
        this.mark = mark;
        this.number = number;
        this.comment = comment;
        this.isDelete = isDelete;
        this.idServer = idServer;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }
}
