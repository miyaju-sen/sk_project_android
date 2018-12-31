package local.hal.st32.android.ploteditor;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashMap;

/**
 * 就職作品
 *
 * 世界観の『舞台』を編集する画面用アクティビティ
 *
 * @author ohs60224
 */
public class StageEditActivity extends AppCompatActivity {
    /**
     * 画面部品
     */
    private EditText _etStage;
    /**
     * プロット概要が格納された配列
     */
    private HashMap<String, String> _outline = new HashMap<>();
    /**
     * 舞台情報が格納された配列
     */
    private HashMap<String, String> _stage = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_edit);

        //アクションバーに前画面へ戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        _etStage = findViewById(R.id.etStage);

        Intent intent = getIntent();
        _outline = (HashMap<String, String>) intent.getSerializableExtra("OUTLINE");
        _stage = (HashMap<String, String>) intent.getSerializableExtra("STAGE");
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("舞台情報　編集");
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

        //保存ボタンを取得・表示
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
                //TODO:保存処理
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
        //TODO:変更項目があった場合の処理
        StageEditActivity.this.finish();
    }

    /**
     * 保存ボタン押下時の処理
     */
    private void onSaveButtonClick() {
        StageJsonAccess access = new StageJsonAccess();
        access.setOnCallBack(new StageJsonAccess.CallBackTask() {
            @Override
            public void CallBack(HashMap<String, String> map) {
                _stage = map;
            }
        });
        //TODO:値を送信
        access.execute(_stage.get("no"), _stage.get("plot"), _etStage.getText().toString());
    }
}
