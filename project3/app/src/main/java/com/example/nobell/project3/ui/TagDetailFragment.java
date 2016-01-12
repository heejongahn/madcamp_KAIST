package com.example.nobell.project3.ui;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;
import com.example.nobell.project3.lib.Utils;

import java.util.List;


public class TagDetailFragment extends Fragment implements Representable, Updatable{
    private Tag tag;
    private Long tagId;
    private TextView tv_friends[];
    private TextView tv_mainevent;
    private boolean changed;
//    private final int friendbox = Color.parseColor("#11805030");
//    private final int friendbox_border = Color.parseColor("#22805030");
//    private final int press = Color.parseColor("#33805030");
//    private final float radius = 10.0f;

    private List<Friend> top3friends;
    private List<Event> events;
    private EventAdapter adapter;
    private ListView tv_events;

    @Override
    public void reactivated() {
        if (changed) {
            initializeData();
            changed = false;
        }
    }


    @Override
    public void notifyChanged(Object o) {
        changed = true;
    }

    public static void activate(Tag tag) {
        /* Setting arguments to newly created fragment. */
        Bundle args = new Bundle();
        args.putLong("tagId", Tag.getIdWithCache(tag));

        /* Make new fragment object with given arguments. */
        Fragment frag = TagDetailFragment.instantiate(MainActivity.getInstance(), TagDetailFragment.class.getName(), args);
        MainActivity.getInstance().startFragment(frag);
    }

    public TagDetailFragment() {
    }

    @Override
    public String getTitle() {
        return "태그 살펴보기";
    }

    @Override
    public void onCreate(Bundle instance) {
        super.onCreate(instance);

        /* After the real fragment started,
         * get arguments from the fragment. */
        tagId = getArguments().getLong("tagId", 0);
        tag = Tag.flushCache(tagId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tag_detail, container, false);
        TextView tv_title = (TextView) view.findViewById(R.id.tagdetailtitle);
        tv_mainevent = (TextView) view.findViewById(R.id.tagdetailmainevent);
        tv_friends = new TextView[] {(TextView) view.findViewById(R.id.tag_friend1),
                                       (TextView) view.findViewById(R.id.tag_friend2),
                                       (TextView) view.findViewById(R.id.tag_friend3)};
        tv_events = (ListView) view.findViewById(R.id.tagdetailotherevents);

        tv_title.setText(tag.tagName);

        initializeData();

//        tv_event.setText("eeeeeeeeeeeeeeeeeeeeeeeeeeeevvvvvvvvvvveeeeeeeeeeeeennnnnnnnnnnnnnnnnnnnnnnnnnnnnt");
//        makeBackground(tv_event, friendbox, friendbox_border, press, radius);
//
//        tv_fr1.setText("friend1");
//        makeBackground(tv_fr1, friendbox, friendbox_border, press, radius);
//        tv_fr2.setText("friend2");
//        makeBackground(tv_fr2, friendbox, friendbox_border, press, radius);
//        tv_fr3.setText("friend3");
//        makeBackground(tv_fr3, friendbox, friendbox_border, press, radius);


        return view;
    }

    private class FriendListener implements View.OnClickListener {
        private Friend f;
        public FriendListener(Friend f) {
            this.f = f;
        }
        @Override
        public void onClick(View v) {
            if (f != null)
                FriendDetailFragment.activate(f);
        }
    }
    public void initializeData() {
        top3friends = tag.getFriendsTopThree();
        for (int i = 0; i < 3; i++) {
            if(i < top3friends.size()) {
                /* Fill with friend */
                tv_friends[i].setText(top3friends.get(i).name);
                tv_friends[i].setOnClickListener(new FriendListener(top3friends.get(i)));
            }
            else {
                /* There are some friends less than three. */
                tv_friends[i].setText("");
                tv_friends[i].setBackgroundColor(Color.TRANSPARENT);
            }
        }
        events = tag.getEventsWithOrder();
        if (events.size() == 0)
            tv_mainevent.setText("아직 작성된 이벤트가 없습니다");
        else
            tv_mainevent.setText(events.get(0).body);
        adapter = new EventAdapter(getContext(), R.layout.event_item, events);
        tv_events.setAdapter(adapter);
    }
//    public void makeBackground(View v, int normal, int border, int press, float rad) {
//        GradientDrawable gd_normal = new GradientDrawable();
//        gd_normal.setColor(normal);
//        gd_normal.setCornerRadius(rad);
//        gd_normal.setStroke(Utils.dipToPx(getContext(), 2), border);
//
//        GradientDrawable gd_press = new GradientDrawable();
//        gd_press.setColor(press);
//        gd_press.setCornerRadius(rad);
//
//        StateListDrawable states = new StateListDrawable();
//        states.addState(new int[]{android.R.attr.state_pressed}, gd_press);
//        states.addState(new int[]{}, gd_normal);
//
//        v.setBackground(states);
//    }


}
