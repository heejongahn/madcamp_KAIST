package com.example.nobell.project3.ui;


import android.content.Context;
import android.os.Bundle;
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

    public EventTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        List<Event> eventqs = new Select().all().from(Event.class).execute();
        mEventAdapter = new EventAdapter(this.getActivity(), R.layout.event_item, events);
    }

    @Override
    public void reactivated() {
        if (updated) {
            // update the UI.
            updated = false;
        }
    }
    @Override
    public void notifyChanged() {
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
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.fragment_event_tab, container, false);

        ListView listView = (ListView) layout.findViewById(R.id.event_listview);
        listView.setAdapter(mEventAdapter);

        return layout;
    }
}
