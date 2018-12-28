package local.hal.st32.android.ploteditor;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URLDecoder;
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
    private ImageView _ivCharacterImage;
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
     * ファイル選択のボタン
     */
    private Button _btImageSelect;
    /**
     * 画像を取得する際の結果コード（任意）
     */
    private static final int RESULT_PICK_IMAGEFILE = 1000;
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
     * 画像取得処理用のインテント
     */
    private Intent _imageIntent;
    /**
     * 作品No
     */
    private String _plot;
    /**
     * プロット概要が格納された配列
     */
    private HashMap<String, String> _outline = new HashMap<>();
    /**
     * 遷移元のアクティビティ（デフォルト：情報画面）
     */
    private String ACTIVITY = new NowActivity().getCharacterActivity();
    /**
     * 年齢
     */
    private String _age;
    /**
     * 性別のタグ
     */
    private String _genderTag;
    /**
     * JSON解析した登場人物の情報を格納する配列
     */
    private HashMap<String, String> _character = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_edit);

        //アクションバーに前画面へ戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //画面部品取得
        _ivCharacterImage = findViewById(R.id.ivCharacterImage);
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
        _btImageSelect = findViewById(R.id.btImageSelect);

        //スピナーに値をセット
        spinnerAdapterSet();

        //ラジオグループにリスナーをセット
        _rgAge.setOnCheckedChangeListener(this);
        _rgGender.setOnCheckedChangeListener(this);

        _intent = getIntent();
//        _plot = _intent.getStringExtra("PLOTNo"); //作品No
        _outline = (HashMap<String, String>) _intent.getSerializableExtra("OUTLINE");
        _plot = _outline.get("no");
    }

    @Override
    public void onResume() {
        super.onResume();
        ACTIVITY = _intent.getStringExtra("ACTIVITY");
        //リスト画面から遷移してきた場合：新規登録
        if (ACTIVITY.equals(new NowActivity().getCharacterListActivity())) {
            setTitle("登場人物 新規登録");
        }
        //情報画面から遷移してきた場合：編集
        else {
            setTitle("登場人物 編集");
            //TODO:編集する登場人物の情報を取得
            setCharacterInfo();
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
        //TODO:ここのメソッド、編集モード時に使うかも
        //選択されたラジオボタンのタグを取得
        RadioButton rb = findViewById(checkedId);
        String tagStr = rb.getTag().toString();
        int tag = Integer.parseInt(tagStr);
//
//        //年齢（____歳）
//        if(10 == tag) {
//            _age = _etAge.getText().toString();
//        }
//        //年齢（不明）
//        else if(20 == tag) {
//            _age = rb.getText().toString();
//        }
//        //性別
//        else if(1 <= tag && tag <= 5) {
//            _genderTag = tag;
//        }
//
//        Toast.makeText(this, "年齢" + _age + "が選択されました", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "性別" + _genderTag + "が選択されました", Toast.LENGTH_SHORT).show();
    }

    /**
     * ラジオボタンで選択されている項目を取得するメソッド
     */
    private void checkedRadioButton() {
        //年齢
        int rbAgeId = _rgAge.getCheckedRadioButtonId();
        RadioButton rbAge = findViewById(rbAgeId);
        String tagStr = rbAge.getTag().toString();
        int tag = Integer.parseInt(tagStr);
        //____歳
        if(10 == tag) {
            _age = _etAge.getText().toString();
        }
        //不明
        else if(20 == tag) {
            _age = rbAge.getText().toString();
        }

        //性別
        int rbGenderId = _rgGender.getCheckedRadioButtonId();
        RadioButton rbGender = findViewById(rbGenderId);
        _genderTag = rbGender.getTag().toString();
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
        //ラジオボタンの項目を取得
        checkedRadioButton();

        //新規登録の場合主キーは空、編集の場合は_characterから値を取得する
        String no = "";
        if(ACTIVITY.equals(new NowActivity().getCharacterActivity())) {
            no = _character.get("no");
        }
        String phonetic = _etPhonetic.getText().toString();
        String name = _etName.getText().toString();
        String another = _etAnotherName.getText().toString();
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
                //TODO:編集モードか新規モードかの判別をする値を送信
                _character = map;

                _intent = new Intent (CharacterEditActivity.this, CharacterActivity.class);
                _intent.putExtra("CHARACTER", _character);
                _intent.putExtra("OUTLINE", _outline);
                startActivity(_intent);
                CharacterEditActivity.this.finish();
            }
        });
        access.execute(
                no, //主キー
                _plot, //作品No
                phonetic, //フリガナ
                name, //名前
                another, //別名
                _imagePath, //画像パス
                _age, //年齢
                _genderTag, //性別
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
     * ファイル選択ボタン押下時の処理
     *
     * @param view
     */
    public void onImageSelectButtonClick(View view) {
        //ドキュメントを開くインテント
        _imageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        _imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
        _imageIntent.setType("image/*");

        //ドキュメントを開く
        startActivityForResult(_imageIntent, RESULT_PICK_IMAGEFILE);
    }

    /**
     * startActivityForResult終了後に呼ばれる
     * resultData(画像パス)からBitmapを作成してImageViewにセット
     *
     * @param requestCode 呼び出し時のID(startActivityForResultの第二引数)
     * @param resultCode 結果コード
     * @param resultData 画像パス
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if(requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK) {
            Uri uri = null;
            if(resultData != null) {
                uri = resultData.getData();
                //画像ファイル名を取得（これをサーバへ送信）
                _imagePath = GetFileName.getFileNameFromUri(this, uri);

                try {
                    Bitmap bmp = getBitmapFromUri(uri);
                    _ivCharacterImage.setImageBitmap(bmp);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 受け取ったUriから画像を取得し、Bitmapを作成するメソッド
     *
     * @param uri
     * @return Bitmap化した画像
     * @throws IOException
     */
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
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

    /**
     * 編集時の場合、画面部品に値をセットするメソッド
     */
    private void setCharacterInfo() {
        _character = (HashMap<String, String>) _intent.getSerializableExtra("CHARACTER");

        //画像
        _imagePath = _character.get("image_path");
        if(!"".equals(_imagePath)) {
            File storage = Environment.getExternalStorageDirectory();
            File file = new File(storage.getAbsolutePath() + "/" + Environment.DIRECTORY_DCIM + "/Camera", _imagePath);
            Bitmap bm = BitmapFactory.decodeFile(file.getPath());
            _ivCharacterImage.setImageBitmap(bm);
        }

        _etName.setText( _character.get("name") );
        _etPhonetic.setText( _character.get("phonetic") );
        _etAnotherName.setText( _character.get("another") );
        _etHeight.setText( _character.get("height") );
        _etWeight.setText( _character.get("weight") );
        _etFirstPerson.setText( _character.get("first_person") );
        _etSecondPerson.setText( _character.get("second_person") );
        _etBelongs.setText( _character.get("belongs") );
        _etSkill.setText( _character.get("skill") );
        _etProfile.setText( _character.get("profile") );
        _etLivedProcess.setText( _character.get("lived_process") );
        _etPersonality.setText( _character.get("personality") );
        _etAppearance.setText( _character.get("appearance") );
        _etOther.setText( _character.get("other") );

        //TODO:性別・誕生日・年齢
    }
}
