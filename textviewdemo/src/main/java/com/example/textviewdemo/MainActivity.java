package com.example.textviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,EmptyActivity.class));
                    }
                });

                Log.i("MainActivity", lineFirst);
            }
        });
    }


}