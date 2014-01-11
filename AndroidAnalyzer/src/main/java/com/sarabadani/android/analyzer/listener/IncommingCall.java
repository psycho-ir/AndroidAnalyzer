package com.sarabadani.android.analyzer.listener;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;
import com.sarabadani.android.analyzer.R;
import com.sarabadani.android.analyzer.model.AggregatedCalls;
import com.sarabadani.android.analyzer.model.Call;
import com.sarabadani.android.analyzer.widget.AnalyzerWidgetProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author: Soroosh Sarabadani
 * Date: 1/10/14
 * Time: 8:40 PM
 */
public class IncommingCall extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
        // TELEPHONY MANAGER class object to register one listner
/*        TelephonyManager tmgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        //Create Listner
        AnalyzerPhoneStateListener PhoneListener = new AnalyzerPhoneStateListener();

        // Register listener for LISTEN_CALL_STATE
        tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);*/

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, AnalyzerWidgetProvider.class);
        final int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        }).start();
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
