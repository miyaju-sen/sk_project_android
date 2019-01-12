package local.hal.st32.android.ploteditor;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * 就職作品
 *
 * ストーリー編集時のダイアログを生成するクラス
 *
 * @author ohs60224
 */
public class StoryEditDialogCreate extends DialogFragment {
    private AlertDialog.Builder builder;
    private AlertDialog _dialog;

    /**
     * 画面部品
     */
    private EditText _etTitle;
    private EditText _etStory;
    /**
     * 新規追加か編集かを示す変数
     */
    private String _mode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.dialog_story_edit, null);
        _etTitle = view.findViewById(R.id.etTitle);
        _etStory = view.findViewById(R.id.etStory);

        builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        Bundle extras = getArguments();
        _mode = extras.getString("mode");
        //新規追加だった場合
        if(_mode.equals("insert")) {
            builder.setTitle(R.string.dialog_story_insert);
            builder.setPositiveButton(R.string.dialog_insert, new DialogButtonClickListener());
        }
        //編集モードだった場合
        else {
            _etTitle.setText( extras.getString("title") );
            _etStory.setText( extras.getString("story") );

            builder.setTitle(R.string.dialog_story_edit);
            builder.setPositiveButton(R.string.dialog_save, new DialogButtonClickListener());
        }

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
                    //保存または新規追加
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    //編集キャンセル
                    break;
            }
        }
    }
}
