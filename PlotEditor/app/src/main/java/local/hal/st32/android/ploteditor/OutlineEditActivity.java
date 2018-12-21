package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BaseTransientBottomBar;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * 概要編集画面用アクティビティクラス
 *
 * @author ohs60224
 */
public class OutlineEditActivity extends AppCompatActivity {
    /**
     * 現在表示している画面に対応したアクティビティ
     */
    public static final String NOW_ACTIVITY = "OutlineEditActivity";
    /**
     * 新規登録後か否かを表す定数
     */
    private int _mode = PlotListActivity.MODE_AGAIN;
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
    private EditText _etTitle;
    /**
     * キャッチコピー
     */
    private EditText _etSlogan;
    /**
     * あらすじ
     */
    private EditText _etSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outline_edit);

        //画面部品取得
        _etTitle = findViewById(R.id.etTitle);
        _etSlogan = findViewById(R.id.etSlogan);
        _etSummary = findViewById(R.id.etSummary);

        //アクションバーに前画面へ戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        _intent = getIntent();
        _map = (HashMap<String, String>) _intent.getSerializableExtra("PLOT");
        _no = _map.get("no");
        _etTitle.setText(_map.get("title") );
        _etSlogan.setText( _map.get("slogan") );
        _etSummary.setText(_map.get("summary") );
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("概要");
        Log.e("*****", "確認地点A");
    }

    /**
     * オプションメニュー作成
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        //表示されたままのメニューアイコンを非表示に
        menu.findItem(R.id.menuEdit).setVisible(false);

        //保存ボタンを取得
        MenuItem save = menu.findItem(R.id.menuSave);
        save.setVisible(true);

        return true;
    }

    /**
     * オプションメニュー選択時処理
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            //保存ボタン
            case R.id.menuSave:
                onSaveButtonClick();
                break;
            //戻るボタン
            case android.R.id.home:
                onBackButtonClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 戻るボタン押下時の処理
     */
    private void onBackButtonClick() {
        String etTitle = _etTitle.getText().toString();
        String etSlogan = _etSlogan.getText().toString();
        String etSummary = _etSummary.getText().toString();

        //変更されてた場合
        if(( !etTitle.equals( _map.get("title") ) || !etSlogan.equals( _map.get("slogan") ) || !etSummary.equals( _map.get("summary") ) )) {
            ReturnConfirmDialogCreate dialog = new ReturnConfirmDialogCreate();
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "OutlineEditActivity");
        }
        else {
            finish();
        }
    }

    /**
     * 保存ボタン押下時の処理
     */
    private void onSaveButtonClick() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        PlotJsonAccess access = new PlotJsonAccess();
        access.setOnCallBack(new PlotJsonAccess.CallBackTask() {
            @Override
            public void CallBack(HashMap<String, String> map) {
                _map = map;

                Intent intent = new Intent(OutlineEditActivity.this, OutlineActivity.class);
                intent.putExtra("MODE", _mode);
                intent.putExtra("PLOT", _map);
                startActivity(intent);
                OutlineEditActivity.this.finish();
            }
        });
        access.execute(_no, _etTitle.getText().toString(), _etSlogan.getText().toString(), _etSummary.getText().toString(), NOW_ACTIVITY);
    }
}
