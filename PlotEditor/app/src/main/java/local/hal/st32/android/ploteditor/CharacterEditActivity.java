package local.hal.st32.android.ploteditor;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.HashMap;

/**
 * 就職作品
 *
 * 登場人物追加・編集画面用アクティビティクラス
 * ・新規追加の場合：一覧画面から
 * ・編集の場合：情報画面から
 *
 * @author ohs60224
 */
public class CharacterEditActivity extends AppCompatActivity {
    /**
     * 画面部品
     */
    private EditText _etName;
    private EditText _etPhonetic;
    private EditText _etAnotherName;
    private RadioGroup _rgAge;
    private RadioGroup _rgGender;
    private EditText _etHeight;
    private EditText _etWeight;
    private EditText _etFirstPerson;
    private EditText _etSecondPerson;
    private EditText _etBelongs;
    private EditText _etSkill;
    private EditText _etProfile;
    private EditText _etLivedProcess;
    private EditText _etPersonality;
    private EditText _etAppearance;
    private EditText _etOther;
    /**
     * 画像パス
     */
    private String _imagePath;
    /**
     * インテント
     */
    private Intent _intent;
    /**
     * 作品No
     */
    private String _plot;
    /**
     * 遷移元のアクティビティ（デフォルト：一覧画面）
     */
    private String ACTIVITY = new NowActivity().getCharacterListActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_edit);

        //アクションバーに前画面へ戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //TODO:誕生日と年齢のスピナー

        //画面部品取得
        _etName = findViewById(R.id.etName);
        _etPhonetic = findViewById(R.id.etPhonetic);
        _etAnotherName = findViewById(R.id.etAnotherName);
        _rgAge = findViewById(R.id.rgAge);
        _rgGender = findViewById(R.id.rgGender);
        _etHeight = findViewById(R.id.etHeight);
        _etWeight = findViewById(R.id.etWeight);
        _etFirstPerson = findViewById(R.id.etFirstPerson);
        _etSecondPerson = findViewById(R.id.etSecondPerson);
        _etBelongs = findViewById(R.id.etBelongs);
        _etSkill = findViewById(R.id.etSkill);
        _etProfile = findViewById(R.id.etProfile);
        _etLivedProcess = findViewById(R.id.etLivedProcess);
        _etPersonality = findViewById(R.id.etPersonality);
        _etAppearance = findViewById(R.id.etAppearance);
        _etOther = findViewById(R.id.etOther);

        _intent = getIntent();
        _plot = _intent.getStringExtra("PLOTNo"); //作品No
    }

    @Override
    public void onResume() {
        super.onResume();
        ACTIVITY = _intent.getStringExtra("ACTIVITY");
        if (ACTIVITY.equals(new NowActivity().getCharacterListActivity())) {
            setTitle("登場人物 新規登録");
        }
        else {
            setTitle("登場人物 編集");

            //TODO:編集する登場人物の情報を取得
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
        //TODO:変更された場合の処理
        finish();
    }

    /**
     * 保存ボタン押下時の処理
     */
    private void onSaveButtonClick() {
        String no = ""; //TODO:新規追加の場合は空
        String phonetic = _etPhonetic.getText().toString();
        String name = _etName.getText().toString();
        String another = _etAnotherName.getText().toString();
        //TODO:画像パス
        //TODO:年齢
        //TODO:性別
        //TODO:誕生日
        String height = _etHeight.getText().toString();
        String weight = _etWeight.getText().toString();
        String firstPerson = _etFirstPerson.getText().toString();
        String secondPerson = _etSecondPerson.getText().toString();
        String belongs = _etBelongs.getText().toString();
        String skill = _etSkill.getText().toString();
        String profile = _etProfile.getText().toString();
        String livedProcess = _etLivedProcess.getText().toString();
        String personality = _etPersonality.getText().toString();
        String appearance = _etAppearance.getText().toString();
        String other = _etOther.getText().toString();

        CharacterJsonAccess access = new CharacterJsonAccess();
        access.setOnCallBack(new CharacterJsonAccess.CallBackTask() {
            @Override
            public void CallBack(HashMap<String, String> map) {
                //TODO:遷移先にデータ送信
//                _intent = new Intent (CharacterEditActivity.this, CharacterActivity.class);
//                startActivity(_intent);
//                CharacterEditActivity.this.finish();
            }
        });
        access.execute(
                no, //主キー
                _plot, //作品No
                phonetic, //フリガナ
                name, //名前
                another, //別名
                "", //画像パス
                "", //年齢
                "", //性別
                "", //誕生日
                height, //身長
                weight, //体重
                firstPerson, //一人称
                secondPerson, //二人称
                belongs, //所属
                skill, //能力
                profile, //紹介文
                livedProcess, //生い立ち
                personality, //性格
                appearance, //容姿
                other //その他
        );
    }
}
