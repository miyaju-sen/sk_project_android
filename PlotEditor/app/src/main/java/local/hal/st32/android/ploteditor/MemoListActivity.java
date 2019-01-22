package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
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

        //リストビュー取得・リスナー設定
        mLvMemos = findViewById(R.id.lvMemos);
        mLvMemos.setOnItemClickListener(new ListItemClickListener());

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
        MemoJsonReceiver receiver = new MemoJsonReceiver();
        receiver.execute(mOutline.get("no"));
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

    /**
     * 非同期でMemoJsonServletと通信するクラス
     */
    private class MemoJsonReceiver extends AsyncTask<String, Void, List<Map<String, String>>> {
        /**
         * ログに記載するタグ用の文字列
         */
        private static final String DEBUG_TAG = "MemoJsonAccess";

        @Override
        public List<Map<String, String>> doInBackground(String... params) {
            String urlStr = ACCESS_URL;
            String plot = params[0];

            String data = "plot=" + plot;
            HttpURLConnection con = null;
            InputStream is = null;
            List<Map<String, String>> result = null;

            try {
                URL url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();
                int status = con.getResponseCode();
                if(status != 200) {
                    throw new IOException("ステータスコード:" + status);
                }
                is = con.getInputStream();

                String jsonStr = is2String(is);
                result = createList(jsonStr);
            }
            catch (JSONException ex) {
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }
            catch (MalformedURLException ex) {
                Log.e(DEBUG_TAG, "URL変換失敗", ex);
            }
            catch (IOException ex) {
                Log.e(DEBUG_TAG, "通信失敗", ex);
            }
            finally {
                if (con != null) {
                    con.disconnect();
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException ex) {
                    Log.e(DEBUG_TAG, "InputStream解析失敗", ex);
                }
            }

            return result;
        }

        @Override
        public void onPostExecute(List<Map<String, String>> list) {
            mList = list;

            //TODO:リストビューのレイアウトを考える（このままだとメモ内容全部がリスト画面に表示されることになる）
            //リストビューに表示する要素を設定
            String[] from = {"note"};
            int[] to = {android.R.id.text1};
            SimpleAdapter adapter = new SimpleAdapter(getApplication(), list, android.R.layout.simple_expandable_list_item_2, from, to);
            mLvMemos.setAdapter(adapter);
        }

        /**
         * リストを作成するメソッド
         *
         * @param result JSON文字列
         * @return 作成したリスト
         * @throws JSONException
         */
        public List<Map<String, String>> createList(String result) throws JSONException {
            List<Map<String, String>> list = new ArrayList<>();
            String no = "";
            String plot = "";
            String note = "";

            try {
                JSONObject rootJSON = new JSONObject(result);

                //メモ一覧のJSONデータを解析
                Map<String, String> item;
                JSONArray memoArray = rootJSON.getJSONArray("memos");
                for(int i = 0; i < memoArray.length(); i++) {
                    item = new HashMap<>();
                    JSONObject memoNow = memoArray.getJSONObject(i);
                    no = memoNow.getString("no");
                    plot = memoNow.getString("plot");
                    note = memoNow.getString("note");

                    item.put("no", no);
                    item.put("plot", plot);
                    item.put("note", note);

                    list.add(item);
                }
            }
            catch (JSONException ex) {
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }

            return list;
        }

        private String is2String(InputStream is) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            char[] b = new char[1024];
            int line;
            while(0 <= (line = reader.read(b))) {
                sb.append(b, 0, line);
            }
            return sb.toString();
        }
    }
}
