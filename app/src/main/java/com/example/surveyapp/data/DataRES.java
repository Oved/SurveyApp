package com.example.surveyapp.data;

import com.google.gson.Gson;

public class DataRES {
    private String codesvy;
    private String seqnqts;
    private String ordnres;
    private String valrres;
    private String descres;
    private String fotores;


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


    public String getOrdnres() {
        return ordnres;
    }

    public void setOrdnres(String value) {
        this.ordnres = value;
    }


    public String getValrres() {
        return valrres;
    }

    public void setValrres(String value) {
        this.valrres = value;
    }


    public String getDescres() {
        return descres;
    }

    public void setDescres(String value) {
        this.descres = value;
    }


    public String getFotores() {
        return fotores;
    }

    public void setFotores(String value) {
        this.fotores = value;
    }

    public String makeJson(){
        Gson gson= new Gson();
        String _jsonstring= gson.toJson(this);
        return  _jsonstring;
    }
}

