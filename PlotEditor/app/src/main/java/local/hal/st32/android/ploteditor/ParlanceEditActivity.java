package local.hal.st32.android.ploteditor;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
     * プロット概要が格納された配列
     */
    private HashMap<String, String> _outline = new HashMap<>();
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
        setContentView(R.layout.activity_world_view_edit);

        //アクションバーに前画面へ戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
