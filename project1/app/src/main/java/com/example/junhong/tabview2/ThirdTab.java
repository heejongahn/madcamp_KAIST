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

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Junhong on 2015-12-26.
 */
public class ThirdTab extends Fragment {
    private String _url = "https://clinic.kaist.ac.kr/pages/view/2";
    private String TAG = "ThirdTab";
    private HTMLparserTask parser;
    private ArrayList<TreatmentPlan> restList;

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
        private String treat_data;

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = null;
            sh = new ServiceHandler(_url, getContext());

            if(null == sh){
                Log.i(TAG, "sh is null");
            }else{
                treat_data = sh.loadPage();
            }

            Source source = new Source(treat_data);
            source.fullSequentialParse();
            Element table = source.getAllElements(HTMLElementName.TABLE).get(1);
            int tr_count = table.getAllElements(HTMLElementName.TR).size();
            for(int i = 0; i < tr_count; i++){ //original is tr_count but in order to debug
                Element tr = table.getAllElements(HTMLElementName.TR).get(i);
                Element td_name = tr.getAllElements(HTMLElementName.TD).get(0);
                Element td_rest = tr.getAllElements(HTMLElementName.TD).get(1);
                Log.i(TAG, "td_rest : " + td_rest);

                String raw_treat_name = (td_name.getAllElements(HTMLElementName.FONT).get(0)).getContent().toString();
                String[] separated = raw_treat_name.split(":");
                String treat_name = separated[0];
                Log.i(TAG, "treat_name :" + treat_name);

                String td_rest_font = (td_rest.getAllElements(HTMLElementName.FONT).get(0)).getContent().toString();
                Log.i(TAG, "td_rest_font : " + td_rest_font);
                String[] raw_rest_date = td_rest_font.toString().split("<br />");
                for(int j = 0; j < raw_rest_date.length; j++){

                    int month_index = 0;
                    int month = 0;  //월
                    int[] date = null;   //일
                    int last_rest_day_index = 0;    //마지막 휴진일의 index
                    int day = 0;    //요일, 1, 2, 3, 4, 5, 6, 7
                    boolean morning = false;    //오전
                    boolean afternoon = false;  //오후
                    boolean substitute = false;  //대체진료

                    Log.i(TAG, "array : " + raw_rest_date[j]);
                    if(raw_rest_date[j].indexOf("월") == -1) {   //"월" 몇 월이 없는 경우
                        continue;
                    } else {
                        String[] separated_rest = raw_rest_date[j].split(" ");
                        String st = "12월 &nbsp;";
                        st = st.replace("&nbsp;", "");
                        Log.i(TAG, "st : " + st);
                        boolean first = true;
                        for(int k = 0; k < separated_rest.length; k++){
                            separated_rest[k] = separated_rest[k].replaceAll("&nbsp;", "");

                            Log.i(TAG, "month_separated[" + k + "] : " + separated_rest[k]);
                            if(separated_rest[k].indexOf("월") == -1){
                                continue;
                            } else {
                                if(first){
                                    first = false;
                                    month_index = k;
                                }
                            }
                        }

                        String[] temp_month = separated_rest[month_index].split("월");
                        if(temp_month[0].indexOf(">") == -1){
                            month = Integer.parseInt(temp_month[0]);
                        } else {
                            String[] temp_month2 = temp_month[0].split(">");
                            month = Integer.parseInt(temp_month2[1]);
                        }

                        Log.i(TAG, "month : " + month);

                        if(separated_rest[month_index + 2].matches("~")){
                            String[] start_date = separated_rest[month_index + 1].split("일");
                            int start_rest = Integer.parseInt(start_date[0]);
                            Log.i(TAG, "start_rest : " + start_rest);

                            String[] end_date = separated_rest[month_index + 3].split("일");
                            int end_rest = Integer.parseInt(end_date[0]);
                            Log.i(TAG, "end_rest : " + end_rest);

                            date = new int[end_rest - start_rest + 1];
                            for(int k = 0; k < (end_rest - start_rest + 1); k++){
                                date[k] = start_rest + k;
                                Log.i(TAG, "rest days[" + k + "]: " + date[k]);
                            }
                            last_rest_day_index = month_index + 3;
                        } else {
                            String[] temp_date = separated_rest[month_index + 1].split("일");
                            date = new int[1];
                            date[0] = Integer.parseInt(temp_date[0]);
                            Log.i(TAG, "date : " + date[0]);
                            last_rest_day_index = month_index + 1;
                        }

                        if(separated_rest[last_rest_day_index + 1].matches("오전")){
                            morning = true;
                            Log.i(TAG, "morning : " + morning);
                        }else if(separated_rest[last_rest_day_index + 1].matches("오후")){
                            afternoon = true;
                            Log.i(TAG, "afternoon : " + afternoon);
                        }else if(separated_rest[last_rest_day_index + 1].matches("대체진료")){
                            substitute = true;
                            Log.i(TAG, "substitute : " + substitute);
                        }else if(separated_rest[last_rest_day_index + 1].matches("휴진")){
                            Log.i(TAG, "rest");
                        }

                        TreatmentPlan temp = new TreatmentPlan(treat_name, month, date, day, morning, afternoon, substitute);
                        restList.add(temp);
                        Log.i(TAG, "restList : " + restList.size());
                    }
                }

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
