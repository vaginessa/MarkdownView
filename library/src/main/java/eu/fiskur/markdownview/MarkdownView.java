package eu.fiskur.markdownview;

import android.content.Context;
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
import java.io.File;

public class MarkdownView extends RelativeLayout {
  private static final String TAG = MarkdownView.class.getSimpleName();
  private WebView webView;
  private WebSettings webSettings;
  private ProgressBar progress;

  private File file = null;
  private String code = null;

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
    webSettings.setJavaScriptEnabled(true);

    //add pinch/zoom
    webSettings.setBuiltInZoomControls(true);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      webSettings.setDisplayZoomControls(false);
    }

    //To get the page finished loading event:
    webView.setWebViewClient(new MarkdownWebViewClient());

    //Just to log JS stuff nicely:
    webView.setWebChromeClient(new MarkdownChromeClient());

    //So Javascript can talk back to native:
    webView.addJavascriptInterface(new JSInterface(), "Android");

    //Does this need to be called yet?
    //webView.loadDataWithBaseURL("file:///android_asset/", String.format(MARKDOWN_MARKUP_TEMPLATE), "text/html", "utf-8", null);
  }

  private class MarkdownWebViewClient extends WebViewClient {
    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      if(file != null){
        l("onPageFinished() loading file...");
      }else if(code != null){
        l("onPageFinished() loading snippet...");
        //webView.loadUrl("javascript:loadSnippet('" + code + "');");
      }

    }
  }

  private class MarkdownChromeClient extends WebChromeClient {
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
      String message = consoleMessage.message() + " -- line " + consoleMessage.lineNumber();
      switch (consoleMessage.messageLevel()) {
        case ERROR:
          logErrorMessage(message);
          break;
        default:
          logInfoMessage(message);
          break;
      }
      return true;
    }

    private void logInfoMessage(String message) {
      Log.i(TAG, message);
    }

    private void logErrorMessage(String message) {
      Log.e(TAG, message);
    }
  }

  private class JSInterface {

    @JavascriptInterface
    public void showToast(String message) {
      l("showToast: " + message);
    }

    @JavascriptInterface
    public void finishedImport() {
      post(new Runnable() {
        @Override
        public void run() {
          progress.setVisibility(View.GONE);
        }
      });
    }
  }

  //todo - double escape markdown syntax, eg: \\#
  //todo - double escape line ends, eg: \\n
  public void showMarkdown(String markdown){
    l("showMarkdown(): " + markdown);
    webView.loadDataWithBaseURL("file:///android_asset/", String.format(MARKDOWN_MARKUP_TEMPLATE, markdown), "text/html", "utf-8", null);
  }

  public void showMarkdown(File markdownFile){
    //todo
  }

  //On by default
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

  private void l(String message){
    Log.d(TAG, message);
  }
}
