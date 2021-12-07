package com.example.dialogdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new MyDialog(this);
        dialog.show();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().setWindowAnimations(0);
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        },4000);
    }



    @Override
    protected void onStop() {
        super.onStop();

    }


}
class MyDialog extends  Dialog{

    public MyDialog(@NonNull Context context) {
        super(context);
    }

    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus){
            getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
        }
    }
}