package com.example.surveyapp.view;

import static android.provider.ContactsContract.CommonDataKinds.Event.START_DATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surveyapp.R;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.utils.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button buttonSettings, buttonStartSurvey, buttonSendSurvey;
    private ConstraintLayout layoutMain;

    private String currentDate;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Tools.permission(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO analizar el ciclo de vida
        if (Tools.getSaveSurveys(this) != null)
            buttonSendSurvey.setVisibility(View.VISIBLE);
        else
            buttonSendSurvey.setVisibility(View.GONE);
    }

    private void initView() {
        layoutMain = findViewById(R.id.layout_main);
        buttonSettings = findViewById(R.id.button_settings);
        buttonStartSurvey = findViewById(R.id.button_start_survey);
        buttonSendSurvey = findViewById(R.id.button_send_surveys);

        buttonStartSurvey.setOnClickListener(v -> {
            if (startSurvey())
                startActivity(new Intent(this, StartSurvey.class));
        });

        buttonSettings.setOnClickListener(v -> {
            if (Tools.arePermissionsGranted(this))
                startActivity(new Intent(this, InsertSurveyNumber.class));
            else
                Tools.permission(this);
        });

        Date date = Calendar.getInstance().getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormat.format(date);

        buttonSendSurvey.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            ScrollView scrollView = new ScrollView(MainActivity.this);
            LinearLayout linearLayout = new LinearLayout(MainActivity.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(40, 40, 40, 40);
            TextView textView1 = new TextView(MainActivity.this);
            textView1.setText(Tools.getSaveSurveysInJson(this));
            linearLayout.addView(textView1);
            scrollView.addView(linearLayout);
            builder.setView(scrollView);

            AlertDialog dialog = builder.create();
            dialog.show();
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

            start = validateDate(dateInit, dateFinish);  //TODO corregir este valor, quitar la negación
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
}