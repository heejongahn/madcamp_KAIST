package com.example.nobell.project3.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Tag;


public class TagDetailFragment extends Fragment {
    private Tag tag;
    private Long tagId;
    public Long eea;

    public static void activate(Tag tag) {
        /* Setting arguments to newly created fragment. */
        Bundle args = new Bundle();
        args.putLong("tagId", tag.getId());

        /* Make new fragment object with given arguments. */
        Fragment frag = TagDetailFragment.instantiate(MainActivity.getInstance(), TagDetailFragment.class.getName(), args);
        MainActivity.getInstance().startFragment(frag);
    }

    public TagDetailFragment() {
    }
    public void onCreate(Bundle instance) {
        super.onCreate(instance);

        /* After the real fragment started,
         * get arguments from the fragment. */
        tagId = getArguments().getLong("tagId", 0);
        tag = new Select().from(Tag.class).where("Id = ?", tagId).executeSingle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tag_detail, container, false);
        TextView tv = (TextView) view.findViewById(R.id.tagdetailtext);

        tv.setText("tag Detail page: "+tag.tagName+", tagId = "+tagId);

        return view;
    }


}
