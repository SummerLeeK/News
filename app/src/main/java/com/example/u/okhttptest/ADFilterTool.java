package com.example.u.okhttptest;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by u on 2017/1/28.
 */

public class ADFilterTool {

    public static String getClearAdDivJs(Context context) {
        String js = "javascript:";
        Resources res = context.getResources();

        js += "var adDiv= document.getElementsByTagName('div')[0];if(adDiv!= null)adDiv.parentNode.removeChild(adDiv);";

        return js;
    }
}
