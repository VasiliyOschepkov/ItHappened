package ru.lod_misis.ithappened.model;


import com.orm.SugarRecord;

public class Parameter extends SugarRecord{
    private Long id;
    private String nameParameter;
    private String typeParameter;

    public Parameter() {}

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
