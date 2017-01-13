package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.Toast;

import com.example.jay.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import gradle.udacity.com.jokedisplay.JokeActivity;

import static gradle.udacity.com.jokedisplay.JokeActivity.ARG_JOKE;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private MyApi myApiService = null;
    private Context context;
    private ProgressDialog progressDialog;
    public AsyncCompletedListener asyncCompletedListener;

    public EndpointsAsyncTask(Context context){
        this.context = context;
}



    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Joke is generating...");
        progressDialog.setMessage("Joke Joke");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
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

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.sayJoke().execute().getData();
        } catch (IOException e) {
            Log.d("Async",e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(result!=null){

            Log.d("Main", result);
            progressDialog.hide();
            super.onPostExecute(result);
            Intent intent = new Intent(context, JokeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ARG_JOKE, result);
            context.startActivity(intent);
        }else {
            progressDialog.hide();
            Toast.makeText(context,"No internet",Toast.LENGTH_SHORT).show();
        }

    }

    interface AsyncCompletedListener {
        void onComplete(String result);
    }
}