package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 就職作品
 *
 * 複雑なタッチイベントを取得するクラス
 * IdeaActivity用
 *
 * @author ohs60224
 */
public class TapEvent implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener  {
    private GestureDetector _gd;
    private Context _context;
    private TextView _textView;
    private DialogCall _dialogCall;

    /**
     * 空のコンテキスト
     */
    public TapEvent() {
    }

    /**
     * コンテキスト
     * @param context 親アクティビティ
     */
    public TapEvent(Context context) {
        this._gd = new GestureDetector(context, this);
        this._context = context;
        this._dialogCall = new DialogCall();
    }

    /**
     * 取得したテキストビューにタッチリスナーをセットするメソッド
     *
     * @param textView テキストビュー
     */
    public void setTouchListener(TextView textView) {
        Log.e("*********", "テキストビュー" + textView);
        _textView = textView;
        Log.e("*********", "テキストビュー２" + _textView);

        _textView.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event) {
                _gd.onTouchEvent(event);
                return true;
            }
        });
    }

    public boolean onTouchEvent(MotionEvent ev) {
        //タッチイベントの判別・振り分けを行う
        this._gd.onTouchEvent(ev);

        return this._gd.onTouchEvent(ev);
    }

    /**
     * 押下時に呼び出される
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * 弾いたときに呼び出される
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    /**
     * 長押ししたときに呼び出される
     */
    @Override
    public void onLongPress(MotionEvent e) {
    }

    /**
     * ドラッグしたときに呼び出される
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * 押下時に呼び出される
     * ※押下してすぐにドラッグしたり、離したりした場合は呼び出されない
     */
    @Override
    public void onShowPress(MotionEvent e) {
    }

    /**
     * シングルタップ時に呼び出される
     * ※ダブルタップ時にも呼び出される
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * シングルタップ時に呼び出される
     * ※ダブルタップ時には呼び出されない
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return false;
    }

    /**
     * ダブルタップ時に呼び出される　TODO:使うのはここ
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.e("*********", "ダブルタップ" + _textView);
        Log.e("*********", "タッチイベント（ダブル）" + onTouchEvent(e));
        _dialogCall.ideaEditDialog(_textView);
//        IdeaActivity.onIdeaDoubleTap();
        return false;
    }

    /**
     * ダブルタップ中のイベント（押下、スクロール、離す）で呼び出される
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        // Toast.makeText(_context, "タップされました", Toast.LENGTH_SHORT).show();

        return false;
    }


    public void setOnDialogCall(DialogCall dc) {
        _dialogCall = dc;
    }

    public static class DialogCall {
        public void ideaEditDialog(TextView textView) {
        }
    }
}
