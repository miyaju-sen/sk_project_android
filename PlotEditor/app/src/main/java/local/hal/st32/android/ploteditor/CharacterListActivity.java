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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * 登場人物一覧画面用アクティビティクラス
 *
 * @author ohs60224
 */
public class CharacterListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * 現在表示している画面に対応したアクティビティ
     */
    public static final String NOW_ACTIVITY = new NowActivity().getCharacterListActivity();
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getCharacterJson();
    /**
     * リストビューに表示されるリストデータ
     */
    private List<Map<String, String>> _list;
    /**
     * リストビュー
     */
    private ListView _lvCharacters;
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
        setContentView(R.layout.activity_character_list);

        //概要の内容取得
        Intent intent = getIntent();
        _outline = (HashMap<String, String>) intent.getSerializableExtra("OUTLINE");

        //リストビュー取得・リスナー設定
        _lvCharacters = findViewById(R.id.lvCharacters);
        _lvCharacters.setOnItemClickListener(new ListItemClickListener());

        //NavigationViewのヘッダー部分のTextViewを取得
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View drawerHeader = inflater.inflate(R.layout.drawer_header, null);
        _tvMenuBack = drawerHeader.findViewById(R.id.tvMenuBack); //プロット一覧へ戻る

        //Toolbar
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        //DrawerLayout
        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(CharacterListActivity.this, mDrawer, _toolbar, R.string.drawable_open, R.string.drawable_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //NavigationViewのリスナー
        NavigationView nvLeftView = findViewById(R.id.nvLeftView);
        nvLeftView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("登場人物一覧");

        //TODO:開いてる作品内の登場人物のみ
        CharacterJsonReceiver receiver = new CharacterJsonReceiver();
        receiver.execute(ACCESS_URL, _outline.get("no"));
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
            //概要画面
            case R.id.menuOutline:
                intent = new Intent(CharacterListActivity.this, OutlineActivity.class);
                break;
            //登場人物一覧画面へ（何もしない）
            case R.id.menuCharacter:
                intent = null;
                break;
            //世界観一覧画面へ
            case R.id.menuWorld:
                intent = new Intent(CharacterListActivity.this, WorldViewListActivity.class);
                break;
            //構想画面へ
            case R.id.menuStory:
                intent = new Intent(CharacterListActivity.this, StoryEditActivity.class);
                break;
            //メモ画面へ
            case R.id.menuMemo:
                intent = new Intent(CharacterListActivity.this, MemoListActivity.class);
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
            intent.putExtra("OUTLINE", _outline);
            startActivity(intent);
        }
        return true;
    }

    /**
     * リストがクリックされたときのリスナクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(CharacterListActivity.this, CharacterActivity.class);
            HashMap<String, String> character = new HashMap<>();

            Map<String, String> item = _list.get(position);
            character.put("no", item.get("no"));
            character.put("plot", item.get("plot"));
            character.put("phonetic", item.get("phonetic"));
            character.put("name", item.get("name"));
            character.put("another", item.get("another"));
            character.put("image_path", item.get("image_path"));
            character.put("age", item.get("age"));
            character.put("gender", item.get("gender"));
            character.put("birthday", item.get("birthday"));
            character.put("height", item.get("height"));
            character.put("weight", item.get("weight"));
            character.put("first_person", item.get("first_person"));
            character.put("second_person", item.get("second_person"));
            character.put("belongs", item.get("belongs"));
            character.put("skill", item.get("skill"));
            character.put("profile", item.get("profile"));
            character.put("lived_process", item.get("lived_process"));
            character.put("personality", item.get("personality"));
            character.put("appearance", item.get("appearance"));
            character.put("other", item.get("other"));

            intent.putExtra("CHARACTER", character);
            startActivity(intent);
        }
    }

    /**
     * 追加ボタン押下時の処理
     */
    private void onInsertButtonClick() {
        Intent intent = new Intent(CharacterListActivity.this, CharacterEditActivity.class);
        intent.putExtra("PLOTNo", _outline.get("no"));
        intent.putExtra("ACTIVITY", NOW_ACTIVITY);

        startActivity(intent);
    }

    /**
     * 「プロット一覧に戻る」押下時の処理
     */
    public void onMenuBackClick(View view) {
        Intent intent = new Intent(CharacterListActivity.this, PlotListActivity.class);
        startActivity(intent);
    }

    /**
     * 削除ボタン押下時の処理
     */
    private void onDeleteButtonClick() {
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
        dialog.show(manager, "CharacterListActivity");
    }

    /**
     * 非同期でCharacterJsonServletと通信するクラス
     */
    private class CharacterJsonReceiver extends AsyncTask<String, Void, List<Map<String, String>>> {
        /**
         * ログに記載するタグ用の文字列
         */
        private static final String DEBUG_TAG = "CharacterJsonReceiver";
        /**
         * サーバ通信が成功したかどうかのフラグ
         * 成功した場合はtrue、失敗した場合はfalse
         */
        private boolean _success = false;

        @Override
        public List<Map<String, String>> doInBackground(String... params) {
            String urlStr = params[0];
            String no = params[1];

            String data = "no=" + no;
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
            String[] from = {"name", "profile"};
            int[] to = {R.id.tvCharacterName, R.id.tvProfile};
            SimpleAdapter adapter = new SimpleAdapter(CharacterListActivity.this, list, R.layout.row_character, from, to);
            _lvCharacters.setAdapter(adapter);
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
            String phonetic = "";
            String name = "";
            String another = "";
            String imagePath = "";
            String age = "";
            String gender = "";
            String birthday = "";
            String height = "";
            String weight = "";
            String firstPerson = "";
            String secondPerson = "";
            String belongs = "";
            String skill = "";
            String profile = "";
            String livedProcess = "";
            String personality = "";
            String appearance = "";
            String other = "";

            try {
                JSONObject rootJSON = new JSONObject(result);

                //プロット一覧のJSONデータを解析
                Map<String, String> item;
                JSONArray characterArray = rootJSON.getJSONArray("characters");
                for(int i = 0; i < characterArray.length(); i++) {
                    item = new HashMap<>();
                    JSONObject characterNow = characterArray.getJSONObject(i);
                    no = characterNow.getString("no");
                    plot = characterNow.getString("plot");
                    phonetic = characterNow.getString("phonetic");
                    name = characterNow.getString("name");
                    another = characterNow.getString("another");
                    imagePath = characterNow.getString("image_path");
                    age = characterNow.getString("age");
                    gender = characterNow.getString("gender");
                    birthday = characterNow.getString("birthday");
                    height = characterNow.getString("height");
                    weight = characterNow.getString("weight");
                    firstPerson = characterNow.getString("first_person");
                    secondPerson = characterNow.getString("second_person");
                    belongs = characterNow.getString("belongs");
                    skill = characterNow.getString("skill");
                    profile = characterNow.getString("profile");
                    livedProcess = characterNow.getString("lived_process");
                    personality = characterNow.getString("personality");
                    appearance = characterNow.getString("appearance");
                    other = characterNow.getString("other");

                    item.put("no", no);
                    item.put("plot", plot);
                    item.put("phonetic", phonetic);
                    item.put("name", name);
                    item.put("another", another);
                    item.put("image_path", imagePath);
                    item.put("age", age);
                    item.put("gender", gender);
                    item.put("birthday", birthday);
                    item.put("height", height);
                    item.put("weight", weight);
                    item.put("first_person", firstPerson);
                    item.put("second_person", secondPerson);
                    item.put("belongs", belongs);
                    item.put("skill", skill);
                    item.put("profile", profile);
                    item.put("lived_process", livedProcess);
                    item.put("personality", personality);
                    item.put("appearance", appearance);
                    item.put("other", other);

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
