package org.greenrobot.eventbus;

import android.util.Log;

/* compiled from: TbsSdkJava */
final class b implements Runnable {
    private final i a = new i();
    private final c b;
    private volatile boolean c;

    b(c cVar) {
        this.b = cVar;
    }

    public void a(n nVar, Object obj) {
        h a = h.a(nVar, obj);
        synchronized (this) {
            this.a.a(a);
            if (!this.c) {
                this.c = true;
                this.b.b().execute(this);
            }
        }
    }

    public void run() {
        while (true) {
            try {
                h a = this.a.a(1000);
                if (a == null) {
                    synchronized (this) {
                        a = this.a.a();
                        if (a == null) {
                            this.c = false;
                            this.c = false;
                            return;
                        }
                    }
                }
                this.b.a(a);
            } catch (Throwable e) {
                Log.w("Event", Thread.currentThread().getName() + " was interruppted", e);
                this.c = false;
                return;
            } catch (Throwable th) {
                this.c = false;
            }
        }
    }
}
