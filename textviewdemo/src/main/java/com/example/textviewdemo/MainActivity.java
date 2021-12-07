package com.example.textviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.text_view);
        textView.post(new Runnable() {
            @Override
            public void run() {
                Layout layout = textView.getLayout();
                int indexStart = layout.getLineStart(0);
                int indexEnd = layout.getLineEnd(0);
                String lineFirst = (String) textView.getText().subSequence(indexStart, indexEnd);
                float firstLineWidth = textView.getPaint().measureText(lineFirst);
                int textViewWidth = textView.getWidth();
                int padding = (int) ((textViewWidth-firstLineWidth)/2);
                textView.setPadding(padding,0,0,0);

                Log.i("MainActivity", lineFirst);
            }
        });
    }
}