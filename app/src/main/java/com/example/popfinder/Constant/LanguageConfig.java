package com.example.popfinder.Constant;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LanguageConfig {
    public static ContextWrapper changeLanguage(Context context, String languageCode) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale systemLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            systemLocale = configuration.getLocales().get(0);
        } else {
            systemLocale = configuration.locale;
        }
        if (!languageCode.equals("") && !systemLocale.getLanguage().equals(languageCode)) {
            Locale locale = new Locale(languageCode);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(locale);
            } else {
                configuration.locale = locale;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context.createConfigurationContext(configuration);
            } else {
                context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
            }
        }
        return new ContextWrapper(context);
    }
}