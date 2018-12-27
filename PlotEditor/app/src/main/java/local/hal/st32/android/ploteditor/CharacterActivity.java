package local.hal.st32.android.ploteditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * 就職作品
 *
 * 各登場人物の情報画面用のアクティビティクラス
 * 登録・編集後、リスト押下後に遷移してくる
 *
 * @author ohs60224
 */
public class CharacterActivity extends AppCompatActivity {
    /**
     * 画面部品取得
     */
    private ImageView _ivCharacterIcon;
    private TextView _tvName;
    private TextView _tvPhonetic;
    private TextView _tvAnother;
    private TextView _tvProfile;
    private TextView _tvAge;
    private TextView _tvFirstPerson;
    /**
     * インテント
     */
    private Intent _intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        Intent intent = getIntent();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
