package com.example.surveyapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.surveyapp.R;
import com.example.surveyapp.data.Answers;
import com.example.surveyapp.data.DataESC;
import com.example.surveyapp.data.DataQTS;
import com.example.surveyapp.data.DataRES;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.data.DataTPE;
import com.example.surveyapp.data.SurveyResponses;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static String KEY_USER_NAME = "key_user_name";

    private static String KEY_SURVEY_NUMBER = "key_survey_number";


    private static String SP_CODE_ALPHA = "sp_code_alpha";
    private static String SP_DATASVY = "sp_datasvy";
    private static String SP_DATAQTS = "sp_dataqts";
    private static String SP_DATARES = "sp_datares";
    private static String SP_DATATPE = "sp_datatpe";
    private static String SP_DATAESC = "sp_dataesc";
    private static String SP_SURVEYS = "sp_surveys";
    private static String SP_USER_NAME = "sp_user_name";
    private static String SP_LOCATION = "sp_location";

    private static String SP_SURVEY_NUMBER = "sp_survey_number";
    private static final int REQUEST_PERMISSIONS = 1;

    public static String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public static String[] permissionsLocation = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    public static void setLatitude(Context ctx, String latitude){
        SharedPreferences settings = ctx.getSharedPreferences(SP_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("latitude", latitude);
        editor.apply();
    }
    public static String getLatitude(Context ctx){
        SharedPreferences settings = ctx.getSharedPreferences(SP_LOCATION, Context.MODE_PRIVATE);
        return settings.getString("latitude", "0");
    }
    public static void setLongitude(Context ctx, String longitude){
        SharedPreferences settings = ctx.getSharedPreferences(SP_LOCATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("longitude", longitude);
        editor.apply();

    }
    public static String getLongitude(Context ctx){
        SharedPreferences settings = ctx.getSharedPreferences(SP_LOCATION, Context.MODE_PRIVATE);
        return settings.getString("longitude", "0");
    }

    public static void saveLocation(Context ctx) {
        Location actualLocation = MyLocation.getRecipientLocation(ctx);
        if (actualLocation!=null) {
            setLatitude(ctx, String.valueOf(actualLocation.getLatitude()));
            setLongitude(ctx, String.valueOf(actualLocation.getLongitude()));
        }
    }

    public static void generateAlphanumericCode(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_CODE_ALPHA, Context.MODE_PRIVATE);

        String character = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int ind = random.nextInt(character.length());
            code.append(character.charAt(ind));
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_CODE_ALPHA, code.toString());
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


    public static void deleteDataSVY(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATASVY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_DATASVY);
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

    public static void deleteDataQTS(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAQTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_DATAQTS);
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


    public static void deleteDataRES(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATARES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_DATARES);
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


    public static void deleteDataTPE(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATATPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(SP_DATATPE);
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


    public static void deleteDataESC(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_DATAESC, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_DATAESC);
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

    public static int generateCodeSurvey(Context ctx) {
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
        deleteDataSVY(ctx);
        deleteDataQTS(ctx);
        deleteDataRES(ctx);
        deleteDataTPE(ctx);
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
            ActivityCompat.requestPermissions(ctx, permissionList.toArray(new String[0]), REQUEST_PERMISSIONS);
        } else {
            // Los permisos ya han sido otorgados, realizar las acciones necesarias
            // ...
        }
    }

    public static boolean arePermissionsGranted(Context context) {
        for (String permission : permissions) {
            int permissionStatus = ContextCompat.checkSelfPermission(context, permission);
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Verifica el permiso de almacenamiento o localización", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        // Todos los permisos están otorgados
        return true;
    }

    public static boolean permissionMEDIA_IMAGESGranted(Context context) {
        int permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES);
        return (permissionStatus == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean arePermissionsGrantedLocation(Context context) {
        for (String permission : permissionsLocation) {
            int permissionStatus = ContextCompat.checkSelfPermission(context, permission);
            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Debes otorgar el permiso de localización", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        // Todos los permisos están otorgados
        return true;
    }

    public static boolean saveImage(String url, String nameImage) {
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File file = new File(directory, nameImage);

        try {
            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void savePicture(Context context, String imageUrl, String name) {
        int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);
                return;
            }
        }

        new Thread(() -> {
            try {
                URL url = new URL(imageUrl);

                // Establecer conexión
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();

                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File imageFile = new File(directory, name);
                //File imageFile = new File(directory, name + ".png");

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                if (!imageFile.exists()) {
                    imageFile.createNewFile();
                }

                FileOutputStream output = new FileOutputStream(imageFile);

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }

                output.close();
                input.close();
                scanImage(context, imageFile.getAbsolutePath());

                Log.d("ImageDownloader", "La imagen se ha guardado correctamente en el dispositivo.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void scanImage(Context context, String imagePath) {
        MediaScannerConnection.scanFile(context, new String[]{imagePath}, null,
                (path, uri) -> {
                });
    }

    public static void saveAnswers(List<Answers> dataResponsesList, Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_SURVEYS, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String jsonString = settings.getString(KEY_SURVEYS, null);
        List<Answers> existingSurveys = new ArrayList<>();

        if (jsonString != null) {
            Type surveyListType = new TypeToken<List<Answers>>() {}.getType();
            existingSurveys = gson.fromJson(jsonString, surveyListType);
        }
        existingSurveys.addAll(dataResponsesList);

        String updatedJsonString = gson.toJson(existingSurveys);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_SURVEYS, updatedJsonString);
        editor.apply();
    }

    public static List<Answers> getSavedAnswers(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_SURVEYS, Context.MODE_PRIVATE);
        String json_listSVY = settings.getString(KEY_SURVEYS, null);
        if (json_listSVY == null)
            return null;
        else {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Answers>>() {
            }.getType();
            return gson.fromJson(json_listSVY, listType);
        }
    }

    public static void clearAnswers(Context ctx) {
        SharedPreferences settings = ctx.getSharedPreferences(SP_SURVEYS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_SURVEYS); // Eliminar la clave correspondiente a las respuestas
        editor.apply();
    }

    public static void userName(String name, Context ctx){
        SharedPreferences settings = ctx.getSharedPreferences(SP_USER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_USER_NAME, name);
        editor.apply();
    }

    public static String getUserName(Context ctx){
        SharedPreferences settings = ctx.getSharedPreferences(SP_USER_NAME, Context.MODE_PRIVATE);
        return settings.getString(KEY_USER_NAME,"");
    }

    public static boolean checkLocationEnabled(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isLocationEnabled) {
            // La ubicación está desactivada, mostrar un diálogo para solicitar al usuario que la active
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            dialogBuilder.setMessage("La ubicación está desactivada. ¿Desea activarla?")
                    .setCancelable(false)
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Abrir la configuración de ubicación del dispositivo
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(intent);
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }
        return isLocationEnabled;
    }

}
