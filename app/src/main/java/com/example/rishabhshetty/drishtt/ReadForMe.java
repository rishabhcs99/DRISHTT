package com.example.rishabhshetty.drishtt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rishabhshetty.drishtt.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Locale;

public class ReadForMe extends AppCompatActivity {
    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    private TextToSpeech mTTs;
    private TextToSpeech mTTS;
    public boolean readfisrttime=true;


    private void initializeTextToSpeech() {
        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mTTs.getEngines().size() == 0) {
                    Toast.makeText(ReadForMe.this, "There is no TTS Engine on Your Device", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    mTTs.setLanguage(Locale.ENGLISH);
                    speak("Your Reading Lense Is Now Open");

                }
            }
        });
    }

    private void speak(String message) {
        if (Build.VERSION.SDK_INT >= 21) {
            mTTs.speak(message, TextToSpeech.QUEUE_ADD, null, null);

        } else {
            mTTs.speak(message, TextToSpeech.QUEUE_ADD, null);
        }
    }

    boolean twice = false;
    boolean once = true;

    @Override
    public void onBackPressed() { //GO TO MAIN PAGE ON BACK PRESSED
        if (twice == true) {
            Intent backhome = new Intent("MainActivityFirst");
            startActivity(backhome);
        }
        //super.onBackPressed();
        twice = true;
        if (once == true) {
            speak("Press Back Again to Get Back to the main menu");
            Toast.makeText(this, "Press Back Again to go to the main menu", Toast.LENGTH_SHORT).show();
            once = false;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;

            }
        }, 3000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mTTs.shutdown();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_for_me);
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

                    }
                }
                else
                {
                    Log.e("TTS", "Initialization failed");
                }

            }
        });

        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("ReadForMe", "Detector Dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024).setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true).build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(ReadForMe.this, new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);

                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                        speak("IM READING");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {


                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                    cameraSource.stop();

                }
            });
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();

                    if (items.size() != 0) {


                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                final StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); ++i) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                //textView.setText((stringBuilder.toString()));
                                textView.setText((stringBuilder.toString()));
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        speak("" + stringBuilder.toString());
                                    }
                                }, 0);
                                //speak("" + stringBuilder.toString());

                                Handler handler2 = new Handler();
                                handler2.postDelayed(new Runnable() {
                                    public void run() {
                                        cameraSource.stop();
                                    }
                                }, 0);                              //



                                Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    public void run() {
                                        try {
                                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                                // TODO: Consider calling
                                                //    ActivityCompat#requestPermissions
                                                // here to request the missing permissions, and then overriding
                                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                //                                          int[] grantResults)
                                                // to handle the case where the user grants the permission. See the documentation
                                                // for ActivityCompat#requestPermissions for more details.
                                                return;
                                            }
                                            cameraSource.start(cameraView.getHolder());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 5000);


                            }
                        });
                    }

                }
            });
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

}

