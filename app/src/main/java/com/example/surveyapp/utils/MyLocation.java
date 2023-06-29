package com.example.surveyapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyLocation {
    public static Location getRecipientLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Obtener la última ubicación conocida
        Location lastKnownLocation = null;
        try {
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {
            Log.e("ERROR", "Error al obtener la ubicación: " + e.getMessage());
        }

        // Comprobar si hay conexión a Internet disponible
        if (Tools.isNetworkAvailable(context)) {
            // Obtener la ubicación actual utilizando el proveedor de red
            try {
                Location currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (currentLocation != null) {
                    return currentLocation;
                }
            } catch (SecurityException e) {
                Log.e("ERROR", "Error al obtener la ubicación: " + e.getMessage());
            }
        }

        // Devolver la última ubicación conocida en caché
        return lastKnownLocation;
    }

}
