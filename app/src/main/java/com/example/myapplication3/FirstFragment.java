package com.example.myapplication3;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication3.databinding.FragmentFirstBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

public class FirstFragment extends Fragment{

    private FragmentFirstBinding binding;

    TextView posOut ;

    private MainActivity activity;

    private Boolean isServiceRunning = false;
    private Intent  intent;

    private Sensor accelerometer;
    SensorManager sensorManager;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        activity = (MainActivity) getActivity();
        binding = FragmentFirstBinding.inflate(inflater, container, false);


         sensorManager= (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        posOut=getView().findViewById(R.id.pos_out);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isServiceRunning){
                    getActivity().stopService(intent);
                    isServiceRunning=false;
                    posOut.setText(""+isServiceRunning);
                }
                else{
                    intent = new Intent(getActivity().getApplicationContext(), SensorDataCollectionService.class);
                    getActivity().startService(intent);
                    isServiceRunning=true;

                    posOut.setText(""+isServiceRunning);
                }

            }
        });
        binding.goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.mapsFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}