package com.example.surveyapp.data;

import com.google.gson.Gson;

public class DataQTS {
    private String codesvy;
    private String seqnqts;
    private String descqts;
    private String tiprqts;
    private String addtqts;
    private String depnqts;
    private String codetpe;
    private String codegrf;


    public String getCodesvy() {
        return codesvy;
    }

    public void setCodesvy(String value) {
        this.codesvy = value;
    }


    public String getSeqnqts() {
        return seqnqts;
    }

    public void setSeqnqts(String value) {
        this.seqnqts = value;
    }


    public String getDescqts() {
        return descqts;
    }

    public void setDescqts(String value) {
        this.descqts = value;
    }


    public String getTiprqts() {
        return tiprqts;
    }

    public void setTiprqts(String value) {
        this.tiprqts = value;
    }


    public String getAddtqts() {
        return addtqts;
    }

    public void setAddtqts(String value) {
        this.addtqts = value;
    }


    public String getDepnqts() {
        return depnqts;
    }

    public void setDepnqts(String value) {
        this.depnqts = value;
    }


    public String getCodetpe() {
        return codetpe;
    }

    public void setCodetpe(String value) {
        this.codetpe = value;
    }


    public String getCodegrf() {
        return codegrf;
    }

    public void setCodegrf(String value) {
        this.codegrf = value;
    }

    public String makeJson(){
        Gson gson= new Gson();
        String _jsonstring= gson.toJson(this);
        return  _jsonstring;
    }
}
