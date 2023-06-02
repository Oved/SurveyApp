package com.example.surveyapp.view;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.surveyapp.R;
import com.example.surveyapp.presenter.SearchPresenter;
import com.example.surveyapp.interfaces.iSearchPresenter;
import com.example.surveyapp.interfaces.iMainActivity;
import com.example.surveyapp.utils.Tools;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

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


    }

    private void loadView() {

        image = findViewById(R.id.image);

        progress = findViewById(R.id.progress_circular);
        layoutMain = findViewById(R.id.constraint_layout);
        buttonDelete = findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener((v) -> {
//
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
    public void hideProgres(){
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showSnackbar(String message) {
        Tools.showSnackBar(message,layoutMain,this);
    }
}