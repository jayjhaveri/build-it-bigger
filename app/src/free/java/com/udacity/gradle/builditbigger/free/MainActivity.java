package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jay.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;

import java.io.IOException;

import gradle.udacity.com.jokedisplay.JokeActivity;

import static gradle.udacity.com.jokedisplay.JokeActivity.ARG_JOKE;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    int mLaunch = 0;
    public static String mResult;
    public  Context mContext;

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

    public void tellJoke(View view) {
//        Toast.makeText(this, JokeLibrary.getJoke(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(MainActivity.this, JokeActivity.class);
//        JokeLibrary joke = new JokeLibrary();
//        joke.setJoke("Hi");
//        intent.putExtra(ARG_JOKE,joke.getJoke());
//        startActivity(intent);
        mContext = MainActivity.this;
        new EndpointsAsyncTask(mContext).execute(new Pair<Context, String>(mContext, "Manfred"));
//        if (mLaunch ==0){
//
//            getSupportLoaderManager().initLoader(2,null,this);
//        }else {
//            getSupportLoaderManager().restartLoader(2,null,this);
//        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<String>(this) {
            private MyApi myApiService = null;
            String joke=null;
            @Override
            protected void onStartLoading() {
               if (joke!=null){
                   deliverResult(joke);
               }else {
                   /*progressDialog.setTitle("Joke is generating...");
                   progressDialog.setMessage("Joke Joke");
                   progressDialog.show();*/
                   forceLoad();
               }
            }

            @Override
            public String loadInBackground() {
                if (myApiService == null) {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("https://jokegenerator-155405.appspot.com/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
                    // end options for devappserver

                    myApiService = builder.build();
                }
                try {
                    return myApiService.sayJoke().execute().getData();
                } catch (IOException e) {
                    return e.getMessage();
                }
            }

            @Override
            public void deliverResult(String data) {
                joke = data;
                mLaunch = 1;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d("Main", data);
        mLaunch =1;
//        progressDialog.cancel();
        Intent intent = new Intent(MainActivity.this, JokeActivity.class);
        intent.putExtra(ARG_JOKE, data);
        startActivity(intent);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }





}
