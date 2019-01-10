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
    private List<Map<String, String>> _idea1;
    private List<Map<String, String>> _idea2;
    private List<Map<String, String>> _idea3;
    private List<Map<String, String>> _idea4;

    private final String IDEA_ONE = "1";
    private final String IDEA_TWO = "2";
    private final String IDEA_THREE = "3";
    private final String IDEA_FOUR = "4";

    public IdeaAllocate() {
        this._idea1 = new ArrayList<>();
        this._idea2 = new ArrayList<>();
        this._idea3 = new ArrayList<>();
        this._idea4 = new ArrayList<>();
    }

    /**
     * 構想・ストーリーのデータを取得
     *
     * @param ideas
     */
    public void setIdeas(List<Map<String, String>> ideas) {
        allocateIdea(ideas);
    }

    /**
     * 取得したデータを起・承・転・結ごとに振り分け
     *
     * @param ideas ideasテーブルかv_ideasテーブルの情報
     */
    public void allocateIdea(List<Map<String, String>> ideas) {
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

            //ストーリーがある場合
            if(null != ideas.get(i).get("story_no")) {
                map.put("storyNo", ideas.get(i).get("story_no"));
                map.put("title", ideas.get(i).get("title"));
                map.put("story", ideas.get(i).get("story"));
            }

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
        //終わり
    }

    /**
     * タブ「起」用データのゲッター
     * @return
     */
    public List<Map<String, String>> getIdea1() {
        return this._idea1;
    }

    /**
     * タブ「承」用データのゲッター
     * @return
     */
    public List<Map<String, String>> getIdea2() {
        return this._idea2;
    }

    /**
     * タブ「転」用データのゲッター
     * @return
     */
    public List<Map<String, String>> getIdea3() {
        return this._idea3;
    }

    /**
     * タブ「結」用データのゲッター
     * @return
     */
    public List<Map<String, String>> getIdea4() {
        return this._idea4;
    }
}
