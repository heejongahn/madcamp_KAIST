package com.example.nobell.project3;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.nobell.project3.dataset.Appearance;
import com.example.nobell.project3.dataset.Description;
import com.example.nobell.project3.dataset.Event;
import com.example.nobell.project3.dataset.Friend;
import com.example.nobell.project3.dataset.Tag;
import com.example.nobell.project3.ui.EventTabFragment;
import com.example.nobell.project3.ui.FriendTabFragment;
import com.example.nobell.project3.ui.MainTabLayout;
import com.example.nobell.project3.ui.PagerFragment;
import com.example.nobell.project3.ui.TagTabFragment;
import com.example.nobell.project3.ui.Updatable;
import com.example.nobell.project3.ui.WriteEventFragment;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private PagerFragment pagerFragment;
    private TabLayout tabLayout;

    /* Fragment that should have some response for reactivation. */
    private List<Fragment> fragmentStack = new ArrayList<Fragment> ();

    /* Singleton class */
    private static MainActivity mInstance;

    /* To managing fragments */
    private FragmentManager fragmentManager;
    // private int stackCount; /* This decides whether TabLayout is shown or not. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Used when fragment transaction is needed
         * Moreover, database needs its context.
         * Assumption: there is only one MainActivity class : should be wrong.*/
        if (mInstance != null) {
            // 2016.01.09 This part called when the screen mode is changed
            // throw new RuntimeException("Main Activity onCreate called twice!");
        }
        mInstance = this;
        fragmentManager = getSupportFragmentManager();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.maintablayout);
        pagerFragment = new PagerFragment();
        fragmentStack.add(pagerFragment);

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.maincontent, pagerFragment, "PAGER");
        ft.commit();

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.maincontent);
//                Log.d("FragmentCallback", "current fragment "+currentFragment);
//                String s = "fragmentstack size "+fragmentStack.size();
//                for (Fragment f: fragmentStack) {
//                    s = s+ " "+f;
//                }
//                Log.d("FragmentCallback", s);

                if (fragmentStack.get(fragmentStack.size() - 1) == currentFragment) {
                    /* New fragment was created */
                }
                else {
                    /* Fragment */
                    assert fragmentStack.get(fragmentStack.size() - 2) == currentFragment;
                    fragmentStack.remove(fragmentStack.size() - 1);

                    /* Callback function */
                    if (currentFragment instanceof Updatable) {
                        ((Updatable) currentFragment).reactivated();
                    }
                }
            }
        });

        ActiveAndroid.initialize(this);
        // dummyDataSetup();
        newDummySetup();
    }

    /*
     * After Initialize the fragment, call this function with initialized fragment.
     * This could be called by MainActivity.getInstance().startFragment()
     * It removes the Tabs, hide previous fragment in R.id.maincontent in activitymain layout
     *   and add the given new fragment to the R.id.maincontent in activitymain.
     * Backbutton makes the transition reversed. (remove child fragment, and reveal the given fragment)
     * This revealing has callback on onHide(boolean b=false).
     */
    public void startFragment(Fragment fragment) {
        /* To handle responsible fragment. */
        fragmentStack.add(fragment);

        FragmentTransaction t = fragmentManager.beginTransaction();
        t.hide(getSupportFragmentManager().findFragmentById(R.id.maincontent));
        t.add(R.id.maincontent, fragment);
        t.addToBackStack(null);
        t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        t.commit();
    }

    /* The fragments should call this function
     *    when the data was changed   */
    public void notifyChangedToFragments() {
        for (Fragment f:fragmentStack) {
            if (f instanceof Updatable)
                ((Updatable) f).notifyChanged();
        }
    }
    //public void launchNewFragment ()
    // Menu is for debugging purpose
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menumain, menu);
        return true;
    }

    // Menu is for debugging purpose
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.fragmenttest:
                supportInvalidateOptionsMenu();
                WriteEventFragment.activate(null);
                break;
        }
        return true;
    }

    public static MainActivity getInstance() {
        return mInstance;
    }
    public TabLayout getTabLayout() { return tabLayout; }

    private void dummyDataSetup() {
        new Delete().from(Appearance.class).execute();
        new Delete().from(Description.class).execute();
        new Delete().from(Event.class).execute();
        new Delete().from(Tag.class).execute();
        new Delete().from(Friend.class).execute();

        int i, j;
        Event e;
        Friend f;
        Tag t;

        for (i=0; i<50; i++) {
            e = new Event("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", Date.valueOf("2016-01-07"));
            e.save();
            if (i%5 == 0) {
                for (j=0; j < i / 5; j++) {
                    f = new Friend(String.format("Friend %d", i/5), "01066796756", "너무 잘생겼고 그리고 랩도 잘하고 너무나도 완벽해");
                    f.save();
                    e.addFriend(f);
                }
                if (i%3 == 2) {
                    for (j=0; j < (i / 3) + 1; j++) {
                        t = new Tag(String.format("Tag %d", i/5));
                        t.save();
                        e.addTag(t);
                    }
                }
            }
            if (i%9 == 0) {
                for (j = 0; j < (i / 9) + 1; j++) {
                    t = new Tag(String.format("Tag %d", i / 5));
                    t.save();
                    e.addTag(t);
                }
            }
        }
    }

    private void newDummySetup() {
        new Delete().from(Appearance.class).execute();
        new Delete().from(Description.class).execute();
        new Delete().from(Event.class).execute();
        new Delete().from(Tag.class).execute();
        new Delete().from(Friend.class).execute();

        List<Event> events = new ArrayList<Event>();
        List<Friend> friends = new ArrayList<Friend>();
        List<Tag> tags = new ArrayList<Tag>();
        Event e;
        Friend f;
        Tag t;
        e = new Event("보이는 구할 커다란 길을 미묘한 두기 되는 청춘이 트고, 소금이라 힘있다.", Date.valueOf("2010-03-27"));
        events.add(e);
        e = new Event("예수는 긴지라 굳세게 수 이상 위하여, 방황하였으며, 시들어 싹이 것이다.", Date.valueOf("2015-04-06"));
        events.add(e);
        e = new Event("그들은 넣는 설산에서 무엇이 이상 돋고, 따뜻한 방지하는 봄바람이다.", Date.valueOf("2011-03-19"));
        events.add(e);
        e = new Event("뛰노는 있으랴? 가지에 심장의 고행을 맺어, 얼음 바이며, 청춘에서만 보라.", Date.valueOf("2013-06-10"));
        events.add(e);
        e = new Event("사랑의 석가는 긴지라 그것을 바로 뛰노는 주는 사람은 넣는 곧 봄바람이다.", Date.valueOf("2013-09-07"));
        events.add(e);
        e = new Event("청춘의 청춘의 하였으며, 위하여, 있으랴? 설레는 크고 그들은 것이다.", Date.valueOf("2010-02-01"));
        events.add(e);
        e = new Event("그들은 바로 얼마나 것이 살았으며, 설레는 수 있으랴? 그들은 것이다.", Date.valueOf("2011-07-07"));
        events.add(e);
        e = new Event("그들은 밝은 사람은 작고 살았으며, 그들은 꽃이 같지 꽃 하는 보라.", Date.valueOf("2012-01-07"));
        events.add(e);
        e = new Event("만물은 영락과 바이며, 크고 열매를 긴지라 소금이라 얼음 영락과 있다.", Date.valueOf("2012-10-01"));
        events.add(e);
        e = new Event("있을 석가는 천하를 부패를 거선의 방지하는 쓸쓸하랴? 밝은 옷을 칼이다.", Date.valueOf("2013-06-06"));
        events.add(e);
        e = new Event("심장의 가지에 소금이라 사막이다.얼마나 꽃이 그들의 청춘에서만 교향악이다.", Date.valueOf("2010-05-08"));
        events.add(e);
        e = new Event("있을 관현악이며, 이것은 이것은 소금이라 굳세게 든 쓸쓸하랴? 이것이다.", Date.valueOf("2015-06-20"));
        events.add(e);
        e = new Event("설산에서 주는 있는 고행을 돋고, 구할 방지하는 그것을 거친 크고 있다.", Date.valueOf("2014-10-20"));
        events.add(e);
        e = new Event("설산에서 온갖 이상의 거선의 미인을 있는 것이 뭇 몸이 스며들어 이것이다.", Date.valueOf("2014-02-01"));
        events.add(e);
        e = new Event("끝까지 인도하겠다는 그들은 유소년에게서 착목한는 바이며, 그들의 이것이다.", Date.valueOf("2011-04-28"));
        events.add(e);
        e = new Event("타오르고 얼마나 석가는 있는 아니더면, 풀이 보이는 인간은 보라.", Date.valueOf("2010-01-08"));
        events.add(e);
        e = new Event("길을 얼마나 구할 주며, 청춘이 미인을 부패를 그것을 곧 소금이라 뿐이다.", Date.valueOf("2013-03-27"));
        events.add(e);
        e = new Event("이상을 따뜻한 위하여, 그들은 싹이 그러므로 미묘한 사막이다.얼마나 것이다.", Date.valueOf("2010-09-04"));
        events.add(e);
        e = new Event("작고 그들의 있는 스며들어 거선의 고동을 가슴에 심장은 것이 뭇 부패뿐이다.", Date.valueOf("2015-04-25"));
        events.add(e);
        e = new Event("크고 보이는 심장은 위하여서, 사랑의 튼튼하며, 착목한는 내는 봄바람이다.", Date.valueOf("2015-10-02"));
        events.add(e);
        e = new Event("우리 있으랴? 돋고, 인간은 우리 크고 인도하겠다는 주는 튼튼하며, 칼이다.", Date.valueOf("2011-06-21"));
        events.add(e);
        e = new Event("간에 사랑의 위하여, 인간은 하였으며, 구할 열매를 있을 착목한는 이것이다.", Date.valueOf("2010-04-11"));
        events.add(e);
        e = new Event("인간이 그들은 얼마나 새가 소금이라 쓸쓸하랴? 얼마나 크고 사랑의 것이다.", Date.valueOf("2013-07-18"));
        events.add(e);
        e = new Event("넣는 간에 심장은 그들은 그들의 바이며, 이상, 용감하고 얼마나 황금시대다.", Date.valueOf("2012-03-05"));
        events.add(e);
        e = new Event("돋고, 이상의 긴지라 살았으며, 그러므로 주는 있는 같으며, 싸인 힘있다.", Date.valueOf("2010-10-05"));
        events.add(e);

        t = new Tag("비교");
        tags.add(t);
        t = new Tag("한자");
        tags.add(t);
        t = new Tag("종이컵");
        tags.add(t);
        t = new Tag("제자리");
        tags.add(t);
        t = new Tag("간판");
        tags.add(t);
        t = new Tag("형성");
        tags.add(t);
        t = new Tag("이상적");
        tags.add(t);
        t = new Tag("대형");
        tags.add(t);
        t = new Tag("종류");
        tags.add(t);
        t = new Tag("문학");
        tags.add(t);
        t = new Tag("귀");
        tags.add(t);
        t = new Tag("소문");
        tags.add(t);
        t = new Tag("관찰");
        tags.add(t);
        t = new Tag("지적");
        tags.add(t);
        t = new Tag("바보");
        tags.add(t);
        t = new Tag("외제");
        tags.add(t);
        t = new Tag("축제");
        tags.add(t);
        t = new Tag("주거");
        tags.add(t);
        t = new Tag("테러");
        tags.add(t);
        t = new Tag("올해");
        tags.add(t);
        t = new Tag("운전기사");
        tags.add(t);
        t = new Tag("물고기");
        tags.add(t);
        t = new Tag("탁자");
        tags.add(t);
        t = new Tag("선거");
        tags.add(t);
        t = new Tag("텍스트");
        tags.add(t);
        t = new Tag("블라우스");
        tags.add(t);
        t = new Tag("속");
        tags.add(t);
        t = new Tag("박물관");
        tags.add(t);
        t = new Tag("가을");
        tags.add(t);
        t = new Tag("논리적");
        tags.add(t);
        t = new Tag("최고급");
        tags.add(t);
        t = new Tag("구석구석");
        tags.add(t);
        t = new Tag("이해");
        tags.add(t);
        t = new Tag("왕자");
        tags.add(t);
        t = new Tag("중요");
        tags.add(t);
        t = new Tag("땅콩");
        tags.add(t);
        t = new Tag("전체");
        tags.add(t);
        t = new Tag("임금");
        tags.add(t);
        t = new Tag("타락");
        tags.add(t);
        t = new Tag("감기");
        tags.add(t);
        t = new Tag("후보");
        tags.add(t);
        t = new Tag("발");
        tags.add(t);
        t = new Tag("학생증");
        tags.add(t);
        t = new Tag("검사");
        tags.add(t);
        t = new Tag("신입생");
        tags.add(t);
        t = new Tag("카레");
        tags.add(t);
        t = new Tag("농구");
        tags.add(t);
        t = new Tag("특성");
        tags.add(t);
        t = new Tag("강변");
        tags.add(t);
        t = new Tag("책임감");
        tags.add(t);
        t = new Tag("이하");
        tags.add(t);
        t = new Tag("형");
        tags.add(t);
        t = new Tag("감상");
        tags.add(t);
        t = new Tag("조");
        tags.add(t);
        t = new Tag("컨디션");
        tags.add(t);
        t = new Tag("행운");
        tags.add(t);
        t = new Tag("인원");
        tags.add(t);
        t = new Tag("주스");
        tags.add(t);
        t = new Tag("하숙집");
        tags.add(t);
        t = new Tag("최후");
        tags.add(t);

        f = new Friend("정영환", "01006201870", "인간은 예수는 쓸쓸하랴? 부패뿐이다.");
        friends.add(f);
        f = new Friend("이석지", "01080848107", "얼마나 가치를 위하여서, 열락의 보라.");
        friends.add(f);
        f = new Friend("정영종", "01058220034", "같지 시들어 풀이 위하여, 넣는 보라.");
        friends.add(f);
        f = new Friend("김우민", "01090046527", "청춘의 사랑의 주는 위하여서 교향악이다.");
        friends.add(f);
        f = new Friend("김석종", "01022660129", "따뜻한 인생을 보이는 교향악이다.");
        friends.add(f);
        f = new Friend("박석민", "01047521598", "노래하며 얼마나 같으며, 그들의 것이다.");
        friends.add(f);
        f = new Friend("이석종", "01090291366", "트고, 불어 무엇을 피어나는 말이다.");
        friends.add(f);
        f = new Friend("박고훈", "01075856544", "구할 구하지 심장은 되는 간에 뿐이다.");
        friends.add(f);
        f = new Friend("안환지", "01046114451", "사라지지 것이다.보라, 황금시대다.");
        friends.add(f);
        f = new Friend("안고훈", "01054868385", "못할 얼마나 것이 그들의 이것이다.");
        friends.add(f);
        f = new Friend("안영훈", "01033381344", "살았으며, 작고 커다란 이상을 아니다.");
        friends.add(f);
        f = new Friend("김승민", "01024459565", "청춘을 있는 타오르고 위하여, 약동하다.");
        friends.add(f);
        f = new Friend("이환종", "01022251132", "청춘에서만 것이다.보라, 끓는다.");
        friends.add(f);
        f = new Friend("김고훈", "01000495600", "크고 청춘 우리 뭇 쓸쓸하랴? 보라.");
        friends.add(f);
        f = new Friend("정고지", "01021101144", "인간은 새가 커다란 청춘 뿐이다.");
        friends.add(f);
        f = new Friend("안환훈", "01035082497", "주며, 있는 불어 인생을 얼마나 보라.");
        friends.add(f);
        f = new Friend("이고종", "01050326322", "착목한는 그들의 그들의 너의 칼이다.");
        friends.add(f);
        f = new Friend("정환환", "01026237882", "어디 그들의 방황하였으며, 칼이다.");
        friends.add(f);
        f = new Friend("박고환", "01051576454", "밝은 심장은 심장의 하는 이것이다.");
        friends.add(f);
        f = new Friend("정석민", "01082037714", "싸인 청춘에서만 청춘에서만 약동하다.");
        friends.add(f);
        f = new Friend("안석환", "01048734118", "얼마나 인생을 하였으며, 만물은 것이다.");
        friends.add(f);
        f = new Friend("김환훈", "01034003108", "인간이 못할 용감하고 천하를 말이다.");
        friends.add(f);
        f = new Friend("김고훈", "01050910568", "그들에게 설레는 그들은 주며, 뿐이다.");
        friends.add(f);
        f = new Friend("이환훈", "01010134493", "넣는 돋고, 하는 수 얼마나 것이다.");
        friends.add(f);
        f = new Friend("김고지", "01077499004", "튼튼하며, 그들은 무엇을 것이다.");
        friends.add(f);
        f = new Friend("안환민", "01023210722", "사라지지 있는 심장은 그것은 하는 보라.");
        friends.add(f);
        f = new Friend("정우웅", "01035890352", "방지하는 위하여, 두기 주는 약동하다.");
        friends.add(f);
        f = new Friend("이우민", "01090607694", "그들의 미묘한 뭇 만물은 부패뿐이다.");
        friends.add(f);
        f = new Friend("정환환", "01047427774", "그들에게 커다란 시들어 부패뿐이다.");
        friends.add(f);
        f = new Friend("박고훈", "01058038805", "하는 석가는 피부가 살았으며, 약동하다.");
        friends.add(f);


        for (Event ee: events) {
            ee.save();
        }
        for (Tag tt: tags) {
            tt.save();
        }
        for (Friend ff: friends) {
            ff.save();
        }
        events.get(9).addFriend(friends.get(27));
        events.get(13).addFriend(friends.get(18));
        events.get(14).addFriend(friends.get(19));
        events.get(8).addFriend(friends.get(27));
        events.get(6).addFriend(friends.get(17));
        events.get(21).addFriend(friends.get(20));
        events.get(7).addFriend(friends.get(28));
        events.get(11).addFriend(friends.get(23));
        events.get(16).addFriend(friends.get(12));
        events.get(5).addFriend(friends.get(5));
        events.get(6).addFriend(friends.get(20));
        events.get(10).addFriend(friends.get(18));
        events.get(2).addFriend(friends.get(10));
        events.get(17).addFriend(friends.get(20));
        events.get(18).addFriend(friends.get(6));
        events.get(7).addFriend(friends.get(6));
        events.get(1).addFriend(friends.get(2));
        events.get(18).addFriend(friends.get(10));
        events.get(14).addFriend(friends.get(5));
        events.get(16).addFriend(friends.get(2));
        events.get(19).addFriend(friends.get(8));
        events.get(16).addFriend(friends.get(25));
        events.get(18).addFriend(friends.get(4));
        events.get(19).addFriend(friends.get(29));
        events.get(20).addFriend(friends.get(7));
        events.get(18).addFriend(friends.get(20));
        events.get(10).addFriend(friends.get(21));
        events.get(8).addFriend(friends.get(7));
        events.get(2).addFriend(friends.get(1));
        events.get(18).addFriend(friends.get(26));
        events.get(5).addFriend(friends.get(21));
        events.get(5).addFriend(friends.get(19));
        events.get(21).addFriend(friends.get(19));
        events.get(19).addFriend(friends.get(9));
        events.get(14).addFriend(friends.get(25));
        events.get(2).addFriend(friends.get(2));
        events.get(24).addFriend(friends.get(27));
        events.get(8).addFriend(friends.get(13));
        events.get(14).addFriend(friends.get(13));
        events.get(6).addFriend(friends.get(4));
        events.get(8).addFriend(friends.get(14));
        events.get(24).addFriend(friends.get(0));
        events.get(9).addFriend(friends.get(2));
        events.get(22).addFriend(friends.get(10));
        events.get(10).addFriend(friends.get(12));
        events.get(15).addFriend(friends.get(14));
        events.get(9).addFriend(friends.get(24));
        events.get(11).addFriend(friends.get(18));
        events.get(20).addFriend(friends.get(16));
        events.get(18).addFriend(friends.get(21));
        events.get(3).addFriend(friends.get(8));
        events.get(19).addFriend(friends.get(21));
        events.get(3).addFriend(friends.get(9));
        events.get(3).addFriend(friends.get(27));
        events.get(8).addFriend(friends.get(22));
        events.get(18).addFriend(friends.get(24));
        events.get(6).addFriend(friends.get(23));
        events.get(18).addFriend(friends.get(8));
        events.get(22).addFriend(friends.get(28));
        events.get(19).addFriend(friends.get(11));
        events.get(5).addFriend(friends.get(22));
        events.get(9).addFriend(friends.get(25));
        events.get(13).addFriend(friends.get(10));
        events.get(23).addFriend(friends.get(27));
        events.get(12).addFriend(friends.get(28));
        events.get(10).addFriend(friends.get(20));
        events.get(14).addFriend(friends.get(23));
        events.get(6).addFriend(friends.get(24));
        events.get(23).addFriend(friends.get(11));
        events.get(7).addFriend(friends.get(2));
        events.get(11).addFriend(friends.get(4));
        events.get(0).addFriend(friends.get(3));
        events.get(13).addFriend(friends.get(13));
        events.get(10).addFriend(friends.get(8));
        events.get(15).addFriend(friends.get(13));
        events.get(0).addFriend(friends.get(19));
        events.get(24).addFriend(friends.get(26));
        events.get(11).addFriend(friends.get(6));
        events.get(10).addFriend(friends.get(2));
        events.get(4).addFriend(friends.get(7));
        events.get(9).addFriend(friends.get(28));
        events.get(13).addFriend(friends.get(16));
        events.get(15).addFriend(friends.get(0));
        events.get(23).addFriend(friends.get(23));
        events.get(2).addFriend(friends.get(22));
        events.get(3).addFriend(friends.get(25));
        events.get(19).addFriend(friends.get(16));
        events.get(3).addFriend(friends.get(10));
        events.get(11).addFriend(friends.get(7));
        events.get(2).addFriend(friends.get(20));
        events.get(8).addFriend(friends.get(23));
        events.get(12).addFriend(friends.get(17));
        events.get(16).addFriend(friends.get(11));
        events.get(17).addFriend(friends.get(22));
        events.get(0).addFriend(friends.get(27));
        events.get(5).addFriend(friends.get(28));
        events.get(23).addFriend(friends.get(17));
        events.get(13).addFriend(friends.get(20));
        events.get(24).addFriend(friends.get(29));
        events.get(21).addFriend(friends.get(26));
        events.get(24).addTag(tags.get(40));
        events.get(18).addTag(tags.get(9));
        events.get(24).addTag(tags.get(29));
        events.get(3).addTag(tags.get(16));
        events.get(14).addTag(tags.get(46));
        events.get(9).addTag(tags.get(6));
        events.get(8).addTag(tags.get(58));
        events.get(24).addTag(tags.get(16));
        events.get(13).addTag(tags.get(1));
        events.get(1).addTag(tags.get(43));
        events.get(4).addTag(tags.get(11));
        events.get(23).addTag(tags.get(20));
        events.get(5).addTag(tags.get(17));
        events.get(16).addTag(tags.get(55));
        events.get(4).addTag(tags.get(5));
        events.get(5).addTag(tags.get(11));
        events.get(21).addTag(tags.get(54));
        events.get(4).addTag(tags.get(50));
        events.get(15).addTag(tags.get(18));
        events.get(12).addTag(tags.get(12));
        events.get(14).addTag(tags.get(49));
        events.get(21).addTag(tags.get(37));
        events.get(3).addTag(tags.get(6));
        events.get(4).addTag(tags.get(53));
        events.get(16).addTag(tags.get(51));
        events.get(18).addTag(tags.get(38));
        events.get(14).addTag(tags.get(4));
        events.get(10).addTag(tags.get(14));
        events.get(5).addTag(tags.get(25));
        events.get(20).addTag(tags.get(48));
        events.get(16).addTag(tags.get(12));
        events.get(8).addTag(tags.get(32));
        events.get(19).addTag(tags.get(54));
        events.get(2).addTag(tags.get(43));
        events.get(9).addTag(tags.get(58));
        events.get(17).addTag(tags.get(45));
        events.get(5).addTag(tags.get(57));
        events.get(23).addTag(tags.get(33));
        events.get(2).addTag(tags.get(16));
        events.get(19).addTag(tags.get(31));
        events.get(14).addTag(tags.get(32));
        events.get(22).addTag(tags.get(51));
        events.get(0).addTag(tags.get(13));
        events.get(20).addTag(tags.get(16));
        events.get(1).addTag(tags.get(19));
        events.get(2).addTag(tags.get(58));
        events.get(12).addTag(tags.get(35));
        events.get(22).addTag(tags.get(14));
        events.get(9).addTag(tags.get(51));
        events.get(12).addTag(tags.get(29));
        events.get(6).addTag(tags.get(18));
        events.get(11).addTag(tags.get(13));
        events.get(6).addTag(tags.get(15));
        events.get(20).addTag(tags.get(46));
        events.get(24).addTag(tags.get(33));
        events.get(18).addTag(tags.get(59));
        events.get(5).addTag(tags.get(35));
        events.get(7).addTag(tags.get(31));
        events.get(16).addTag(tags.get(18));
        events.get(6).addTag(tags.get(32));
        events.get(4).addTag(tags.get(37));
        events.get(13).addTag(tags.get(43));
        events.get(1).addTag(tags.get(45));
        events.get(16).addTag(tags.get(11));
        events.get(22).addTag(tags.get(32));
        events.get(9).addTag(tags.get(25));
        events.get(11).addTag(tags.get(23));
        events.get(9).addTag(tags.get(57));
        events.get(11).addTag(tags.get(7));
        events.get(2).addTag(tags.get(53));
        events.get(15).addTag(tags.get(0));
        events.get(5).addTag(tags.get(9));
        events.get(17).addTag(tags.get(27));
        events.get(16).addTag(tags.get(22));
        events.get(11).addTag(tags.get(44));
        events.get(17).addTag(tags.get(11));
        events.get(24).addTag(tags.get(23));
        events.get(19).addTag(tags.get(7));
        events.get(6).addTag(tags.get(42));
        events.get(21).addTag(tags.get(46));
        events.get(18).addTag(tags.get(13));
        events.get(5).addTag(tags.get(59));
        events.get(2).addTag(tags.get(2));
        events.get(8).addTag(tags.get(31));
        events.get(8).addTag(tags.get(41));
        events.get(7).addTag(tags.get(6));
        events.get(4).addTag(tags.get(42));
        events.get(2).addTag(tags.get(31));
        events.get(9).addTag(tags.get(2));
        events.get(1).addTag(tags.get(1));
        events.get(8).addTag(tags.get(35));
        events.get(0).addTag(tags.get(9));
        events.get(6).addTag(tags.get(40));
        events.get(5).addTag(tags.get(45));
        events.get(0).addTag(tags.get(28));
        events.get(23).addTag(tags.get(40));
        events.get(8).addTag(tags.get(47));
        events.get(11).addTag(tags.get(22));
        events.get(9).addTag(tags.get(36));
        events.get(18).addTag(tags.get(39));
        events.get(21).addTag(tags.get(15));
        events.get(19).addTag(tags.get(0));
        events.get(24).addTag(tags.get(34));
        events.get(4).addTag(tags.get(49));
        events.get(7).addTag(tags.get(40));
        events.get(0).addTag(tags.get(41));
        events.get(22).addTag(tags.get(24));
        events.get(17).addTag(tags.get(19));
        events.get(7).addTag(tags.get(26));
        events.get(24).addTag(tags.get(54));
        events.get(12).addTag(tags.get(10));
        events.get(11).addTag(tags.get(57));
        events.get(7).addTag(tags.get(28));
        events.get(22).addTag(tags.get(38));
        events.get(2).addTag(tags.get(12));
        events.get(16).addTag(tags.get(13));
        events.get(14).addTag(tags.get(22));
        events.get(6).addTag(tags.get(19));
        events.get(21).addTag(tags.get(10));
        events.get(11).addTag(tags.get(53));
        events.get(9).addTag(tags.get(33));
        events.get(24).addTag(tags.get(9));
        events.get(3).addTag(tags.get(7));
        events.get(19).addTag(tags.get(32));
        events.get(24).addTag(tags.get(18));
        events.get(17).addTag(tags.get(2));
        events.get(4).addTag(tags.get(20));
        events.get(9).addTag(tags.get(45));
        events.get(4).addTag(tags.get(15));
        events.get(22).addTag(tags.get(15));
        events.get(0).addTag(tags.get(0));
        events.get(11).addTag(tags.get(21));
        events.get(13).addTag(tags.get(27));
        events.get(1).addTag(tags.get(16));
        events.get(12).addTag(tags.get(59));
        events.get(21).addTag(tags.get(6));
        events.get(2).addTag(tags.get(11));
        events.get(12).addTag(tags.get(42));
        events.get(9).addTag(tags.get(27));
        events.get(8).addTag(tags.get(3));
        events.get(20).addTag(tags.get(34));
        events.get(24).addTag(tags.get(32));
        events.get(21).addTag(tags.get(33));
        events.get(12).addTag(tags.get(15));
        events.get(22).addTag(tags.get(58));
        events.get(0).addTag(tags.get(10));
        events.get(15).addTag(tags.get(36));
        events.get(14).addTag(tags.get(47));
        events.get(14).addTag(tags.get(25));
        events.get(8).addTag(tags.get(18));
        events.get(15).addTag(tags.get(15));
        events.get(20).addTag(tags.get(53));
        events.get(16).addTag(tags.get(50));
        events.get(20).addTag(tags.get(18));
        events.get(10).addTag(tags.get(26));
        events.get(14).addTag(tags.get(6));
        events.get(16).addTag(tags.get(54));
        events.get(10).addTag(tags.get(54));
        events.get(8).addTag(tags.get(8));
        events.get(0).addTag(tags.get(21));
        events.get(19).addTag(tags.get(57));
        events.get(1).addTag(tags.get(22));
        events.get(8).addTag(tags.get(59));
        events.get(17).addTag(tags.get(14));
        events.get(2).addTag(tags.get(49));
        events.get(4).addTag(tags.get(51));
        events.get(14).addTag(tags.get(23));
        events.get(8).addTag(tags.get(28));
        events.get(11).addTag(tags.get(12));
        events.get(20).addTag(tags.get(33));
        events.get(5).addTag(tags.get(32));
        events.get(14).addTag(tags.get(45));
        events.get(21).addTag(tags.get(39));
        events.get(12).addTag(tags.get(11));
        events.get(2).addTag(tags.get(13));
        events.get(5).addTag(tags.get(38));
        events.get(9).addTag(tags.get(55));
        events.get(23).addTag(tags.get(48));
        events.get(13).addTag(tags.get(36));
        events.get(2).addTag(tags.get(50));
        events.get(15).addTag(tags.get(38));
        events.get(4).addTag(tags.get(0));
        events.get(17).addTag(tags.get(50));
        events.get(20).addTag(tags.get(41));
        events.get(22).addTag(tags.get(27));
        events.get(12).addTag(tags.get(18));
        events.get(24).addTag(tags.get(4));
        events.get(11).addTag(tags.get(54));
        events.get(10).addTag(tags.get(24));
        events.get(20).addTag(tags.get(7));
        events.get(20).addTag(tags.get(26));
        events.get(23).addTag(tags.get(57));
        events.get(4).addTag(tags.get(18));
        events.get(10).addTag(tags.get(32));
        events.get(4).addTag(tags.get(41));
        events.get(1).addTag(tags.get(0));
        events.get(15).addTag(tags.get(41));
        events.get(1).addTag(tags.get(28));
        events.get(12).addTag(tags.get(50));
        events.get(17).addTag(tags.get(0));
        events.get(0).addTag(tags.get(31));
        events.get(9).addTag(tags.get(8));
        events.get(8).addTag(tags.get(7));
        events.get(21).addTag(tags.get(13));
        events.get(15).addTag(tags.get(42));
        events.get(10).addTag(tags.get(27));
        events.get(9).addTag(tags.get(37));
        events.get(5).addTag(tags.get(47));
        events.get(7).addTag(tags.get(30));
        events.get(18).addTag(tags.get(51));
        events.get(9).addTag(tags.get(49));
        events.get(23).addTag(tags.get(19));
        events.get(11).addTag(tags.get(19));
        events.get(9).addTag(tags.get(15));
        events.get(0).addTag(tags.get(58));
        events.get(15).addTag(tags.get(22));
        events.get(22).addTag(tags.get(13));
        events.get(22).addTag(tags.get(1));
        events.get(8).addTag(tags.get(13));
        events.get(10).addTag(tags.get(36));
        events.get(13).addTag(tags.get(47));
        events.get(5).addTag(tags.get(43));
        events.get(21).addTag(tags.get(59));
        events.get(10).addTag(tags.get(23));
        events.get(3).addTag(tags.get(34));
        events.get(9).addTag(tags.get(12));
        events.get(21).addTag(tags.get(48));
        events.get(5).addTag(tags.get(26));
        events.get(6).addTag(tags.get(13));
        events.get(21).addTag(tags.get(25));
        events.get(8).addTag(tags.get(38));
        events.get(21).addTag(tags.get(56));
        events.get(7).addTag(tags.get(50));
        events.get(11).addTag(tags.get(30));
        events.get(9).addTag(tags.get(44));
        events.get(8).addTag(tags.get(4));
        events.get(17).addTag(tags.get(25));
        events.get(12).addTag(tags.get(26));
        events.get(8).addTag(tags.get(52));
        events.get(9).addTag(tags.get(54));
        events.get(9).addTag(tags.get(10));
        events.get(17).addTag(tags.get(3));
        events.get(3).addTag(tags.get(20));
        events.get(3).addTag(tags.get(24));
        events.get(9).addTag(tags.get(14));
        events.get(11).addTag(tags.get(47));
        events.get(11).addTag(tags.get(27));
        events.get(23).addTag(tags.get(2));
        events.get(19).addTag(tags.get(44));
        events.get(7).addTag(tags.get(3));
    }
}
