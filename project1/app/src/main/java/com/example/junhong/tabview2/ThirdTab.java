package com.example.junhong.tabview2;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Calendar;

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
        parser = new HTMLparserTask();
        parser.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.third_fragment, container, false);
        CalendarView calendar = (CalendarView) layout.findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new onDateChangeListener());
        return layout;
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

    private class onDateChangeListener implements CalendarView.OnDateChangeListener {
        public void onDateChangeListener() {}

        public void	onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            showDialog(dayOfMonth);

        }
    }

    void showDialog(int dayOfMonth) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = Dialog.newInstance(dayOfMonth);
        newFragment.show(ft, "dialog");
    }
}
