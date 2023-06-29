package com.example.surveyapp.view.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.surveyapp.R;
import com.example.surveyapp.utils.Tools;
import com.example.surveyapp.view.MainActivity;

import java.util.UUID;

public class ActivitySplash extends AppCompatActivity {

    private ImageView imageSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageSplash = findViewById(R.id.imageSplash);

        if (Tools.getCodeAlpha(this).isEmpty())
            Tools.generateAlphanumericCode(this);
        if (Tools.isNetworkAvailable(this) && Tools.arePermissionsGrantedLocation(this)) {
            if (Tools.checkLocationEnabled(this))
                Tools.saveLocation(this);
        }

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.move_bottom);

        imageSplash.setAnimation(animation);

        new Handler().postDelayed(this::goToMain, 3000);
    }

    public void goToMain(){
            startActivity(new Intent(this, MainActivity.class));
            finish();
    }
}