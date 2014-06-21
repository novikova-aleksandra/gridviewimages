package com.twogis.gridviewimages;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BackgroundActivity extends FragmentActivity {

    private List<String> urls = new ArrayList<String>();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.actitivy_layout);

        if(isOnline()) {
            new GSON(this, this, savedInstanceState).execute(new Object[]{});
        } else {
            final TextView textView = (TextView) findViewById(R.id.noconnection);
            textView.setText(R.string.no_connection_message);
            final Button button = (Button) findViewById(R.id.continuebutton);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isOnline()) {
                        textView.setVisibility(View.GONE);
                        button.setVisibility(View.GONE);
                        new GSON(BackgroundActivity.this, BackgroundActivity.this, savedInstanceState).execute(new Object[] {});
                    } else {
                        popDialog();
                    }
                }
            });
        }

    }

    protected void showFullImage(String url) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragments, FullImageFragment.newInstance(url))
                .addToBackStack(null)
                .commit();
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void popDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.no_connection_title)
                .setMessage(R.string.no_connection_message)
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

}