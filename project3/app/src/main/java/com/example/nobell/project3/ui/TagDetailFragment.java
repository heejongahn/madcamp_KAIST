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


public class TagDetailFragment extends Fragment {
    private Tag tag;
    private Long tagId;
    private final int friendbox = Color.parseColor("#11805030");
    private final int friendbox_border = Color.parseColor("#22805030");
    private final int press = Color.parseColor("#33805030");
    private final float radius = 10.0f;

    private List<Friend> top3friends;

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
        TextView tv_mainevent = (TextView) view.findViewById(R.id.tagdetailmainevent);
        ListView tv_friends = (ListView) view.findViewById(R.id.tagdetailfriends);
        ListView tv_events = (ListView) view.findViewById(R.id.tagdetailotherevents);

        Context c = getContext();
        tv_title.setText(tag.tagName);

        top3friends = tag.getFriendsTopThree();
        FriendAdapter topFriends = new FriendAdapter(c, R.layout.friend_item, top3friends);;
        tv_friends.setAdapter(topFriends);
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).notifyChangedToFragments(null);
            }
        });

        List<Event> events = tag.getEventsWithOrder();
        tv_mainevent.setText(events.get(0).body);

        EventAdapter eventAdapter = new EventAdapter(c, R.layout.event_item, events);
        tv_events.setAdapter(eventAdapter);

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

    public void makeBackground(View v, int normal, int border, int press, float rad) {
        GradientDrawable gd_normal = new GradientDrawable();
        gd_normal.setColor(normal);
        gd_normal.setCornerRadius(rad);
        gd_normal.setStroke(Utils.dipToPx(getContext(), 2), border);

        GradientDrawable gd_press = new GradientDrawable();
        gd_press.setColor(press);
        gd_press.setCornerRadius(rad);

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, gd_press);
        states.addState(new int[]{}, gd_normal);

        v.setBackground(states);
    }


}
