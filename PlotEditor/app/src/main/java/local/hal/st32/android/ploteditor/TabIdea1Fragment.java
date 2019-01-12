package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * タブ「起」のFragmentクラス
 *
 * @author ohs60224
 */
public class TabIdea1Fragment extends Fragment {
    /**
     * タグ
     */
    private static String _tag;
    /**
     * 画面部品
     */
    private static TextView _tvIdea;
    private static ListView _lvStories;
    /**
     * 構想情報を格納する配列
     */
    private static HashMap<String, String> _ideas = new HashMap<>();
    /**
     * ストーリー一覧を格納する配列
     */
    private static List<Map<String, String>> _stories = new ArrayList<>();

    /**
     * コンストラクタ
     */
    public TabIdea1Fragment() {
    }

    /**
     * アクティビティが生成された際に呼び出される
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * FragmentのUIが描写される際に呼び出される（ActivityにおけるsetContentView）
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("*******", "地点フラグメント1" + getTag());
        View view = inflater.inflate(R.layout.fragment_tab_idea1, null);

        //画面部品取得
        _tvIdea = view.findViewById(R.id.tvIdea);
        _lvStories = view.findViewById(R.id.lvStories);
        _lvStories.setOnItemClickListener(new ListItemClickListener());

        //タグ
        _tag = getTag();

        //TapEventへテキストビューをセット→ダブルタップ後、編集用のダイアログを表示
        final TapEvent event = new TapEvent(getContext());
        event.setOnDialogCall(new TapEvent.DialogCall() {
            @Override
            public void ideaEditDialog(TextView textView) {
                Log.e("*********", "テキストビューの中身は：" + textView.getText().toString());

                IdeaEditDialogCreate dialog = new IdeaEditDialogCreate();
                Bundle extras = new Bundle();
                extras.putString("ideaTitle", getString( R.string.idea_1 ));
                extras.putString("ideaNo", _ideas.get("ideaNo"));
                extras.putString("plot", _ideas.get("plot"));
                extras.putString("idea", _ideas.get("idea"));
                extras.putString("note", _tvIdea.getText().toString());
                dialog.setArguments(extras);

                //TODO:間に合わせの処理（組み直す予定）
                dialog.setOnCallBack(new IdeaEditDialogCreate.CallBackTask() {
                    @Override
                    public void CallBack(List<Map<String, String>> ideas, List<Map<String, String>> stories) {
                        Log.e("*******", "地点フラグバック");
                        IdeaActivity.receiveIdea(getTag());
                    }
                });

                FragmentManager manager = getActivity().getSupportFragmentManager();
                dialog.show(manager, "IdeaActivity");
            }
        });
        event.setTouchListener(_tvIdea);

        return view;
    }

    /**
     * TabIdea1Fragmentのタグのゲッター
     * @return TabIdea1Fragmentのタグ
     */
    public static String getTabIdea1FragmentTag() {
        return _tag;
    }

    /**
     * 取得した配列内の内容（note）をtvIdeaにセットするメソッド
     * @param ideas
     */
    public static void setTvIdea(HashMap<String, String> ideas) {
        _ideas = ideas;
        _tvIdea.setText( ideas.get("note") );
    }

    /**
     * 取得したアダプタをlvStoriesにセットするメソッド
     * @param adapter
     */
    public static void setLvStories(SimpleAdapter adapter, List<Map<String, String>> stories) {
        _stories = stories;
        _lvStories.setAdapter(adapter);
    }

    /**
     * リスト押下時のリスナクラス
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            StoryEditDialogCreate dialog = new StoryEditDialogCreate();
            Bundle extras = new Bundle();
            extras.putString("mode", "edit");
            extras.putString("title", _stories.get(position).get("title"));
            extras.putString("story", _stories.get(position).get("story"));
            dialog.setArguments(extras);

            FragmentManager manager = getActivity().getSupportFragmentManager();
            dialog.show(manager, "IdeaActivity");
        }
    }
}
