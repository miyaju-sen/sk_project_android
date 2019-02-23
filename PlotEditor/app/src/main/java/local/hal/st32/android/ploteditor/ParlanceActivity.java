package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * 就職作品
 *
 * 設定・用語の確認画面用アクティビティクラス
 * 登録（編集）後あるいはリスト押下後に遷移してくる
 *
 * @author ohs60224
 */
public class ParlanceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * 現在表示している画面に対応したアクティビティ
     */
    public static final String NOW_ACTIVITY = new NowActivity().getParlanceActivity();
    /**
     * 画面部品
     */
    private TextView _tvName;
    private TextView _tvExplanation;
    /**
     * インテント
     */
    private Intent _intent;
    /**
     * 登場人物の情報を格納する配列
     */
    private HashMap<String, String> _parlance = new HashMap<>();
    /**
     * DrawerLayoutとActionBarDrawerToggle
     */
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar _toolbar;
    /**
     * NavigationViewのヘッダー部分のTextView
     */
    private TextView mTvMenuTitle;
    /**
     * プロット概要が格納された配列
     */
    private HashMap<String, String> _outline = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parlance);

        //画面部品取得
        _tvName = findViewById(R.id.tvParlanceName);
        _tvExplanation = findViewById(R.id.tvExplanation);

        //スクロールビューにフェードアウト追加
//        ScrollView scrollView = findViewById(R.id.scrollView);
//        scrollView.setVerticalFadingEdgeEnabled(true);
//        scrollView.setFadingEdgeLength(100);

        //NavigationViewのヘッダー部分のTextViewを取得
        NavigationView nvLeftView = findViewById(R.id.nvLeftView);
        View drawerHeader = nvLeftView.getHeaderView(0);
        mTvMenuTitle = drawerHeader.findViewById(R.id.tvMenuTitle); //プロット一覧へ戻る

        //Toolbar
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        //DrawerLayout
        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(ParlanceActivity.this, mDrawer, _toolbar, R.string.drawable_open, R.string.drawable_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //NavigationViewのリスナー
        nvLeftView.setNavigationItemSelectedListener(this);

        //遷移元からデータ取得
        _intent = getIntent();
        _outline = (HashMap<String, String>) _intent.getSerializableExtra("OUTLINE");
        _parlance = (HashMap<String, String>) _intent.getSerializableExtra("PARLANCE");
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("設定・用語");
        mTvMenuTitle.setText( _outline.get("title") );

        ViewPager viewPager = findViewById(R.id.pager);
        PagerParlanceStatePagerAdapter adapter = new PagerParlanceStatePagerAdapter( getSupportFragmentManager() );
        adapter.setPageCount( _parlance.size() );
        Log.e("＊＊＊＊＊＊＊＊＊＊", "長さは" + _parlance.size());
        viewPager.setAdapter(adapter);

        //画面部品に遷移元から取得した値をセット
        PagerParlanceFragment.setData( _parlance.get("name"), _parlance.get("explanation") );

//        _tvName.setText( _parlance.get("name") );
//        _tvExplanation.setText( _parlance.get("explanation") );
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

        //表示されたままのメニューアイコンを非表示に
        menu.findItem(R.id.menuInsert).setVisible(false);
        menu.findItem(R.id.menuReload).setVisible(false);

        //削除ボタンを表示
        MenuItem delete = menu.findItem(R.id.menuDelete);
        delete.setVisible(true);

        //編集ボタンを表示
        MenuItem edit = menu.findItem(R.id.menuEdit);
        edit.setVisible(true);

        return true;
    }

    /**
     * オプションメニュー選択時処理
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            //編集ボタン
            case R.id.menuEdit:
                onEditButtonClick();
                break;
            //削除ボタン
            case R.id.menuDelete:
                onCharacterDeleteButtonClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * NavigationView
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent();
        int itemId = item.getItemId();
        switch (itemId) {
            //プロット一覧へ戻る
            case R.id.menuBack:
                intent = new Intent(getApplication(), PlotListActivity.class);
                break;
            //概要画面
            case R.id.menuOutline:
                intent = new Intent(ParlanceActivity.this, OutlineActivity.class);
                break;
            //登場人物一覧画面へ
            case R.id.menuCharacter:
                intent = new Intent(ParlanceActivity.this, CharacterListActivity.class);
                break;
            //世界観一覧画面へ
            case R.id.menuWorld:
                intent = new Intent(ParlanceActivity.this, WorldViewListActivity.class);
                break;
            //構想画面へ
            case R.id.menuIdea:
                intent = new Intent(ParlanceActivity.this, IdeaActivity.class);
                break;
            //メモ画面へ
            case R.id.menuMemo:
                intent = new Intent(ParlanceActivity.this, MemoListActivity.class);
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
     * 編集ボタンの処理
     */
    private void onEditButtonClick() {
        Intent intent = new Intent(ParlanceActivity.this, ParlanceEditActivity.class);
        intent.putExtra("PARLANCE", _parlance);
        intent.putExtra("OUTLINE", _outline);
        intent.putExtra("ACTIVITY", NOW_ACTIVITY);
        startActivity(intent);
    }

    /**
     * 削除ボタン押下時の処理
     */
    private void onCharacterDeleteButtonClick() {
        Bundle extras = new Bundle();
        extras.putString("no", _parlance.get("no"));
        extras.putString("table", "parlances");
        extras.putString("msg", getString( R.string.dialog_parlance_delete_msg, _parlance.get("name") ));

        Context context = this;
        DeleteConfirmDialogCreate.setActivityContext(context);

        DeleteConfirmDialogCreate dialog = new DeleteConfirmDialogCreate();
        dialog.setArguments(extras);

        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, "ParlanceActivity");
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
        dialog.show(manager, "ParlanceActivity");
    }
}
