package local.hal.st32.android.ploteditor;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * 非同期でStoryJsonServletと通信するクラス
 *
 * @author ohs60224
 */
public class StoryJsonAccess extends AsyncTask<String, String, String> {
    /**
     * コールバック用のクラス
     */
    private CallBackTask _callBack;
    /**
     * ログに記載するタグ用の文字列
     */
    private static final String DEBUG_TAG = "StoryJsonAccess";
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getStoryJson();
    /**
     * サーバ通信が成功したかどうかのフラグ
     * 成功した場合はtrue、失敗した場合はfalse
     */
    private boolean _success = false;
    /**
     * 解析したJSONデータを格納する配列
     */
    private static List<Map<String, String>> _ideas; //構想
    private static List<Map<String, String>> _stories; //ストーリー

    /**
     * コンストラクタ
     */
    public StoryJsonAccess() {
        this._ideas = new ArrayList<>();
        this._stories = new ArrayList<>();
    }

    @Override
    public String doInBackground(String... params) {
        String urlStr = ACCESS_URL;
        String plot = params[0];
        String no = params[1];
        String idea = params[2];
        String title = params[3];
        String story = params[4];

        String data = "plot=" + plot + "&no=" + no + "&idea=" + idea + "&title=" + title + "&story=" + story;
        Log.e("データ", data);
        HttpURLConnection con = null;
        InputStream is = null;
        String result = "";

        try {
            publishProgress("サーバにデータを送信");
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();
            int status = con.getResponseCode();
            if(status != 200) {
                throw new IOException("ステータスコード:" + status);
            }
            is = con.getInputStream();

            result = is2String(is);
            _success = true;
        }
        catch (SocketTimeoutException ex) {
            publishProgress(String.valueOf(R.string.msg_err_timeout));
            Log.e(DEBUG_TAG, "タイムアウト", ex);
        }
        catch (MalformedURLException ex) {
            publishProgress(String.valueOf(R.string.msg_err_send));
            Log.e(DEBUG_TAG, "URL変換失敗", ex);
        }
        catch (IOException ex) {
            publishProgress(String.valueOf(R.string.msg_err_send));
            Log.e(DEBUG_TAG, "通信失敗", ex);
        }
        finally {
            if (con != null) {
                con.disconnect();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                publishProgress(String.valueOf(R.string.msg_err_parse));
                Log.e(DEBUG_TAG, "InputStream解析失敗", ex);
            }
        }

        return result;
    }

    @Override
    public void onPostExecute(String result) {
        if(_success) {
            HashMap<String, String> ideaMap = new HashMap<>();
            HashMap<String, String> storyMap = new HashMap<>();
            String ideaNo = "";
            String plot = "";
            String idea = "";
            String note = "";
            String storyNo = "";
            String title = "";
            String story = "";

            try {
                //JSONデータの解析・取得
                JSONObject rootJSON = new JSONObject(result);

                //構想分
                //-----------------------------------------------------------------------------
                JSONArray ideaArray = rootJSON.getJSONArray("ideas");
                for(int i = 0; i < ideaArray.length(); i++) {
                    ideaMap = new HashMap<>();
                    JSONObject ideaNow = ideaArray.getJSONObject(i);

                    ideaNo = ideaNow.getString("idea_no");
                    plot = ideaNow.getString("plot");
                    idea = ideaNow.getString("idea");
                    note = ideaNow.getString("note");

                    ideaMap.put("ideaNo", ideaNo);
                    ideaMap.put("plot", plot);
                    ideaMap.put("idea", idea);
                    ideaMap.put("note", note);
                    _ideas.add(ideaMap);
                }
                //-----------------------------------------------------------------------------

                //ストーリー分
                //-----------------------------------------------------------------------------
                JSONArray storyArray = rootJSON.getJSONArray("stories");
                for(int i = 0; i < storyArray.length(); i++) {
                    storyMap = new HashMap<>();
                    JSONObject storyNow = storyArray.getJSONObject(i);

                    idea = storyNow.getString("idea");
                    storyNo = storyNow.getString("story_no");
                    title = storyNow.getString("title");
                    story = storyNow.getString("story");

                    storyMap.put("idea", idea);
                    storyMap.put("storyNo", storyNo);
                    storyMap.put("title", title);
                    storyMap.put("story", story);
                    _stories.add(storyMap);
                }
                //-----------------------------------------------------------------------------
            }
            catch (JSONException ex) {
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }

            //Log.e("**********", "構想：" + _ideas + "：ストーリー：" + _stories);
            _callBack.CallBack(_ideas, _stories);
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

    private String is2String(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int line;
        while(0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
        return sb.toString();
    }

    /**
     * コールバック用のstaticなクラス
     */
    public static class CallBackTask {
        public void CallBack(List<Map<String, String>> ideas, List<Map<String, String>> stories) {
        }
    }
}
