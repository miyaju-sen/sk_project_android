package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * 構想画面用アクティビティクラス
 * TODO:タップで編集・ストーリー登録・更新・抽出用のメソッド
 *
 * @author ohs60224
 */
public class IdeaActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {
    /**
     * 構想とストーリー一覧を格納する配列
     */
    private List<Map<String, String>> _ideas = new ArrayList<>();
    /**
     * プロット概要が格納された配列
     */
    private HashMap<String, String> _outline = new HashMap<>();
    /**
     * 画面部品
     */
    private static TextView _tvIdea;
    private static ListView _lvStories;
    /**
     * 起承転結ごとにデータを振り分けるクラス
     */
    private IdeaAllocate _allocate = new IdeaAllocate();
    /**
     * 各タブごとに表示するデータ
     */
    private List<Map<String, String>> _idea1 = new ArrayList<>();
    private List<Map<String, String>> _idea2 = new ArrayList<>();
    private List<Map<String, String>> _idea3 = new ArrayList<>();
    private List<Map<String, String>> _idea4 = new ArrayList<>();
    /**
     * DrawerLayoutとActionBarDrawerToggle
     */
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar _toolbar;
    /**
     * NavigationViewのヘッダー部分のTextView
     */
    private TextView _tvMenuBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        Log.e("*******", "地点onCreate");

        //NavigationViewのヘッダー部分のTextViewを取得
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View drawerHeader = inflater.inflate(R.layout.drawer_header, null);
        _tvMenuBack = drawerHeader.findViewById(R.id.tvMenuBack); //プロット一覧へ戻る

        //Toolbar
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        //DrawerLayout
        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(IdeaActivity.this, mDrawer, _toolbar, R.string.drawable_open, R.string.drawable_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //NavigationViewのリスナー
        NavigationView nvLeftView = findViewById(R.id.nvLeftView);
        nvLeftView.setNavigationItemSelectedListener(this);

        //タブレイアウト
        setTabLayout();

        Intent intent = getIntent();
        _outline = (HashMap<String, String>) intent.getSerializableExtra("OUTLINE");
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("世界観");
        Log.e("*******", "地点onResume");

        IdeaJsonAccess access = new IdeaJsonAccess();
        access.setOnCallBack(new IdeaJsonAccess.CallBackTask() {
            @Override
            public void CallBack(List<Map<String, String>> list) {
                Log.e("*******", "地点onResume");
                _allocate.allocateIdea(list);

                //TODO:テキストビューにセット
                //TODO:リストビューにセット
//                _stories = list;
//                if(0 == list.size()) {
//                    String[] from = {"title", "story"};
//                    int[] to = {android.R.id.text1, android.R.id.text2};
//                    SimpleAdapter adapter = new SimpleAdapter(IdeaActivity.this, list, android.R.layout.simple_list_item_2, from, to);
//                    _lvStories.setAdapter(adapter);
//                    _lvStories.setOnItemClickListener(new ListItemClickListener());
//                }
            }
        });
        access.execute("", _outline.get("no"), "", "");
    }

    /**
     * フラグメントクラスから画面部品を受け取るメソッド
     */
    public static void setIdeaView(TextView textView, ListView listView) {
        Log.e("*******", "地点画面部品来た");
        _tvIdea = textView;
        _lvStories = listView;
    }

    /**
     * NavigationView
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent();
        int itemId = item.getItemId();
        switch (itemId) {
            //概要画面
            case R.id.menuOutline:
                intent = new Intent(IdeaActivity.this, OutlineActivity.class);
                break;
            //登場人物一覧画面へ（何もしない）
            case R.id.menuCharacter:
                intent = new Intent(IdeaActivity.this, CharacterListActivity.class);
                break;
            //世界観一覧画面へ
            case R.id.menuWorld:
                intent = new Intent(IdeaActivity.this, WorldViewListActivity.class);
                break;
            //構想画面へ
            case R.id.menuIdea:
                intent = null;
                break;
            //メモ画面へ
            case R.id.menuMemo:
                intent = new Intent(IdeaActivity.this, MemoListActivity.class);
                break;
            //削除
            case R.id.menuDelete:
                intent = null;
                onPlotDeleteClick();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);

        if(null != intent) {
            intent.putExtra("OUTLINE", _outline);
            startActivity(intent);
        }
        return true;
    }

    /**
     * 「プロット一覧に戻る」押下時の処理
     */
    public void onMenuBackClick(View view) {
        Intent intent = new Intent(IdeaActivity.this, PlotListActivity.class);
        startActivity(intent);
    }

