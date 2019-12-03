package com.synb;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2017-04-09.
 */

public class ToolUtils {
    public static void showToast(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}