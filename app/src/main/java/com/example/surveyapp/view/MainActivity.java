package com.example.surveyapp.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surveyapp.BuildConfig;
import com.example.surveyapp.R;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.interfaces.insertSurveysMySql.MainPresenter;
import com.example.surveyapp.interfaces.insertSurveysMySql.MainView;
import com.example.surveyapp.presenter.MainPresenterImpl;
import com.example.surveyapp.utils.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainView {

    private Button buttonSettings, buttonStartSurvey, buttonSendSurvey;
    private ConstraintLayout layoutMain;
    private ProgressBar progress;
    private TextView text_universe, text_subtitle_universe, text_name_survey;

    private String currentDate;
    private SimpleDateFormat dateFormat;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Tools.permission(this);
        presenter = new MainPresenterImpl(this, this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(!Tools.permissionMEDIA_IMAGESGranted(this))
                request_permission_launcher().launch(Manifest.permission.READ_MEDIA_IMAGES);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO analizar el ciclo de vida

        if (Tools.getSaveSurveys(this) != null)
            buttonSendSurvey.setVisibility(View.VISIBLE);
        else
            buttonSendSurvey.setVisibility(View.VISIBLE); //TODO cambiar a gone

        List<DataSVY> dataSVY = Tools.getDataSVY(this);
        if (dataSVY!=null){
            text_name_survey.setVisibility(View.VISIBLE);
            text_universe.setVisibility(View.VISIBLE);
            text_subtitle_universe.setVisibility(View.VISIBLE);
            text_name_survey.setText(dataSVY.get(0).getCodesvy());
            text_subtitle_universe.setText(dataSVY.get(0).getUnivsvy());
        }else{
            text_name_survey.setVisibility(View.GONE);
            text_universe.setVisibility(View.GONE);
            text_subtitle_universe.setVisibility(View.GONE);
        }
        if (Tools.isNetworkAvailable(this) && Tools.arePermissionsGrantedLocation(this)) {
            if (Tools.checkLocationEnabled(this))
                Tools.saveLocation(this);
        }
    }

    private void initView() {
        layoutMain = findViewById(R.id.layout_main);
        progress = findViewById(R.id.progress);
        text_name_survey = findViewById(R.id.text_name_survey);
        text_universe = findViewById(R.id.text_universe);
        text_subtitle_universe = findViewById(R.id.text_subtitle_universe);
        buttonSettings = findViewById(R.id.button_settings);
        buttonStartSurvey = findViewById(R.id.button_start_survey);
        buttonSendSurvey = findViewById(R.id.button_send_surveys);

        buttonStartSurvey.setOnClickListener(v -> {
            if(Tools.checkLocationEnabled(this)) {
                if (startSurvey())
                    startActivity(new Intent(this, StartSurvey.class));
            }else {
                Toast.makeText(this, "Debes activar la ubicación para continuar", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSettings.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                if (Tools.arePermissionsGranted(this)) {
                    if (Tools.checkLocationEnabled(this))
                        startActivity(new Intent(this, InsertSurveyNumber.class));
                } else {
                    Tools.permission(this);
                }
            } else {
                if (Tools.arePermissionsGrantedLocation(this)) {
                    if (Tools.checkLocationEnabled(this))
                        startActivity(new Intent(this, InsertSurveyNumber.class));
                }
            }
        });

        Date date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormat.format(date);

        buttonSendSurvey.setOnClickListener(v -> {
            if (Tools.getSavedAnswers(this) !=  null)
                presenter.insertSurveysInDB();
            else
                Tools.showSnackBar("No tienes encuestas guardadas", layoutMain,this);
        });
    }


    private boolean startSurvey() {
        boolean start = true;
        List<DataSVY> dataSVYList = Tools.getDataSVY(this);
        if (dataSVYList == null) {
            Tools.showSnackBar("No tienes encuestas pendientes, realiza la consulta en los ajustes", layoutMain, this);
            start = false;
        }

        if (dataSVYList != null) {
            String dateInit = dataSVYList.get(0).getDinisvy();
            String dateFinish = dataSVYList.get(0).getDfinsvy();

            start = validateDate(dateInit, dateFinish);
            if (!start)
                Tools.showSnackBar("Encuesta disponible solo entre " +
                        dateInit + " y " + dateFinish + " de 8:00 AM a 5:00 PM ", layoutMain, this);

        }
        return start;
    }

    private boolean validateDate(String dateInit, String dateFin) {
        try {
            Date dateInitial = dateFormat.parse(dateInit);
            Date dateFinish = dateFormat.parse(dateFin);
            Date currentDateUser = dateFormat.parse(currentDate);

            return currentDateUser.compareTo(dateInitial) >= 0 && currentDateUser.compareTo(dateFinish) <= 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "Insertada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Tools.showSnackBar(message,layoutMain,this);
    }


    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    private ActivityResultLauncher<String> request_permission_launcher(){
        return registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                isGranted -> {
            if (!isGranted) {
                Toast.makeText(this, "Otorga el permiso de almacenamiento", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(this::goSettings,1500);
            }
                });
    }

    private void goSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}