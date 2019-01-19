package local.hal.st32.android.ploteditor;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * テスト動作用アクティビティクラス
 *
 * @author ohs60224
 */
public class TestActivity extends AppCompatActivity {

    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private LinearLayout mLayout;
    private Button mBtAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mLayout = findViewById(R.id.layout);

        mBtAdd = mLayout.findViewById(R.id.btAdd);
        mBtAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button button1 = new Button(TestActivity.this);
                button1.setText("Button1");
                mLayout.addView(button1, 1, createParam(WC, WC));

                TextView text = new TextView(TestActivity.this);
                text.setText("Text");
                mLayout.addView(text, createParam(WC, WC));

                Button button2 = new Button(TestActivity.this);
                button2.setText("Button2");
                mLayout.addView(button2, 2, createParam(WC, WC));
            }
        });


    }

    private LinearLayout.LayoutParams createParam(int w, int h){
        return new LinearLayout.LayoutParams(w, h);
    }
}
