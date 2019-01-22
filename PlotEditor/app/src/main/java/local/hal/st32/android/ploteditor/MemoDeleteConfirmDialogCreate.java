package local.hal.st32.android.ploteditor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


/**
 * 就職作品
 *
 * メモ内容の削除確認のダイアログを生成するクラス
 *
 * @author ohs60224
 */
public class MemoDeleteConfirmDialogCreate extends DialogFragment {
    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;

    /**
     * トースト
     */
    private static Toast mToast;
    /**
     * 主キー
     */
    private String mNo;
    /**
     * テーブル
     */
    private final String TABLE = "memos";
    /**
     * アクティビティ
     */
    private Activity mActivity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //呼びだし元のアクティビティを取得
        mActivity = getActivity();

        Bundle extras = getArguments();
        mNo = extras.getString("no");

        mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle(R.string.dialog_confirm);
        String msg = getString(R.string.dialog_memo_delete_msg);
        mBuilder.setMessage(msg);
        mBuilder.setPositiveButton(R.string.dialog_delete, new DialogButtonClickListener());
        mBuilder.setNegativeButton(R.string.dialog_cancel, new DialogButtonClickListener());
        mDialog = mBuilder.create();
        return mDialog;
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
                            mToast.show();
                            mActivity.finish();
                        }
                    });
                    access.execute(mNo, TABLE);
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
        mToast = Toast.makeText(context, "削除が完了しました。", Toast.LENGTH_SHORT);
    }
}
