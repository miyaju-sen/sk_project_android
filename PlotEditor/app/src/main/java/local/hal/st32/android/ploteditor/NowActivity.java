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
    private String PlotListActivity;
    private String OutlineActivity;
    private String CharacterListActivity;
    private String CharacterActivity;
    private String WorldViewListActivity;
    private String MemoListActivity;
    private String IdeaActivity;
    private String ParlanceActivity;

    /**
     * コンストラクタ
     */
    public NowActivity() {
        this.NOW_ACTIVITY = "";
        this.PlotListActivity = "PlotListActivity";
        this.OutlineActivity = "OutlineActivity";
        this.CharacterListActivity = "CharacterListActivity";
        this.CharacterActivity = "CharacterActivity";
        this.WorldViewListActivity = "WorldViewListActivity";
        this.MemoListActivity = "MemoListActivity";
        this.IdeaActivity = "IdeaActivity";
        this.ParlanceActivity = "ParlanceActivity";
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

    public String getPlotListActivity() {
        return this.PlotListActivity;
    }

    public String getOutlineActivity() {
        return this.OutlineActivity;
    }

    public String getCharacterListActivity() {
        return this.CharacterListActivity;
    }

    public String getCharacterActivity() {
        return this.CharacterActivity;
    }

    public String getWorldViewListActivity() {
        return this.WorldViewListActivity;
    }

    public String getMemoListActivity() {
        return this.MemoListActivity;
    }

    public String getIdeaActivity() {
        return this.IdeaActivity;
    }

    public String getParlanceActivity() {
        return this.ParlanceActivity;
    }
}
