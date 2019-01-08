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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

/**
 * 就職作品
 *
 * 構想画面用アクティビティクラス
 * TODO:タップで編集
 *
 * @author ohs60224
 */
public class IdeaActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {
    /**
     * プロット概要が格納された配列
     */
    private HashMap<String, String> _outline = new HashMap<>();
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
                switch (position) {
                    case 0:
                        return new TabIdeaFragment();
                    case 1:
                        return new TabIdeaFragment();
                    case 2:
                        return new TabIdeaFragment();
                    case 3:
                        return new TabIdeaFragment();
                    default:
                        return null;
                }
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
     * TODO:ページが切り替わった時に呼び出される
     * @param position どのタブか
     */
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            //「起」の場合
            case 0:
                //処理
                break;
            //「承」の場合
            case 1:
                break;
            //「転」
            case 2:
                break;
            //「結」
            case 3:
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
    }
    /**
     * スクロール状態が変化したときに呼び出される
     */
    @Override
    public void onPageScrollStateChanged(int state) {
    }
    //----------------------------------------------------------------------------------------------
}