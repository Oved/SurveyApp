package com.example.surveyapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.surveyapp.R;
import com.example.surveyapp.data.DataESC;
import com.example.surveyapp.data.DataQTS;
import com.example.surveyapp.data.DataRES;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.data.DataTPE;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Tools {

    private static String KEY_DATASVY ="key_datasvy";
    private static String KEY_DATAQTS ="key_dataqts";
    private static String KEY_DATARES ="key_datares";
    private static String KEY_DATATPE ="key_datatpe";
    private static String KEY_DATAESC ="key_dataesc";

    private static String SP_DATASVY ="sp_datasvy";
    private static String SP_DATAQTS ="sp_dataqts";
    private static String SP_DATARES ="sp_datares";
    private static String SP_DATATPE ="sp_datatpe";
    private static String SP_DATAESC ="sp_dataesc";

    public static void saveDataSVY(List<DataSVY> dataSvy, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATASVY,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataSvy);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATASVY, jsonString);
        editor.apply();
    }

    public static List<DataSVY> getDataSVY(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATASVY,Context.MODE_PRIVATE);
        String json_listSVY = settings.getString(KEY_DATASVY, null);
        if (json_listSVY == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataSVY>>(){}.getType();
            return gson.fromJson(json_listSVY, listType);
        }
    }

    public static void saveDataQTS(List<DataQTS> dataQTS, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAQTS,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataQTS);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATAQTS, jsonString);
        editor.apply();
    }

    public static List<DataQTS> getDataQTS(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAQTS,Context.MODE_PRIVATE);
        String json_listQTS = settings.getString(KEY_DATAQTS, null);
        if (json_listQTS == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataQTS>>(){}.getType();
            return gson.fromJson(json_listQTS, listType);
        }
    }

    public static void saveDataRES(List<DataRES> dataRES, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATARES,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataRES);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATARES, jsonString);
        editor.apply();
    }

    public static List<DataRES> getDataRES(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATARES,Context.MODE_PRIVATE);
        String json_listRES = settings.getString(KEY_DATARES, null);
        if (json_listRES == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataRES>>(){}.getType();
            return gson.fromJson(json_listRES, listType);
        }
    }

    public static void saveDataTPE(List<DataTPE> dataTPE, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATATPE,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataTPE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATATPE, jsonString);
        editor.apply();
    }

    public static List<DataTPE> getDataTPE(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATATPE,Context.MODE_PRIVATE);
        String json_listTPE = settings.getString(KEY_DATATPE, null);
        if (json_listTPE == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataTPE>>(){}.getType();
            return gson.fromJson(json_listTPE, listType);
        }
    }
    public static void saveDataESC(List<DataESC> dataESC, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAESC,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataESC);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATAESC, jsonString);
        editor.apply();
    }

    public static List<DataESC> getDataESC(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAESC,Context.MODE_PRIVATE);
        String json_listESC = settings.getString(KEY_DATAESC, null);
        if (json_listESC == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataESC>>(){}.getType();
            return gson.fromJson(json_listESC, listType);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public static void showSnackBar(String message, View layoutContext, Context context) {
        Snackbar snackbar = Snackbar.make(layoutContext, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", v -> snackbar.dismiss());
        snackbar.show();
    }
}
