package local.hal.st32.android.ploteditor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

/**
 * 就職作品
 *
 * プロット一覧画面用アクティビティクラス
 *
 * @author ohs60224
 */
public class PlotListActivity extends AppCompatActivity {
    /**
     * 新規登録後からの遷移を表す定数
     */
    static final int MODE_FIRST = 1;

    /**
     * リスト押下からの遷移を表す定数
     */
    static final int MODE_AGAIN = 2;
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getPlotJson();
    /**
     * リストビューに表示されるリストデータ
     */
    private List<Map<String, String>> _list;
    /**
     * リストビュー
     */
    private ListView _lvPlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_list);

        //PlotJsonAccessへコンテキストを送信
        Context context = PlotListActivity.this;
        PlotJsonAccess access = new PlotJsonAccess();
        access.setContext(context);

        //リストビュー取得・リスナー設定
        _lvPlots = findViewById(R.id.lvPlots);
        _lvPlots.setOnItemClickListener(new ListItemClickListener());

        //Toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("プロット一覧");

        PlotJsonReceiver receiver = new PlotJsonReceiver();
        receiver.execute(ACCESS_URL);
    }

    /**
     * オプションメニュー作成
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        //新規追加ボタンを表示
        MenuItem insert = menu.findItem(R.id.menuInsert);
        insert.setVisible(true);

        //更新ボタンを表示
        MenuItem reload = menu.findItem(R.id.menuReload);
        reload.setVisible(true);
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
                TitleSetDialogCreate dialog = new TitleSetDialogCreate();
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "TitleSetDialogFragment");
                break;
            case R.id.menuReload:
                onResume();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ダイアログの「作成」が押下されたときの処理
     */
    public void onPositiveButtonClick(Context context) {
        //JSONデータを解析し取得したレコードを遷移先に送信
        Intent intent = new Intent(context, OutlineActivity.class);
        HashMap<String, String> plot = new HashMap<>( PlotJsonAccess.getPlot() );
        intent.putExtra("MODE", MODE_FIRST);
        intent.putExtra("PLOT", plot);

        startActivity(intent);
    }

    /**
     * リストがクリックされたときのリスナクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(PlotListActivity.this, OutlineActivity.class);
            HashMap<String, String> plot = new HashMap<>();

            Map<String, String> item = _list.get(position);
            plot.put("no", item.get("no"));
            plot.put("title", item.get("title"));
            plot.put("slogan", item.get("slogan"));
            plot.put("summary", item.get("summary"));

            intent.putExtra("MODE", MODE_AGAIN);
            intent.putExtra("PLOT", plot);

            startActivity(intent);
        }
    }

    /**
     * 非同期でPlotJsonServletと通信するクラス
     */
    private class PlotJsonReceiver extends AsyncTask<String, Void, List<Map<String, String>>> {
        /**
         * ログに記載するタグ用の文字列
         */
        private static final String DEBUG_TAG = "PlotJsonAccess";
        /**
         * サーバ通信が成功したかどうかのフラグ
         * 成功した場合はtrue、失敗した場合はfalse
         */
        private boolean _success = false;

        @Override
        public List<Map<String, String>> doInBackground(String... params) {
            String urlStr = params[0];

            HttpURLConnection con = null;
            InputStream is = null;
            List<Map<String, String>> result = null;

            try {
                URL url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();

                String jsonStr = is2String(is);
                result = createList(jsonStr);
            }
            catch (MalformedURLException ex) {
                Log.e(DEBUG_TAG, "URL変換失敗", ex);
            }
            catch (IOException ex) {
                Log.e(DEBUG_TAG, "通信失敗", ex);
            }
            catch (JSONException ex) {
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }
            finally {
                if(con != null) {
                    con.disconnect();
                }
                try {
                    if(is != null) {
                        is.close();
                    }
                }
                catch (IOException ex) {
                    Log.e(DEBUG_TAG, "InputStream解析失敗", ex);
                }
            }

            return result;
        }

        @Override
        public void onPostExecute(List<Map<String, String>> list) {
            _list = list;
            String[] from = {"title", "created_at"};
            int[] to = {android.R.id.text1, android.R.id.text2};
            SimpleAdapter adapter = new SimpleAdapter(PlotListActivity.this, list, android.R.layout.simple_list_item_2, from, to);
            _lvPlots.setAdapter(adapter);
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
            String title = "";
            String slogan = "";
            String summary = "";
            String createdAt = "";

            try {
                JSONObject rootJSON = new JSONObject(result);

                //プロット一覧のJSONデータを解析
                Map<String, String> item;
                JSONArray plotArray = rootJSON.getJSONArray("plots");
                for(int i = 0; i < plotArray.length(); i++) {
                    item = new HashMap<>();
                    JSONObject plotNow = plotArray.getJSONObject(i);
                    no = plotNow.getString("no");
                    title = plotNow.getString("title");
                    slogan = plotNow.getString("slogan");
                    summary = plotNow.getString("summary");
                    createdAt = plotNow.getString("created_at");

                    item.put("no", no);
                    item.put("title", title);
                    item.put("slogan", slogan);
                    item.put("summary", summary);
                    item.put("created_at", createdAt);

                    list.add(item);
                }
            }
            catch (JSONException ex) {
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }

            return list;
        }

        /**
         * InputStreamオブジェクトを文字列に変換するメソッド。変換文字コードはUTF-8。
         *
         * @param is 変換対象のInputStreamオブジェクト
         * @return 変換された文字列
         * @throws IOException 変換に失敗したときに発生
         */
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


