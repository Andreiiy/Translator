package com.example.translater;

import android.os.AsyncTask;
import android.widget.TextView;

public class MyAsynkTask extends AsyncTask<String,Void,String> {
    private TextView tvTranslated;
    public MyAsynkTask(TextView tvTranslated) {
this.tvTranslated = tvTranslated;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s!=null)
        tvTranslated.setText(s);

    }
}
