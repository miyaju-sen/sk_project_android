package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.content.Intent;
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
 * 非同期でMemoJsonServletに通信するクラス
 *
 * @author ohs60224
 */
public class MemoJsonAccess extends AsyncTask<String, String, String> {
    /**
     * コールバック用のクラス
     */
    private CallBackTask _callBack;
    /**
     * ログに記載するタグ用の文字列
     */
    private static final String DEBUG_TAG = "MemoJsonAccess";
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getMemoJson();
    /**
     * サーバ通信が成功したかどうかのフラグ
     * 成功した場合はtrue、失敗した場合はfalse
     */
    private boolean mSuccess = false;
    /**
     * 解析したJSONデータを格納する配列
     */
    private static List<Map<String, String>> sMemos;

    /**
     * コンストラクタ
     */
    public MemoJsonAccess() {
        this.sMemos = new ArrayList<>();
    }

    @Override
    public String doInBackground(String... params) {
        String urlStr = ACCESS_URL;
        String no = params[0];
        String plot = params[1];
        String note = params[2];

        String data = "no=" + no + "&plot=" + plot + "&note=" + note;
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
            mSuccess = true;
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
        if(mSuccess) {
            HashMap<String, String> map = new HashMap<>();
            String no = "";
            String plot = "";
            String note = "";

            try {
                //JSONデータの解析・取得
                JSONObject rootJSON = new JSONObject(result);
                no = rootJSON.getString("newId");

                //新規登録あるいは更新したレコードのみを取得
                JSONArray memoArray = rootJSON.getJSONArray("memos");
                for(int i = 0; i < memoArray.length(); i++) {
                    map = new HashMap<>();
                    JSONObject memoNow = memoArray.getJSONObject(i);

                    if(no.equals( memoNow.getString("no") )) {
                        no = memoNow.getString("no");
                        plot = memoNow.getString("plot");
                        note = memoNow.getString("note");

                        map.put("no", no);
                        map.put("plot", plot);
                        map.put("note", note);

                        break;
                    }
                }
            }
            catch (JSONException ex) {
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }

            _callBack.CallBack(map);
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
        public void CallBack(HashMap<String, String> map) {
        }
    }
}
