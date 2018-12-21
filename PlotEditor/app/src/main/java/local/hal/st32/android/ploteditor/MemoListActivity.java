package local.hal.st32.android.ploteditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MemoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        setTitle("メモ");
    }
}
