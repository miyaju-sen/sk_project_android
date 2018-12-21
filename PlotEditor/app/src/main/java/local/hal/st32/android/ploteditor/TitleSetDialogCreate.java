package local.hal.st32.android.ploteditor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * タイトル入力時のダイアログを生成するクラス
 *
 * @author ohs60224
 */
public class TitleSetDialogCreate extends DialogFragment {

    private AlertDialog.Builder builder;
    private AlertDialog _dialog;
    private EditText etTitle;

    /**
     * 現在表示している画面に対応したアクティビティ
     */
    public static final String NOW_ACTIVITY = "PlotListActivity";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.dialog_title_set, null);
        etTitle = view.findViewById(R.id.etTitleSet);

        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_insert);
        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_create, new DialogButtonClickListener());
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
                    //プロット作成
                    PlotJsonAccess access = new PlotJsonAccess();
                    access.execute("", etTitle.getText().toString(), "", "", NOW_ACTIVITY); //主キー、タイトル、キャッチコピー、あらすじ
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    //プロット作成キャンセル
                    break;
            }
        }
    }
}
