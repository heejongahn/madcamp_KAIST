package com.example.nobell.project4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by daeseongkim on 2015. 11. 29..
 */
public class FeedFragment extends Fragment {
    ListView _listView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        _listView = (ListView)rootView.findViewById(R.id.listView);
        String jsondata = "{\"items\":{\"itemlist\":[{\"name\":\"서울\",\"age\":\"10\"},{\"name\":\"대전\",\"age\":\"20\"}]}}";
        get_list(jsondata);

        return rootView;
    }

    public void get_list (String jsondata) {
        ArrayList<String> list = new ArrayList<>();
        list.clear();

        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsondata);
            JSONObject items = (JSONObject) jsonObject.get("items");
            JSONArray itemlist = (JSONArray) items.get("itemlist");

            for (int i=0; i<itemlist.size(); i++) {
                JSONObject obj = (JSONObject) itemlist.get(i);
                String name = (String) obj.get("name");
                String age = (String) obj.get("age");
                list.add("name: " + name + " age: " + age);
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        FeedAdapter adapter = new FeedAdapter(getActivity(), list, getResources());
        _listView.setAdapter(adapter);
    }
}

