package com.a2valdez.ulp_moviles_practico_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.a2valdez.ulp_moviles_practico_6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Lector lector;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_SMS},2500);
        }
         intent = new Intent(this, Lector.class);
    }

    public void iniciarServicio(View view){
        startService(intent);
    }

    public void detenerServicio(View view){
        stopService(intent);
    }
}