package com.example.rishabhshetty.drishtt;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rishabhshetty.drishtt.R;

import java.util.Locale;

public class Calling_RS extends AppCompatActivity implements View.OnLongClickListener,View.OnClickListener,View.OnFocusChangeListener{
    private RelativeLayout myLayout=null;
    private float x;
    private float y;
    private static ToggleButton t8;
    private static ToggleButton t4;
    private static ToggleButton t2;
    private static ToggleButton t1;
    private static Button reset;
    private static Button enter;
    public static int pos;
    public int count=0;
    public int i8=0;
    public int i4=0;
    public int i2=0;
    public int i1=0;
    public String numbers;
    public int[] number = new int[11];
    int pid=1;//a process task execution
    private TextToSpeech mTTS;
    private Boolean firstTime=null;


    private void initializeTextToSpeech() {
        mTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mTTS.getEngines().size()==0){
                    Toast.makeText(Calling_RS.this,"There is no TTS Engine on Ypur Device",Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    mTTS.setLanguage(Locale.ENGLISH);
                    speak("Your BCD Keypad Is Now Open");

                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT>=21){
            mTTS.speak(message,TextToSpeech.QUEUE_ADD,null,null);

        }
        else
        {
            mTTS.speak(message,TextToSpeech.QUEUE_ADD,null);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling__rs);
        initializeTextToSpeech();
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status)
            {
                if (status == TextToSpeech.SUCCESS)
                {
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Log.e("TTS", "Language not supported");
                    }
                    else
                    {
                        enter.setEnabled(true);
                    }
                }
                else
                {
                    Log.e("TTS", "Initialization failed");
                }

            }
        });


        myLayout=(RelativeLayout)findViewById(R.id.myLayout);
        reset=(Button) findViewById(R.id.button);
        enter=(Button) findViewById(R.id.button1);
        t8=(ToggleButton)findViewById(R.id.toggleButton8);
        t4=(ToggleButton)findViewById(R.id.toggleButton4);
        t2=(ToggleButton)findViewById(R.id.toggleButton2);
        t1=(ToggleButton)findViewById(R.id.toggleButton1);
        isFirstTime();

