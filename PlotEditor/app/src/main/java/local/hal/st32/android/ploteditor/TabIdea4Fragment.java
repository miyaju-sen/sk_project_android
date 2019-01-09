package local.hal.st32.android.ploteditor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

/**
 * 就職作品
 *
 * タブ「結」のFragmentクラス
 *
 * @author ohs60224
 */
public class TabIdea4Fragment extends Fragment {
    /**
     * 画面部品
     */
    private static TextView _tvIdea;
    private static ListView _lvStories;

    /**
     * コンストラクタ
     */
    public TabIdea4Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_tab_idea4, null);

        //画面部品取得
        _tvIdea = view.findViewById(R.id.tvIdea);
        _lvStories = view.findViewById(R.id.lvStories);

        //取得した部品は親アクティビティへ
        IdeaActivity.setIdeaView(_tvIdea, _lvStories);

        return view;
    }
}
