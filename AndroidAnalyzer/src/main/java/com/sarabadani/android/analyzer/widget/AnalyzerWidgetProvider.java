package com.sarabadani.android.analyzer.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;
import android.widget.RemoteViews;
import com.sarabadani.android.analyzer.R;
import com.sarabadani.android.analyzer.model.AggregatedCalls;
import com.sarabadani.android.analyzer.model.Call;

import java.util.*;

/**
 * Author: Soroosh Sarabadani
 * Date: 1/10/14
 * Time: 4:46 PM
 */
public class AnalyzerWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                AnalyzerWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        AggregatedCalls aggregatedCalls = analyzeTodayCallLogs(context);
        for (int widgetId : allWidgetIds) {
            // create some random data

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget);
            // Set the text
            remoteViews.setTextViewText(R.id.total_duration_value,String.valueOf(aggregatedCalls.calculateTotalDuration())+ " ثانیه");
            remoteViews.setTextViewText(R.id.incommingNumber,String.valueOf(aggregatedCalls.getNumberOfIncommingCalls()));
            remoteViews.setTextViewText(R.id.outgoingNumber,String.valueOf(aggregatedCalls.getNumberOfOutgoingCalls()));
            remoteViews.setTextViewText(R.id.missedNumber,String.valueOf(aggregatedCalls.getNumberOfMissedCalls()));
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private AggregatedCalls analyzeTodayCallLogs(Context context){

        Uri uri = Uri.parse("content://call_log/calls");

        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Cursor c = context.getContentResolver().query(uri, null, CallLog.Calls.DATE + " > " + calendar.getTimeInMillis(), null, "_ID ");

        final int numberIndex = c.getColumnIndex(CallLog.Calls.NUMBER);
        final int nameIndex = c.getColumnIndex(CallLog.Calls.CACHED_NAME);
        final int dateIndex = c.getColumnIndex(CallLog.Calls.DATE);
        final int durationIndex = c.getColumnIndex(CallLog.Calls.DURATION);
        final int typeIndex = c.getColumnIndex(CallLog.Calls.TYPE);


        final List<Call> calls = new ArrayList<Call>();
        while (c.moveToNext()) {
            final String number = c.getString(numberIndex);
            final String name = c.getString(nameIndex);
            final Date date = new Date(c.getLong(dateIndex));
            final int duration = Integer.parseInt(c.getString(durationIndex));
            final int type = c.getInt(typeIndex);

            Call call = new Call(number, name, duration, date, type);
            calls.add(call);
        }

        AggregatedCalls aggregatedCalls = new AggregatedCalls();
        aggregatedCalls.aggregate(calls);

        return aggregatedCalls;



    }
}
