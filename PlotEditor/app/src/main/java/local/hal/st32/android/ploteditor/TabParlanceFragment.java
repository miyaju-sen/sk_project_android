package local.hal.st32.android.ploteditor;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 就職作品
 *
 * tab_parlanceのFragmentクラス
 *
 * @author ohs60224
 */
public class TabParlanceFragment extends Fragment {

    /**
     * コンストラクタ
     * ※fragmentには、コンストラクタに引数を渡してはいけないルールが存在する
     */
    public TabParlanceFragment() {
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
        View view = inflater.inflate(R.layout.fragment_tab_parlance, null);

        return view;
    }
}
