package com.sarabadani.android.analyzer.adapter;

import android.content.Context;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sarabadani.android.analyzer.R;
import com.sarabadani.android.analyzer.model.AggregatedCall;
import com.sarabadani.android.analyzer.model.Call;
import com.sarabadani.android.common.utils.PersianCalendar;

import java.util.List;

/**
 * Created by soroosh on 1/8/14.
 */
public class ListDetailsAdapter extends ArrayAdapter<Call> {
    private final Context context;
    private List<Call> calls;

    public ListDetailsAdapter(Context context, AggregatedCall aggregatedCall) {
        super(context, R.layout.calls_row, aggregatedCall.getAllCalls());
        this.calls = aggregatedCall.getAllCalls();
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Call call = calls.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.details_row, parent, false);
        ImageView callImg = (ImageView) view.findViewById(R.id.callTypeImg);
        TextView phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        TextView contactName = (TextView) view.findViewById(R.id.contactName);
        TextView duration = (TextView) view.findViewById(R.id.duration);
        TextView date = (TextView) view.findViewById(R.id.date);

        switch (call.getCallType()) {
            case CallLog.Calls.MISSED_TYPE:
                callImg.setImageResource(R.drawable.cancel);
                duration.setText("");
                break;
            case CallLog.Calls.INCOMING_TYPE:
                callImg.setImageResource(R.drawable.down);
                duration.setText(call.getDuration() + " ثانیه");
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                callImg.setImageResource(R.drawable.up);
                duration.setText(call.getDuration() + " ثانیه");
                break;
            default:
                break;
        }

        phoneNumber.setText(call.getNumber());
        contactName.setText(call.getName() == null ? "دخیره نشده" : call.getName());
        PersianCalendar persianCalendar = new PersianCalendar(call.getDate());
        String d = persianCalendar.getYear() + "/" + persianCalendar.getMonth() + "/" + persianCalendar.getDate() + " " + call.getDate().getHours() + ":" + call.getDate().getMinutes();
        date.setText(d);

        return view;

    }

}
