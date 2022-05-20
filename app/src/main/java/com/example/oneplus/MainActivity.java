package com.example.oneplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerOne, spinnerTwo;
    Button btnSubmit;
    String[] listOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerOne= findViewById(R.id.spinnerOne);
        spinnerTwo= findViewById(R.id.spinnerTwo);
        btnSubmit= findViewById(R.id.btnSubmit);
        populateSpinnerOne();
        populateSpinnerTwo();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String One= spinnerOne.getSelectedItem().toString();
                String Two= spinnerTwo.getSelectedItem().toString();
                Toast.makeText(MainActivity.this,One +" , "+Two, Toast.LENGTH_SHORT).show();
                ApplyActivity(One,Two);
            }

            private void ApplyActivity(String one, String two) {
                switch (one){
                    case "ENGLISH":{
                        switch (two){
                            case "TELUGU":{
                                openActivity2();
                            }break;
                            case "HINDI":{
                                openMainActivity2();
                            }break;
                        }
                    }break;
                    case "TELUGU" :{
                        switch (two){
                            case "ENGLISH":{
                                openActivity2();
                            }break;
                            case "HINDI":{
                                openActivity3();
                            }break;
                        }

                    }break;
                    case "HINDI":{
                        switch (two){
                            case "ENGLISH":{
                                openMainActivity2();
                            }break;
                            case "TELUGU":{
                                openActivity3();
                            }break;
                        }
                    }break;
                }
            }
        });
    }

    private void openActivity3() {
        Intent intent= new Intent(this, Activity3.class);
        startActivity(intent);
    }

    private void openMainActivity2() {
        Intent intent= new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    private void openActivity2() {
        Intent intent= new Intent(this, Activity2.class);
        startActivity(intent);
    }

    private void populateSpinnerTwo() {
        ArrayAdapter<String> listTwoAdaptor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinnerTwo));
        listTwoAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTwo.setAdapter(listTwoAdaptor);
    }

    private void populateSpinnerOne() {
        ArrayAdapter<String> listOneAdaptor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinnerOne));
        listOneAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOne.setAdapter(listOneAdaptor);

    }
}