package com.example.nobell.project4;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 1/22/2016.
 */
public class ServerConnector {

    public static JSONObject uploadToServer(JSONObject jsonobj, String url_tail) throws IOException, JSONException {
        String server_address = "http://hjlog.me:3000" + url_tail;
        Log.e("address", server_address);

        //String json = "{\"key\":1}";
        String json = jsonobj.toString();

        URL url = new URL(server_address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        JSONObject result =new JSONObject(responseStrBuilder.toString());

        in.close();
        conn.disconnect();

        return result;
    }

    public static JSONObject GetFromServer(String url_tail, String cookie) throws IOException, JSONException {
        String server_address = "http://hjlog.me:3000" + url_tail;
        Log.e("address", server_address);

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);

        System.out.println("########## 1");

        URL url = new URL(server_address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Cookie", "connect.sid=" + cookie );
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("GET");

        System.out.println("########## 2");

        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());

        System.out.println("########## 3");

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        System.out.println("########## 4");

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        JSONObject result = new JSONObject(responseStrBuilder.toString());

        System.out.println("########## 5");

        in.close();
        conn.disconnect();

        System.out.println("########## 6");
        return result;
    }
}
