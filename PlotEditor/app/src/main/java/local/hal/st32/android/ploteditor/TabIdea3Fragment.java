package local.hal.st32.android.ploteditor;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * タブ「転」のFragmentクラス
 *
 * @author ohs60224
 */
public class TabIdea3Fragment extends Fragment {
    /**
     * 画面部品
     */
    private static TextView _tvIdea;
    private static ListView _lvStories;
    /**
     * ストーリー一覧を格納する配列
     */
    private static List<Map<String, String>> _stories = new ArrayList<>();

    /**
     * コンストラクタ
     */
    public TabIdea3Fragment() {
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
        Log.e("*******", "地点フラグメント3");

        View view = inflater.inflate(R.layout.fragment_tab_idea3, null);

        //画面部品取得
        _tvIdea = view.findViewById(R.id.tvIdea);
        _lvStories = view.findViewById(R.id.lvStories);
        _lvStories.setOnItemClickListener(new ListItemClickListener());

        //TapEventへテキストビューをセット→ダブルタップ後、編集用のダイアログを表示
        TapEvent event = new TapEvent(getContext());
        event.setOnDialogCall(new TapEvent.DialogCall() {
            @Override
            public void ideaEditDialog(TextView textView) {
                IdeaEditDialogCreate dialog = new IdeaEditDialogCreate();
                Bundle extras = new Bundle();
                extras.putString("idea", getString( R.string.idea_3 ));
                extras.putString("note", _tvIdea.getText().toString());
                dialog.setArguments(extras);

                FragmentManager manager = getActivity().getSupportFragmentManager();
                dialog.show(manager, "IdeaActivity");
            }
        });
        event.setTouchListener(_tvIdea);

        return view;
    }

    /**
     * 取得した内容（note）をtvIdeaにセットするメソッド
     * @param note 内容
     */
    public static void setTvIdea(String note) {
        _tvIdea.setText(note);
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
