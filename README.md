# MarkdownView
[![Release](https://jitpack.io/v/fiskurgit/MarkdownView.svg)](https://jitpack.io/#fiskurgit/MarkdownView) [![Build Status](https://travis-ci.org/fiskurgit/MarkdownView.svg?branch=master)](https://travis-ci.org/fiskurgit/MarkdownView) [![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](https://github.com/fiskurgit/ChipCloud/blob/master/LICENSE) [![Gitter](https://img.shields.io/gitter/room/nwjs/nw.js.svg?maxAge=2592000)](https://gitter.im/fiskurgit/fiskur) 

Markdown view for Android. Display [Markdown](https://en.wikipedia.org/wiki/Markdown) in your apps, useful for FAQs, Help, Size Guides, About screens etc. To make things really easy there's an Activity included, just use the included IntentBuilder.

This library is essentially just a wrapper around [Marked.js](https://github.com/chjj/marked) and uses [Github Markdown CSS](https://github.com/sindresorhus/github-markdown-css) to style the generated Markdown.

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

```java
MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdown_view);
markdownView.showMarkdown("# Hello Markdown\n\n[fiskurgit](https://github.com/fiskurgit)");
```

##Dependency

Add jitpack.io to your root build.gradle, eg:

```groovy
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

then add the dependency to your project build.gradle:

```groovy
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.github.fiskurgit:MarkdownView:1.0.1'
}
```
You can find the latest version in the releases tab above: https://github.com/fiskurgit/MarkdownView/releases

More options at jitpack.io: https://jitpack.io/#fiskurgit/MarkdownView

##Licence

Full licence here: https://github.com/fiskurgit/MarkdownView/blob/master/LICENSE

In short:

> The MIT License is a permissive license that is short and to the point. It lets people do anything they want with your code as long as they provide attribution back to you and don’t hold you liable.
