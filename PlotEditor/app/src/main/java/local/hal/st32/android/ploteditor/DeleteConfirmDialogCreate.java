package local.hal.st32.android.ploteditor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

/**
 * 就職作品
 *
 * 選択した項目の削除確認のダイアログを生成するクラス
 *
 * @author ohs60224
 */
public class DeleteConfirmDialogCreate extends DialogFragment {
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
    private String mTable;
    /**
     * アクティビティ
     */
    private Activity mActivity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //呼び出し元のアクティビティを取得
        mActivity = getActivity();

        Bundle extras = getArguments();
        mNo = extras.getString("no");
        mTable = extras.getString("table");
        String msg = extras.getString("msg");

        AlertDialog.Builder builder = new AlertDialog.Builder( mActivity );
        builder.setTitle(R.string.dialog_confirm);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.dialog_delete, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_cancel, new DialogButtonClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }

    /**
     * ダイアログのボタンが押下されたときの処理が記述されたメンバクラス
     */
    private class DialogButtonClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.e("********", "アクティビティ" + getActivity());

            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    DeleteAccess access = new DeleteAccess();
                    access.setOnCallBack(new DeleteAccess.CallBackTask() {
                        @Override
                        public void CallBack() {
                            mToast.show();

                            //削除するのがstoriesテーブルのレコードであった場合
                            if("stories".equals(mTable)) {
                                IdeaActivity.getInstance().onReloadButtonClick();
                            }
                            else {
                                mActivity.finish();
                            }
                        }
                    });
                    access.execute(mNo, mTable);
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
