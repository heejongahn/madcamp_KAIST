package com.example.nobell.madagora;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PeopleCountFragment extends Fragment {
    private static final String DEBUG_TAG = "DEBUG_madagora";
    private static final String SERVER_URL = "http://bit.sparcs.org:8282";

    public static PeopleCountFragment newInstance() {
        PeopleCountFragment fragment = new PeopleCountFragment();
        return fragment;
    }

    public PeopleCountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_people_count, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new GetPeopleCountTask().execute();
        } else {
            Log.d(DEBUG_TAG, "Connection Failure");
        }
    }

    private class GetPeopleCountTask extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... voids) {
            try {
                return getPeopleCount();
            } catch (IOException e) {
                return "Not Connected to Internet.";
            }
        }

        protected void onPostExecute(String result) {
            TextView countView = (TextView) getView().findViewById(R.id.people_count);
            countView.setText(result);
            Log.i(DEBUG_TAG, "SUCCESS");
        }
    }


    private String getPeopleCount() throws IOException{
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            String count = readIt(is, len);
            return String.format("현재 실습실에는 %s명이 있습니다!", count);
        } finally{
            if (is != null) {
                is.close();
            }
        }
    }


    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];

        int bodyLength = reader.read(buffer);
        char[] result = new char[bodyLength];
        System.arraycopy(buffer, 0, result, 0, bodyLength);
        return new String(result);
    }

}
