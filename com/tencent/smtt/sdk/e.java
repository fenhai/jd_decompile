package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

final class e extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ ValueCallback c;

    e(Context context, String str, ValueCallback valueCallback) {
        this.a = context;
        this.b = str;
        this.c = valueCallback;
    }

    public void run() {
        bo b = bo.b();
        b.a(this.a, null);
        boolean z = false;
        if (b.c()) {
            z = b.d().a(this.a, this.b);
        }
        new Handler(Looper.getMainLooper()).post(new f(this, z));
    }
}
