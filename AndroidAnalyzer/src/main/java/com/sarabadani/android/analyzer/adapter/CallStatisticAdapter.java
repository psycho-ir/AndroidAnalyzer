package com.sarabadani.android.analyzer.adapter;

import java.util.List;

import com.sarabadani.android.analyzer.R;
import com.sarabadani.android.analyzer.model.AggregatedCall;
import com.sarabadani.android.analyzer.model.AggregatedCalls;
import com.sarabadani.android.analyzer.model.Call;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CallStatisticAdapter extends ArrayAdapter<AggregatedCall> {


	private final Context context;
	private List<AggregatedCall> calls;

	public CallStatisticAdapter(Context context, List<AggregatedCall> calls) {
		super(context, R.layout.calls_row, calls);
		this.calls= calls;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AggregatedCall call =  calls.get(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.calls_row, parent, false);
		TextView name = (TextView) view.findViewById(R.id.lblNameValue);
		TextView totalDuration = (TextView) view.findViewById(R.id.lblTotalDurationValue);
		TextView durationPercentage = (TextView) view.findViewById(R.id.lblDurationPercentageValue);
		TextView numberOfCalls = (TextView) view.findViewById(R.id.lblNumberOfCallsValue);
        TextView numberOfIncommingCalls = (TextView) view.findViewById(R.id.incommingNumber);
        TextView numberOfOutgoinggCalls = (TextView) view.findViewById(R.id.outgoingNumber);
        TextView numberOfMissedCalls = (TextView) view.findViewById(R.id.missedNumber);

		name.setText(call.getName() != null ? call.getName() : call.getNumber());
		totalDuration.setText(String.valueOf(call.getDuration())+" ثانیه");
		durationPercentage.setText(call.getPercentage() + "%");
		numberOfCalls.setText(String.valueOf(call.getNumberOffCalls()));
        numberOfIncommingCalls.setText(String.valueOf(call.getNumberOfIncomingCalls()));
        numberOfOutgoinggCalls.setText(String.valueOf(call.getNumberOfOutgoingCalls()));
        numberOfMissedCalls.setText(String.valueOf(call.getNumberOfMissedCalls()));

		return view;

	}
}
