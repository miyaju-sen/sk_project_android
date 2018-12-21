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
 * 非同期でPlotJsonServletと通信するクラス
 * データ送信用
 *
 * @author ohs60224
 */
public class PlotJsonAccess extends AsyncTask<String, String, String> {
    /**
     * コールバック用のクラス
     */
    private CallBackTask _callBack;
    /**
     * ログに記載するタグ用の文字列
     */
    private static final String DEBUG_TAG = "PlotJsonAccess";
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getPlotJson();
    /**
     * サーバ通信が成功したかどうかのフラグ
     * 成功した場合はtrue、失敗した場合はfalse
     */
    private boolean _success = false;
    /**
     * 解析したJSONデータを格納する配列
     */
    private static HashMap<String, String> _plot;
    /**
     * 現在表示している画面に対応したアクティビティ
     */
    private static String NOW_ACTIVITY = TitleSetDialogCreate.NOW_ACTIVITY;
    /**
     * コンテキスト
     */
    private static Context _context;

    /**
     * コンストラクタ
     */
    public PlotJsonAccess() {
        this._plot = new HashMap<>();
    }

    /**
     * セッター
     * @param plot プロット情報が格納された配列
     */
    private void setPlot(HashMap<String, String> plot) {
        Log.e("セット中身", plot.toString());
        _plot = plot;
    }

    /**
     * ゲッター
     * @return プロット情報が格納された配列
     */
    public static HashMap<String, String> getPlot() {
        Log.e("ゲット中身", _plot.toString());
        return _plot;
    }

    /**
     * セッター
     * @param context コンテキスト
     */
    public void setContext(Context context) {
        this._context = context;
    }

    @Override
    public String doInBackground(String... params) {
        String urlStr = ACCESS_URL;
        String no = params[0];
        String title = params[1];
        String slogan = params[2];
        String summary = params[3];
        NOW_ACTIVITY = params[4];

        String data = "no=" + no + "&title=" + title + "&slogan=" + slogan + "&summary=" + summary;
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
            String no = "";
            String title = "";
            String slogan = "";
            String summary = "";

            try {
                JSONObject rootJSON = new JSONObject(result);
                no = rootJSON.getString("newId");
                Log.e("主キー", no);

                //プロット一覧のJSONデータを解析し、新規登録あるいは更新したレコードのデータのみを取得
                JSONArray plotArray = rootJSON.getJSONArray("plots");
                for(int i = 0; i < plotArray.length(); i++) {
                    map = new HashMap<>();
                    JSONObject plotNow = plotArray.getJSONObject(i);

                    if(no.equals( plotNow.getString("no")) ) {
                        Log.e("IF文の中", plotNow.getString("title"));
                        title = plotNow.getString("title");
                        slogan = plotNow.getString("slogan");
                        summary = plotNow.getString("summary");

                        map.put("no", no);
                        map.put("title", title);
                        map.put("slogan", slogan);
                        map.put("summary", summary);
                        setPlot(map);

                        break;
                    }
                }
            }
            catch (JSONException ex) {
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }

            //新規登録だった場合（遷移元：PlotListActivity、遷移先：OutlineEditActivity）
            if(NOW_ACTIVITY.equals( TitleSetDialogCreate.NOW_ACTIVITY )) {
                PlotListActivity activity = (PlotListActivity) _context;
                activity.onPositiveButtonClick(_context);
            }
            else {
                _callBack.CallBack(map);
            }
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
