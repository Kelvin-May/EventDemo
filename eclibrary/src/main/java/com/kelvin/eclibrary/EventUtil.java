package com.kelvin.eclibrary;

import android.os.Build;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

public class EventUtil {

    public static View.OnClickListener getOnClickListener(View view) {
        if (view == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getOnClickListenerV14(view);
        } else {
            return getOnClickListenerV(view);
        }
    }

    //Used for APIs lower than ICS (API 14)
    private static View.OnClickListener getOnClickListenerV(View view) {
        View.OnClickListener retrievedListener = null;
        String viewStr = "android.view.View";
        Field field;

        try {
            field = Class.forName(viewStr).getDeclaredField("mOnClickListener");
            retrievedListener = (View.OnClickListener) field.get(view);
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }

    //Used for new ListenerInfo class structure used beginning with API 14 (ICS)
    private static View.OnClickListener getOnClickListenerV14(View view) {
        View.OnClickListener retrievedListener = null;
        String viewStr = "android.view.View";
        String lInfoStr = "android.view.View$ListenerInfo";

        try {
            Field listenerField = Class.forName(viewStr).getDeclaredField("mListenerInfo");
            Object listenerInfo = null;

            if (listenerField != null) {
                listenerField.setAccessible(true);
                listenerInfo = listenerField.get(view);
            }

            Field clickListenerField = Class.forName(lInfoStr).getDeclaredField("mOnClickListener");

            if (clickListenerField != null && listenerInfo != null) {
                retrievedListener = (View.OnClickListener) clickListenerField.get(listenerInfo);
            }
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }
}
