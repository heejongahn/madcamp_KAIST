package com.example.nobell.project3.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Event;

import java.util.List;

/**
 * Created by Sia on 2016-01-09.
 */
public class FriendTabCustomListView extends BaseAdapter{

    private LayoutInflater inflater;
    private Context m_context;
    private int layout;
    private List<Event> mEvents;

    public FriendTabCustomListView(Context context, int layout, List<Event> events){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.m_context = context;
        this.layout = layout;
        this.mEvents = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView=inflater.inflate(layout,parent,false);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.imageview);
        Bitmap orgImage = BitmapFactory.decodeResource(m_context.getResources(), R.drawable.basic_profile);
        Bitmap resize = Bitmap.createScaledBitmap(orgImage,100,100,true);
        icon.setImageBitmap(resize);

        Event event = mEvents.get(position);
        TextView textContents = (TextView)convertView.findViewById(R.id.textview);
        textContents.setText(event.body);

        return convertView;

    }

    @Override
    public int getCount(){return mEvents.size();}
    @Override
    public String getItem(int position){return null;}
    @Override
    public long getItemId(int position){return position;}
}
