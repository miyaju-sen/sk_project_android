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
 * 非同期でIdeaJsonServletと通信するクラス
 * ・構想の更新を行う
 * ・
 *
 * @author ohs60224
 */
public class IdeaJsonAccess extends AsyncTask<String, String, String> {
    /**
     * コールバック用のクラス
     */
    private CallBackTask _callBack;
    /**
     * ログに記載するタグ用の文字列
     */
    private static final String DEBUG_TAG = "IdeaJsonAccess";
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getIdeaJson();
    /**
     * サーバ通信が成功したかどうかのフラグ
     * 成功した場合はtrue、失敗した場合はfalse
     */
    private boolean _success = false;
    /**
     * 解析したJSONデータを格納する配列
     */
    private static HashMap<String, String> _idea; //構想
    private static List<Map<String, String>> _stories; //ストーリー
    /**
     * ストーリーNo
     */
    private static String _storyNo;

    /**
     * コンストラクタ
     */
    public IdeaJsonAccess() {
        this._idea = new HashMap<>();
        this._stories = new ArrayList<>();
    }

    @Override
    public String doInBackground(String... params) {
        String urlStr = ACCESS_URL;
        String no = params[0];
        String plot = params[1];
        String idea = params[2];
        String note = params[3];

        String data = "no=" + no + "&plot=" + plot + "&idea=" + idea + "&note=" + note;
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
            HashMap<String, String> map = new HashMap<>();
            String ideaNo = "";
            String plot = "";
            String idea = "";
            String note = "";
            String storyNo = "";
            String title = "";
            String story = "";

            try {
                JSONObject rootJSON = new JSONObject(result);
                String storyFlag = rootJSON.getString("storyFlag");

                //JSONデータの解析・取得
                JSONArray ideaArray = rootJSON.getJSONArray("ideas");
                for(int i = 0; i < ideaArray.length(); i++) {
                    map = new HashMap<>();
                    JSONObject ideaNow = ideaArray.getJSONObject(i);

                    ideaNo = ideaNow.getString("ideaNo");
                    plot = ideaNow.getString("plot");
                    idea = ideaNow.getString("idea");
                    note = ideaNow.getString("note");
                    map.put("ideaNo", ideaNo);
                    map.put("plot", plot);
                    map.put("idea", idea);
                    map.put("note", note);

                    //v_ideasの分
                    if("true".equals(storyFlag)) {
                        storyNo = ideaNow.getString("story_no");
                        title = ideaNow.getString("title");
                        story = ideaNow.getString("story");
                        map.put("storyNo", storyNo);
                        map.put("title", title);
                        map.put("story", story);
                        _stories.add(map);
                    }
                }
            }
            catch (JSONException ex) {
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }

            _callBack.CallBack(map, _stories);
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
        public void CallBack(HashMap<String, String> map, List<Map<String, String>> list) {
        }
    }
}
