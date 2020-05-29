package com.example.rishabhshetty.drishtt;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rishabhshetty.drishtt.R;

import java.util.Locale;

public class Tutorial extends AppCompatActivity {
    private TextToSpeech mTTs;

    public TextView t;
    private String say;
    public static Button inv;


/*
    private void tut()
    {speak(" ");
        Toast.makeText(this, "Hello first time user heres your Tutorial", Toast.LENGTH_SHORT).show();
        speak("Hello first time user heres your Tutorial");
        speak("hello  New User Heres your tutorial to call any Number: on to the left we have Reset Button on the right we have the Enter Button in the center We have a BCD Board of Vaues 8,4,2,1 Respectively This Keypad takes One Value at a time Pressing the reset button once Resets Onboard Values Pressing the Enter Button Once Enters a Value Long Press on the Reset Button Resets all previously entered Values and Long Press on Enter Helps you Have This Tutorial Again");

        mTTs.speak(say, TextToSpeech.QUEUE_ADD, null);

    }*/


    private void initializeTextToSpeech() {
        mTTs=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mTTs.getEngines().size()==0){
                    Toast.makeText(Tutorial.this,"There is no TTS Engine on Ypur Device",Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    mTTs.setLanguage(Locale.ENGLISH);
                    speak("Hello first time user heres your Tutorial.");
                    speak("To call any Number, on to the left we have Reset Button. On the right we have the Enter Button. In the center We have a BCD Board of Values 8,4,2,1 (top to bottom) Respectively. This Keypad takes One Value at a time. Pressing the reset button once Resets Onboard Values. Pressing the Enter Button Once Enters a Value. Long Press on the Reset Button Resets all previously entered Values. and Long Press on Enter Helps you Have This Tutorial Again");

                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT>=21){
            mTTs.speak(message,TextToSpeech.QUEUE_ADD,null,null);

        }
        else
        {
            mTTs.speak(message,TextToSpeech.QUEUE_ADD,null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTTs.shutdown();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        initializeTextToSpeech();
        /*inv=(Button)findViewById(R.id.button2);
        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tut();

            }
        });*/
        final Intent in=new Intent(getApplicationContext(),Calling_RS.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(in);
            }
        }, 31000);


        //inv.performClick();






    }






}
