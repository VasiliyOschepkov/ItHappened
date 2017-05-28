package ru.lod_misis.ithappened.model;


import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class Parameter extends RealmObject{
    @Ignore
    public final static String TYPE_MARK = "Type: Mark";
    @Ignore
    public final static String TYPE_NUMBER = "Type: Number";
    @Ignore
    public final static String TYPE_COMMENT = "Type: Comment";

    private String nameParameter;
    private String typeParameter;

    public Parameter() {
    }

    public Parameter(String nameParameter, String typeParameter) {
        this.nameParameter = nameParameter;
        this.typeParameter = typeParameter;
    }

    public String getNameParameter() {
        return nameParameter;
    }

    public void setNameParameter(String nameParameter) {
        this.nameParameter = nameParameter;
    }

    public String getTypeParameter() {
        return typeParameter;
    }

    public void setTypeParameter(String typeParameter) {
        this.typeParameter = typeParameter;
    }
}
