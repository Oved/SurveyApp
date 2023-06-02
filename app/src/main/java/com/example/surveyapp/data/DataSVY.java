package com.example.surveyapp.data;

import com.google.gson.Gson;

public class DataSVY {

    private String codesvy;
    private String namesvy;
    private String objesvy;
    private String datesvy;
    private String dinisvy;
    private String dfinsvy;

    public String getCodesvy() {
        return codesvy;
    }

    public void setCodesvy(String codesvy) {
        this.codesvy = codesvy;
    }

    public String getNamesvy() {
        return namesvy;
    }

    public void setNamesvy(String namesvy) {
        this.namesvy = namesvy;
    }

    public String getObjesvy() {
        return objesvy;
    }

    public void setObjesvy(String objesvy) {
        this.objesvy = objesvy;
    }

    public String getDatesvy() {
        return datesvy;
    }

    public void setDatesvy(String datesvy) {
        this.datesvy = datesvy;
    }

    public String getDinisvy() {
        return dinisvy;
    }

    public void setDinisvy(String dinisvy) {
        this.dinisvy = dinisvy;
    }

    public String getDfinsvy() {
        return dfinsvy;
    }

    public void setDfinsvy(String dfinsvy) {
        this.dfinsvy = dfinsvy;
    }

    public String makeJson(){
        Gson gson= new Gson();
        String _jsonstring= gson.toJson(this);
        return  _jsonstring;
    }
}
