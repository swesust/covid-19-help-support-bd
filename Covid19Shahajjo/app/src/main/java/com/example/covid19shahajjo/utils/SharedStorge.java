package com.example.covid19shahajjo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedStorge {

    private static final int DEFAULT_INT = 1;
    private static final String KEY_LANGUAGE = "lang";
    private static final String STORE_NAME = "user_preferences";


    public static Enums.Language getUserLanguage(Context context){
        // index wise value NONE = 0, BD = 1, EN = 2
        SharedPreferences sharedPreferences = getStorage(context);
        int sharedValue = sharedPreferences.getInt(KEY_LANGUAGE, DEFAULT_INT);
        switch (sharedValue){
            case 1: return Enums.Language.BD;
            case 2: return Enums.Language.EN;
            default:return Enums.Language.NONE;
        }
    }

    public static boolean setUserLanguage(Context context, Enums.Language language) {
        SharedPreferences sharedPreferences = getStorage(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(language == Enums.Language.BD){
            editor.putInt(KEY_LANGUAGE, 1);
            return editor.commit();
        }
        else if(language == Enums.Language.EN){
            editor.putInt(KEY_LANGUAGE, 2);
            return editor.commit();
        }
        editor.commit();
        return false;
    }

    private static SharedPreferences getStorage(Context context){
        return context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
    }
}
