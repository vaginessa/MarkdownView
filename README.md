# MarkdownView - work in progress/coming soon.
Markdown view for Android

## Usage: Activity
```java
Intent intent = MarkdownActivity.IntentBuilder.getBuilder()
    .showToolbar(true)
    .title("Markdown")
    .displayHomeAsUp(true)
    .markdown("# Hello Markdown\\n\\n[fiskurgit](https://github.com/fiskurgit)")
    .openLinksExternally(true)
    .allowGestures(false)
    .build(MainActivity.this);

startActivity(intent);
```

## Usage: View

```xml
<eu.fiskur.markdownview.MarkdownView
    android:id="@+id/markdown_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
```
