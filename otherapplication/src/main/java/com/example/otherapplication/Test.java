package com.example.otherapplication;

import android.view.Gravity;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

public class Test extends DialogFragment {
    public Test() {
        super();
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.gravity = Gravity.START|Gravity.TOP;
    }
}
