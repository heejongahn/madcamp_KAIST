package com.example.junhong.tabview2;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Junhong on 2015-12-28.
 */
public class ServiceHandler {
    private StringBuffer buffer;
    private String TAG = "ServiceHandler";
    private String _url;

    public ServiceHandler(String url){
        _url = url;
    }

    public String loadPage(){
        try {
            URL url = new URL(_url);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            buffer = new StringBuffer();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "doInBackground" + buffer.toString());
        return buffer.toString();
    }

    private void readStream(InputStream in) {
        int c;
        try {
            while((c = in.read()) != -1){
                buffer.append((char)c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
