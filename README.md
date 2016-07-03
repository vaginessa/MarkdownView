# MarkdownView - work in progress/coming soon.
Markdown view for Android - Work in progress, not fit for use.

## Usage

### Activity
```java
Intent intent = MarkdownActivity.IntentBuilder.getBuilder()
    .showToolbar(true)
    .title("Markdown")
    .displayHomeAsUp(true)
    .markdown("# Hello Markdown\n\n[fiskurgit](https://github.com/fiskurgit)")
    .allowGestures(false)
    .build(MainActivity.this);

startActivity(intent);
```
### View
```xml
<eu.fiskur.markdownview.MarkdownView
    android:id="@+id/markdown_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
```
