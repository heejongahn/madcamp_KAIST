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
import com.example.nobell.project3.ui.WriteEventFragment;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private PagerFragment pagerFragment;
    private TabLayout tabLayout;

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

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.maincontent, pagerFragment, "PAGER");
        ft.commit();

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
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.hide(getSupportFragmentManager().findFragmentById(R.id.maincontent));
        t.add(R.id.maincontent, fragment);
        t.addToBackStack(null);
        t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        t.commit();
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
        e = new Event("구하기 되는 발휘하기 위하여서, 미인을 하였으며, 온갖 것이 그들은 보라.", Date.valueOf("2011-07-09"));
        e.save();
        events.add(e);
        e = new Event("크고 위하여, 두기 꽃 심장은 긴지라 위하여서 소금이라 있는 든 봄바람이다.", Date.valueOf("2010-08-21"));
        e.save();
        events.add(e);
        e = new Event("보이는 밝은 거친 그들은 눈이 작고 크고 밝은 청춘 온갖 열매를 황금시대다.", Date.valueOf("2014-03-16"));
        e.save();
        events.add(e);
        e = new Event("거친 따뜻한 방지하는 관현악이며, 가진 커다란 심장은 위하여, 너의 칼이다.", Date.valueOf("2013-05-14"));
        e.save();
        events.add(e);
        e = new Event("심장은 살았으며, 고동을 살았으며, 얼마나 새가 그들은 살았으며, 것이다.", Date.valueOf("2014-09-16"));
        e.save();
        events.add(e);
        e = new Event("돋고, 방지하는 구하지 풀이 청춘 맺어, 이것은 바로 석가는 바로 끓는다.", Date.valueOf("2012-11-02"));
        e.save();
        events.add(e);
        e = new Event("크고 노래하며 뭇 있는 우리 것은 천하를 굳세게 발휘하기 청춘 봄바람이다.", Date.valueOf("2015-10-12"));
        e.save();
        events.add(e);
        e = new Event("그들에게 커다란 몸이 이상 얼마나 그들은 이것은 내는 용감하고 아니다.", Date.valueOf("2011-06-10"));
        e.save();
        events.add(e);
        e = new Event("풀이 바이며, 미묘한 그들은 심장의 따뜻한 고행을 청춘의 천하를 것이다.", Date.valueOf("2011-09-27"));
        e.save();
        events.add(e);
        e = new Event("발휘하기 간에 바로 청춘을 미인을 석가는 영락과 주는 인간은 부패뿐이다.", Date.valueOf("2015-12-15"));
        e.save();
        events.add(e);
        e = new Event("바이며, 이상, 그들의 어디 위하여, 석가는 그들은 인간은 교향악이다.", Date.valueOf("2014-04-21"));
        e.save();
        events.add(e);
        e = new Event("얼마나 위하여, 하는 싸인 거친 것이 같지 착목한는 있는가? 것이다.", Date.valueOf("2014-12-22"));
        e.save();
        events.add(e);
        e = new Event("부패를 청춘을 있는 뛰노는 찾아다녀도, 불어 사라지지 것이 얼마나 것이다.", Date.valueOf("2012-02-28"));
        e.save();
        events.add(e);
        e = new Event("품고 얼음 그들의 천고에 내는 얼마나 청춘 품고 청춘을 간에 청춘 말이다.", Date.valueOf("2015-10-27"));
        e.save();
        events.add(e);
        e = new Event("수 이상 이것은 거친 뭇 인도하겠다는 가진 심장은 미묘한 살았으며, 것이다.", Date.valueOf("2012-08-27"));
        e.save();
        events.add(e);
        e = new Event("미묘한 뛰노는 사람은 그들의 하였으며, 심장은 몸이 가치를 돋고, 약동하다.", Date.valueOf("2013-05-04"));
        e.save();
        events.add(e);
        e = new Event("그리하였는가? 위하여서, 불어 청춘에서만 피어나는 불어 인간은 없는 힘있다.", Date.valueOf("2014-09-29"));
        e.save();
        events.add(e);
        e = new Event("얼음 가장 무엇이 소금이라 타오르고 구하기 그들의 그들의 돋고, 것이다.", Date.valueOf("2014-05-25"));
        e.save();
        events.add(e);
        e = new Event("불어 있으랴? 꽃이 인간은 눈이 예수는 하는 용감하고 고동을 봄바람이다.", Date.valueOf("2012-10-21"));
        e.save();
        events.add(e);
        e = new Event("석가는 청춘에서만 싸인 같지 그리하였는가? 것이 거친 열락의 약동하다.", Date.valueOf("2015-07-03"));
        e.save();
        events.add(e);
        e = new Event("착목한는 위하여 석가는 인간은 것은 용감하고 청춘을 고동을 하는 힘있다.", Date.valueOf("2013-09-22"));
        e.save();
        events.add(e);
        e = new Event("몸이 사랑의 청춘의 맺어, 곧 열락의 그들의 튼튼하며, 쓸쓸하랴? 뿐이다.", Date.valueOf("2011-11-28"));
        e.save();
        events.add(e);
        e = new Event("사막이다.얼마나 꽃이 청춘을 같지 커다란 유소년에게서 구할 봄바람이다.", Date.valueOf("2013-03-04"));
        e.save();
        events.add(e);
        e = new Event("구하지 관현악이며, 그들에게 모래뿐일 인간은 있는가? 그들은 것이다.", Date.valueOf("2014-03-19"));
        e.save();
        events.add(e);
        e = new Event("열매를 인간은 따뜻한 하였으며, 얼마나 인간이 스며들어 타오르고 것이다.", Date.valueOf("2011-05-09"));
        e.save();
        events.add(e);

        t = new Tag("관대내");
        t.save();
        tags.add(t);
        t = new Tag("없바넣기현");
        t.save();
        tags.add(t);
        t = new Tag("긴다꽃");
        t.save();
        tags.add(t);
        t = new Tag("감없향");
        t.save();
        tags.add(t);
        t = new Tag("말았디커");
        t.save();
        tags.add(t);
        t = new Tag("열레년어");
        t.save();
        tags.add(t);
        t = new Tag("므한의면아");
        t.save();
        tags.add(t);
        t = new Tag("일자방뿐영");
        t.save();
        tags.add(t);
        t = new Tag("말할방");
        t.save();
        tags.add(t);
        t = new Tag("며물므춘되");
        t.save();
        tags.add(t);
        t = new Tag("러너트");
        t.save();
        tags.add(t);
        t = new Tag("이힘년");
        t.save();
        tags.add(t);
        t = new Tag("소작돋");
        t.save();
        tags.add(t);
        t = new Tag("목끓불관");
        t.save();
        tags.add(t);
        t = new Tag("림찾착");
        t.save();
        tags.add(t);
        t = new Tag("가곧곧같");
        t.save();
        tags.add(t);
        t = new Tag("를심작까이");
        t.save();
        tags.add(t);
        t = new Tag("레꽃생든");
        t.save();
        tags.add(t);
        t = new Tag("았없가자");
        t.save();
        tags.add(t);
        t = new Tag("길위였예");
        t.save();
        tags.add(t);
        t = new Tag("러청소게");
        t.save();
        tags.add(t);
        t = new Tag("설위은상피");
        t.save();
        tags.add(t);
        t = new Tag("리힘선꽃");
        t.save();
        tags.add(t);
        t = new Tag("구든가싸");
        t.save();
        tags.add(t);
        t = new Tag("나불인온");
        t.save();
        tags.add(t);
        t = new Tag("겠생진");
        t.save();
        tags.add(t);
        t = new Tag("들에긴따쓸");
        t.save();
        tags.add(t);
        t = new Tag("노은금겠");
        t.save();
        tags.add(t);
        t = new Tag("으은리발돋");
        t.save();
        tags.add(t);
        t = new Tag("였천세모있");
        t.save();
        tags.add(t);
        t = new Tag("라며너슴슴");
        t.save();
        tags.add(t);
        t = new Tag(" 게커청과");
        t.save();
        tags.add(t);
        t = new Tag("심디방석");
        t.save();
        tags.add(t);
        t = new Tag("선구할진");
        t.save();
        tags.add(t);
        t = new Tag("면세며고대");
        t.save();
        tags.add(t);
        t = new Tag("미하내");
        t.save();
        tags.add(t);
        t = new Tag("란면랑");
        t.save();
        tags.add(t);
        t = new Tag("옷놀란시");
        t.save();
        tags.add(t);
        t = new Tag("방선랴");
        t.save();
        tags.add(t);
        t = new Tag("얼리다");
        t.save();
        tags.add(t);

        f = new Friend("정석종", "01056013683", "길을 착목한는 어디 청춘이 작고 아니다.");
        f.save();
        friends.add(f);
        f = new Friend("안승환", "01000975301", "방황하였으며, 얼마나 돋고, 약동하다.");
        f.save();
        friends.add(f);
        f = new Friend("박우지", "01058874092", "구하기 뛰노는 타오르고 싸인 있다.");
        f.save();
        friends.add(f);
        f = new Friend("안석민", "01029065961", "곧 사라지지 그들은 위하여 봄바람이다.");
        f.save();
        friends.add(f);
        f = new Friend("정환훈", "01081628986", "내는 가장 미인을 그들은 봄바람이다.");
        f.save();
        friends.add(f);
        f = new Friend("김승웅", "01081945365", "방황하였으며, 거선의 얼마나 봄바람이다.");
        f.save();
        friends.add(f);
        f = new Friend("이환지", "01039026116", "열락의 것이다.보라, 새가 이것이다.");
        f.save();
        friends.add(f);
        f = new Friend("정우민", "01033775873", "싸인 하는 위하여, 설레는 이것이다.");
        f.save();
        friends.add(f);
        f = new Friend("박영지", "01020373510", "심장은 구하기 것이 심장의 이것이다.");
        f.save();
        friends.add(f);
        f = new Friend("안고지", "01081124024", "그들은 아니더면, 설레는 끓는다.");
        f.save();
        friends.add(f);
        f = new Friend("박고웅", "01055501960", "얼마나 관현악이며, 그들의 칼이다.");
        f.save();
        friends.add(f);
        f = new Friend("정우종", "01024266287", "온갖 위하여서, 인도하겠다는 구할 있다.");
        f.save();
        friends.add(f);
        f = new Friend("박승웅", "01086950486", "청춘 구할 얼마나 온갖 트고, 칼이다.");
        f.save();
        friends.add(f);
        f = new Friend("안석민", "01064555227", "착목한는 그들의 위하여, 그것은 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("김우민", "01066435573", "얼마나 뭇 사랑의 있는 봄바람이다.");
        f.save();
        friends.add(f);
        f = new Friend("이환지", "01059088301", "그것은 품고 미인을 사람은 봄바람이다.");
        f.save();
        friends.add(f);
        f = new Friend("정승웅", "01093167772", "심장은 쓸쓸하랴? 인도하겠다는 있다.");
        f.save();
        friends.add(f);
        f = new Friend("이영지", "01000938708", "미인을 구할 얼마나 이것이다.");
        f.save();
        friends.add(f);
        f = new Friend("김영훈", "01075148806", "가슴에 돋고, 굳세게 얼마나 말이다.");
        f.save();
        friends.add(f);
        f = new Friend("정승종", "01023179493", "가진 착목한는 그림자는 있는 이것이다.");
        f.save();
        friends.add(f);
        f = new Friend("김석훈", "01017927733", "청춘 가치를 청춘 이상 교향악이다.");
        f.save();
        friends.add(f);
        f = new Friend("안영종", "01023228556", "유소년에게서 있으랴? 열락의 이것이다.");
        f.save();
        friends.add(f);
        f = new Friend("김우종", "01054724556", "얼음 얼마나 튼튼하며, 거선의 보라.");
        f.save();
        friends.add(f);
        f = new Friend("김승지", "01010720195", "사라지지 옷을 위하여서, 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("정우지", "01018077054", "어디 그러므로 그들은 그들은 이것이다.");
        f.save();
        friends.add(f);
        f = new Friend("정승종", "01096900991", "불어 위하여, 착목한는 길을 약동하다.");
        f.save();
        friends.add(f);
        f = new Friend("김고웅", "01050394452", "못할 가치를 몸이 가슴에 따뜻한 있다.");
        f.save();
        friends.add(f);
        f = new Friend("정고웅", "01019007523", "소금이라 설산에서 바이며, 힘있다.");
        f.save();
        friends.add(f);
        f = new Friend("정환훈", "01038401209", "고행을 옷을 용감하고 간에 부패뿐이다.");
        f.save();
        friends.add(f);
        f = new Friend("이영훈", "01034604472", "넣는 있으랴? 바로 하는 타오르고 있다.");
        f.save();
        friends.add(f);

        events.get(3).addFriend(friends.get(29));
        events.get(15).addFriend(friends.get(5));
        events.get(23).addFriend(friends.get(2));
        events.get(17).addFriend(friends.get(6));
        events.get(24).addFriend(friends.get(7));
        events.get(7).addFriend(friends.get(4));
        events.get(20).addFriend(friends.get(29));
        events.get(9).addFriend(friends.get(25));
        events.get(0).addFriend(friends.get(13));
        events.get(18).addFriend(friends.get(3));
        events.get(15).addFriend(friends.get(16));
        events.get(1).addFriend(friends.get(9));
        events.get(21).addFriend(friends.get(0));
        events.get(23).addFriend(friends.get(1));
        events.get(23).addFriend(friends.get(1));
        events.get(6).addFriend(friends.get(23));
        events.get(8).addFriend(friends.get(26));
        events.get(11).addFriend(friends.get(29));
        events.get(15).addFriend(friends.get(17));
        events.get(16).addFriend(friends.get(26));
        events.get(13).addFriend(friends.get(15));
        events.get(22).addFriend(friends.get(24));
        events.get(16).addFriend(friends.get(1));
        events.get(12).addFriend(friends.get(23));
        events.get(17).addFriend(friends.get(4));
        events.get(3).addFriend(friends.get(19));
        events.get(8).addFriend(friends.get(18));
        events.get(23).addFriend(friends.get(22));
        events.get(5).addFriend(friends.get(4));
        events.get(9).addFriend(friends.get(19));
        events.get(17).addFriend(friends.get(12));
        events.get(4).addFriend(friends.get(2));
        events.get(23).addFriend(friends.get(13));
        events.get(18).addFriend(friends.get(15));
        events.get(19).addFriend(friends.get(18));
        events.get(17).addFriend(friends.get(23));
        events.get(11).addFriend(friends.get(25));
        events.get(24).addFriend(friends.get(1));
        events.get(24).addFriend(friends.get(2));
        events.get(19).addFriend(friends.get(27));
        events.get(1).addFriend(friends.get(29));
        events.get(19).addFriend(friends.get(7));
        events.get(13).addFriend(friends.get(19));
        events.get(14).addFriend(friends.get(4));
        events.get(16).addFriend(friends.get(22));
        events.get(10).addFriend(friends.get(28));
        events.get(18).addFriend(friends.get(13));
        events.get(1).addFriend(friends.get(4));
        events.get(5).addFriend(friends.get(14));
        events.get(9).addFriend(friends.get(3));
        events.get(9).addFriend(friends.get(2));
        events.get(12).addFriend(friends.get(26));
        events.get(11).addFriend(friends.get(4));
        events.get(24).addFriend(friends.get(15));
        events.get(16).addFriend(friends.get(29));
        events.get(15).addFriend(friends.get(29));
        events.get(15).addFriend(friends.get(24));
        events.get(1).addFriend(friends.get(5));
        events.get(1).addFriend(friends.get(16));
        events.get(5).addFriend(friends.get(14));
        events.get(10).addFriend(friends.get(10));
        events.get(23).addFriend(friends.get(2));
        events.get(21).addFriend(friends.get(9));
        events.get(18).addFriend(friends.get(19));
        events.get(16).addFriend(friends.get(11));
        events.get(11).addFriend(friends.get(21));
        events.get(3).addFriend(friends.get(14));
        events.get(6).addFriend(friends.get(11));
        events.get(24).addFriend(friends.get(21));
        events.get(1).addFriend(friends.get(1));
        events.get(7).addFriend(friends.get(18));
        events.get(14).addFriend(friends.get(25));
        events.get(19).addFriend(friends.get(19));
        events.get(10).addFriend(friends.get(24));
        events.get(15).addFriend(friends.get(29));
        events.get(6).addFriend(friends.get(23));
        events.get(24).addFriend(friends.get(22));
        events.get(21).addFriend(friends.get(25));
        events.get(17).addFriend(friends.get(2));
        events.get(10).addFriend(friends.get(13));
        events.get(9).addFriend(friends.get(25));
        events.get(20).addFriend(friends.get(5));
        events.get(12).addFriend(friends.get(9));
        events.get(5).addFriend(friends.get(21));
        events.get(20).addFriend(friends.get(2));
        events.get(8).addFriend(friends.get(29));
        events.get(1).addFriend(friends.get(9));
        events.get(24).addFriend(friends.get(14));
        events.get(4).addFriend(friends.get(25));
        events.get(18).addFriend(friends.get(22));
        events.get(17).addFriend(friends.get(26));
        events.get(14).addFriend(friends.get(22));
        events.get(5).addFriend(friends.get(9));
        events.get(16).addFriend(friends.get(25));
        events.get(12).addFriend(friends.get(17));
        events.get(15).addFriend(friends.get(8));
        events.get(23).addFriend(friends.get(4));
        events.get(24).addFriend(friends.get(6));
        events.get(10).addFriend(friends.get(10));
        events.get(15).addFriend(friends.get(25));
        events.get(24).addTag(tags.get(11));
        events.get(19).addTag(tags.get(12));
        events.get(20).addTag(tags.get(6));
        events.get(9).addTag(tags.get(37));
        events.get(18).addTag(tags.get(13));
        events.get(18).addTag(tags.get(6));
        events.get(3).addTag(tags.get(3));
        events.get(18).addTag(tags.get(30));
        events.get(1).addTag(tags.get(37));
        events.get(24).addTag(tags.get(29));
        events.get(19).addTag(tags.get(33));
        events.get(23).addTag(tags.get(26));
        events.get(3).addTag(tags.get(9));
        events.get(8).addTag(tags.get(25));
        events.get(3).addTag(tags.get(18));
        events.get(12).addTag(tags.get(6));
        events.get(19).addTag(tags.get(35));
        events.get(24).addTag(tags.get(25));
        events.get(7).addTag(tags.get(22));
        events.get(18).addTag(tags.get(6));
        events.get(21).addTag(tags.get(36));
        events.get(17).addTag(tags.get(31));
        events.get(7).addTag(tags.get(6));
        events.get(21).addTag(tags.get(34));
        events.get(19).addTag(tags.get(39));
        events.get(24).addTag(tags.get(11));
        events.get(21).addTag(tags.get(0));
        events.get(22).addTag(tags.get(38));
        events.get(11).addTag(tags.get(38));
        events.get(15).addTag(tags.get(13));
        events.get(6).addTag(tags.get(5));
        events.get(8).addTag(tags.get(32));
        events.get(7).addTag(tags.get(30));
        events.get(20).addTag(tags.get(32));
        events.get(21).addTag(tags.get(14));
        events.get(7).addTag(tags.get(23));
        events.get(10).addTag(tags.get(9));
        events.get(1).addTag(tags.get(29));
        events.get(6).addTag(tags.get(23));
        events.get(20).addTag(tags.get(34));
        events.get(7).addTag(tags.get(12));
        events.get(21).addTag(tags.get(10));
        events.get(8).addTag(tags.get(39));
        events.get(1).addTag(tags.get(15));
        events.get(11).addTag(tags.get(27));
        events.get(18).addTag(tags.get(8));
        events.get(11).addTag(tags.get(27));
        events.get(2).addTag(tags.get(20));
        events.get(10).addTag(tags.get(29));
        events.get(24).addTag(tags.get(20));
        events.get(7).addTag(tags.get(32));
        events.get(8).addTag(tags.get(1));
        events.get(12).addTag(tags.get(10));
        events.get(14).addTag(tags.get(28));
        events.get(19).addTag(tags.get(8));
        events.get(11).addTag(tags.get(9));
        events.get(13).addTag(tags.get(21));
        events.get(13).addTag(tags.get(2));
        events.get(14).addTag(tags.get(33));
        events.get(10).addTag(tags.get(33));
        events.get(24).addTag(tags.get(12));
        events.get(7).addTag(tags.get(36));
        events.get(23).addTag(tags.get(6));
        events.get(19).addTag(tags.get(3));
        events.get(2).addTag(tags.get(36));
        events.get(12).addTag(tags.get(2));
        events.get(20).addTag(tags.get(5));
        events.get(18).addTag(tags.get(15));
        events.get(18).addTag(tags.get(27));
        events.get(24).addTag(tags.get(6));
        events.get(17).addTag(tags.get(13));
        events.get(3).addTag(tags.get(5));
        events.get(8).addTag(tags.get(4));
        events.get(9).addTag(tags.get(32));
        events.get(21).addTag(tags.get(21));
        events.get(8).addTag(tags.get(1));
        events.get(2).addTag(tags.get(31));
        events.get(11).addTag(tags.get(19));
        events.get(4).addTag(tags.get(32));
        events.get(17).addTag(tags.get(8));
        events.get(18).addTag(tags.get(27));
        events.get(5).addTag(tags.get(34));
        events.get(8).addTag(tags.get(7));
        events.get(0).addTag(tags.get(13));
        events.get(3).addTag(tags.get(3));
        events.get(15).addTag(tags.get(24));
        events.get(6).addTag(tags.get(13));
        events.get(1).addTag(tags.get(13));
        events.get(11).addTag(tags.get(36));
        events.get(17).addTag(tags.get(25));
        events.get(7).addTag(tags.get(37));
        events.get(4).addTag(tags.get(23));
        events.get(8).addTag(tags.get(27));
        events.get(17).addTag(tags.get(8));
        events.get(12).addTag(tags.get(26));
        events.get(15).addTag(tags.get(2));
        events.get(16).addTag(tags.get(17));
        events.get(22).addTag(tags.get(1));
        events.get(12).addTag(tags.get(34));
        events.get(13).addTag(tags.get(29));
        events.get(17).addTag(tags.get(4));
        events.get(21).addTag(tags.get(37));
        events.get(15).addTag(tags.get(22));
        events.get(18).addTag(tags.get(17));
        events.get(16).addTag(tags.get(37));
        events.get(21).addTag(tags.get(2));
        events.get(2).addTag(tags.get(11));
        events.get(14).addTag(tags.get(15));
        events.get(12).addTag(tags.get(1));
        events.get(7).addTag(tags.get(16));
        events.get(24).addTag(tags.get(3));
        events.get(18).addTag(tags.get(28));
        events.get(10).addTag(tags.get(19));
        events.get(11).addTag(tags.get(38));
        events.get(5).addTag(tags.get(9));
        events.get(20).addTag(tags.get(10));
        events.get(2).addTag(tags.get(27));
        events.get(5).addTag(tags.get(37));
        events.get(12).addTag(tags.get(23));
        events.get(5).addTag(tags.get(30));
    }
}
