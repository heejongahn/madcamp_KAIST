package com.example.nobell.project3.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Appearance;
import com.example.nobell.project3.dataset.Description;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;

import java.util.List;

public class EventTabFragment extends Fragment implements Updatable, Representable{
    private boolean updated = false;
    private EventAdapter mEventAdapter;
    private ListView mListView;

    public EventTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        List<Event> events = new Select().all().from(Event.class).execute();
        mEventAdapter = new EventAdapter(this.getActivity(), R.layout.event_item, events);
    }

    @Override
    public void reactivated() {
        if (updated) {
            // update the UI.
            List<Event> events = new Select().all().from(Event.class).execute();
            Log.i("Debug", String.format("Reactivated, %d", events.size()));
            mEventAdapter = new EventAdapter(this.getActivity(), R.layout.event_item, events);
            mListView.setAdapter(mEventAdapter);
            updated = false;
        }
    }
    @Override
    public void notifyChanged(Object arg) {
        updated = true;
    }
    @Override
    public String getTitle() {
        return "일기 모아보기";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_event_tab, container, false);

        mListView = (ListView) layout.findViewById(R.id.event_listview);
        mListView.setAdapter(mEventAdapter);

        TextView writeTriggerTextView = (TextView) layout.findViewById(R.id.write_event_trigger);
        writeTriggerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteEventFragment.activate(null);
            }
        });

        return layout;
    }
}
