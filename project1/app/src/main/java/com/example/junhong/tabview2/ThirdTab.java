package com.example.junhong.tabview2;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Created by Junhong on 2015-12-26.
 */
public class ThirdTab extends Fragment {
    private String _url = "https://clinic.kaist.ac.kr/pages/view/2";
    private String TAG = "ThirdTab";
    private HTMLparserTask parser;

    public ThirdTab(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.third_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parser = new HTMLparserTask();
        parser.execute();
    }

    public class HTMLparserTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = null;
            sh = new ServiceHandler(_url, getContext());

            if(null == sh){
                Log.i(TAG, "sh is null");
            }else{
                String treat_data = sh.loadPage();
                Log.i(TAG, "HTMLparserTask" + treat_data);
            }
            return null;
        }
    }
}
