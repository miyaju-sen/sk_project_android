package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;

/**
 * 就職作品
 *
 * 各登場人物の情報画面用のアクティビティクラス
 * 登録・編集後、リスト押下後に遷移してくる
 *
 * @author ohs60224
 */
public class CharacterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * 画面部品
     */
    private ImageView _ivCharacterIcon;
    private TextView _tvName;
    private TextView _tvPhonetic;
    private TextView _tvAnother;
    private TextView _tvAge;
    private TextView _tvGender;
    private TextView _tvBirthday;
    private TextView _tvHeightWeight;
    private TextView _tvFirstPerson;
    private TextView _tvSecondPerson;
    private TextView _tvBelongs;
    private TextView _tvSkill;
    private TextView _tvProfile;
    private TextView _tvLivedProcess;
    private TextView _tvPersonality;
    private TextView _tvAppearance;
    private TextView _tvOther;
    /**
     * インテント
     */
    private Intent _intent;
    /**
     * 登場人物の情報を格納する配列
     */
    private HashMap<String, String> _character = new HashMap<>();
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
        setContentView(R.layout.activity_character);

        //画面部品取得
        _ivCharacterIcon = findViewById(R.id.ivCharacterIcon);
        _tvName = findViewById(R.id.tvName);
        _tvPhonetic = findViewById(R.id.tvPhonetic);
        _tvAnother = findViewById(R.id.tvAnother);
        _tvAge = findViewById(R.id.tvAge);
        _tvGender = findViewById(R.id.tvGender);
        _tvBirthday = findViewById(R.id.tvBirthday);
        _tvHeightWeight = findViewById(R.id.tvHeightWeight);
        _tvFirstPerson = findViewById(R.id.tvFirstPerson);
        _tvSecondPerson = findViewById(R.id.tvSecondPerson);
        _tvBelongs = findViewById(R.id.tvBelongs);
        _tvSkill = findViewById(R.id.tvSkill);
        _tvProfile = findViewById(R.id.tvProfile);
        _tvLivedProcess = findViewById(R.id.tvLivedProcess);
        _tvPersonality = findViewById(R.id.tvPersonality);
        _tvAppearance = findViewById(R.id.tvAppearance);
        _tvOther = findViewById(R.id.tvOther);

        //NavigationViewのヘッダー部分のTextViewを取得
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View drawerHeader = inflater.inflate(R.layout.drawer_header, null);
        _tvMenuBack = drawerHeader.findViewById(R.id.tvMenuBack); //プロット一覧へ戻る

        //Toolbar
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        //DrawerLayout
        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(CharacterActivity.this, mDrawer, _toolbar, R.string.drawable_open, R.string.drawable_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //NavigationViewのリスナー
        NavigationView nvLeftView = findViewById(R.id.nvLeftView);
        nvLeftView.setNavigationItemSelectedListener(this);

        //遷移元からデータ取得
        _intent = getIntent();
        _outline = (HashMap<String, String>) _intent.getSerializableExtra("OUTLINE");
        _character = (HashMap<String, String>) _intent.getSerializableExtra("CHARACTER");

        //TODO:人物を削除する処理
    }

    @Override
    public void onResume() {
        super.onResume();

        setCharacterInfo();
    }

    /**
     * オプションメニュー作成
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        //表示されたままのメニューアイコンを非表示に
        menu.setGroupVisible(R.id.mgEdit, false);

        //表示されたままのメニューアイコンを非表示に
        menu.findItem(R.id.menuInsert).setVisible(false);
        menu.findItem(R.id.menuReload).setVisible(false);

        //削除ボタンを表示
        MenuItem delete = menu.findItem(R.id.menuDelete);
        delete.setVisible(true);

        //編集ボタンを表示
        MenuItem edit = menu.findItem(R.id.menuEdit);
        edit.setVisible(true);

        return true;
    }

    /**
     * オプションメニュー選択時処理
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            //編集ボタン
            case R.id.menuEdit:
                onEditButtonClick();
                break;
            //削除ボタン
            case R.id.menuDelete:
                onCharacterDeleteButtonClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * NavigationView
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent();
        int itemId = item.getItemId();
        switch (itemId) {
            //概要画面（何もしない）
            case R.id.menuOutline:
                intent = new Intent(CharacterActivity.this, OutlineActivity.class);
                break;
            //登場人物一覧画面へ
            case R.id.menuCharacter:
                intent = new Intent(CharacterActivity.this, CharacterListActivity.class);
                break;
            //世界観一覧画面へ
            case R.id.menuWorld:
                intent = new Intent(CharacterActivity.this, WorldViewListActivity.class);
                break;
            //構想画面へ
            case R.id.menuStory:
                intent = new Intent(CharacterActivity.this, StoryEditActivity.class);
                break;
            //メモ画面へ
            case R.id.menuMemo:
                intent = new Intent(CharacterActivity.this, MemoListActivity.class);
                break;
            //削除
            case R.id.menuDelete:
                intent = null;
                onPlotDeleteClick();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);

        if(null != intent) {
            intent.putExtra("OUTLINE", _outline);
            startActivity(intent);
        }
        return true;
    }

    /**
     * 編集ボタン押下時の処理
     */
    private void onEditButtonClick() {
        //TODO:値送信
        Intent intent = new Intent(CharacterActivity.this, CharacterEditActivity.class);
        intent.putExtra("OUTLINE", _outline);
        startActivity(intent);
        CharacterActivity.this.finish();
    }

    /**
     * 削除ボタン押下時の処理
     */
    private void onCharacterDeleteButtonClick() {
        String no = _character.get("no");

        //TODO:ダイアログ作成クラスへ
    }


    /**
     * プロット削除押下時の処理
     */
    private void onPlotDeleteClick() {
        String no = _outline.get("no");
        String title = _outline.get("title");

        Bundle extras = new Bundle();
        extras.putString("no", no);
        extras.putString("title", title);

        Context context = this;
        PlotDeleteConfirmDialogCreate.setActivityContext(context);

        PlotDeleteConfirmDialogCreate dialog = new PlotDeleteConfirmDialogCreate();
        dialog.setArguments(extras);

        FragmentManager manager = getSupportFragmentManager();
        dialog.show(manager, "CharacterActivity");
    }

    /**
     * 「プロット一覧に戻る」押下時の処理
     */
    public void onMenuBackClick(View view) {
        Intent intent = new Intent(CharacterActivity.this, PlotListActivity.class);
        startActivity(intent);
    }

    /**
     * 画面部品に遷移元から取得した値をセットするメソッド
     */
    private void setCharacterInfo() {
        //画像
        File storage = Environment.getExternalStorageDirectory();
        File file = new File(storage.getAbsolutePath() + "/" + Environment.DIRECTORY_DCIM + "/Camera", _character.get("image_path"));
        Bitmap bm = BitmapFactory.decodeFile(file.getPath());
        _ivCharacterIcon.setImageBitmap(bm);

        _tvName.setText( _character.get("name") );
        _tvPhonetic.setText( _character.get("phonetic") );
        _tvAnother.setText( _character.get("another") );
        _tvAge.setText( _character.get("age") );
        _tvGender.setText( _character.get("gender") );
        _tvBirthday.setText( _character.get("birthday") );

        //身長・体重
        String height = _character.get("height") + "cm";
        String weight = _character.get("weight") + "kg";
        _tvHeightWeight.setText( height + " " + weight );

        _tvFirstPerson.setText( _character.get("first_person") );
        _tvSecondPerson.setText( _character.get("second_person") );
        _tvBelongs.setText( _character.get("belongs") );
        _tvSkill.setText( _character.get("skill") );
        _tvProfile.setText( _character.get("profile") );
        _tvLivedProcess.setText( _character.get("lived_process") );
        _tvPersonality.setText( _character.get("personality") );
        _tvAppearance.setText( _character.get("appearance") );
        _tvOther.setText( _character.get("other") );
    }
}
