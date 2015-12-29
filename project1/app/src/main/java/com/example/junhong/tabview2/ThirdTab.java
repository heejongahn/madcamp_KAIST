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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Junhong on 2015-12-26.
 */
public class ThirdTab extends Fragment {
    private String _url = "https://clinic.kaist.ac.kr/pages/view/2";
    private String TAG = "ThirdTab";
    private HTMLparserTask parser;
    private ArrayList<TreatmentPlan> restList;
    private ReservedPlan reserved;
    private static boolean[] mSchedule = new boolean[20];

    public ThirdTab(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        parser = new HTMLparserTask();
        parser.execute();
        restList = new ArrayList<TreatmentPlan>();
        reserved = new ReservedPlan();
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

                String raw_treat_name = (td_name.getAllElements(HTMLElementName.FONT).get(0)).getContent().toString();
                String[] separated = raw_treat_name.split(":");
                String treat_name = separated[0];

                String td_rest_font = (td_rest.getAllElements(HTMLElementName.FONT).get(0)).getContent().toString();
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

                    if(raw_rest_date[j].indexOf("월") == -1) {   //"월" 몇 월이 없는 경우
                        continue;
                    } else {
                        String[] separated_rest = raw_rest_date[j].split(" ");
                        String st = "12월 &nbsp;";
                        st = st.replace("&nbsp;", "");
                        boolean first = true;
                        for(int k = 0; k < separated_rest.length; k++){
                            separated_rest[k] = separated_rest[k].replaceAll("&nbsp;", "");

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

                        if(separated_rest[month_index + 2].matches("~")){
                            String[] start_date = separated_rest[month_index + 1].split("일");
                            int start_rest = Integer.parseInt(start_date[0]);

                            String[] end_date = separated_rest[month_index + 3].split("일");
                            int end_rest = Integer.parseInt(end_date[0]);

                            date = new int[end_rest - start_rest + 1];
                            for(int k = 0; k < (end_rest - start_rest + 1); k++){
                                date[k] = start_rest + k;
                            }
                            last_rest_day_index = month_index + 3;
                        } else {
                            String[] temp_date = separated_rest[month_index + 1].split("일");
                            date = new int[1];
                            date[0] = Integer.parseInt(temp_date[0]);
                            last_rest_day_index = month_index + 1;
                        }

                        if(separated_rest[last_rest_day_index + 1].matches("오전")){
                            morning = true;
                        }else if(separated_rest[last_rest_day_index + 1].matches("오후")){
                            afternoon = true;
                        }else if(separated_rest[last_rest_day_index + 1].matches("대체진료")){
                            substitute = true;
                        }

                        TreatmentPlan temp = new TreatmentPlan(treat_name, month, date, day, morning, afternoon, substitute);
                        restList.add(temp);
                    }
                }

            }

            return null;
        }
    }

    private class onDateChangeListener implements CalendarView.OnDateChangeListener {
        public void onDateChangeListener() {}

        public void	onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            // 가정의학과
            mSchedule[0] = isOpened("home_medical", true, year, month+1, dayOfMonth);
            mSchedule[1] = isOpened("home_medical", false, year, month+1, dayOfMonth);

            // 내과
            mSchedule[2] = isOpened("internal", true, year, month+1, dayOfMonth);
            mSchedule[3] = isOpened("internal", false, year, month+1, dayOfMonth);

            // 소화기내과
            mSchedule[4] = isOpened("gastrointestinal", true, year, month+1, dayOfMonth);
            mSchedule[5] = isOpened("gastrointestinal", false, year, month+1, dayOfMonth);

            // 스트레스 클리닉
            mSchedule[6] = isOpened("stress", true, year, month+1, dayOfMonth);
            mSchedule[7] = isOpened("stress", false, year, month+1, dayOfMonth);

            // 신경과
            mSchedule[8] = isOpened("neurology", true, year, month+1, dayOfMonth);
            mSchedule[9] = isOpened("neurology", false, year, month+1, dayOfMonth);

            // 안과
            mSchedule[10] = isOpened("eye", true, year, month+1, dayOfMonth);
            mSchedule[11] = isOpened("eye", false, year, month+1, dayOfMonth);

            // 영상의학과
            mSchedule[12] = isOpened("image", true, year, month+1, dayOfMonth);
            mSchedule[13] = isOpened("image", false, year, month+1, dayOfMonth);

            // 이비인후과
            mSchedule[14] = isOpened("ear_nose", true, year, month+1, dayOfMonth);
            mSchedule[15] = isOpened("ear_nose", false, year, month+1, dayOfMonth);

            // 치과
            mSchedule[16] = isOpened("dental", true, year, month+1, dayOfMonth);
            mSchedule[17] = isOpened("dental", false, year, month+1, dayOfMonth);

            // 피부과
            mSchedule[18] = isOpened("skin", true, year, month+1, dayOfMonth);
            mSchedule[19] = isOpened("skin", false, year, month+1, dayOfMonth);


            showDialog(year, month, dayOfMonth);
        }

        public boolean isOpened(String department, boolean morning, int year, int month, int dayOfMonth) {
            String dateString = String.format("%d/%d/%d", dayOfMonth, month, year);
            Date date;
            boolean opened = true;

            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
                String dayOfWeek=new SimpleDateFormat("EEEE").format(date);

                switch (dayOfWeek) {
                    case "Monday":
                        opened = reserved.isReserved(department, 0, morning);
                        break;

                    case "Tuesday":
                        opened = reserved.isReserved(department, 1, morning);
                        break;

                    case "Wednesday":
                        opened = reserved.isReserved(department, 2, morning);
                        break;

                    case "Thursday":
                        opened = reserved.isReserved(department, 3, morning);
                        break;

                    case "Friday":
                        opened = reserved.isReserved(department, 4, morning);
                        break;

                    default:
                        opened = false;

                }
                // 대체진료 혹은 휴일
                for (TreatmentPlan plan: restList) {
                    for (int plannedDate: plan.get_date()) {
                        if (plannedDate == dayOfMonth && plan.get_department().equals(department)){
                            opened = plan.get_substitute();
                        }
                    }
                }

            } catch(ParseException e) {
                Log.i(TAG, "ParseException");
            };

            return opened;
        }
    }

    void showDialog(int year, int month, int dayOfMonth) {
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
        DialogFragment newFragment = Dialog.newInstance(mSchedule, year, month, dayOfMonth);
        newFragment.show(ft, "dialog");
    }
}
