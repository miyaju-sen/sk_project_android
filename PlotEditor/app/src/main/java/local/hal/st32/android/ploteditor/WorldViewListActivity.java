package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * 就職作品
 *
 * 世界観一覧画面用アクティビティ
 *
 * @author ohs60224
 */
public class WorldViewListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {
    /**
     * 画面部品
     */
    private TextView _tvStage; //舞台の内容
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
     * メニューアイテム
     */
    private MenuItem _edit;
    private MenuItem _insert;
    private MenuItem _reload;
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

        Intent intent = getIntent();
        _outline = (HashMap<String, String>) intent.getSerializableExtra("OUTLINE");

        //画面部品取得
        _tvStage = _stageView.findViewById(R.id.tvStage);

        //テスト用
        ListView lvMenu = _parlanceView.findViewById(R.id.lvMenu);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("世界観");

        //TODO:設定・用語を取得する（JSONアクセス）
    }

    /**
     * オプションメニュー作成
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        //表示されたままのメニューアイコンを非表示に
        menu.setGroupVisible(R.id.mgEdit, false);

        //更新ボタンを取得
        _reload = menu.findItem(R.id.menuReload);

        //追加ボタンを取得
        _insert = menu.findItem(R.id.menuInsert);

        //編集ボタンを取得
        _edit = menu.findItem(R.id.menuEdit);
        _edit.setVisible(true);

        return true;
    }

    /**
     * オプションメニュー選択時処理
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menuInsert:
                //TODO:追加処理
//                onInsertButtonClick();
                break;
            case R.id.menuEdit:
                //TODO:編集処理
                onEditButtonClick();
                break;
            case R.id.menuReload:
                onResume();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 編集ボタン押下時の処理
     */
    private void onEditButtonClick() {
        Intent intent = new Intent(WorldViewListActivity.this, StageEditActivity.class);
        intent.putExtra("OUTLINE", _outline);
        startActivity(intent);
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
                intent = new Intent(WorldViewListActivity.this, CharacterListActivity.class);
                break;
            //世界観一覧画面へ
            case R.id.menuWorld:
                intent = null;
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
        Intent intent = new Intent(WorldViewListActivity.this, PlotListActivity.class);
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
        dialog.show(manager, "WorldViewListActivity");
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

    /**
     * ページが切り替わった時に呼び出される
     * @param position どのタブか
     */
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            //「舞台」の場合
            case 0:
                _insert.setVisible(false);
                _reload.setVisible(false);
                _edit.setVisible(true);
                break;
            //「設定・用語」の場合
            case 1:
                _insert.setVisible(true);
                _reload.setVisible(true);
                _edit.setVisible(false);
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
