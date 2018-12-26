package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 就職作品
 *
 * 登場人物一覧ListView用の、ArrayAdapterを継承したクラス
 *
 * @author ohs60224
 */
public class CharacterListAdapter extends ArrayAdapter<CharacterListItem> {
    /**
     * リソースID
     */
    private int _resource;
    /**
     * リストビューの要素
     */
    private List<CharacterListItem> _items;
    /**
     * コンテキストを格納するための
     */
    private LayoutInflater _inflater;

    /**
     * コンストラクタ
     *
     * @param context コンテキスト
     * @param resource リソースID
     * @param items リストビューの要素
     */
    public CharacterListAdapter(Context context, int resource, List<CharacterListItem> items) {
        super(context, resource, items);

        _resource = resource;
        _items = items;
        _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * データセット内の指定された位置(position)にあるデータを表示するビューを取得
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView != null) {
            view = convertView;
        }
        else {
            view = _inflater.inflate(_resource, null);
        }

        //リストビューに表示する要素を取得
        CharacterListItem item = _items.get(position);

        //イメージ画像をセット
        ImageView ivCharacterIcon = view.findViewById(R.id.ivCharacterIcon);
        ivCharacterIcon.setImageBitmap( item.getIcon() );

        //名前をセット
        TextView tvName = view.findViewById(R.id.tvCharacterName);
        tvName.setText( item.getName() );

        //紹介文をセット
        TextView tvProfile = view.findViewById(R.id.tvProfile);
        tvProfile.setText( item.getProfile() );

        return view;
    }
}
