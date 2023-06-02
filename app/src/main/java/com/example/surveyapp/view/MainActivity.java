package com.example.surveyapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.surveyapp.R;
import com.example.surveyapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonSettings, buttonStartSurvey;
    private ConstraintLayout layoutMain;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutMain = findViewById(R.id.layout_main);
        buttonSettings = findViewById(R.id.button_settings);
        buttonStartSurvey = findViewById(R.id.button_start_survey);
        buttonSettings.setOnClickListener(v -> {
            startActivity(new Intent(this, InsertSurveyNumber.class));
        });
        buttonStartSurvey.setOnClickListener(v -> {
            if(Tools.getDataSVY(this) == null) {
                Tools.showSnackBar("No tienes encuestas pendientes, realiza la consulta en los ajustes", layoutMain, this);
            }else{
                if(Tools.getDataSVY(this).isEmpty()||Tools.getDataSVY(this).size()==0 ){
                    Tools.showSnackBar("Encuesta vacía", layoutMain, this);
                }else{
                    startActivity(new Intent(this, StartSurvey.class));
                }
            }
        });
        permission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        // Permiso no otorgado, manejar esta situación si es necesario
                        // ...
                    }
                }
                // Todos los permisos han sido otorgados, realizar las acciones necesarias
                // ...
            }
        }
    }

    private void permission() {
        List<String> permissionList = new ArrayList<>();

        // Verificar los permisos que aún no han sido otorgados
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        // Si hay permisos que no han sido otorgados, solicitarlos al usuario
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        } else {
            // Los permisos ya han sido otorgados, realizar las acciones necesarias
            // ...
        }
    }
}