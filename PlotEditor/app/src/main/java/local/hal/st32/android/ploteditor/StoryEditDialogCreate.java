package local.hal.st32.android.ploteditor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * ストーリー新規追加・編集時のダイアログを生成するクラス
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
                    //保存または新規追加 TODO:現時点ではまだ取得するだけで更新処理はしてない
                    IdeaJsonAccess access = new IdeaJsonAccess();
                    access.setOnCallBack(new IdeaJsonAccess.CallBackTask() {
                        @Override
                        public void CallBack(List<Map<String, String>> ideas, List<Map<String, String>> stories) {
                            IdeaAllocate allocate = new IdeaAllocate();
                            allocate.setIdeas(ideas, stories);
                            IdeaActivity.setAllocate(allocate);

                            //TODO:起承転結のどのストーリーを編集したのかを判断する必要がある
                            //ストーリー一覧をListViewにセット
                            String from[] = {"title", "story"};
                            int to[] = {android.R.id.text1, android.R.id.text2};
                            SimpleAdapter adapter = new SimpleAdapter(IdeaActivity.getInstance().getApplicationContext(), allocate.getStory1(), android.R.layout.simple_list_item_2, from, to);
                            TabIdea1Fragment.setLvStories(adapter, stories);
                        }
                    });
                    access.execute("", "1", "", "");
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    //編集キャンセル
                    break;
            }
        }
    }
}
