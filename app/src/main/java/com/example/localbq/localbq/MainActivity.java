package com.example.localbq.localbq;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class MainActivity extends AppCompatActivity {
    private SsoHandler mSsoHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        WebView mSinaAgreementWebView = (WebView)findViewById(R.id.wv);
        final EditText editText = (EditText) findViewById(R.id.edit_text);
//        mSinaAgreementWebView.loadUrl("http://baidu.adssss");
        mSsoHandler = new SsoHandler(MainActivity.this,createAuthInfo());
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)findViewById(R.id.text_view)).setText(editText.getText());

                mSsoHandler.authorizeClientSso(new WeiboAuthListener() {
                    @Override
                    public void onComplete(Bundle bundle) {
                        Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWeiboException(WeiboException e) {
                        Toast.makeText(MainActivity.this,"失败" + e.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSsoHandler.authorizeCallBack(requestCode,resultCode,data);
    }

    private AuthInfo createAuthInfo(){
        Context context = this.getApplicationContext();
        return new AuthInfo(context, "211160679", "http://oauth.weico.cc",
                "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write");
    }

    public static final String WEIXIN_APP_ID = "wx2faea841a8e0c605";

    public static final String TENCENT_APP_ID = "101027235";

    public static final String WEIBO_APP_KEY = "2391992726";

    public static final String WEIBO_REDIRECT_URL = "http://open.weibo.com/apps/2391992726/privilege/oauth";

    public static final String WEIBO_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
}
