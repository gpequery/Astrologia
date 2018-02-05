package com.esgi.astrologia;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.esgi.astrologia.Constants.Preferences;

/**
 * Implementation of App Widget functionality.
 */
public class myWidget extends AppWidgetProvider {
    public static String CLICK_ACTION = "CLICK_ACTION";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);

        Intent intent = new Intent(context, myWidget.class);
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.sendSms, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equals(CLICK_ACTION)) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String number = preferences.getString(Preferences.NUMBER_PHONE, null);
            String message = preferences.getString(Preferences.MESSAGE, null);

            if (message.length() != 0) {
                SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
                Toast.makeText(context, "Message envoyé", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Message vide !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

