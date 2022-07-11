package com.example.tamobilecomputing19030012;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {
    Button semarang, bandung, magelang, aceh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        semarang = (Button) findViewById(R.id.btnsemarang);
        bandung = (Button) findViewById(R.id.btnbandung);
        magelang = (Button) findViewById(R.id.btnmagelang);
        aceh = (Button) findViewById(R.id.btnaceh);


        semarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, KotaSemarang.class);
                startActivity(intent);
            }
        });
        bandung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, KotaBandung.class);
                startActivity(intent);
            }
        });

        magelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, KotaMagelang.class);
                startActivity(intent);
            }
        });

        aceh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, BandaAceh.class);
                startActivity(intent);
            }
        });

    }
}