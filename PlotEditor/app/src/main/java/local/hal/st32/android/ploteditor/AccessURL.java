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
    private String PARLANCE_JSON; //parlancesの更新・抽出
    private String IDEA_JSON; //ideasの更新・抽出
    private String STORY_JSON; //storiesの更新・抽出
    private String MEMO_JSON; //memosの更新・抽出
    private String DELETE_SERVLET; //削除処理

    /**
     * コンストラクタ
     */
    public AccessURL() {
        this.ROOT_URL = "http://192.168.0.111:8080/PlotEditor";
//        this.ROOT_URL = "http://192.168.0.43:8080/PlotEditor";
        this.PLOT_JSON = ROOT_URL + "/PlotJsonServlet";
        this.CHARACTER_JSON = ROOT_URL + "/CharacterJsonServlet";
        this.STAGE_JSON = ROOT_URL + "/StageJsonServlet";
        this.PARLANCE_JSON = ROOT_URL + "/ParlanceJsonServlet";
        this.IDEA_JSON = ROOT_URL + "/IdeaJsonServlet";
        this.STORY_JSON = ROOT_URL + "/StoryJsonServlet";
        this.MEMO_JSON = ROOT_URL + "/MemoJsonServlet";
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
     * ParlanceJsonServletへのURL
     */
    public String getParlanceJson() {
        return this.PARLANCE_JSON;
    }

    /**
     * IdeaJsonServletへのURL
     */
    public String getIdeaJson() {
        return this.IDEA_JSON;
    }

    /**
     * StoryJsonServletへのURL
     */
    public String getStoryJson() {
        return this.STORY_JSON;
    }

    /**
     * StoryJsonServletへのURL
     */
    public String getMemoJson() {
        return this.MEMO_JSON;
    }

    /**
     * DeleteServletへのURL
     */
    public String getDeleteServlet() {
        return this.DELETE_SERVLET;
    }

}
