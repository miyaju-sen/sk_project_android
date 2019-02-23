package local.hal.st32.android.ploteditor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 就職作品
 *
 * 設定・用語画面用のFragmentクラス
 *
 * @author ohs60224
 */
public class PagerParlanceFragment extends Fragment {

    /**
     * 画面部品
     */
    private static View sView;
    private static TextView sTvName;
    private static TextView sTvExplanation;

    private String mName;
    private String mExplanation;

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
        sView = inflater.inflate(R.layout.fragment_pager_parlance, null);

        //画面部品取得
        sTvName = sView.findViewById(R.id.tvParlanceName);
        sTvExplanation = sView.findViewById(R.id.tvExplanation);
        Log.e("**********", "地点PagerParlanceFragment");

        sTvName.setText(mName);
        sTvExplanation.setText(mExplanation);

        ScrollView scrollView = sView.findViewById(R.id.scrollView);
        scrollView.setVerticalFadingEdgeEnabled(true);
        scrollView.setFadingEdgeLength(100);

        return sView;
    }

    public void setData(String name, String explanation) {
//        sTvName.setText(name);
//        sTvExplanation.setText(explanation);
        mName = name;
        mExplanation = explanation;
    }
}
