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
import com.sarabadani.android.analyzer.repository.CallLogRepository;
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

        final CallLogRepository callLogRepository = new CallLogRepository(context);


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
                AggregatedCalls aggregatedCalls = analyzeTodayCallLogs(callLogRepository);
                for (int widgetId : allWidgetIds) {
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

                    remoteViews.setTextViewText(R.id.total_duration_value, String.valueOf(aggregatedCalls.calculateTotalDuration()) + " ثانیه");
                    remoteViews.setTextViewText(R.id.incommingNumber, String.valueOf(aggregatedCalls.getNumberOfIncommingCalls()));
                    remoteViews.setTextViewText(R.id.outgoingNumber, String.valueOf(aggregatedCalls.getNumberOfOutgoingCalls()));
                    remoteViews.setTextViewText(R.id.missedNumber, String.valueOf(aggregatedCalls.getNumberOfMissedCalls()));
                    appWidgetManager.updateAppWidget(widgetId, remoteViews);
                }
            }
        }).start();
    }

    private AggregatedCalls analyzeTodayCallLogs(CallLogRepository callLogRepository) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        List<Call> allTodayCalls = callLogRepository.findAllCallsAfter(calendar.getTimeInMillis());

        AggregatedCalls aggregatedCalls = new AggregatedCalls();
        aggregatedCalls.aggregate(allTodayCalls);

        return aggregatedCalls;
    }


}
