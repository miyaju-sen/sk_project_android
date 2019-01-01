package local.hal.st32.android.ploteditor;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
public class ParlanceActivity extends AppCompatActivity {
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
    private TextView _tvMenuBack;
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

        
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("設定・用語");
    }
}
