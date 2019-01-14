package local.hal.st32.android.ploteditor;

import android.app.Activity;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * 構想・ストーリー一覧を取得し、起承転結ごとに振り分けるクラス
 */
public class ReceiveIdeaStory {
    private SimpleAdapter _adapter;
    private Activity _activity;
    private List<Map<String, String>> _stories;

    public ReceiveIdeaStory() {
    }

    public ReceiveIdeaStory(Activity activity, List<Map<String, String>> list) {
        String[] from = {"title", "story"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        this._activity = activity;
        this._adapter = new SimpleAdapter(activity.getApplicationContext(), list, android.R.layout.simple_list_item_2, from, to);
    }

    public SimpleAdapter getAdapter() {
        return this._adapter;
    }
}
