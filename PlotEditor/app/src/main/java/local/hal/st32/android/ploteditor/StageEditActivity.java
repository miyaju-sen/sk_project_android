package local.hal.st32.android.ploteditor;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * 就職作品
 *
 * 世界観の『舞台』を編集する画面用アクティビティ
 * TODO:まずは初回時の処理から（INSERTが動くのは最初の一度だけ）
 *
 * @author ohs60224
 */
public class StageEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_edit);

        //アクションバーに前画面へ戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
//                onSaveButtonClick();
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
}
