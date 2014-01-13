package com.sarabadani.android.analyzer.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.widget.*;
import com.sarabadani.android.analyzer.R;
import com.sarabadani.android.analyzer.adapter.CallStatisticAdapter;
import com.sarabadani.android.analyzer.model.AggregatedCall;
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
import com.sarabadani.android.analyzer.repository.CallLogRepository;
import com.sarabadani.android.analyzer.repository.ContactRepository;

public class MainActivity extends ActionBarActivity {
    private List<Call> calls = new ArrayList<Call>();
    ListView listView;
    private AggregatedCalls aggregatedCalls;
    private CallLogRepository callLogRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        this.callLogRepository = new CallLogRepository(this);
        setContentView(R.layout.activity_call);

        findAllCalls();
        Toast.makeText(this, "تعداد تماسهای بررسی شده: " + calls.size(), Toast.LENGTH_LONG).show();

        listView = (ListView) findViewById(R.id.listCalls);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("aggregatedCallPosition", position);
                startActivity(intent);
            }
        });

        aggregatedCalls = AggregatedCalls.getInstance();
        aggregatedCalls.aggregate(calls);

        listView.setAdapter(new CallStatisticAdapter(this, aggregatedCalls));
    }

    private void findAllCalls() {
        calls.clear();
        calls = this.callLogRepository.findAllCalls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
