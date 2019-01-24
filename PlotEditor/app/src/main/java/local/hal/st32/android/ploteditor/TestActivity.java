package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
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

/**
 * 就職作品
 *
 * テスト動作用アクティビティクラス
 *
 * 参考
 * （動的にView追加）
 * 　http://www.cliph.net/wordpress/archives/793
 * 　https://www.javadrive.jp/android/linearlayout/index3.html
 *
 * （折りたたみListView）
 * 　https://qiita.com/yu_naka0607/items/143da026fb19b2c8e46d
 *
 * @author ohs60224
 */
public class TestActivity extends AppCompatActivity {

    private final String KEY1 = "TITLE";
    private final String KEY2 = "SUMMARY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        int PARENT_DATA = 3;

        //親ノードのリスト
        List<Map<String, String>> parentList = new ArrayList<Map<String, String>>();

        //親ノードに表示する内容を生成
        for(int i = 0; i < PARENT_DATA; i++) {
            Map<String, String> parentData = new HashMap<String, String>();
            parentData.put(KEY1, "タイトル" + Integer.toString(i));

            //親ノードのリストに内容を格納
            parentList.add(parentData);
        }

        int CHILD_DATA = 3;

        //全体の子ノードのリスト
        List<List<Map<String, String>>> allChildList = new ArrayList<List<Map<String, String>>>();

        //子要素として表示する文字を生成
        for(int i = 0; i < PARENT_DATA; i++) {
            //子要素全体用のリスト
            List<Map<String, String>> childList = new ArrayList<>();

            //各子ノード用データ格納
            for(int j = 0; j < CHILD_DATA; j++) {
                Map<String, String> childData = new HashMap<>();
                childData.put(KEY1, "子要素" + Integer.toString(j));
                childData.put(KEY2, "概要" + Integer.toString(j));

                //子ノードのリストに文字を格納
                childList.add(childData);
            }
            //全体の子ノードリストに各小ノードリストのデータを格納
            allChildList.add(childList);
         }

         //アダプタを作る
        final SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                parentList,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { KEY1 },
                new int[] {android.R.id.text1, android.R.id.text2},
                allChildList,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { KEY1, KEY2 },
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        //生成した情報をセット
        ExpandableListView lv = findViewById(R.id.expandableListView1);
        lv.setAdapter(adapter);

        //リスト項目がクリックされたときの処理
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //クリックされた場所の内容情報を取得
                Map<String, String> item = (Map<String, String>) adapter.getChild(groupPosition, childPosition);

                return false;
            }
        });

        //グループの親項目がクリックされたときの処理
        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                ExpandableListAdapter adapter = parent.getExpandableListAdapter();

                //クリックされた場所の内容情報を取得
                Map<String, String> item = (Map<String, String>)adapter.getGroup(groupPosition);


                return false;
            }
        });
    }
}
