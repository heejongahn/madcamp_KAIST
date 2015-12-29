package com.example.junhong.tabview2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Dialog extends DialogFragment {
    private static boolean[] mSchedule;
    private static int mYear;
    private static int mMonth;
    private static int mDayOfMonth;

    public Dialog() {
        // Required empty public constructor
    }

    public static Dialog newInstance(boolean[] schedule, int year, int month, int dayOfMonth) {
        Dialog frag = new Dialog();
        Bundle args = new Bundle();
        args.putBooleanArray("schedule", schedule);
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("dayOfMonth", dayOfMonth);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSchedule = getArguments().getBooleanArray("schedule");
        mYear = getArguments().getInt("year");
        mMonth = getArguments().getInt("month");
        mDayOfMonth = getArguments().getInt("dayOfMonth");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog, container, false);

        TextView indicator = (TextView) layout.findViewById(R.id.date_indicator);
        indicator.setText(String.format("%d년 %d월 %d일", mYear, mMonth+1, mDayOfMonth));

        GridView gridView = (GridView) layout.findViewById(R.id.dialog_grid);
        gridView.setAdapter(new DepartmentAdapter(this.getActivity()));

        return layout;
    }

    public class DepartmentAdapter extends BaseAdapter {
        private Context mContext;
        private String mDepartments[] = {"가정의학과", "내과", "소화기내과", "스트레스", "신경과", "안과",
                "영상의학과", "이비인후과", "치과", "피부과"};

        public DepartmentAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mDepartments.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LinearLayout layout;
            TextView title, before_noon, after_noon;

            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                layout = (LinearLayout) inflater.inflate(R.layout.department, null);
            } else {
                layout = (LinearLayout) convertView;
            }

            title = (TextView) layout.findViewById(R.id.department_name);
            title.setTextColor(0xFF000000);
            before_noon = (TextView) layout.findViewById(R.id.before_noon);

            if (mSchedule[position*2]) {
                before_noon.setText("진료");
                before_noon.setTextColor(0xFF00FF00);
            }
            else {
                before_noon.setText("휴진");
                before_noon.setTextColor(0xFFFF0000);
            }

            after_noon = (TextView) layout.findViewById(R.id.after_noon);

            if (mSchedule[position*2 + 1]) {
                after_noon.setText("진료");
                after_noon.setTextColor(0xFF00FF00);
            }
            else {
                after_noon.setText("휴진");
                after_noon.setTextColor(0xFFFF0000);
            }

            title.setText(mDepartments[position]);
            return layout;
        }
    }

}
