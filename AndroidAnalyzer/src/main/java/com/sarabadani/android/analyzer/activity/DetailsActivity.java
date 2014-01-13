package com.sarabadani.android.analyzer.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.sarabadani.android.analyzer.R;
import com.sarabadani.android.analyzer.adapter.ListDetailsAdapter;
import com.sarabadani.android.analyzer.model.AggregatedCall;
import com.sarabadani.android.analyzer.model.AggregatedCalls;
import com.sarabadani.android.analyzer.model.Contact;
import com.sarabadani.android.analyzer.model.ContactwithNoPhoto;
import com.sarabadani.android.analyzer.repository.ContactRepository;

import java.io.InputStream;


/**
 * Created by soroosh on 1/8/14.
 */
public class DetailsActivity extends ActionBarActivity {

    private ListView listDetails;
    private ContactRepository contactRepository;
    private Contact contact;
    private AggregatedCall aggregatedCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        this.contactRepository = new ContactRepository(this);

        final Intent intent = getIntent();
        final int index = intent.getIntExtra("aggregatedCallPosition", -1);
        if (index == -1) {
            finish();
        } else {
            listDetails = (ListView) findViewById(R.id.listDetails);
            aggregatedCall = AggregatedCalls.getInstance().get(index);
            listDetails.setAdapter(new ListDetailsAdapter(this, aggregatedCall));
            this.contact = this.contactRepository.findContactWithPhoneNumber(aggregatedCall.getNumber());
            initContactProfile();
        }
    }

    private void initContactProfile() {
        TextView name = (TextView) findViewById(R.id.contact_name);
        ImageView img = (ImageView) findViewById(R.id.imageView);
        if (this.contact == Contact.NullContact) {
            name.setText(aggregatedCall.getNumber());
            img.setImageResource(R.drawable.no_image);
        } else {
            name.setText(this.contact.getName());
            if(! (this.contact instanceof ContactwithNoPhoto)){
            img.setImageBitmap(this.contact.getPhoto());
            }
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
