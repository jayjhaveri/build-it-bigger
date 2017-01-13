package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.util.Pair;
import android.util.Log;

import com.udacity.gradle.builditbigger.free.MainActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Jay on 1/12/2017.
 */
public class EndpointsAsyncTaskTest {

    /*@Before
    public void setPd(){
        MainActivity.progressDialog = new ProgressDialog(getContext());
    }*/

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testString() throws InterruptedException, ExecutionException, TimeoutException {

        Context context = mActivityRule.getActivity().getBaseContext();
        AsyncTask<Pair<Context, String>, Void, String> endpointsAsyncTask =
                new EndpointsAsyncTask(context).
                        execute(new Pair<Context, String>(context, "Manfred"));
        String joke = endpointsAsyncTask.get(30,TimeUnit.SECONDS);

        Assert.assertNotNull(joke);
        if (joke!=null){
            Log.d("TEST",joke);
        }
    }

}