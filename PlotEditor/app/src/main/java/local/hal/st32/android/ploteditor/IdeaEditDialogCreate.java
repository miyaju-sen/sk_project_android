package local.hal.st32.android.ploteditor;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 就職作品
 *
 * 起承転結の内容編集時のダイアログを生成するクラス
 *
 * @author ohs60224
 */

public class IdeaEditDialogCreate extends DialogFragment {
    private AlertDialog.Builder builder;
    private AlertDialog _dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.dialog_idea_edit, null);

        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.idea_0); //TODO:現在固定値→選択した箇所のタイトルになるように
        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_save, new DialogButtonClickListener());
        builder.setNeutralButton(R.string.dialog_cancel, new DialogButtonClickListener());
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
                    //内容保存
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    //編集キャンセル
                    break;
            }
        }
    }
}
