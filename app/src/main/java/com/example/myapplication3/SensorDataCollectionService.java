package com.example.myapplication3;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SensorDataCollectionService extends Service implements SensorEventListener, LocationListener {
    public SensorDataCollectionService() {
    }

    private Sensor accelerometer;
    private SensorManager sensorManager;
    private  FusedLocationProviderClient fusedLocationClient;
    private String currentLocation;
    private DatabaseHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        dbHelper = new DatabaseHelper(this.getApplicationContext());

        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        sensorManager.unregisterListener(this);
        stopSelf();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(currentLocation==null || currentLocation.length()==0) {
            getLocationData();
        }
        String accelerationData=""+sensorEvent.values[0]+ " "+sensorEvent.values[1]+
                " "+sensorEvent.values[2];
        dbHelper.insertRow(currentLocation,accelerationData);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void getLocationData(){
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation= location.getLatitude() + " " + location.getLongitude();
                }
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation= location.getLatitude() + " " + location.getLongitude();
    }
}