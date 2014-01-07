package com.sarabadani.android.analyzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sarabadani.android.analyzer.R;
import com.sarabadani.android.analyzer.adapter.CallStatisticAdapter;
import com.sarabadani.android.analyzer.model.AggregatedCalls;
import com.sarabadani.android.analyzer.model.Call;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog.Calls;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private final List<Call> calls = new ArrayList<Call>();
	ListView listView;
	private  AggregatedCalls aggregatedCalls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);

		findAllCalls();
		Toast.makeText(this, "تعداد تماسهای بررسی شده: " + calls.size(), Toast.LENGTH_LONG).show();

		listView = (ListView) findViewById(R.id.listCalls);

		aggregatedCalls = new AggregatedCalls();
		aggregatedCalls.aggregate(calls);

		listView.setAdapter(new CallStatisticAdapter(this, aggregatedCalls));
	}

	private void findAllCalls() {

		calls.clear();
		Uri uri = Uri.parse("content://call_log/calls");

		Cursor c = getContentResolver().query(uri, null, null, null, "_ID ");

		final int numberIndex = c.getColumnIndex(Calls.NUMBER);
		final int nameIndex = c.getColumnIndex(Calls.CACHED_NAME);
		final int dateIndex = c.getColumnIndex(Calls.DATE);
		final int durationIndex = c.getColumnIndex(Calls.DURATION);

		while (c.moveToNext()) {
			final String number = c.getString(numberIndex);
			final String name = c.getString(nameIndex);
			final Date date = new Date(c.getLong(dateIndex));
			final int duration = Integer.parseInt(c.getString(durationIndex));
			Call call = new Call(number, name, duration, date);
			calls.add(call);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			findAllCalls();
			aggregatedCalls.clear();
			aggregatedCalls.aggregate(calls);
			
			((BaseAdapter) listView.getAdapter()).notifyDataSetChanged(); 
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
