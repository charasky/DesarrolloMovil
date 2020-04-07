package org.lapoderosa.app.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.lapoderosa.app.R;

public class MyAnimation {
    public static void blink(View view, Context context){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink);
        view.startAnimation(animation);
    }

    public static void bounce(View view, Context context){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
        view.startAnimation(animation);
    }
}
