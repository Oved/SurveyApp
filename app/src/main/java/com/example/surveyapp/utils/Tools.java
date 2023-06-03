package com.example.surveyapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.surveyapp.R;
import com.example.surveyapp.data.DataESC;
import com.example.surveyapp.data.DataQTS;
import com.example.surveyapp.data.DataRES;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.data.DataTPE;
import com.example.surveyapp.data.SurveyResponses;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Tools {

    private static String KEY_CODE_ALPHA = "key_code_alpha";
    private static String KEY_DATASVY = "key_datasvy";
    private static String KEY_DATAQTS = "key_dataqts";
    private static String KEY_DATARES = "key_datares";
    private static String KEY_DATATPE = "key_datatpe";
    private static String KEY_DATAESC = "key_dataesc";
    private static String KEY_SURVEYS = "key_surveys";

    private static String KEY_SURVEY_NUMBER = "key_survey_number";


    private static String SP_CODE_ALPHA = "sp_code_alpha";
    private static String SP_DATASVY = "sp_datasvy";
    private static String SP_DATAQTS = "sp_dataqts";
    private static String SP_DATARES = "sp_datares";
    private static String SP_DATATPE = "sp_datatpe";
    private static String SP_DATAESC = "sp_dataesc";
    private static String SP_SURVEYS = "sp_surveys";

    private static String SP_SURVEY_NUMBER = "sp_survey_number";
    private static final int PERMISSION_REQUEST_CODE = 1;

    public static String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    public static void generateAlphanumericCode(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_CODE_ALPHA, Context.MODE_PRIVATE);

        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int indice = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(indice));
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_CODE_ALPHA, codigo.toString());
        editor.apply();
    }

    public static String getCodeAlpha(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_CODE_ALPHA, Context.MODE_PRIVATE);
        String code = settings.getString(KEY_CODE_ALPHA, "");
        return code;
    }

    public static void saveDataSVY(List<DataSVY> dataSvy, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATASVY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataSvy);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATASVY, jsonString);
        editor.apply();
    }

    public static List<DataSVY> getDataSVY(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATASVY, Context.MODE_PRIVATE);
        String json_listSVY = settings.getString(KEY_DATASVY, null);
        if (json_listSVY == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataSVY>>() {
            }.getType();
            return gson.fromJson(json_listSVY, listType);
        }
    }

    public static void saveDataQTS(List<DataQTS> dataQTS, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAQTS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataQTS);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATAQTS, jsonString);
        editor.apply();
    }

    public static List<DataQTS> getDataQTS(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAQTS, Context.MODE_PRIVATE);
        String json_listQTS = settings.getString(KEY_DATAQTS, null);
        if (json_listQTS == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataQTS>>() {
            }.getType();
            return gson.fromJson(json_listQTS, listType);
        }
    }

    public static void saveDataRES(List<DataRES> dataRES, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATARES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataRES);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATARES, jsonString);
        editor.apply();
    }

    public static List<DataRES> getDataRES(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATARES, Context.MODE_PRIVATE);
        String json_listRES = settings.getString(KEY_DATARES, null);
        if (json_listRES == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataRES>>() {
            }.getType();
            return gson.fromJson(json_listRES, listType);
        }
    }

    public static void saveDataTPE(List<DataTPE> dataTPE, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATATPE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataTPE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATATPE, jsonString);
        editor.apply();
    }

    public static List<DataTPE> getDataTPE(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATATPE, Context.MODE_PRIVATE);
        String json_listTPE = settings.getString(KEY_DATATPE, null);
        if (json_listTPE == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataTPE>>() {
            }.getType();
            return gson.fromJson(json_listTPE, listType);
        }
    }

    public static void saveDataESC(List<DataESC> dataESC, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAESC, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(dataESC);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DATAESC, jsonString);
        editor.apply();
    }

    public static List<DataESC> getDataESC(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAESC, Context.MODE_PRIVATE);
        String json_listESC = settings.getString(KEY_DATAESC, null);
        if (json_listESC == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<DataESC>>() {
            }.getType();
            return gson.fromJson(json_listESC, listType);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public static void showSnackBar(String message, View layoutContext, Context context) {
        Snackbar snackbar = Snackbar.make(layoutContext, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", v -> snackbar.dismiss());
        snackbar.show();
    }

    public static int generateCodeSurvey(Context ctx){
        SharedPreferences settings = ctx.getSharedPreferences(SP_SURVEY_NUMBER, Context.MODE_PRIVATE);
        int currentCode = settings.getInt(KEY_SURVEY_NUMBER, 0);
        int newCode = currentCode + 1;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_SURVEY_NUMBER, newCode);
        editor.apply();
        return newCode;
    }


    public static void saveSurvey(SurveyResponses dataResponses, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_SURVEYS, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String jsonString = settings.getString(KEY_SURVEYS, null);
        List<SurveyResponses> existingSurveys = new ArrayList<>();

        if (jsonString != null) {
            Type surveyListType = new TypeToken<List<SurveyResponses>>() {
            }.getType();
            existingSurveys = gson.fromJson(jsonString, surveyListType);
        }

        existingSurveys.add(dataResponses);

        String updatedJsonString = gson.toJson(existingSurveys);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_SURVEYS, updatedJsonString);
        editor.apply();
    }

    public static List<SurveyResponses> getSaveSurveys(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_SURVEYS, Context.MODE_PRIVATE);
        String json_listSVY = settings.getString(KEY_SURVEYS, null);
        if (json_listSVY == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<SurveyResponses>>() {
            }.getType();
            return gson.fromJson(json_listSVY, listType);
        }
    }

    public static String getSaveSurveysInJson(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_SURVEYS, Context.MODE_PRIVATE);
        String json_listSVY = settings.getString(KEY_SURVEYS, null);
        if (json_listSVY == null)
            return null;
        else {
            return json_listSVY;
        }
    }

    public static void deleteAllSurveys(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_SURVEYS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_SURVEYS);
        editor.apply();
    }
    public static void permission(Activity ctx) {
        List<String> permissionList = new ArrayList<>();

        // Verificar los permisos que aún no han sido otorgados
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(ctx, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        // Si hay permisos que no han sido otorgados, solicitarlos al usuario
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(ctx, permissionList.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        } else {
            // Los permisos ya han sido otorgados, realizar las acciones necesarias
            // ...
        }
    }

    public static boolean arePermissionsGranted(Context context) {
        for (String permission : permissions) {
            int permissionStatus = ContextCompat.checkSelfPermission(context, permission);
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                // El permiso no está otorgado
                return false;
            }
        }
        // Todos los permisos están otorgados
        return true;
    }

}
