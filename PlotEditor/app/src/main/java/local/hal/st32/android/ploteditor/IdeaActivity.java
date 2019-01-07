package local.hal.st32.android.ploteditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 就職作品
 *
 * 構想画面用アクティビティクラス
 * //TODO:StoryEditActivityが不要。あらゆる箇所からの遷移先になってるから削除の際は注意
 *
 * @author ohs60224
 */
public class IdeaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);
    }
}
