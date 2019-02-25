package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
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
import android.widget.TextView;

import java.util.HashMap;

/**
 * 就職作品
 *
 * メモ内容表示画面
 * 登録（編集）後あるいはリスト押下後に遷移してくる
 *
 * @author ohs60224
 */
public class MemoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * 現在表示している画面に対応したアクティビティ
     */
    public static final String NOW_ACTIVITY = new NowActivity().getMemoActivity();
    /**
     * 画面部品
     */
    private TextView mTvMemo;
    /**
     * メモの情報を格納する配列
     */
    private HashMap<String, String> mMemo = new HashMap<>();
    /**
     * プロット概要が格納された配列
     */
    private HashMap<String, String> mOutline = new HashMap<>();
    /**
     * DrawerLayoutとActionBarDrawerToggle
     */
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    /**
     * NavigationViewのヘッダー部分のTextView
     */
    private TextView mTvMenuTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        //画面部品取得
        mTvMemo = findViewById(R.id.tvMemo);

        //NavigationViewのヘッダー部分のTextViewを取得
        NavigationView nvLeftView = findViewById(R.id.nvLeftView);
        View drawerHeader = nvLeftView.getHeaderView(0);
        mTvMenuTitle = drawerHeader.findViewById(R.id.tvMenuTitle); //プロット一覧へ戻る

        //Toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //DrawerLayout
        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(MemoActivity.this, mDrawer, mToolbar, R.string.drawable_open, R.string.drawable_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //NavigationViewのリスナー
        nvLeftView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        mMemo = (HashMap<String, String>) intent.getSerializableExtra("MEMO");
        mOutline = (HashMap<String, String>) intent.getSerializableExtra("OUTLINE");
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("メモ内容");
        mTvMenuTitle.setText( mOutline.get("title") );

        //画面部品に値をセット
        mTvMemo.setText( mMemo.get("note") );
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
                onMemoDeleteButtonClick();
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
                intent = new Intent(getApplication(), OutlineActivity.class);
                break;
            //登場人物一覧画面へ
            case R.id.menuCharacter:
                intent = new Intent(getApplication(), CharacterListActivity.class);
                break;
            //世界観一覧画面へ
            case R.id.menuWorld:
                intent = new Intent(getApplication(), WorldViewListActivity.class);
                break;
            //構想画面へ
            case R.id.menuIdea:
                intent = new Intent(getApplication(), IdeaActivity.class);
                break;
            //メモ画面へ
            case R.id.menuMemo:
                intent = new Intent(getApplication(), MemoListActivity.class);
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
            intent.putExtra("OUTLINE", mOutline);
            startActivity(intent);
        }
        return true;
    }

    /**
     * 編集ボタン押下時の処理
     */
    private void onEditButtonClick() {
        Intent intent = new Intent(getApplication(), MemoEditActivity.class);
        intent.putExtra("MEMO", mMemo);
        intent.putExtra("OUTLINE", mOutline);
        intent.putExtra("ACTIVITY", NOW_ACTIVITY);
        startActivity(intent);
        MemoActivity.this.finish();
    }

    /**
     * 削除ボタン押下時の処理
     */
    private void onMemoDeleteButtonClick() {

        Bundle extras = new Bundle();
        extras.putString("no", mMemo.get("no"));

        Context context = this;
        MemoDeleteConfirmDialogCreate.setActivityContext(context);

        MemoDeleteConfirmDialogCreate dialog = new MemoDeleteConfirmDialogCreate();
        dialog.setArguments(extras);

        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, "MemoActivity");
    }

    /**
     * プロット削除押下時の処理
     */
    private void onPlotDeleteClick() {
        Bundle extras = new Bundle();
        extras.putString("no", mOutline.get("no"));
        extras.putString("table", "memos");
        extras.putString("msg", getString(R.string.dialog_memo_delete_msg));

        Context context = this;
        DeleteConfirmDialogCreate.setActivityContext(context);

        DeleteConfirmDialogCreate dialog = new DeleteConfirmDialogCreate();
        dialog.setArguments(extras);

        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, "MemoActivity");
    }
}
