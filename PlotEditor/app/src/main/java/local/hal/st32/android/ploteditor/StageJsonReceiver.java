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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * 非同期でStageJsonServletと通信するクラス
 * データ受信用
 *
 * @author ohs60224
 */
public class StageJsonReceiver extends AsyncTask<String, Void, String> {
    /**
     * コールバック用のクラス
     */
    private CallBackTask _callBack;
    /**
     * ログに記載するタグ用の文字列
     */
    private static final String DEBUG_TAG = "StageJsonReceiver";
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getStageJson();

    @Override
    public String doInBackground(String... params) {
        String urlStr = ACCESS_URL;
        String plot = params[0];

        String data = "plot=" + plot;
        Log.e("データ", data);
        HttpURLConnection con = null;
        InputStream is = null;
        String result = "";

        try {
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
        }
        catch (SocketTimeoutException ex) {
            Log.e(DEBUG_TAG, "タイムアウト", ex);
        }
        catch (MalformedURLException ex) {
            Log.e(DEBUG_TAG, "URL変換失敗", ex);
        }
        catch (IOException ex) {
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
                Log.e(DEBUG_TAG, "InputStream解析失敗", ex);
            }
        }

        return result;
    }

    @Override
    public void onPostExecute(String result) {
        HashMap<String, String> map = new HashMap<>();
        String no = "";
        String plot = "";
        String stage = "";

        try {
            JSONObject rootJSON = new JSONObject(result);

            //JSONデータの解析・取得
            JSONArray stageArray = rootJSON.getJSONArray("stages");
            for(int i = 0; i < stageArray.length(); i++) {
                map = new HashMap<>();
                JSONObject stageNow = stageArray.getJSONObject(i);

                no = stageNow.getString("no");
                plot = stageNow.getString("plot");
                stage = stageNow.getString("stage");

                map.put("no", no);
                map.put("plot", plot);
                map.put("stage", stage);
            }
        }
        catch (JSONException ex) {
            Log.e(DEBUG_TAG, "JSON解析失敗", ex);
        }

        _callBack.CallBack(map);
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
