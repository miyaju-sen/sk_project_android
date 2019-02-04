package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * タブ「承」のFragmentクラス
 *
 * @author ohs60224
 */
public class TabIdea2Fragment extends Fragment {
    /**
     * タグ
     */
    private static String sTag;
    /**
     * 画面部品
     */
    private static TextView _tvIdea;
    private static ExpandableListView _lvStories;
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
    public TabIdea2Fragment() {
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
        Log.e("*******", "地点フラグメント2" + getTag());
        View view = inflater.inflate(R.layout.fragment_tab_idea2, null);

        //タグ取得
        sTag = getTag();

        //画面部品取得
        _tvIdea = view.findViewById(R.id.tvIdea);
        _lvStories = view.findViewById(R.id.lvStories);
        _lvStories.setOnChildClickListener(new ChildListClickListener());

        //親ノード押下時のリスナクラス
        _lvStories.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                return false;
            }
        });

        //コンテキストメニューをセット
        registerForContextMenu(_lvStories);

        //TapEventへテキストビューをセット→ダブルタップ後、編集用のダイアログを表示
        TapEvent event = new TapEvent(getContext());
        event.setOnDialogCall(new TapEvent.DialogCall() {
            @Override
            public void ideaEditDialog(TextView textView) {
                IdeaEditDialogCreate dialog = new IdeaEditDialogCreate();
                Bundle extras = new Bundle();
                extras.putString("ideaTitle", getString( R.string.idea_2 ));
                extras.putString("ideaNo", _ideas.get("ideaNo"));
                extras.putString("plot", _ideas.get("plot"));
                extras.putString("idea", _ideas.get("idea"));
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
     * TabIdea2Fragmentのタグのゲッター
     * @return TabIdea2Fragmentのタグ
     */
    public static String getTabIdea2FragmentTag() {
        return sTag;
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
     * @param stories
     */
    public static void setLvStories(SimpleExpandableListAdapter adapter, List<Map<String, String>> stories) {
        _stories = stories;
        _lvStories.setAdapter(adapter);

        Log.e("+++++++++++++++++", "ストーリーズ：" + _stories);
    }

    /**
     * 取得したアダプタをlvStoriesにセットするメソッド
     * @param adapter
     * @param stories
     * @param position
     * @param top
     */
    public static void setLvStories(SimpleExpandableListAdapter adapter, List<Map<String, String>> stories, String position, String top) {
        _stories = stories;
        _lvStories.setAdapter(adapter);

        //編集前の表示位置を指定
        if(!"".equals(position)) {
            _lvStories.setSelectionFromTop(Integer.valueOf(position), Integer.valueOf(top));
        }
        else {
            _lvStories.setSelection( _lvStories.getCount() - 1 );
        }
    }

    /**
     * リスト（子ノード）押下時のリスナクラス
     */
    private class ChildListClickListener implements ExpandableListView.OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

            //値をセット・ダイアログを表示
            storyEdit(groupPosition);

            Log.e("+++++++++++++++++", "ストーリーズ：" + _stories);

            return false;
        }
    }

    /**
     * コンテキストメニュー作成
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    /**
     * コンテキストメニュー選択時処理
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        int position = ExpandableListView.getPackedPositionGroup( info.packedPosition );
        Log.e("****************", "フラグメント２");

        int itemId = item.getItemId();
        switch (itemId) {
            //編集
            case R.id.mcEdit:
                storyEdit(position);
                break;
            //削除
            case R.id.mcDelete:
                onStoryDeleteButtonClick(position);
                break;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * 編集時に値を送信する処理を記述したメソッド
     *
     * @param position
     */
    private void storyEdit(int position) {
        if( IdeaActivity.getInstance().getNowFragmentTag().equals(getTag()) ) {
            StoryEditDialogCreate dialog = new StoryEditDialogCreate();
            Bundle extras = new Bundle();

            extras.putString("mode", "edit");
            extras.putString("plot", _ideas.get("plot")); //作品No
            extras.putString("storyNo", _stories.get(position).get("storyNo")); //ストーリーNo
            extras.putString("ideaNo", _ideas.get("ideaNo")); //構想No
            extras.putString("idea", _ideas.get("idea")); //起承転結番号
            extras.putString("title", _stories.get(position).get("title")); //タイトル
            extras.putString("story", _stories.get(position).get("story")); //ストーリー

            //編集後のListViewの表示位置を、現状と同一にするための値を送信（スクロール位置の記憶）
            extras.putString("top", Integer.toString(_lvStories.getChildAt(0).getTop()));
            extras.putString("position", Integer.toString(_lvStories.getFirstVisiblePosition()));

            dialog.setArguments(extras);

            FragmentManager manager = getActivity().getSupportFragmentManager();
            dialog.show(manager, "IdeaActivity");
        }
    }

    /**
     * 削除ボタン押下時の処理
     *
     * @param position
     */
    private void onStoryDeleteButtonClick(int position) {
        if( IdeaActivity.getInstance().getNowFragmentTag().equals(getTag()) ) {
            Bundle extras = new Bundle();
            extras.putString("no", _stories.get(position).get("storyNo"));
            extras.putString("table", "stories");
            extras.putString("msg", getString(R.string.dialog_story_delete_msg,  _stories.get(position).get("title")));

            Context context = IdeaActivity.getInstance();
            DeleteConfirmDialogCreate.setActivityContext(context);

            DeleteConfirmDialogCreate dialog = new DeleteConfirmDialogCreate();
            dialog.setArguments(extras);

            FragmentManager manager = getActivity().getSupportFragmentManager();
            dialog.show(manager, "IdeaActivity");
        }
    }
}
