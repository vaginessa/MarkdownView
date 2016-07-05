package eu.fiskur.markdownview;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MarkdownView extends RelativeLayout {
  private static final String TAG = MarkdownView.class.getSimpleName();
  private WebView webView;
  private WebSettings webSettings;
  private MarkdownWebViewClient webViewClient;

  private static final String MARKDOWN_MARKUP_TEMPLATE = "<!doctype html>\n"
      + "<html>\n"
      + "<head>\n"
      + "  <meta charset=\"utf-8\"/>\n"
      + "  <title>Marked in the browser</title>\n"
      + "  <link rel=\"stylesheet\" href=\"./githubmarkdown.css\">\n"
      + "  <script src=\"./marked.js\"></script>\n"
      + "  <script src=\"./markdown.js\"></script>\n"
      + "</head>\n"
      + "<body>\n"
      + "  <div id=\"content\" class=\"markdown-body\"></div>\n"
      + "<script>\n"
      + "    document.getElementById('content').innerHTML =\n"
      + "      marked('%s');\n"
      + "  </script>"

      + "</body>\n"
      + "</html>";

  public MarkdownView(Context context) {
    super(context);
    setup();
  }

  public MarkdownView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setup();
  }

  public MarkdownView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setup();
  }

  private void setup(){
    LayoutInflater.from(getContext()).inflate(R.layout.markdown_view, this);
    webView = (WebView) findViewById(R.id.markdown_web_view);
    webView.loadUrl("about:blank");//clear all

    progress = (ProgressBar) findViewById(R.id.markdown_progress_bar);

    webSettings = webView.getSettings();
    webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    webSettings.setJavaScriptEnabled(true);

    //To handle web links:
    webViewClient = new MarkdownWebViewClient();
    webView.setWebViewClient(webViewClient);

    allowGestures(false);
  }

  private class MarkdownWebViewClient extends WebViewClient {

    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
      Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
      getContext().startActivity(intent);
      return true;
    }
  }

  public void showMarkdown(String markdown){
    if(markdown == null || markdown.isEmpty()){
      return;
    }
    markdown = markdown.replace("\n", "\\n");
    webView.loadDataWithBaseURL("file:///android_asset/", String.format(MARKDOWN_MARKUP_TEMPLATE, markdown), "text/html", "utf-8", null);
  }

  public void showMarkdown(int fileId){
    StringBuffer sb = new StringBuffer();
    BufferedReader reader = null;
    InputStreamReader isr = null;
    try {
      isr = new InputStreamReader(getContext().getResources().openRawResource(fileId));
      reader = new BufferedReader(isr);

      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      Log.e(TAG, e.toString());
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {}
      }

      if(isr != null){
        try {
          isr.close();
        } catch (IOException e) {}
      }
    }
    showMarkdown(sb.toString());
  }

  //Off by default
  public void allowGestures(boolean allowGestures){
    if(allowGestures){
      webSettings.setBuiltInZoomControls(true);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        webSettings.setDisplayZoomControls(false);
      }
    }else{
      webSettings.setBuiltInZoomControls(false);
    }
  }
}
