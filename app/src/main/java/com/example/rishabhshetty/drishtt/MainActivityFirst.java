package com.example.rishabhshetty.drishtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rishabhshetty.drishtt.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivityFirst extends AppCompatActivity implements View.OnClickListener {


    private RelativeLayout myLayout =null;
    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechRecogizer;
    public boolean sayBonce=false;
    Button ReadIt;
    private String currentBattry;
    private BroadcastReceiver mBatInfoReciever=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);


                    currentBattry = String.valueOf(level + "%");


                if(sayBonce==false) {
                    speak("Your Current Battry Level is " + currentBattry);
                    sayBonce = true;
                }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_first);





        Button ReadIt=(Button)findViewById(R.id.readit);
        Button call_rs=(Button)findViewById(R.id.call_rs);
        Button messageSOS=(Button)findViewById(R.id.sosbutton);

        ReadIt.setOnClickListener(this);
        call_rs.setOnClickListener(this);
        messageSOS.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeTextToSpeech();
        initializeSpeechRecognizer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                mySpeechRecogizer.startListening(intent);
                Toast.makeText(getApplicationContext(),"I am Being Long Clicked",Toast.LENGTH_LONG).show();
                return true;
            }
        });


    }

    private void initializeSpeechRecognizer() {
        if(SpeechRecognizer.isRecognitionAvailable(getApplicationContext()))
        {

            mySpeechRecogizer=SpeechRecognizer.createSpeechRecognizer(this);
            mySpeechRecogizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int i) {

                }

                @Override
                public void onResults(Bundle bundle) {
                    List<String> results = bundle.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                    );
                    processResult(results.get(0));

                }

                @Override
                public void onPartialResults(Bundle bundle) {


                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });
        }
    }

    private void processResult(String command) {
        command = command.toLowerCase();
        //what is your name
        //what is the time

        if(command.indexOf("what")!= -1)
        {
            if (command.indexOf("your name")!=-1)
            {
                speak("Hello! My name is Koko: I am happy to Help You.");
            }
            if (command.indexOf("time")!= -1)
            {
                Date now =new Date();
                String time = DateUtils.formatDateTime(this,now.getTime(),DateUtils.FORMAT_SHOW_TIME);
                speak("The Time is "+time);
            }
            if(command.indexOf("date")!= -1)
            {
                Calendar calendar=Calendar.getInstance();
                String currentDate= java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(calendar.getTime());
                speak("Today is "+currentDate);
            }
            if(command.indexOf("battery")!= -1)
            {
                    this.registerReceiver(this.mBatInfoReciever, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

                    sayBonce=false;
            }

        }
        else if(command.indexOf("help")!= -1)
        {
            if (command.indexOf("read")!=-1)
            {


                speak("Opening Your Reading Lense");
                Intent i=new Intent("ReadForMe");
                startActivity(i);
            }
            if(command.indexOf("call")!=-1)
            {
                speak("Opening Binary Coded Keypad");
                Intent i1=new Intent("Calling_RS");
                startActivity(i1);
            }
        }
        /*else if(command.indexOf("which") != -1)
        {
            if(command.indexOf("day") != -1)
            {
                Calendar calendar=Calendar.getInstance();
                String currentDate= java.text.DateFormat.getDateInstance(java.text.DateFormat.DAY_OF_WEEK_FIELD).format(calendar.getTime());
            }
            if(command.indexOf("year") != -1)
            {
                Calendar calendar=Calendar.getInstance();
                String currentDate= java.text.DateFormat.getDateInstance(java.text.DateFormat.YEAR_FIELD).format(calendar.getTime());
            }
        }*/
        else if(command.indexOf("how") != -1)
        {
            if(command.indexOf("are you") != -1)
            {
                    speak("Thankyou for asking, I'm very Much Well. Is there Anything i can do for you?");

            }
            if(command.indexOf("battery") != -1)
            {
                this.registerReceiver(this.mBatInfoReciever, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

                sayBonce=false;
            }
        }
        else
        {
            speak("Sorry: I Didnt Get You.");
        }

    }



    private void initializeTextToSpeech() {
        myTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
              if(myTTS.getEngines().size()==0){
                  Toast.makeText(MainActivityFirst.this,"There is no TTS Engine on Ypur Device",Toast.LENGTH_LONG).show();
                  finish();
              }
              else
              {
                  myTTS.setLanguage(Locale.ENGLISH);
                  speak("Hello: How may i help you.");
              }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT>=21){
            myTTS.speak(message,TextToSpeech.QUEUE_ADD,null,null);

        }
        else
        {
            myTTS.speak(message,TextToSpeech.QUEUE_ADD,null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        myTTS.shutdown();
    }
    boolean twice=false;
    boolean once=true;
    @Override
    public void onBackPressed() { //GO TO MAIN PAGE ON BACK PRESSED
        if(twice==true)
        {
            speak("You're in the Main Menu");
            Toast.makeText(this, "You're in the Main Menu", Toast.LENGTH_SHORT).show();
        }
        //super.onBackPressed();
        twice =true;
        if(once==true) {
            speak("You're in the Main Menu");
            Toast.makeText(this, "You're in the Main Menu", Toast.LENGTH_SHORT).show();
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
        switch (view.getId())
        {
            case R.id.readit:
                Intent i=new Intent("ReadForMe");
                startActivity(i);
                break;

            case R.id.fab:
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi Speak Something");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                mySpeechRecogizer.startListening(intent);
                Toast.makeText(getApplicationContext(),"I am Being  Clicked",Toast.LENGTH_LONG).show();

                break;
            case R.id.call_rs:
                Intent i1=new Intent("Calling_RS");
                startActivity(i1);
                break;



                /*
                case R.id.sosbutton:
                Intent i2=new Intent("MessageSOS");
                startActivity(i2);
                break;
                */


        }

    }

















    /* THE FOLLOWING CODE IS FOR CALCULATOR
    *
    * package com.example.calc;
import android.support.v7.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class MainActivity extends AppCompatActivity{
        // IDs of all the numeric buttons
        private int[] numericButtons = {R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine};
        // IDs of all the operator buttons
        private int[] operatorButtons = {R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide};
        // TextView used to display the output
        private TextView txtScreen;
        // Represent whether the lastly pressed key is numeric or not
        private boolean lastNumeric;
        // Represent that current state is in error or not
        private boolean stateError;
        // If true, do not allow to add another DOT
        private boolean lastDot;
        private ImageButton btnSpeak;
        private final int REQ_CODE_SPEECH_INPUT = 100;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            this.btnSpeak= (ImageButton) btnSpeak.findViewById(btnSpeak);
            // Find the TextView

            this.txtScreen = (TextView) txtScreen.findViewById( xtScreen);
            // Find and set OnClickListener to numeric buttons
            setNumericOnClickListener();
            // Find and set OnClickListener to operator buttons, equal button and decimal point button
            setOperatorOnClickListener();
        }

        private void setContentView(int activity_main) {
        }

        /**
         * Find and set OnClickListener to numeric buttons.

    private void setNumericOnClickListener() {
        // Create a common OnClickListener
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just append/set the text of clicked button
                Button button = (Button) v;
                if (stateError) {
                    // If current state is Error, replace the error message
                    txtScreen.setText(button.getText());
                    stateError = false;
                } else {
                    // If not, already there is a valid expression so append to it
                    txtScreen.append(button.getText());
                }
                // Set the flag
                lastNumeric = true;
            }
        };
        // Assign the listener to all the numeric buttons
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /**
     * Find and set OnClickListener to operator buttons, equal button and decimal point button.

    private void setOperatorOnClickListener() {
        // Create a common OnClickListener for operators
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the current state is Error do not append the operator
                // If the last input is number only, append the operator
                if (lastNumeric && !stateError) {
                    Button button = (Button) v;
                    txtScreen.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;    // Reset the DOT flag
                }
            }
        };
        // Assign the listener to all the operator buttons
        for (int id : operatorButtons) {
            txtScreen.findViewById().setOnClickListener(listener);
        }
        // Decimal point
        txtScreen.findViewById().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot) {
                    txtScreen.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });
        // Clear button
        Button btnClr = null;
        // Equal button
        btnClr.findViewById().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
        btnSpeak.findViewById().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateError) {
                    // If current state is Error, replace the error message
                    txtScreen.setText("Try Again");
                    stateError = false;
                } else {
                    // If not, already there is a valid expression so append to it
                    promptSpeechInput();
                }
                // Set the flag
                lastNumeric = true;

            }
        });
    }

    /**
     * Logic to calculate the solution.

    private void onEqual() {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        if (lastNumeric && !stateError) {
            // Read the expression
            String txt = txtScreen.getText().toString();
            // Create an Expression (A class from exp4j library)
            try {
                Expression expression=null;
                try{
                    expression = new ExpressionBuilder(txt).build();
                    double result = expression.evaluate();
                    txtScreen.setText(Double.toString(result));
                }catch (Exception e){
                    txtScreen.setText("Error");
                }
                lastDot = true; // Result contains a dot
            } catch (ArithmeticException ex) {                    // Display an error message
                txtScreen.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }
    /**
     * Showing google speech input dialog
     *
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Receiving speech input
     *
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
       //    {
                if (resultCode == RESULT_OK && null != data) {

          //     <String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    change=change.replace("x","*");
                    change=change.replace("X","*");
                    change=change.replace("add","+");
                    change=change.replace("sub","-");
                    change=change.replace("to","2");
                    change=change.replace(" plus ","+");
                    change=change.replace(" minus ","-");
                    change=change.replace(" times ","*");
                    change=change.replace(" into ","*");
                    change=change.replace(" in2 ","*");
                    change=change.replace(" multiply by ","*");
                    change=change.replace(" divide by ","/");
                    change=change.replace("divide","/");
                    change=change.replace("equal","=");
                    change=change.replace("equals","=");
                    if(change.contains("=")){
                        change=change.replace("=","");
                        txtScreen.setText(change);
                        onEqual();
                    }else{
                        txtScreen.setText(change);
                    }
                }
                break;
            }

        }
    }*/
}




