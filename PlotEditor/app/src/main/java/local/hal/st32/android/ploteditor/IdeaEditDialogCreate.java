package local.hal.st32.android.ploteditor;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * EditText
     */
    private EditText _etIdeaEdit;
    /**
     * 構想情報
     */
    private String _ideaNo;
    private String _plot;
    private String _idea;
    private String _note;

    private CallBackTask _callBack = new CallBackTask();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.dialog_idea_edit, null);
        _etIdeaEdit = view.findViewById(R.id.etIdeaEdit);

        Bundle extras = getArguments();
        _ideaNo = extras.getString("ideaNo");
        _plot = extras.getString("plot");
        _idea = extras.getString("idea");
        _note = extras.getString("note");
        _etIdeaEdit.setText( _note );

        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle( extras.getString("ideaTitle") );
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
                    IdeaJsonAccess access = new IdeaJsonAccess();
                    access.setOnCallBack(new IdeaJsonAccess.CallBackTask() {
                        @Override
                        public void CallBack(List<Map<String, String>> ideas, List<Map<String, String>> stories) {
                            //TODO:データ保存後どうにかしてアクティビティを再生成するかなんかしないとエラー
                            IdeaActivity.receiveIdea(_idea);

//                            _callBack.CallBack(ideas, stories);
                            _dialog.dismiss();
                        }
                    });
                    access.execute(_ideaNo, _plot, _idea, _etIdeaEdit.getText().toString());
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    //編集キャンセル
                    break;
            }
        }
    }

    /**
     * 他アクティビティからCallBackTaskを呼びだす際に必要なメソッド
     *
     * @param cbt CallBackTaskクラス
     */
    public void setOnCallBack(CallBackTask cbt) {
        _callBack = cbt;
    }

    /**
     * コールバック用のstaticなクラス
     */
    public static class CallBackTask {
        public void CallBack(List<Map<String, String>> ideas, List<Map<String, String>> stories) {
        }
    }
}
