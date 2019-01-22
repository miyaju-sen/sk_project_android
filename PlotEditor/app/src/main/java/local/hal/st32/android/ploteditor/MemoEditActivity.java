package local.hal.st32.android.ploteditor;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * 就職作品
 *
 * メモ内容編集画面
 * ・新規追加の場合：一覧画面から
 * ・編集の場合：確認画面から
 *
 * @author ohs60224
 */
public class MemoEditActivity extends AppCompatActivity {
    /**
     * 画面部品
     */
    private EditText mEtMemo;
    /**
     * メモ内容
     */
    private String mNote;
    /**
     * 作品No
     */
    private String mPlot;
    /**
     * インテント
     */
    private Intent mIntent;
    /**
     * プロット概要が格納された配列
     */
    private HashMap<String, String> mOutline = new HashMap<>();
    /**
     * 遷移元のアクティビティ（デフォルト：MemoActivity）
     */
    private String ACTIVITY = new NowActivity().getMemoActivity();
    /**
     * JSON解析した登場人物の情報を格納する配列
     */
    private HashMap<String, String> mMemo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        //アクションバーに前画面へ戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //画面部品取得
        mEtMemo = findViewById(R.id.etMemo);

        mIntent = getIntent();
        mOutline = (HashMap<String, String>) mIntent.getSerializableExtra("OUTLINE");
        mPlot = mOutline.get("no");
    }

    @Override
    public void onResume() {
        super.onResume();
        ACTIVITY = mIntent.getStringExtra("ACTIVITY");

        //リスト画面から遷移してきた場合：新規登録
        if(ACTIVITY.equals(new NowActivity().getMemoListActivity())) {
            setTitle("メモ 新規登録");

            mNote = "";
        }
        //確認画面から遷移してきた場合：編集
        else {
            setTitle("メモ 編集");

            //編集する内容を取得
            mMemo = (HashMap<String, String>) mIntent.getSerializableExtra("MEMO");
            mNote = mMemo.get("note");
            mEtMemo.setText(mNote);
        }
    }

    /**
     * オプションメニュー作成
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        //表示されたままのメニューアイコンを非表示に
        menu.findItem(R.id.menuEdit).setVisible(false);

        //保存ボタンを表示
        MenuItem save = menu.findItem(R.id.menuSave);
        save.setVisible(true);

        return true;
    }

    /**
     * オプションメニュー選択時処理
     *
     * @param item
     * @return
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
     * 保存ボタン押下時の処理 TODO:新規登録の場合、遷移先はリスト画面の方がいいのでは
     */
    private void onSaveButtonClick() {
        //新規登録の場合主キーは空、編集の場合はmMemoから値を取得
        String no = "";
        if(ACTIVITY.equals(new NowActivity().getMemoActivity())) {
            no = mMemo.get("no");
        }
        String note = mEtMemo.getText().toString();

        MemoJsonAccess access = new MemoJsonAccess();
        access.setOnCallBack(new MemoJsonAccess.CallBackTask() {
            @Override
            public void CallBack(HashMap<String, String> map) {
                mMemo = map;

                mIntent = new Intent(getApplication(), MemoActivity.class);
                mIntent.putExtra("MEMO", mMemo);
                mIntent.putExtra("OUTLINE", mOutline);
                startActivity(mIntent);
                MemoEditActivity.this.finish();
            }
        });
        access.execute(no, mPlot, note);
    }

    /**
     * 戻るボタン押下時の処理
     */
    private void onBackButtonClick() {
        //変更されていた場合
        if( !mNote.equals( mEtMemo.getText().toString() ) ) {
            ReturnConfirmDialogCreate dialog =  new ReturnConfirmDialogCreate();
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "MemoEditActivity");
        }
        else {
            finish();
        }
    }
}
