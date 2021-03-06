package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

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
//    public static final String NOW_ACTIVITY = "OutlineEditActivity";
    private static final String NOW_ACTIVITY = new NowActivity().getOutlineActivity();
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
    private HashMap<String, String> _outline = new HashMap<>();
    /**
     * インテント
     */
    private Intent _intent;
    /**
     * レイアウト
     */
    private Layout _layout;
    /**
     * 画面部品
     */
    private TextView _tvTitle;
    private TextView _tvSlogan;
    private TextView _tvSummary;
    /**
     * tvTitleの高さ（24spで二行になると163）
     */
    private final int TITLE_LINE = 163;
    /**
     * DrawerLayoutとActionBarDrawerToggle
     */
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar _toolbar;
    /**
     * NavigationViewのヘッダー部分のTextView
     */
    private TextView _tvMenuTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outline);

        //画面部品取得
        _tvTitle = findViewById(R.id.tvTitle);
        _tvSlogan = findViewById(R.id.tvSlogan);
        _tvSummary = findViewById(R.id.tvSummary);

        //NavigationViewのヘッダー部分のTextViewを取得（コメント化した部分を使用すると、NavigationView内の画面部品へのアクセスができない（文字変更とかができない））
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View drawerHeader = inflater.inflate(R.layout.drawer_header, null);
        NavigationView nvLeftView = findViewById(R.id.nvLeftView);
        View drawerHeader = nvLeftView.getHeaderView(0);
        _tvMenuTitle = drawerHeader.findViewById(R.id.tvMenuTitle); //作品タイトル

        //Toolbar
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        //DrawerLayout
        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(OutlineActivity.this, mDrawer, _toolbar, R.string.drawable_open, R.string.drawable_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //NavigationViewのリスナー
        nvLeftView.setNavigationItemSelectedListener(this);

        _intent = getIntent();
        _mode = _intent.getIntExtra("MODE", PlotListActivity.MODE_FIRST);
        _outline = (HashMap<String, String>) _intent.getSerializableExtra("OUTLINE");
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("概要");

        _no = _outline.get("no");
        _tvTitle.setText(_outline.get("title") );

        //画面部品が表示されるタイミングで動作
        ViewTreeObserver observer = _tvTitle.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //二行になっていたら文字サイズを18spに
                if( TITLE_LINE == _tvTitle.getHeight() ) {
                    _tvTitle.setTextSize(18.0f);
                }
            }
        });
        _tvMenuTitle.setText(_outline.get("title"));

        //新規登録後、またはデータベースに値が登録されてなかった場合にはデフォルトの値をセット
        if("".equals( _outline.get("slogan") )) {
            _tvSlogan.setText( getString(R.string.tv_slogan_text) );
        }
        else {
            _tvSlogan.setText( _outline.get("slogan") );
        }

        //新規登録後、またはデータベースに値が登録されてなかった場合にはデフォルトの値をセット
        if("".equals( _outline.get("summary") )) {
            _tvSummary.setText( getString(R.string.tv_summary_text) );
        }
        else {
            _tvSummary.setText(_outline.get("summary") );
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
        menu.setGroupVisible(R.id.mgEdit, false);

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
            //プロット一覧へ戻る
            case R.id.menuBack:
                intent = new Intent(OutlineActivity.this, PlotListActivity.class);
                break;
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
            case R.id.menuIdea:
                intent = new Intent(OutlineActivity.this, IdeaActivity.class);
                break;
            //メモ画面へ
            case R.id.menuMemo:
                intent = new Intent(OutlineActivity.this, MemoListActivity.class);
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
     * 編集ボタン押下時の処理
     */
    private void onEditButtonClick() {
        Intent intent = new Intent(OutlineActivity.this, OutlineEditActivity.class);
        intent.putExtra("OUTLINE", _outline);
        startActivity(intent);
        //OutlineActivity.this.finish();
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
        dialog.show(manager, "OutlineActivity");
    }
}
