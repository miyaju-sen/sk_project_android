package local.hal.st32.android.ploteditor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * 就職作品
 *
 * PagerParlanceFragmentを引き渡すアダプターを作成するクラス
 *
 * @author ohs60224
 */
public class PagerParlanceStatePagerAdapter extends FragmentStatePagerAdapter {

    private int sCount;

    public PagerParlanceStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
//
//        switch(i){
//            case 0:
//                return new Fragment0();
//            case 1:
//                return new Fragment1();
//            default:
//                return new Fragment2();
//        }

        return new PagerParlanceFragment();
    }

    @Override
    public int getCount() {
        return sCount;
    }

    public void setPageCount(int count) {
        sCount = count;
    }
}
