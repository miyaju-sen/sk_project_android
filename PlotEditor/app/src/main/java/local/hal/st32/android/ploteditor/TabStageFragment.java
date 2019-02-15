package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * 就職作品
 *
 * tab_stageのFragmentクラス
 *
 * @author ohs60224
 */
public class TabStageFragment extends Fragment {
    /**
     * ビュー
     */
    private View _view;
    /**
     * テキストビュー
     */
    private static TextView _tvStage;

    /**
     * コンストラクタ
     * ※fragmentには、コンストラクタに引数を渡してはいけないルールが存在する
     */
    public TabStageFragment() {
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
        _view = inflater.inflate(R.layout.fragment_tab_stage, null);

        //スクロールビューにフェードアウト追加
        ScrollView scrollView = _view.findViewById(R.id.scrollView);
        scrollView.setVerticalFadingEdgeEnabled(true);
        scrollView.setFadingEdgeLength(130);

        _tvStage = _view.findViewById(R.id.tvStageNote);

        return _view;
    }

    /**
     * TextViewにセットする値を取得するメソッド
     * @param stage
     */
    public static void setStage(HashMap<String, String> stage) {
        _tvStage.setText(stage.get("stage"));
    }
}
