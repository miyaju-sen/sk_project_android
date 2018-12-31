package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.TextView;

/**
 * 就職作品
 *
 * 世界観一覧画面用アクティビティ
 *
 * @author ohs60224
 */
public class WorldViewListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {
    /**
     * タブアイテム
     */
    private TabItem _tabItemStage; //舞台
    private TabItem _tabItemParlance; //設定・用語
    /**
     * タブレイアウト
     */
    private TabLayout _tlWorldView;
    /**
     * ViewPager
     */
    private ViewPager _pager;
    /**
     * 舞台と設定・用語のビュー
     */
    private View _stageView;
    private View _parlanceView;

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
        setContentView(R.layout.activity_world_view_list);

        //NavigationViewのヘッダー部分のTextViewを取得
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View drawerHeader = inflater.inflate(R.layout.drawer_header, null);
        _tvMenuBack = drawerHeader.findViewById(R.id.tvMenuBack); //プロット一覧へ戻る

        //Toolbar
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        //DrawerLayout
        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(WorldViewListActivity.this, mDrawer, _toolbar, R.string.drawable_open, R.string.drawable_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //NavigationViewのリスナー
        NavigationView nvLeftView = findViewById(R.id.nvLeftView);
        nvLeftView.setNavigationItemSelectedListener(this);

        //タブレイアウト
        setTabLayout();

        //テスト用
        ListView lvMenu = _stageView.findViewById(R.id.lvMenu);
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
                intent = new Intent(WorldViewListActivity.this, OutlineActivity.class);
                break;
            //登場人物一覧画面へ（何もしない）
            case R.id.menuCharacter:
                intent = null;
                break;
            //世界観一覧画面へ
            case R.id.menuWorld:
                intent = new Intent(WorldViewListActivity.this, WorldViewListActivity.class);
                break;
            //構想画面へ
            case R.id.menuStory:
                intent = new Intent(WorldViewListActivity.this, StoryEditActivity.class);
                break;
            //メモ画面へ
            case R.id.menuMemo:
                intent = new Intent(WorldViewListActivity.this, MemoListActivity.class);
                break;
            //削除
            case R.id.menuDelete:
                intent = null;
                //TODO:プロット削除処理
//                onPlotDeleteClick();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);

        if(null != intent) {
            //TODO:概要取得
//            intent.putExtra("OUTLINE", _outline);
            startActivity(intent);
        }
        return true;
    }

    /**
     * タブをレイアウトするのに必要な処理を行うメソッド
     */
    private void setTabLayout() {
        //タブレイアウト・viewPager・タブアイテムを取得
        _tlWorldView = findViewById(R.id.tlWorldView);
        _pager = findViewById(R.id.pager);
        _tabItemStage = _tlWorldView.findViewById(R.id.tiStage);
        _tabItemParlance = _tlWorldView.findViewById(R.id.tiParlance);

        //タブタイトル
        final String[] _tabTitles = {getString(R.string.tv_stage), getString(R.string.tv_parlance)};

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
                        return new TabStageFragment();
                    case 1:
                        //return TabParlanceFragment.newInstance(position + 1);
                        return new TabParlanceFragment();
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
                // return _tabTitles[position];
                return null;
            }

            /**
             * タブの総数を返すメソッド
             * @return
             */
            @Override
            public int getCount() {
                return _tabTitles.length;
            }
        };

        //タブレイアウト
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _stageView = inflater.inflate(R.layout.tab_stage, null); //タブ「舞台」
        _parlanceView = inflater.inflate(R.layout.tab_parlance, null); //タブ「設定・用語」
        TabLayout.Tab tbStage = _tlWorldView.getTabAt(0);
        TabLayout.Tab tbParlance = _tlWorldView.getTabAt(1);
        tbStage.setCustomView(_stageView);
        tbParlance.setCustomView(_parlanceView);

        //ViewPagerにページを設定
        _pager.setAdapter(adapter);
        _pager.addOnPageChangeListener(this);

        //ViewPagerをTabLayoutに設定
        _tlWorldView.setupWithViewPager(_pager);

        //初期に選択されているTabを設定
        _tlWorldView.getTabAt(0).select();
    }


    //ViewPager.OnPageChangeListenerを実装する際、以下の内容を実装する必要がある。（空で問題ない）
    //----------------------------------------------------------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
    //----------------------------------------------------------------------------------------------
}
