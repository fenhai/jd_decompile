package cn.jiguang.e.b;

import android.content.Context;
import android.telephony.TelephonyManager;
import cn.jiguang.e.b.a.a;
import cn.jiguang.e.b.a.c;
import cn.jiguang.e.b.a.d;
import cn.jiguang.e.b.a.e;
import java.util.ArrayList;

public final class b {
    private static b b;
    private static final String[] z;
    private Class<?>[] a = new Class[]{c.class, d.class, e.class};
    private a c = null;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "/Ej$DlNc&\\?\rn5]-T/0N?\ra2C ";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0061;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r3;
        r10 = r8;
        r13 = r7;
        r7 = r3;
        r3 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0055;
            case 1: goto L_0x0058;
            case 2: goto L_0x005b;
            case 3: goto L_0x005e;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 47;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0018;
    L_0x002d:
        r7 = r3;
        r3 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x0050;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r4] = r3;
        r0 = "\u0018Hc\nN\"Lh\"]";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "lCj0\u000f%C|3N\"NjgI-Dc\"Kv";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0050:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0055:
        r11 = 76;
        goto L_0x0021;
    L_0x0058:
        r11 = 45;
        goto L_0x0021;
    L_0x005b:
        r11 = 15;
        goto L_0x0021;
    L_0x005e:
        r11 = 71;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jiguang.e.b.b.<clinit>():void");
    }

    private b() {
    }

    private static a a(Context context, Class<?>[] clsArr) {
        if (clsArr == null) {
            cn.jiguang.c.d.i(z[1], z[0]);
            return null;
        }
        int length = clsArr.length;
        int i = 0;
        while (i < length) {
            try {
                a aVar = (a) clsArr[i].newInstance();
                if (aVar.b(context)) {
                    return aVar;
                }
                i++;
            } catch (Throwable th) {
                cn.jiguang.c.d.i(z[1], new StringBuilder(z[2]).append(th).toString());
            }
        }
        return null;
    }

    public static b a() {
        if (b == null) {
            synchronized (TelephonyManager.class) {
                if (b == null) {
                    b = new b();
                }
            }
        }
        return b;
    }

    public final ArrayList<a> a(Context context) {
        if (this.c == null) {
            a bVar = new cn.jiguang.e.b.a.b();
            if (bVar.b(context)) {
                this.c = bVar;
                return bVar.a(context);
            }
            a a = a(context, this.a);
            if (a != null) {
                this.c = a;
                return a.a(context);
            }
            this.c = bVar;
        }
        return this.c.a(context);
    }
}
