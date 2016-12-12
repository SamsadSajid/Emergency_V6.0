package com.example.dolphin.emergency_v60;

/**
 * Created by DOLPHIN on 12/12/2016.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Msg {
    public static void mymsg(String message) {
        Log.d("oga", "" + message);
    }

    public static void cout(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
    public static void COUT(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}
