package com.example.nobell.project3.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;
import com.example.nobell.project3.dataset.Friend;

/**
 * Created by Sia on 2016-01-11.
 */
public class FriendAddFragment extends Fragment implements Representable{

    String name;
    String phoneNumber;
    String memo;
    Bitmap photo;

    public FriendAddFragment() {
    }

    public static void activate()
    {
        Bundle args = new Bundle();

        Fragment frag = FriendAddFragment.instantiate(MainActivity.getInstance(), FriendAddFragment.class.getName(), args);
        MainActivity.getInstance().startFragment(frag);
    }

    public void onCreate(Bundle instance) {
        super.onCreate(instance);
        name = null;
        phoneNumber = null;
        memo = null;
        photo = null;
    }

    @Override
    public String getTitle() {
        return "친구 추가";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_add,null);

        Drawable defaultImg = getResources().getDrawable(R.drawable.basic_profile);

        ImageView img = (ImageView)view.findViewById(R.id.friend_add_image);
        img.setImageDrawable(defaultImg);
        photo = ((BitmapDrawable)defaultImg).getBitmap();

        Button saveButton = (Button) view.findViewById(R.id.friend_add_save_button);
        saveButton.setOnClickListener(new saveButtonListener());

        return view;
    }

    public class saveButtonListener implements View.OnClickListener {
        public saveButtonListener() {}

        @Override
        public void onClick(View v) {
            EditText nameEdit = (EditText) getActivity().findViewById(R.id.friend_add_name_body);
            name = nameEdit.getText().toString();

            EditText phonoNumberEdit = (EditText) getActivity().findViewById(R.id.friend_add_phone_number_body);
            phoneNumber = phonoNumberEdit.getText().toString();

            EditText memoEdit = (EditText) getActivity().findViewById(R.id.friend_add_memo_body);
            memo = memoEdit.getText().toString();

            Friend friend = new Friend();
            friend.name = name;
            friend.phoneNumber = phoneNumber;
            friend.memo = memo;

            friend.save();

            ((MainActivity)getActivity()).notifyChangedToFragments();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
