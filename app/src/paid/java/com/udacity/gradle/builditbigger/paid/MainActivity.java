package com.udacity.gradle.builditbigger.paid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;

import gradle.udacity.com.jokedisplay.JokeActivity;

import static gradle.udacity.com.jokedisplay.JokeActivity.ARG_JOKE;

public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.AsyncCompletedListener {

    public ProgressDialog progressDialog;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        mContext = MainActivity.this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("Joke is generating...");
        progressDialog.setMessage("Joke Joke");
        progressDialog.show();
        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(mContext, "Manfred"));
    }

    private void startJokeActivity(String joke){
        Intent intent = new Intent(this, JokeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ARG_JOKE, joke);
        startActivity(intent);
    }

    @Override
    public void onComplete(String result) {
        progressDialog.hide();
        startJokeActivity(result);
    }
}
