package com.example.rishabhshetty.drishtt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.rishabhshetty.drishtt.R;

public class MessageSOS extends AppCompatActivity {
   DatabaseHelper mDatabaseHelper;
   private Button btnAdd,btnView;
   private EditText editText;
    public static int num1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_sos);
        editText=(EditText)findViewById(R.id.editText2);
        btnAdd=(Button)findViewById(R.id.button3);
        btnView=(Button)findViewById(R.id.button6);
        mDatabaseHelper=new DatabaseHelper(getApplicationContext());
    }
}