/*
        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                x=motionEvent.getX();
                y=motionEvent.getY();
                Toast.makeText(getApplicationContext(),"X= "+x+"& Y= "+y,Toast.LENGTH_SHORT).show();
                if(motionEvent.getAction()==MotionEvent.ACTION_MOVE)
                {
                    if(x==0||x==274||x==268 && y==0||y==25||y==1657)
                    {
                        speak("Over Reset");
                    }
                }

                return true;
            }
        });
        */

        t8.setOnClickListener(this);
        t4.setOnClickListener(this);
        t2.setOnClickListener(this);
        t1.setOnClickListener(this);

        t8.setOnFocusChangeListener(this);
        t4.setOnFocusChangeListener(this);
        t2.setOnFocusChangeListener(this);
        t1.setOnFocusChangeListener(this);


        reset.setOnClickListener(this);
        enter.setOnClickListener(this);

        reset.setOnLongClickListener(this);
        enter.setOnLongClickListener(this);


    }



    @Override
    protected void onPause() {
        super.onPause();
        mTTS.shutdown();
    }


    private boolean isFirstTime()
    {
        if (firstTime == null)
        {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime)
            {



                Toast.makeText(this, "Hello first time user heres your Tutorial", Toast.LENGTH_SHORT).show();
                speak("Hello first time user heres your Tutorial");
                speak("To call any Number: on to the left we have Reset Button on the right we have the Enter Button in the center We have a BCD Board of Values 8,4,2,1 Respectively This Keypad takes One Values at a time Pressing the reset button once Resets Onboard Values Pressing the Enter Button Once Enters a Value Long Press on the Reset Button Resets all previously entered Values and Long Press on Enter Helps you Have This Tutorial Again");
                Intent i=new Intent(getApplicationContext(),Tutorial.class);
                startActivity(i);

                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();


            }
        }
        else
        {
            firstTime = false;
        }
        return firstTime;

    }

    public void tutorial()
    {
        speak("Hello first time user here is your Tutorial");
    }




    public int BinaryToDecimal(int binaryNumber){

        int decimal = 0;
        int p = 0;
        while(true){
            if(binaryNumber == 0){
                break;
            } else {
                int temp = binaryNumber%10;
                decimal += temp*Math.pow(2, p);
                binaryNumber = binaryNumber/10;
                p++;
            }
        }
        return decimal;
    }

    public void call(int deci)
    {
        if(deci<10)
        {
            number[count] = deci;
            count++;
            // call
            if (count == 11)
            {
                String numbers = Integer.toString(number[0]) + Integer.toString(number[1])+ Integer.toString(number[2])+ Integer.toString(number[3])
                        + Integer.toString(number[4])+ Integer.toString(number[5])+ Integer.toString(number[6])
                        + Integer.toString(number[7])+ Integer.toString(number[8])+ Integer.toString(number[9])+ Integer.toString(number[10]);
                speak("Calling " + numbers);
                Toast.makeText(this, "Calling " + numbers, Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        callatruntimepermission();
                    }
                }, 5000);


                count = 0;
            }
            else
            {
                //return
            }
        }
        else
        {
            speak("Enter a valid BCD between 0 and 9 ");
            Toast.makeText(this, "Enter a valid BCD (0 to 9) ", Toast.LENGTH_SHORT).show();
        }

    }

    private void callatruntimepermission()
    {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},pid);
        }
        else
        {
            String numbers = Integer.toString(number[0]) + Integer.toString(number[1])+ Integer.toString(number[2])+ Integer.toString(number[3])
                    + Integer.toString(number[4])+ Integer.toString(number[5])+ Integer.toString(number[6])
                    + Integer.toString(number[7])+ Integer.toString(number[8])+ Integer.toString(number[9])+ Integer.toString(number[10]);
            Intent call=new Intent(Intent.ACTION_CALL);
            call.setData(Uri.parse("tel:"+numbers));
            startActivity(call);

        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);

        if(requestCode==pid)
        {
            if(grantResult[0]==PackageManager.PERMISSION_GRANTED)
            {
                callatruntimepermission();
            }
        }
    }



    public void refresh()
    {
        t8.setChecked(false);
        t4.setChecked(false);
        t2.setChecked(false);
        t1.setChecked(false);
    }
    boolean twice=false;
    boolean once=true;
    @Override
    public void onBackPressed() { //GO TO MAIN PAGE ON BACK PRESSED
        if(twice==true)
        {
            Intent backhome=new Intent("MainActivityFirst");
            startActivity(backhome);
        }
        //super.onBackPressed();
        twice =true;
        if(once==true) {
            speak("Press Back Again to Get Back to the main menu");
            Toast.makeText(this, "Press Back Again to go to the main menu", Toast.LENGTH_SHORT).show();
            once=false;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice =false;

            }
        },3000);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button :
                speak("Resetting On Board Values");
                Toast.makeText(this, "Resetting Values", Toast.LENGTH_SHORT).show();
                refresh();
                break;

            case R.id.button1 :


                t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(t1.isChecked())
                        {
                            i1=1;
                        }
                        else
                        {
                            i1=0;
                        }
                    }
                });
                t2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(t2.isChecked())
                        {
                            i2=1;
                        }
                        else
                        {
                            i2=0;
                        }
                    }
                });

                t4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(t4.isChecked())
                        {
                            i4=1;
                        }
                        else
                        {
                            i4=0;
                        }
                    }
                });

                t8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(t8.isChecked())
                        {
                            i8=1;
                        }
                        else
                        {
                            i8=0;
                        }
                    }
                });

                String concat = Integer.toString(i8) + Integer.toString(i4)+ Integer.toString(i2)+ Integer.toString(i1);
                int combined = Integer.parseInt(concat);
                int deci=BinaryToDecimal(combined);
                if(deci<10)
                {
                    speak(deci + "Entered");
                    Toast.makeText(this, "Entered Value " + deci, Toast.LENGTH_SHORT).show();
                }
                call(deci);
                refresh();
                break;
            case R.id.toggleButton8 :
                if(t8.isChecked())
                    speak("Eight");
                else
                    speak("Eight Unchecked");
                break;
            case R.id.toggleButton4 :
                if(t4.isChecked())
                    speak("Four");
                else
                    speak("Four Unchecked");
                break;
            case R.id.toggleButton2 :
                if(t2.isChecked())
                    speak("Two");
                else
                    speak("Two Unchecked");
                break;
            case R.id.toggleButton1 :
                if(t1.isChecked())
                    speak("One");
                else
                    speak("One Unchecked");
                break;

        }

    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        switch(view.getId())
        {
            case R.id.toggleButton8:
                if(!b)
                {
                    speak("Eight");
                }
        }
    }


    @Override
    public boolean onLongClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button:
                // reinitialize count to 0
                count=0;
                speak("Resetting All Values");
                break;

            case R.id.button1:
                speak("Hello first time user heres your Tutorial");
                speak("To call any Number: on to the left we have Reset Button on the right we have the Enter Button in the center We have a BCD Board of Values 8,4,2,1 Respectively This Keypad takes One Values at a time Pressing the reset button once Resets Onboard Values Pressing the Enter Button Once Enters a Value Long Press on the Reset Button Resets all previously entered Values and Long Press on Enter Helps you Have This Tutorial Again");

                Intent i=new Intent(getApplicationContext(),Tutorial.class);
                startActivity(i);
                break;

        }
        return true;
    }
}

/*

    boolean flag = false;

    boolean flag2 = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.activity_main, menu);
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Log.d("Test", "Long press!");
            flag = false;
            flag2 = true;
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            event.startTracking();
            if (flag2 == true) {
                flag = false;
            } else {
                flag = true;
                flag2 = false;
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

            event.startTracking();
            if (flag) {
                Log.d("Test", "Short");
            }
            flag = true;
            flag2 = false;
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }



*/