    /**
     * プロット削除押下時の処理
     */
    private void onPlotDeleteClick() {
        String no = _outline.get("no");
        String title = _outline.get("title");

        Bundle extras = new Bundle();
        extras.putString("no", no);
        extras.putString("title", title);

        Context context = this;
        PlotDeleteConfirmDialogCreate.setActivityContext(context);

        PlotDeleteConfirmDialogCreate dialog = new PlotDeleteConfirmDialogCreate();
        dialog.setArguments(extras);

        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, "IdeaActivity");
    }

    /**
     * タブをレイアウトするのに必要な処理を行うメソッド
     */
    private void setTabLayout() {
        //タブレイアウト・viewPager・タブアイテムを取得
        TabLayout tlIdeas = findViewById(R.id.tlIdeas);
        ViewPager pager = findViewById(R.id.pager);
        TabItem tabItemIdea1 = tlIdeas.findViewById(R.id.tiIdea1);
        TabItem tabItemIdea2 = tlIdeas.findViewById(R.id.tiIdea2);
        TabItem tabItemIdea3 = tlIdeas.findViewById(R.id.tiIdea3);
        TabItem tabItemIdea4 = tlIdeas.findViewById(R.id.tiIdea4);

        //タブタイトル
        final String[] tabTitles = {
                getString(R.string.idea_1),
                getString(R.string.idea_2),
                getString(R.string.idea_3),
                getString(R.string.idea_4)
        };

        //表示ページに必要な項目を設定
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            /**
             * positionに対応したFragmentを反映するメソッド
             * タブを選択するたびに呼ばれる
             *
             * @param position 設定するタブのページ番号
             * @return タブページに設定するFragment
             */
            @Override
            public Fragment getItem(int position) {
                Log.e("*******", "地点タブ選択" + position);
                return new TabIdea1Fragment();

//                switch (position) {
//                    case 0:
//                        TabIdea1Fragment f1 = new TabIdea1Fragment();
//                        return f1;
//                    case 1:
//                        TabIdea2Fragment f2 = new TabIdea2Fragment();
//                        return f2;
//                    case 2:
//                        TabIdea3Fragment f3 = new TabIdea3Fragment();
//                        return f3;
//                    case 3:
//                        TabIdea4Fragment f4 = new TabIdea4Fragment();
//                        return f4;
//                    default:
//                        return null;
//                }
            }

            /**
             * タブ総数分のタイトルをタブに反映するメソッド（後からセットするためにnullを返しておく）
             * @param position
             * @return
             */
            @Override
            public CharSequence getPageTitle(int position) {
                return null;
            }

            /**
             * タブの総数を返すメソッド
             * @return
             */
            @Override
            public int getCount() {
                return tabTitles.length;
            }
        };

        //タブレイアウト
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View idea1View = inflater.inflate(R.layout.tab_idea1, null); //タブ「起」
        View idea2View = inflater.inflate(R.layout.tab_idea2, null); //タブ「承」
        View idea3View = inflater.inflate(R.layout.tab_idea3, null); //タブ「転」
        View idea4View = inflater.inflate(R.layout.tab_idea4, null); //タブ「結」

        TabLayout.Tab tbIdea1 = tlIdeas.getTabAt(0);
        TabLayout.Tab tbIdea2 = tlIdeas.getTabAt(1);
        TabLayout.Tab tbIdea3 = tlIdeas.getTabAt(2);
        TabLayout.Tab tbIdea4 = tlIdeas.getTabAt(3);
        tbIdea1.setCustomView(idea1View);
        tbIdea2.setCustomView(idea2View);
        tbIdea3.setCustomView(idea3View);
        tbIdea4.setCustomView(idea4View);

        //ViewPagerにページを設定
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);

        //ViewPagerをTabLayoutに設定
        tlIdeas.setupWithViewPager(pager);

        //初期に選択されているTabを設定
        tlIdeas.getTabAt(0).select();
    }

    /**
     * ページが切り替わった時に呼び出される
     * @param position どのタブか
     */
    @Override
    public void onPageSelected(int position) {
        Log.e("*******", "地点ページ切り替わった" + position);
        _ideas = new ArrayList<>();

        switch (position) {
            case 0:
                _ideas = _allocate.getIdea1();
                _tvIdea.setText( _ideas.get(0).get("note") );
                break;
            case 1:
                _ideas = _allocate.getIdea2();
                _tvIdea.setText( _ideas.get(0).get("note") );
                break;
            case 2:
                _ideas = _allocate.getIdea3();
                _tvIdea.setText( _ideas.get(0).get("note") );
                break;
            case 3:
                _ideas = _allocate.getIdea4();
                _tvIdea.setText( _ideas.get(0).get("note") );
                break;
        }
    }


    //ViewPager.OnPageChangeListenerを実装する際、以下の内容も実装しておく必要がある。（空で問題ない）
    //----------------------------------------------------------------------------------------------
    /**
     * ページスクロール中に呼び出される
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e("*******", "地点ページ切り替え中" + position);
    }
    /**
     * スクロール状態が変化したときに呼び出される
     */
    @Override
    public void onPageScrollStateChanged(int state) {
    }
    //----------------------------------------------------------------------------------------------
}
