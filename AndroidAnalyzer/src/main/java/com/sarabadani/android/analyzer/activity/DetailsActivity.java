package com.sarabadani.android.analyzer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.sarabadani.android.analyzer.R;
import com.sarabadani.android.analyzer.adapter.ListDetailsAdapter;
import com.sarabadani.android.analyzer.model.AggregatedCalls;


/**
 * Created by soroosh on 1/8/14.
 */
public class DetailsActivity extends ActionBarActivity {

    private ListView listDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        final Intent intent = getIntent();
        final int index = intent.getIntExtra("aggregatedCallPosition", -1);
        if (index == -1) {
            finish();
        } else {
            listDetails = (ListView) findViewById(R.id.listDetails);
            listDetails.setAdapter(new ListDetailsAdapter(this, AggregatedCalls.getInstance().get(index)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
