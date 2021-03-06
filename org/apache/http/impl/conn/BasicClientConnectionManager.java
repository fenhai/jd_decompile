package org.apache.http.impl.conn;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpClientConnection;
import org.apache.http.annotation.GuardedBy;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@ThreadSafe
/* compiled from: TbsSdkJava */
public class BasicClientConnectionManager implements ClientConnectionManager {
    private static final AtomicLong COUNTER = new AtomicLong();
    public static final String MISUSE_MESSAGE = "Invalid use of BasicClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    @GuardedBy("this")
    private ManagedClientConnectionImpl conn;
    private final ClientConnectionOperator connOperator;
    private final Log log;
    @GuardedBy("this")
    private HttpPoolEntry poolEntry;
    private final SchemeRegistry schemeRegistry;
    @GuardedBy("this")
    private volatile boolean shutdown;

    public BasicClientConnectionManager(SchemeRegistry schemeRegistry) {
        this.log = LogFactory.getLog(getClass());
        Args.notNull(schemeRegistry, "Scheme registry");
        this.schemeRegistry = schemeRegistry;
        this.connOperator = createConnectionOperator(schemeRegistry);
    }

    public BasicClientConnectionManager() {
        this(SchemeRegistryFactory.createDefault());
    }

    protected void finalize() throws Throwable {
        try {
            shutdown();
        } finally {
            super.finalize();
        }
    }

