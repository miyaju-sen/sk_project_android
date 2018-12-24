package local.hal.st32.android.ploteditor;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
public class CharacterEditActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    /**
     * 画面部品
     */
    private EditText _etName;
    private EditText _etPhonetic;
    private EditText _etAnotherName;
    private RadioGroup _rgAge;
    private EditText _etAge;
    private RadioGroup _rgGender;
    private Spinner _spMonth;
    private Spinner _spDay;
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
     * 誕生月のアダプタ
     */
    private ArrayAdapter<String> _monthAdapter;
    /**
     * 誕生日のアダプタ
     */
    private ArrayAdapter<String> _dayAdapter;
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
    int ageCheckedId;
    int genderCheckedId;

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
        _etAge = findViewById(R.id.etAge);
        _rgGender = findViewById(R.id.rgGender);
        _spMonth = findViewById(R.id.spMonth);
        _spDay = findViewById(R.id.spDay);
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

        //スピナーに値をセット
        spinnerAdapterSet();

        //ラジオグループにリスナーをセット
        _rgAge.setOnCheckedChangeListener(this);
        _rgGender.setOnCheckedChangeListener(this);

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
     * ラジオボタン選択時処理
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //選択されているRadioButtonのIDを取得（どれも選択されていない場合：-1）
        ageCheckedId = _rgAge.getCheckedRadioButtonId();
        genderCheckedId = _rgGender.getCheckedRadioButtonId();

        if (-1 != ageCheckedId) {
            Toast.makeText(this, ((RadioButton)findViewById(checkedId)).getText() + "が選択されています", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 戻るボタン押下時の処理
     */
    private void onBackButtonClick() {
        //TODO:変更された場合の処理
        Log.e("ラジオボタン", "年齢：" + ageCheckedId + "性別：" + genderCheckedId);
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
        //TODO:性別（現在サーブレット側で固定値にしてる）
        String birthday = _spMonth.getSelectedItem().toString() + _spDay.getSelectedItem().toString();
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
                birthday, //誕生日
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

    /**
     * 誕生日のスピナーに値をセットするメソッド
     */
    private void spinnerAdapterSet() {
        //アダプタの作成
        //月
        _monthAdapter = new ArrayAdapter<String>(CharacterEditActivity.this, android.R.layout.simple_spinner_item);
        _monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //日
        _dayAdapter = new ArrayAdapter<String>(CharacterEditActivity.this, android.R.layout.simple_spinner_item);
        _dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //アダプタにアイテムを追加
        //月
        for(int i = 1; i <= 12; i++) {
            _monthAdapter.add(i + "月");
        }
        //日
        for(int i = 1; i <= 31; i++) {
            _dayAdapter.add(i + "日");
        }

        _spMonth.setAdapter(_monthAdapter);
        _spDay.setAdapter(_dayAdapter);
    }
}
