# MarkdownView
Markdown view for Android. Display [Markdown](https://en.wikipedia.org/wiki/Markdown) in your apps, useful for FAQs, Help, Size Guides, About screens etc. To make things really easy there's an Activity included, just use the included IntentBuilder.

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
