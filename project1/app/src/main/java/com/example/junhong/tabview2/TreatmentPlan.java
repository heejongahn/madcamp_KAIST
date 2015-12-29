package com.example.junhong.tabview2;

/**
 * Created by Junhong on 2015-12-29.
 */
public class TreatmentPlan {
    private String _treatment_name;  //진료과명
    private int _month;  //월
    private int[] _date;   //일
    private int _day;    //요일, 1, 2, 3, 4, 5, 6, 7
    private boolean _morning;    //오전
    private boolean _afternoon;  //오후
    private boolean _substitute;  //대체진료

    public TreatmentPlan(){
        _treatment_name = null;
        _month = 0;
        _date = new int[0];
        _day = 0;
        _morning = false;
        _afternoon = false;
        _substitute = false;
    }

    public TreatmentPlan(String treatment_name, int month, int[] date, int day, boolean morning, boolean afternoon, boolean substitue){
        _treatment_name = treatment_name;
        _month = month;
        _date = date;
        _day = day;
        _morning = morning;
        _afternoon = afternoon;
        _substitute = substitue;
    }

    public String getTreatment_name(){
        return _treatment_name;
    }

    public int get_month(){
        return _month;
    }

    public int[] get_date(){
        return _date;
    }

    public int get_day(){
        return _day;
    }

    public boolean get_morning(){
        return _morning;
    }

    public boolean get_afternoon(){
        return _afternoon;
    }

    public boolean get_substitute(){
        return _substitute;
    }
}
