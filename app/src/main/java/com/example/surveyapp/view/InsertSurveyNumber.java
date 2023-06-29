package com.example.surveyapp.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.surveyapp.R;
import com.example.surveyapp.presenter.SearchPresenter;
import com.example.surveyapp.interfaces.searchSurvey.iSearchPresenter;
import com.example.surveyapp.interfaces.searchSurvey.iMainActivity;
import com.example.surveyapp.utils.Tools;

public class InsertSurveyNumber extends AppCompatActivity implements iMainActivity {

    private ImageButton buttonBack;
    int REQUEST_CODE = 123;
    private ImageView image;
    private ProgressBar progress;
    private ConstraintLayout layoutMain;
    private Button buttonSearchSurvey, buttonDelete;
    private EditText textSearchSurvey;
    private iSearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_survey_number);

        presenter = new SearchPresenter(this, this);
        loadView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(!Tools.permissionMEDIA_IMAGESGranted(this))
                request_permission_launcher().launch(Manifest.permission.READ_MEDIA_IMAGES);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Tools.isNetworkAvailable(this) && Tools.arePermissionsGrantedLocation(this)) {
            if (Tools.checkLocationEnabled(this))
                Tools.saveLocation(this);
        }
    }

    private void loadView() {

        image = findViewById(R.id.image);

        progress = findViewById(R.id.progress_circular);
        layoutMain = findViewById(R.id.constraint_layout);
        buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener((v) -> {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Eliminando encuestas descargadas...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
            new Handler().postDelayed((Runnable) () -> {
                Tools.deleteAllSurveys(this);
                Tools.clearAnswers(this);
                progressDialog.dismiss();

            }, 1500);
        });
        textSearchSurvey = findViewById(R.id.etSurveyNumber);

        buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener((view) -> {
            finish();
        });

        buttonSearchSurvey = findViewById(R.id.button_save);
        buttonSearchSurvey.setOnClickListener((v) -> {
            searchData();
        });
    }

    private void searchData() {
        String search = textSearchSurvey.getText().toString().trim();
        if (!search.equals(""))
            presenter.searchData(search);
        else
            textSearchSurvey.setError("Ingresa código de la encuesta");
    }

    @Override
    public void showData(String response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView textView = new TextView(this);
        textView.setText(response);
        builder.setView(textView);
        builder.show();

    }

    @Override
    public void showProgress(){
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress(){
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showSnackbar(String message) {
        Tools.showSnackBar(message,layoutMain,this);
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