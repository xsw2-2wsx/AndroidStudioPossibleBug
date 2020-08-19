Build: AI-193.6911.18.40.6626763, 202006250242, 

AI-193.6911.18.40.6626763, JRE 1.8.0_242-release-1644-b01x64 JetBrains s.r.o, OS Windows 10(amd64) v10.0 , screens 1920x1080, 1920x1080

AS: 4.0.1; Kotlin plugin: 1.4.0-release-Studio4.0-1; Android Gradle Plugin: 4.0.1; Gradle: 6.1.1; NDK: from local.properties: (not specified), latest from SDK: (not found); LLDB: pinned revision 3.1 not found, latest from SDK: (package not found); CMake: from local.properties: (not specified), latest from SDK: (not found), from PATH: (not found)

### GitHub project: https://github.com/xsw2-2wsx/AndroidStudioPossibleBug

**Steps for recreation:**

1. Create a new Android Studio gradle project
2. Create a custom property, for example:
```
import kotlin.reflect.KProperty

class CustomProperty<T>(var _value: T) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = _value

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        _value = value
    }
}
```

3. Create a class that extends View and use the custom property:
```
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CustomView : View {

    // When this line is commented the view renders properly in the layout editor
    var property: Int by CustomProperty(0)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) = canvas?.drawCircle(
        width/2F,
        height/2F,
        min(width, height)/2F,
        Paint().apply { color = Color.RED }
    ) ?: Unit
}
```

4. Add the CustomView to the layout and build the project

GitHub project: https://github.com/xsw2-2wsx/AndroidStudioPossibleBug

**Expected:** In the layout editor i can see a rendered preview of my view.  
**Observed:** The view bounds are filled with a gray color and the fully qualified class name is written on it. One of the two following exceptions is thrown:

**Exception 1:**
```
java.lang.NoClassDefFoundError: Could not initialize class com.xsw22wsx.possiblebug.CustomView
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
	at org.jetbrains.android.uipreview.ViewLoader.createNewInstance(ViewLoader.java:403)
	at org.jetbrains.android.uipreview.ViewLoader.loadClass(ViewLoader.java:186)
	at org.jetbrains.android.uipreview.ViewLoader.loadView(ViewLoader.java:144)
	at com.android.tools.idea.rendering.LayoutlibCallbackImpl.loadView(LayoutlibCallbackImpl.java:309)
	at android.view.BridgeInflater.loadCustomView(BridgeInflater.java:416)
	at android.view.BridgeInflater.loadCustomView(BridgeInflater.java:427)
	at android.view.BridgeInflater.createViewFromTag(BridgeInflater.java:331)
	at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:961)
	at android.view.LayoutInflater.rInflate_Original(LayoutInflater.java:1123)
	at android.view.LayoutInflater_Delegate.rInflate(LayoutInflater_Delegate.java:72)
	at android.view.LayoutInflater.rInflate(LayoutInflater.java:1097)
	at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:1084)
	at android.view.LayoutInflater.inflate(LayoutInflater.java:682)
	at android.view.LayoutInflater.inflate(LayoutInflater.java:501)
	at com.android.layoutlib.bridge.impl.RenderSessionImpl.inflate(RenderSessionImpl.java:348)
	at com.android.layoutlib.bridge.Bridge.createSession(Bridge.java:404)
	at com.android.tools.idea.layoutlib.LayoutLibrary.createSession(LayoutLibrary.java:141)
	at com.android.tools.idea.rendering.RenderTask.createRenderSession(RenderTask.java:678)
	at com.android.tools.idea.rendering.RenderTask.lambda$inflate$8(RenderTask.java:809)
	at java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1604)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
```
**Excepition 2:**
```
java.lang.NoSuchMethodError: kotlin.jvm.internal.MutablePropertyReference1Impl.<init>(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;I)V
	at com.xsw22wsx.possiblebug.CustomView.<clinit>(CustomView.kt)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
	at org.jetbrains.android.uipreview.ViewLoader.createNewInstance(ViewLoader.java:403)
	at org.jetbrains.android.uipreview.ViewLoader.loadClass(ViewLoader.java:186)
	at org.jetbrains.android.uipreview.ViewLoader.loadView(ViewLoader.java:144)
	at com.android.tools.idea.rendering.LayoutlibCallbackImpl.loadView(LayoutlibCallbackImpl.java:309)
	at android.view.BridgeInflater.loadCustomView(BridgeInflater.java:416)
	at android.view.BridgeInflater.loadCustomView(BridgeInflater.java:427)
	at android.view.BridgeInflater.createViewFromTag(BridgeInflater.java:331)
	at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:961)
	at android.view.LayoutInflater.rInflate_Original(LayoutInflater.java:1123)
	at android.view.LayoutInflater_Delegate.rInflate(LayoutInflater_Delegate.java:72)
	at android.view.LayoutInflater.rInflate(LayoutInflater.java:1097)
	at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:1084)
	at android.view.LayoutInflater.inflate(LayoutInflater.java:682)
	at android.view.LayoutInflater.inflate(LayoutInflater.java:501)
	at com.android.layoutlib.bridge.impl.RenderSessionImpl.inflate(RenderSessionImpl.java:348)
	at com.android.layoutlib.bridge.Bridge.createSession(Bridge.java:404)
	at com.android.tools.idea.layoutlib.LayoutLibrary.createSession(LayoutLibrary.java:141)
	at com.android.tools.idea.rendering.RenderTask.createRenderSession(RenderTask.java:678)
	at com.android.tools.idea.rendering.RenderTask.lambda$inflate$8(RenderTask.java:809)
	at java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1604)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
	at java.lang.Thread.run(Thread.java:748)
```
**Despite of the exceptions thrown in the IDE, the view renders propertly on an emulator as well as a real devie.**
