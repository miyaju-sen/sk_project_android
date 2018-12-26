package local.hal.st32.android.ploteditor;

import android.graphics.Bitmap;

/**
 * 就職作品
 *
 * 登場人物一覧のListViewに表示するカスタマイズ要素を格納するクラス
 *
 * @author ohs60224
 */
public class CharacterListItem {
    /**
     * フィールド
     */
    private Bitmap _icon; //アイコン
    private String _name; //名前
    private String _profile; //紹介文

    /**
     * コンストラクタ（空）
     */
    public CharacterListItem() {
    }

    /**
     * コンストラクタ
     *
     * @param icon 登場人物のイメージ画像
     * @param name 名前
     * @param profile 紹介文
     */
    public CharacterListItem(Bitmap icon, String name, String profile) {
        this._icon = icon;
        this._name = name;
        this._profile = profile;
    }

    /**
     * イメージ画像のセッター
     * @param icon イメージ画像
     */
    public void setIcon(Bitmap icon) {
        this._icon = icon;
    }

    /**
     * 名前のセッター
     * @param name 名前
     */
    public void setName(String name) {
        this._name = name;
    }

    /**
     * 紹介文のセッター
     * @param profile 紹介文
     */
    public void setProfile(String profile) {
        this._profile = profile;
    }

    /**
     * イメージ画像のゲッター
     * @return イメージ画像
     */
    public Bitmap getIcon() {
        return this._icon;
    }

    /**
     * 名前のゲッター
     * @return 名前
     */
    public String getName() {
        return this._name;
    }

    /**
     * 紹介文のゲッター　
     * @return 紹介文
     */
    public String getProfile() {
        return this._profile;
    }
}
