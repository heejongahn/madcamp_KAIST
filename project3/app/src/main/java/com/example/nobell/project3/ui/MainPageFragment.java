package com.example.nobell.project3.ui;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nobell.project3.R;
import com.example.nobell.project3.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainPageFragment extends Fragment implements Updatable, Representable{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    public MainPageFragment() {
    }

    @Override
    public void reactivated() {
        ((Updatable)(adapter.getItem(0))).reactivated();
        ((Updatable)(adapter.getItem(1))).reactivated();
        ((Updatable)(adapter.getItem(2))).reactivated();
    }
    @Override
    public void notifyChanged(Object arg) {
        ((Updatable)(adapter.getItem(0))).notifyChanged(arg);
        ((Updatable)(adapter.getItem(1))).notifyChanged(arg);
        ((Updatable)(adapter.getItem(2))).notifyChanged(arg);
    }

    @Override
    public String getTitle() {
        return ((Representable) adapter.getCurrentFragment()).getTitle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main,null);

        tabLayout = (TabLayout) view.findViewById(R.id.maintablayout);
        viewPager = (ViewPager) view.findViewById(R.id.mainviewpager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
    private void setupViewPager (ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new EventTabFragment(), "Events");
        adapter.addFragment(new FriendTabFragment(), "Friends");
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
        private Fragment currentFragment;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getCurrentFragment() {
            return currentFragment;
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

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object obj) {
            if (currentFragment != obj) {
                currentFragment = ((Fragment) obj);
                ((MainActivity)getActivity()).notifyTitleChanged();
            }
            super.setPrimaryItem(container, position, obj);
        }
    }
}
