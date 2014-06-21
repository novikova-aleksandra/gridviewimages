package com.twogis.gridviewimages;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class GSON extends AsyncTask<Object, Void, List<String>> {

    private static final String JSON_URL = "https://gist.githubusercontent.com/lnevermore/46f704deaece03ccd230/raw/68b44464532d32b0aeac1c813c33e0c7399d4652/jsondump";
    private static String PROGRESS_BAR_MESSAGE;
    private static String PROGRESS_BAR_TITLE;
    private static String ERROR_MESSAGE;
    private final BackgroundActivity activity;
    private final Bundle savedInstanceState;
    private ProgressDialog dialog;
    private boolean error;

    GSON(Context context, BackgroundActivity activity, Bundle savedInstanceState) {
        this.activity = activity;
        this.savedInstanceState = savedInstanceState;
        dialog = new ProgressDialog(context);
        PROGRESS_BAR_MESSAGE = context.getString(R.string.dialog_message);
        PROGRESS_BAR_TITLE = context.getString(R.string.dialog_title);
        ERROR_MESSAGE = context.getString(R.string.error_message);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle(PROGRESS_BAR_TITLE);
        dialog.setMessage(PROGRESS_BAR_MESSAGE);
        dialog.show();
    }

    @Override
    protected List<String> doInBackground(Object[] params) {
        return getURLs();
    }

    @Override
    protected void onPostExecute(List<String> urls) {
        if(urls != null) {
            activity.setUrls(urls);
            if (savedInstanceState == null) {
                activity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragments, GridViewFragment.newInstance())
                        .commit();
            }
        }
        dialog.dismiss();
        if(error) {
            TextView textView = (TextView) activity.findViewById(R.id.noconnection);
            textView.setText(ERROR_MESSAGE);
        }
    }

    private List<String> getURLs() {

        try {
            InputStream source = retrieveStream(JSON_URL);
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(source);
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            List<String> urls = gson.fromJson(reader, listType);
            return urls;
        }
        catch (JsonSyntaxException | IllegalStateException e) {
            error = true;
            return null;
        }
    }

    private InputStream retrieveStream (String url) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse getResponce = client.execute(getRequest);
            HttpEntity getResponseEntity = getResponce.getEntity();
            return getResponseEntity.getContent();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
