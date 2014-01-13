package com.sarabadani.android.analyzer.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import com.sarabadani.android.analyzer.model.Call;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by soroosh on 1/13/14.
 */
public class CallLogRepository extends AbstractRepository {

    final Uri uri = Uri.parse("content://call_log/calls");

    public CallLogRepository(Context context) {
        super(context);
    }


    public List<Call> findAllCalls() {
        List<Call> calls = new ArrayList<Call>();

        Cursor c = context.getContentResolver().query(uri, null, null, null, "_ID ");

        final int numberIndex = c.getColumnIndex(CallLog.Calls.NUMBER);
        final int nameIndex = c.getColumnIndex(CallLog.Calls.CACHED_NAME);
        final int dateIndex = c.getColumnIndex(CallLog.Calls.DATE);
        final int durationIndex = c.getColumnIndex(CallLog.Calls.DURATION);
        final int typeIndex = c.getColumnIndex(CallLog.Calls.TYPE);

        while (c.moveToNext()) {
            final String number = c.getString(numberIndex);
            final String name = c.getString(nameIndex);
            final Date date = new Date(c.getLong(dateIndex));
            final int duration = Integer.parseInt(c.getString(durationIndex));
            final int type = c.getInt(typeIndex);

            Call call = new Call(number, name, duration, date, type);
            calls.add(call);
        }

        return calls;
    }

    public List<Call> findAllCallsAfter(long timeMillis) {


        Cursor c = context.getContentResolver().query(uri, null, CallLog.Calls.DATE + " > " + timeMillis, null, "_ID ");

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

        return calls;

    }

}
