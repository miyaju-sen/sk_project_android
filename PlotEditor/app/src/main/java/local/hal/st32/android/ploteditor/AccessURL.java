package local.hal.st32.android.ploteditor;

/**
 * アクセス先のURLを格納するクラス
 *
 * @author ohs60224
 */
public class AccessURL {
    /**
     * フィールド
     */
    private String ROOT_URL; //URLの共通部分
    private String PLOT_JSON; //plotsの更新・抽出
    private String CHARACTER_JSON; //charactersの更新・抽出
    private String STAGE_JSON; //stagesの更新・抽出
    private String DELETE_SERVLET; //削除処理

    /**
     * コンストラクタ
     */
    public AccessURL() {
        this.ROOT_URL = "http://192.168.0.111:8080/PlotEditor";
        this.PLOT_JSON = ROOT_URL + "/PlotJsonServlet";
        this.CHARACTER_JSON = ROOT_URL + "/CharacterJsonServlet";
        this.STAGE_JSON = ROOT_URL + "/StageJsonServlet";
        this.DELETE_SERVLET = ROOT_URL + "/DeleteServlet";
    }

    /**
     * PlotJsonServletへのURL
     */
    public String getPlotJson() {
        return this.PLOT_JSON;
    }

    /**
     * CharacterJsonServletへのURL
     */
    public String getCharacterJson() {
        return this.CHARACTER_JSON;
    }

    /**
     * StageJsonServletへのURL
     */
    public String getStageJson() {
        return this.STAGE_JSON;
    }

    /**
     * DeleteServletへのURL
     */
    public String getDeleteServlet() {
        return this.DELETE_SERVLET;
    }

}
