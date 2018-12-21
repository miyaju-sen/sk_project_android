package local.hal.st32.android.ploteditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MemoEditActivity extends AppCompatActivity {

    /**
     * 画面部品用フィールド
     */
    private TextView tvMemo;
    private EditText etMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        //画面部品取得
         tvMemo = findViewById(R.id.tvMemo);
         etMemo = findViewById(R.id.etMemo);
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

        //修正ボタンを表示
        MenuItem update = menu.findItem(R.id.menuEdit);
        update.setVisible(true);

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
            //修正ボタン
            case R.id.menuEdit:
                //etMemoを表示してtvMemoを表示
                tvMemo.setVisibility(View.INVISIBLE);
                etMemo.setVisibility(View.VISIBLE);

                //tvMemoの内容をetMemoへ
                String memo = tvMemo.getText().toString();
                etMemo.setText(memo);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
