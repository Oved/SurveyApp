package com.example.surveyapp.data;

import com.google.gson.Gson;

public class DataTPE {

    public String makeJson(){
        Gson gson= new Gson();
        String _jsonstring= gson.toJson(this);
        return  _jsonstring;
    }
}
