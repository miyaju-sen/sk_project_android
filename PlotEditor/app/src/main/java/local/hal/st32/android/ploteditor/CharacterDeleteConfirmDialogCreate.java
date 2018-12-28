package local.hal.st32.android.ploteditor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * 就職作品
 *
 * 登場人物削除確認のダイアログを生成するクラス
 *
 * @author ohs60224
 */
public class CharacterDeleteConfirmDialogCreate extends DialogFragment{
    private AlertDialog.Builder builder;
    private AlertDialog _dialog;

    /**
     * トースト
     */
    private static Toast _toast;
    /**
     * 主キー
     */
    private String _no;
    /**
     * テーブル
     */
    private final String _table = "characters";
    /**
     * アクティビティ
     */
    private Activity _activity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //呼びだし元のアクティビティを取得
        _activity = getActivity();

        Bundle extras = getArguments();
        _no = extras.getString("no");
        String name = extras.getString("name");

        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_confirm);
        String msg = getString(R.string.dialog_character_delete_msg, name);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.dialog_delete, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_cancel, new DialogButtonClickListener());
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
                    DeleteAccess access = new DeleteAccess();
                    access.setOnCallBack(new DeleteAccess.CallBackTask() {
                        @Override
                        public void CallBack() {
                            _toast.show();
//                            Intent intent = new Intent(_activity, CharacterListActivity.class);
//                            _activity.startActivity(intent);
                            _activity.finish();
                        }
                    });
                    access.execute(_no, _table);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    }

    /**
     * コンテキストを取得するセッター
     *
     * @param context コンテキスト
     */
    public static void setActivityContext(Context context) {
        toastCreate(context);
    }

    /**
     * トーストを作成し、返すメソッド
     *
     * @param context コンテキスト
     */
    public static void toastCreate(Context context) {
        _toast = Toast.makeText(context, "削除が完了しました。", Toast.LENGTH_SHORT);
    }
}
