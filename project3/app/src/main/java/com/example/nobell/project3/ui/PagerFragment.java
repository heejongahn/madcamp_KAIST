package com.example.nobell.project3.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nobell.project3.MainActivity;
import com.example.nobell.project3.R;

import java.util.ArrayList;
import java.util.List;

public class PagerFragment extends Fragment {
    private static int instanceCount = 0;
    private ViewPager viewPager;

    public PagerFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (instanceCount >= 1) {
            Log.e("PagerFragment", "PagerFragment onCreateView called more than one time??:"+instanceCount);
        }
        instanceCount += 1;

        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.mainviewpager);
        setupViewPager(viewPager);
        ((MainActivity) getActivity()).getTabLayout().setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onHiddenChanged(boolean b) {
        super.onHiddenChanged(b);
        if (b)
            MainActivity.getInstance().getTabLayout().setVisibility(View.GONE);
        else
            MainActivity.getInstance().getTabLayout().setVisibility(View.VISIBLE);
    }
    @Override
    public void onResume() {
        Log.d("PagerFragment", "onResume called");
        ((MainActivity) getActivity()).getTabLayout().setupWithViewPager(viewPager);
        if (viewPager == null){
            Log.d("PagerFragment", "viewPager is null");
        }
        else {
            Log.d("PagerFragment", "viewPager is not null, adapter is "+viewPager.getAdapter().toString());
        }
        super.onResume();
    }

    private void setupViewPager (ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new EventTabFragment(), "Events");
        adapter.addFragment(new FriendTabFragment(), "Friends" );
        adapter.addFragment(new TagTabFragment(), "Tags");
        viewPager.setAdapter(adapter);
        /*  the number of pages that should be retained to either side of the current page
         *  default value is 1 */
        viewPager.setOffscreenPageLimit(2);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<String>();
        private int mSize = 0;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mSize;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
            mSize = mSize + 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}