package com.example.nobell.project3.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nobell.project3.R;

@SuppressLint("ValidFragment")
public class FriendTabFragment extends Fragment {

    Context mContext;
    private ListView m_ListView;
    private FriendTabCustomListView adapter;

    public FriendTabFragment() {
    }

    public FriendTabFragment(Context context)
    {
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_tab,null);

        m_ListView = (ListView) view.findViewById(R.id.listView);

        adapter = new FriendTabCustomListView(getContext(),R.layout.friend_combined_listview);
        adapter.notifyDataSetChanged();

        m_ListView.setAdapter(adapter);
        //m_ListView.setOnItemClickListener(mItemClickListener);

        return view;
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long l_position) {
            // TODO Add this events
        }
    };


}
