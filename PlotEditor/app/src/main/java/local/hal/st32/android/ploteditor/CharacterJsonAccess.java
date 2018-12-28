package local.hal.st32.android.ploteditor;

import android.content.Context;
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

/**
 * 就職作品
 *
 * 非同期でCharacterJsonAccessと通信するクラス
 * データ送信用
 *
 * @author ohs60224
 */
public class CharacterJsonAccess extends AsyncTask<String, String, String> {
    /**
     * コールバック用のクラス
     */
    private CallBackTask _callBack;
    /**
     * ログに記載するタグ用の文字列
     */
    private static final String DEBUG_TAG = "CharacterJsonAccess";
    /**
     * アクセス先のURL
     */
    private static final String ACCESS_URL = new AccessURL().getCharacterJson();
    /**
     * サーバ通信が成功したかどうかのフラグ
     * 成功した場合はtrue、失敗した場合はfalse
     */
    private boolean _success = false;
    /**
     * 解析したJSONデータを格納する配列
     */
    private static HashMap<String, String> _character;
    /**
     * 登場人物の主キー
     */
    private String _characterNo = "";

    /**
     * コンストラクタ
     */
    public CharacterJsonAccess() {
        this._character = new HashMap<>();
    }

    /**
     * セッター
     * @param character 登場人物情報が格納された配列
     */
    private void setCharacter(HashMap<String, String> character) {
        _character = character;
    }

    /**
     * ゲッター
     * @return 登場人物情報が格納された配列
     */
    public static HashMap<String, String> getCharacter() {
        return _character;
    }

    @Override
    public String doInBackground(String... params) {
        String urlStr = ACCESS_URL;
        _characterNo = params[0];
        String plot = params[1];
        String phonetic = params[2];
        String name = params[3];
        String another = params[4];
        String imagePath = params[5];
        String age = params[6];
        String gender = params[7];
        String birthday = params[8];
        String height = params[9];
        String weight = params[10];
        String firstPerson = params[11];
        String secondPerson = params[12];
        String belongs = params[13];
        String skill = params[14];
        String profile = params[15];
        String livedProcess = params[16];
        String personality = params[17];
        String appearance = params[18];
        String other = params[19];

        String data = "no=" + _characterNo +
                "&plot=" + plot +
                "&phonetic=" + phonetic +
                "&name=" + name +
                "&another=" + another +
                "&image_path=" + imagePath +
                "&age=" + age +
                "&gender=" + gender +
                "&birthday=" + birthday +
                "&height=" + height +
                "&weight=" + weight +
                "&first_person=" + firstPerson +
                "&second_person=" + secondPerson +
                "&belongs=" + belongs +
                "&skill=" + skill +
                "&profile=" + profile +
                "&lived_process=" + livedProcess +
                "&personality=" + personality +
                "&appearance=" + appearance +
                "&other=" + other;
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
            String no = "";
            String plot = "";
            String phonetic = "";
            String name = "";
            String another = "";
            String imagePath = "";
            String age = "";
            String gender = "";
            String birthday = "";
            String height = "";
            String weight = "";
            String firstPerson = "";
            String secondPerson = "";
            String belongs = "";
            String skill = "";
            String profile = "";
            String livedProcess = "";
            String personality = "";
            String appearance = "";
            String other = "";

            try {
                JSONObject rootJSON = new JSONObject(result);
                no = rootJSON.getString("newId");

                //登場人物一覧のJSONデータを解析し、新規登録あるいは更新したレコードのデータのみを取得
                JSONArray characterArray = rootJSON.getJSONArray("characters");
                for(int i = 0; i < characterArray.length(); i++) {
                    map = new HashMap<>();
                    JSONObject characterNow = characterArray.getJSONObject(i);

                    if(no.equals( characterNow.getString("no")) ) {
                        plot = characterNow.getString("plot");
                        phonetic = characterNow.getString("phonetic");
                        name = characterNow.getString("name");
                        another = characterNow.getString("another");
                        imagePath = characterNow.getString("image_path");
                        age = characterNow.getString("age");
                        gender = characterNow.getString("gender");
                        birthday = characterNow.getString("birthday");
                        height = characterNow.getString("height");
                        weight = characterNow.getString("weight");
                        firstPerson = characterNow.getString("first_person");
                        secondPerson = characterNow.getString("second_person");
                        belongs = characterNow.getString("belongs");
                        skill = characterNow.getString("skill");
                        profile = characterNow.getString("profile");
                        livedProcess = characterNow.getString("lived_process");
                        personality = characterNow.getString("personality");
                        appearance = characterNow.getString("appearance");
                        other = characterNow.getString("other");

                        map.put("no", no);
                        map.put("plot", plot);
                        map.put("phonetic", phonetic);
                        map.put("name", name);
                        map.put("another", another);
                        map.put("image_path", imagePath);
                        map.put("age", age);
                        map.put("gender", gender);
                        map.put("birthday", birthday);
                        map.put("height", height);
                        map.put("weight", weight);
                        map.put("first_person", firstPerson);
                        map.put("second_person", secondPerson);
                        map.put("belongs", belongs);
                        map.put("skill", skill);
                        map.put("profile", profile);
                        map.put("lived_process", livedProcess);
                        map.put("personality", personality);
                        map.put("appearance", appearance);
                        map.put("other", other);
                        setCharacter(map);

                        break;
                    }
                }
            }
            catch (JSONException ex) {
                Log.e(DEBUG_TAG, "JSON解析失敗", ex);
            }

            //TODO:遷移元で分岐ではなく新規か編集か（編集画面からしか飛ばされない）
            //TODO:↑登場人物の主キーが空か否かで新規か編集かの判別できる
            //新規登録の場合
//            if("".equals(_characterNo)) {
//
//            }
//            //編集の場合
//            else {
                _callBack.CallBack(map);
//            }

            //新規登録だった場合（遷移元：PlotListActivity、遷移先：OutlineEditActivity）
//            if(NOW_ACTIVITY.equals( TitleSetDialogCreate.NOW_ACTIVITY )) {
//                PlotListActivity activity = (PlotListActivity) _context;
//                activity.onPositiveButtonClick(_context);
//            }
//            else {
//                _callBack.CallBack(map);
//            }
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
