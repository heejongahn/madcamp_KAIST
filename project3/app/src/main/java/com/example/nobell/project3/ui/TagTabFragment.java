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
import com.example.nobell.project3.lib.tag.TagContainerLayout;
import com.example.nobell.project3.lib.tag.TagView;

import java.util.ArrayList;
import java.util.List;

public class TagTabFragment extends Fragment implements Updatable, Representable{
    private List<Tag> tags;
    private boolean updated = false;

    public TagTabFragment() {
    }

    @Override
    public void reactivated() {
        if (updated) {
            // update the UI.
            updated = false;
        }
    }
    @Override
    public void notifyChanged(Object arg) {
        updated = true;
    }

    @Override
    public String getTitle() {
        return "태그 모아보기";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag_tab, container, false);
        TagContainerLayout tagContainerLayout = (TagContainerLayout) view.findViewById(R.id.tagcontainerlayout);
        tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                TagDetailFragment.activate(tags.get(position));
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }
        });

        tags = new Select().all().from(Tag.class).execute();
        List<String> tagNames = new ArrayList<String> ();
        for (Tag t: tags)
            tagNames.add(t.tagName);
        tagContainerLayout.setTags(tagNames);
        return view;
    }
}
