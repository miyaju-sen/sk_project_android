package local.hal.st32.android.ploteditor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * 就職作品
 *
 * アクションバーの戻るボタン押下時に確認として表示するダイアログを生成するクラス
 *
 * @author ohs60224
 */
public class ReturnConfirmDialogCreate extends DialogFragment {
    private AlertDialog.Builder builder;
    private AlertDialog _dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_confirm);
        builder.setMessage(R.string.dialog_confirm_text);
        builder.setPositiveButton(R.string.dialog_yes, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_no, new DialogButtonClickListener());
        _dialog = builder.create();
        return _dialog;
    }

    /**
     * ダイアログのボタンが押下されたときの処理が記述されたメンバクラス
     */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    getActivity().finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    }
}
