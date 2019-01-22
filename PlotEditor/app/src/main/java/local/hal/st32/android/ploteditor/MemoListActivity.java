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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * 現在表示している画面に対応したアクティビティ
     */
    public static final String NOW_ACTIVITY = new NowActivity().getMemoListActivity();
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getMemoJson();
    /**
     * リストビュー
     */
    private ListView mLvMemos;
    /**
     * リストビューに表示されるリストデータ
     */
    private List<Map<String, String>> mList;
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
    private TextView mTvMenuBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        //概要の内容取得
        Intent intent = getIntent();
        mOutline = (HashMap<String, String>) intent.getSerializableExtra("OUTLINE");

        //TODO:リストビュー取得・リスナー設定
        mLvMemos = findViewById(R.id.lvMemos);
//        mLvMemos.setOnClickListener(new ListItemClickListener);

        //NavigationViewのヘッダー部分のTextViewを取得
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View drawerHeader = inflater.inflate(R.layout.drawer_header, null);
        mTvMenuBack = drawerHeader.findViewById(R.id.tvMenuBack); //プロット一覧へ戻る

        //Toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //DrawerLayout
        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(MemoListActivity.this, mDrawer, mToolbar, R.string.drawable_open, R.string.drawable_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //NavigationViewのリスナー
        NavigationView nvLeftView = findViewById(R.id.nvLeftView);
        nvLeftView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("メモ一覧");

        //メモ一覧データを取得
        MemoJsonAccess access = new MemoJsonAccess();
        access.setOnCallBack(new MemoJsonAccess.CallBackTask() {
            @Override
            public void CallBack(List<Map<String, String>> memos) {
                mList = memos;

                //TODO:リストビューのレイアウトを考える（このままだとメモ内容全部がリスト画面に表示されることになる）
                String[] from = {"note"};
                int[] to = {android.R.id.text1};
                SimpleAdapter adapter = new SimpleAdapter(getApplication(), mList, android.R.layout.simple_expandable_list_item_1, from, to);
                mLvMemos.setAdapter(adapter);
            }
        });
        access.execute("", mOutline.get("no"), "");
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

        //更新ボタンを表示
        MenuItem reload = menu.findItem(R.id.menuReload);
        reload.setVisible(true);

        //追加ボタンを表示
        MenuItem insert = menu.findItem(R.id.menuInsert);
        insert.setVisible(true);

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
                onInsertButtonClick();
                break;
            case R.id.menuReload:
                onResume();
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
            //概要画面へ
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
                intent = null;
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
     * 追加ボタン押下時の処理
     */
    private void onInsertButtonClick() {
        Intent intent = new Intent(getApplication(), MemoEditActivity.class);
        intent.putExtra("OUTLINE", mOutline);
        intent.putExtra("ACTIVITY", NOW_ACTIVITY);

        startActivity(intent);
    }

    /**
     * 「プロット一覧に戻る」押下時の処理
     */
    public void onMenuBackClick(View view) {
        Intent intent = new Intent(getApplication(), PlotListActivity.class);
        startActivity(intent);
    }

    /**
     * プロット削除押下時の処理
     */
    private void onPlotDeleteClick() {
        String no = mOutline.get("no");
        String title = mOutline.get("title");

        Bundle extras = new Bundle();
        extras.putString("no", no);
        extras.putString("title", title);

        Context context = this;
        PlotDeleteConfirmDialogCreate.setActivityContext(context);

        PlotDeleteConfirmDialogCreate dialog = new PlotDeleteConfirmDialogCreate();
        dialog.setArguments(extras);

        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, "MemoListActivity");
    }

    /**
     * リスト押下時のリスナクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getApplication(), MemoActivity.class);
            HashMap<String, String> memo = new HashMap<>();

            Map<String, String> item = mList.get(position);
            memo.put("no", item.get("no"));
            memo.put("plot", item.get("plot"));
            memo.put("note", item.get("note"));

            intent.putExtra("MEMO", memo);
            intent.putExtra("OUTLINE", mOutline); //TODO:これいらないのでは
            startActivity(intent);
        }
    }
}
