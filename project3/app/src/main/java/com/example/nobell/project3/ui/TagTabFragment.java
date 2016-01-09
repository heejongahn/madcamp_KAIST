package com.example.nobell.project3.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Tag;
import com.example.nobell.project3.lib.tag.OnTagClickListener;
import com.example.nobell.project3.lib.tag.TagItem;
import com.example.nobell.project3.lib.tag.TagView;

import java.util.ArrayList;
import java.util.List;

public class TagTabFragment extends Fragment {
    private List<Tag> tags;
    public TagTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag_tab, container, false);
        TagView tagView = (TagView)view.findViewById(R.id.tags_view);
        ArrayList<TagItem> tagItems = new ArrayList<TagItem> ();

       tags = new Select().all().from(Tag.class).execute();

        int i = 0;
        for(Tag t:tags) {
            Log.d("TagTab", "tag#"+i+" ["+t.name+"]received");
            TagItem ti = new TagItem(t.name);
            ti.radius = 10f;
            ti.layoutColor = Color.rgb((i*30)%256, (i*14)%256, (i*50)%256);
            ti.isDeletable = false;
            tagItems.add(ti);
            i = i+1;
        }
        tagView.addTags(tagItems);
        tagView.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(TagItem tag, int position) {
                Toast.makeText(getContext(), "tag#" + position + "[" + tags.get(position).name+"]", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
