package android.support.v4.os;

import android.os.Build.VERSION;

/* compiled from: TbsSdkJava */
public class BuildCompat {
    private BuildCompat() {
    }

    public static boolean isAtLeastN() {
        return VERSION.SDK_INT >= 24;
    }

    public static boolean isAtLeastNMR1() {
        return VERSION.SDK_INT >= 25;
    }
}
