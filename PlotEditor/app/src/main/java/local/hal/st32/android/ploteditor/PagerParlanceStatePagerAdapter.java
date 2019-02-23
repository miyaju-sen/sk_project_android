package local.hal.st32.android.ploteditor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * 就職作品
 *
 * PagerParlanceFragmentを引き渡すアダプターを作成するクラス
 *
 * @author ohs60224
 */
public class PagerParlanceStatePagerAdapter extends FragmentStatePagerAdapter {

    private int sCount;
    private String mName;
    private String mExplanation;

    public PagerParlanceStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        PagerParlanceFragment fragment = new PagerParlanceFragment();
        fragment.setData(mName, mExplanation);

        Log.e("**********", "地点Adapter");
        return fragment;
    }

    @Override
    public int getCount() {
        return sCount;
    }

    public void setPageCount(int count) {
        sCount = count;
    }

    public void setData(String name, String explanation) {
        mName = name;
        mExplanation = explanation;
    }
}
