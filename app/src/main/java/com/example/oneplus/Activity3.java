package com.example.oneplus;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.Locale;

public class Activity3 extends AppCompatActivity {

    public TextView txtResult;
    TextToSpeech textToSpeech;
    TextToSpeech textToSpeech2;
    public TextView txtResult2;


    // Create an Hindi-Telugu translator:
    TranslatorOptions options =
            new TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.TELUGU)
                    .setTargetLanguage(TranslateLanguage.HINDI)
                    .build();
    final Translator TeluguHindiTranslator =
            Translation.getClient(options);

    TranslatorOptions options2 =
            new TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.HINDI)
                    .setTargetLanguage(TranslateLanguage.TELUGU)
                    .build();
    final Translator HindiTeluguTranslator =
            Translation.getClient(options2);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_translator);
        txtResult = findViewById(R.id.txtResult1);
        txtResult2= findViewById(R.id.txtResult2);
        txtResult.setText("Telugu to Hindi");
        txtResult2.setText("Hindi to Telugu");

        DownloadConditions conditions = new DownloadConditions.Builder()
                .build();


        TeluguHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        (OnSuccessListener) o -> {
                            // Model downloaded successfully. Okay to start translating.
                            // (Set a flag, unhide the translation UI, etc.)
                        })
                .addOnFailureListener(
                        e -> {
                            // Model couldn’t be downloaded or other internal error.
                            // ...
                        });


        textToSpeech= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(new Locale("hi","IN"));
                }
            }
        });
        textToSpeech2= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech2.setLanguage(new Locale("te","IN"));
                }
            }
        });

    }


    public void getSpeechInput(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"te-IN");
        if(intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent,10);
        }
        else
        {
            Toast.makeText(this,"Your device Dont Support",Toast.LENGTH_SHORT).show();
        }
    }
    public void getSpeechInput2(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi-IN");

        if(intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent,5);
        }
        else
        {
            Toast.makeText(this,"Your device Dont Support",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case 10:
                if(resultCode== RESULT_OK && data !=null){
                    ArrayList<String> result=  data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String cap = result.get(0);
                    txtResult.setText(cap);

                    setUpConditions(cap);

                }
                break;
            case 5:
                if(resultCode== RESULT_OK && data !=null){
                    ArrayList<String> result=  data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String cap = result.get(0);
                    txtResult2.setText(cap);

                    setUpConditions2(cap);

                }
                break;
        }
    }


    public void setUpConditions(String text1) {
        // Create an Telugu-Hindi translator:
        TeluguHindiTranslator.translate(text1)
                .addOnSuccessListener(
                        (OnSuccessListener) translatedText ->{
                            txtResult.setText("");
                            txtResult.append((String) translatedText);
                            textToSpeech.speak((String) translatedText,TextToSpeech.QUEUE_FLUSH, null);
                            Log.i("TAG","Translation is "+ (String) translatedText);
                        }
                )
                .addOnFailureListener(
                        e -> {
                            // Error.
                            // ...
                            txtResult.setText("failed");
                        });


        Translator translator = Translation.getClient(options);
        getLifecycle().addObserver(translator);
    }
    public void setUpConditions2(String text1) {
        // Create an Telugu-Hindi translator:
        HindiTeluguTranslator.translate(text1)
                .addOnSuccessListener(
                        (OnSuccessListener) translatedText ->{
                            txtResult2.setText("");
                            txtResult2.append((String) translatedText);
                            textToSpeech2.speak((String) translatedText,TextToSpeech.QUEUE_FLUSH, null);
                            Log.i("TAG","Translation is "+ (String) translatedText);
                        }
                )
                .addOnFailureListener(
                        e -> {
                            // Error.
                            // ...
                            txtResult2.setText("failed");
                        });

        Translator translator = Translation.getClient(options2);
        getLifecycle().addObserver(translator);
    }
}