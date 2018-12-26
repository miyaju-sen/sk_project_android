package local.hal.st32.android.ploteditor;

import android.app.Activity;
import android.content.Intent;

/**
 * 就職作品
 *
 * プロットを削除する際に使用するクラス
 * PlotListActivityへ遷移し、直前に開いていたアクティビティをfinishする
 *
 * @author ohs60224
 */
public class FinishActivity {
    private Intent _intent;
    private Activity _activity;

    public FinishActivity(Activity activity) {
        this._intent = new Intent();
        this._activity = activity;
    }

    public void startPlotListActivity() {
        this._intent = new Intent(_activity, PlotListActivity.class);
        _activity.startActivity(this._intent);
        _activity.finish();
    }
}
