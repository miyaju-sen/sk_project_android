package local.hal.st32.android.ploteditor;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * 就職作品
 *
 * EditTextの入力状態をリアルタイムで監視するクラス
 *
 * @author ohs60224
 */
public class EditTextWatcher implements TextWatcher {
    private String _str;
    private EditText _editText;

    public void setEditText(EditText editText) {
        this._editText = editText;
        Log.e("テキスト", this._editText.getText().toString());
        this._editText.addTextChangedListener(this);
    }

    //入力前
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    //入力最中
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    //入力後
    @Override
    public void afterTextChanged(Editable s) {
        //テキスト変更後に変更されたテキストを取り出す
        this._str = s.toString();
    }

    /**
     * 変更されたテキストのゲッター
     * @return 変更されたテキスト
     */
    public String getStr() {
        return this._str;
    }
}
