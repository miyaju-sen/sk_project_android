package local.hal.st32.android.ploteditor;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleAdapter;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 就職作品
 *
 * 非同期でParlanceJsonServletと通信するクラス
 * データ受信用
 *
 * @author ohs60224
 */
public class ParlanceJsonReceive extends AsyncTask<String, Void, String> {
    /**
     * コールバック用のクラス
     */
    private CallBackTask _callBack;
    /**
     * ログに記載するタグ用の文字列
     */
    private static final String DEBUG_TAG = "ParlanceJsonReceieve";
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getParlanceJson();

    @Override
    public String doInBackground(String... params) {
        String urlStr = ACCESS_URL;
        String plot = params[0];

        String data = "plot=" + plot;
        Log.e("*******", "データ" + data);
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
            is = con.getInputStream();
            result = is2String(is);
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
        List<Map<String, String>> list = new ArrayList<>();
        String no = "";
        String plot = "";
        String name = "";
        String explanation = "";

        try {
            JSONObject rootJSON = new JSONObject(result);

            //JSONデータの解析・取得
            Map<String, String> item;
            JSONArray parlanceArray = rootJSON.getJSONArray("parlances");
            for(int i = 0; i < parlanceArray.length(); i++) {
                item = new HashMap<>();
                JSONObject parlanceNow = parlanceArray.getJSONObject(i);
                no = parlanceNow.getString("no");
                plot = parlanceNow.getString("plot");
                name = parlanceNow.getString("name");
                explanation = parlanceNow.getString("explanation");

                item.put("no", no);
                item.put("plot", plot);
                item.put("name", name);
                item.put("explanation", explanation);

                list.add(item);
            }
        }
        catch (JSONException ex) {
            Log.e(DEBUG_TAG, "JSON解析失敗", ex);
        }

        _callBack.CallBack(list);
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
        return String.valueOf(sb);
    }

    /**
     * コールバック用のstaticなクラス
     */
    public static class CallBackTask {
        public void CallBack(List<Map<String, String>> list) {
        }
    }
}
