package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        final EditText input_weight = findViewById(R.id.editTextWeight);
        final EditText input_height = findViewById(R.id.editTextHeight);
        final TextView viewBMI = findViewById(R.id.textView);
        final TextView viewExplain = findViewById(R.id.textView2);
        final TextView viewLoad = findViewById(R.id.textView3);

        final String filename = "data.txt";

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //未入力処理
                String str_weight = input_weight.getText().toString();
                String str_height = input_height.getText().toString();
                if(str_height.matches("") || str_weight.matches("")){
                    return;
                }

                double weight = Double.parseDouble(input_weight.getText().toString());
                double height = Double.parseDouble(input_height.getText().toString());
                height = height/100;
                double bmi = weight / (height * height);
                String str_bmi = String.valueOf(bmi);
                viewBMI.setText("BMI:" + String.valueOf(bmi));
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if(bmi<18.5){
                    viewExplain.setText("痩せています");
                }
                else if(bmi>=18.5 && bmi<25){
                    viewExplain.setText("標準範囲です");
                }
                else {
                    viewExplain.setText("肥満気味です");
                }
                saveFile(filename,str_weight,str_height,str_bmi);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = readFile(filename);
                if(str != null){
                    viewLoad.setText(str);
                }
                else{
                    viewLoad.setText("error");
                }
            }
        });
    }
    public void saveFile(String file, String w, String h, String b){
        try {
            FileOutputStream fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE);
            fileOutputStream.write(w.getBytes());
            fileOutputStream.write(",".getBytes());

            fileOutputStream.write(h.getBytes());
            fileOutputStream.write(",".getBytes());

            fileOutputStream.write(b.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String readFile(String file){
        String text = null;

        try{
            FileInputStream fileInputStream = openFileInput(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String lineBuffer;
            while((lineBuffer = reader.readLine()) != null) {
                text = lineBuffer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
