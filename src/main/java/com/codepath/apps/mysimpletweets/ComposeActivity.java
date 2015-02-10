package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {
    TwitterClient client;
    User thisUser;
    ImageView ivUserIcon;
    TextView tvHandle;
    EditText etTweet;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        setContentView(R.layout.activity_compose);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Typeface boldFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/OpenSans-Bold.ttf");
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/OpenSans-Regular.ttf");
        ivUserIcon = (ImageView) findViewById(R.id.ivUserIcon);
        ivUserIcon.setImageResource(android.R.color.transparent);
        tvHandle = (TextView) findViewById(R.id.tvHandle);
        etTweet = (EditText) findViewById(R.id.etTweet);
        tvHandle.setTypeface(boldFont);
        etTweet.setTypeface(font);
        client = TwitterApplication.getRestClient();
        client.getThisUserProfileInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                thisUser = User.fromJSON(jsonObject);
                Picasso.with(getApplicationContext()).load(thisUser.getProfileImageUrl()).into(ivUserIcon);
                tvHandle.setText("@" + thisUser.getScreenName());
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void onTweet(View view) {
        client.composeTweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getApplicationContext(),"Tweet Sent!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(errorResponse != null) {
                    Log.d("Debug", errorResponse.toString());
                }
                Toast.makeText(getApplicationContext(),"Error sending Tweet, try again.", Toast.LENGTH_SHORT).show();
            }
        }, etTweet.getText().toString());
    }

    public void onCancel(View view) {
        this.finish();
    }
}
