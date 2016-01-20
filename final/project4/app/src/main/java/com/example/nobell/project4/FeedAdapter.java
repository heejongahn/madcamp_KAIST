package com.example.nobell.project4;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    Context context;
    List<Feed_item> items;
    int item_layout;
    public FeedAdapter(Context context, List<Feed_item> items, int item_layout) {
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed,null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Feed_item item=items.get(position);
        Drawable drawable=context.getResources().getDrawable(item.getImage());
        holder.image.setBackground(drawable);
        holder.title.setText(item.getTitle());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,item.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            title=(TextView)itemView.findViewById(R.id.title);
            cardview=(CardView)itemView.findViewById(R.id.cardview);
        }
    }
}

///********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
//public class FeedAdapter extends BaseAdapter implements View.OnClickListener {
//
//    /*********** Declare Used Variables *********/
//    private Activity activity;
//    private ArrayList data;
//    private static LayoutInflater inflater = null;
//    public Resources res;
//    String tempValues=null;
//
//    /*************  CustomAdapter Constructor *****************/
//    public FeedAdapter(Activity a, ArrayList d, Resources resLocal) {
//
//        /********** Take passed values **********/
//        activity = a;
//        data = d;
//        res = resLocal;
//
//        /***********  Layout inflator to call external xml layout () ***********/
//        inflater = (LayoutInflater)activity.
//                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//    }
//
//    /******** What is the size of Passed Arraylist Size ************/
//    public int getCount() {
//
//        if(data.size()<=0)
//            return 0;
//        return data.size();
//    }
//
//    public Object getItem(int position) {
//        return position;
//    }
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//    /********* Create a holder Class to contain inflated xml file elements *********/
//    public static class ViewHolder{
//        public TextView text;
//    }
//
//    /****** Depends upon data size called for each row , Create each ListView row *****/
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        View vi = convertView;
//        final ViewHolder holder;
//
//        if(convertView==null){
//
//            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
//            vi = inflater.inflate(R.layout.adapter_feed, null);
//
//            /****** View Holder Object to contain tabitem.xml file elements ******/
//
//            holder = new ViewHolder();
//            holder.text = (TextView) vi.findViewById(R.id.objectName);
//
//            /************  Set holder with LayoutInflater ************/
//            vi.setTag( holder );
//        }
//        else
//            holder=(ViewHolder)vi.getTag();
//
//
//
//        if(data.size()<=0)
//        {
//            //holder.text.setText("No Data");
//        }
//
//        else
//        {
//            /***** Get each Model object from Arraylist ********/
//            tempValues = null;
//            tempValues =  data.get( position ).toString();
//
//            /************  Set Model values in Holder elements ***********/
//
//            holder.text.setText(tempValues);
//            final TextView objectName = holder.text;
//
//        }
//        return vi;
//    }
//
//    @Override
//    public void onClick(View v) {
//        Log.v("CustomAdapter", "=====Row button clicked=====");
//    }
//
//    /********* Called when Item click in ListView ************/
//    private class OnItemClickListener  implements View.OnClickListener {
//        private int mPosition;
//
//        OnItemClickListener(int position){
//            mPosition = position;
//        }
//
//        @Override
//        public void onClick(View arg0) {
//
//
//        }
//    }
//}