    public SchemeRegistry getSchemeRegistry() {
        return this.schemeRegistry;
    }

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
        return new DefaultClientConnectionOperator(schemeRegistry);
    }

    public final ClientConnectionRequest requestConnection(final HttpRoute httpRoute, final Object obj) {
        return new ClientConnectionRequest() {
            public void abortRequest() {
            }

            public ManagedClientConnection getConnection(long j, TimeUnit timeUnit) {
                return BasicClientConnectionManager.this.getConnection(httpRoute, obj);
            }
        };
    }

    private void assertNotShutdown() {
        Asserts.check(!this.shutdown, "Connection manager has been shut down");
    }

    ManagedClientConnection getConnection(HttpRoute httpRoute, Object obj) {
        ManagedClientConnection managedClientConnection;
        Args.notNull(httpRoute, "Route");
        synchronized (this) {
            assertNotShutdown();
            if (this.log.isDebugEnabled()) {
                this.log.debug("Get connection for route " + httpRoute);
            }
            Asserts.check(this.conn == null, MISUSE_MESSAGE);
            if (!(this.poolEntry == null || this.poolEntry.getPlannedRoute().equals(httpRoute))) {
                this.poolEntry.close();
                this.poolEntry = null;
            }
            if (this.poolEntry == null) {
                this.poolEntry = new HttpPoolEntry(this.log, Long.toString(COUNTER.getAndIncrement()), httpRoute, this.connOperator.createConnection(), 0, TimeUnit.MILLISECONDS);
            }
            if (this.poolEntry.isExpired(System.currentTimeMillis())) {
                this.poolEntry.close();
                this.poolEntry.getTracker().reset();
            }
            this.conn = new ManagedClientConnectionImpl(this, this.connOperator, this.poolEntry);
            managedClientConnection = this.conn;
        }
        return managedClientConnection;
    }

    private void shutdownConnection(HttpClientConnection httpClientConnection) {
        try {
            httpClientConnection.shutdown();
        } catch (Throwable e) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("I/O exception shutting down connection", e);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void releaseConnection(org.apache.http.conn.ManagedClientConnection r7, long r8, java.util.concurrent.TimeUnit r10) {
        /*
        r6 = this;
        r0 = r7 instanceof org.apache.http.impl.conn.ManagedClientConnectionImpl;
        r1 = "Connection class mismatch, connection not obtained from this manager";
        org.apache.http.util.Args.check(r0, r1);
        r0 = r7;
        r0 = (org.apache.http.impl.conn.ManagedClientConnectionImpl) r0;
        monitor-enter(r0);
        r1 = r6.log;	 Catch:{ all -> 0x004a }
        r1 = r1.isDebugEnabled();	 Catch:{ all -> 0x004a }
        if (r1 == 0) goto L_0x002b;
    L_0x0013:
        r1 = r6.log;	 Catch:{ all -> 0x004a }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x004a }
        r2.<init>();	 Catch:{ all -> 0x004a }
        r3 = "Releasing connection ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x004a }
        r2 = r2.append(r7);	 Catch:{ all -> 0x004a }
        r2 = r2.toString();	 Catch:{ all -> 0x004a }
        r1.debug(r2);	 Catch:{ all -> 0x004a }
    L_0x002b:
        r1 = r0.getPoolEntry();	 Catch:{ all -> 0x004a }
        if (r1 != 0) goto L_0x0033;
    L_0x0031:
        monitor-exit(r0);	 Catch:{ all -> 0x004a }
    L_0x0032:
        return;
    L_0x0033:
        r1 = r0.getManager();	 Catch:{ all -> 0x004a }
        if (r1 != r6) goto L_0x004d;
    L_0x0039:
        r1 = 1;
    L_0x003a:
        r2 = "Connection not obtained from this manager";
        org.apache.http.util.Asserts.check(r1, r2);	 Catch:{ all -> 0x004a }
        monitor-enter(r6);	 Catch:{ all -> 0x004a }
        r1 = r6.shutdown;	 Catch:{ all -> 0x00dd }
        if (r1 == 0) goto L_0x004f;
    L_0x0044:
        r6.shutdownConnection(r0);	 Catch:{ all -> 0x00dd }
        monitor-exit(r6);	 Catch:{ all -> 0x00dd }
        monitor-exit(r0);	 Catch:{ all -> 0x004a }
        goto L_0x0032;
    L_0x004a:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x004a }
        throw r1;
    L_0x004d:
        r1 = 0;
        goto L_0x003a;
    L_0x004f:
        r1 = r0.isOpen();	 Catch:{ all -> 0x00ca }
        if (r1 == 0) goto L_0x005e;
    L_0x0055:
        r1 = r0.isMarkedReusable();	 Catch:{ all -> 0x00ca }
        if (r1 != 0) goto L_0x005e;
    L_0x005b:
        r6.shutdownConnection(r0);	 Catch:{ all -> 0x00ca }
    L_0x005e:
        r1 = r0.isMarkedReusable();	 Catch:{ all -> 0x00ca }
        if (r1 == 0) goto L_0x00af;
    L_0x0064:
        r2 = r6.poolEntry;	 Catch:{ all -> 0x00ca }
        if (r10 == 0) goto L_0x00c4;
    L_0x0068:
        r1 = r10;
    L_0x0069:
        r2.updateExpiry(r8, r1);	 Catch:{ all -> 0x00ca }
        r1 = r6.log;	 Catch:{ all -> 0x00ca }
        r1 = r1.isDebugEnabled();	 Catch:{ all -> 0x00ca }
        if (r1 == 0) goto L_0x00af;
    L_0x0074:
        r2 = 0;
        r1 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
        if (r1 <= 0) goto L_0x00c7;
    L_0x007a:
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ca }
        r1.<init>();	 Catch:{ all -> 0x00ca }
        r2 = "for ";
        r1 = r1.append(r2);	 Catch:{ all -> 0x00ca }
        r1 = r1.append(r8);	 Catch:{ all -> 0x00ca }
        r2 = " ";
        r1 = r1.append(r2);	 Catch:{ all -> 0x00ca }
        r1 = r1.append(r10);	 Catch:{ all -> 0x00ca }
        r1 = r1.toString();	 Catch:{ all -> 0x00ca }
    L_0x0097:
        r2 = r6.log;	 Catch:{ all -> 0x00ca }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ca }
        r3.<init>();	 Catch:{ all -> 0x00ca }
        r4 = "Connection can be kept alive ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x00ca }
        r1 = r3.append(r1);	 Catch:{ all -> 0x00ca }
        r1 = r1.toString();	 Catch:{ all -> 0x00ca }
        r2.debug(r1);	 Catch:{ all -> 0x00ca }
    L_0x00af:
        r0.detach();	 Catch:{ all -> 0x00dd }
        r1 = 0;
        r6.conn = r1;	 Catch:{ all -> 0x00dd }
        r1 = r6.poolEntry;	 Catch:{ all -> 0x00dd }
        r1 = r1.isClosed();	 Catch:{ all -> 0x00dd }
        if (r1 == 0) goto L_0x00c0;
    L_0x00bd:
        r1 = 0;
        r6.poolEntry = r1;	 Catch:{ all -> 0x00dd }
    L_0x00c0:
        monitor-exit(r6);	 Catch:{ all -> 0x00dd }
        monitor-exit(r0);	 Catch:{ all -> 0x004a }
        goto L_0x0032;
    L_0x00c4:
        r1 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ all -> 0x00ca }
        goto L_0x0069;
    L_0x00c7:
        r1 = "indefinitely";
        goto L_0x0097;
    L_0x00ca:
        r1 = move-exception;
        r0.detach();	 Catch:{ all -> 0x00dd }
        r2 = 0;
        r6.conn = r2;	 Catch:{ all -> 0x00dd }
        r2 = r6.poolEntry;	 Catch:{ all -> 0x00dd }
        r2 = r2.isClosed();	 Catch:{ all -> 0x00dd }
        if (r2 == 0) goto L_0x00dc;
    L_0x00d9:
        r2 = 0;
        r6.poolEntry = r2;	 Catch:{ all -> 0x00dd }
    L_0x00dc:
        throw r1;	 Catch:{ all -> 0x00dd }
    L_0x00dd:
        r1 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x00dd }
        throw r1;	 Catch:{ all -> 0x004a }
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.BasicClientConnectionManager.releaseConnection(org.apache.http.conn.ManagedClientConnection, long, java.util.concurrent.TimeUnit):void");
    }

    public void closeExpiredConnections() {
        synchronized (this) {
            assertNotShutdown();
            long currentTimeMillis = System.currentTimeMillis();
            if (this.poolEntry != null && this.poolEntry.isExpired(currentTimeMillis)) {
                this.poolEntry.close();
                this.poolEntry.getTracker().reset();
            }
        }
    }

    public void closeIdleConnections(long j, TimeUnit timeUnit) {
        long j2 = 0;
        Args.notNull(timeUnit, "Time unit");
        synchronized (this) {
            assertNotShutdown();
            long toMillis = timeUnit.toMillis(j);
            if (toMillis >= 0) {
                j2 = toMillis;
            }
            j2 = System.currentTimeMillis() - j2;
            if (this.poolEntry != null && this.poolEntry.getUpdated() <= j2) {
                this.poolEntry.close();
                this.poolEntry.getTracker().reset();
            }
        }
    }

    public void shutdown() {
        synchronized (this) {
            this.shutdown = true;
            try {
                if (this.poolEntry != null) {
                    this.poolEntry.close();
                }
                this.poolEntry = null;
                this.conn = null;
            } catch (Throwable th) {
                this.poolEntry = null;
                this.conn = null;
            }
        }
    }
}
