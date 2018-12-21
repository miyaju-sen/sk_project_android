package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 就職作品
 *
 * 世界観一覧画面用アクティビティ
 *
 * @author ohs60224
 */
public class WorldViewListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_view_list);

        TabLayout tlWorldView = findViewById(R.id.tlWorldView);
        ViewPager pager = findViewById(R.id.pager);
        final String[] _tabTitles = {getString(R.string.tv_stage), getString(R.string.tv_parlance)};

        TabItem tabItemStage = tlWorldView.findViewById(R.id.tiStage);
        TabItem tabItemParlance = tlWorldView.findViewById(R.id.tiParlance);

        //表示ページに必要な項目を設定
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            /**
             * positionに対応したFragmentを反映するメソッド
             * タブを選択するたびに呼ばれる
             *
             * @param position 設定するタブのページ番号
             * @return タブページに設定するFragment
             */
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new TabStageFragment();
                    case 1:
                        //return TabParlanceFragment.newInstance(position + 1);
                        return new TabParlanceFragment();
                    default:
                        return null;
                }
            }

            /**
             * タブ総数分のタイトルをタブに反映するメソッド（後からセットするためにnullを返しておく）
             * @param position
             * @return
             */
            @Override
            public CharSequence getPageTitle(int position) {
               // return _tabTitles[position];
                return null;
            }

            /**
             * タブの総数を返すメソッド
             * @return
             */
            @Override
            public int getCount() {
                return _tabTitles.length;
            }
        };

        //タブレイアウト
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View stage = inflater.inflate(R.layout.tab_stage, null); //タブ「舞台」
        View parlance = inflater.inflate(R.layout.tab_parlance, null); //タブ「設定・用語」
        TabLayout.Tab tbStage = tlWorldView.getTabAt(0);
        TabLayout.Tab tbParlance = tlWorldView.getTabAt(1);
        tbStage.setCustomView(stage);
        tbParlance.setCustomView(parlance);

        //ViewPagerにページを設定
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);

        //ViewPagerをTabLayoutに設定
        tlWorldView.setupWithViewPager(pager);

        //初期に選択されているTabを設定
        tlWorldView.getTabAt(0).select();
    }

    //以下の内容を実装する必要がある。（空で問題ない）
    //----------------------------------------------------------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
    //----------------------------------------------------------------------------------------------
}
