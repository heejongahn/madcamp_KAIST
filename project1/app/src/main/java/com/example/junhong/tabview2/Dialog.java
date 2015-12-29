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

public class Dialog extends DialogFragment {
    private int mDayofMonth;

    public Dialog() {
        // Required empty public constructor
    }

    public static Dialog newInstance(int day) {
        Dialog frag = new Dialog();
        Bundle args = new Bundle();
        args.putInt("dayOfMonth", day);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDayofMonth = getArguments().getInt("dayOfMonth");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView gridView = (GridView) inflater.inflate(R.layout.dialog, container, false);
        gridView.setAdapter(new DepartmentAdapter(this.getActivity()));

        return gridView;
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
            TextView title;

            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                layout = (LinearLayout) inflater.inflate(R.layout.department, null);
                title = (TextView) layout.findViewById(R.id.department_name);
            } else {
                layout = (LinearLayout) convertView;
                title = (TextView) layout.findViewById(R.id.department_name);
            }

            title.setText(mDepartments[position]);
            return layout;
        }
    }

}
