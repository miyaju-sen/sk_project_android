package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.widget.SimpleAdapter;

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
    private HashMap<String, String> _idea1;
    private HashMap<String, String> _idea2;
    private HashMap<String, String> _idea3;
    private HashMap<String, String> _idea4;

    /**
     * 起承転結ごとのストーリー
     */
    private List<Map<String, String>> _story1;
    private List<Map<String, String>> _story2;
    private List<Map<String, String>> _story3;
    private List<Map<String, String>> _story4;

    /**
     * 起承転結ごとのストーリー：子ノード表示用
     */
    private List<List<Map<String, String>>> _childStory1;
    private List<List<Map<String, String>>> _childStory2;
    private List<List<Map<String, String>>> _childStory3;
    private List<List<Map<String, String>>> _childStory4;

    private List<Map<String, String>> _list;
    private HashMap<String, String> _map;

    private final String IDEA_ONE = "1";
    private final String IDEA_TWO = "2";
    private final String IDEA_THREE = "3";
    private final String IDEA_FOUR = "4";

    public IdeaAllocate() {
        this._idea1 = new HashMap<>();
        this._idea2 = new HashMap<>();
        this._idea3 = new HashMap<>();;
        this._idea4 = new HashMap<>();

        this._story1 = new ArrayList<>();
        this._story2 = new ArrayList<>();
        this._story3 = new ArrayList<>();
        this._story4 = new ArrayList<>();

        this._childStory1 = new ArrayList<>();
        this._childStory2 = new ArrayList<>();
        this._childStory3 = new ArrayList<>();
        this._childStory4 = new ArrayList<>();

        this._list = new ArrayList<>();
        this._map = new HashMap<>();
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
                    this._idea1 = map;
                    break;
                //承
                case IDEA_TWO:
                    this._idea2 = map;
                    break;
                //転
                case IDEA_THREE:
                    this._idea3 = map;
                    break;
                //結
                case IDEA_FOUR:
                    this._idea4 = map;
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
    public HashMap<String, String> getIdea1() {
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
     * タブ「起」用データのゲッター
     * @return ストーリー
     */
    public List<List<Map<String, String>>> getChildStory1() {
        for(int i = 0; i < this._story1.size(); i++) {
            this._map = new HashMap<>();
            this._map.put("story", this._story1.get(i).get("story"));

            this._list.add(this._map);
        }
        this._childStory1.add(this._list);

        return this._childStory1;
    }

    /**
     * タブ「承」用データのゲッター
     * @return 構想内容
     */
    public HashMap<String, String> getIdea2() {
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
     * タブ「承」用データのゲッター
     * @return ストーリー
     */
    public List<List<Map<String, String>>> getChildStory2() {
        for(int i = 0; i < this._story2.size(); i++) {
            this._map = new HashMap<>();
            this._map.put("story", this._story2.get(i).get("story"));

            this._list.add(this._map);
        }
        this._childStory2.add(this._list);

        return this._childStory2;
    }

    /**
     * タブ「転」用データのゲッター
     * @return 構想内容
     */
    public HashMap<String, String> getIdea3() {
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
     * タブ「転」用データのゲッター
     * @return ストーリー
     */
    public List<List<Map<String, String>>> getChildStory3() {
        for(int i = 0; i < this._story3.size(); i++) {
            this._map = new HashMap<>();
            this._map.put("story", this._story3.get(i).get("story"));

            this._list.add(this._map);
        }
        this._childStory3.add(this._list);

        return this._childStory3;
    }

    /**
     * タブ「結」用データのゲッター
     * @return 構想内容
     */
    public HashMap<String, String> getIdea4() {
        return this._idea4;
    }
    /**
     * タブ「結」用データのゲッター
     * @return ストーリー一覧
     */
    public List<Map<String, String>> getStory4() {
        return this._story4;
    }
    /**
     * タブ「結」用データのゲッター
     * @return ストーリー
     */
    public List<List<Map<String, String>>> getChildStory4() {
        for(int i = 0; i < this._story4.size(); i++) {
            this._map = new HashMap<>();
            this._map.put("story", this._story4.get(i).get("story"));

            this._list.add(this._map);
        }
        this._childStory4.add(this._list);

        return this._childStory4;
    }
}
