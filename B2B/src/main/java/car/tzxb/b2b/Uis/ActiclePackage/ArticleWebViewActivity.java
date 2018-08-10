package car.tzxb.b2b.Uis.ActiclePackage;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import car.tzxb.b2b.BasePackage.BasePresenter;
import car.tzxb.b2b.BasePackage.MyBaseAcitivity;
import car.tzxb.b2b.R;

public class ArticleWebViewActivity extends MyBaseAcitivity {
    @BindView(R.id.article_webview)
    WebView webview;
    @BindView(R.id.tv_actionbar_title)
    TextView tv_title;
    private String title;
    private String content;

    @Override
    public void initParms(Bundle parms) {
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_article_web_view;
    }

    @Override
    public void doBusiness(Context mContext) {
        tv_title.setText(title);
        initWebview();
    }

    private void initWebview() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setDefaultTextEncodingName("UTF-8");
        webview.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(webview.getSettings()
                    .MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
        }

        webview.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);


    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick(R.id.tv_actionbar_back)
    public void back() {
        onBackPressed();
    }
}
