package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * tab_parlanceのFragmentクラス
 *
 * @author ohs60224
 */
public class TabParlanceFragment extends Fragment {
    /**
     * リストビュー
     */
    private static ListView _lvParlances;

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
        _lvParlances = view.findViewById(R.id.lvParlances);
        registerForContextMenu(_lvParlances);

        //取得したListViewを親アクティビティへ
        WorldViewListActivity.setListView(_lvParlances);

        return view;
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;

        int itemId = item.getItemId();
        switch (itemId) {
            //編集
            case R.id.mcEdit:
                Intent intent = new Intent(getActivity(), ParlanceEditActivity.class);
                intent.putExtra("ACTIVITY", ParlanceActivity.NOW_ACTIVITY);
                WorldViewListActivity.getListItem(position, intent);
                break;
            //削除
            case R.id.mcDelete:
                onParlanceDeleteButtonClick(position);
                break;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * 削除ボタン押下時の処理
     */
    private void onParlanceDeleteButtonClick(int position) {
        Bundle extras = new Bundle();
        Map<String, String> parlance = (Map<String, String>) _lvParlances.getAdapter().getItem(position);
        extras.putString("no", parlance.get("no"));
        extras.putString("table", "parlances");
        extras.putString("msg", getString( R.string.dialog_parlance_delete_msg, parlance.get("name") ));

        Context context = getActivity().getApplicationContext();
        DeleteConfirmDialogCreate.setActivityContext(context);

        DeleteConfirmDialogCreate dialog = new DeleteConfirmDialogCreate();
        dialog.setArguments(extras);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        dialog.show(manager, "TabParlanceFragment");
    }
}
