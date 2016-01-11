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

import java.util.ArrayList;
import java.util.Date;
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
            e = new Event("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", new Date());
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
        e = new Event("같지 바이며, 그들의 같으며, 위하여, 품고 이상, 만물은 그들은 힘있다.", new Date(1417446000));
        e.save();
        events.add(e);
        e = new Event("가장 사랑의 아니더면, 청춘 맺어, 그림자는 있을 인간은 사랑의 것이다.", new Date(1448031600));
        e.save();
        events.add(e);
        e = new Event("있는가? 돋고, 그것은 피부가 위하여 위하여서, 찾아다녀도, 교향악이다.", new Date(1314025200));
        e.save();
        events.add(e);
        e = new Event("그들은 그것을 이상은 그들의 있는 청춘에서만 인도하겠다는 그것은 있다.", new Date(1365087600));
        e.save();
        events.add(e);
        e = new Event("천고에 쓸쓸하랴? 살았으며, 청춘을 위하여, 하였으며, 열매를 것이다.", new Date(1400943600));
        e.save();
        events.add(e);
        e = new Event("있으랴? 얼마나 그들에게 위하여, 스며들어 그것을 싹이 모래뿐일 말이다.", new Date(1297954800));
        e.save();
        events.add(e);
        e = new Event("우리 사막이다.얼마나 천고에 트고, 찾아다녀도, 아니더면, 봄바람이다.", new Date(1423234800));
        e.save();
        events.add(e);
        e = new Event("그리하였는가? 밝은 인간은 우리 천고에 없는 구할 든 옷을 돋고, 칼이다.", new Date(1445958000));
        e.save();
        events.add(e);
        e = new Event("그것은 심장의 작고 얼마나 새가 작고 그들은 돋고, 영락과 바로 것이다.", new Date(1268146800));
        e.save();
        events.add(e);
        e = new Event("만물은 사랑의 청춘의 인생을 열락의 심장의 이상의 미인을 설레는 교향악이다.", new Date(1425481200));
        e.save();
        events.add(e);
        e = new Event("든 작고 심장의 있는 커다란 사랑의 하였으며, 눈이 불어 바이며, 이것이다.", new Date(1448290800));
        e.save();
        events.add(e);
        e = new Event("그리하였는가? 커다란 구할 이상 청춘 용감하고 인간은 굳세게 교향악이다.", new Date(1301756400));
        e.save();
        events.add(e);
        e = new Event("그리하였는가? 따뜻한 그들은 몸이 천하를 그것은 바이며, 것이 부패뿐이다.", new Date(1344783600));
        e.save();
        events.add(e);
        e = new Event("구할 그들은 착목한는 얼음 같으며, 그들의 있을 주는 이것은 것이다.", new Date(1349535600));
        e.save();
        events.add(e);
        e = new Event("우리 풀이 아니더면, 있을 거선의 밝은 가장 설산에서 풀이 봄바람이다.", new Date(1308063600));
        e.save();
        events.add(e);
        e = new Event("살았으며, 소금이라 고동을 가지에 방황하였으며, 뭇 주는 봄바람이다.", new Date(1333378800));
        e.save();
        events.add(e);
        e = new Event("소금이라 석가는 소금이라 청춘 얼마나 온갖 그들은 소금이라 따뜻한 칼이다.", new Date(1356447600));
        e.save();
        events.add(e);
        e = new Event("만물은 곧 밝은 크고 굳세게 것이다.보라, 사랑의 위하여, 이것이다.", new Date(1378134000));
        e.save();
        events.add(e);
        e = new Event("튼튼하며, 이상의 시들어 우리 하였으며, 하는 같지 청춘의 얼음 것이다.", new Date(1282230000));
        e.save();
        events.add(e);
        e = new Event("부패를 쓸쓸하랴? 두기 위하여서, 소금이라 얼마나 쓸쓸하랴? 따뜻한 말이다.", new Date(1446994800));
        e.save();
        events.add(e);
        e = new Event("하는 그리하였는가? 타오르고 하는 가진 것이 그것을 소금이라 황금시대다.", new Date(1444921200));
        e.save();
        events.add(e);
        e = new Event("그들은 트고, 청춘의 것이 소금이라 용감하고 얼음과 하는 하는 싹이 힘있다.", new Date(1329836400));
        e.save();
        events.add(e);
        e = new Event("가치를 이상, 얼음 열락의 유소년에게서 구할 쓸쓸하랴? 구할 구할 보라.", new Date(1265468400));
        e.save();
        events.add(e);
        e = new Event("있으랴? 있는가? 되는 넣는 곧 수 위하여서 설산에서 이상의 이상은 보라.", new Date(1283612400));
        e.save();
        events.add(e);
        e = new Event("커다란 얼음 착목한는 가슴에 관현악이며, 같지 유소년에게서 소금이라 보라.", new Date(1306508400));
        e.save();
        events.add(e);

        t = new Tag("몸만심일고");
        t.save();
        tags.add(t);
        t = new Tag("몸유불림");
        t.save();
        tags.add(t);
        t = new Tag("현패칼불");
        t.save();
        tags.add(t);
        t = new Tag("쓸림설석");
        t.save();
        tags.add(t);
        t = new Tag("목엇바갖");
        t.save();
        tags.add(t);
        t = new Tag("나생의");
        t.save();
        tags.add(t);
        t = new Tag("진미들바관");
        t.save();
        tags.add(t);
        t = new Tag("예위너");
        t.save();
        tags.add(t);
        t = new Tag("청슴간금");
        t.save();
        tags.add(t);
        t = new Tag("약간현");
        t.save();
        tags.add(t);
        t = new Tag("행싸시미이");
        t.save();
        tags.add(t);
        t = new Tag("나랴기끓");
        t.save();
        tags.add(t);
        t = new Tag("품약보");
        t.save();
        tags.add(t);
        t = new Tag("것다길바말");
        t.save();
        tags.add(t);
        t = new Tag("싸아춘");
        t.save();
        tags.add(t);
        t = new Tag("넣되악");
        t.save();
        tags.add(t);
        t = new Tag("위더목러");
        t.save();
        tags.add(t);
        t = new Tag("았얼열유");
        t.save();
        tags.add(t);
        t = new Tag("바자묘");
        t.save();
        tags.add(t);
        t = new Tag("물세발유");
        t.save();
        tags.add(t);
        t = new Tag("란곧 유");
        t.save();
        tags.add(t);
        t = new Tag("석못진뜻");
        t.save();
        tags.add(t);
        t = new Tag("림과여녀");
        t.save();
        tags.add(t);
        t = new Tag("착살묘소나");
        t.save();
        tags.add(t);
        t = new Tag("과나장슴을");
        t.save();
        tags.add(t);
        t = new Tag("심패랴");
        t.save();
        tags.add(t);
        t = new Tag("예나로");
        t.save();
        tags.add(t);
        t = new Tag("꽃행봄");
        t.save();
        tags.add(t);
        t = new Tag("의산래");
        t.save();
        tags.add(t);
        t = new Tag("며생관래얼");
        t.save();
        tags.add(t);
        t = new Tag("에끓을몸");
        t.save();
        tags.add(t);
        t = new Tag("나리너");
        t.save();
        tags.add(t);
        t = new Tag("음튼녀아");
        t.save();
        tags.add(t);
        t = new Tag("므튼것도금");
        t.save();
        tags.add(t);
        t = new Tag("면긴물");
        t.save();
        tags.add(t);
        t = new Tag("일한막피");
        t.save();
        tags.add(t);
        t = new Tag("싹현같거");
        t.save();
        tags.add(t);
        t = new Tag("리는말넣았");
        t.save();
        tags.add(t);
        t = new Tag("레소교겠");
        t.save();
        tags.add(t);
        t = new Tag("발행묘상시");
        t.save();
        tags.add(t);

        f = new Friend("김았면", "01032686759", "옷을 몸이 두기 유소년에게서 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("박오년", "01058181165", "인간이 사랑의 쓸쓸하랴? 돋고, 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("정래행싹", "01015761040", "그들은 가장 착목한는 그들의 교향악이다.");
        f.save();
        friends.add(f);
        f = new Friend("정림니불", "01020950519", "얼음과 놀이 주며, 그러므로 내는 보라.");
        f.save();
        friends.add(f);
        f = new Friend("박노커를", "01064944104", "두기 위하여서, 우리 현저하게 말이다.");
        f.save();
        friends.add(f);
        f = new Friend("안석대락", "01017017753", "방지하는 것이다.보라, 구하지 보라.");
        f.save();
        friends.add(f);
        f = new Friend("김악트시", "01028910439", "것이다.보라, 것이다.보라, 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("안관모", "01006946647", "거친 따뜻한 위하여, 그들의 약동하다.");
        f.save();
        friends.add(f);
        f = new Friend("박만저", "01072768026", "따뜻한 있는 살았으며, 그것은 아니다.");
        f.save();
        friends.add(f);
        f = new Friend("박스없", "01032029477", "타오르고 천고에 길을 바이며, 뿐이다.");
        f.save();
        friends.add(f);
        f = new Friend("안며악", "01064795342", "그리하였는가? 주며, 천고에 칼이다.");
        f.save();
        friends.add(f);
        f = new Friend("김림황", "01004305306", "설레는 사라지지 청춘에서만 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("안아한너", "01045345882", "청춘에서만 열락의 구하지 교향악이다.");
        f.save();
        friends.add(f);
        f = new Friend("박하할", "01039626861", "그것을 주는 얼마나 것이 그들의 보라.");
        f.save();
        friends.add(f);
        f = new Friend("김은뿐상", "01085795266", "찾아다녀도, 사랑의 바로 부패뿐이다.");
        f.save();
        friends.add(f);
        f = new Friend("이과있말", "01043937860", "있는가? 크고 길을 돋고, 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("정온놀구", "01082391406", "구할 천고에 그들은 긴지라 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("안봄두금", "01015262739", "주는 커다란 하였으며, 위하여서 보라.");
        f.save();
        friends.add(f);
        f = new Friend("박은행힘", "01032136990", "커다란 그들에게 하는 뭇 봄바람이다.");
        f.save();
        friends.add(f);
        f = new Friend("정미풀", "01039317018", "거선의 돋고, 든 얼마나 그들의 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("김만얼", "01014214623", "것은 바로 위하여 인도하겠다는 이것이다.");
        f.save();
        friends.add(f);
        f = new Friend("안산어", "01091731538", "가진 수 청춘 것은 거선의 이것이다.");
        f.save();
        friends.add(f);
        f = new Friend("박할뛰옷", "01078085557", "이상의 미인을 방황하였으며, 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("이까도", "01073348668", "그들은 청춘이 이상은 청춘을 부패뿐이다.");
        f.save();
        friends.add(f);
        f = new Friend("정꽃면", "01011807739", "구할 그들은 미인을 구하지 말이다.");
        f.save();
        friends.add(f);
        f = new Friend("안로뜻", "01048874168", "사막이다.얼마나 아니더면, 봄바람이다.");
        f.save();
        friends.add(f);
        f = new Friend("정놀가", "01036037600", "그들은 얼음 영락과 위하여 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("박뿐악노", "01035192311", "위하여서 사라지지 타오르고 있다.");
        f.save();
        friends.add(f);
        f = new Friend("김매석일", "01076943829", "예수는 이것은 이것은 바이며, 것이다.");
        f.save();
        friends.add(f);
        f = new Friend("안를행모", "01075111272", "청춘에서만 크고 있으랴? 부패뿐이다.");
        f.save();
        friends.add(f);

        events.get(18).addFriend(friends.get(18));
        events.get(18).addFriend(friends.get(23));
        events.get(2).addFriend(friends.get(7));
        events.get(5).addFriend(friends.get(16));
        events.get(6).addFriend(friends.get(3));
        events.get(24).addFriend(friends.get(22));
        events.get(4).addFriend(friends.get(24));
        events.get(18).addFriend(friends.get(14));
        events.get(0).addFriend(friends.get(25));
        events.get(23).addFriend(friends.get(14));
        events.get(6).addFriend(friends.get(16));
        events.get(16).addFriend(friends.get(3));
        events.get(5).addFriend(friends.get(29));
        events.get(13).addFriend(friends.get(24));
        events.get(14).addFriend(friends.get(28));
        events.get(20).addFriend(friends.get(14));
        events.get(3).addFriend(friends.get(28));
        events.get(13).addFriend(friends.get(29));
        events.get(23).addFriend(friends.get(0));
        events.get(0).addFriend(friends.get(17));
        events.get(15).addFriend(friends.get(5));
        events.get(23).addFriend(friends.get(8));
        events.get(8).addFriend(friends.get(8));
        events.get(10).addFriend(friends.get(13));
        events.get(6).addFriend(friends.get(15));
        events.get(21).addFriend(friends.get(0));
        events.get(22).addFriend(friends.get(27));
        events.get(23).addFriend(friends.get(14));
        events.get(14).addFriend(friends.get(6));
        events.get(0).addFriend(friends.get(17));
        events.get(7).addFriend(friends.get(22));
        events.get(16).addFriend(friends.get(29));
        events.get(2).addFriend(friends.get(4));
        events.get(8).addFriend(friends.get(19));
        events.get(10).addFriend(friends.get(28));
        events.get(11).addFriend(friends.get(10));
        events.get(12).addFriend(friends.get(26));
        events.get(18).addFriend(friends.get(18));
        events.get(14).addFriend(friends.get(20));
        events.get(5).addFriend(friends.get(7));
        events.get(19).addFriend(friends.get(15));
        events.get(0).addFriend(friends.get(9));
        events.get(17).addFriend(friends.get(25));
        events.get(12).addFriend(friends.get(20));
        events.get(9).addFriend(friends.get(1));
        events.get(12).addFriend(friends.get(25));
        events.get(18).addFriend(friends.get(29));
        events.get(20).addFriend(friends.get(9));
        events.get(15).addFriend(friends.get(20));
        events.get(18).addFriend(friends.get(26));
        events.get(9).addFriend(friends.get(8));
        events.get(6).addFriend(friends.get(8));
        events.get(13).addFriend(friends.get(20));
        events.get(13).addFriend(friends.get(9));
        events.get(12).addFriend(friends.get(28));
        events.get(20).addFriend(friends.get(27));
        events.get(9).addFriend(friends.get(11));
        events.get(8).addFriend(friends.get(26));
        events.get(17).addFriend(friends.get(21));
        events.get(20).addFriend(friends.get(6));
        events.get(9).addFriend(friends.get(20));
        events.get(3).addFriend(friends.get(1));
        events.get(2).addFriend(friends.get(11));
        events.get(15).addFriend(friends.get(10));
        events.get(9).addFriend(friends.get(27));
        events.get(20).addFriend(friends.get(6));
        events.get(23).addFriend(friends.get(8));
        events.get(17).addFriend(friends.get(5));
        events.get(11).addFriend(friends.get(25));
        events.get(3).addFriend(friends.get(20));
        events.get(24).addFriend(friends.get(6));
        events.get(10).addFriend(friends.get(10));
        events.get(18).addFriend(friends.get(19));
        events.get(22).addFriend(friends.get(14));
        events.get(14).addFriend(friends.get(15));
        events.get(8).addFriend(friends.get(21));
        events.get(22).addFriend(friends.get(10));
        events.get(21).addFriend(friends.get(3));
        events.get(13).addFriend(friends.get(9));
        events.get(24).addFriend(friends.get(29));
        events.get(20).addFriend(friends.get(14));
        events.get(14).addFriend(friends.get(23));
        events.get(10).addFriend(friends.get(21));
        events.get(22).addFriend(friends.get(14));
        events.get(21).addFriend(friends.get(14));
        events.get(9).addFriend(friends.get(6));
        events.get(19).addFriend(friends.get(8));
        events.get(7).addFriend(friends.get(1));
        events.get(20).addFriend(friends.get(22));
        events.get(4).addFriend(friends.get(4));
        events.get(14).addFriend(friends.get(7));
        events.get(3).addFriend(friends.get(27));
        events.get(21).addFriend(friends.get(14));
        events.get(24).addFriend(friends.get(15));
        events.get(7).addFriend(friends.get(18));
        events.get(4).addFriend(friends.get(3));
        events.get(17).addFriend(friends.get(26));
        events.get(5).addFriend(friends.get(21));
        events.get(12).addFriend(friends.get(19));
        events.get(9).addFriend(friends.get(19));
        events.get(7).addTag(tags.get(10));
        events.get(8).addTag(tags.get(29));
        events.get(3).addTag(tags.get(39));
        events.get(0).addTag(tags.get(17));
        events.get(10).addTag(tags.get(33));
        events.get(17).addTag(tags.get(25));
        events.get(18).addTag(tags.get(35));
        events.get(18).addTag(tags.get(7));
        events.get(7).addTag(tags.get(37));
        events.get(6).addTag(tags.get(5));
        events.get(6).addTag(tags.get(28));
        events.get(8).addTag(tags.get(39));
        events.get(16).addTag(tags.get(23));
        events.get(3).addTag(tags.get(38));
        events.get(4).addTag(tags.get(35));
        events.get(0).addTag(tags.get(2));
        events.get(15).addTag(tags.get(18));
        events.get(11).addTag(tags.get(0));
        events.get(22).addTag(tags.get(35));
        events.get(9).addTag(tags.get(2));
        events.get(8).addTag(tags.get(25));
        events.get(9).addTag(tags.get(37));
        events.get(20).addTag(tags.get(0));
        events.get(5).addTag(tags.get(17));
        events.get(6).addTag(tags.get(12));
        events.get(14).addTag(tags.get(27));
        events.get(6).addTag(tags.get(23));
        events.get(3).addTag(tags.get(26));
        events.get(2).addTag(tags.get(30));
        events.get(17).addTag(tags.get(2));
        events.get(3).addTag(tags.get(29));
        events.get(3).addTag(tags.get(37));
        events.get(12).addTag(tags.get(25));
        events.get(15).addTag(tags.get(0));
        events.get(13).addTag(tags.get(14));
        events.get(11).addTag(tags.get(11));
        events.get(7).addTag(tags.get(7));
        events.get(6).addTag(tags.get(34));
        events.get(14).addTag(tags.get(28));
        events.get(19).addTag(tags.get(5));
        events.get(9).addTag(tags.get(23));
        events.get(21).addTag(tags.get(30));
        events.get(8).addTag(tags.get(25));
        events.get(15).addTag(tags.get(12));
        events.get(6).addTag(tags.get(18));
        events.get(13).addTag(tags.get(10));
        events.get(5).addTag(tags.get(33));
        events.get(6).addTag(tags.get(24));
        events.get(16).addTag(tags.get(1));
        events.get(14).addTag(tags.get(36));
        events.get(12).addTag(tags.get(2));
        events.get(7).addTag(tags.get(10));
        events.get(10).addTag(tags.get(8));
        events.get(16).addTag(tags.get(20));
        events.get(1).addTag(tags.get(33));
        events.get(12).addTag(tags.get(4));
        events.get(21).addTag(tags.get(38));
        events.get(7).addTag(tags.get(25));
        events.get(9).addTag(tags.get(35));
        events.get(16).addTag(tags.get(5));
        events.get(9).addTag(tags.get(15));
        events.get(15).addTag(tags.get(30));
        events.get(9).addTag(tags.get(28));
        events.get(10).addTag(tags.get(28));
        events.get(17).addTag(tags.get(20));
        events.get(21).addTag(tags.get(34));
        events.get(13).addTag(tags.get(27));
        events.get(16).addTag(tags.get(31));
        events.get(24).addTag(tags.get(3));
        events.get(0).addTag(tags.get(36));
        events.get(5).addTag(tags.get(38));
        events.get(7).addTag(tags.get(24));
        events.get(3).addTag(tags.get(14));
        events.get(14).addTag(tags.get(39));
        events.get(14).addTag(tags.get(13));
        events.get(11).addTag(tags.get(34));
        events.get(19).addTag(tags.get(1));
        events.get(4).addTag(tags.get(32));
        events.get(6).addTag(tags.get(0));
        events.get(9).addTag(tags.get(19));
        events.get(17).addTag(tags.get(28));
        events.get(9).addTag(tags.get(20));
        events.get(8).addTag(tags.get(24));
        events.get(13).addTag(tags.get(20));
        events.get(4).addTag(tags.get(2));
        events.get(9).addTag(tags.get(20));
        events.get(5).addTag(tags.get(13));
        events.get(3).addTag(tags.get(34));
        events.get(21).addTag(tags.get(27));
        events.get(5).addTag(tags.get(14));
        events.get(3).addTag(tags.get(36));
        events.get(10).addTag(tags.get(31));
        events.get(17).addTag(tags.get(30));
        events.get(21).addTag(tags.get(2));
        events.get(12).addTag(tags.get(14));
        events.get(20).addTag(tags.get(3));
        events.get(10).addTag(tags.get(33));
        events.get(12).addTag(tags.get(29));
        events.get(20).addTag(tags.get(32));
        events.get(6).addTag(tags.get(24));
        events.get(17).addTag(tags.get(16));
        events.get(18).addTag(tags.get(36));
        events.get(0).addTag(tags.get(3));
        events.get(19).addTag(tags.get(3));
        events.get(7).addTag(tags.get(34));
        events.get(7).addTag(tags.get(37));
        events.get(10).addTag(tags.get(31));
        events.get(10).addTag(tags.get(17));
        events.get(4).addTag(tags.get(7));
        events.get(0).addTag(tags.get(27));
        events.get(22).addTag(tags.get(38));
        events.get(17).addTag(tags.get(32));
        events.get(8).addTag(tags.get(23));
        events.get(3).addTag(tags.get(29));
        events.get(5).addTag(tags.get(12));
        events.get(21).addTag(tags.get(15));
        events.get(12).addTag(tags.get(24));
        events.get(8).addTag(tags.get(4));
        events.get(21).addTag(tags.get(30));
        events.get(24).addTag(tags.get(38));
    }
}
