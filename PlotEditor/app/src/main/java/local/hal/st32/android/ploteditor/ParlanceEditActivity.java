package local.hal.st32.android.ploteditor;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
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
 * 設定・用語の編集画面用アクティビティクラス
 * ・新規登録の場合：一覧画面から
 * ・編集の場合：確認画面から
 *
 * @author ohs60224
 */
public class ParlanceEditActivity extends AppCompatActivity {
    /**
     * 画面部品
     */
    private EditText _etName;
    private EditText _etExplanation;
    /**
     * インテント
     */
    private Intent _intent;
    /**
     * プロット概要が格納された配列
     */
    private HashMap<String, String> _outline = new HashMap<>();
    /**
     * 作品No
     */
    private String _plot;
    /**
     * 遷移元のアクティビティ（デフォルト：確認画面）
     */
    private String ACTIVITY = new NowActivity().getParlanceActivity();
    /**
     * JSON解析した設定・用語の情報を格納する配列
     */
    private HashMap<String, String> _parlance = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parlance_edit);

        //アクションバーに前画面へ戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //画面部品取得
        _etName = findViewById(R.id.etParlanceName);
        _etExplanation = findViewById(R.id.etExplanation);

        _intent = getIntent();
        _outline = (HashMap<String, String>) _intent.getSerializableExtra("OUTLINE");
        _plot = _outline.get("no");
    }

    @Override
    public void onResume() {
        super.onResume();
        ACTIVITY = _intent.getStringExtra("ACTIVITY");
        //一覧画面から遷移してきた場合：新規登録
        if(ACTIVITY.equals(new NowActivity().getWorldViewListActivity())) {
            setTitle("設定・用語 新規登録");
        }
        //確認画面から遷移してきた場合：編集
        else {
            setTitle("設定・用語 編集");

            //編集する情報を取得
            _parlance = (HashMap<String, String>) _intent.getSerializableExtra("PARLANCE");
            _etName.setText( _parlance.get("name") );
            _etExplanation.setText( _parlance.get("explanation") );
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
        String etName = _etName.getText().toString();
        String etExplanation = _etExplanation.getText().toString();
        String name = _parlance.get("name");
        String explanation = _parlance.get("explanation");

        //新規登録時に変更されていた場合
        if(ACTIVITY.equals(new NowActivity().getWorldViewListActivity()) && ( !"".equals(etName) || !"".equals(etExplanation) )) {
            ReturnConfirmDialogCreate dialog = new ReturnConfirmDialogCreate();
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "ParlanceEditActivity-New");
        }
        //編集時に変更されていた場合（横長になるため上のif文と分割）
        else if(ACTIVITY.equals(new NowActivity().getParlanceActivity()) && ( !etName.equals(name) || !etExplanation.equals(explanation) )) {
            ReturnConfirmDialogCreate dialog = new ReturnConfirmDialogCreate();
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "ParlanceEditActivity-Edit");
        }
        else {
            ParlanceEditActivity.this.finish();
        }
    }

    /**
     * 保存ボタン押下時の処理
     */
    private void onSaveButtonClick() {
        //主キー（新規登録の場合は空、編集の場合は__parlanceから値を取得する）
        String no = "";
        if(ACTIVITY.equals(new NowActivity().getParlanceActivity())) {
            no = _parlance.get("no");
        }

        ParlanceJsonAccess access = new ParlanceJsonAccess();
        access.setOnCallBack(new ParlanceJsonAccess.CallBackTask() {
            @Override
            public void CallBack(HashMap<String, String> map) {
                Intent intent = new Intent(ParlanceEditActivity.this, ParlanceActivity.class);
                intent.putExtra("PARLANCE", map);
                intent.putExtra("OUTLINE", _outline);
                startActivity(intent);
                ParlanceEditActivity.this.finish();
            }
        });
        access.execute(no, _outline.get("no"), _etName.getText().toString(), _etExplanation.getText().toString());
    }
}
