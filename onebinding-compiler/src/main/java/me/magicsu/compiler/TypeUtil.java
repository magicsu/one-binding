package me.magicsu.compiler;

import com.squareup.javapoet.ClassName;

public class TypeUtil {

    // ToDo

    public static final ClassName FINDER = ClassName.get("me.magicsu.api", "Finder");
    public static final ClassName ANDROID_ON_CLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    public static final ClassName ANDROID_VIEW = ClassName.get("android.view", "View");
    public static final ClassName INJECTOR = ClassName.get("me.magicsu.api", "Injector");
}
