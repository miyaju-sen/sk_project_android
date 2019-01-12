package local.hal.st32.android.ploteditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * 取得した構想・ストーリーのデータを四つに振り分けるクラス
 *
 * @author ohs60224
 */
public class IdeaAllocate {
    /**
     * 起承転結ごとの内容
     */
    private List<Map<String, String>> _idea1;
    private List<Map<String, String>> _idea2;
    private List<Map<String, String>> _idea3;
    private List<Map<String, String>> _idea4;

    /**
     * 起承転結ごとのストーリー
     */
    private List<Map<String, String>> _story1;
    private List<Map<String, String>> _story2;
    private List<Map<String, String>> _story3;
    private List<Map<String, String>> _story4;

    private final String IDEA_ONE = "1";
    private final String IDEA_TWO = "2";
    private final String IDEA_THREE = "3";
    private final String IDEA_FOUR = "4";

    public IdeaAllocate() {
        this._idea1 = new ArrayList<>();
        this._idea2 = new ArrayList<>();
        this._idea3 = new ArrayList<>();
        this._idea4 = new ArrayList<>();

        this._story1 = new ArrayList<>();
        this._story2 = new ArrayList<>();
        this._story3 = new ArrayList<>();
        this._story4 = new ArrayList<>();
    }

    /**
     * 構想・ストーリーのデータを取得
     *
     * @param ideas
     */
    public void setIdeas(List<Map<String, String>> ideas, List<Map<String, String>> stories) {
        allocateIdea(ideas);
        allocateStory(stories);
    }

    /**
     * 構想のデータを取得
     *
     * @param ideas
     */
    public void setIdeas(List<Map<String, String>> ideas) {
        allocateIdea(ideas);
    }

    /**
     * 取得したideasの中身を起・承・転・結ごとに振り分け
     *
     * @param ideas ideasテーブルの情報
     */
    private void allocateIdea(List<Map<String, String>> ideas) {
        String idea;
        HashMap<String, String> map;

        for(int i = 0; i < ideas.size(); i++) {
            idea = "";
            map = new HashMap<>();

            idea =  ideas.get(i).get("idea");
            map.put("ideaNo", ideas.get(i).get("ideaNo"));
            map.put("plot", ideas.get(i).get("plot"));
            map.put("idea", idea);
            map.put("note", ideas.get(i).get("note"));

            //起承転結ごとにデータを分ける
            switch (idea) {
                //起
                case IDEA_ONE:
                    this._idea1.add(map);
                    break;
                //承
                case IDEA_TWO:
                    this._idea2.add(map);
                    break;
                //転
                case IDEA_THREE:
                    this._idea3.add(map);
                    break;
                //結
                case IDEA_FOUR:
                    this._idea4.add(map);
                    break;
            }
        }
    }

    /**
     * 取得したstoriesの中身を起・承・転・結ごとに振り分け
     *
     * @param stories v_ideasテーブルの情報
     */
    private void allocateStory(List<Map<String, String>> stories) {
        String idea;
        HashMap<String, String> map;

        for(int i = 0; i < stories.size(); i++) {
            idea = "";
            map = new HashMap<>();

            idea =  stories.get(i).get("idea");
            map.put("idea", idea);
            map.put("storyNo", stories.get(i).get("storyNo"));
            map.put("title", stories.get(i).get("title"));
            map.put("story", stories.get(i).get("story"));

            //起承転結ごとにデータを分ける
            switch (idea) {
                //起
                case IDEA_ONE:
                    this._story1.add(map);
                    break;
                //承
                case IDEA_TWO:
                    this._story2.add(map);
                    break;
                //転
                case IDEA_THREE:
                    this._story3.add(map);
                    break;
                //結
                case IDEA_FOUR:
                    this._story4.add(map);
                    break;
            }
        }
    }

    /**
     * タブ「起」用データのゲッター
     * @return 構想内容
     */
    public List<Map<String, String>> getIdea1() {
        return this._idea1;
    }
    /**
     * タブ「起」用データのゲッター
     * @return ストーリー一覧
     */
    public List<Map<String, String>> getStory1() {
        return this._story1;
    }

    /**
     * タブ「承」用データのゲッター
     * @return 構想内容
     */
    public List<Map<String, String>> getIdea2() {
        return this._idea2;
    }
    /**
     * タブ「承」用データのゲッター
     * @return ストーリー一覧
     */
    public List<Map<String, String>> getStory2() {
        return this._story2;
    }

    /**
     * タブ「転」用データのゲッター
     * @return 構想内容
     */
    public List<Map<String, String>> getIdea3() {
        return this._idea3;
    }
    /**
     * タブ「転」用データのゲッター
     * @return ストーリー一覧
     */
    public List<Map<String, String>> getStory3() {
        return this._story3;
    }

    /**
     * タブ「結」用データのゲッター
     * @return 構想内容
     */
    public List<Map<String, String>> getIdea4() {
        return this._idea4;
    }
    /**
     * タブ「結」用データのゲッター
     * @return ストーリー一覧
     */
    public List<Map<String, String>> getStory4() {
        return this._story4;
    }
}
