package com.udacity.gradle.builditbigger.free;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;

import gradle.udacity.com.jokedisplay.JokeActivity;

import static gradle.udacity.com.jokedisplay.JokeActivity.ARG_JOKE;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.AsyncCompletedListener {
    public ProgressDialog progressDialog;
    public Context mContext;
    InterstitialAd mInterstitialAd;
    private String ARG_JOKE_MAIN = "joke_main_string";
    private String mJoke = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                startJokeActivity(mJoke);
            }
        });
        requestNewInterstitial();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            generateAsyncTask();


    }

    @Override
    public void onComplete(String result) {
        mJoke = result;
        progressDialog.hide();
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }else {
            startJokeActivity(result);
        }

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("D0BF979905FC8E15E033AB85FFD8B2D1")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_JOKE_MAIN,mJoke);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mJoke = savedInstanceState.getString(ARG_JOKE_MAIN);
    }

    private void generateAsyncTask() {
        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(mContext, "Manfred"));
    }

    private void startJokeActivity(String joke){
        Intent intent = new Intent(this, JokeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ARG_JOKE, joke);
        startActivity(intent);
    }

}
