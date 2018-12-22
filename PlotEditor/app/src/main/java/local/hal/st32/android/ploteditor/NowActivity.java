package local.hal.st32.android.ploteditor;

/**
 * 就職作品
 *
 * 現在表示している画面に対応したアクティビティ名を格納するクラス
 *
 * @author ohs60224
 */
public class NowActivity {
    /**
     * フィールド
     */
    private String NOW_ACTIVITY;

    /**
     * コンストラクタ
     */
    public NowActivity() {
        this.NOW_ACTIVITY = "";
    }

    /**
     * セッター
     * @param activity 現在表示している画面に対応したアクティビティ名
     */
    public void setNowActivity(String activity) {
        this.NOW_ACTIVITY = activity;
    }

    /**
     * ゲッター
     * @return 現在表示している画面に対応したアクティビティ名
     */
    public String getNowActivity() {
        return this.NOW_ACTIVITY;
    }
}
