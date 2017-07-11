package vip.yile.sinademo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

public class MainActivity extends AppCompatActivity implements WbShareCallback {

    private WbShareHandler handler;
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WbSdk.install(this,new AuthInfo(this, "3205227303", "https://api.weibo.com/oauth2/default.html", SCOPE));
        handler = new WbShareHandler(this);
        handler.registerApp();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handler.doResultIntent(intent,this);
    }

    public void share(View view) {
        WeiboMultiMessage multiMessage=new WeiboMultiMessage();
        multiMessage.textObject=getTextObj();
        multiMessage.imageObject= getImageObj();
        handler.shareMessage(multiMessage,false);
    }

    @Override
    public void onWbShareSuccess() {
        Log.e("message","成功");
    }

    @Override
    public void onWbShareCancel() {
        Log.e("message","取消");
    }

    @Override
    public void onWbShareFail() {
        Log.e("message","失败");
    }
    /**
     * 创建文本消息对象。
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = "分享内容"+"http://www.baidu.com";
        textObject.title = "分享标题";
        return textObject;
    }

    /**
     * 创建图片消息对象。
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(BitmapFactory.decodeResource(getResources(),R.drawable.ic_share_music_thumb));
        return imageObject;
    }

}
