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
    private String DELETE_SERVLET; //削除処理

    /**
     * コンストラクタ
     */
    public AccessURL() {
        this.ROOT_URL = "http://192.168.0.111:8080/PlotEditor";
        this.PLOT_JSON = ROOT_URL + "/PlotJsonServlet";
        this.DELETE_SERVLET = ROOT_URL + "/DeleteServlet";
    }

    /**
     * PlotJsonServletへのURL
     */
    public String getPlotJson() {
        return PLOT_JSON;
    }

    /**
     * DeleteServletへのURL
     */
    public String getDeleteServlet() {
        return DELETE_SERVLET;
    }

}
