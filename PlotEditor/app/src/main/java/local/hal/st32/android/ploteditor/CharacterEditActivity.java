package local.hal.st32.android.ploteditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * 就職作品
 *
 * 登場人物追加・編集画面用アクティビティクラス
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_edit);

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
}
