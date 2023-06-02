package com.example.surveyapp.data;

import com.google.gson.Gson;

public class DataESC {
    private String codetpe;
    private String seqnesc;
    private String descesc;
    private String valresc;

    public String getCodetpe() {
        return codetpe;
    }

    public void setCodetpe(String value) {
        this.codetpe = value;
    }


    public String getSeqnesc() {
        return seqnesc;
    }

    public void setSeqnesc(String value) {
        this.seqnesc = value;
    }


    public String getDescesc() {
        return descesc;
    }

    public void setDescesc(String value) {
        this.descesc = value;
    }


    public String getValresc() {
        return valresc;
    }

    public void setValresc(String value) {
        this.valresc = value;
    }

    public String makeJson(){
        Gson gson= new Gson();
        String _jsonstring= gson.toJson(this);
        return  _jsonstring;
    }
}
