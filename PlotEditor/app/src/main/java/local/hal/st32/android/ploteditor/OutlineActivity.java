package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

/**
 * 就職作品
 *
 * 概要確認画面用アクティビティクラス
 *
 * @author ohs60224
 */
public class OutlineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * 現在表示している画面に対応したアクティビティ
     */
    public static final String NOW_ACTIVITY = "OutlineEditActivity";
    /**
     * 新規登録後か否かを表す定数
     */
    private int _mode = PlotListActivity.MODE_FIRST;
    /**
     * 現在表示しているプロットの、データベース上での主キー値（更新モード時）
     */
    private String _no = "";
    /**
     * プロット情報が格納された配列
     */
    private HashMap<String, String> _map = new HashMap<>();
    /**
     * インテント
     */
    private Intent _intent;
    /**
     * レイアウト
     */
    private Layout _layout;
    /**
     * 作品タイトル
     */
    private TextView _tvTitle;
    /**
     * キャッチコピー
     */
    private TextView _tvSlogan;
    /**
     * あらすじ
     */
    private TextView _tvSummary;
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
        setContentView(R.layout.activity_outline);

        //画面部品取得
        _tvTitle = findViewById(R.id.tvTitle);
        _tvSlogan = findViewById(R.id.tvSlogan);
        _tvSummary = findViewById(R.id.tvSummary);

        //NavigationViewのヘッダー部分のTextViewを取得
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View drawerHeader = inflater.inflate(R.layout.drawer_header, null);
        _tvMenuBack = drawerHeader.findViewById(R.id.tvMenuBack);

        //Toolbar
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        //DrawerLayout
        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(OutlineActivity.this, mDrawer, _toolbar, R.string.drawable_open, R.string.drawable_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //NavigationViewのリスナー
        NavigationView nvLeftView = findViewById(R.id.nvLeftView);
        nvLeftView.setNavigationItemSelectedListener(this);

        _intent = getIntent();
        _mode = _intent.getIntExtra("MODE", PlotListActivity.MODE_FIRST);
        _map = (HashMap<String, String>) _intent.getSerializableExtra("PLOT");
        _no = _map.get("no");
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("概要");
        Log.e("*****", "確認地点A");

        _no = _map.get("no");
        _tvTitle.setText(_map.get("title") );

        //新規登録後、またはデータベースに値が登録されてなかった場合にはデフォルトの値をセット
        if(_mode == PlotListActivity.MODE_FIRST || "".equals( _map.get("slogan") )) {
            Log.e("*****", "確認地点");
            _tvSlogan.setText( getString(R.string.tv_slogan_text) );
        }
        else {
            _tvSlogan.setText( _map.get("slogan") );
        }

        //新規登録後、またはデータベースに値が登録されてなかった場合にはデフォルトの値をセット
        if(_mode == PlotListActivity.MODE_FIRST || "".equals( _map.get("summary") )) {
            _tvSummary.setText( getString(R.string.tv_summary_text) );
        }
        else {
            _tvSummary.setText(_map.get("summary") );
        }
    }

    /**
     * オプションメニュー作成
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        //表示されたままのメニューアイコンを非表示に
        menu.findItem(R.id.menuInsert).setVisible(false);
        menu.findItem(R.id.menuReload).setVisible(false);

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
            //概要画面（何もしない）
            case R.id.menuOutline:
                intent = null;
                break;
            //登場人物一覧画面へ
            case R.id.menuCharacter:
                intent = new Intent(OutlineActivity.this, CharacterListActivity.class);
                break;
            //世界観一覧画面へ
            case R.id.menuWorld:
                intent = new Intent(OutlineActivity.this, WorldViewListActivity.class);
                break;
            //構想画面へ
            case R.id.menuStory:
                intent = new Intent(OutlineActivity.this, StoryEditActivity.class);
                break;
            //メモ画面へ
            case R.id.menuMemo:
                intent = new Intent(OutlineActivity.this, MemoListActivity.class);
                break;
            //削除
            case R.id.menuDelete:
                intent = null;
                onDeleteButtonClick();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);

        if(null != intent) {
            startActivity(intent);
        }
        return true;
    }

    /**
     * 編集ボタン押下時の処理
     */
    private void onEditButtonClick() {
        Intent intent = new Intent(OutlineActivity.this, OutlineEditActivity.class);
        intent.putExtra("PLOT", _map);
        startActivity(intent);
        OutlineActivity.this.finish();
    }

    /**
     * 削除ボタン押下時の処理
     */
    private void onDeleteButtonClick() {
        String no = _map.get("no");
        String title = _map.get("title");

        Bundle extras = new Bundle();
        extras.putString("no", no);
        extras.putString("title", title);

        Context context = this;
        PlotDeleteConfirmDialogCreate.setActivityContext(context);

        PlotDeleteConfirmDialogCreate dialog = new PlotDeleteConfirmDialogCreate();
        dialog.setArguments(extras);

        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, "OutlineActivity");
    }

    /**
     * 「プロット一覧に戻る」押下時の処理
     */
    public void onMenuBackClick(View view) {
        finish();
    }

}
