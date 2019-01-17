package local.hal.st32.android.ploteditor;

import android.app.Dialog;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
     * ダイアログのキャンセルボタン
     */
    private Button _btChancel;
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
    /**
     * 規定時間内に二度タップされたことを検知するためのタイマー
     */
    private CountDownTimer mTimer;
    /**
     * 一度目のボタンが押下されたかどうかを判定するフラグ
     */
    private boolean mPressed = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.dialog_idea_edit, null);
        _etIdeaEdit = view.findViewById(R.id.etIdeaEdit);

        //タイマー設定（onFinish()、onTick()）
        mTimer = new CountDownTimer(5000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mPressed = false;
            }
        };

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
        builder.setNeutralButton(R.string.dialog_cancel, null);
        _dialog = builder.create();

        //キャンセルボタンだけ別リスナー TODO:長文警告をトースト表示するのは不適切では→検討中ということで一旦放置
        _dialog.show(); //TODO:上記のトースト表示をなくすのならここではなくIdeaActivityでshow()する
        _btChancel = _dialog.getButton( DialogInterface.BUTTON_NEUTRAL );
        _btChancel.setOnClickListener(new NeutralButtonClickListener());

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
                            //起承転結の内、どの内容を編集したかの判別のために_ideaを送信
                            IdeaActivity.receiveIdea(_idea);
                            _dialog.dismiss();
                        }
                    });
                    access.execute(_ideaNo, _plot, _idea, _etIdeaEdit.getText().toString());
                    break;
            }
        }
    }

    /**
     * ダイアログでキャンセルボタンが押下されたときの処理
     */
    private class NeutralButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //変更項目があった場合
            if(!_note.equals( _etIdeaEdit.getText().toString() )) {
                if(!mPressed) {
                    //タイマーを開始
                    mTimer.cancel();
                    mTimer.start();

                    Toast.makeText(IdeaActivity.getInstance().getApplicationContext(), getString(R.string.toast_chancel_msg), Toast.LENGTH_LONG).show();
                    mPressed = true;
                    return;
                }

                //mPressed = trueなら終了
                _dialog.dismiss();
            }
            else {
                _dialog.dismiss();
            }
        }
    }
}
