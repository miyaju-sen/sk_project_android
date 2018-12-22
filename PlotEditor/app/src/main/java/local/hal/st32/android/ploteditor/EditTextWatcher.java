package local.hal.st32.android.ploteditor;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 就職作品
 *
 * EditTextの入力を監視するクラス
 *
 * @author ohs60224
 */
public class EditTextWatcher implements TextWatcher {
    private  static EditText _editText;

    public static void setEditText(EditText editText) {
        _editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
